package com.pemng.serviceSystem.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * Created on May 17, 2012
 * <p>Description: [描述该类概要功能介绍]</p>
 * @version        1.0
 */
public class FileEntityUtil {

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/dws.txt
	 * @return boolean
	 * @throws IOException
	 */
	public static void copyFile(File oldFile, File newFile) throws IOException {

		if (oldFile.isFile()) { // 文件存在时
			FileInputStream inputStream = null;
			FileOutputStream outputStream = null;
			try {
				inputStream = new FileInputStream(oldFile); // 读入原文件
				outputStream = new FileOutputStream(newFile);
				byte[] buffer = new byte[1024];
				int length = 0;
				while ((length = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
				}
			} finally {
				try {
					outputStream.close();
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/dws.txt
	 * @return boolean
	 * @throws IOException 
	 * @throws IOException
	 */
	public static File copyFile(File upload,String path,String uploadFileName) throws IOException{
		File filePath = new File(path);//绝对路径
		if (!filePath.isDirectory())
			filePath.mkdirs();

		String uuid = UUIDHexGenerator.generateUUID();
		String extName = null; // 扩展名
		int lastDotPos = uploadFileName.lastIndexOf(".");
		if (lastDotPos > 0) {
			extName = uploadFileName.substring(lastDotPos);
		}
		String fullNewName = extName == null ? uuid	: (uuid + extName);
		File newFile = new File(filePath, fullNewName);
//		try {
			FileEntityUtil.copyFile(upload, newFile);
//		} catch (IOException e) {
//		}
		return newFile;
	}
	
	/**
	 * 复制多个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/dws.txt
	 * @return boolean
	 * @throws IOException
	 */
	public static void copyDirectory(File srcPath, File destPath) throws IOException{
//        FileInputStream fis=null;
//        FileOutputStream fos=null;
//        BufferedReader br=null;
//        BufferedWriter bw=null;
//        if(oldFile.isDirectory()){
//            File[] files=oldFile.listFiles();
//            for(int i=0;i<files.length;i++){
//                try {
//                    fis=new FileInputStream(files[i].getAbsoluteFile());//以源文件中的文件的绝对路径创建fis
//                    fos=new FileOutputStream(newFile + File.separator + files[i].getName());//目标文件夹，目标文件夹中的文件名和源相同
//                    br=new BufferedReader(new InputStreamReader(fis));
//                    bw=new BufferedWriter(new OutputStreamWriter(fos));
//                    String line;
//                    while((line=br.readLine())!=null){
//                        bw.write(line);
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally{
//                    try {
//                        br.close();
//                        bw.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
		
		FileUtils.copyDirectory(srcPath, destPath);
	}
	
	/**
	 * 获取文件后缀名
	 * @param name
	 * @return
	 */
	public static String getFileSuffix(String name) {
		if(null == name || "".equals(name)){
			return "";
		}
		String suffix = "";
		suffix = name.substring(name.lastIndexOf(".")+1, name.length());
		return suffix;
	}
	
}
