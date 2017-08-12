package com.pemng.serviceSystem.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.output.FileWriterWithEncoding;

import com.pemng.serviceSystem.common.office.GlobalData;

public class PeFileUtil {

	/**
	 * 返回委托书文件的路径名字符串
	 * 
	 * @param cmsId
	 *            : 委托书ID
	 * @param printType
	 *            : 打印类型
	 * @param authenticateType
	 *            : 鉴定类型
	 * @param fileType
	 *            : 文件类型(html、doc)
	 * @return
	 */
	public  String getCmsFilePath(String padCode, int printType,int authenticateType, String fileType) {
		
		String cmsFilePath = null;
		// 输出文档路径及名称
		if (printType == 1) {
			// 委托书
			cmsFilePath = GlobalData.aucate + padCode + "." + fileType;

		} else if (printType == 2) {
			// 取件通知
			cmsFilePath = GlobalData.getnote + padCode + "." + fileType;
		} else if (printType == 3) {
			// 送达回执
			cmsFilePath = GlobalData.sendback + padCode + "." + fileType;
		} else if (printType == 4) {
			// 鉴定结论
			if (authenticateType == 1) {
				// 1：刑事:
				cmsFilePath = GlobalData.criminal + padCode + "." + fileType;
			} else if (authenticateType == 2) {
				// 2:行政执法，纪检监察，其他
				cmsFilePath = GlobalData.nocriminal + padCode + "." + fileType;
			}

		}
		
		return cmsFilePath;
	}
	
	
	/**
	 * 返回委托书文件(doc)的路径名字符串
	 * 
	 * @param cmsId
	 *            : 委托书ID
	 * @param printType
	 *            : 打印类型
	 * @param authenticateType
	 *            : 鉴定类型
	 * @return
	 */
	public  String getCmsDocPath(String padCode, int printType,int authenticateType) {
		return getCmsFilePath(padCode, printType, authenticateType, "doc");
	}
	
	/**
	 * 返回委托书文件(html)的路径名字符串
	 * 
	 * @param cmsId
	 *            : 委托书ID
	 * @param printType
	 *            : 打印类型
	 * @param authenticateType
	 *            : 鉴定类型
	 * @return
	 */
	public  String getCmsHtmlPath(String padCode, int printType,int authenticateType) {
		return getCmsFilePath(padCode, printType, authenticateType, "html");
	}
	
	/**
	 * 测试此路径的文件是否存在
	 * @param filePath
	 * @return
	 */
	public  boolean isFileExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
	/**
	 * 返回此路径的文件内容
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public  String getFileContent(String filePath , String encoding) throws FileNotFoundException, UnsupportedEncodingException {
		StringBuilder content = new StringBuilder("");
		Scanner  scanner = null;
		//File file = new File(filePath);
		BufferedReader reader = null;  
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), encoding);
		reader = new BufferedReader(isr);  
		
		String tempString = null;
		try {
			while ((tempString = reader.readLine()) != null){  
				content.append(tempString);
				content.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}   finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		   
			
//		scanner = new Scanner(file, "UTF-8");
//		while(scanner.hasNextLine()) {
//			content.append(scanner.nextLine() +"\n");
//		}
		
		return content.toString();
	}
	
	
	/**
	 * 返回此路径的文件内容
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public  String getFileContent(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
		return getFileContent(filePath, "UTF-8");
	}
	
	
	/**
	 * 保存文件
	 * @param filePath		: 文件的路径
	 * @param fileSource	: 文件的内容
	 * @return
	 * @throws IOException 
	 */
	public  void saveFile(String filePath , String fileSource) throws IOException {
		FileWriterWithEncoding file = new FileWriterWithEncoding(filePath , "UTF-8");
		file.write(fileSource);
		file.close();
	}
	
	public static void main(String[] args) {
		String filePath = "E:\\test.txt";
		String fileSource = "我靠";
		PeFileUtil u = new PeFileUtil();
		try {
			u.saveFile(filePath, fileSource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String replaceFileExtension(String filePath , String oldExtension , String newExtension) {
		String oldRegex = oldExtension + "$";
		return replace(filePath, oldRegex, newExtension);
		
	}
	
	public String replace(String input , String regex , String targetStr) {
		
		String result = null;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		result = matcher.replaceFirst(targetStr);
		
		return result;
	}

	
}
