package com.pemng.serviceSystem.base.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author luohanbin
 * 
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7906395903189927195L;

	private List errors = Collections.EMPTY_LIST;

	private Map errorsMap = Collections.EMPTY_MAP;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
		this.errors = new ArrayList(1);
		this.errors.add(message);
	}

	public BusinessException(List errors) {
		if (errors != null) {
			this.errors = errors;
		}
	}

	/**
	 * 
	 * @param message
	 * @param errors
	 * @param errorsMap
	 */
	public BusinessException(String message, List errors, Map errorsMap) {
		super(message);
		if (errors != null) {
			this.errors = errors;
		}
		if (errorsMap != null) {
			this.errorsMap = errorsMap;
		}
	}

	/**
	 * 
	 * @param message
	 * @param errors
	 * @param cause
	 */
	public BusinessException(String message, List errors, Throwable cause) {
		super(message, cause);
		if (errors != null) {
			this.errors = errors;
		}
		if (errorsMap != null) {
			this.errorsMap = errorsMap;
		}
	}

	public BusinessException(Throwable cause) {
		super(cause);
		this.errors = new ArrayList(1);
		this.errors.add(cause.getMessage());
	}

	public List getErrors() {
		return errors;
	}

	public void setErrors(List errors) {
		this.errors = errors;
	}

	public Map getErrorsMap() {
		return errorsMap;
	}

	public void setErrorsMap(Map errorsMap) {
		this.errorsMap = errorsMap;
	}

}
