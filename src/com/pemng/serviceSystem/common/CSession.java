package com.pemng.serviceSystem.common;



public class CSession {
//	public static final String CURRENT_USER = "currentUser";
	
//	public static final String CURRENT_CSTMRSRVCR_USER = "currentCstmrSrvcrUser";//当前的场务用户

//	public static final String CURRENT_ACTIONS = "currentActions";
	
	public static final int SESSION_TIME = 1800;	//1800秒
	
	public static final String APPLICATION_COUNTRYS = "countryList";//保存在Application中的省列表key
	public static final String APPLICATION_PROVINCES = "prvncList";//保存在Application中的省列表key
	public static final String APPLICATION_CITYLIST = "cityList";//保存在Application中的省列表key
	
	public static final String APPLICATION_CITYS = "cityMap";//保存在Application中的市Map<provinceId,Citys>的key
	public static final String APPLICATION_ORGTYPE = "orgtypeList";//保存在Application中的机构性质名称的key
	
	public static final String APPLICATION_PROVINCES_IDMAP = "prvncsIdMap";//保存在Application中的ID为key的省份Map
	public static final String APPLICATION_CITYS_IDMAP = "cityIdMap";//保存在Application中的ID为key的城市Map
	
	public static final String APPLICATION_NOT_REQUEST_ROLE_RIGHT_MAP = "NOT_REQUEST_ROLE_RIGHT_MAP";  //不需要角色的权限列表（公开、要登录)
	
	public static final String SESSION_DYNAC_OPEN_RIGHT_SET = "dynacOpenRightSet"; //动态的公开权限Set

	public static final String SESSION_WEBINFO = "SESSION_WEBINFO";
	
//	public static final String CURRENT_ORG = "org";//保存在session中的org信息
}
