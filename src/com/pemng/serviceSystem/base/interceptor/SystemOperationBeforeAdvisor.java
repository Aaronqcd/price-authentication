package com.pemng.serviceSystem.base.interceptor;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class SystemOperationBeforeAdvisor implements MethodBeforeAdvice {
	
	protected Log logger = LogFactory.getLog(SystemOperationBeforeAdvisor.class);

	public void before(Method method, Object[] args, Object target) throws Throwable {
		if (!logger.isDebugEnabled())
			return;
		StringBuffer infoMessage = new StringBuffer();
		infoMessage.append("\nBegin execute ").append(target.getClass())
				.append(".").append(method.getName()).append("(");
		if (args != null && args.length > 1) {

			for (int i = 0, len = args.length; i < len; i++) {
				if (i < len - 1) {
					if (args[i] != null) {
						infoMessage.append("[").append(args[i].getClass())
								.append(" : ").append(args[i]).append("],");
					} else {
						infoMessage.append("[").append(args[i]).append(" : ")
								.append(args[i]).append("],");
					}
				} else {
					if (args[i] != null) {
						infoMessage.append("[").append(args[i].getClass())
								.append(" : ").append(args[i]).append("]");
					} else {
						infoMessage.append("[").append(args[i]).append(" : ")
								.append(args[i]).append("]");
					}
				}

			}
		} else if (args != null && args.length == 1) {
			if (args[0] != null) {
				infoMessage.append("[").append(args[0].getClass())
						.append(" : ").append(args[0]).append("]");
			} else {
				infoMessage.append("[").append(args[0]).append(" : ").append(
						args[0]).append("]");
			}

		}
		infoMessage.append(")");
		logger.debug(infoMessage);

	}

}
