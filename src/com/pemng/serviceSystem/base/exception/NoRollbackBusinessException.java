/**    
 * description: 不回滚异常处理
 * Create on Oct 14, 2008 5:59:04 PM    
 *      
 */
package com.pemng.serviceSystem.base.exception;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author luohanbin
 * 
 */
public class NoRollbackBusinessException extends BusinessException {

	public NoRollbackBusinessException() {
		super();
	}

	public NoRollbackBusinessException(String message) {
		super(message);
	}

	public NoRollbackBusinessException(List errors) {
		super(errors);
	}

	/**
	 * 
	 * @param message
	 * @param errors
	 * @param errorsMap
	 */
	public NoRollbackBusinessException(String message, List errors,Map errorsMap) {
		super(message, errors, errorsMap);
	}

	/**
	 * 
	 * @param message
	 * @param errors
	 * @param cause
	 */
	public NoRollbackBusinessException(String message, List errors,Throwable cause) {
		super(message, errors, cause);
	}

	public NoRollbackBusinessException(Throwable cause) {
		super(cause);
	}

}
