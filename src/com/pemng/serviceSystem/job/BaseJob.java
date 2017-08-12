package com.pemng.serviceSystem.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pemng.common.util.Utility;
import com.pemng.serviceSystem.common.CacheConstants;
import com.pemng.serviceSystem.common.DataConstants;


public class BaseJob extends QuartzJobBean{

	Log log = LogFactory.getLog(this.getClass());
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		String jobName = null;
		String tiggerName = null;
		try {
			jobName = ctx.getJobDetail().getName();
			tiggerName = ctx.getTrigger().getName();
			
			
			String startBiz = Utility.getValue(CacheConstants.ALL_DATA_MAP,DataConstants.CODE_2099_START_BIZ, DataConstants.KEY_2099_START_BIZ,1);
			if("true".equalsIgnoreCase(startBiz)) {
				biz(ctx);
			}else {
				log.info("系统初始化参数没有准备完毕，忽略["+jobName+"]处理......");
			}
			
		}catch(Exception e) {
			log.info( "["+tiggerName+"][" + jobName + "]业务逻辑  发生错误 : " + e);
		}
		
	}
	
	
	protected void biz(JobExecutionContext ctx) throws Exception {
	}

}
