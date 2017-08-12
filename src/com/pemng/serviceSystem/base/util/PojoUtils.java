package com.pemng.serviceSystem.base.util;

import java.beans.Beans;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



public class PojoUtils {
	
	/**
	 * 将Pojo转换为对应的DTO
	 * 
	 * @param clazz
	 *            DTO的class
	 * @param pojo
	 * @return
	 */
	public static Object convert2Dto(Class clazz, Object pojo) {
		if (pojo == null) {
			return null;
		}

//		if (Beans.isInstanceOf(pojo, clazz)) {
//			throw new RuntimeException("command not an instance of "
//					+ clazz.getName());
//		}

		Object dto = null;

		try {
			dto = clazz.newInstance();
			WSBeanUtils.copyProperties(dto, pojo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return dto;
	}
	
	/**
	 * 将POJO list 转换为 DTO list
	 * @param clazz
	 * 				DTO的class
	 * @param list
	 * @return
	 */
	public static List convertPoList2DtoList(Class clazz, List<?> list){
		List<Object> targetList = new ArrayList<Object>();
		
		if(null==list){
			return null;
		}
		
		for(Object o:list){
			targetList.add(convert2Dto(clazz, o));
		}
		return targetList;
	}

	/**
	 * 将DTO转换为对应的Pojo
	 * 
	 * @param dto
	 * @return
	 */
	public static Object convert2Pojo(Object dto) {
		if (dto == null) {
			return null;
		}

		Class clazz = dto.getClass().getSuperclass();
		Object pojo = null;

		try {
			pojo = clazz.newInstance();
			WSBeanUtils.copyProperties(pojo, dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return pojo;
	}

	/**
	 * 将Dto List 转换为 POJO List
	 * @param dtoList	Dto List
	 */
	public static List convertDtoList2PojoList(List<?> dtoList) {
		List<Object> dataList = new ArrayList();
		for(Object o:dtoList){
			dataList.add(convert2Pojo(o));
		}
		return dataList;
	}

	/**
	 * 将POJO Set 转换 为 DTO Set
	 * @param pojoSet	POJO Set
	 * @param clazz		Dto class
	 * @return
	 */
	public static List<? extends Class> convertPojoSet2DtoList(Set<?> pojoSet, Class clazz) {
		if(null==pojoSet){
			return null;
		}
		List dtoList = new ArrayList();
		for(Iterator<?> iter=pojoSet.iterator(); iter.hasNext();){
			dtoList.add(convert2Dto(clazz, iter.next()));
		}
		return dtoList;
	}

	/**
	 * 获得Bean数据
	 * @param obj Bean对象
	 * @param methodName 所要获取数据的方法名
	 * @return 返回数据
	 */
	public static Object getBean(Object obj,String methodName){
		Class<?> objClass=obj.getClass();
		Method[] ms=objClass.getMethods();
		for(Method m:ms){
			if(m.getName().equalsIgnoreCase(methodName)){
				try {
					Object result=m.invoke(obj,null);
					return result;
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
