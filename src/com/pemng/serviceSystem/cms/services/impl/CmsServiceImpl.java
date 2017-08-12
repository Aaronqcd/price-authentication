package com.pemng.serviceSystem.cms.services.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.pemng.common.util.StringUtil;
import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.base.util.DateUtil;
import com.pemng.serviceSystem.base.util.FilePathCfgUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.base.util.Propertiesconfiguration;
import com.pemng.serviceSystem.cms.dao.CmsDao;
import com.pemng.serviceSystem.cms.dto.CmsDto;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.common.DateFormatterZh;
import com.pemng.serviceSystem.common.FileTool;
import com.pemng.serviceSystem.common.NumberFormatterZh;
import com.pemng.serviceSystem.common.PeUtility;
import com.pemng.serviceSystem.common.WebInfoMgmt;
import com.pemng.serviceSystem.common.WebUser;
import com.pemng.serviceSystem.common.office.CM;
import com.pemng.serviceSystem.common.office.DataToHtml;
import com.pemng.serviceSystem.common.office.OfficeConverter;
import com.pemng.serviceSystem.common.office.pojo.obinfo;
import com.pemng.serviceSystem.common.utils.PeFileUtil;
import com.pemng.serviceSystem.pojo.TApprovalInfo;
import com.pemng.serviceSystem.pojo.TAprislArtclsInfo;
import com.pemng.serviceSystem.pojo.TAttachment;
import com.pemng.serviceSystem.pojo.TCmsArtclsAccesors;
import com.pemng.serviceSystem.pojo.TCommission;
import com.pemng.serviceSystem.pojo.TRecUsr;
import com.pemng.serviceSystem.pojo.TUserInfo;

public class CmsServiceImpl extends BaseServiceImpl implements
		com.pemng.serviceSystem.cms.services.CmsService {

	public CmsDao cmsDao;

	@Override
	public void deleteCms(CmsDto cmsDto) {
		List<TCommission> cmsList = new ArrayList<TCommission>();
		TCommission tcommission = new TCommission();
		for(long id:cmsDto.getIds())
		{
			tcommission = getCms(id);
			tcommission.setMarkForDel(Constants.MARK_FOR_DELETE_YES);
			cmsList.add(tcommission);
		}
		cmsDao.saveObjectList(cmsList);
	}

	@Override
	public List<TCommission> getCmsList(Pager page, CmsDto cmsDto) {
		StringBuffer hql = new StringBuffer("select distinct cms from TCommission cms left join fetch cms.tunitsInfo ui left join fetch cms.acptUsrId aui ");
		//标的物名称
		if (cmsDto.isDownloadFlag() ||  StringUtils.isNotBlank(cmsDto.getAprislArtclsNm())) {
			hql.append("left join fetch cms.TAprislArtclsInfos aai "); 
		}
		hql.append("where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		//委托书类型
		if (cmsDto.getCmsType() == 1) {	//鉴定,认定
			//委托书类别
			if (StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("1")) {
				hql.append(" and cms.cmsTp = 1 ");
			}else if(StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("2")){
				hql.append(" and cms.cmsTp = 2 ");
			}else{
				hql.append(" and (cms.cmsTp = 1 or cms.cmsTp = 2 ) ");
			}
		}else if (cmsDto.getCmsType() == 3) {//复核
			hql.append(" and cms.cmsTp = 3 ");
		}else{
			//委托书类别
			if (StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("1")) {
				hql.append(" and cms.cmsTp = 1 ");
			}else if(StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("2")){
				hql.append(" and cms.cmsTp = 2 ");
			}else if(StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("3")){
				hql.append(" and cms.cmsTp = 3 ");
			}
		}
		//委托内容
		if (StringUtils.isNotBlank(cmsDto.getCmsCnt())) {
			params.put("cmsCnt", "%" + cmsDto.getCmsCnt().trim() + "%");
			hql.append(" and cms.cmsCnt like :cmsCnt ");
		}
		//委托人:
		if (StringUtils.isNotBlank(cmsDto.getCmsUsr())) {
			params.put("cmsUsr", "%" + cmsDto.getCmsUsr().trim() + "%");
			hql.append(" and cms.cmsUsr like :cmsUsr ");
		}
		//状态:
		if (StringUtils.isNotBlank(cmsDto.getSt()) && !cmsDto.getSt().equals("-1")) {
			if (cmsDto.getSt().equals("99")) {//待归档
				params.put("achiv", "1");
				hql.append(" and cms.achiv = :achiv "); 
			}else{
				params.put("st", cmsDto.getSt().trim());
				hql.append(" and cms.st = :st ").append(" and cms.achiv = '0' "); 
			}
		}else {
			if (cmsDto.getFlag() ==1) {//鉴定、认定
				hql.append(" and (cms.st = '01' or cms.st = '02'  or cms.st = '03' or cms.st = '04' or cms.st = '05' or cms.st = '08' or cms.st = '10' or cms.st = '12' or cms.st = '13' or cms.st = '14') "); 
			}else if (cmsDto.getFlag() ==2) {//复核裁定
				hql.append(" and (cms.st = '01' or cms.st = '02'  or cms.st = '03' or cms.st = '04' or cms.st = '05' or cms.st = '08' or cms.st = '10' or cms.st = '12' or cms.st = '13' or cms.st = '14') "); 
			}else if (cmsDto.getFlag() ==3) {//审核
				hql.append(" and (cms.st = '07' or cms.st = '09' or cms.st = '08' or cms.st = '10' or cms.st = '11' or cms.st = '12' or cms.st = '14') "); 
			}else if (cmsDto.getFlag() ==4) {//历史
				hql.append(" and (cms.st = '06' or cms.achiv = '1') "); 
			}
			if (cmsDto.getFlag() !=4) {
				hql.append(" and cms.achiv = '0' "); 
			}else{
			}
		}
		//涉嫌罪名
		if (StringUtils.isNotBlank(cmsDto.getSuspktOfens())) {
			params.put("suspktOfens","%" + cmsDto.getSuspktOfens().trim() + "%" );
			hql.append(" and cms.suspktOfens like :suspktOfens"); 
		}
		//鉴定文号
		if (StringUtils.isNotBlank(cmsDto.getPrcAprislDocmsNo())) {
			params.put("prcAprislDocmsNo","%" + cmsDto.getPrcAprislDocmsNo().trim() + "%" );
			hql.append(" and cms.prcAprislDocmsNo like :prcAprislDocmsNo"); 
		}
		//受理员
		if (StringUtils.isNotBlank(cmsDto.getAcptUsrNm())) {
			params.put("name",  "%" + cmsDto.getAcptUsrNm().trim()+ "%");
			hql.append(" and aui.name like :name "); 
		}
		//标的物名称
		if (StringUtils.isNotBlank(cmsDto.getAprislArtclsNm())) {
			params.put("aprislArtclsNm", "%" + cmsDto.getAprislArtclsNm().trim() + "%");
			hql.append(" and aai.artclNm like :aprislArtclsNm "); 
		}
		//鉴定基准日
		if(StringUtils.isNotBlank(cmsDto.getBeginTime())){
			hql.append(" and cms.prcAprislBaseDt >= :beginTime");
			try {
				params.put("beginTime", DateUtil.getDateFromYYYYMMDD(cmsDto.getBeginTime().trim()));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(cmsDto.getEndTime())){
			hql.append(" and cms.prcAprislBaseDt <= :endTime");
			try {
				if (StringUtils.isNotBlank(cmsDto.getEndTime()) && cmsDto.getEndTime().length() == 10) {
					params.put("endTime", DateUtil.getDateFromYYYYMMDDHHMMSS(cmsDto.getEndTime().trim() +" 23:59:59"));
				}else{
					params.put("endTime", DateUtil.getDateFromYYYYMMDDHHMMSS(cmsDto.getEndTime().trim()));
				}
			} catch (ParseException e) {
			}
		}
		//委托单位一级
		if (cmsDto.getUnitId1() != null && cmsDto.getUnitId1() != -1) {
			params.put("unitId1", cmsDto.getUnitId1());
			hql.append(" and ui.TUnitsSort.TUnitsSort.id = :unitId1"); 
		}
		//委托单位二级级
		if (cmsDto.getUnitId2() != null && cmsDto.getUnitId2() != -1) {
			params.put("unitId2", cmsDto.getUnitId2());
			hql.append(" and ui.TUnitsSort.id = :unitId2"); 
		}
		//委托单位三级
		if (cmsDto.getUnitId3() != null && cmsDto.getUnitId3() != -1) {
			params.put("unitId3", cmsDto.getUnitId3());
			hql.append(" and ui.id = :unitId3"); 
		}
		//委托日期
		if(StringUtils.isNotBlank(cmsDto.getBeginTime1())){
			hql.append(" and cms.crtTime >= :beginTime1");
			try {
				params.put("beginTime1", DateUtil.getDateFromYYYYMMDD(cmsDto.getBeginTime1().trim()));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(cmsDto.getEndTime1())){
			hql.append(" and cms.crtTime <= :endTime1");
			try {
				if (StringUtils.isNotBlank(cmsDto.getEndTime1()) && cmsDto.getEndTime1().length() == 10) {
					params.put("endTime1", DateUtil.getDateFromYYYYMMDDHHMMSS(cmsDto.getEndTime1().trim() +" 23:59:59"));
				}else{
					params.put("endTime1", DateUtil.getDateFromYYYYMMDDHHMMSS(cmsDto.getEndTime1().trim()));
				}
			} catch (ParseException e) {
			}
		}
		hql.append(" and cms.markForDel='V' ");
		hql.append(" order by cms.id desc ");
		
		page = cmsDao.pagedQuery(hql.toString(), page, params);
		return page.getData();
	}

	@Override
	public Map<String, Object> getCmsListCount(CmsDto cmsDto) {
		StringBuffer hql = new StringBuffer("select new map(coalesce(sum(1), 0) as numCnt, coalesce(sum(coalesce(cms.aprislMnySum, 0)),0) as mnyCnt) ")
		.append("from TCommission cms left join cms.tunitsInfo ui left join cms.acptUsrId aui ");
		//标的物名称
		if (StringUtils.isNotBlank(cmsDto.getAprislArtclsNm())) {
			hql.append("left join cms.TAprislArtclsInfos aai "); 
		}
		hql.append("where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		//委托书类型
		if (cmsDto.getCmsType() == 1) {	//鉴定,认定
			//委托书类别
			if (StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("1")) {
				hql.append(" and cms.cmsTp = 1 ");
			}else if(StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("2")){
				hql.append(" and cms.cmsTp = 2 ");
			}else{
				hql.append(" and (cms.cmsTp = 1 or cms.cmsTp = 2 ) ");
			}
		}else if (cmsDto.getCmsType() == 3) {//复核
			hql.append(" and cms.cmsTp = 3 ");
		}else{
			//委托书类别
			if (StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("1")) {
				hql.append(" and cms.cmsTp = 1 ");
			}else if(StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("2")){
				hql.append(" and cms.cmsTp = 2 ");
			}else if(StringUtils.isNotBlank(cmsDto.getCmsTp()) && cmsDto.getCmsTp().equals("3")){
				hql.append(" and cms.cmsTp = 3 ");
			}
		}
		//委托内容
		if (StringUtils.isNotBlank(cmsDto.getCmsCnt())) {
			params.put("cmsCnt", "%" + cmsDto.getCmsCnt().trim() + "%");
			hql.append(" and cms.cmsCnt like :cmsCnt ");
		}
		//委托人:
		if (StringUtils.isNotBlank(cmsDto.getCmsUsr())) {
			params.put("cmsUsr", "%" + cmsDto.getCmsUsr().trim() + "%");
			hql.append(" and cms.cmsUsr like :cmsUsr ");
		}
		//状态:
		if (StringUtils.isNotBlank(cmsDto.getSt()) && !cmsDto.getSt().equals("-1")) {
			if (cmsDto.getSt().equals("99")) {//待归档
				params.put("achiv", "1");
				hql.append(" and cms.achiv = :achiv "); 
			}else{
				params.put("st", cmsDto.getSt().trim());
				hql.append(" and cms.st = :st ").append(" and cms.achiv = '0' "); 
			}
		}else {
			if (cmsDto.getFlag() ==1) {//鉴定、认定
				hql.append(" and (cms.st = '01' or cms.st = '02'  or cms.st = '03' or cms.st = '04' or cms.st = '05' or cms.st = '08' or cms.st = '10' or cms.st = '12' or cms.st = '13' or cms.st = '14') "); 
			}else if (cmsDto.getFlag() ==2) {//复核裁定
				hql.append(" and (cms.st = '01' or cms.st = '02'  or cms.st = '03' or cms.st = '04' or cms.st = '05' or cms.st = '08' or cms.st = '10' or cms.st = '12' or cms.st = '13' or cms.st = '14') "); 
			}else if (cmsDto.getFlag() ==3) {//审核
				hql.append(" and (cms.st = '07' or cms.st = '09' or cms.st = '08' or cms.st = '10' or cms.st = '11' or cms.st = '12' or cms.st = '14') "); 
			}else if (cmsDto.getFlag() ==4) {//历史
				hql.append(" and (cms.st = '06' or cms.achiv = '1') "); 
			}
			if (cmsDto.getFlag() !=4) {
				hql.append(" and cms.achiv = '0' "); 
			}else{
			}
		}
		//涉嫌罪名
		if (StringUtils.isNotBlank(cmsDto.getSuspktOfens())) {
			params.put("suspktOfens","%" + cmsDto.getSuspktOfens().trim() + "%" );
			hql.append(" and cms.suspktOfens like :suspktOfens"); 
		}
		//鉴定文号
		if (StringUtils.isNotBlank(cmsDto.getPrcAprislDocmsNo())) {
			params.put("prcAprislDocmsNo","%" + cmsDto.getPrcAprislDocmsNo().trim() + "%" );
			hql.append(" and cms.prcAprislDocmsNo like :prcAprislDocmsNo"); 
		}
		//受理员
		if (StringUtils.isNotBlank(cmsDto.getAcptUsrNm())) {
			params.put("name",  "%" + cmsDto.getAcptUsrNm().trim()+ "%");
			hql.append(" and aui.name like :name "); 
		}
		//标的物名称
		if (StringUtils.isNotBlank(cmsDto.getAprislArtclsNm())) {
			params.put("aprislArtclsNm", "%" + cmsDto.getAprislArtclsNm().trim() + "%");
			hql.append(" and aai.artclNm like :aprislArtclsNm "); 
		}
		//鉴定基准日
		if(StringUtils.isNotBlank(cmsDto.getBeginTime())){
			hql.append(" and cms.prcAprislBaseDt >= :beginTime");
			try {
				params.put("beginTime", DateUtil.getDateFromYYYYMMDD(cmsDto.getBeginTime().trim()));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(cmsDto.getEndTime())){
			hql.append(" and cms.prcAprislBaseDt <= :endTime");
			try {
				if (StringUtils.isNotBlank(cmsDto.getEndTime()) && cmsDto.getEndTime().length() == 10) {
					params.put("endTime", DateUtil.getDateFromYYYYMMDDHHMMSS(cmsDto.getEndTime().trim() +" 23:59:59"));
				}else{
					params.put("endTime", DateUtil.getDateFromYYYYMMDDHHMMSS(cmsDto.getEndTime().trim()));
				}
			} catch (ParseException e) {
			}
		}
		//委托单位一级
		if (cmsDto.getUnitId1() != null && cmsDto.getUnitId1() != -1) {
			params.put("unitId1", cmsDto.getUnitId1());
			hql.append(" and ui.TUnitsSort.TUnitsSort.id = :unitId1"); 
		}
		//委托单位二级级
		if (cmsDto.getUnitId2() != null && cmsDto.getUnitId2() != -1) {
			params.put("unitId2", cmsDto.getUnitId2());
			hql.append(" and ui.TUnitsSort.id = :unitId2"); 
		}
		//委托单位三级
		if (cmsDto.getUnitId3() != null && cmsDto.getUnitId3() != -1) {
			params.put("unitId3", cmsDto.getUnitId3());
			hql.append(" and ui.id = :unitId3"); 
		}
		//委托日期
		if(StringUtils.isNotBlank(cmsDto.getBeginTime1())){
			hql.append(" and cms.crtTime >= :beginTime1");
			try {
				params.put("beginTime1", DateUtil.getDateFromYYYYMMDD(cmsDto.getBeginTime1().trim()));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(cmsDto.getEndTime1())){
			hql.append(" and cms.crtTime <= :endTime1");
			try {
				if (StringUtils.isNotBlank(cmsDto.getEndTime1()) && cmsDto.getEndTime1().length() == 10) {
					params.put("endTime1", DateUtil.getDateFromYYYYMMDDHHMMSS(cmsDto.getEndTime1().trim() +" 23:59:59"));
				}else{
					params.put("endTime1", DateUtil.getDateFromYYYYMMDDHHMMSS(cmsDto.getEndTime1().trim()));
				}
			} catch (ParseException e) {
			}
		}
		hql.append(" and cms.markForDel='V' ");
		return (Map<String, Object>) cmsDao.uniqueResult(hql.toString(), params);
	}
	
	@Override
	public TCommission getCms(Long id) {
		String hql = "select cms from TCommission cms left join fetch cms.acptUsrId where cms.id = :id and cms.markForDel='V' ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<TCommission> cmsList = (List<TCommission>) cmsDao.findObjectsByHql(hql, params);
		if (cmsList.size() <= 0) {
			return null;
		}
		return cmsList.get(0);
	}
	
	@Override
	public int uploadFiles(File file, String fileName, TAttachment at) throws Exception{
		// 获得登陆用户信息 
		WebUser webUser = WebInfoMgmt.getWebInfo().getWebUser();
		//获取文书号
		TCommission cms = (TCommission) this.cmsDao.getObject(TCommission.class, at.getTCommission().getId());
		if (null == cms || StringUtils.isBlank(cms.getPadCode())) {
			return 0;
		}
		//上传文件路径
		String articleRootPath= FilePathCfgUtil.getCmsdir();
		//String autoFileName = FileTool.getAutoFileName(FileTool.getSuffixName(fileName));
		String autoFileName = cms.getPadCode() + "/" + fileName;
		at.setFileNm(fileName);
		at.setSaveFileNm(autoFileName);
		TUserInfo updUser = new TUserInfo();
		updUser.setId(webUser.getId());
		at.setTUserInfo(updUser);	//上传人
		at.setDownloads(0l);
		if (null == at.getTCommission() || null == at.getTCommission().getId()) {
			at.setTCommission(null);
		}
		//上传文件真实路径
		String filePath = articleRootPath + "/" + autoFileName;
		//是否上传成功
		Boolean isUpload = FileTool.copyFile(file, filePath);	
		if (!isUpload) {
			//throw new Exception("上传文件错误");
		}
		this.cmsDao.saveObject(at);
		return 1;
	}
	
	@Override
	public List<TAttachment> getAttachmentList(TAttachment at, int type){
		StringBuilder hql = new StringBuilder("select at from TAttachment at where 1 = 1 ");
		if (type == 1 && at.getTCommission().getId() != null) {
			hql.append("and at.TCommission.id = :cmsId ");
		}else if(null != at.getTmpCmsId()){
			hql.append("and at.tmpCmsId = :tmpCmsId ");
		}else if(null == at.getTmpCmsId() || null == at.getTCommission().getId()){
			return null;
		}
		hql.append("and at.atTp = :atTp ")
		.append("and at.markForDel='V' ")
		.append("order by at.id asc ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (type == 1) {
			params.put("cmsId", at.getTCommission().getId());
		}else{
			params.put("tmpCmsId", at.getTmpCmsId());
		}
		params.put("atTp", at.getAtTp());
		List<TAttachment> atList = (List<TAttachment>) cmsDao.findObjectsByHql(hql.toString(), params);
		return atList;
	}
	
	@Override
	public List<TAttachment> getAttachmentList(TAttachment at){
		return this.getAttachmentList(at, 1);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TAttachment downloadAttachment(Long id){
		//StringBuilder hql = new StringBuilder("select at from TAttachment at where at.id = :atId and at.markForDel='V' ");
		TAttachment at = (TAttachment) this.cmsDao.getObjectByClass(TAttachment.class, id);
		at.setDownloads(at.getDownloads() + 1);
		this.cmsDao.saveObject(at);
		return at;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteAttachment(Long atId){
		//上传文件路径
		String articleRootPath= FilePathCfgUtil.getCmsdir();
		TAttachment at = (TAttachment) this.cmsDao.getObject(TAttachment.class, atId);
		//上传文件真实路径
		String filePath = articleRootPath + "/" + at.getSaveFileNm();
		try {
			File f = new File(filePath);
			if(f.isFile()){
				f.delete();
			}
		} catch (Exception e) {
			getLog().info(e, e);
		}
		this.cmsDao.removeObject(at);
	}
	
	@Override
	public List<TAprislArtclsInfo> getAprislList(TAprislArtclsInfo aprisl,int type) {
		StringBuilder hql = new StringBuilder("select aprisl from TAprislArtclsInfo aprisl where 1 = 1 ");
		if (type == 1 && aprisl.getTCommission().getId() != null) {
			hql.append("and aprisl.TCommission.id = :cmsId ");
		}else if(null != aprisl.getTmpCmsId()){
			hql.append("and aprisl.tmpCmsId = :tmpCmsId ");
		}
		hql.append("and aprisl.markForDel='V' ").append("order by aprisl.id asc ");
		Map<String, Object> params = new HashMap<String, Object>();
		if (type == 1) {
			params.put("cmsId", aprisl.getTCommission().getId());
		}else{
			params.put("tmpCmsId", aprisl.getTmpCmsId());
		}
		List<TAprislArtclsInfo> aprislList = (List<TAprislArtclsInfo>) cmsDao.findObjectsByHql(hql.toString(), params);
		return aprislList;
	}

	@Override
	public List<TAprislArtclsInfo> getAprislList(TAprislArtclsInfo aprisl) {
		// TODO Auto-generated method stub
		return this.getAprislList(aprisl, 1);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void deleteAprisl(Long aprislId) {
		TAprislArtclsInfo aprisl = (TAprislArtclsInfo) cmsDao.getObject(TAprislArtclsInfo.class, aprislId);
		cmsDao.removeAllObjects(aprisl.getTCmsArtclsAccesorses());
		cmsDao.removeAllObjects(aprisl.getTPrfrncPrcLib());
		cmsDao.removeObject(aprisl);
	}
	
	@Override
	public TCommission saveCms(CmsDto saveDto,TRecUsr recUsr) {
		// 获得登陆用户信息 
		WebUser webUser = WebInfoMgmt.getWebInfo().getWebUser();
		
		boolean editFlag = false;
		TCommission cms = null;
		//修改
		if (null != saveDto && null != saveDto.getId() && saveDto.getId() > 0) {
			editFlag = true;
			cms = (TCommission) this.cmsDao.getObject(TCommission.class, saveDto.getId());
		}
		saveDto.setSuspktOfens(StringUtils.isNotBlank(saveDto.getSuspktOfens2())? ("99:" + saveDto.getSuspktOfens2()) : saveDto.getSuspktOfens1());
		//saveDto.setSt("01");	//待处理
		saveDto.setAchiv("0");	//未归档
		if(saveDto.getTCommission().getId() == null) {	//历史委托书
			saveDto.setTCommission(null);
		}
		if(saveDto.getTunitsInfo() != null && saveDto.getTunitsInfo().getId() == null) {	//委托单位
			saveDto.setTunitsInfo(null);
		}
		if (null != cms) {
			if (!cms.getAprislTp().equals(saveDto.getAprislTp())) {
				//对流水号赋值
				String padCode = selectPadCode(saveDto.getAprislTp(), cms.getId());
				saveDto.setPadCode(padCode);
				//修改附件目录
				String articleRootPath= FilePathCfgUtil.getCmsdir()+"/" ;
				FileTool.renameFile(articleRootPath + cms.getPadCode(), articleRootPath + padCode);
			}
			cms = this.copyDto(cms, saveDto);
		}else{
			cms=(TCommission) PojoUtils.convert2Pojo(saveDto);
			TUserInfo acptUsrId = new TUserInfo();
			acptUsrId.setId(webUser.getId());
			cms.setAcptUsrId(acptUsrId);
			//cms.setAcptDt(new Date());
			//对流水号赋值
			String padCode = selectPadCode(saveDto.getAprislTp(), 0l);
			cms.setPadCode(padCode);
		}
		//对流水号赋值
		String prcAprislDocmsNo= cms.getPrcAprislDocmsNo();
		prcAprislDocmsNo = "京价" +prcAprislDocmsNo + "" + cms.getPadCode() +"号";
		cms.setPrcAprislDocmsNo(prcAprislDocmsNo); 
		//保存委托书
		cms = (TCommission) cmsDao.saveObject(cms);
		//保存查验人员
		List<TRecUsr> recList = null;
		if(StringUtils.isNotBlank(saveDto.getRecUsrIds())){
			recList = new ArrayList<TRecUsr>();
			String [] ids = saveDto.getRecUsrIds().split(",");
			for(String id : ids){
				recUsr = new TRecUsr();
				recUsr.setTCommission(cms);
				TUserInfo userInfo = new TUserInfo();
				userInfo.setId(Long.valueOf(id));
				recUsr.setTUserInfo(userInfo);
				recUsr.setTCommission(cms);
				recList.add(recUsr);
			}  
			if(recList != null) {
				cmsDao.saveObjectList(recList);
			}
		}
		if (!editFlag && saveDto.getTmpCmsId() > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cmsId", cms.getId());
			params.put("tmpCmsId", saveDto.getTmpCmsId());
			//保存委托明细
			cmsDao.updateTAprislArtclsInfos(params);
			//保存相关附件
			cmsDao.updateAttachments(params);
		}
		return cms;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int auditCms(CmsDto saveDto){
		int status = 0;
		// 获得登陆用户信息 
		WebUser webUser = WebInfoMgmt.getWebInfo().getWebUser();
		TUserInfo updUser = new TUserInfo();
		updUser.setId(webUser.getId());
		
		//审核委托书
		TCommission cms = (TCommission) cmsDao.getObject(TCommission.class, saveDto.getId());
		//委托书授权
		if (saveDto.getSt().equals("-99")) {
			cms.setAthrzUser(saveDto.getAthrzUser());
			this.cmsDao.saveObject(cms);
			return 1;
		}
		cms.setAchiv(saveDto.getAchiv());	//是否归档
		if (saveDto.getAchiv().equals("1")) {
			cms.setAchivDt(new Date());	//归档日期
		}
		if (cms.getSt().equals("03") && saveDto.getSt().equals("02")) {
			cms.setSt("02");			
		}else{
			cms.setSt(saveDto.getSt());			//委托书状态
		}
		if (saveDto.getSt().equals("03")) {
			cms.setAcptUsrId(updUser);
			cms.setAcptDt(new Date());//受理日期
		}
		if (saveDto.getSt().equals("14")) {//审批通过
			cms.setLastTrialDt(new Date());//受理日期
		}
		/*if (saveDto.getAuditFlag().equals("1")) {
			cms.setFirstTrialOpinis(saveDto.getFirstTrialOpinis());		//初审意见
		}else if(saveDto.getAuditFlag().equals("2")){
			cms.setRetrialOpinis(saveDto.getRetrialOpinis());			//复审意见
		}else if(saveDto.getAuditFlag().equals("3")){
			cms.setLastTrialOpinis(saveDto.getLastTrialOpinis());		//终审意见
		}*/
		this.cmsDao.saveObject(cms);
		if (null != saveDto.getAuditFlag() && saveDto.getAuditFlag().matches("1|2|3")) {
			//新增审批记录
			TApprovalInfo ai = new TApprovalInfo();
			ai.setTCommission(cms);	//委托书
			ai.setApprovalTp(saveDto.getSt());	//操作类型saveDto.getSt()
			ai.setMarkForDel("V");				//是否有效
			if (null != saveDto.getAuditFlag() && saveDto.getAuditFlag().equals("1")) {
				ai.setRemark(saveDto.getFirstTrialOpinis());//初审
			}else if(null != saveDto.getAuditFlag() && saveDto.getAuditFlag().equals("2")){
				ai.setRemark(saveDto.getRetrialOpinis());	//复审
			}else if(null != saveDto.getAuditFlag() && saveDto.getAuditFlag().equals("3")){
				ai.setRemark(saveDto.getLastTrialOpinis());	//终审
			}
			
			ai.setTUserInfo(updUser);
			this.cmsDao.saveObject(ai);
		}
		status = 1;
		return status;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String selectPadCode(String tp, Long cmsId) {
		String padCode = "";
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("select cms from TCommission cms where 1=1 ");
		hql.append("and date_format(cms.crtTime, '%Y') = date_format(sysdate(),'%Y')");
		hql.append("and cms.aprislTp =:aprislTp ");
		if (cmsId > 0) {
			hql.append("and cms.id != :cmsId ");
			params.put("cmsId", cmsId);
		}
		params.put("aprislTp", tp);
		hql.append(" order by cms.padCode desc ");
		List<TCommission> list = cmsDao.findObjectsByHql(hql.toString(), params);
		int year = DateUtil.getYYYYFromDate(new Date());
		if(list.size() > 0 && list.get(0).getPadCode()!= null){
			padCode = list.get(0).getPadCode();
			String pad = padCode.substring(padCode.length()-4,padCode.length()); 
			int p = Integer.parseInt(pad) + 1;
			//纪检监察默认从121开始
			if (tp.equals("12")) {
				padCode = "【"+year+"】" + tp + (p<12?"10"+12:p<100?"10"+p:p<1000?"1"+p:p);
			}else{
				padCode = "【"+year+"】" + tp + (p<10?"000"+p:p<100?"00"+p:p<1000?"0"+p:p);
			}
			if (DateUtil.getCurrentYear().equals("2013") && tp.equals("11") && p<860) {
				padCode = "【"+year+"】" + tp + "0860";
			}
		}else{
			if (DateUtil.getCurrentYear().equals("2013") && tp.equals("11")) {
				padCode = "【"+year+"】" + tp + "0860";
			}
			//纪检监察默认从121开始
			else if (DateUtil.getCurrentYear().equals("2013") && tp.equals("12")) {
				padCode = "【"+year+"】" + tp + "1012";
			}else{
				padCode = "【"+year+"】" + (tp.equals("12")?(tp+"1001"):tp + "0001");
			}
		}
		return padCode;
	}
	
	@Override
	public int achiveCms(CmsDto cmsDto){
		int status = 0;
		List<TCommission> cmsList = new ArrayList<TCommission>();
		TCommission cms = null;
		for(long id:cmsDto.getIds()) {
			cms = getCms(id);
			if (cms.getSt().equals("13")) {
				cms.setAchiv("1");
				cmsList.add(cms);
			}else{
				break;
			}
		}
		if (cmsList.size() > 0) {
			cmsDao.saveObjectList(cmsList);	
			status = 1;
		}
		return status;
	}
	
	//复制saveDto
	private TCommission copyDto(TCommission cms, CmsDto saveDto) {
		cms.setCmsTp(saveDto.getCmsTp());
		cms.setIsEnty(saveDto.getIsEnty());
		cms.setPrcAprislDocmsNo(saveDto.getPrcAprislDocmsNo());
		cms.setSuspktOfens(saveDto.getSuspktOfens());
		cms.setCmsUsr(saveDto.getCmsUsr());
		cms.setCmsUnitAdd(saveDto.getCmsUnitAdd());
		cms.setPostcode(saveDto.getPostcode());
		cms.setUnitPhon(saveDto.getUnitPhon());
		cms.setPrcAprislRqrms(saveDto.getPrcAprislRqrms());
		cms.setPrcAprislBaseDt(saveDto.getPrcAprislBaseDt());
		cms.setAprislTp(saveDto.getAprislTp());
		cms.setCmsUsrTel(saveDto.getCmsUsrTel());
		cms.setCmsCnt(saveDto.getCmsCnt());
		cms.setCmsUnitAreaId(saveDto.getCmsUnitAreaId());
		cms.setCmsUnitNm(saveDto.getCmsUnitNm());
		cms.setTCommission(saveDto.getTCommission());
		cms.setPrcAprislCln(saveDto.getPrcAprislCln());
		cms.setPrcAprislMtd(saveDto.getPrcAprislMtd());
		cms.setPrcAprislPrc(saveDto.getPrcAprislPrc());
		cms.setRecDt(saveDto.getRecDt());
		cms.setRecAdrs(saveDto.getRecAdrs());
		cms.setFirstTrialOpinis(saveDto.getFirstTrialOpinis());
		cms.setRetrialOpinis(saveDto.getRetrialOpinis());
		cms.setLastTrialOpinis(saveDto.getLastTrialOpinis());
		cms.setTunitsInfo(saveDto.getTunitsInfo());
		cms.setAprislMnySum(saveDto.getAprislMnySum());
		cms.setOciAprislMny(saveDto.getOciAprislMny());
		cms.setPadCode(saveDto.getPadCode());
		cms.setArtclsDetail(saveDto.getArtclsDetail());
		return cms;
	}

	
	@Override
	public Object[] getPrintCms(Long printCmsId, int printCmsType,String attachmentType ,String isGenerateCms) throws Exception {
		Object[] resultObj = {new String(), new String()};
		TCommission tcommission = null;
		String cmsContent = null;
		String cmsFilePath = null;
		Map dataMap = new HashMap();
		OfficeConverter officeConverter = new OfficeConverter();
		PeFileUtil peFileUtil = new PeFileUtil();
		if (null == printCmsId || printCmsId <=0) {
			return resultObj;
		}
		tcommission = getCms(printCmsId);
		
		if(-1 == printCmsType) { //打印附件，此功能已经删除
			StringBuilder sb = new StringBuilder();
			String attachmentPath = Propertiesconfiguration.getStringProperty("FILE_CMS_ATTR_DIR");
			String fileName = null; //文件的名称
			String filePath = null; //文件的路径
			
			Set<TAttachment> attachments = tcommission.getTAttachments(); //获得所有的附件
			for(TAttachment attachment : attachments) { //遍历附件
				String atTp = attachment.getAtTp(); //附件类型
				String fileTp = attachment.getFileTp();
				if(StringUtils.isNotBlank(atTp) && StringUtils.isNotBlank(attachmentType) && atTp.trim().equalsIgnoreCase(attachmentType.trim())) { //附件类型与此附件类型相同
					fileName = attachment.getSaveFileNm();
					filePath = attachmentPath + "/" +fileName;
					
					if("doc".equalsIgnoreCase(fileTp)) { //附件是word文档
						
						String htmlFilePath = peFileUtil.replaceFileExtension(filePath, "doc", "html");
						officeConverter.convert(filePath, htmlFilePath); //把doc转化成html
						sb.append("\n<br/><br/>\n");
						sb.append(peFileUtil.getFileContent(htmlFilePath , "UTF-8"));
						
					} else if("jpg".equalsIgnoreCase(fileTp) || "gif".equalsIgnoreCase(fileTp)) { //附件类型是图片
						sb.append("\n<br><br/>\n");
						sb.append("<img src=\"#@#imgActionPath#@#?id=" +attachment.getId().longValue() + "\"/>");
					}
				}
			}
			
			cmsContent = sb.toString();
			
		} else {
			String aprislTp = tcommission.getAprislTp();
			int authenticateType = 2;
			
			getLog().info("打印，鉴定类别 :" + aprislTp);
			if("11".equals(aprislTp)) {
				authenticateType = 1;
			}
			DataToHtml drh = new DataToHtml();
			//取得文件的路径
			cmsFilePath = peFileUtil.getCmsHtmlPath(tcommission.getPadCode(), printCmsType, authenticateType);
			getLog().info("打印文件路径 :" + cmsFilePath);
			convert(dataMap , tcommission , printCmsType , authenticateType);
			if(!peFileUtil.isFileExist(cmsFilePath) || "true".equalsIgnoreCase(isGenerateCms)) { //生成文件
				getLog().info("第一次打印或者用户点击重新生成打印");
				drh.createHtml(dataMap, printCmsType, authenticateType, tcommission.getPadCode());   //生成html文件
				String cmsDodPath = peFileUtil.getCmsDocPath(tcommission.getPadCode(), printCmsType, authenticateType);
				
				officeConverter.convert(cmsFilePath , cmsDodPath); //把html转化成doc
			}
			
			cmsContent = peFileUtil.getFileContent(cmsFilePath);
		}
		cmsContent = cmsContent.replaceAll(":dsysdate", MapUtils.getString(dataMap, "dsysdate"))
				.replaceAll("<span style=\"display:none\">", "<span style=\"display:\">").replace(":sysdate", MapUtils.getString(dataMap, "sysdate"));
		resultObj[0] = cmsContent;
		resultObj[1] = tcommission.getSt();
		return resultObj;
	}

	
	
	@SuppressWarnings("unchecked")
	private void convert(Map dataMap, TCommission tcommission, int printCmsType , int authenticateType) {
		
		if(printCmsType == 1) {	//委托书
			getLog().info("打印委托书");
			dataMap.put("doccode", StringUtil.convertNull(tcommission.getPrcAprislDocmsNo()));	//PRC_APRISL_DOCMS_NO
			dataMap.put("pcontent", StringUtil.convertNull(tcommission.getCmsCnt()));	//CMS_CNT
			dataMap.put("sysdate", CM.formatDate("yyyy年M月d日"));
			//CMS_TP : 1、2 ： CMS_UNIT_ID.NAME  ; 3:CMS_UNIT_NM
			if("3".equals(StringUtil.convertNull(tcommission.getCmsTp()))) {
				dataMap.put("wname", StringUtil.convertNull(tcommission.getCmsUnitNm()));
			} else  {
				try {
					dataMap.put("wname", StringUtil.convertNull(tcommission.getTunitsInfo().getName()));
				} catch(Exception e) {
					getLog().error("tcommission.getTunitsInfo() 为空" , e);
					dataMap.put("wname", "");
				}
			}
					
			dataMap.put("wperson", StringUtil.convertNull(tcommission.getCmsUsr()));		//CMS_USR
			dataMap.put("wtel", StringUtil.convertNull(tcommission.getUnitPhon()));	//UNIT_PHON
			dataMap.put("wadd", StringUtil.convertNull(tcommission.getCmsUnitAdd()));	//CMS_UNIT_ADD
			dataMap.put("wzip", StringUtil.convertNull(tcommission.getPostcode()));		//POSTCODE

			List obitems =  new  ArrayList();
			
			//TAprislArtclsInfo
			for (TAprislArtclsInfo tAprislArtclsInfo : tcommission.getTAprislArtclsInfos()){
				obinfo t = new obinfo();
				t.setBrm("");		//输入“”
				t.setBuydate(CM.formatDate("yyyy年M月d日"));
				if("1".equals(StringUtil.convertNull(tAprislArtclsInfo.getIsNmlUse()))) {//IS_NML_USE1：是 0：否
					t.setIsuse("是");
				} else if("0".equals(StringUtil.convertNull(tAprislArtclsInfo.getIsNmlUse()))) {
					t.setIsuse("否");
				}
						
				t.setObname(StringUtil.convertNull(tAprislArtclsInfo.getQnty()).toString() + 
						StringUtil.convertNull(tAprislArtclsInfo.getUnit()).toString() + 
						StringUtil.convertNull(tAprislArtclsInfo.getBrndNm()).toString() + 
						StringUtil.convertNull(tAprislArtclsInfo.getSpecTp()).toString() +
						StringUtil.convertNull(tAprislArtclsInfo.getArtclNm()).toString());		//QNTY+UNIT+BRND_NM+SPEC_TP+ARTCL_NM
				t.setOtremark(StringUtil.convertNull(tAprislArtclsInfo.getOthInfo()).toString());	//OTH_INFO
				
				StringBuilder parts = new StringBuilder();
				int tindex = 0;
				for(TCmsArtclsAccesors tCmsArtclsAccesorses : tAprislArtclsInfo.getTCmsArtclsAccesorses()) {
					if(tindex != 0) {
						parts.append(" 、 ");
					}
					parts.append(StringUtil.convertNull(tCmsArtclsAccesorses.getNm()));
					tindex += 1;
				}
				t.setParts(StringUtil.convertNull(parts.toString()).toString());		// TAprislArtclsInfo.TCmsArtclsAccesorses.NM
				t.setStm(StringUtil.convertNull(tAprislArtclsInfo.getSpecTp()).toString());		//SPEC_TP
				t.setYprice(StringUtil.convertNull(tAprislArtclsInfo.getOrgnlPrc()).toString());		//ORGNL_PRC
				obitems.add(t);
			}
			dataMap.put("items", obitems);
		} else if(printCmsType == 2) { //取件通知书
			getLog().info("取件通知书");
			dataMap.put("doc_code", StringUtil.convertNull(tcommission.getPrcAprislDocmsNo()));	//PRC_APRISL_DOCMS_NO

			if("3".equals(StringUtil.convertNull(tcommission.getCmsTp()))) {
				dataMap.put("org_name", StringUtil.convertNull(tcommission.getCmsUnitNm()));
				dataMap.put("type_name", "复核裁定委托书");
			} else if("1".equals(StringUtil.convertNull(tcommission.getCmsTp())) || "2".equals(StringUtil.convertNull(tcommission.getCmsTp()))) {
				try {
					dataMap.put("org_name", StringUtil.convertNull(tcommission.getTunitsInfo().getName()));
				} catch(Exception e) {
					getLog().error("tcommission.getTunitsInfo() 为空");
					dataMap.put("org_name", StringUtil.convertNull(""));
				}
				dataMap.put("type_name", "价格鉴定委托书");
			}
			

			dataMap.put("getdate", CM.formatDate(7, "yyyy年M月d日"));
			
			try {
				dataMap.put("rec_name", StringUtil.convertNull(tcommission.getAcptUsrId().getName()));		//acptUsrId.NAME
				dataMap.put("rec_tel", StringUtil.convertNull(tcommission.getAcptUsrId().getTel()));		//acptUsrId.TEL
			} catch(Exception e) {
				getLog().error("tcommission.getAcptUsrId() 为空");
				dataMap.put("rec_name", "");		//acptUsrId.NAME
				dataMap.put("rec_tel", "");		//acptUsrId.TEL
			}
			dataMap.put("sysdate", CM.formatDate("yyyy年M月d日"));
			
		} else if(printCmsType == 3) {	//送达回证
			
			dataMap.put("writing", StringUtil.convertNull(tcommission.getPrcAprislDocmsNo()));
			dataMap.put("consignor", StringUtil.convertNull(tcommission.getCmsUsr()));
			dataMap.put("senddate", CM.formatDate("yyyy年M月d日"));
			dataMap.put("sysdate", CM.formatDate("yyyy年M月d日HH时mm分"));
			
			
		} else if(printCmsType == 4) {	//鉴定结论
			
			//文案号  PRC_APRISL_DOCMS_NO
			dataMap.put("doc_code", StringUtil.convertNull(tcommission.getPrcAprislDocmsNo()));
			
			//CMS_TP : 1、2 ： CMS_UNIT_ID.NAME  ; 3:CMS_UNIT_NM //单位名称 
			if("3".equals(tcommission.getCmsTp())) {
				dataMap.put("p_name", StringUtil.convertNull(tcommission.getCmsUnitNm()));
			} else if("1".equals(StringUtil.convertNull(tcommission.getCmsTp())) || "2".equals(StringUtil.convertNull(tcommission.getCmsTp()))) {
				//一级单位+二级单位+三级单位
				String pName = "";
//				try {
//					pName += StringUtil.convertNull(tcommission.getTunitsInfo().getTUnitsSort().getTUnitsSort().getSortName());
//				} catch(Exception e) {
//					getLog().error("一级单位为空" ,e);
//				}
//				try {
//					pName += StringUtil.convertNull(tcommission.getTunitsInfo().getTUnitsSort().getSortName());
//				} catch(Exception e) {
//					getLog().error("二级单位为空" , e);
//				}
				try {
					if (null != tcommission.getTunitsInfo()) {
						pName += StringUtil.convertNull(tcommission.getTunitsInfo().getName());
					}
				} catch(Exception e) {
					getLog().error("三级单位为空" , e);
				}
				dataMap.put("p_name",    pName );
				
			}
			
			
			//标的物名称 	如类别多无法使用统称，则使用“XX（第一项物品）等一批物品”概括描述。
			
			String pContext = "";
			String objName = "";
			String pMethod = "";
			String pMethodSub = "";
			String mPriceStr = "";
			List<String> mPrice2List = new ArrayList<String>();
			dataMap.put("mPrice2List", new ArrayList<String>());
			//鉴定标的描述 20131223修改
			if (StringUtils.isNotBlank(tcommission.getArtclsDetail())) {
				pContext = tcommission.getArtclsDetail();
			}
			
			//标的物
			Set<TAprislArtclsInfo> tAprislArtclsInfoSet = tcommission.getTAprislArtclsInfos();
			if(tAprislArtclsInfoSet.size() > 0) {
				Double mprice = tcommission.getAprislMnySum();
				int tAprislArtclsInfoSizeIndex = 0;
				for(TAprislArtclsInfo info : tAprislArtclsInfoSet) {
					tAprislArtclsInfoSizeIndex ++;
					
					if(tAprislArtclsInfoSizeIndex == 1) { //第1个标的物
						//pContext = info.getQnty().intValue() + info.getUnit() +info.getArtclNm();
						pMethodSub = PeUtility.getMethod(StringUtil.convertNull(info.getPrcAprislMtd()));
						pMethod= (null != pMethodSub)? pMethodSub : "";
						if(tAprislArtclsInfoSet.size() > 2) {	//多个标的物(3个以上) 当3种以上物品（包括3种）时（）内容（详见价格鉴定清单1-N项）
							//objName = StringUtil.convertNull(info.getQnty()) + info.getUnit() +info.getArtclNm();
							objName += "（详见价格鉴定清单1-"+tAprislArtclsInfoSet.size()+"项）";
						} else if(tAprislArtclsInfoSet.size() == 1) {
							//只有1个标的物	当1种物品时（）内容描述为品牌名称型号规格、备注信息20131223修改
							objName += "（" + info.getBrndNm() + info.getSpecTp();
							if (StringUtils.isNotBlank(info.getRemark())) {
								objName += "、" + info.getRemark();
							}
							objName += "）";
						}else{
							//只有2个标的物		[回车]+[（标的1）一把锤子（**牌**型、黑色、旧、无实物）]+[回车]+[（标的2）一把锤子（**牌**型、黑色、旧、无实物）]20131223修改
							int i = 0;
							for (TAprislArtclsInfo info_ : tAprislArtclsInfoSet) {
								objName += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +  (i+1) + "、" + 
										StringUtil.convertNull(info_.getQnty()) + info_.getUnit() + info_.getArtclNm();
								if (StringUtils.isNotBlank(info_.getBrndNm()) || StringUtils.isNotBlank(info_.getSpecTp()) || StringUtils.isNotBlank(info_.getRemark())) {
									objName += "（" + StringUtil.convertNull(info_.getBrndNm()) + StringUtil.convertNull(info_.getSpecTp())
											+  StringUtil.convertNull(info_.getRemark(), "、") + "）";
								}
								i++;
							}
						}
						
						mPriceStr = NumberFormatterZh.toChinese(mprice) + "（￥"+ StringUtil.convertNull(mprice).toString()+"）";
						try{
							mPrice2List.add(info.getArtclNm()+" ¥ "+StringUtil.convertNull(info.getAprislPrc()));
						}catch(Exception e) {
							
						}
					} else { 
						if(tAprislArtclsInfoSet.size()  == 2) { //只有2个标的物
							mPriceStr += "<br/>其中：";
							try {
								mPrice2List.add(info.getArtclNm()+" ¥ "+StringUtil.convertNull(info.getAprislPrc()));
							}catch(Exception e) {
								
							}
							dataMap.put("mPrice2List", mPrice2List);
						} 
						pMethodSub = PeUtility.getMethod(StringUtil.convertNull(info.getPrcAprislMtd()));
						if(pMethodSub != null && !pMethod.contains(pMethodSub)) {
							pMethod += "、" + PeUtility.getMethod(StringUtil.convertNull(info.getPrcAprislMtd())); //多个鉴定方法用"、"分割
						}
					}
				}
			}
			
			dataMap.put("p_context", pContext); //CMS_CNT
			dataMap.put("obj_name", objName);
			dataMap.put("pmethod", pMethod);
			dataMap.put("mprice", mPriceStr);
			
			List<String> personList = new ArrayList<String>();
			personList.add("薛酉冬");
			personList.add("潘&nbsp;&nbsp;&nbsp;育");
			dataMap.put("personList", personList); //acptUsrId.NAME
			dataMap.put("dsysdate", DateFormatterZh.dateToCN(tcommission.getLastTrialDt()));//当前时间格式化成 二○一x年x月x日  改成终审时间
			
			if("11".equals(StringUtil.convertNull(tcommission.getAprislTp()))) { //刑事criminal_html		APRISL_TP:11 刑事，其他为非刑事	
				getLog().info("打印刑事鉴定结论");

				dataMap.put("basedate", DateFormatterZh.getDate("yyyy年M月d日", tcommission.getPrcAprislBaseDt()));//PRC_APRISL_BASE_DT 价格基准日  格式2013年10月12日

				//List tempgists = PeUtility.splitTrim(tcommission.getPrcAprislCln());
				List<String> tempgists = new ArrayList<String>();
				tempgists.add("1、《中华人民共和国价格法》；");
				tempgists.add("2、《北京市涉案财产价格鉴定管理办法》北京市人民政府令第46号；");
				tempgists.add("3、《北京市人民政府关于修改五十九项规章部分条款的决定》北京市人民政府令第200号第四十七款；");
				tempgists.add("4、《价格鉴定行为规范（2010年版）》（发改价证办[2010]103号）国家发展和改革委员会价格认证中心；");
				tempgists.add("5、《北京市涉案财产价格鉴定管理办法》实施细则（京发改〖2009〗368号）；");
				
				tempgists.add("6、委托方出具的《涉案财产价格鉴定委托书》及价格鉴定标的清单等相关材料；");
				tempgists.add("7、价格鉴定人员对鉴定标的有关资料的核实及市场调查所取得的资料。");
				
				// 价格鉴定依据
				dataMap.put("gists", tempgists);
				
				String isEntry = tcommission.getIsEnty();
				Date acptDate = tcommission.getAcptDt();
				Date chkDate = tcommission.getRecDt();//查验日期
				
				List<String> pprocessList = new ArrayList<String>();
				
				String pprocess = "";
				if("1".equals(StringUtil.convertNull(isEntry))) {
					pprocess += "我中心接受委托后，价格鉴定人员于";
					pprocess += CM.formatDate(chkDate, "yyyy年M月d日");
					pprocess += "对鉴定标的进行了实物勘验。";
					pprocessList.add(pprocess);
				}
				
				pprocessList.add("依据市场法的替代原则，参照同种或类似物品市场价格进行修正并计算标的价格。");
				
				dataMap.put("pprocessList", pprocessList);//PRC_APRISL_PRC 价格鉴定过程
				
				dataMap.put("sysdate", CM.formatDate(tcommission.getLastTrialDt(),"yyyy年M月d日"));//价格鉴定作业日期
				
				
				Set<TAprislArtclsInfo> tAprislArtclsInfos = tcommission.getTAprislArtclsInfos();
				List<String> bfs = new ArrayList<String>();
				if(tAprislArtclsInfos.size() <= 2) {
					bfs.add("北京市涉案财产价格鉴定机构资质证书。");
					dataMap.put("p_acce", bfs);
				} else {
					
					bfs.add("1.价格鉴定清单1-" + tAprislArtclsInfos.size() + "项");
					
					bfs.add("2.北京市涉案财产价格鉴定机构资质证书。");
					
					dataMap.put("p_acce", bfs);
					
				}
				
			} else { //非刑事 nocriminal_html
				getLog().info("打印非刑事鉴定结论");
				
				
				dataMap.put("basedate", DateFormatterZh.getDate("yyyy年M月d日", tcommission.getPrcAprislBaseDt()));//PRC_APRISL_BASE_DT 价格基准日  格式2013年10月12日
				
				//List tempgists = PeUtility.splitTrim(tcommission.getPrcAprislCln());
				List<String> tempgists = new ArrayList<String>();
				tempgists.add("1、《中华人民共和国价格法》；");
				tempgists.add("2、《纪检监察机关查办案件涉案财物价格认定工作暂行办法》（中纪发［2010］35号）中共中央纪委、国家发展改革委、监察部、财政部；");
				tempgists.add("3、《北京市涉案财产价格鉴定管理办法》北京市人民政府令第46号；");
				tempgists.add("4、《北京市人民政府关于修改五十九项规章部分条款的决定》北京市人民政府令第200号第四十七款；");
				tempgists.add("5、《价格鉴定行为规范（2010年版）》（发改价证办[2010]103号）国家发展和改革委员会价格认证中心；");
				
				tempgists.add("6、《北京市涉案财产价格鉴定管理办法》实施细则（京发改〖2009〗368号）；");
				tempgists.add("7、《北京市纪检监察机关查办案件涉案财物价格认定工作办法（试行）》（京纪发［2012］11号）中共北京市纪委、北京市发展和改革委员会、北京市监察局、北京市财政局；");
				tempgists.add("8、提出方出具的《纪检监察机关查办案件涉案财物价格认定协助书》及价格认定标的相关材料；");
				tempgists.add("9、价格认定人员对认定标的有关资料的核实及市场调查所取得的资料。");
				
				// 价格鉴定依据
				dataMap.put("gists", tempgists);
				
				//价格鉴定过程
				dataMap.put("pprocess", "我中心受理价格认定协助申请后，组成价格认定工作小组。工作小组成员对认定标的进行了实物勘验。依据市场法的替代原则，参照同种或类似物品市场价格进行修正并计算标的价格，确定价格结论。");
				
				dataMap.put("sysdate", CM.formatDate(tcommission.getAcptDt() , "yyyy年M月d日") + "至" + CM.formatDate(tcommission.getLastTrialDt() , "yyyy年M月d日"));//价格鉴定作业日期
				
			}
		}
		
	}
	

	@Override
	public void savePrintCms(Long printCmsId, int printCmsType,String cmsContent) throws IOException {
		TCommission tcommission = null;
		String cmsFilePath = null;
		PeFileUtil peFileUtil = new PeFileUtil();
		OfficeConverter officeConverter = new OfficeConverter();
		tcommission = getCms(printCmsId);
		
		String aprislTp = tcommission.getAprislTp();
		int authenticateType = 2;
		
		getLog().info("打印保存，鉴定类别 :" + aprislTp);
		if("11".equals(aprislTp)) {
			authenticateType = 1;
		} 
		
		//取得文件的路径
		cmsFilePath = peFileUtil.getCmsHtmlPath(tcommission.getPadCode(), printCmsType, authenticateType);
		peFileUtil.saveFile(cmsFilePath, cmsContent);
		
		String cmsDodPath = peFileUtil.getCmsDocPath(tcommission.getPadCode(), printCmsType, authenticateType);
		
		officeConverter.convert(cmsFilePath , cmsDodPath); //把html转化成doc
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateGnlSendBk(Long cmsId, int printCmsType) {
		// 获得登陆用户信息 
		WebUser webUser = WebInfoMgmt.getWebInfo().getWebUser();
		if (null == cmsId || cmsId <= 0) {
			return;
		}
		TCommission cms = (TCommission) this.cmsDao.getObject(TCommission.class, cmsId);
		
		//管理员所有打印只能修改打印时间,其他人员只能点击生成送达回证按钮后修改送达回证时间
		if (null != webUser && StringUtils.isNotEmpty(webUser.getUsername()) && webUser.getUsername().equals("admin")) {
			cms.setPrtDt(new Date());
		}else if(printCmsType == 3) {
			cms.setGnlSendBkDt(new Date());
		}
		
		this.cmsDao.saveObject(cms);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TUserInfo> getUserRetrialUsers() {
		StringBuilder hql = new StringBuilder("select urr.TUserInfo from TUserRoleRel urr where urr.TUserInfo.markForDel = 'V' ")
		.append("and urr.TRoleInfo.id = 4 and  urr.TUserInfo.id != 1 ");	//4：复审权限,但不是管理员
		return this.cmsDao.findObjectsByHql(hql.toString(), null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getRepeatArtcls(Long cmsId){
		StringBuilder repeatCmsStr = new StringBuilder();
		//通过委托书id查询该委托书鉴定物品信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cmsId", cmsId);
		StringBuilder hql = new StringBuilder("select new map(aai.brndNm as brndNm, aai.specTp as specTp, aai.prcArtclRefDt as prcArtclRefDt) from TAprislArtclsInfo aai ")
		.append("where aai.TCommission.id = :cmsId ");
		List<Map<String, Object>> aais = this.cmsDao.findObjectsByHql(hql.toString(), params);
		params.clear();
		//判断重复(第一检索是基准日 然后是品牌 型号  存在疑似重复的委托书，文案号为XXX，请确认) 20131114
		hql = new StringBuilder("select new map(c.prcAprislDocmsNo as prcAprislDocmsNo, count(c.id) as countCms) from TAprislArtclsInfo aai ")
		.append("right join aai.TCommission c ");
		hql.append("where c.markForDel = 'V' and aai.markForDel = 'V' and c.id != :cmsId ");
		params.put("cmsId", cmsId);
		int idx = 0;
		if (null != aais && aais.size() > 0) {
			hql.append("and (");
			for (Map<String, Object> m : aais) {
				if (idx != 0) {
					hql.append("or ");
				}
				hql.append("(");
				if (null != MapUtils.getString(m, "brndNm")) {
					params.put("brndNm", MapUtils.getString(m, "brndNm"));
					hql.append("aai.brndNm = :brndNm ");
				}else{
					hql.append("aai.brndNm is null ");
				}
				if (null != MapUtils.getString(m, "specTp")) {
					params.put("specTp", MapUtils.getString(m, "specTp"));
					hql.append("and aai.specTp = :specTp ");
				}else{
					hql.append("and aai.specTp is null ");
				}
				if (null != MapUtils.getObject(m, "prcArtclRefDt")) {
					params.put("prcArtclRefDt", MapUtils.getObject(m, "prcArtclRefDt"));
					hql.append("and aai.prcArtclRefDt = :prcArtclRefDt ");
				}else{
					hql.append("and aai.prcArtclRefDt is null ");
				}
				hql.append(")");
				idx++;
			}
			hql.append(") group by c.id ");
			List<Map<String, Object>> repeatCms = this.cmsDao.findObjectsByHql(hql.toString(), params);
			if (null != repeatCms) {
				for (Map<String, Object> m : repeatCms) {
					if (idx == MapUtils.getInteger(m, "countCms", 0)) {
						repeatCmsStr.append("，\\n").append(MapUtils.getString(m, "prcAprislDocmsNo", ""));
					}
				}
			}
		}
		return repeatCmsStr.toString().replaceFirst("，", "").replaceFirst("\\n", "");
	}
	
	public CmsDao getCmsDao() {
		return cmsDao;
	}

	public void setCmsDao(CmsDao cmsDao) {
		this.cmsDao = cmsDao;
	}

}
