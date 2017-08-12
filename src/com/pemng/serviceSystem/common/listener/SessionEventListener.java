package com.pemng.serviceSystem.common.listener;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created on Apr 28, 2012
 * <p>Description: [用户登录登出时保存状态]</p>
 * @version        1.0
 */
public class SessionEventListener implements HttpSessionListener {


	
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
//		updateUsrLgnState(arg0.getSession(), C_Constants.LOGIN_STATE_OFFLINE);
	}

	/**
	 * Function Name               saveUsrLgnState                                   
	 * @param                      //参数1说明
	 * @param                      //参数2说明
	 * @return                     //函数返回值的说明
	 * @description                //更新登录状态	             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            May 2, 2012             zhoujunyang         Initial
	 **********************************************************************
	 */
	public void updateUsrLgnState(HttpSession  session,String state){
//			UsrLgnStateDao usrLgnStateDao =(UsrLgnStateDao) AppContainerUtils.getBean(session.getServletContext(), "usrLgnStateDao");
//			usrLgnStateDao.updateUsrLgnState(session.getId(), state);//更新普通用户
//			
//			CstmrSrvcrLgnStateDao cstmrSrvcrLgnStateDao =(CstmrSrvcrLgnStateDao) AppContainerUtils.getBean(session.getServletContext(), "cstmrSrvcrLgnStateDao");
//			cstmrSrvcrLgnStateDao.updateCstmrSrvcrLgnState(session.getId(), state);//更新场务用户
	}


}
