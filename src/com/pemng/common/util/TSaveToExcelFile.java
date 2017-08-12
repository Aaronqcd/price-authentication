/**
 * <p>Description: 保存为Excel文件 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: kingstar</p>
 * <p>Created on:2004-9-16 </p>
 * @author sxx
 * @version 1.0
 */
package com.pemng.common.util;

import java.io.IOException;
import java.io.OutputStream;

import jxl.format.Alignment;


/**
 * @author sjdong
 * 
 * 保存从modellist或者cachefile中读取的数据
 */
public class TSaveToExcelFile {
	
	private String fileName = "";

	private JXLMaker excelFile = null;// add by yuyu 2006-05-11

	//private String tableName = "";

	/**
	 * 构建函数 根据给定的文件名,创建一个新的excel文件 如果文件已经存在,覆盖
	 */
	public TSaveToExcelFile(String tableName) {
		//this.tableName = tableName;
	}


	/**
	 * 改用JXL包生成EXECL表格
	 * 
	 * @author yuyu
	 * @Created on 2006-05-11
	 */
	public void open(String sourceFile) {
		try {
			excelFile = new JXLMaker(sourceFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void open(OutputStream outputStream) {
		try {
			excelFile = new JXLMaker(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean open() {
		if (fileName.equals("")) {
			getSavePath(); // 设置路进
		}
		if (!fileName.equals("")) {
			try {
				excelFile = new JXLMaker(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	public void close() {
		// excelFile.close();
		// TODO:需要ksexcel操作文件中增加此函数
	}
	
	public boolean saveFile(DBResultDataSet inValue, String[] ColumnName) {
		return saveFile(inValue,new String[2],ColumnName,null,false);
	}
	
	public boolean saveFile(DBResultDataSet inValue, String[] ColumnName, String mainTitle) {
		return saveFile(inValue,new String[2],ColumnName,mainTitle,false);
	}

	/**
	 * 文件保存，获得传入的KSTableData中的标题、列显示名、 列类型名、序号、单元格值，调用JXLMaker生成Execl文件
	 * 
	 * @author yuyu
	 * @param srcData
	 * @param tableInt
	 * @param ifData
	 * @return
	 * @create on 2006-05-11
	 */
	public boolean saveFile(DBResultDataSet inValue,String[] date, String[] ColumnName, String mainTitle, boolean appendDateRange) {
		try {

			String[] title = new String[2];
			String[] columnName = ColumnName;
			int colCount = columnName.length;  //header.length
			int[] order = new int[colCount];  //order.length = header.length
			
			// 第一行:显示表名+系统表名
			title[0] = mainTitle;
			if (appendDateRange == true) {// 是历史数据
				String dateLabel = "  查询日期范围  ";
				if(date[0].length()!=0 && date[1].length()!=0){
					String dateFrom = date[0];
					String dateTo = date[1];
					title[1] = dateLabel + dateFrom + " ～ " + dateTo;
				}else{
					title[1]= null;
				}
			}
			if(title[0]== null && title[1]== null){
				title = null;
			}
			// 表格内容在文件中的起始行
			int index = title != null ? (title.length + 1) : 0;


			// 得到序号、列显示名、列属性
			for (int j = 0; j < colCount; j++) {
				order[j] = j;
//				System.out.println(" order["+j+"]:" + order[j]);
			}

			// 文件中添加标题
			if (title != null) {
				for (int i = 0; i < title.length; i++) {
					if(title[i] == null){
						continue;
					}
					excelFile.mergeCells(i, 0, i, columnName.length - 1);
					excelFile.addStringCell(title[i], i, 0, true,
							i == title.length - 1 ? Alignment.RIGHT
									: Alignment.CENTRE);
				}
			}
			if(inValue!=null){
				excelFile.input(index, columnName, order, inValue);
				excelFile.write();
				System.out.println("Save OK");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "error while save file !"
//					+ fileName);
			return false;
		}
	}


	/**
	 * 用户选择保存的文件路进和文件名 路径默认为当前路径,文件名默认为qttn+日期+.xls
	 */
	private void getSavePath() {	
		fileName = "c:\\a.xls";
	}


	public void setFileName(String fileName) {
		if (!fileName.endsWith(".xls")) {
			fileName += ".xls";
		}
		this.fileName = fileName;
	}


	public JXLMaker getExcelFile() {
		return excelFile;
	}
	
}