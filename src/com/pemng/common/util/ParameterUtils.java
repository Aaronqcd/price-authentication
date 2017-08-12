package com.pemng.common.util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.pemng.serviceSystem.base.util.DateUtils;
import com.pemng.serviceSystem.base.util.FilePathCfgUtil;


public class ParameterUtils {

	/**
	 * 传递一个对象得到一个map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> convertToMap(Object obj) {
		try {
			if(obj == null) {
				return null;
			}
			Map<String, Object> result = new HashMap<String, Object>();
			//自己本身的
			Method[] methods = obj.getClass().getDeclaredMethods();

			if(obj.getClass().getSuperclass()!=null){//父类的方法处理
				Method[] superMethods = obj.getClass().getSuperclass().getDeclaredMethods();
				if(superMethods!=null){
					for(Method method : superMethods) {
						String name = method.getName();
						if(StringUtils.isNotBlank(name) && name.startsWith("get")) {
							Object attrVal = method.invoke(obj);
							if(attrVal != null && StringUtils.isNotEmpty(attrVal.toString())) {
								String attrName = StringUtils.uncapitalize(name.substring(3));
								if(StringUtils.equals("class", attrName)) { // getClass()方法排除在外
									continue;
								}
								if(attrName.indexOf("beginDate") >= 0) {
									result.put(attrName, DateUtils.parse(attrVal.toString() + " 00:00:00", DateUtils.YYYYMMDDHHMMSS_FORMAT)); // 转成Date类型
								} else if(attrName.indexOf("endDate") >= 0) {
									result.put(attrName, DateUtils.parse(attrVal.toString() + " 23:59:59", DateUtils.YYYYMMDDHHMMSS_FORMAT)); // 转成Date类型
								} else {
									result.put(attrName, attrVal.toString());
								}
							}
						}
					}
				}
			}
			//自己本身的方法
			for(Method method : methods) {
				String name = method.getName();
				if(StringUtils.isNotBlank(name) && name.startsWith("get")) {
					Object attrVal = method.invoke(obj);
					if(attrVal != null && StringUtils.isNotEmpty(attrVal.toString())) {
						String attrName = StringUtils.uncapitalize(name.substring(3));
						if(attrName.indexOf("beginDate") >= 0) {
							result.put(attrName, DateUtils.parse(attrVal.toString() + " 00:00:00", DateUtils.YYYYMMDDHHMMSS_FORMAT)); // 转成Date类型
						} else if(attrName.indexOf("endDate") >= 0) {
							result.put(attrName, DateUtils.parse(attrVal.toString() + " 23:59:59", DateUtils.YYYYMMDDHHMMSS_FORMAT)); // 转成Date类型
						} else {
							result.put(attrName, attrVal.toString());
						}
					}
				}
			}
			
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * 传递一个对象得到一个map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> convertToObjectMap(Object obj) {
		try {
			if(obj == null) {
				return null;
			}
			Map<String, Object> result = new HashMap<String, Object>();
			//自己本身的
			Method[] methods = obj.getClass().getDeclaredMethods();

			if(obj.getClass().getSuperclass()!=null){//父类的方法处理
				Method[] superMethods = obj.getClass().getSuperclass().getDeclaredMethods();
				if(superMethods!=null){
					for(Method method : superMethods) {
						String name = method.getName();
						if(StringUtils.isNotBlank(name) && name.startsWith("get")) {
							Object attrVal = method.invoke(obj);
							if(attrVal != null && StringUtils.isNotEmpty(attrVal.toString())) {
								String attrName = StringUtils.uncapitalize(name.substring(3));
								if(attrName.indexOf("beginDate") >= 0) {
									result.put(attrName, DateUtils.parse(attrVal.toString() + " 00:00:00", DateUtils.YYYYMMDDHHMMSS_FORMAT)); // 转成Date类型
								} else if(attrName.indexOf("endDate") >= 0) {
									result.put(attrName, DateUtils.parse(attrVal.toString() + " 23:59:59", DateUtils.YYYYMMDDHHMMSS_FORMAT)); // 转成Date类型
								} else {
									result.put(attrName, attrVal);
								}
							}
						}
					}
				}
			}
			//自己本身的方法
			for(Method method : methods) {
				String name = method.getName();
				if(StringUtils.isNotBlank(name) && name.startsWith("get")) {
					Object attrVal = method.invoke(obj);
					if(attrVal != null && StringUtils.isNotEmpty(attrVal.toString())) {
						String attrName = StringUtils.uncapitalize(name.substring(3));
						if(attrName.indexOf("beginDate") >= 0) {
							result.put(attrName, DateUtils.parse(attrVal.toString() + " 00:00:00", DateUtils.YYYYMMDDHHMMSS_FORMAT)); // 转成Date类型
						} else if(attrName.indexOf("endDate") >= 0) {
							result.put(attrName, DateUtils.parse(attrVal.toString() + " 23:59:59", DateUtils.YYYYMMDDHHMMSS_FORMAT)); // 转成Date类型
						} else {
							result.put(attrName, attrVal);
						}
					}
				}
			}
			
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 得到拼接的hql
	 * @param hql
	 * @param prefix		这个参数是表的查询重命名,如果为空则先传递空字符串
	 * @param params		
	 * @return
	 */
	public static String combinaHQL(String hql, String prefix, String dateAttr, Map<String, ?> params, String[] likeParams) {
		StringBuffer result = new StringBuffer("");
		result.append(hql);
		for(Map.Entry<String, ?> maps : params.entrySet()) {
			String key = maps.getKey();
			if(StringUtils.isNotBlank(dateAttr)) {
				if(StringUtils.equals("beginDate", key) || key.indexOf("Start") >= 0) {
					result.append(" and " + prefix + dateAttr + " >= :" + key);
					continue;
				} else if(StringUtils.equals("endDate", key) || key.indexOf("End") >= 0) {
					result.append(" and " + prefix + dateAttr + " <= :" + key);
					continue;
				}
			}
			if(likeParams !=null && ArrayUtils.contains(likeParams, key)) {
				result.append(" and " + prefix + key + " Like '%" + key + "%' ");
			} else {
				result.append(" and " + prefix + key + " = :" + key);
			}
			
		}
		result.append(" order by  crtTime desc ");
		return result.toString();
	}
	
	public static Criterion[] buildCriterion(Map<String, Object> params) {
		Criterion[] result = new Criterion[params.size()];
		String[] keys = new String[2];
		String attr = "";
		Object value = null;
		int i = 0;
		for(Map.Entry<String, Object> maps : params.entrySet()) {
			String key = maps.getKey();
			value = maps.getValue();
			if(key.indexOf("_") >= 0 && value != null) {
				keys = StringUtils.split(key, '_');
				if(StringUtils.equals("eq", attr)) {
					result[i] = Restrictions.eq(keys[1] + "", value);
				} else if(StringUtils.equals("like", attr)) {
					result[i] = Restrictions.like(keys[1], value.toString(), MatchMode.ANYWHERE);
				}
				i ++;
			}
			
		}
		return result;
	}
	
	/**
	 * 根据list 和 key得到list中对象对应key的值  -- 可以利用struts2页面标签进行调用输出.
	 * 
	 * example: <s:property value="@com.pemng.common.util.ParameterUtils@findValueFromList(repairTypeList, engineType)"/>
	 * 
	 * @param list
	 * @param key
	 * @return
	 *//*
	public static String findValueFromList(List<BasicDataCnt> list, String key) {
		String result = "";
		if(list == null || list.size() <= 0) {
			return result;
		}
		for(BasicDataCnt basicDataCnt: list) {
			String cacheListKey = basicDataCnt.getTheKey();
			if(StringUtils.equalsIgnoreCase(key, cacheListKey)) {
				result = basicDataCnt.getValue();
				break;
			}
		}
		return result;
	}*/
	
	/**
	 * 将null转空串
	 * @param str
	 * @return
	 */
	public static String changeNullToEmpty(String str) {
		if(StringUtils.isBlank(str)) {
			return "";
		}
		return str;
	}
	
	/**
	 * 查询参数的返回Criterion条件组合
	 * @param request
	 * @param prefix
	 * @return
	 */
	public static Criterion[] getParameters(ServletRequest request, String prefix) {
		try {
			List<Criterion> result = new ArrayList<Criterion>();
			Enumeration<?> paramNames = request.getParameterNames();
			
			String attrVal = null;
			while(paramNames != null && paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				if("".equals(prefix) || paramName.startsWith(prefix)) {
					String[] values = request.getParameterValues(paramName);
					if(values == null || values.length == 0 || values[0] == null) {
						continue;
					}
					
					if(values.length > 1) {
						attrVal = values.toString();
					} else {
						attrVal = values[0].toString();
					}
					
					String[] regexAndVal = StringUtils.split(paramName, '_'); // filter + regex + attrname
					String regex = regexAndVal[1];
					String attrName = regexAndVal[2];
					
					if(StringUtils.equals("EQ", regex)) {
						result.add(Restrictions.eq(attrName, attrVal));
					} else if(StringUtils.equals("LIKE", regex)) {
						result.add(Restrictions.like(attrName, attrVal, MatchMode.ANYWHERE));
					} else if(StringUtils.equals("GE", regex)) {
						if(attrName.startsWith("beginDate")) {
							result.add(Restrictions.ge(attrName, Restrictions.ge(attrName, DateUtils.parse(attrVal + " 00:00:00", DateUtils.YYYYMMDDHHMMSS_FORMAT))));
						} else {
							result.add(Restrictions.ge(attrName, attrVal));
						}
					} else if(StringUtils.equals("LE", regex)) {
						if(attrName.startsWith("endDate")) {
							result.add(Restrictions.le(attrName, DateUtils.parse(attrVal + " 23:59:59", DateUtils.YYYYMMDDHHMMSS_FORMAT)));
						} else {
							result.add(Restrictions.le(attrName, attrVal));
						}
					}
					
				}
			}
			
			if(result == null || result.size() <= 0) {
				return  null;
			}

			int listSize = result.size();
			Criterion[] criterions = new Criterion[listSize];
			for(int i=0; i<listSize; i++) {
				criterions[i] = result.get(i);
			}
			
			result.clear();
			
			return criterions;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 对文件重命名还是删除该文件
	 * @param rename
	 * @throws RuntimeException
	 */
	/*public static void renameOrDelete(boolean rename, String repairNo, String slefRepairNo) throws RuntimeException {
		File rootFile = new File(FilePathCfgUtil.getAuditdir());
		if(rootFile.exists()) {
			File[] directoryFiles = rootFile.listFiles();
			for(File directory: directoryFiles) {
				File[] innerDirectory = directory.listFiles();
				for(File innerFile: innerDirectory) {
					if(innerFile.isDirectory() && StringUtils.equals(slefRepairNo, innerFile.getName())) {
						if(rename) {
							String fileName = innerFile.getAbsolutePath().replaceAll(slefRepairNo, repairNo);
							innerFile.renameTo(new File(fileName));
						} else {
							innerFile.delete();
						}
						break;
					}
				}
			}
		}
	}*/
}
