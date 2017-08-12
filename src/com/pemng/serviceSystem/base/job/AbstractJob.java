/**
 * 
 */
package com.pemng.serviceSystem.base.job;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.pemng.serviceSystem.base.util.ApplicationContextHolder;

/**
 * @author Lay
 * 
 */
public abstract class AbstractJob implements Job, Serializable {
	protected ApplicationContext ac = null;
	
	protected final Log logger = LogFactory.getLog(AbstractJob.class);

	public AbstractJob() {
		ac = ApplicationContextHolder.applicationContext;
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			beforeExecute(context);
			doExecute(context);
		} finally {
			afterExecute(context);
		}
	}

	protected abstract void doExecute(JobExecutionContext context);

	protected abstract void afterExecute(JobExecutionContext context);

	protected abstract void beforeExecute(JobExecutionContext context);
}
