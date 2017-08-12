package com.pemng.common.job.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.scheduling.quartz.CronTriggerBean;


public class CheckTriggerListener implements TriggerListener{
		
	protected Log log = LogFactory.getLog(getClass());
	
	public String getName() {
		return CheckTriggerListener.class.getName();
	}

	public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, int triggerInstructionCode) {
		String jobName = jobExecutionContext.getJobDetail().getName();
		log.info("定时任务:[" + jobName + "]执行结束。");
	}

	public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
		String jobName = jobExecutionContext.getJobDetail().getName();
		CronTriggerBean t = (CronTriggerBean)trigger;
		String cron = t.getCronExpression();
		
		log.info("正在执行定时任务:[" + jobName + "]["+cron+"]。");

	}

	public void triggerMisfired(Trigger trigger) {
		log.info(trigger.getJobName() + "----> finalfire misfired, trigger.getEndTime():" + trigger.getEndTime() + ",trigger.getFinalFireTime()" + trigger.getFinalFireTime());
		trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);
	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;   
	}

}
