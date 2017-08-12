package com.pemng.serviceSystem.base.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil{
    
    public static final String SIZE = "size";   
    public static final String CURPAGE = "curPage";
	public static final String START = "start";
	public static final String ORDER_SENTENCE = "ordersentence";
	public static final String ORDERS = "orders";  
    
 
    private int total;
    private List data = new ArrayList();
   

    public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public List getData() {
		return data;
	}


	public void setData(List data) {
		this.data = data;
	}


	@Override
    public String toString() {
       return "PageUtil [data=" + data + ", total=" + total + "]";
    }
}