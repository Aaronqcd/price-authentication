package com.pemng.common.util;

import java.math.BigDecimal;

public class TradeUtil {
	
	public static String getPercentValue(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null") ||inStr.equals("&nbsp;")|| inStr.trim().length()==0){
			return value;
		}
		
		value = new BigDecimal(inStr).multiply(new BigDecimal(100)).setScale(4,BigDecimal.ROUND_HALF_UP).toString();
		return value;
	}

	public static String getWanYuanValue(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")||inStr.equals("&nbsp;")|| inStr.trim().length()==0){
			return value;
		}
		
		value = new BigDecimal(inStr.trim()).divide(new BigDecimal(10000),6,BigDecimal.ROUND_HALF_UP).toString();
		return value;
	}
	
	public static String getPercentValue2(String inStr){
		String value = "-";
		if(inStr==null || inStr.equals("null") ||inStr.equals("&nbsp;")|| inStr.trim().length()==0){
			return value;
		}
		
		value = new BigDecimal(inStr).multiply(new BigDecimal(100)).setScale(4,BigDecimal.ROUND_HALF_UP).toString();
		return value;
	}

	public static String getWanYuanValue2(String inStr){
		String value = "-";
		if(inStr==null || inStr.equals("null")||inStr.equals("&nbsp;")|| inStr.trim().length()==0){
			return value;
		}
		
		value = new BigDecimal(inStr.trim()).divide(new BigDecimal(10000),6,BigDecimal.ROUND_HALF_UP).toString();
		return value;
	}
	
	public static String getTradeTypeName(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")){
			return value;
		}
		if(inStr.equals("1")){ 
			value = "整体转让";
		}else if(inStr.equals("2")){
			value = "可拆分转让";
		}
		return value;
	}
	
	public static String getTradeManageOrgName(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")){
			return value;
		}
		if(inStr.equals("1")){ 
			value = "出让方";
		}else if(inStr.equals("2")){
			value = "受让方";
		}else if(inStr.equals("3")){
			value = "其他";
		}
		return value;
	}
	
	
	
	public static String getTradeManageOrgNameForPrint(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")){
			return value;
		}
		if(inStr.equals("1")){ 
			value = "出让方";
		}else if(inStr.equals("2")){
			value = "受让方";
		}else if(inStr.equals("3")){
			value = "见委托代理合同";
		}
		return value;
	}
	
	public static String getTradeArgSolveWayName(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")){
			return value;
		}
		if(inStr.equals("0")){ 
			value = "无";
		}else if(inStr.equals("1")){
			value = "仲裁";
		}else if(inStr.equals("2")){
			value = "诉讼";
		}
		return value;
	}
	
	public static String getTradeInitPayTypeName(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")){
			return value;
		}
		if(inStr.equals("0")){ 
			value = "&nbsp;";
		}else if(inStr.equals("1")){
			value = "利随本清";
		}else if(inStr.equals("2")){
			value = "付息期末结算";
		}
		return value;
	}
	
	public static String getTradeInitPayTypeName2(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")){
			return value;
		}
		if(inStr.equals("0")){ 
			value = "-";
		}else if(inStr.equals("1")){
			value = "利随本清";
		}else if(inStr.equals("2")){
			value = "付息期末结算";
		}
		return value;
	}
	
	
	public static String getTradeDealStateTypeName(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")){
			return value;
		}
		inStr = inStr.trim();
		if(inStr.equals("1")){
			value = "全部成交";
		}else if(inStr.equals("2")){
			value = "部分成交";
		}else if(inStr.equals("3")){
			value = "进行中";
		}else if(inStr.equals("4")){
			value = "已关闭";
		}else if(inStr.equals("5")){
			value = "待复核";
		}else if(inStr.equals("6")){ 
			value = "等待其他受让方确认";
		}else{ 
			value = "";
		} 
		return value;
	}
	
	
	public static String getTradeStateTypeName(String inStr){
		String value = "";
		if(inStr==null || inStr.equals("null")){
			return value;
		}
	    inStr = inStr.trim();
		if(inStr.equals("1")){
			value = "待复核";
		}else if(inStr.equals("2")){
			value = "等待对方确认";
		}else if(inStr.equals("3")){
			value = "等待本方确认";
		}else if(inStr.equals("4")){
			value = "已接受";
		}else if(inStr.equals("5")){
			value = "已拒绝";
		}else if(inStr.equals("6")){ 
			value = "超时未确认";
		}else if(inStr.equals("7")){ 
			value = "已撤销";
		}else if(inStr.equals("8")){ 
			value = "未通过";
		}else{ 
			value = "";
		} 
		return value;
	}
	
}
