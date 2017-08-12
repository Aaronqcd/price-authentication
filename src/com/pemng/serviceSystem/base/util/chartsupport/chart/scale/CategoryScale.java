package com.pemng.serviceSystem.base.util.chartsupport.chart.scale;

/**
 * 标识一个category刻度
 * 
 * @author Administrator
 * 
 */
public class CategoryScale implements Comparable<CategoryScale> {
	private String value; // 刻度值
	private boolean show; // 标识是否显示

	public CategoryScale(String value) {
		this(value, false);
	}

	public CategoryScale(String value, boolean show) {
		this.value = value;
		this.show = show;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (show ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CategoryScale other = (CategoryScale) obj;
		if (show != other.show)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	
	public int compareTo(CategoryScale o) {
		try{
			return new Double(value).compareTo(new Double(o.value));
		}catch(Exception ex){
			return value.compareTo(o.value);
		}
		
	}
}
