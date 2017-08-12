package com.pemng.serviceSystem.base.util.chartsupport.chart.event;


/**
 * 默认的Am charts事件实现
 * 
 */
public class DefaultAmchartsPointEvent implements AmchartsPointEvent {

	private String letter;
	private String description;

	
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
