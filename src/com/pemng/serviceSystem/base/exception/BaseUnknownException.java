package com.pemng.serviceSystem.base.exception;

/**
 * 未知异常
 * @author shaojie
 *
 */
public class BaseUnknownException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4832545124946256739L;

	public BaseUnknownException(String msg) {

		super(msg);
	}

	public BaseUnknownException(String msg, Throwable ex) {

		super(msg, ex);
	}
}
