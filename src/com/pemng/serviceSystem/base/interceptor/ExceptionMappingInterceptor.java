/**    
 * description: 
 *      
 */
package com.pemng.serviceSystem.base.interceptor;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.pemng.serviceSystem.base.exception.BusinessException;

/**
 * @author luohanbin</a>
 * @version 1.0
 */

public class ExceptionMappingInterceptor extends AbstractInterceptor {

	protected static final Log LOG = LogFactory.getLog(ExceptionMappingInterceptor.class);

	public String intercept(ActionInvocation invocation) throws Exception {
		String result;

		try {
			result = invocation.invoke();
		} catch (Exception e) {

			handleLogging(e);

			businessExceptionHandler(invocation, e);

			List exceptionMappings = invocation.getProxy().getConfig()
					.getExceptionMappings();
			String mappedResult = this.findResultFromExceptions(
					exceptionMappings, e);
			if (mappedResult != null) {
				result = mappedResult;
				publishException(invocation, new ExceptionHolder(e));
			} else {
				throw e;
			}
		}

		return result;
	}

	private void businessExceptionHandler(ActionInvocation invocation,
			Exception e) throws Exception {
		if (e instanceof BusinessException) {
			BusinessException e2 = (BusinessException) e;
			ActionSupport action = (ActionSupport) invocation.getAction();
			if (e2.getErrors() != Collections.EMPTY_LIST) {
				for (Iterator it = e2.getErrors().iterator(); it.hasNext();) {
					action.addActionError((String) it.next());
				}
			}
			if (e2.getErrorsMap() != Collections.EMPTY_MAP) {
				Map errsMap = e2.getErrorsMap();
				for (Iterator it = errsMap.keySet().iterator(); it.hasNext();) {
					String key = (String) it.next();
					action.addFieldError(key, (String) errsMap.get(key));
				}
			}
		}
	}

	/**
	 * Handles the logging of the exception.
	 * 
	 * @param e
	 *            the exception to log.
	 */
	protected void handleLogging(Exception e) {
		if (e instanceof BusinessException) { // BusinessException主要用于错误提示，简单的输出出错信息
			LOG.error(e);
		} else {
			LOG.error("", e);
		}
	}

	private String findResultFromExceptions(List exceptionMappings, Throwable t) {
		String result = null;

		// Check for specific exception mappings.
		if (exceptionMappings != null) {
			int deepest = Integer.MAX_VALUE;
			for (Iterator iter = exceptionMappings.iterator(); iter.hasNext();) {
				ExceptionMappingConfig exceptionMappingConfig = (ExceptionMappingConfig) iter
						.next();
				int depth = getDepth(exceptionMappingConfig
						.getExceptionClassName(), t);
				if (depth >= 0 && depth < deepest) {
					deepest = depth;
					result = exceptionMappingConfig.getResult();
				}
			}
		}

		return result;
	}

	/**
	 * Return the depth to the superclass matching. 0 means ex matches exactly.
	 * Returns -1 if there's no match. Otherwise, returns depth. Lowest depth
	 * wins.
	 * 
	 * @param exceptionMapping
	 *            the mapping classname
	 * @param t
	 *            the cause
	 * @return the depth, if not found -1 is returned.
	 */
	public int getDepth(String exceptionMapping, Throwable t) {
		return getDepth(exceptionMapping, t.getClass(), 0);
	}

	private int getDepth(String exceptionMapping, Class exceptionClass,
			int depth) {
		if (exceptionClass.getName().indexOf(exceptionMapping) != -1) {
			// Found it!
			return depth;
		}
		// If we've gone as far as we can go and haven't found it...
		if (exceptionClass.equals(Throwable.class)) {
			return -1;
		}
		return getDepth(exceptionMapping, exceptionClass.getSuperclass(),
				depth + 1);
	}

	/**
	 * Default implementation to handle ExceptionHolder publishing. Pushes given
	 * ExceptionHolder on the stack. Subclasses may override this to customize
	 * publishing.
	 * 
	 * @param invocationThe invocation to publish Exception for.
	 * @param exceptionHolderThe exceptionHolder wrapping the Exception to publish.
	 */
	protected void publishException(ActionInvocation invocation,ExceptionHolder exceptionHolder) {
		invocation.getStack().push(exceptionHolder);
	}
}