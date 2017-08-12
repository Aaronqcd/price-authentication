package com.pemng.scheduler;

public class TaskBase {
	
	
	private long taskID;//工作任务ID
	private int taskType;//工作类型{1:定时;2:时间间隔}
	private int taskHour;//运行小时
	private int taskMinute;//运行分钟
	private long taskDistance;//间隔时间
	private String taskRemark;//备注
	private boolean autostart;//是否自动运行
	
	public boolean isAutostart() {
		return autostart;
	}
	public void setAutostart(boolean autostart) {
		this.autostart = autostart;
	}
	public long getTaskID() {
		return taskID;
	}
	public void setTaskID(long taskID) {
		this.taskID = taskID;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public int getTaskHour() {
		return taskHour;
	}
	public void setTaskHour(int taskHour) {
		this.taskHour = taskHour;
	}
	public int getTaskMinute() {
		return taskMinute;
	}
	public void setTaskMinute(int taskMinute) {
		this.taskMinute = taskMinute;
	}
	public long getTaskDistance() {
		return taskDistance;
	}
	public void setTaskDistance(long taskDistance) {
		this.taskDistance = taskDistance;
	}
	public String getTaskRemark() {
		return taskRemark;
	}
	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}
	
	
}
