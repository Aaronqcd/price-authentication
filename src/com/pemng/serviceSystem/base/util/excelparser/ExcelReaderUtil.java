package com.pemng.serviceSystem.base.util.excelparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 是无法在整个sheet里获取列数的，每一行有每一行实际的列数，如果单元格无值，认为列是不存在的 本工具默认不存在的单元格的值为""
 * 
 * @author luohanbin
 * @version 1.0 基于POI3.6
 */

public class ExcelReaderUtil {

	private Workbook workbook;

	protected Sheet sheet;

	private int numOfSheet;

	private int lastRowNum; // POI sheet没有一个绝对的行数
	
	private boolean isMergedCellUseFirstCellValue; // 是否设置合并单元格获得第一个值

	private Set alreadyInitMergedCellValueSet; // 当前sheet的合并单元格的数据
	
	protected final Log logger = LogFactory.getLog(getClass());

	public ExcelReaderUtil() {}

	/**
	 * 根据文件判断是否为EXCEL，并返回相应的Workbook
	 * 
	 * @param finName
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public void createWorkBook(String fileName, InputStream is) throws IOException {
		if (fileName.toLowerCase().endsWith("xls")) {
			workbook = new HSSFWorkbook(is);
		} else if (fileName.toLowerCase().endsWith("xlsx")) {
			workbook = new XSSFWorkbook(is);
		} else {
			workbook = null;
			logger.info("上传文件格式不正确");
		}
		numOfSheet = 0;
	}

	/**
	 * 合并单元格是否使用第一个单元格值，‘是’则在获取合并单元格其余单元格时数据不会为空
	 * 
	 * @param isMergedCellUseFirstCellValue
	 */
	public ExcelReaderUtil(boolean isMergedCellUseFirstCellValue) {
		this.isMergedCellUseFirstCellValue = isMergedCellUseFirstCellValue;
	}

	/**
	 * 创建poi文件解析对象
	 * 
	 * @param inputStream
	 */
	public void read(InputStream inputStream) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
		workbook = new HSSFWorkbook(fs);
		numOfSheet = workbook.getNumberOfSheets();
		alreadyInitMergedCellValueSet = new HashSet(numOfSheet);
		try {
			locateSheet(0);
		} catch (NoSuchSheetException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 定位sheet
	 * 
	 * @param index
	 * @throws NoSuchSheetException
	 */
	public void locateSheet(int index) throws NoSuchSheetException {
		if (index >= workbook.getNumberOfSheets()){
			logger.info("Sheet越界");
			throw new NoSuchSheetException("Sheet index (" + index + ") is out of range (0.." + (workbook.getNumberOfSheets() - 1) + ")");
		}
		sheet = (Sheet) workbook.getSheetAt(index);
		sheetInit();
		//setOtherMergedCellValue();
	}

	/**
	 * 定位到某个名称的sheet
	 * 
	 * @param sheetName
	 * @throws NoSuchSheetException
	 */
	public void locateSheet(String sheetName) throws NoSuchSheetException {
		Sheet sheet = (Sheet) workbook.getSheet(sheetName);
		if (sheet == null)
			throw new NoSuchSheetException("Sheet " + sheetName + "no exist");
		this.sheet = sheet;
		sheetInit();
		//setOtherMergedCellValue();
	}

	protected void sheetInit() {
		this.lastRowNum = sheet.getLastRowNum();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.svw.vps.framework.util.ExcelReader#getSheetName()
	 */
	public String getSheetName() {
		return sheet.getSheetName();
	}

	/**
	 * 获取最后一个有值单元格位置，此方法常用作判断sheet该有几列, getPhysicalNumberOfCells返回的是有值的单元格的数量
	 * 基于0开始计算
	 * 
	 * @param row
	 * @return
	 */
	public int getLastCellNum(int row) {
		Row hssfRow = getRow(sheet, row);
		if (hssfRow != null)
			return hssfRow.getLastCellNum() - 1;
		return 0;
	}

	private ParseException createParseExceptionIfNull(ParseException parseException, int row, int column, Exception e) {
		if (parseException == null)
			parseException = new ParseException();
		ParseError error = new ParseError();
		error.sheetName = getSheetName();
		error.errPosition = EcxelCellDescriptionConvert.convertDigital2Letter(row, column);
		error.exception = e;
		try {
			error.actualValue = getStringCellValue(row, column);
		} catch (Exception ee) {
			ee.printStackTrace();
			error.actualValue = "错误(单元格无法识别)";
		}
		parseException.addParseError(error);
		return parseException;
	}

	/**
	 * 合并单元格的值一定在第一个 有可能会影响公式值
	 */
	private void setOtherMergedCellValue() {
		if (!alreadyInitMergedCellValueSet.contains(sheet.getSheetName())
				&& isMergedCellUseFirstCellValue) {
			alreadyInitMergedCellValueSet.add(sheet.getSheetName());

			int mergedNum = sheet.getNumMergedRegions();
			for (int i = 0; i < mergedNum; i++) {
				CellRangeAddress cellMerged = sheet.getMergedRegion(i);
				Cell valueCell = (Cell) sheet.getRow(
						cellMerged.getFirstRow()).getCell(
						cellMerged.getFirstColumn());
				for (int m = cellMerged.getFirstRow(); m <= cellMerged
						.getLastRow(); m++)
					for (int n = cellMerged.getFirstColumn(); n <= cellMerged
							.getLastColumn(); n++) {
						if (m == cellMerged.getFirstRow()
								&& n == cellMerged.getFirstColumn())
							continue;
						Cell cell = (Cell) sheet.getRow(m)
								.createCell(n, valueCell.getCellType());
						setMergedCellValue(cell, valueCell);
					}
			}
		}
	}

	/**
	 * 可返回日期 java.util.Date
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Object getCellValue(int row, int col) {
		Cell cell = getCell(sheet, row, col);
		return getCellValue(cell);
	}

	private Object getCellValue(Cell cell) {
		if (cell == null)
			return null;
		int cellType = cell.getCellType();
		if (cellType == Cell.CELL_TYPE_BLANK) {
			return cell.getRichStringCellValue().getString();
		}
		if (cellType == Cell.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			}
			return new Double((cell.getNumericCellValue()));
		}
		if (cellType == Cell.CELL_TYPE_BOOLEAN) {
			return Boolean.valueOf(cell.getBooleanCellValue());
		}
		if (cellType == Cell.CELL_TYPE_ERROR) {
		}
		if (cellType == Cell.CELL_TYPE_FORMULA) {
			try {
				return new Double(cell.getNumericCellValue());
			} catch (RuntimeException e) {
			}

			try {
				return Boolean.valueOf(cell.getBooleanCellValue());
			} catch (RuntimeException e) {
			}

			try {
				return cell.getStringCellValue();
			} catch (RuntimeException e) {
				System.err.println("Error: " + e.getMessage() + " \n Cell["
						+ cell.getRow().getRowNum() + ","
						+ cell.getColumnIndex() + "]  Formula:"
						+ cell.getCellFormula());
			}
		}
		if (cellType == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		}
		return null;
	}

	/**
	 * 返回Date类型数据
	 * @param row
	 * @param col
	 * @param format
	 * @return
	 * @throws java.text.ParseException
	 */
	public Date getCellDateValue(int row, int col, String format) throws java.text.ParseException {
		Object o = getCellValue(row, col);
		if (o == null)
			return null;

		if (java.util.Date.class.isInstance(o)) {
			return (java.util.Date) o;
		}

		String parseStr = o.toString();
		if (parseStr.trim().equals(""))
			return null;

		DateFormat df = new SimpleDateFormat(format);
		return df.parse(o.toString());
	}

	private void setMergedCellValue(Cell cell, Cell valueCell) {
		int cellType = cell.getCellType();
		if (cellType == Cell.CELL_TYPE_BLANK) {
		}
		if (cellType == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellValue(Double.parseDouble(getCellValue(valueCell)
					.toString()));
		}
		if (cellType == Cell.CELL_TYPE_BOOLEAN) {
			cell.setCellValue(Boolean.getBoolean(getCellValue(valueCell)
					.toString()));
		}
		if (cellType == Cell.CELL_TYPE_ERROR) {
		}
		if (cellType == Cell.CELL_TYPE_FORMULA) {
			try {
				Object val = getCellValue(valueCell);
				if (val == null) {
				} else if (String.class.isInstance(val)) {
					cell.setCellValue((String) val);
				} else if (Double.class.isInstance(val))
					cell.setCellValue(Double.parseDouble(val.toString()));
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		if (cellType == Cell.CELL_TYPE_STRING) {
			cell.setCellValue((String) getCellValue(valueCell));
		}
	}

	/**
	 * 获取字符型的行列值,如果是日期返回格式为"yyy-MM-dd"
	 * 
	 * @param cell
	 *            列对象
	 * @param rowNo
	 *            行号
	 * @param cellNo
	 *            列号
	 * @return
	 */
	public String getStringCellValue(int rowNo, int columnNo) {
		return getStringCellValue(sheet, rowNo, columnNo);
	}

	public Date getCellValueByDateType(int rowNo, int columnNo){
		Cell cell = getCell(sheet, rowNo, columnNo);
		Date date = cell.getDateCellValue();
		return date;
	}
	/**
	 * 获取字符型的行列值
	 * 
	 * @param cell
	 *            列对象
	 * 
	 * @param rowNo
	 *            行号
	 * @param cellNo
	 *            列号
	 * @return
	 */
	protected static String getStringCellValue(Sheet sheet, int rowNo,
			int columnNo) {
		Cell cell = getCell(sheet, rowNo, columnNo);
		if (cell == null)
			return null;
		int cellType = cell.getCellType();
		if (cellType == Cell.CELL_TYPE_BLANK) {
			return cell.getRichStringCellValue().getString();
		}
		if (cellType == Cell.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				return new SimpleDateFormat("yyyy-MM-dd").format(cell
						.getDateCellValue());
			}
			double val = cell.getNumericCellValue();
			return String.valueOf(val);
		}
		if (cellType == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		}
		if (cellType == Cell.CELL_TYPE_ERROR) {
		}
		if (cellType == Cell.CELL_TYPE_FORMULA) {
			return cell.getRichStringCellValue().getString();
		}

		return StringUtils.trimToEmpty(cell.getRichStringCellValue()
				.getString());
	}

	/**
	 * 获取数字型的行列值
	 * 
	 * @param cell
	 *            列对象
	 * @param rowNo
	 *            行号
	 * @param cellNo
	 *            列号
	 * @throws NumberFormatException
	 *             不是数字时抛出异常
	 * 
	 * @return
	 */
	public Double getNumericCellValue(int rowNo, int columnNo) {
		return getNumericCellValue(sheet, rowNo, columnNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.svw.vps.framework.util.ExcelReader#getIntCellValue(int, int)
	 */
	public Integer getIntCellValue(int rowNo, int columnNo) {
		Double val = getNumericCellValue(rowNo, columnNo);
		if (val == null)
			return null;
		return new Integer(val.intValue());
	}

	private static Row getRow(Sheet sheet, int row) {
		Row hssfRow = (Row) sheet.getRow(row);
		if (hssfRow == null) {
			if (row > sheet.getLastRowNum())
				throw new IndexOutOfBoundsException(" row[" + row + "]");
			else {
				return null;
			}
		}
		return hssfRow;
	}

	private static Cell getCell(Sheet sheet, int row, int column) {
		Row hssfRow = getRow(sheet, row);
		if (hssfRow != null) {
			return (Cell) hssfRow.getCell(column, Row.CREATE_NULL_AS_BLANK);
		}
		return null;
	}

	/**
	 * 获取数字型的行列值
	 * 
	 * @param cell
	 *            列对象
	 * 
	 * @param rowNo
	 *            行号
	 * @param cellNo
	 *            列号
	 * @throws NumberFormatException
	 *             不是数字时抛出异常
	 * 
	 * @return
	 */
	protected static Double getNumericCellValue(Sheet sheet, int rowNo,
			int columnNo) {

		Cell cell = getCell(sheet, rowNo, columnNo);
		if (cell == null)
			return null;

		int cellType = cell.getCellType();

		if (cellType == Cell.CELL_TYPE_NUMERIC) {
			return new Double(cell.getNumericCellValue());
		}
		if (cellType == Cell.CELL_TYPE_FORMULA) {
			return new Double(cell.getNumericCellValue());
		}

		// 剩余的当作是string处理
		String value = cell.getRichStringCellValue().getString();
		if (value == null || "".equals(value.trim())) {
			return null;
		} else {
			Double val = Double.valueOf(value.trim());
			return val;
		}
	}

	/**
	 * 返回根据行列号返回Integer
	 * 
	 * @param rowNo
	 * @param columnNo
	 * @param getIntegerWay
	 *            1是舍弃小数位， 2是四舍五入， 3是向下取整， 4是向上取整
	 * @return
	 */
	public Integer getIntegerCellValue(int rowNo, int columnNo) {
		return getIntegerCellValue(rowNo, columnNo, GetIntegerStrategy.DIRECT);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.svw.vps.framework.util.ExcelReader#getIntegerCellValue(int, int,
	 *      com.svw.vps.framework.util.ExcelReaderUtil.GetIntegerStrategy)
	 */
	public Integer getIntegerCellValue(int rowNo, int columnNo,
			GetIntegerStrategy getIntegerStrategy) {
		Double d = getNumericCellValue(rowNo, columnNo);
		if (d == null)
			return null;

		return getIntegerStrategy.toInteger(d);
	}

	public static void main(String[] args) {

		System.out.println("firewall slow");

		try {
			Thread.sleep(1000 * 20);
			System.out.println("test jps");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		try {
			test();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// importBasePayLisp();
		String value = null;
		String.valueOf(value);
	}

	private static void test() throws FileNotFoundException, IOException,
			ParseException {

		System.out.println("======================================");
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
				"C:/Documents and Settings/admin/桌面/1 report-flex.xls"));// 创建poi文件解析对象
		Workbook book = new HSSFWorkbook(fs);
		Sheet sheet = (Sheet) book.getSheetAt(0);// 得到第一个工作薄

		ExcelReaderUtil reader = new ExcelReaderUtil();
		reader.read(new FileInputStream(
				"C:/Documents and Settings/admin/桌面/1 report-flex.xls"));
		System.out.println(sheet.getRow(1).getCell(1).getNumericCellValue());
		System.out.println(sheet.getRow(3).getCell(2).getNumericCellValue());
		System.out.println(sheet.getRow(2).getCell(1).getNumericCellValue());
		sheet.groupRow(2, 5);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.svw.vps.framework.util.ExcelReader#getLastRowNum()
	 */
	public int getLastRowNum() {
		return lastRowNum;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.svw.vps.framework.util.ExcelReader#getNumOfSheet()
	 */
	public int getNumOfSheet() {
		return numOfSheet;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.svw.vps.framework.util.ExcelReader#isMergedCellUseFirstCellValue()
	 */
	public boolean isMergedCellUseFirstCellValue() {
		return isMergedCellUseFirstCellValue;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.svw.vps.framework.util.ExcelReader#setMergedCellUseFirstCellValue(boolean)
	 */
	public void setMergedCellUseFirstCellValue(
			boolean isMergedCellUseFirstCellValue) {
		this.isMergedCellUseFirstCellValue = isMergedCellUseFirstCellValue;
	}

	/**
	 * 返回的单元格类型
	 * 
	 * @param rowNo
	 * @param columnNo
	 * @return
	 */
	public int getCellType(int rowNo, int columnNo) {
		Row rows = (Row) sheet.getRow(rowNo); // 读取sheet1中的第i行

		if (rows != null) {
			Cell cell = (Cell) rows.getCell(columnNo);
			if (cell != null) {
				return cell.getCellType();
			}
		}
		return -1;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Set getAlreadyInitMergedCellValueSet() {
		return alreadyInitMergedCellValueSet;
	}

	public void setAlreadyInitMergedCellValueSet(Set alreadyInitMergedCellValueSet) {
		this.alreadyInitMergedCellValueSet = alreadyInitMergedCellValueSet;
	}

	public void setNumOfSheet(int numOfSheet) {
		this.numOfSheet = numOfSheet;
	}

	public void setLastRowNum(int lastRowNum) {
		this.lastRowNum = lastRowNum;
	}
	/**
	 * 获取字符型的行列值
	 * 
	 * @param cell
	 *            列对象
	 * 
	 * @param rowNo
	 *            行号
	 * @param cellNo
	 *            列号
	 * @return 返回Excel原样的值
	 * @author zhoujunyang
	 */
	protected static String getOrigianlValue(Sheet sheet, int rowNo,
			int columnNo) {
		Cell cell = getCell(sheet, rowNo, columnNo);
		if (cell == null)
			return null;
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);

		return StringUtils.trimToEmpty(cell.getRichStringCellValue()
				.getString());
	}

	/**
	 * 获取字符型的行列值,如果是日期返回格式为"yyy-MM-dd"
	 * 
	 * @param cell
	 *            列对象
	 * @param rowNo
	 *            行号
	 * @param cellNo
	 *            列号
	 * @return
	 */
	public String getOrigianlValue(int rowNo, int columnNo) {
		return getOrigianlValue(sheet, rowNo, columnNo);
	}
	/**
	 * 测试合并的单元格内容
	 * 
	 * @param sheet
	 
	private static void testMergedRegions(Sheet sheet){
	 int rows = sheet.getPhysicalNumberOfRows();
	 System.out.println("rows:" + rows);
	
	 int mergeSize = sheet.getNumMergedRegions(); // test merge region, 注意，如果只有一行，则必不要合并
	 System.out.println("-------mergeSize:----------"+mergeSize);
	 ExcelReaderUtil util=new ExcelReaderUtil();
	 List regionList=util.getFirstSecondRowSortedRegions(sheet,6);// 测试结束的表头行数为6
	 mergeSize=regionList.size();
	 for(int i=0; i<mergeSize; i++){
		 Region region =(Region)regionList.get(i);
		 Cell cell =
		 sheet.getRow(region.getRowFrom()).getCell(region.getColumnFrom());
		 String headerValue =cell.getStringCellValue();
		 System.out.println("----headerValue----:" + headerValue);
		 // AreaReference areaRef=new AreaReference();
		 System.out.println(region.getRowFrom()+ "--" + region.getColumnFrom()+
		 "--" + region.getRowTo()+ "--" + region.getColumnTo());
	 }
	 }
	*/
}