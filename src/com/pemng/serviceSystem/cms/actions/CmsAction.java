package com.pemng.serviceSystem.cms.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.util.CollectionUtils;

import com.pemng.common.util.DateUtil;
import com.pemng.common.util.StringUtil;
import com.pemng.common.util.excelparser.SimpleWriter;
import com.pemng.serviceSystem.base.actions.BaseDownloadAction;
import com.pemng.serviceSystem.base.util.FilePathCfgUtil;
import com.pemng.serviceSystem.base.util.JsonUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.base.util.Propertiesconfiguration;
import com.pemng.serviceSystem.base.util.WSUtils;
import com.pemng.serviceSystem.cms.dto.CmsDto;
import com.pemng.serviceSystem.cms.services.CmsService;
import com.pemng.serviceSystem.common.DateFormatterZh;
import com.pemng.serviceSystem.common.Result;
import com.pemng.serviceSystem.common.WebUser;
import com.pemng.serviceSystem.pojo.TAprislArtclsInfo;
import com.pemng.serviceSystem.pojo.TAttachment;
import com.pemng.serviceSystem.pojo.TBasicDataCnt;
import com.pemng.serviceSystem.pojo.TCommission;
import com.pemng.serviceSystem.pojo.TRecUsr;
import com.pemng.serviceSystem.pojo.TUnitsInfo;
import com.pemng.serviceSystem.pojo.TUnitsSort;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.units.services.UnitsManagerService;

/**
 * @author eide
 *	委托书管理模块,包含以下两部分：
 *	1.价格鉴定/认定管理
 *	2.复核裁定管理
 */
public class CmsAction extends BaseDownloadAction {

	private static final long serialVersionUID = -8296299265001744184L;
	private CmsDto cmsDto = new CmsDto();
	private CmsDto saveDto = new CmsDto();
	private CmsService cmsService;
	private UnitsManagerService unitsManagerService;
	private Pager pager = new Pager();
	private WebUser webUser;
	private String selectComponetValue;	//下拉框json格式值
	private List<TCommission> cmList;
	private TAttachment attachment;		//附件
	private List<TAttachment> atList;	//附件列表
	private TAprislArtclsInfo aprislDto; //价格鉴定
	private List<TAprislArtclsInfo> aprislArtclsList; //委托明细列表
	private TRecUsr recUsr;
	private String cmsFlag;	//1:鉴定认定委托书创建或修改,2：复核裁定委托书创建或修改,3:原鉴定结论及信息,4:待受理委托书修改
	private String padCde;
	private List<TAttachment> atList1 = new ArrayList<TAttachment>();
	private List<TAttachment> atList2 = new ArrayList<TAttachment>();
	private List<TAttachment> atList3 = new ArrayList<TAttachment>();
	private int atFlag = 0;	//附件错误代码
	private String atId;	//附件ID
	private String hisCmsFlag;	//历史委托书标志
	private Map<String, Object> dataMap;
	private List<TUserInfo> dataList;

	/**
	 * @return
	 * 价格鉴定/认定管理列表
	 */
	public String aprislCertList() {
		selectComponetValue = unitsManagerService.getUnitSelectValue();
		if(StringUtils.isBlank(selectComponetValue)){
			selectComponetValue = "{}";
		}
		if (null != cmsDto) {
			cmsDto.setCmsType(1);
			cmsDto.setFlag(1);
			cmList = cmsService.getCmsList(pager, cmsDto);
			dataMap = cmsService.getCmsListCount(cmsDto);
			DecimalFormat df_4 = new DecimalFormat("###.####");
			dataMap.put("mnyCnt", df_4.format(MapUtils.getObject(dataMap, "mnyCnt")));//取消科学计数法
		}
		return SUCCESS;
	}
	
	/**
	 * @return
	 * 复核裁定管理列表
	 */
	public String reviewRuleList(){
		selectComponetValue = unitsManagerService.getUnitSelectValue();
		if(StringUtils.isBlank(selectComponetValue)){
			selectComponetValue = "{}";
		}
		if (null != cmsDto) {
			cmsDto.setCmsType(3);
			cmsDto.setFlag(2);
			cmList = cmsService.getCmsList(pager, cmsDto);
			dataMap = cmsService.getCmsListCount(cmsDto);
			DecimalFormat df_4 = new DecimalFormat("###.####");
			dataMap.put("mnyCnt", df_4.format(MapUtils.getObject(dataMap, "mnyCnt")));//取消科学计数法
		}
		return SUCCESS;
	}
	
	/**
	 * @return
	 * 审核管理列表
	 */
	public String auditList(){
		selectComponetValue = unitsManagerService.getUnitSelectValue();
		if(StringUtils.isBlank(selectComponetValue)){
			selectComponetValue = "{}";
		}
		if (null != cmsDto) {
			cmsDto.setCmsType(0);
			cmsDto.setFlag(3);
			cmList = cmsService.getCmsList(pager, cmsDto);
			dataMap = cmsService.getCmsListCount(cmsDto);
			DecimalFormat df_4 = new DecimalFormat("###.####");
			dataMap.put("mnyCnt", df_4.format(MapUtils.getObject(dataMap, "mnyCnt")));//取消科学计数法
		}
		return SUCCESS;
	}
	
	/**
	 * @return
	 * 历史委托书查询列表
	 */
	public String hisCmsList(){
		selectComponetValue = unitsManagerService.getUnitSelectValue();
		if(StringUtils.isBlank(selectComponetValue)){
			selectComponetValue = "{}";
		}
		if (null != cmsDto) {
			cmsDto.setCmsType(0);
			cmsDto.setFlag(4);
			cmList = cmsService.getCmsList(pager, cmsDto);
			dataMap = cmsService.getCmsListCount(cmsDto);
			DecimalFormat df_4 = new DecimalFormat("###.####");
			dataMap.put("mnyCnt", df_4.format(MapUtils.getObject(dataMap, "mnyCnt")));//取消科学计数法
		}
		return SUCCESS;
	}

	/**
	 * 跳转到委托书创建页面
	 * @return
	 * @throws Exception
	 */
	public String toCmsAdd() throws Exception{
		//设置委托书类型的默认值
		saveDto.setCmsTp("1");
		//设置有无实物的默认值
		saveDto.setIsEnty("1");
		saveDto.setPrcAprislDocmsNo("刑鉴");
		//设置价格鉴定依据的默认值
		/*saveDto.setPrcAprislCln("1、《中华人民共和国价格法》；" +"\n"+
				"2、《北京市涉案财产价格鉴定管理办法》北京市人民政府令第46号；" +"\n"+
				"3、《北京市人民政府关于修改五十九项规章部分条款的决定》北京市人民政府令第200号第四十七款；" +"\n"+
				"4、《价格鉴定行为规范（2010年版）》（发改价证办[2010]103号）国家发展和改革委员会价格认证中心；" +"\n"+
				"5、《北京市涉案财产价格鉴定管理办法》实施细则（京发改〖2009〗368号）；" +"\n"+
				"6、委托方出具的《涉案财产价格鉴定委托书》及价格鉴定标的相关材料；" +"\n"+
				"7、价格鉴定人员对鉴定标的有关资料的核实及市场调查所取得的资料。");
		//设置价格鉴定过程的默认值
		String str="我中心接受委托后，价格鉴定人员于 "+CM.formatDate("yyyy年MM月dd日")+" 对鉴定标的进行了实物勘验。 " +"\n"+
				"依据市场法的替代原则，参照同种或类似物品市场价格进行修正并计算标的价格，确定价格结论。";
		saveDto.setPrcAprislPrc(str);*/
		selectComponetValue = unitsManagerService.getUnitSelectValueAll();
		if(StringUtils.isBlank(selectComponetValue)){
			selectComponetValue = "{}";
		}
		return SUCCESS;
	}

	/**
	 * 保存委托书(价格鉴定委托书,价格认定委托书,复核裁定委托书,原鉴定结论书)
	 * @return
	 * @throws Exception
	 */
	public String saveCms() throws Exception{
		if (null != saveDto) {
			TCommission cms = cmsService.saveCms(saveDto,recUsr);
			padCde = cms.getPadCode();
			saveDto = (CmsDto) PojoUtils.convert2Dto(CmsDto.class, cms);
			String repeatCms = cmsService.getRepeatArtcls(saveDto.getId());
			if (StringUtils.isNotBlank(repeatCms)) {
				this.setSuccMsg(repeatCms);
			}else{
				this.setSuccMsg("1");
			}
		}
		if (cmsFlag.equals("21")) {
			StringBuilder json = new StringBuilder();
			if (saveDto != null) {
				json.append("{");
				json.append("\"id\":\"").append(saveDto.getId()).append("\"");
				json.append("}");
				writeJsonData(json.toString());
			}
			return null;
		}
		return SUCCESS;
	}
	
	/**
	 * 跳转到委托书修改页面
	 * @return
	 * @throws Exception
	 */
	public String toUpdateCms() throws Exception{
		selectComponetValue = unitsManagerService.getUnitSelectValueAll();
		if(StringUtils.isBlank(selectComponetValue)){
			selectComponetValue = "{}";
		}
		if (null != saveDto && null !=saveDto.getId()) {
			TCommission cms = this.cmsService.getCms(saveDto.getId());
			saveDto = (CmsDto) PojoUtils.convert2Dto(CmsDto.class, cms);
			//勘验人员
			Set<TRecUsr> rus = saveDto.getTRecUsrs();
			StringBuilder userNames = new StringBuilder("");
			StringBuilder userIds = new StringBuilder("");
			for (TRecUsr ru : rus) {
				userIds.append(",").append(ru.getTUserInfo().getId());
				userNames.append(",").append(ru.getTUserInfo().getName());
			}
			saveDto.setUserNames(userNames.toString().replaceFirst(",", ""));
			saveDto.setRecUsrIds(userIds.toString().replaceFirst(",", ""));
			//单位关联数据
			TUnitsInfo ti = saveDto.getTunitsInfo();
			TUnitsSort ts2 = null;
			TUnitsSort ts1 = null;
			try {
				if (null != ti && null != ti.getId()) {
					saveDto.setUnitId3(ti.getId());
					ts2 = ti.getTUnitsSort();
				}
				if (null != ts2 && null != ts2.getId()) {
					saveDto.setUnitId2(ts2.getId());
					ts1 = ts2.getTUnitsSort();
				}
				if (null != ts1 && null != ts1.getId()) {
					saveDto.setUnitId1(ts1.getId());
				}
			} catch (Exception e) {
				log.error(e, e);
			}
			/**
				1.剩余天数判断,若有归档日期，则按受理到归档日期计算，否则按受理到当前日期计算;
				2.剩余天数默认值 7天(受理之后到归档 超过7天)
			*/
			int overDueNum = 0;
			TBasicDataCnt bdc = WSUtils.getBasicData("002", "001");;
			int overDueNumSet = 7;
			if (null != bdc.getValue() && bdc.getValue().matches("\\d+")) {
				overDueNumSet = Integer.valueOf(bdc.getValue());
			}
			if (null != saveDto.getAcptDt() && null != saveDto.getAchivDt()) {
				overDueNum = DateUtil.getRealDaysBetween(DateUtil.getDate("yyyy-MM-dd", saveDto.getAcptDt()), DateUtil.getDate("yyyy-MM-dd", saveDto.getAchivDt()));
			}else if(null != saveDto.getAcptDt() && saveDto.getAchiv().equals("0")){
				overDueNum = DateUtil.getRealDaysBetween(DateUtil.getDate("yyyy-MM-dd", saveDto.getAcptDt()), DateUtil.getCurrentDate("yyyy-MM-dd"));
			}
			saveDto.setOverDueNum(overDueNumSet+1 - overDueNum);
			//涉嫌罪名判断
			if (StringUtils.isNotBlank(saveDto.getSuspktOfens()) && saveDto.getSuspktOfens().matches("99:.*")) {
				saveDto.setOtherCase(true);
				saveDto.setSuspktOfens2(saveDto.getSuspktOfens().split(":")[1]);
			}else{
				saveDto.setSuspktOfens1(saveDto.getSuspktOfens());
			}
			//
			if (null != saveDto.getCmsTp()) {
				if (saveDto.getCmsTp().matches("1|2")) {
					this.cmsFlag = "1";
				} else if (saveDto.getCmsTp().matches("3")) {
					this.cmsFlag = "2";
				}
			}
		}
		return "upd";
	}
	
	
	/**
	 * 跳转到委托书修改页面
	 * @return
	 * @throws Exception
	 */
	public String toAduitCms() throws Exception{
		selectComponetValue = unitsManagerService.getUnitSelectValueAll();
		if(StringUtils.isBlank(selectComponetValue)){
			selectComponetValue = "{}";
		}
		//查询复核人员
		dataList = this.cmsService.getUserRetrialUsers();
		if (null != saveDto && null !=saveDto.getId()) {
			TCommission cms = this.cmsService.getCms(saveDto.getId());
			saveDto = (CmsDto) PojoUtils.convert2Dto(CmsDto.class, cms);
			//勘验人员
			Set<TRecUsr> rus = saveDto.getTRecUsrs();
			StringBuilder userNames = new StringBuilder("");
			StringBuilder userIds = new StringBuilder("");
			for (TRecUsr ru : rus) {
				userIds.append(",").append(ru.getTUserInfo().getId());
				userNames.append(",").append(ru.getTUserInfo().getName());
			}
			saveDto.setUserNames(userNames.toString().replaceFirst(",", ""));
			saveDto.setRecUsrIds(userIds.toString().replaceFirst(",", ""));
			//单位关联数据
			TUnitsInfo ti = saveDto.getTunitsInfo();
			TUnitsSort ts2 = null;
			TUnitsSort ts1 = null;
			try {
				if (null != ti && null != ti.getId()) {
					saveDto.setUnitId3(ti.getId());
					ts2 = ti.getTUnitsSort();
				}
				if (null != ts2 && null != ts2.getId()) {
					saveDto.setUnitId2(ts2.getId());
					ts1 = ts2.getTUnitsSort();
				}
				if (null != ts1 && null != ts1.getId()) {
					saveDto.setUnitId1(ts1.getId());
				}
			} catch (Exception e) {
				log.error(e, e);
			}
			/**
				1.剩余天数判断,若有归档日期，则按受理到归档日期计算，否则按受理到当前日期计算;
				2.剩余天数默认值 7天(受理之后到归档 超过7天)
			*/
			int overDueNum = 0;
			TBasicDataCnt bdc = WSUtils.getBasicData("002", "001");;
			int overDueNumSet = 7;
			if (null != bdc.getValue() && bdc.getValue().matches("\\d+")) {
				overDueNumSet = Integer.valueOf(bdc.getValue());
			}
			if (null != saveDto.getAcptDt() && null != saveDto.getAchivDt()) {
				overDueNum = DateUtil.getRealDaysBetween(DateUtil.getDate("yyyy-MM-dd", saveDto.getAcptDt()), DateUtil.getDate("yyyy-MM-dd", saveDto.getAchivDt()));
			}else if(null != saveDto.getAcptDt() && saveDto.getAchiv().equals("0")){
				overDueNum = DateUtil.getRealDaysBetween(DateUtil.getDate("yyyy-MM-dd", saveDto.getAcptDt()), DateUtil.getCurrentDate("yyyy-MM-dd"));
			}
			saveDto.setOverDueNum(overDueNumSet+1 - overDueNum);
			//涉嫌罪名判断
			if (StringUtils.isNotBlank(saveDto.getSuspktOfens()) && saveDto.getSuspktOfens().matches("99:.*")) {
				saveDto.setOtherCase(true);
				saveDto.setSuspktOfens2(saveDto.getSuspktOfens().split(":")[1]);
			}else{
				saveDto.setSuspktOfens1(saveDto.getSuspktOfens());
			}
		}
		return "aduit";
	}
	
	/**
	 * 删除委托书
	 * @return
	 * @throws Exception
	 */
	public String delCms() throws Exception{
		if(cmsDto != null && cmsDto.getIds() != null) {
			cmsService.deleteCms(cmsDto);
		}
		if (cmsFlag.equals("1")) {//鉴定列表
			return "acList";
		}else if(cmsFlag.equals("2")){//复核列表
			return "rrList";
		}
		return "acList";
	}
	
	/**	
	 * 委托明细
	 * @return
	 * @throws Exception
	 */
	public String getAprislList(){
		//获取价格鉴定列表
		if (null !=  aprislDto && null != aprislDto.getTmpCmsId() && aprislDto.getTmpCmsId()>0) {
			aprislArtclsList = cmsService.getAprislList(aprislDto, 2);
		}else if(null != aprislDto && null != aprislDto.getTCommission() && aprislDto.getTCommission().getId()>0){
			aprislArtclsList = cmsService.getAprislList(aprislDto);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除价格鉴定
	 * @return
	 */
	public String delAprisl(){
		if (null != aprislDto && null != aprislDto.getId()) {
			cmsService.deleteAprisl(aprislDto.getId());
		}
		return SUCCESS;
	}
		
	/**
	 * 相关附件
	 * @return
	 * @throws Exception
	 */
	public String relateAtList(){
		//获取文件列表
		if (null != attachment && null != attachment.getTmpCmsId() ) {
			atList = cmsService.getAttachmentList(attachment, 2);
		}else if(null != attachment){
			atList = cmsService.getAttachmentList(attachment);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询技术报告列表
	 * @return
	 * @throws Exception
	 */
	public String techReportList() throws Exception {
		//获取文件列表
		if(null != attachment && attachment.getTCommission()!= null && attachment.getTCommission().getId() != null){
			attachment.setTCommission(cmsService.getCms(attachment.getTCommission().getId()));
			atList = cmsService.getAttachmentList(attachment);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询实物勘验文件列表
	 * @return
	 * @throws Exception
	 */
	public String artclsRecList() throws Exception {
		//获取文件列表
		if(null != attachment){
			atList = cmsService.getAttachmentList(attachment);
		}
		return SUCCESS;
	}
	
	/**
	 * 选择历史案件列表
	 * @return
	 */
	public String historyCase() {
		if(cmsDto != null)
		{
			cmsDto.setCmsType(0);
			cmsDto.setFlag(4);
			cmList = cmsService.getCmsList(pager, cmsDto);
		}
		return SUCCESS;
	}
	

	/**
	 * 文件上传
	 * @return
	 */
	public String uploadFiles() throws Exception{
		atFlag = 0;
		String atFileSuffixs = Propertiesconfiguration.getStringProperty("AT_FILE_SUFFIXS").trim();
		Long atFileMaxSize = Long.valueOf(Propertiesconfiguration.getStringProperty("AT_FILE_MAX_SIZE")); 
		//文件类型
		if(null == this.get_file() || StringUtils.isBlank(this.get_fileFileName())){
			atFlag = 1;
		}else{
			String suffix = getSuffixName(this.get_fileFileName());
			if (StringUtils.isBlank(suffix)) {
				atFlag = 2;
			}else if(!atFileSuffixs.toUpperCase().contains(suffix.toUpperCase())){
				atFlag = 2;
			}
			attachment.setFileTp(suffix.toUpperCase());	//附件类型
		}
		//验证附件大小atFileMaxSize
		if (atFlag!=0 && this.get_file().length() > atFileMaxSize) {
			atFlag = 3;
		}else{
			//文件大小
			attachment.setFileSize(this.get_file().length());
		}
		if (atFlag == 0) {
			int result = cmsService.uploadFiles(this.get_file(), this.get_fileFileName(), attachment);
			if (result == 0) {
				atFlag = 88;
			}
		}
		//保存文件
		if (attachment.getAtTp().equals("1")) {
			return "ratList";
		}else if(attachment.getAtTp().equals("2")){
			return "trList";
		}else if(attachment.getAtTp().equals("3")){
			return "arsRecList";
		}
		return "ratList";
	}
	
	/**
	 * 删除附件
	 * @return
	 */
	public String delAt(){
		if (null != attachment && null != attachment.getId()) {
			cmsService.deleteAttachment(attachment.getId());
		}
		if (attachment.getAtTp().equals("1")) {
			return "ratList";
		}else if(attachment.getAtTp().equals("2")){
			return "trList";
		}else if(attachment.getAtTp().equals("3")){
			return "arsRecList";
		}
		return "ratList";
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
	 * 受理/审核委托书
	 * @return
	 */
	public void auditCms(){
		int status = 0;
		if (null != saveDto && saveDto.getId() > 0) {
			status = cmsService.auditCms(saveDto);
		}
		try {
			if (status == 1) {
				this.writeJsonToResponse(JsonUtil.object2json((new Result("1"))));
			}else{
				this.writeJsonToResponse(JsonUtil.object2json((new Result("0"))));
			}
		} catch (IOException e) {
			this.log.error(e, e);
		}
		/*if (cmsFlag.equals("1")) {//鉴定列表
			return "acList";
		}else if(cmsFlag.equals("2")){//复核列表
			return "rrList";
		}else if(cmsFlag.equals("3")){//审核列表
			return "adList";
		}
		return "adList";*/
	}
	/**
	 * 预览委托书
	 * @return
	 */
	public String viewCms(){
		selectComponetValue = unitsManagerService.getUnitSelectValueAll();
		if(StringUtils.isBlank(selectComponetValue)){
			selectComponetValue = "{}";
		}
		if (null != saveDto && null !=saveDto.getId()) {
			TCommission cms = this.cmsService.getCms(saveDto.getId());
			saveDto = (CmsDto) PojoUtils.convert2Dto(CmsDto.class, cms);
			//勘验人员
			Set<TRecUsr> rus = saveDto.getTRecUsrs();
			StringBuilder userNames = new StringBuilder("");
			StringBuilder userIds = new StringBuilder("");
			for (TRecUsr ru : rus) {
				userIds.append(",").append(ru.getTUserInfo().getId());
				userNames.append(",").append(ru.getTUserInfo().getName());
			}
			saveDto.setUserNames(userNames.toString().replaceFirst(",", ""));
			saveDto.setRecUsrIds(userIds.toString().replaceFirst(",", ""));
			//单位关联数据
			try {
				TUnitsInfo ti = saveDto.getTunitsInfo();
				TUnitsSort ts2 = null;
				TUnitsSort ts1 = null;
				if (null != ti && null != ti.getId()) {
					saveDto.setUnitId3(ti.getId());
					ts2 = ti.getTUnitsSort();
				}
				if (null != ts2 && null != ts2.getId()) {
					saveDto.setUnitId2(ts2.getId());
					ts1 = ts2.getTUnitsSort();
				}
				if (null != ts1 && null != ts1.getId()) {
					saveDto.setUnitId1(ts1.getId());
				}
			} catch (Exception e) {
				this.log.info("不存在该单位信息");
				this.log.error(e, e);
			}
			List<TAttachment>  TAttachmentList = new ArrayList<TAttachment>(saveDto.getTAttachments());
			for(int i=0;i<TAttachmentList.size();i++){
				if(TAttachmentList.get(i).getAtTp().equals("1")){
					atList1.add(TAttachmentList.get(i));
				}else if(TAttachmentList.get(i).getAtTp().equals("2")){
					atList2.add(TAttachmentList.get(i));
				}else{
					atList3.add(TAttachmentList.get(i));
				}
			}
			/**
				1.剩余天数判断,若有归档日期，则按受理到归档日期计算，否则按受理到当前日期计算;
				2.剩余天数默认值 7天(受理之后到归档 超过7天)
			*/
			int overDueNum = 0;
			TBasicDataCnt bdc = WSUtils.getBasicData("002", "001");;
			int overDueNumSet = 7;
			if (null != bdc.getValue() && bdc.getValue().matches("\\d+")) {
				overDueNumSet = Integer.valueOf(bdc.getValue());
			}
			if (null != saveDto.getAcptDt() && null != saveDto.getAchivDt()) {
				overDueNum = DateUtil.getRealDaysBetween(DateUtil.getDate("yyyy-MM-dd", saveDto.getAcptDt()), DateUtil.getDate("yyyy-MM-dd", saveDto.getAchivDt()));
			}else if(null != saveDto.getAcptDt() && saveDto.getAchiv().equals("0")){
				overDueNum = DateUtil.getRealDaysBetween(DateUtil.getDate("yyyy-MM-dd", saveDto.getAcptDt()), DateUtil.getCurrentDate("yyyy-MM-dd"));
			}
			saveDto.setOverDueNum(overDueNumSet+1 - overDueNum);
			
			if(StringUtils.isNotEmpty(saveDto.getSuspktOfens())){
				String str = saveDto.getSuspktOfens()+",";
				str = StringUtil.changValue(str,"1,","盗窃罪,");
				str = StringUtil.changValue(str,"2,","抢劫罪,");
				str = StringUtil.changValue(str,"3,","故意毁坏财物,");
				str = StringUtil.changValue(str,"4,","破坏生产经营罪,");
				str = StringUtil.changValue(str,"5,","受贿罪,");
				str = StringUtil.changValue(str,"6,","扒窃罪,");
				str = StringUtil.changValue(str,"7,","寻衅滋事罪,");
				str = StringUtil.changValue(str,"8,","诈骗罪,");
				str = StringUtil.changValue(str,"9,","生产销售假冒伪劣产品罪,");
				str = StringUtil.changValue(str,"99:","");
				saveDto.setSuspktOfens(str.substring(0, str.length()-1));
			}
		}
		return SUCCESS;
	}
	
	//批量归档委托书
	public void achiveCms(){
		int status = 0;
		if(cmsDto != null && cmsDto.getIds() != null) {
			status = cmsService.achiveCms(cmsDto);
		}
		try {
			if (status == 1) {
				this.writeJsonToResponse(JsonUtil.object2json((new Result("1"))));
			}else{
				this.writeJsonToResponse(JsonUtil.object2json((new Result("0"))));
			}
		} catch (IOException e) {
			this.log.error(e, e);
		}
	}
	
	/**
	 * Function Name               downLoadAttachment                                   
	 * @description                //下载附件 
	 */
	public String downloadAttachment() throws Exception{
		String filePath = null;
		if (null != atId && atId.matches("\\d+")) {
			//上传文件路径
			String articleRootPath= FilePathCfgUtil.getCmsdir();
			attachment = cmsService.downloadAttachment(Long.valueOf(atId));
			//上传文件真实路径
			if (null != attachment) {
				filePath = articleRootPath + "/" + attachment.getSaveFileNm();		
			}
		}
		if (null != attachment && filePath != null && attachment.getFileNm() != null) {
			File file = new File(filePath) ;
			if (file.exists()) {
				doDownload(attachment.getFileNm().trim(), new FileInputStream(file), FileType.OTHER_TYPE);
				return null;
			}else if (StringUtils.isNotBlank(hisCmsFlag) && hisCmsFlag.equals("H")) {
				atFlag = 99;
				return "hisCmsView";
			}else{
				atFlag = 99;
			}
		}
		if (attachment.getAtTp().equals("1")) {
			return "ratList";
		}else if(attachment.getAtTp().equals("2")){
			return "trList";
		}else if(attachment.getAtTp().equals("3")){
			return "arsRecList";
		}
		return "ratList";
	} 
	
	/**
	 * Function Name               downLoadCms                                   
	 * @return                     //函数返回值的说明
	 * @description                //委托书查询结果下载
	 */
	public void downLoadCms() throws Exception {
		SimpleWriter simpleWriter = new SimpleWriter();
		if (null != cmsDto) {
			cmsDto.setDownloadFlag(true);
			pager.setStartRd(0);
			pager.setPageSize(0);
			cmList = cmsService.getCmsList(pager, cmsDto);
			dataMap = cmsService.getCmsListCount(cmsDto);
			DecimalFormat df_4 = new DecimalFormat("###.####");
			dataMap.put("mnyCnt", df_4.format(MapUtils.getObject(dataMap, "mnyCnt")));//取消科学计数法
		}
		//主承销商18列
		String[] headers = new String[] {"鉴定文号", "委托单位", "基准日", "委托标的", "鉴定金额", "委托人", "联系电话"};
		Object[][] datas = null;
		if(!CollectionUtils.isEmpty(cmList)){
			TCommission cms = null;
			datas = new Object[cmList.size() + 1][];
			Set<TAprislArtclsInfo> aais = null;
			String unitsinfoName = " ";//委托单位名称
			String artclsNm = " ";	//标的物
			for(int i = 0, len = (cmList.size() + 1); i < len; i++){
				if (i == (len -1)) {
					dataMap = cmsService.getCmsListCount(cmsDto);
					DecimalFormat df_4 = new DecimalFormat("###.####");
					dataMap.put("mnyCnt", df_4.format(MapUtils.getObject(dataMap, "mnyCnt")));//取消科学计数法
					datas[i] = new Object[]{"统计  数量:"+MapUtils.getIntValue(dataMap, "numCnt")+"   金额  :"+MapUtils.getDoubleValue(dataMap, "mnyCnt"), " ", " ", " ", " ", " ", " "};
				}else{
					//委托书信息
					cms = cmList.get(i);
					//价格鉴定物品列表
					aais = cms.getTAprislArtclsInfos();
					
					if (null != aais) {
						for (TAprislArtclsInfo aai : aais) {
							if (aais.size() == 1) {
								artclsNm = StringUtil.convertNull(aai.getQnty()) + aai.getUnit() +aai.getArtclNm();
							}else if (aais.size() > 1) {
								artclsNm = StringUtil.convertNull(aai.getQnty()) + aai.getUnit() +aai.getArtclNm() + "等物品";
								break;
							}
						}
					}
					if(null != cms){
						if (cms.getCmsTp().equals("3")) {	//复核裁定委托书单位
							unitsinfoName = cms.getCmsUnitNm();
						}else{								//非复核裁定委托书单位
							unitsinfoName = (null !=cms.getTunitsInfo() && null !=cms.getTunitsInfo().getShortName())?cms.getTunitsInfo().getShortName():" ";
						}
						datas[i] = new Object[]{null != cms.getPrcAprislDocmsNo()? cms.getPrcAprislDocmsNo():" ", unitsinfoName,
								null != cms.getPrcAprislBaseDt()?DateUtil.getDate("yyyy-MM-dd", cms.getPrcAprislBaseDt()):" ", artclsNm, 
								null != cms.getAprislMnySum()?cms.getAprislMnySum():"", null != cms.getCmsUsr() ?cms.getCmsUsr() :" ", 
								null != cms.getCmsUsrTel()?cms.getCmsUsrTel():" "};
					}
					artclsNm = " ";
				}
			}
		}
		if (cmsDto.getFlag() == 1) {
			simpleWriter.addSheet("鉴定认证委托书", headers, (Object[][]) datas);
		}else if(cmsDto.getFlag() == 2) {
			simpleWriter.addSheet("复核裁定委托书", headers, (Object[][]) datas);
		}else if(cmsDto.getFlag() == 3) {
			simpleWriter.addSheet("审核委托书", headers, (Object[][]) datas);
		}else if(cmsDto.getFlag() == 4) {
			simpleWriter.addSheet("历史委托书", headers, (Object[][]) datas);
		}
		Map<Short, Short> widthMap = new HashMap<Short, Short>(20);
		int ii = 0;
		widthMap.put((short) ii++, (short) 8000);
		widthMap.put((short) ii++, (short) 5800);
		widthMap.put((short) ii++, (short) 4000);
		widthMap.put((short) ii++, (short) 8500);
		widthMap.put((short) ii++, (short) 3000);
		widthMap.put((short) ii++, (short) 7500);
		widthMap.put((short) ii++, (short) 4500);
		widthMap.put((short) ii++, (short) 4500);
		
		//设置列宽
		simpleWriter.setColumnWidth(0, widthMap);
		
		//下载
		try {
			this.getResponse().reset();
			if (cmsDto.getFlag() == 1) {
				this.getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String("鉴定认证委托书.xls".getBytes("GB2312"), "ISO8859-1"));
			}else if(cmsDto.getFlag() == 2) {
				this.getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String("复核裁定委托书.xls".getBytes("GB2312"), "ISO8859-1"));
			}else if(cmsDto.getFlag() == 3) {
				this.getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String("审核委托书.xls".getBytes("GB2312"), "ISO8859-1"));
			}else if(cmsDto.getFlag() == 4) {
				this.getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String("历史委托书.xls".getBytes("GB2312"), "ISO8859-1"));
			}
			this.getResponse().setContentType("application/octet-stream");
			ServletOutputStream outputStream = this.getResponse().getOutputStream();
			simpleWriter.writeExcel(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	/**
	 * 获取当年年份
	 * @return
	 */
	public String getCurrentYear(){
		return DateUtils.format(new Date(), "yyyy");
	}
	
	public void setAprislArtclsList(List<TAprislArtclsInfo> aprislArtclsList) {
		this.aprislArtclsList = aprislArtclsList;
	}

	public List<TAprislArtclsInfo> getAprislArtclsList() {
		return aprislArtclsList;
	}

	public CmsService getCmsService() {
		return cmsService;
	}
	public void setCmsService(CmsService cmsService) {
		this.cmsService = cmsService;
	}
	public WebUser getWebUser() {
		return webUser;
	}
	public void setWebUser(WebUser webUser) {
		this.webUser = webUser;
	}

	public String getSelectComponetValue() {
		return selectComponetValue;
	}

	public void setSelectComponetValue(String selectComponetValue) {
		this.selectComponetValue = selectComponetValue;
	}

	public UnitsManagerService getUnitsManagerService() {
		return unitsManagerService;
	}

	public void setUnitsManagerService(UnitsManagerService unitsManagerService) {
		this.unitsManagerService = unitsManagerService;
	}

	public CmsDto getCmsDto() {
		return cmsDto;
	}

	public void setCmsDto(CmsDto cmsDto) {
		this.cmsDto = cmsDto;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public List<TCommission> getCmList() {
		return cmList;
	}

	public CmsDto getSaveDto() {
		return saveDto;
	}

	public void setSaveDto(CmsDto saveDto) {
		this.saveDto = saveDto;
	}

	public TAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(TAttachment attachment) {
		this.attachment = attachment;
	}

	public List<TAttachment> getAtList() {
		return atList;
	}

	public void setAtList(List<TAttachment> atList) {
		this.atList = atList;
	}

	public void setCmList(List<TCommission> cmList) {
		this.cmList = cmList;
	}

	public TRecUsr getRecUsr() {
		return recUsr;
	}

	public void setRecUsr(TRecUsr recUsr) {
		this.recUsr = recUsr;
	}

	public String getCmsFlag() {
		return cmsFlag;
	}

	public void setCmsFlag(String cmsFlag) {
		this.cmsFlag = cmsFlag;
	}

	public TAprislArtclsInfo getAprislDto() {
		return aprislDto;
	}

	public void setAprislDto(TAprislArtclsInfo aprislDto) {
		this.aprislDto = aprislDto;
	}

	public String getPadCde() {
		return padCde;
	}

	public void setPadCde(String padCde) {
		this.padCde = padCde;
	}

	public List<TAttachment> getAtList1() {
		return atList1;
	}

	public void setAtList1(List<TAttachment> atList1) {
		this.atList1 = atList1;
	}

	public List<TAttachment> getAtList2() {
		return atList2;
	}

	public void setAtList2(List<TAttachment> atList2) {
		this.atList2 = atList2;
	}

	public List<TAttachment> getAtList3() {
		return atList3;
	}

	public void setAtList3(List<TAttachment> atList3) {
		this.atList3 = atList3;
	}

	public int getAtFlag() {
		return atFlag;
	}

	public void setAtFlag(int atFlag) {
		this.atFlag = atFlag;
	}

	public String getAtId() {
		return atId;
	}

	public void setAtId(String atId) {
		this.atId = atId;
	}

	public String getHisCmsFlag() {
		return hisCmsFlag;
	}

	public void setHisCmsFlag(String hisCmsFlag) {
		this.hisCmsFlag = hisCmsFlag;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public List<TUserInfo> getDataList() {
		return dataList;
	}

	public void setDataList(List<TUserInfo> dataList) {
		this.dataList = dataList;
	}


}
