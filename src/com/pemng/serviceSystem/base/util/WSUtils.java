package com.pemng.serviceSystem.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.jfree.util.Log;

import com.pemng.serviceSystem.basicdata.services.BasicDataMangerService;
import com.pemng.serviceSystem.common.BeanComparator;
import com.pemng.serviceSystem.common.SortRule;
import com.pemng.serviceSystem.pojo.TBasicDataCnt;

public class WSUtils extends BeanUtils {

	private BasicDataMangerService basicDataMangerService;
	private static Map<String,Map<String,TBasicDataCnt>> basicDataMap;
	
	public BasicDataMangerService getBasicDataMangerService() {
		return basicDataMangerService;
	}
	public void setBasicDataMangerService(
			BasicDataMangerService basicDataMangerService) {
		this.basicDataMangerService = basicDataMangerService;
	}
	public void init(){
		//初始化基础数据
		basicDataMap = basicDataMangerService.getAllDataMap();
		Log.info("初始化基础数据....");
	}
	/**
	 * 返回数据字典键值对象MAP
	 * @param code
	 * @return
	 */
	public static Map<String,TBasicDataCnt> getBasicDataMap(String code){
		Map<String,TBasicDataCnt> map =basicDataMap.get(code);
		if(map==null){
			map = new HashMap<String,TBasicDataCnt>();
		}
		return map;
	}
	/**
	 * 返回数据字典键值对象List(按key排序)
	 * @param code
	 * @return
	 */
	public static List<TBasicDataCnt> getBasicDataList(String code){
		Map<String,TBasicDataCnt> map = basicDataMap.get(code);
		if(map!=null){
			List<TBasicDataCnt> list = new ArrayList<TBasicDataCnt>();
			list.addAll(map.values());
			SortRule[] sortRules = new SortRule[]{new SortRule("theKey","ASC")};
			Collections.sort(list, new BeanComparator<TBasicDataCnt>(sortRules));
			return list;
		}
		return new ArrayList();
	}
	/**
	 * 返回数据字典键值对象
	 * @param code
	 * @param key
	 * @return
	 */
	public static TBasicDataCnt getBasicData(String code,String key){
		Map<String,TBasicDataCnt> map = basicDataMap.get(code);
		if(map!=null){
			return map.get(key);
		}
		
		return new TBasicDataCnt();
	}

	public static void setBasicData(String code,Map<String,TBasicDataCnt> newMap){
		basicDataMap.put(code, newMap);
	}
	
	
	public static void resetDataMap(Map<String,Map<String,TBasicDataCnt>>  newMap){
		basicDataMap.clear();
		basicDataMap.putAll(newMap);
	}
}
