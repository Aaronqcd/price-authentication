package com.pemng.scheduler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.pemng.serviceSystem.base.util.ApplicationContextHolder;

/**
 * 任务计划管理
 * @author yunxiangfu@163.com
 *
 */
public abstract class TaskScheduler extends Thread{


	private TaskBase taskInfo;//任务信息
	private boolean onOff=true;//线程开关
	private boolean estate;//线程状态（true：已启动；false:未启动）
	private int run_estate;//运行状态（0：未运行；1：运行中；）
	private int status;//运行状况{0：正常;1:警告;2：异常}
	private Date topTime;//上次运行时间
	private Date nextTime;//下次运行时间
	private static Logger logger = Logger.getLogger(TaskScheduler.class);//日志
	private int runCount;//运行次数
	private String errorMessage;//最后的异常消息
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public TaskScheduler(TaskBase work){
		this.taskInfo=work;
	}
	/**
	 * 定时设置每天几点几分启动
	 * @param hours 几点 
	 * @param minutes 几分
	 * @return 返回等待时间
	 */
    public long getTime(int hours,int minutes){
        Date date=new Date();
        long time=date.getTime();
        long time2=0;
        date.setHours(hours);
        date.setMinutes(minutes);
        time2=date.getTime();
        if(time>time2){
            date.setDate(date.getDate()+1);
            time2=date.getTime();
        }
        System.out.println("相隔="+((time2-time)/1000/60/60)+"小时"+(((time2-time)/1000/60)%60)+"分");
        return (time2-time);
    }
    public long getTime(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		/*** 定制每日00：24：00执行方法 ***/
		calendar.set(year, month, day, taskInfo.getTaskHour(), taskInfo.getTaskMinute(), 00);
		Date date = calendar.getTime();
		Date date2 = new Date();
		run_estate=0;//初始化状态为未运行
//		System.out.println("date:"+date.toString());
//		System.out.println("date2:"+date2.toString());
		if(date.getTime()>date2.getTime()){
			return date.getTime()-date2.getTime();
		}else{
			calendar.set(year, month, day+1, taskInfo.getTaskHour(), taskInfo.getTaskMinute(), 00);
			date = calendar.getTime();
			return date.getTime()-date2.getTime();
		}
    }
	public void run(){
		while(onOff)
		if(estate){
			try{
			long distance=0;
			if(taskInfo.getTaskType()==1){
				distance=getTime();//
			}else{
				if(topTime==null){
					distance=taskInfo.getTaskDistance()*1000;
				}else{
					distance=taskInfo.getTaskDistance()*1000-(new Date().getTime()-topTime.getTime());
					if(distance<=0)
						distance=1;
				}
			}
			if(distance>=0){//
				run_estate=0;//初始化状态为未运行
				nextTime=new Date(System.currentTimeMillis()+distance);//下次启动时间
//				System.out.println("任务ID:"+taskInfo.getTaskID()+" 任务下次运行时间为:"+nextTime.toString());
				logger.info("任务ID:"+taskInfo.getTaskID()+" 任务下次运行时间为:"+nextTime.toString());
				try {
					boolean result = false;
					sleep(distance);
					/*************计划任务执行开始***************/
					run_estate=1;//运行状态为已运行
					topTime=new Date();//当前运行时间
//					System.out.println("任务ID:"+taskInfo.getTaskID()+" 任务运行开始...");
					logger.info("任务ID:"+taskInfo.getTaskID()+" 任务运行开始...");
					runCount++;//执行次数
					execute();//执行任务
//					result = execute();//执行任务
//					if(!result){
//						System.out.println("任务ID:"+taskInfo.getTaskID()+" 任务执行过程中出现异常，本次任务执行结束！");
//						logger.info("任务ID:"+taskInfo.getTaskID()+" 任务执行过程中出现异常，本次任务执行结束...");
//					}
//					System.out.println("任务ID:"+taskInfo.getTaskID()+" 任务运行结束...");
					logger.info("任务ID:"+taskInfo.getTaskID()+" 任务运行结束...");
					run_estate=0;//初始化状态为未运行
					/*************计划任务执行结束***************/
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
//					System.out.println("任务ID:"+taskInfo.getTaskID()+" 计划任务时间发生调整，重新调整任务运行时间！");
					logger.info("任务ID:"+taskInfo.getTaskID()+" 计划任务时间发生调整，重新调整任务运行时间...");
//					e.printStackTrace();
				}
			}
			try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			}catch(Exception ex){
				status=2;//异常
				this.setErrorMessage("执行过程中任务线程发生异常："+ex.getMessage());
				logger.error("任务ID:"+taskInfo.getTaskID()+" 执行过程中发生异常：");
				logger.error(ex);
			}
		}else{
			try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		status=2;//异常
		this.setErrorMessage("任务已关闭！");
		logger.info("任务ID:"+taskInfo.getTaskID()+" 已关闭！");
	}
	public Date getTopTime() {
		return topTime;
	}
	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}
	public TaskBase getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(TaskBase taskInfo) {
		this.taskInfo = taskInfo;
	}
	/**
	 * 重新加载任务
	 * @param bean
	 * @throws IOException
	 */
	public void updateTask(){
		try{
			this.interrupt();//打断休眠状态
			logger.info("任务ID:"+this.taskInfo.getTaskID()+" 更新任务");
		}catch(Exception ex){
			status=1;//警告
			this.errorMessage="更新任务失败："+ex.getMessage();
			logger.error("任务ID:"+this.taskInfo.getTaskID()+" 更新任务失败：");
			logger.error(ex);
	//		ex.printStackTrace();
		}
	}
    /**
     * 停止
     *
     */
	public void stopTask(){
		try{
			estate=false;
			run_estate=0;//设置为停止状态
			this.interrupt();//打断休眠状态
			logger.info("任务ID:"+this.taskInfo.getTaskID()+" 停止任务");
		}catch(Exception ex){
			status=1;//警告
			this.errorMessage="停止失败："+ex.getMessage();
			logger.error("任务ID:"+this.taskInfo.getTaskID()+" 停止失败：");
			logger.error(ex);
//			ex.printStackTrace();
		}
	}
	//关闭线程，无法恢复
	public void offTask(){
		try{
			onOff=false;
			estate=false;
			run_estate=0;//设置为停止状态
			this.interrupt();//打断休眠状态
			logger.info("任务ID:"+this.taskInfo.getTaskID()+" 关闭任务，不再开启");
		}catch(Exception ex){
			status=1;//警告
			this.errorMessage="关闭任务失败："+ex.getMessage();
			logger.error("任务ID:"+this.taskInfo.getTaskID()+" 关闭任务失败：");
			logger.error(ex);
//			ex.printStackTrace();
		}
	}
	/**
	 * 启动
	 *
	 */
	public void startTask(){
		if(!this.isAlive()){//线程存活状态
			start();
		}
		if(taskInfo.isAutostart()){
			try{
				estate=true;
				run_estate=1;//设置为运行状态
				this.interrupt();//打断休眠状态
				logger.info("任务ID:"+this.taskInfo.getTaskID()+" 启动任务");
			//		this.start();
			}catch(Exception ex){
				status=1;//警告
				this.errorMessage="开启失败："+ex.getMessage();
				logger.error("任务ID:"+this.taskInfo.getTaskID()+" 开启失败 ：");
				logger.error(ex);
	//			ex.printStackTrace();
			}
		}
	}
	public Date getNextTime() {
		return nextTime;
	}
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}
	public int getRunCount() {
		return runCount;
	}
	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}
	//任务执行
	public abstract void execute();
	//执行状态
	public int getRun_estate() {
		return run_estate;
	}
	public void setRun_estate(int run_estate) {
		this.run_estate = run_estate;
	}
	public boolean isEstate() {
		return estate;
	}
	public void setEstate(boolean estate) {
		this.estate = estate;
	}
	
}
