package com.pemng.serviceSystem.base.exception;

/**
 * 应用类型异常
 * @author shaojie
 *
 */
public class BaseAppException extends Exception {

	private Object infoObject = null;
	private String errorCode = null;
	private String detail = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 605066521045692823L;

	public Object getInfoObject() {
		return infoObject;
	}

	public void setInfoObject(Object infoObject) {
		this.infoObject = infoObject;
	}

	public BaseAppException(String errorCode,String msg,String detail,Object infoObject,Throwable ex){
		super(msg,ex);
		this.errorCode = errorCode;
		this.detail = detail;
		this.infoObject = infoObject;
		
		
	}
	
	public BaseAppException(String msg,Throwable ex){
		super(msg,ex);
		
		
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
