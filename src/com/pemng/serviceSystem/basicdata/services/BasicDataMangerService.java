package com.pemng.serviceSystem.basicdata.services;

import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.basicdata.dto.BasicDataDto;
import com.pemng.serviceSystem.pojo.TBasicDataCnt;

public interface BasicDataMangerService extends BaseService{
	
	public TBasicDataCnt addBasicData(TBasicDataCnt basicData);
	
	public List basicDataList(Pager pager,BasicDataDto basicDataDto);
	
	public List basicDataCntList(Pager pager,BasicDataDto basicDataDto);
	
	public TBasicDataCnt queryBasic(long id);
	
	public void modifyBasicData(TBasicDataCnt basicData);
	
	public void deleteBasicData(List basicDataList) ;
	
	public List checkTheKey(String key,Long dataId,Long basicId);
	
	/**
	 * 返回所有数据字典
	 * @return Map<code,Map<key,BasicDataCnt>>
	 */
	public Map<String,Map<String,TBasicDataCnt>> getAllDataMap();
	
	/**
	 * 保存类别
	 * @param basicDataList
	 */
	public void saveBasicData(Long basicDataId,List<TBasicDataCnt> basicDataList);
}
