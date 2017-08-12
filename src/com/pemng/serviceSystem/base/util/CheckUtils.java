package com.pemng.serviceSystem.base.util;


/**
 * Created on May 21, 2012
 * <p>Description: 校验基本的表单信息</p>
 * @version        1.0
 */
public class CheckUtils {
	private static String postCodeRex="[1-9]\\d{5}(?!\\d)";	//邮编
	private static String faxRex="([0-9]{3,4})+-([0-9]{7,8})+(-[0-9]{2,4})?+";//传真 座机-----这个不行啊 TMD
	private static String phoneRex1= "\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)";	//移动电话
	private static String phoneRex2 = "^[1][3,5]+\\d{9}";	//移动电话
	private static String phoneRex3 = "^(13[0-9]|147|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";	//移动电话
	private static String emailRex="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";//email
	private static String notNegativeIntegerRex = "^\\d+$";	//非负整数
	private static String floatRex = "^[-|+]?\\d*([.]\\d{0,10})?$";	//浮点数 0到10位小数
	
	public static Boolean isEmpty(String str){
		if(str==null || "".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean checkLength(String str,int lenght){
		if(str!=null){
			return str.length()==lenght;
		}else{
			return false;
		}
	}
	
	public static Boolean maxSize(String str,int maxSize){
		if(str!=null && !"".equals(str.trim())){
			return str.trim().length()>maxSize;
		}
			return false;
		
	}
	
	public static Boolean isNull(Object obj){
		return obj==null;
	}
	
	public static Boolean minCheckbox(Object[] obj,int minCheck){
		if(obj!=null && obj.length>=minCheck){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean isPostCode(String post){
		if(post!=null && !"".equals(post.trim()) && post.matches(postCodeRex)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean isFax(String fax){
		if(fax!=null && !"".equals(fax.trim()) && fax.matches(faxRex)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean isMobilePhone(String mobilePhone){
		if(mobilePhone!=null && !"".equals(mobilePhone) ){
//			if(mobilePhone.matches(phoneRex1)){ 
//				return true; 
//				}else if(mobilePhone.matches(phoneRex2)){ 
//				return true; 
//				}
			if(mobilePhone.matches(phoneRex3)){ 
				return true; 
			}
		}
			return false;
	}
	
	public static Boolean isEmail(String email){
		if(email!=null && !"".equals(email.trim()) && email.matches(emailRex)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean isNotNegativeInteger(String number){
		if(number!=null && !"".equals(number.trim()) && number.matches(notNegativeIntegerRex)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean isfloat(String floatStr){
		if(floatStr!=null && !"".equals(floatStr.trim()) && floatStr.matches(floatRex)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String verifyFax(String name,String fax){
		String error = null;
		if(!isFax(fax)){
			error = name+"格式不正确!";
		}
		return error;
	}
	public static String verifyMobilePhone(String name,String mobilePhone){
		String error = null;
		if(!isMobilePhone(mobilePhone)){
			error = name+"格式不正确!";
		}
		return error;
	}
	public static String verifyEmail(String name,String email){
		String error = null;
		if(!isEmail(email)){
			error = name+"格式不正确!";
		}
		return error;
	}
	public static String verifyPostCode(String name,String post){
		String error = null;
		if(!isPostCode(post)){
			error = name+"格式不正确!";
		}
		return error;
	}
	public static String verifyMinCheckbox(String name,Object[] obj,int minCheck){
		String error = null;
		if(!minCheckbox(obj,minCheck)){
			error = name+"至少选择"+minCheck+"项!";
		}
		return error;
	}
	public static String verifyMaxSize(String name,String str,int maxSize){
		String error = null;
		if(maxSize(str,maxSize)){
			error = name+"长度不可超过"+maxSize+"字符!";
		}
		return error;
	}
	public static String verifyEmpty(String name,String str){
		String error = null;
		if(isEmpty(str)){
			error = name+"不可为空!";
		}
		return error;
	}
	
	
	
}
