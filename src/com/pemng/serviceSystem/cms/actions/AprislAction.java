package com.pemng.serviceSystem.cms.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;

import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.base.util.StringUtil;
import com.pemng.serviceSystem.cms.dto.AprislDto;
import com.pemng.serviceSystem.cms.services.AprislService;
import com.pemng.serviceSystem.mining.dto.MinDto;
import com.pemng.serviceSystem.mining.services.MiningService;
import com.pemng.serviceSystem.pojo.TAprislArtclsInfo;
import com.pemng.serviceSystem.pojo.TMiningPriceInfo;
import com.pemng.serviceSystem.pojo.TMiningPriceSort;
import com.pemng.serviceSystem.pojo.TUserInfo;

/**
 * 鉴定相关模块
 * 1.价格鉴定
 * 2.鉴定物品
 * @author eide
 */
public class AprislAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1865568280115530329L;
	private MiningService miningService;
	private AprislService aprislService;
	private AprislDto aprislDto = new AprislDto();
	private TMiningPriceInfo miningPriceInfo;
	private TMiningPriceSort miningPriceSort;
	private MinDto minDto = new MinDto();
	private List<TMiningPriceInfo> list;
	private List<TMiningPriceInfo> misList;
	private List<TAprislArtclsInfo> aprislList;
	private Long[] ids;
	private String mid;
	private Long id;
	private String sortName;
	private Date beginTime;
	private Date endTime;
	private Long userId;
	private String address;
	private String name;
	private Pager pager = new Pager();

	public String mining(){
		List<TUserInfo> list = miningService.getUserList();
		this.getRequest().setAttribute("userList", list);
		return SUCCESS;
	}
	
	public String preUpdateMin() {
		miningPriceInfo = miningService.getMiningPrice(minDto.getId());
		return SUCCESS;
	}
	
	/**
	 * 修改价格信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String modifyPrc() {
		if (null != minDto) {
			miningService.updateMiningPrice(minDto);
		}
		return SUCCESS;
	}


	/**
	 * 鉴定物品列表
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
			params.put("address", "%"+address+"%");
		}
		list = miningService.getMiningPriceList(pager, params);
		List<TUserInfo> list = miningService.getUserList();
		this.getRequest().setAttribute("userList", list);
		return SUCCESS;
	}
	
	/**
	 * 
	 * 跳转价格鉴定页面
	 * @return
	 */
	public String prcAprisl()
	{
		//通过id获得AprislDto
		if(aprislDto!=null && null!=(aprislDto.getId())){
			TAprislArtclsInfo tAprislArtclsInfo	= aprislService.getObjectById(aprislDto.getId());	
			aprislDto = (AprislDto) PojoUtils.convert2Dto(AprislDto.class, tAprislArtclsInfo);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加价格参考
	 * @return
	 */
	public String getPrcList() {
		List<Long> list1 = new ArrayList<Long>();
		List<Long> list2 = new ArrayList<Long>();
		if(StringUtils.isNotBlank(mid)) {
			Long[] ids_ = (Long[]) ConvertUtils.convert(mid.split(","), Long.class);
			list1 = Arrays.asList(ids_);		//隐藏域中的数据
		}
		TMiningPriceInfo tmpi = null;
		misList = new ArrayList<TMiningPriceInfo>();
		if (minDto != null && minDto.getIds() != null) {
			list2 = Arrays.asList(minDto.getIds());//表格中的数据
			HashSet<Long> hashSet = new HashSet<Long>();
			hashSet.addAll(list1);
			hashSet.addAll(list2);
			Iterator<Long> prcIds = hashSet.iterator();
			while (prcIds.hasNext()) {
				Long prcId = prcIds.next();
				//价格库数据
				tmpi = miningService.getMiningPrice(prcId);
				misList.add(tmpi);
			}
		}
		return SUCCESS;
	}

	/**
	 * 添加 鉴定物品
	 * @return
	 * @throws Exception 
	 */
	public void addAprisl() throws Exception {
		if(aprislDto!=null){
			JSONArray array = new JSONArray(this.parseStringParameter("reData"));
			aprislService.saveAprisl(aprislDto,array);
		}
	}
	
	/**
	 * 添加鉴定物品的页面
	 * @return
	 */
	public String toCreateAprisl() {
		return SUCCESS;
	}
	
	
	/**
	 * 通过ajax跳转不同的物品页面
	 * @return
	 */
	public String selectAprisl() {
		if(null != aprislDto && aprislDto.getId()!=null){
			TAprislArtclsInfo tAprislArtclsInfo	= aprislService.getObjectById(aprislDto.getId());
			aprislDto = (AprislDto) PojoUtils.convert2Dto(AprislDto.class, tAprislArtclsInfo);	
		}
		if(aprislDto!=null && !StringUtil.isEmpty(aprislDto.getArtclTp())){
			return "artcl"+aprislDto.getArtclTp();
		}else{
			aprislDto= new AprislDto();
			aprislDto.setArtclTp("002");
			return SUCCESS;
		}
	}
	
	/**
	 * 搜索同标的技术报告列表
	 * @return
	 */
	public String referTech() {
		if (null != aprislDto) {
			aprislList = aprislService.getAprislList(pager, aprislDto);
		}
		return SUCCESS;
	}
	/**
	 * 修改物品
	 * @return
	 * @throws Exception 
	 */
	public void updateAprisl() throws Exception {
		
		if(aprislDto!=null){
			JSONArray expArray = new JSONArray(this.parseStringParameter("expData"));
			aprislService.updateAprisl(aprislDto,expArray,ids);
		}
	}
	
	public MiningService getMiningService() {
		return miningService;
	}


	public void setMiningService(MiningService miningService) {
		this.miningService = miningService;
	}

	public List<TMiningPriceInfo> getList() {
		return list;
	}

	public void setList(List<TMiningPriceInfo> list) {
		this.list = list;
	}

	public AprislDto getAprislDto() {
		return aprislDto;
	}

	public void setAprislDto(AprislDto aprislDto) {
		this.aprislDto = aprislDto;
	}

	public AprislService getAprislService() {
		return aprislService;
	}

	public void setAprislService(AprislService aprislService) {
		this.aprislService = aprislService;
	}


	public TMiningPriceInfo getMiningPriceInfo() {
		return miningPriceInfo;
	}


	public void setMiningPriceInfo(TMiningPriceInfo miningPriceInfo) {
		this.miningPriceInfo = miningPriceInfo;
	}

	public MinDto getMinDto() {
		return minDto;
	}

	public void setMinDto(MinDto minDto) {
		this.minDto = minDto;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public List<TMiningPriceInfo> getMisList() {
		return misList;
	}

	public void setMisList(List<TMiningPriceInfo> misList) {
		this.misList = misList;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPage(Pager pager) {
		this.pager = pager;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public TMiningPriceSort getMiningPriceSort() {
		return miningPriceSort;
	}

	public void setMiningPriceSort(TMiningPriceSort miningPriceSort) {
		this.miningPriceSort = miningPriceSort;
	}

	public List<TAprislArtclsInfo> getAprislList() {
		return aprislList;
	}

	public void setAprislList(List<TAprislArtclsInfo> aprislList) {
		this.aprislList = aprislList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
