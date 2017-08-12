package com.pemng.serviceSystem.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on May 2, 2012
 * <p>Description: [描述该类概要功能介绍]</p>
 * @version        1.0
 */
public class Result{
	private String message;
	private List<String> actionErrors = new ArrayList();
	private Map<String,String> fieldErrors = new HashMap();
	
	public List<String> getActionErrors() {
		return actionErrors;
	}
	public void setActionErrors(List<String> actionErrors) {
		this.actionErrors = actionErrors;
	}
	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}
	public void setFieldErrors(Map<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
	public Result(String message){
		this.message = message;
		
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void addActionError(String errorMessage) {
		if(actionErrors==null)
			actionErrors = new ArrayList();
		actionErrors.add(errorMessage);
	}
	public void addFieldError(String fieldName, String errorMessage) {
		if(fieldErrors==null)
			fieldErrors = new HashMap<String,String>();
		fieldErrors.put(fieldName, errorMessage);
		
	}
	
	
	
}
