package com.pemng.serviceSystem.common.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pemng.serviceSystem.base.util.AppContainerUtils;
import com.pemng.serviceSystem.base.util.TransactionUtil;

/**
 * Created on May 28, 2012
 * <p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @version 1.0
 */
public class AppContextListener implements ServletContextListener {

	public AppContextListener() {
		System.out.println("AppContextListener()-------");

	}

	
	public void contextDestroyed(ServletContextEvent event) {

	}

	
	public void contextInitialized(ServletContextEvent event) {
		// 初始化省列表地区信息
//		initPrvncList(event);

	}

	private void initPrvncList(ServletContextEvent event) {

		ServletContext application = event.getServletContext();

		// 开启事务
		TransactionUtil util = (TransactionUtil) AppContainerUtils.getBean(application,
				"transactionUtil");
		util.beginTrans();
		// 获取省信息保存入application
		getPrvncs(application);
		// 获取市信息保存入application
		getCities(application);
		// 获取国家信息保存入application
		getCountrys(application);
		//公开权限的action
//		getOpenActn(application);
		
		util.endTrans();
	}

	private void getCountrys(ServletContext application) {
//		CntryDao cntryDao = (CntryDao) AppContainerUtils.getBean(application, "cntryDao");
//		// 获取所有市
//		List<Cntry> cntryList = cntryDao.findObjects();
//		if (cntryList == null)
//			cntryList = new ArrayList<Cntry>();
//		
//		// 将国家列表保存入application
//		application.setAttribute(CSession.APPLICATION_COUNTRYS, cntryList);
	}


	private void getCities(ServletContext application) {
//		CityDao cityDao = (CityDao) AppContainerUtils.getBean(application, "cityDao");
//		// 获取所有市
//		List<City> cityList = cityDao.findObjects();
//		if (cityList == null)
//			cityList = new ArrayList<City>();
//		
//		// 将城市列表保存入application
//		application.setAttribute(CSession.APPLICATION_CITYLIST, cityList);
//		
//		Map<Long, List<City>> cityMap = new java.util.Hashtable<Long, List<City>>();
//		// 保存城市map
//		for (City city : cityList) {
//			List<City> cities = null;
//			if (!cityMap.containsKey(city.getPrvnc().getId())) {// 如果map中尚不包含该城市的省id
//				cities = new ArrayList<City>();
//			} else {
//				cities = cityMap.get(city.getPrvnc().getId());
//			}
//			cities.add(city);// 添加入相同省的城市列表
//			cityMap.put(city.getPrvnc().getId(), cities);
//
//		}
//		application.setAttribute(CSession.APPLICATION_CITYS, cityMap);
//		// 城市ID对应Map
//		Map<Long, City> cityIdMap = new HashMap<Long, City>(1000); // 城市ID对应名称
//		for (City city : cityList) {
//			cityIdMap.put(city.getId(), city);
//		}
//		application.setAttribute(CSession.APPLICATION_CITYS_IDMAP, cityIdMap);

	}

	private void getPrvncs(ServletContext application) {

//		PrvncDao prvncDao = (PrvncDao) AppContainerUtils.getBean(application, "prvncDao");
//
//		// 获取所有省份
//		List<Prvnc> prvncList = prvncDao.findObjects();
//		if (prvncList == null) {
//			prvncList = new ArrayList<Prvnc>();
//		}
//		// 将省列表保存入application
//		application.setAttribute(CSession.APPLICATION_PROVINCES, prvncList);
//
//		// 省份ID对应Map
//		Map<Long, Prvnc> prvncMap = new HashMap<Long, Prvnc>(prvncList.size());
//		for (Prvnc prvnc : prvncList) {
//			prvncMap.put(prvnc.getId(), prvnc);
//		}
//		application.setAttribute(CSession.APPLICATION_PROVINCES_IDMAP, prvncMap);

	}
	
	//获得公开的权限
	private void getOpenActn(ServletContext application){
//		ActnInfoDao actnInfoDao = (ActnInfoDao) AppContainerUtils.getBean(application, "actnInfoDao");
//
//		List<ActnInfo> actnInfoList = actnInfoDao.getOpenActnInfo();
//		Map<String, ActnInfo> resourceMap = new HashMap<String, ActnInfo>(actnInfoList.size());
//		for(ActnInfo info : actnInfoList){
//			resourceMap.put(info.getViewId(), info);
//		}
//		
//		application.setAttribute(CSession.APPLICATION_NOT_REQUEST_ROLE_RIGHT_MAP, resourceMap);

	}

}
