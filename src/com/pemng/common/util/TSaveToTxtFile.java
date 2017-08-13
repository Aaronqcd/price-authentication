package com.pemng.common.util;
/*
 *******************************************************************
*<p>文件名：CharCheck.java
*<p>主要功能: 导出数据
*<p>模块名称：数据转换
*<p>模块编号：SMCU_utiltransform
******************************************************************
 * $Author: liangyan $
 * $Date: 2003/03/15 03:42:37 $
 * $Revision: 1.1 $
 * $Header: /home/cvsroot/TV/4编码/SOURCE/TS/market/src/com/kingstar/mauserinterface/util/transform/ExportFile.java,v 1.1 2003/03/15 03:42:37 liangyan Exp $
 * $Id: ExportFile.java,v 1.1 2003/03/15 03:42:37 liangyan Exp $
 * $State: Exp $
 * $Log: ExportFile.java,v $
 * Revision 1.1  2003/03/15 03:42:37  liangyan
 * 增加文件，用于导出数据
 *
 * Revision 1.2  2003/02/17 22:12:50  liangyan
 * 增加文件头
 *
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;



public class TSaveToTxtFile {

	public TSaveToTxtFile(){
	}
   
    
    public static boolean saveNewFile(String sourceFile, int[] length, String[] headss, DBResultDataSet model) {
		if (!sourceFile.endsWith(".txt")) {
			sourceFile = sourceFile + ".txt";
		}
		File theFile = new File(sourceFile);
		System.out.println("theFile.exists():"+theFile.exists());
		if (!theFile.exists()) {
			try {
				theFile.createNewFile();
				FileWriter outfile = new FileWriter(sourceFile, true);
				int columns = length.length;
				for (int kk = 0; kk < columns; kk++) {
					outfile.write(defineLength(length[kk], headss[kk]));
				}
				outfile.write("\r\n");
				outfile.write("===========================================================================================================================================");
				outfile.write("\r\n");
				outfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (theFile.exists()) {
			try {
				FileWriter outfile = new FileWriter(sourceFile, true);
				int columns = length.length;
				int rows = model.getRowCount();
				for (int i = 0; i < rows; i++) {
					// 取出一行数据
					for (int kk = 0; kk < columns; kk++) {
						outfile.write(defineLength(length[kk], model.getCell(i,kk)));
					}
					outfile.write("\r\n");
				}
				outfile.close();
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
    
    public static boolean saveSTNewFile(String sourceFile, int[] length, String[] headss, DBResultDataSet model) {
		if (!sourceFile.endsWith(".txt")) {
			sourceFile = sourceFile + ".txt";
		}
		File theFile = new File(sourceFile);
		System.out.println("theFile.exists():"+theFile.exists());
		if (!theFile.exists()) {
			try {
				theFile.createNewFile();
				FileWriter outfile = new FileWriter(sourceFile, true);
				int columns = length.length;
				for (int kk = 0; kk < columns; kk++) {
					outfile.write(defineLength(length[kk], headss[kk]));
				}
				outfile.write("\r\n");
				outfile.write("===========================================================================================================================================");
				outfile.write("\r\n");
				String[] titleNew = new String[columns];
				titleNew[0] = "";
				for (int m = 1; m < columns; m += 3) {
					titleNew[m] = "5日均值";
					titleNew[m + 1] = "10日均值";
					titleNew[m + 2] = "20日均值";
				}
				for (int kk = 0; kk < columns; kk++) {
					outfile.write(defineLength(length[kk], titleNew[kk]));
				}
				outfile.write("\r\n");
				outfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (theFile.exists()) {
			try {
				FileWriter outfile = new FileWriter(sourceFile, true);
				int columns = length.length;
				int rows = model.getRowCount();
				for (int i = 0; i < rows; i++) {
					// 取出一行数据
					for (int kk = 0; kk < columns; kk++) {
						outfile.write(defineLength(length[kk], model.getCell(i,kk)));
					}
					outfile.write("\r\n");
				}
				outfile.close();
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
    
    
    public static boolean saveNewFile(String sourceFile, int[] length, String[] headss, DBResultDataSet model,boolean deleted) {
    	if(deleted==true){
    		File theFile = new File(sourceFile);
    		if(theFile.exists()){
    			theFile.delete();
    		}
    		theFile = null;
    		return saveNewFile(sourceFile,length, headss, model);
    	}else{
    		return saveNewFile(sourceFile,length, headss, model);
    	}
    }
   
    
    /**
	 * 产生定长字符
	 * 
	 * @param k
	 *            字符串长度
	 * @param value
	 *            字符串值
	 * @return 指定长度的字符串
	 */
    private static String defineLength(int k,String value){
        int length = 0;
        // 得到 value 长度
        try {
            //CharToByteConverter toByte = CharToByteConverter.getConverter("GB2312");
            //byte[] orig = toByte.convertAll(value.toCharArray());
			//length = orig.length;
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(value);
			byte[] orig = new byte[byteBuffer.remaining()];
            length = orig.length;
        }
        catch (Exception ex) {
        }
        if (length<k){
            StringBuffer sb = new StringBuffer(value);
            for(int i=0;i<k-length;i++){
                sb.append(" ");
            }
            return sb.toString();
        }
        return value;
    }
    
   
}