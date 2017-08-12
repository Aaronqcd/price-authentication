package com.pemng.serviceSystem.base.util;

public class DataTableReturnObject {  
    private long iTotalRecords;  
    private long iTotalDisplayRecords;  
    private String sEcho;  
    private String[][] aaData;  
      
    public DataTableReturnObject(long totalRecords, long totalDisplayRecords,
			String echo, String[][] aaData) {
		
		iTotalRecords = totalRecords;
		iTotalDisplayRecords = totalDisplayRecords;
		sEcho = echo;
		this.aaData = aaData;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(long totalRecords) {
		iTotalRecords = totalRecords;
	}

	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(long totalDisplayRecords) {
		iTotalDisplayRecords = totalDisplayRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String echo) {
		sEcho = echo;
	}

	public String[][] getaaData() {
		return aaData;
	}

	public void setaaData(String[][] aaData) {
		this.aaData = aaData;
	}
}  