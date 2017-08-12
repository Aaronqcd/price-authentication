package com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue;

import java.util.LinkedList;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.scale.CategoryScale;

/**
 * Am chart 的Category坐标轴
 * 
 */
public class DefaultAmchartsCategoryAxis implements AmchartsCategoryAxis {
	private LinkedList<CategoryScale> scales;

	public DefaultAmchartsCategoryAxis() {
		scales = new LinkedList<CategoryScale>();
	}

	
	public void addAmchartsCategoryScale(String scaleValue) {
		scales.add(new CategoryScale(scaleValue));
	}

	
	public void addAmchartsCategoryScale(int id, String scaleValue) {
		if (id > scales.size()) {
			scales.add(new CategoryScale(scaleValue));
		} else {
			scales.add(id, new CategoryScale(scaleValue));
		}
	}

	
	public String getScale(int id) throws IndexOutOfBoundsException {
		String scale = null;
		try {
			scale = scales.get(id).getValue();
		} catch (NullPointerException e) {
		}
		return scale;
	}

	@SuppressWarnings("unchecked")
	public List<String> listScale() {
		if (scales == null) {
			scales = new LinkedList<CategoryScale>();
		}

		List<CategoryScale> clone = (List<CategoryScale>) scales.clone();
		List<String> result = new LinkedList<String>();
		for (CategoryScale scale : clone) {
			if (scale != null) {
				result.add(scale.getValue());
			}
		}
		return result;
	}

	
	public boolean removeAmchartsCategoryScale(String scaleValue) {
		for (int i = 0; i < scales.size(); i++) {
			String scale = null;
			if (scales.get(i) != null) {
				scale = scales.get(i).getValue();
			} else {
				return false;
			}
			if (scale.equals(scaleValue)) {
				return removeAmchartsCategoryScale(i);
			}
		}
		return false;
	}


	public boolean removeAmchartsCategoryScale(int id) {
		String scale = null;
		try {
			scale = scales.remove(id).getValue();
		} catch (IndexOutOfBoundsException e) {
			return false;
		} catch (NullPointerException ne) {
			return false;
		}
		return (scale != null);
	}


	public void clearScales() {
		if(scales != null){
			scales.clear();
		}
	}

	
	public void addAmchartsCategoryScale(String scaleValue, boolean show)
			throws NullPointerException {
		scales.add(new CategoryScale(scaleValue, show));
	}


	public void addAmchartsCategoryScale(int id, String scaleValue, boolean show)
			throws NullPointerException {
		if (id > scales.size()) {
			scales.add(new CategoryScale(scaleValue, show));
		} else {
			scales.add(id, new CategoryScale(scaleValue, show));
		}
	}


	public CategoryScale getCategoryScale(int id)
			throws IndexOutOfBoundsException {
		CategoryScale scale = null;

		scale = scales.get(id);

		return scale;
	}

	@SuppressWarnings("unchecked")

	public List<CategoryScale> listCategoryScale() {
		if (scales == null) {
			scales = new LinkedList<CategoryScale>();
		}

		List<CategoryScale> clone = (List<CategoryScale>) scales.clone();
		return clone;
	}

}
