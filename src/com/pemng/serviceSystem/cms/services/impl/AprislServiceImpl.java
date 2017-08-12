package com.pemng.serviceSystem.cms.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.base.util.CopyBeanUtils;
import com.pemng.serviceSystem.base.util.DateUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.cms.dao.AprislDao;
import com.pemng.serviceSystem.cms.dto.AprislDto;
import com.pemng.serviceSystem.cms.services.AprislService;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.pojo.TAprislArtclsInfo;
import com.pemng.serviceSystem.pojo.TAprislArtclsPrcAprisl;
import com.pemng.serviceSystem.pojo.TCmsArtclsAccesors;
import com.pemng.serviceSystem.pojo.TCommission;
import com.pemng.serviceSystem.pojo.TMiningPriceInfo;
import com.pemng.serviceSystem.pojo.TPrfrncPrcLib;
import com.pemng.serviceSystem.pojo.TUserInfo;

public class AprislServiceImpl extends BaseServiceImpl implements AprislService {

	private AprislDao aprislDao;

	public AprislDao getAprislDao() {
		return aprislDao;
	}

	public void setAprislDao(AprislDao aprislDao) {
		this.aprislDao = aprislDao;
	}

	//添加价格鉴定
	@SuppressWarnings("unchecked")
	@Override
	public void saveAprisl(AprislDto aprislDto,JSONArray array) {
		TAprislArtclsInfo aprisl= new TAprislArtclsInfo();
		CopyBeanUtils.copyPropsWithoutNull(aprisl, aprislDto);
		aprisl= (TAprislArtclsInfo) aprislDao.saveObject(aprisl);
		
		int isize = array.length();
		List<TCmsArtclsAccesors> tCmsArtclsAccesorses = new ArrayList<TCmsArtclsAccesors>();
		for(int i =0;i <isize; i ++ ){
			JSONObject jsonobj = array.getJSONObject(i);
			//配件
			//id
			TCmsArtclsAccesors tCmsArtcls= new TCmsArtclsAccesors();
			
			tCmsArtcls.setTAprislArtclsInfo(aprisl);
			//配件名称
			tCmsArtcls.setNm(jsonobj.getString("name"));
			//配件备注
			tCmsArtcls.setRemark(jsonobj.getString("remark"));
			tCmsArtcls.setMarkForDel(Constants.MARK_FOR_DELETE_NO);
			tCmsArtclsAccesorses.add(tCmsArtcls);
		}
		aprisl.setTCmsArtclsAccesorses(tCmsArtclsAccesorses);
		aprislDao.saveObject(aprisl);
	}

	//通过id获得
	@SuppressWarnings("unchecked")
	@Override
	public TAprislArtclsInfo getObjectById(Long id) {
		String hql = "select aprisl from TAprislArtclsInfo aprisl where aprisl.id = :id and aprisl.markForDel='V' ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<TAprislArtclsInfo> TAprislArtclsInfos = (List<TAprislArtclsInfo>) aprislDao.findObjectsByHql(hql.toString(), params);
		
		if(TAprislArtclsInfos.size()==0){
			return null;
		}else{
			TAprislArtclsInfo april=TAprislArtclsInfos.get(0);
			return april;
		}
	}
	
	

	@Override
	public List<TAprislArtclsInfo> getAprislList(Pager page, AprislDto aprislDto) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("select aprisl from TAprislArtclsInfo aprisl left join fetch aprisl.TCommission where 1=1 ");
		//鉴定文号： 
		if (aprislDto.getTCommission() != null && StringUtils.isNotBlank(aprislDto.getTCommission().getPrcAprislDocmsNo())) {
			params.put("prcAprislDocmsNo", "%" + aprislDto.getTCommission().getPrcAprislDocmsNo().trim() + "%");
			hql.append(" and aprisl.TCommission.prcAprislDocmsNo like :prcAprislDocmsNo "); 
		}

		//鉴定基准日
		if(StringUtils.isNotBlank(aprislDto.getBeginTime1())){
			hql.append(" and aprisl.TCommission.prcAprislBaseDt >= :beginTime1");
			try {
				params.put("beginTime1", DateUtil.getDateFromYYYYMMDD(aprislDto.getBeginTime1().trim()));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(aprislDto.getEndTime1())){
			hql.append(" and aprisl.TCommission.prcAprislBaseDt <= :endTime1");
			try {
				if (StringUtils.isNotBlank(aprislDto.getEndTime1()) && aprislDto.getEndTime1().length() == 10) {
					params.put("endTime1", DateUtil.getDateFromYYYYMMDDHHMMSS(aprislDto.getEndTime1().trim() +" 23:59:59"));
				}else{
					params.put("endTime1", DateUtil.getDateFromYYYYMMDDHHMMSS(aprislDto.getEndTime1().trim()));
				}
			} catch (ParseException e) {
			}
		}
		
		//委托日期
		if(StringUtils.isNotBlank(aprislDto.getBeginTime2())){
			hql.append(" and aprisl.TCommission.crtTime >= :beginTime2");
			try {
				params.put("beginTime2", DateUtil.getDateFromYYYYMMDD(aprislDto.getBeginTime2().trim()));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(aprislDto.getEndTime2())){
			hql.append(" and aprisl.TCommission.crtTime <= :endTime2");
			try {
				if (StringUtils.isNotBlank(aprislDto.getEndTime2()) && aprislDto.getEndTime2().length() == 10) {
					params.put("endTime2", DateUtil.getDateFromYYYYMMDDHHMMSS(aprislDto.getEndTime2().trim() +" 23:59:59"));
				}else{
					params.put("endTime2", DateUtil.getDateFromYYYYMMDDHHMMSS(aprislDto.getEndTime2().trim()));
				}
			} catch (ParseException e) {
			}
		}
		
		
		//物品名称：
		if (StringUtils.isNotBlank(aprislDto.getArtclNm())) {
			params.put("artclNm", "%" + aprislDto.getArtclNm().trim() + "%");
			hql.append(" and aprisl.artclNm like :artclNm ");
		}
		
		//鉴定金额：
		if(aprislDto.getAprislPrc1() != null){
			params.put("aprislPrc1", aprislDto.getAprislPrc1());
			hql.append(" and aprisl.aprislPrc >= :aprislPrc1");
		}
		if(aprislDto.getAprislPrc2() != null){
			params.put("aprislPrc2", aprislDto.getAprislPrc2());
			hql.append(" and aprisl.aprislPrc <= :aprislPrc2");
		}
		
		hql.append(" and aprisl.markForDel='V' ");
		hql.append(" order by aprisl.id desc ");
		
		page = aprislDao.pagedQuery(hql.toString(), page, params);
		return page.getData();
	}

	//修改价格鉴定
	@SuppressWarnings("unchecked")
	@Override
	public void updateAprisl(AprislDto aprislDto, JSONArray expArray,Long[] ids) {
		TAprislArtclsInfo aprisl = (TAprislArtclsInfo) aprislDao.getObject(TAprislArtclsInfo.class, aprislDto.getId());
		aprislDao.removeAllObjects(aprisl.getTCmsArtclsAccesorses());
		aprislDao.removeAllObjects(aprisl.getTPrfrncPrcLib());
		aprisl.setTCmsArtclsAccesorses(null);
		aprisl.setTPrfrncPrcLib(null);
		
		this.copyProperties(aprisl,aprislDto);

		//鉴定物品附件
		int isize = expArray.length();
		List<TCmsArtclsAccesors> tCmsArtclsAccesorsList = new ArrayList<TCmsArtclsAccesors>();
		String amnt = null;
		for(int i =0;i <isize; i ++ ){
			JSONObject jsonobj = expArray.getJSONObject(i);
			//配件
			//id
			TCmsArtclsAccesors tCmsArtcls= new TCmsArtclsAccesors();
			tCmsArtcls.setTAprislArtclsInfo(aprisl);
			//配件名称
			tCmsArtcls.setNm(jsonobj.getString("name"));
			//配件备注
			tCmsArtcls.setRemark(jsonobj.getString("remark"));
			//配件鉴定价格
			amnt = jsonobj.getString("valutnAmnt");
			tCmsArtcls.setValutnAmnt(StringUtils.isBlank(amnt) ? null : Double.valueOf(amnt));
			
			tCmsArtcls.setMarkForDel(Constants.MARK_FOR_DELETE_NO);
			tCmsArtclsAccesorsList.add(tCmsArtcls);
		}
		if (tCmsArtclsAccesorsList.size() > 0) {
			aprislDao.saveObjectList(tCmsArtclsAccesorsList);
			aprisl.setTCmsArtclsAccesorses(tCmsArtclsAccesorsList);
		}

		//鉴定物品参考价格库
		List<TPrfrncPrcLib> tPrfrncPrcLibList = new ArrayList<TPrfrncPrcLib>();
		if(null !=ids){
			for(int i=0;i<ids.length;i++){
				TPrfrncPrcLib prcLib = new TPrfrncPrcLib();
				TMiningPriceInfo mp = new TMiningPriceInfo();
				mp.setId( ids[i]);
				prcLib.setTMiningPriceInfo(mp);
				prcLib.setTAprislArtclsInfo(aprisl);
				tPrfrncPrcLibList.add(prcLib);
			}
		}
		if (tPrfrncPrcLibList.size()>0) {
			aprislDao.saveObjectList(tPrfrncPrcLibList);
			aprisl.setTPrfrncPrcLib(tPrfrncPrcLibList);
		}
		aprisl = (TAprislArtclsInfo) aprislDao.saveObject(aprisl);
	}

	private void copyProperties(TAprislArtclsInfo aprisl, AprislDto aprislDto) {
		// TODO Auto-generated method stub
		//if(null!=aprislDto.getAprislPrc()){
			aprisl.setAprislPrc(aprislDto.getAprislPrc());
		//}
		aprisl.setPrcAprislMtd(aprislDto.getPrcAprislMtd());
		aprisl.setAprislSt(aprislDto.getAprislSt());
		aprisl.setArtclNm(aprislDto.getArtclNm());
		aprisl.setArtclTp(aprislDto.getArtclTp());
		aprisl.setBlAdrs(aprislDto.getBlAdrs());
		aprisl.setBlOwnrshpCertNo(aprislDto.getBlOwnrshpCertNo());
		if(null!=aprislDto.getQnty()){
			aprisl.setQnty(aprislDto.getQnty());
		}
		aprisl.setUnit(aprislDto.getUnit());
		aprisl.setBrndNm(aprislDto.getBrndNm());
		aprisl.setSpecTp(aprislDto.getSpecTp());
		if(null!=aprislDto.getOrgnlPrc()){
			aprisl.setOrgnlPrc(aprislDto.getOrgnlPrc());
		}
		aprisl.setPrcArtclRefDt(aprislDto.getPrcArtclRefDt());
		//if(null!=aprislDto.getNewRate()){
			aprisl.setNewRate(aprislDto.getNewRate());
		//}
		aprisl.setBuyTm(aprislDto.getBuyTm());
		aprisl.setIsNmlUse(aprislDto.getIsNmlUse());
		aprisl.setIsLos(aprislDto.getIsLos());
		aprisl.setIsMjrChng(aprislDto.getIsMjrChng());
		aprisl.setOthInfo(aprislDto.getOthInfo());
		aprisl.setPrcArtclRmk(aprislDto.getPrcArtclRmk());
		aprisl.setRemark(aprislDto.getRemark());
		aprisl.setMblImeiTp(aprislDto.getMblImeiTp());
		aprisl.setMblNtwkLcns(aprislDto.getMblNtwkLcns());
		aprisl.setVcPlNm(aprislDto.getVcPlNm());
		aprisl.setVcFrmNm(aprislDto.getVcFrmNm());
		aprisl.setVcEgnNm(aprislDto.getVcEgnNm());
		aprisl.setVcCarClr(aprislDto.getVcCarClr());
		aprisl.setVcInitRegDt(aprislDto.getVcInitRegDt());
		aprisl.setVcTotlTrip(aprislDto.getVcTotlTrip());
		aprisl.setVcUseNtr(aprislDto.getVcUseNtr());
		aprisl.setVcLosFuelKnd(aprislDto.getVcLosFuelKnd());
		aprisl.setVcLosStruct(aprislDto.getVcLosStruct());
		aprisl.setVcLosInitRegDt(aprislDto.getVcLosInitRegDt());
		aprisl.setVcLosAnnPassDt(aprislDto.getVcLosAnnPassDt());
		aprisl.setVcLosPymntStatn(aprislDto.getVcLosPymntStatn());
		aprisl.setVcLosBuyAdrs(aprislDto.getVcLosBuyAdrs());
		aprisl.setVcLosArtclRefDtSt(aprislDto.getVcLosArtclRefDtSt());
		aprisl.setJjcSap(aprislDto.getJjcSap());
		aprisl.setJjcSize(aprislDto.getJjcSize());
		aprisl.setJjcWght(aprislDto.getJjcWght());
		aprisl.setJjcClr(aprislDto.getJjcClr());
		aprisl.setJjcGrd(aprislDto.getJjcGrd());
		if(null!=aprislDto.getJjcFnns()){
			aprisl.setJjcFnns(aprislDto.getJjcFnns());
		}
		aprisl.setJjcIsQltArtclCert(aprislDto.getJjcIsQltArtclCert());
		aprisl.setJjcCertRprtNo(aprislDto.getJjcCertRprtNo());
		aprisl.setMfArea(aprislDto.getMfArea());
		aprisl.setMfBuyAdd(aprislDto.getMfBuyAdd());
	}

}
