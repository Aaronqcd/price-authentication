package com.pemng.serviceSystem.common;
/**
 * Created on Apr 25, 2012
 * <p>Description: 封装字段排序规则</p>
 * @version        1.0
 */
public class SortRule {
	/**
	 * <p>Discription:需要排序的字段，需和POJO的属性一致</p>
	 */
	private String param = null;
	/**
	 * <p>Discription:排序的方向：ASC/DESC</p>
	 */
	private String direction = null;

	public SortRule() {
	}

	public SortRule(String param, String direction) {
		this.param = param;
		this.direction = direction;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
