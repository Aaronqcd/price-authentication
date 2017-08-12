package com.pemng.serviceSystem.base.util.excelparser;
   
public class ParseError {

	public String sheetName;
	
	public String errPosition; //错误位置(EXCEL表示方式)
	
	public int errType; //错误类型, 单元格不存在，格式错误（非数字），为空

	public String actualValue;
	
	public Exception exception; //异常
	
	public String toString(){
		return sheetName+" " + errPosition + " " + actualValue;
	}
}
   