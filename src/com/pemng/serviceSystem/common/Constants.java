package com.pemng.serviceSystem.common;

public class Constants {
	/**
	 * 用户状态：正常
	 */
	public static final String USER_STATUS_VALID="1";
	
	/**
	 * 用户状态：禁用
	 */
	public static final String USER_STATUS_DISABLE="0";
	
	/**
	 * <p>Discription:全局删除标记-已删除</p>
	 */
	public static final String MARK_FOR_DELETE_YES = "D";
	/**
	 * <p>Discription:全局删除标记-有效</p>
	 */
	public static final String MARK_FOR_DELETE_NO = "V";

	/**
	 * 登录类型数据字典分类CODE
	 */
	public static final String BASEDATA_LOGIN_CODE = "005";
	/**
	 * 数据字典-登录方式：
	 */
	public static final String BASEDATA_LOGIN_KEY_AUTH = "auth_type";
	public static final String BASEDATA_LOGIN_KEY_EXCLUDE_USERS = "exclude_users";
	public static final String BASEDATA_LOGIN_KEY_AD_IP = "ad_ip";
	public static final String BASEDATA_LOGIN_KEY_AD_PORT = "ad_port";
	public static final String BASEDATA_LOGIN_KEY_AD_DOMAIN = "ad_domain";
	public static final String BASEDATA_LOGIN_KEY_AD_BASEDN = "ad_basedn";
	public static final String BASEDATA_LOGIN_KEY_AD_AD_USERNAME = "ad_username";
	public static final String BASEDATA_LOGIN_KEY_AD_AD_PASSWORD = "ad_password";
	/**
	 * 
	 */
	public static final String AUTH_TYPE_AD = "1";
	
	public static final String AUTH_TYPE_SYS = "2";

	
	/**
	 * 工作流	操作动作
	 */
	public static final String ACTION_TEMPSAVE = "00";//动作-暂存
	public static final String ACTION_SUBMIT = "01";//动作-提交
	public static final String ACTION_APPROVE = "02";//动作-通过
	public static final String ACTION_REJECT = "03";//动作-驳回
	public static final String ACTION_TURN = "04";//动作-转办
	/**
	 * 工作流	是否待办
	 */
	public static final String IS_OUTCOME_Y = "1";//待办-是
	public static final String IS_OUTCOME_N = "0";//待办-否
	/**
	 * 工作流	是否有效
	 */
	public static final String IS_VALID_V = "1";//有效
	public static final String IS_VALID_N = "0";//无效
	
	
	//========================待办事项===========================
	
	
	////////////////////////表单类型
	
	//========================待办事项===========================
	/**
	 * 表单：有效
	 */
	public static final String DOLIST_IS_VALID_YES = "1";
	/**
	 * 表单：无效
	 */
	public static final String DOLIST_IS_VALID_NO = "0";
	
	
	/**
	 * 任务是否完成，如果没有完成，则忽略此次任务  发送短信
	 */
	public static boolean 	TASK_IS_RUNNING_SENDSMS = false;
	
	/**
	 * <p>Discription:自动提示默认长度</p>
	 */
	public static final Integer AUTO_COMPLETE_SIZE_10 = 10;
}

