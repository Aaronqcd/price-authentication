package com.pemng.serviceSystem.mining.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pemng.common.util.StringUtil;
import com.pemng.serviceSystem.base.actions.BaseDownloadAction;
import com.pemng.serviceSystem.base.util.DateUtil;
import com.pemng.serviceSystem.base.util.JsonUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.Propertiesconfiguration;
import com.pemng.serviceSystem.base.util.excelparser.NoSuchSheetException;
import com.pemng.serviceSystem.mining.dto.ZTreeObj;
import com.pemng.serviceSystem.mining.services.MiningService;
import com.pemng.serviceSystem.pojo.TMiningPriceInfo;
import com.pemng.serviceSystem.pojo.TMiningPriceSort;
import com.pemng.serviceSystem.pojo.TUserInfo;

public class MiningAction extends BaseDownloadAction {

	private MiningService miningService;
	
	private TMiningPriceSort miningPriceSort;
	private TMiningPriceInfo miningPriceInfo;
	private Pager pager = new Pager();
	
	private List<TMiningPriceInfo> list;
	private Long id;
	private Date beginTime;
	private Date endTime;
	private Long userId;
	private String address;
	private String sortName;
	private String name;		//品名
	private int tmpFlag = -1;	//附件类型
	
	public String mining(){
		List<TUserInfo> list = miningService.getUserList();
		this.getRequest().setAttribute("userList", list);
		return SUCCESS;
	}
	/**
	 * 获得分类JSON数据
	 */
	public void getMiningSort() throws IOException{
		List<ZTreeObj> list = null;
		if(miningPriceSort != null && StringUtils.isNotBlank(miningPriceSort.getSortName())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sortName", miningPriceSort.getSortName());
			//list = miningService.getMiningPriceSorts(params);
		}else{
			list = miningService.getMiningPriceSorts();
		}
		//增加"标的物目录"选择项
		list.add(0, new ZTreeObj("0", "-1", "标的物目录"));
		writeJsonToResponse(JsonUtil.list2json(list));
	}
	
	public void getMiningPrice() throws IOException{
		TMiningPriceInfo miningPriceInfo = miningService.getMiningPrice(id);
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append(StringUtil.addJsonParameter("id", miningPriceInfo.getId())).append(",");
		json.append(StringUtil.addJsonParameter("pId", miningPriceInfo.getTMiningPriceSort()==null?"":miningPriceInfo.getTMiningPriceSort().getId())).append(",");
		//json.append(StringUtil.addJsonParameter("documentNo", miningPriceInfo.getDocumentNo())).append(",");
		json.append(StringUtil.addJsonParameter("status", miningPriceInfo.getStatus())).append(",");
		json.append(StringUtil.addJsonParameter("miningPrice", miningPriceInfo.getMiningPrice())).append(",");
		if (null != miningPriceInfo.getTUserInfo() && null != miningPriceInfo.getTUserInfo().getId()) {
			json.append(StringUtil.addJsonParameter("userId", miningPriceInfo.getTUserInfo().getId())).append(",");
		}
		json.append(StringUtil.addJsonParameter("miningAddress", miningPriceInfo.getMiningAddress())).append(",");
		json.append(StringUtil.addJsonParameter("source", miningPriceInfo.getSource())).append(",");
		json.append(StringUtil.addJsonParameter("remark", miningPriceInfo.getRemark())).append(",");
		json.append(StringUtil.addJsonParameter("referenceDate", DateUtil.getYYYYMMDDFromDate(miningPriceInfo.getReferenceDate()))).append(",");
		json.append(StringUtil.addJsonParameter("miningDate", DateUtil.getYYYYMMDDFromDate(miningPriceInfo.getMiningDate()))).append(",");
		json.append(StringUtil.addJsonParameter("name", miningPriceInfo.getName())).append(",");
		json.append(StringUtil.addJsonParameter("sortName", miningPriceInfo.getSortName())).append(",");
		json.append(StringUtil.addJsonParameter("typeName", miningPriceInfo.getTypeName())).append(",");
		json.append(StringUtil.addJsonParameter("guidePrice", miningPriceInfo.getGuidePrice()));
		json.append("}");
		writeJsonData(json.toString());
	}
	public void getMiningCount() throws IOException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sortId", id);
		int count = miningService.getMiningCount(params);
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append(StringUtil.addJsonParameter("result", this.SUCCESS)).append(",");
		json.append(StringUtil.addJsonParameter("count", count));
		json.append("}");
		writeJsonData(json.toString());
	}
	/**
	 * 创建分类
	 * @throws IOException 
	 */
	public void addMiningSort() throws IOException{
		try{
			if (miningService.getMiningSortNameCount(miningPriceSort.getSortName(), null) > 0) {
				this.writeJsonToResponse("1");
				return;
			}
			TMiningPriceSort sort = miningService.addMiningSort(miningPriceSort);
			StringBuilder json = new StringBuilder();
			json.append("{");
				json.append(StringUtil.addJsonParameter("result", this.SUCCESS)).append(",");
				json.append("\"obj\":{");
					json.append(StringUtil.addJsonParameter("id", sort.getId())).append(",");
					json.append(StringUtil.addJsonParameter("sortName", sort.getSortName())).append(",");
					if (null == sort.getTMiningPriceSort() || null == sort.getTMiningPriceSort().getId() || sort.getTMiningPriceSort().getId()<0) {
						json.append(StringUtil.addJsonParameter("parentId", 0));
					}else{
						json.append(StringUtil.addJsonParameter("parentId", sort.getTMiningPriceSort().getId()));
					}
				json.append("}");
			json.append("}");
			this.writeJsonToResponse(json.toString());
		}catch(Exception ex){
			StringBuilder json = new StringBuilder();
			json.append("{");
				json.append(StringUtil.addJsonParameter("result", this.ERROR));
			json.append("}");
			this.writeJsonData(json.toString());
			this.log.error(ex, ex);
		}
	}
	
	/**
	 * 分类重命名
	 * @throws IOException 
	 */
	public void renameMiningSort() throws IOException{
		try{
			if (null != miningPriceSort && StringUtils.isNotBlank(miningPriceSort.getSortName())) {
				if (miningService.getMiningSortNameCount(miningPriceSort.getSortName(), miningPriceSort.getId()) > 0) {
					this.writeJsonToResponse("1");
					return;
				}
				miningService.renameMiningSort(miningPriceSort.getId(), miningPriceSort.getSortName());
				this.writeJsonToResponse(this.SUCCESS);
			}else{
				this.writeJsonToResponse(this.ERROR);
			}
		}catch(Exception ex){
			this.writeJsonToResponse(this.ERROR);
		}
	}
	/**
	 * 删除分类
	 * @throws IOException 
	 */
	public void removeMiningSort() throws IOException{
		try{
			miningService.removeMiningSort(miningPriceSort.getId());
			this.writeJsonToResponse(this.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			this.writeJsonToResponse(this.ERROR);
		}
	}
	/**
	 * 删除采价信息
	 * @throws IOException
	 */
	public void removeMining() throws IOException{
		try{
			miningService.removeMining(id);
			this.writeJsonToResponse(this.SUCCESS);
		}catch(Exception ex){
			this.writeJsonToResponse("采价删除失败");
		}
	}
	/**
	 * 采价列表
	 * @return
	 */
	public String miningPriceList(){
		Map<String, Object> params = new HashMap<String, Object>();
		if (null != id && id > 0) {
			params.put("sortId", id);
		}
		if(StringUtils.isNotBlank(name)){
			params.put("name", "%"+name.trim()+"%");
		}
		if(beginTime!=null){
			params.put("beginTime", beginTime);
		}
		if(endTime!=null){
			params.put("endTime", endTime);
		}
		if(userId!=null){
			params.put("userId", userId);
		}
		if(StringUtils.isNotBlank(address)){
			params.put("address", "%"+address.trim()+"%");
		}
		list = miningService.getMiningPriceList(pager, params);
		miningPriceSort = miningService.getTMiningPriceSort(id);
		List<TUserInfo> list = miningService.getUserList();
		this.getRequest().setAttribute("userList", list);
		return SUCCESS;
	}
	/**
	 * 添加采价价格
	 * @return
	 * @throws IOException 
	 */
	public void addMiningPrice() throws IOException{
		if(null != miningPriceInfo && miningPriceInfo.getTMiningPriceSort()!=null && miningPriceInfo.getTMiningPriceSort().getId()!=null){
			if (null != miningPriceInfo.getTUserInfo() && null == miningPriceInfo.getTUserInfo().getId()) {
				miningPriceInfo.setTUserInfo(null);
			}
			miningService.addMiningPrice(miningPriceInfo);
			this.writeJsonToResponse(this.SUCCESS);
		}else{
			this.writeJsonToResponse("采价分类无效！");
		}
	}
	/**
	 * 更新采价价格
	 * @return
	 * @throws IOException 
	 */
	public String updateMiningPrice() throws IOException{
		if (null != miningPriceInfo) {
			if (null != miningPriceInfo.getTUserInfo() && null == miningPriceInfo.getTUserInfo().getId()) {
				miningPriceInfo.setTUserInfo(null);
			}
			miningService.updateMiningPrice(miningPriceInfo);
		}
		this.writeJsonToResponse(this.SUCCESS);
		return null;
	}
	
	/**
	 * 执行导入采价
	 * @return
	 */
	public String impMinings() {
		Long atFileMaxSize = Long.valueOf(Propertiesconfiguration.getStringProperty("AT_FILE_MAX_SIZE")); 
		//文件类型
		if(null == this.get_file() || StringUtils.isBlank(this.get_fileFileName())){
			tmpFlag = 1;
		}else{
			String suffix = getSuffixName(this.get_fileFileName());
			if (StringUtils.isBlank(suffix)) {
				tmpFlag = 2;
			}else if(!"xls|xlsx".toUpperCase().contains(suffix.toUpperCase())){
				tmpFlag = 2;
			}
			//验证附件大小atFileMaxSize
			if (tmpFlag!=0 && this.get_file().length() > atFileMaxSize) {
				tmpFlag = 3;
			}
		}
		if (tmpFlag == -1) {
			try {
				String result = this.miningService.impMinings(this.get_file(), this.get_fileFileName());
				tmpFlag = 0;
				if (StringUtils.isNotBlank(result)) {
					this.setSuccMsg(result);
					tmpFlag = 99;
				}
			} catch (FileNotFoundException e) {
				tmpFlag = 99;
				this.log.info(e,  e);
			} catch (IOException e) {
				tmpFlag = 99;
				this.setSuccMsg("导入失败！");
				this.log.info(e,  e);
			} catch (NoSuchSheetException e) {
				tmpFlag = 99;
				this.setSuccMsg(e.getMessage());
				this.log.info(e,  e);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 获取文件后缀 
	 */
	private String getSuffixName(String fileName){
		int i = fileName.lastIndexOf(".");
		if(i == -1){
			return "";
		} else {
			return fileName.substring(i + 1);
		}
	}
	
	/**
	 * 下载导入采价模板
	 * @return
	 */
	public void dwnMiningsTmp() {
		String rootPath = "resources/templates";
		String basePath = null;
		String downLoadPath = null;

		// 获取当前类路径 start
		Class cls = MiningAction.class;
		ClassLoader loader = cls.getClassLoader();
		java.net.URL url = loader.getResource(rootPath);
		basePath = url.getPath();
		if (basePath.endsWith("/")) {
		} else {
			basePath += "/";
		}
		// 获取当前类路径 end
		
		downLoadPath = "minprc_imp.xls";
		String fileName = "采价模板.xls";
		if (downLoadPath != null) {
			File downLoadFile = new File(basePath + "/" + downLoadPath);
			FileInputStream downLoadInputStream;
			try {
				downLoadInputStream = new FileInputStream(downLoadFile);
				//下载excel文件
				super.doDownload(fileName, downLoadInputStream, FileType.EXCEL_TYPE);
			} catch (FileNotFoundException e) {
			}
		}
	}
	
	/**
	 * <p>Discription:default</p>
	 */
	public String execute(){
		return SUCCESS;
	}
	
	public MiningService getMiningService() {
		return miningService;
	}
	public void setMiningService(MiningService miningService) {
		this.miningService = miningService;
	}
	public TMiningPriceSort getMiningPriceSort() {
		return miningPriceSort;
	}
	public void setMiningPriceSort(TMiningPriceSort miningPriceSort) {
		this.miningPriceSort = miningPriceSort;
	}
	public List<TMiningPriceInfo> getList() {
		return list;
	}
	public void setList(List<TMiningPriceInfo> list) {
		this.list = list;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TMiningPriceInfo getMiningPriceInfo() {
		return miningPriceInfo;
	}
	public void setMiningPriceInfo(TMiningPriceInfo miningPriceInfo) {
		this.miningPriceInfo = miningPriceInfo;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	public int getTmpFlag() {
		return tmpFlag;
	}
	
	public void setTmpFlag(int tmpFlag) {
		this.tmpFlag = tmpFlag;
	}
	
	
}
