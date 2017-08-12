package com.pemng.common.util.excelparser;

import java.util.HashMap;
import java.util.Map;

/**
 * // 单元格描述转换，如(0,0)为A0, A1为(1,0)
 * @author Administrator
 *
 */
public class EcxelCellDescriptionConvert {

	private static final char[] letterArray = new char[26];

	private static final Map letterDigitalMap = new HashMap(26);
	static {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		letters.getChars(0, letters.length(), letterArray, 0);
		for (int i = 0; i < letterArray.length; i++) {
			letterDigitalMap.put(String.valueOf(letterArray[i]), new Integer(
					i + 1));
		}
	}

	/**
	 * 根据行列值返回EXCEL单元格位置表示比如 0,0返回A1
	 * 
	 * @param rowNum  从0开始
	 * @param columnNum 从0开始
	 * @return
	 */
	public static String convertDigital2Letter(int rowNum, int columnNum) {
		int tenPlace = columnNum / 26;
		int lastPlace = columnNum % 26;
		if (tenPlace != 0) {
			return String.valueOf(letterArray[tenPlace - 1])
					+ String.valueOf(letterArray[lastPlace]) + (rowNum + 1);
		} else {
			return String.valueOf(letterArray[lastPlace]) + (rowNum + 1);
		}
	}

	/**
	 * 根据EXCEl单元格表示方式返回API所使用的行列,比如A1返回0,0
	 * 
	 * @param excelReprsent
	 * @return 从0开始
	 * 
	 */
	public static int[] convertLetter2Digital(String excelReprsent) {
		excelReprsent = excelReprsent.toUpperCase();
		int letterSize = 0;
		for (int i = 0; i < excelReprsent.length(); i++) {
			char c = excelReprsent.charAt(i);
			if (Character.isLetter(c)) {
				letterSize++;
			}
		}
		String letter = excelReprsent.substring(0, letterSize);
		String rowDigit = excelReprsent.substring(letterSize);
		int column = 0;
		if (letter.length() == 2) {
			column = ((Integer) letterDigitalMap.get(String.valueOf(letter
					.charAt(0)))).intValue()
					* 26
					+ ((Integer) letterDigitalMap.get(String.valueOf(letter
							.charAt(1)))).intValue();
		} else {
			column = ((Integer) letterDigitalMap.get(String.valueOf(letter
					.charAt(0)))).intValue();
		}

		return new int[] { Integer.parseInt(rowDigit) - 1, (column - 1) };
	}
}
