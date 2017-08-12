package com.pemng.common.util;

import java.util.HashMap;
import java.util.Map;

public class SelectComponentUtil {

   	public static void buildSelectOptionValues(Object[] array, Map valueMap){
		int mapLength = array.length/2;
		for(int i=0; i<mapLength; i++){
			String key = String.valueOf(array[i*2]) + ":" + String.valueOf(array[i*2+1]);
			Map innerMap = (Map)valueMap.get(key);
			if(innerMap == null){
				innerMap = new HashMap(3); //初始化默认3个单元
				valueMap.put(key, innerMap);
			}
			valueMap = innerMap;
		}
	}
   	
}
