package com.pemng.common.util.excelparser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class SimpleWriter {

	protected Log log = LogFactory.getLog(this.getClass());

	private ExcelWriterUtil excel = new ExcelWriterUtil(log, null, true);
	private List<SheetValue> sheetValues = new ArrayList<SheetValue>();
	private HSSFCellStyle defaultStyle = getCellStyle(excel);
	private HSSFCellStyle titleStyle = getTitleCellStyle(excel);

	/**
	 * Function Name               addSheet                                   
	 * @param                      //参数1说明
	 * @param                      //参数2说明
	 * @return                     //函数返回值的说明
	 * @description                //添加sheet  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            2013-4-27          weiyisheng         Initial
	 **********************************************************************
	 */
	public void addSheet(String sheetName, String[] headers,
			Object[][] cellDatas) {
		SheetValue sheet = new SheetValue();
		sheet.sheetName = sheetName;
		sheet.headers = headers;
		sheet.cellDatas = cellDatas;
		if (CollectionUtils.isEmpty(sheetValues)) {
			sheetValues = new ArrayList<SheetValue>();
		}
		sheetValues.add(sheet);
	}

	public void addSheet(String[] headers, Object[][] cellDatas) {
		addSheet(null, headers, cellDatas);
	}

	public void setColumnWidth(int sheetIndex, Short column, Short width) {
		if (sheetIndex >= 0 && !CollectionUtils.isEmpty(sheetValues)) {
			sheetValues.get(sheetIndex).columnWidthMap.put(column, width);
		}
	}
	
	/**
	 * Function Name               setColumnWidth                                   
	 * @param                      //sheet编号
	 * @param                      //宽度map
	 * @description                //设置列宽度 			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            2013-4-27          weiyisheng         Initial
	 **********************************************************************
	 */
	public void setColumnWidth(int sheetIndex, Map<Short, Short> widthMap) {
		if (sheetIndex >= 0 && !CollectionUtils.isEmpty(sheetValues) && !CollectionUtils.isEmpty(widthMap)) {
			Map<Short, Short> map = sheetValues.get(sheetIndex).columnWidthMap;
			for(Map.Entry<Short, Short> enty: widthMap.entrySet()){
				map.put(enty.getKey(), enty.getValue());
			}
		}
	}
	
	public void setColumnWidth(int sheetIndex, short columnNum) {
		if (sheetIndex >= 0 && !CollectionUtils.isEmpty(sheetValues) && columnNum > 0) {
			Map<Short, Short> map = sheetValues.get(sheetIndex).columnWidthMap;
			for(short i = 0, len = columnNum; i < len; i++){
				map.put(i, columnNum);
			}
		}
	}
	
	public void setAutoSizeColumn(int sheetIndex, boolean isAutoSizeColumn) {
		if (sheetIndex >= 0 && !CollectionUtils.isEmpty(sheetValues)) {
			sheetValues.get(sheetIndex).isAutoSizeColumn = isAutoSizeColumn;
		}
	}
	
	/**
	 * Function Name               writeExcel                                   
	 * @description                //将excel导出到输入流  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            2013-4-27          weiyisheng         Initial
	 **********************************************************************
	 */
	public InputStream writeExcel() {
		if (!CollectionUtils.isEmpty(sheetValues)) {
			for (SheetValue sheet : sheetValues) {
				if (StringUtils.isBlank(sheet.sheetName)) {
					excel.createNewSheet();
				} else {
					excel.createNewSheet(sheet.sheetName);
				}
				if (!ObjectUtils.isEmpty(sheet.headers)) {
					for (int i = 0, len = sheet.headers.length; i < len; i++) {
						if (sheet.isAutoSizeColumn) {
							excel.autoSizeColumn(i);
						}
						excel.write(0, i, sheet.headers[i], titleStyle);
						if (null != MapUtils.getShort(sheet.columnWidthMap, (short) i)) {
							excel.setColumnWidth((short) i, sheet.columnWidthMap.get((short) i));
						}
					}
				}
				int rowIndex = 1;
				if (!ObjectUtils.isEmpty(sheet.cellDatas)) {
					for (Object[] rows : sheet.cellDatas) {
						if (!ObjectUtils.isEmpty(rows)) {
							for (int colIdx = 0, len = rows.length; colIdx < len; colIdx++) {
								excel.write(rowIndex, colIdx, rows[colIdx], defaultStyle);
							}
						}
						rowIndex++;
					}
				}
			}
		}
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream(10240);
		excel.writeExcel(outputstream);
		ByteArrayInputStream inputstream = new ByteArrayInputStream(
				outputstream.toByteArray());
		return inputstream;
	}
	
	/**
	 * Function Name               writeExcel                                   
	 * @description                //将excel导出到输入流  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            2013-4-27          weiyisheng         Initial
	 **********************************************************************
	 */
	public void writeExcel(OutputStream outputStream) {
		if (!CollectionUtils.isEmpty(sheetValues)) {
			for (SheetValue sheet : sheetValues) {
				if (StringUtils.isBlank(sheet.sheetName)) {
					excel.createNewSheet();
				} else {
					excel.createNewSheet(sheet.sheetName);
				}
				if (!ObjectUtils.isEmpty(sheet.headers)) {
					for (int i = 0, len = sheet.headers.length; i < len; i++) {
						if (sheet.isAutoSizeColumn) {
							excel.autoSizeColumn(i);
						}
						excel.write(0, i, sheet.headers[i], titleStyle);
						if (null != MapUtils.getShort(sheet.columnWidthMap, (short) i)) {
							excel.setColumnWidth((short) i, sheet.columnWidthMap.get((short) i));
						}
					}
				}
				int rowIndex = 1;
				if (!ObjectUtils.isEmpty(sheet.cellDatas)) {
					for (Object[] rows : sheet.cellDatas) {
						if (!ObjectUtils.isEmpty(rows)) {
							for (int colIdx = 0, len = rows.length; colIdx < len; colIdx++) {
								excel.write(rowIndex, colIdx, rows[colIdx], defaultStyle);
							}
						}
						rowIndex++;
					}
				}
			}
		}
		excel.writeExcel(outputStream);
	}

	/**
	 * Function Name getTitleCellStyle
	 * 
	 * @throws IOException
	 * @description //表头样式
	 */
	private HSSFCellStyle getTitleCellStyle(ExcelWriterUtil writer) {
		HSSFCellStyle contentStyle = writer.newCellStyle();
		contentStyle = writer.newCellStyle();
		contentStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		contentStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return contentStyle;
	}

	/**
	 * Function Name getCellStyle
	 * 
	 * @throws IOException
	 * @description //表格样式
	 */
	private HSSFCellStyle getCellStyle(ExcelWriterUtil writer) {
		HSSFCellStyle contentStyle = writer.newCellStyle();
		contentStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);// 单元格水平居中
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 右对齐对齐
		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contentStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		contentStyle.setRightBorderColor(HSSFColor.BLACK.index);
		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contentStyle.setTopBorderColor(HSSFColor.BLACK.index);
		return contentStyle;
	}

	private static class SheetValue {
		String sheetName;
		String[] headers;
		Object[][] cellDatas;
		boolean isAutoSizeColumn;
		Map<Short, Short> columnWidthMap = new HashMap<Short, Short>(10);
	}
	
	public static void main(String[] args) {
		
	}
}