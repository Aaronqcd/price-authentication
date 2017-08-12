/**
 * 
 */
package com.pemng.serviceSystem.base.exception;

/**
 * 调用接口异常
 * @author shaojie
 *
 */
public class BaseCallInterfaceException extends Exception {
	private static final long serialVersionUID = -4231424306432179310L;

	private Object infoObject = null;
	public BaseCallInterfaceException(String msg, Throwable ex) {
		super(msg, ex);
	}
	
	public BaseCallInterfaceException(String msg) {
		super(msg);
	}
	
	public BaseCallInterfaceException(String msg,Object[] obj) {
		super(msg);
		infoObject = obj;
	}

	public Object getInfoObject() {
		return infoObject;
	}

	public void setInfoObject(Object infoObject) {
		this.infoObject = infoObject;
	}
}
