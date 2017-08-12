package com.pemng.serviceSystem.common.utils;

import java.util.HashMap;
import java.util.Map;

public class PeUtil {
	
	static Map<String, String> cmsTpMap = new HashMap<String, String>();
	
	static Map<String, String> stMap = new HashMap<String, String>();
	
	
	
	static{
		//委托书类型
		cmsTpMap.put("1", "价格鉴定委托书");
		cmsTpMap.put("2", "价格认定委托书");
		cmsTpMap.put("3", "复核裁定委托书");
		cmsTpMap.put("4", "原鉴定结论委托书");
		
		//委托书状态名称
		stMap.put("01", "待受理");
		stMap.put("02", "补充材料");
		stMap.put("03", "已受理");
		stMap.put("04", "补充材料");
		stMap.put("05", "拒绝受理	");
		stMap.put("06", "终止受理");
		stMap.put("07", "待初审");
		stMap.put("08", "初审驳回");
		stMap.put("09", "待复审");
		stMap.put("10", "复审驳回");
		stMap.put("11", "待终审");
		stMap.put("12", "终审驳回");
		stMap.put("13", "送达");
		stMap.put("14", "审批通过");
		stMap.put("1", "待受理");
		stMap.put("2", "补充材料");
		stMap.put("3", "已受理");
		stMap.put("4", "补充材料");
		stMap.put("5", "拒绝受理");
		stMap.put("6", "终止受理");
		stMap.put("7", "待初审");
		stMap.put("8", "初审驳回");
		stMap.put("9", "待复审");
	}
	
	/**
	 * 获取委托书类型名称
	 * @return
	 */
	public static String getTpStr(String tp){
		tp = null != tp?tp.trim():"";
		if(cmsTpMap.containsKey(tp))
			return cmsTpMap.get(tp);
		return "";
	}
	
	/**
	 * 获取委托书状态名称
	 * @return
	 */
	public static String getStStr(String cmsTp){
		if(stMap.containsKey(cmsTp))
			return stMap.get(cmsTp);
		return "";
	}
	
	/**
	 * 是否归档
	 * @return
	 */
	public static String getAchivStr(String achiv){
		String strVal = "";
		if (null == achiv) {
			return strVal;
		}
		if (achiv.equals("0")) {
			strVal = "未归档";
		}else if(achiv.equals("1")){
			strVal = "已归档";
		}
		return strVal;
	}
	
}
