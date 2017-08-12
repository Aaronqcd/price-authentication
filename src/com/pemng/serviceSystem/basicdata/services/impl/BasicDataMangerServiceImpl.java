package com.pemng.serviceSystem.basicdata.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.WSUtils;
import com.pemng.serviceSystem.basicdata.dao.BasicDataCntDao;
import com.pemng.serviceSystem.basicdata.dao.BasicDataDao;
import com.pemng.serviceSystem.basicdata.dto.BasicDataDto;
import com.pemng.serviceSystem.basicdata.services.BasicDataMangerService;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.pojo.TBasicData;
import com.pemng.serviceSystem.pojo.TBasicDataCnt;

public class BasicDataMangerServiceImpl extends BaseServiceImpl implements BasicDataMangerService{
	private  BasicDataDao basicDataDao;
	private  BasicDataCntDao basicDataCntDao;

	@Override
	public TBasicDataCnt addBasicData(TBasicDataCnt basicData){
		return (TBasicDataCnt) basicDataCntDao.saveObject(basicData);
	}

	@Override
	public TBasicDataCnt queryBasic(long id){
		return (TBasicDataCnt) basicDataCntDao.getObject(id);
	}
	
	@Override
	public void modifyBasicData(TBasicDataCnt basicData){
		basicDataCntDao.updateObjectOnly(basicData);
	}
	
	public void deleteBasicData(List basicDataList){
		basicDataCntDao.saveObjectList(basicDataList);
	}
	
	@Override
	public List checkTheKey(String key,Long dataId,Long basicId){
		String hql="from TBasicDataCnt b where b.theKey =:theKey and b.TBasicData.id = :dataId and b.markForDel = 'V'";
		Map params=new HashMap();
		params.put("theKey",key);
		params.put("dataId",dataId);
		if(basicId != null){
			hql += " and b.id != :basicId";
			params.put("basicId",basicId);
		}
		return basicDataDao.findObjectsByHql(hql,params);
	}
	
	@Override
	public List basicDataCntList(Pager pager, BasicDataDto basicDataDto) {
		String hql = " from TBasicDataCnt d where 1=1 and markForDel = '"+Constants.MARK_FOR_DELETE_NO+"'";
		List values = new ArrayList();
		if(StringUtils.isNotEmpty(basicDataDto.getTheKey())){
			hql += " and d.theKey like '%"+basicDataDto.getTheKey()+"%'";
		}
		if(StringUtils.isNotEmpty(basicDataDto.getValue())){
			hql += " and d.value like '%"+basicDataDto.getValue()+"%'";
		}
		if(basicDataDto.getId()!=null){
			hql += " and d.TBasicData.id = "+basicDataDto.getId();
		}
		hql += " order by id desc";
		return basicDataDao.pagedQuery(hql, pager, values.toArray(new Object[values.size()])).getData();
	}

	@Override
	public List basicDataList(Pager pager,BasicDataDto basicDataDto) {
		String hql ="  from TBasicData where 1=1  ";
		List values = new ArrayList();
		if(StringUtils.isNotEmpty(basicDataDto.getRemark())){
			hql += " and remark like ? ";
			values.add("%"+basicDataDto.getRemark().trim()+"%");
		}
		if(StringUtils.isNotEmpty(basicDataDto.getCode())){
			hql += " and theCode like ? ";
			values.add("%"+basicDataDto.getCode().trim()+"%");
		}
		hql += "order by theCode ";
		return basicDataCntDao.pagedQuery(hql, pager, values.toArray(new Object[values.size()])).getData();
	}

	public BasicDataCntDao getBasicDataCntDao() {
		return basicDataCntDao;
	}

	public void setBasicDataCntDao(BasicDataCntDao basicDataCntDao) {
		this.basicDataCntDao = basicDataCntDao;
	}

	@Override
	public Map<String, Map<String, TBasicDataCnt>> getAllDataMap() {
		Map<String, Map<String, TBasicDataCnt>> rtMap = new HashMap<String, Map<String, TBasicDataCnt>>();
		String hql = " from TBasicDataCnt d where markForDel = '"+Constants.MARK_FOR_DELETE_NO+"' order by d.TBasicData.id";
		List<TBasicDataCnt> list = basicDataCntDao.findObjectsByHql(hql, new HashMap<String, String>() );
		for (TBasicDataCnt basicDataCnt : list) {
			String theCode = basicDataCnt.getTBasicData().getTheCode();
			Map<String, TBasicDataCnt>  codeMap = rtMap.get(theCode);
			if (codeMap == null) {
				codeMap = new HashMap<String, TBasicDataCnt>();
				rtMap.put(theCode, codeMap);
			}
			String theKey = basicDataCnt.getTheKey();
			TBasicDataCnt keyVal = codeMap.get(theKey);
			if (keyVal == null) {
				codeMap.put(theKey, basicDataCnt);
			}
		}
		return rtMap;
	}
	
	public void saveBasicData(Long basicDataId, List<TBasicDataCnt> basicDataList){
		basicDataCntDao.saveObjectList(basicDataList);
		TBasicData basicData = (TBasicData) basicDataCntDao.getObject(TBasicData.class, basicDataId);
		if(basicDataList!=null && basicDataList.size()>0){
			Map<String, TBasicDataCnt> rtMap = new HashMap<String, TBasicDataCnt>();
			for (TBasicDataCnt basicDataCnt : basicDataList) {
				rtMap.put(basicDataCnt.getTheKey(), basicDataCnt);
			}
			//重新set数据字典
			WSUtils.setBasicData(basicData.getTheCode(), rtMap);
		}else{
			//重新set数据字典
			WSUtils.setBasicData(basicData.getTheCode(), null);
		}
	}
	
	public BasicDataDao getBasicDataDao() {
		return basicDataDao;
	}

	public void setBasicDataDao(BasicDataDao basicDataDao) {
		this.basicDataDao = basicDataDao;
	}

	
}