package com.pemng.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Scheduler extends Thread{

	private static List<TaskScheduler> taskList;//工作任务
	private static Logger logger = Logger.getLogger(TaskScheduler.class);//日志
	private boolean onOff = true;

	public static List<TaskScheduler> getTaskList() {
		if(taskList==null)
			taskList = new ArrayList<TaskScheduler>();
		return taskList;
	}
	
	public void setTaskList(List<TaskScheduler> taskList) {
		this.taskList = taskList;
	}
	public void startTask(){
		start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(onOff){
			try{
				if(taskList!=null)
				for(int i=0;i<taskList.size();i++){
					TaskScheduler task = taskList.get(i);
//					System.out.println("=====================开启线程"+task.getId()+" estate:"+task.isEstate()+" alive:"+task.isAlive());
					if((task.getTaskInfo().isAutostart() || task.isEstate()) && !task.isAlive()){//唤醒已死掉的线程
						System.out.println("=====================开启线程"+task.getId());
						task.startTask();
					}
				}
			}catch(Exception ex){
				logger.error("线程服务执行过程中发生异常：");
				logger.error(ex);
			}
			try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加新任务
	 * @param bean
	 */
	public static synchronized void addTask(TaskScheduler task){
		try{
			if(task.getTaskInfo().getTaskID()==0)
				task.getTaskInfo().setTaskID(System.currentTimeMillis());
			getTaskList().add(task);
			if(task.getTaskInfo().isAutostart()){
				task.startTask();
			}
			logger.info("添加新任务 ID："+task.getTaskInfo().getTaskID());
		}catch(Exception ex){
			logger.error("添加新任务发生异常：");
			logger.error(ex);
		}
	}
	
	
	public static synchronized void updateTask(TaskBase bean){
		TaskScheduler task = getTaskScheduler(bean.getTaskID());
		if(task!=null){
			task.setTaskInfo(bean);
			if(bean.isAutostart()){
				task.startTask();
			}
			task.updateTask();
		}else{
			logger.info("更新任务失败 ID："+bean.getTaskID()+"不存在！");
		}
	}
	
	public static TaskScheduler getTaskScheduler(long id){
		for(TaskScheduler task:taskList){
			if(task.getTaskInfo().getTaskID()==id){
				return task;
			}
		}
		logger.info("获得任务失败 ID："+id+" 不存在！");
		return null;
	}
	
	public static void stopTask(long id){
		TaskScheduler task = getTaskScheduler(id);
		if(task!=null){
			task.stopTask();
		}
	}
	public static void startTask(long id){
		TaskScheduler task = getTaskScheduler(id);
		if(task!=null){
			task.startTask();
		}
	}
	
	public static synchronized void deleteTask(long id){
		TaskScheduler task = getTaskScheduler(id);
		if(task!=null){
			task.offTask();
			taskList.remove(task);
			logger.info("删除任务 ID："+id+" 成功！");
		}else{
			logger.info("删除任务失败 ID："+id+" 不存在！");
		}
	}
	
	public static int getCurrentTaskSize(){
		return taskList.size();
	}
}
