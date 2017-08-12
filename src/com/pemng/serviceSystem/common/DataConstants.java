package com.pemng.serviceSystem.common;

public class DataConstants {

	/*
	 * =========================================只读平台设定(代码以100开头)		代码 	开始==================================================
	 */

	
	/**
	 * 1001:短信发送状态
	 */
	public static String CODE_1001_SMS_STATUS = "1001";
	
	/**
	 * 1003:短信类型
	 */
	public static String CODE_1002_SMS_TYPE = "1002";
	
	
	
	/*
	 * =========================================只读平台设定		代码  	结束==================================================
	 */
	
	
	/*
	 * =========================================可修改 平台设定(代码以200开头)	代码 	开始==================================================
	 */
	

	/**
	 * 2001:短信发送人
	 */
	public static String CODE_2001_RECEIVED_NUMS_BASIC= "2001";
	
		
	/**
	 * 2002:短信发送间隔时间
	 */
	public static String CODE_2002_SEND_SMS_INTERVAL_TIME= "2002";
	
	/**
	 * 2003:故障未响应响应发送短信
	 */
	public static String CODE_2003_CASE_SMS_NO_RESPONSE= "2003";
	
	
	
	/**
	 * 2004:测试短信
	 */
	public static String CODE_2004_SMS_WEBMASTER_TEST_TIME= "2004";
	
	/**
	 * 2005:余额提醒
	 */
	public static String CODE_2005_SMS_BALANCE_ALARM= "2005";
	
	/**
	 * 2006:余额提醒值
	 */
	public static String CODE_2006_SMS_BALANCE_ALARM_VALUE= "2006";
	
	
	
	
	/**
	 * 2099:是否执行业务逻辑
	 */
	public static String CODE_2099_START_BIZ = "2099";
	
		
	
	/*
	 * =========================================可修改平台设定	代码 	开始==================================================
	 */
	
	/*
	 * =========================================平台设定	KEY 	开始==================================================
	 */
	
	
	
	/**
	 * 1001 短信发送状态KEY 0：待发送
	 */
	public static String KEY_1001_SMS_STATUS_PRESEND= "0";
	
	
	/**
	 * 1001 短信发送状态KEY -1:： 失败
	 */
	public static String KEY_1001_SMS_STATUS_FAILURE= "-1";
	
		
	/**
	 * 1001 短信发送状态KEY 1 ： 成功
	 */
	public static String KEY_1001_SMS_STATUS_SUCCESS= "1";
	
	
	/**
	 * 1002 短信类型KEY 0:故障 
	 */
	public static String KEY_1002_SMS_TYPE_CASE = "0";
	
	/**
	 * 1002 短信类型KEY 1：网管测试
	 */
	public static String KEY_1002_SMS_TYPE_WEBMASTER_TEST = "1";
	
	/**
	 * 1002 短信类型KEY 2：余额提醒
	 */
	public static String KEY_1002_SMS_TYPE_BALANCE_ALARM = "2";
	
	
	/**
	 * 1002 短信类型KEY 3：故障关联
	 */
	public static String KEY_1003_SMS_TYPE_CASE_DERIVE = "3";
	
	
	
	/**
	 * 2001 短信发送人KEY 1:新建故障
	 */
	public static String KEY_2001_RECEIVED_NUMS_BASIC_NEW_CASE= "1";
	
	/**
	 * 2001 短信发送人KEY 2:故障升降级
	 */
	public static String KEY_2001_RECEIVED_NUMS_BASIC_CHANGE_LEVEL= "2";
	
	/**
	 * 2001 短信发送人KEY 3:故障转移
	 */
	public static String KEY_2001_RECEIVED_NUMS_BASIC_DIVERT_CASE= "3";
	
	/**
	 * 2001 短信发送人KEY 4:故障关闭
	 */
	public static String KEY_2001_RECEIVED_NUMS_BASIC_CLOSE_CASE= "4";
	
	/**
	 * 2001 短信发送人KEY 5:故障24小时未解决
	 */
	public static String KEY_2001_RECEIVED_NUMS_BASIC_NOSOLVED_DAY= "5";
	
	/**
	 * 2001 短信发送人KEY 5:新建问题
	 */
	public static String KEY_2001_RECEIVED_NUMS_BASIC_NEW_QUESTION= "6";
	
	
	
	/**
	 * 2002 
	 */
	public static String KEY_2002_SEND_SMS_INTERVAL_TIME="1";
	
		
	/**
	 * 2004 测试短信
	 */
	public static String KEY_2004_SMS_WEBMASTER_TEST= "1";
	
	
	
	
	/**
	 * 2005:余额提醒
	 */
	public static String KEY_2005_SMS_BALANCE_ALARM= "1";
	
	/**
	 * 2006:余额提醒值
	 */
	public static String KEY_2006_SMS_BALANCE_ALARM_VALUE= "1";
	

	
	/**
	 * 2099:是否执行业务逻辑
	 */
	public static String KEY_2099_START_BIZ = "1";
	

	/*
	 * =========================================平台设定	KEY 	结束==================================================
	 */
}
