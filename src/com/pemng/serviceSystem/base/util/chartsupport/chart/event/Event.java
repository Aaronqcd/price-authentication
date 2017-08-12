package com.pemng.serviceSystem.base.util.chartsupport.chart.event;

import java.util.Date;

/**
 * 描述一个(事件)信息地雷
 * 
 * @author WANGYEPING
 * @since 2010-03-17
 * 
 */
public class Event {
	// 事件ID
	private String id;
	// 日期
	private Date date;
	// 提示标签
	private String letter;
	// 详细信息
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
