package com.pemng.serviceSystem.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * Created on Apr 25, 2012
 * <p>Description: 对列表排序，支持多属性排序，但只支持单个bean中的属性排序</p>
 * @version        1.0
 */
public class BeanComparator<T> implements Comparator<T> {

	private SortRule[] sortRules = null;

	public BeanComparator(SortRule[] sortRules){
		this.sortRules = sortRules;
	}

	public int compare(Object arg0, Object arg1) {
		int compareResult = 0;
		// 如果没有排序规则，默认不排序
		if(sortRules == null || sortRules.length <= 0)
			return compareResult;

		int ruleIndex = 0;
		try{
		while(compareResult == 0){
			if(ruleIndex > sortRules.length - 1)
				break;
			String compName = sortRules[ruleIndex].getParam();
			
			PropertyDescriptor pd = new PropertyDescriptor(compName, arg0.getClass());
			Method getMethod = pd.getReadMethod();
			// 获取需要比对的值
			Object o1 = getMethod.invoke(arg0);
			Object o2 = getMethod.invoke(arg1);
			// 比对
			if(o1 != null){
				if(o1 instanceof Comparable){
					compareResult = ((Comparable)o1).compareTo((Comparable)o2);
				} else{ 
					// 对于不可比较的对象，不需要排序
				}
			}else{
				compareResult = 1;
			}
			if(sortRules[ruleIndex].getDirection().equals("DESC"))
				compareResult *= -1;
			ruleIndex ++;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return compareResult;
	}
	public static void main(String [] args){
	}
}
