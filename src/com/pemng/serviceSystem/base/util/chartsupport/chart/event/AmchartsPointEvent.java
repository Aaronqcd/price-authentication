package com.pemng.serviceSystem.base.util.chartsupport.chart.event;


/**
 * 描述一个Am charts事件(信息地雷)
 * 
 */
public interface AmchartsPointEvent {

	/**
	 * 设置信息标示
	 * @param letter 信息标示
	 */
	void setLetter(String letter);

	/**
	 * 获取信息标示
	 * @return 信息标示
	 */
	String getLetter();

	/**
	 * 设置描述信息
	 * @param description 描述信息
	 */
	void setDescription(String description);

	/**
	 * 获取描述信息
	 * @return 描述信息
	 */
	String getDescription();
}
