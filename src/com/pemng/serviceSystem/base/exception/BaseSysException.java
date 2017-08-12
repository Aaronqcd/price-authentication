package com.pemng.serviceSystem.base.exception;

/**
 * 系统异常
 * @author shaojie
 *
 */
public class BaseSysException extends Exception {
	private Object infoObject = null;
	private String errorCode = null;
	private String detail = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4402238880893123063L;


	
	public BaseSysException(String errorCode,String detail,String msg,Object infoObject,Throwable ex) {		
		super(msg,ex);
		this.errorCode = errorCode;
		this.detail = detail;
		this.infoObject = infoObject;
	}
	
	public BaseSysException(String msg, Throwable ex) {
		super(msg, ex);
	}
	
	

	public Object getInfoObject() {
		return infoObject;
	}

	public void setInfoObject(Object infoObject) {
		this.infoObject = infoObject;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
