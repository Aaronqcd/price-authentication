package com.pemng.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class DownFile {
	public static void  downfileByPath(HttpServletResponse reponse,String file) throws IOException{
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	    OutputStream fos = null;
        InputStream fis = null;
        String  filepath=file;//本地绝对路径
        File uploadFile = new File(filepath);
        fis = new FileInputStream(uploadFile);
        bis = new BufferedInputStream(fis);
        fos = reponse.getOutputStream();
        bos = new BufferedOutputStream(fos);     
	    reponse.setHeader("Content-disposition","attachment;filename=" +URLEncoder.encode(uploadFile.getName(), "utf-8"));
	    int bytesRead = 0;
	    //用输入流进行先读，然后用输出流去写
	    byte[] buffer = new byte[8192];
	    while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
	    	bos.write(buffer, 0, bytesRead);
	    }         
	    bos.flush();
	    fis.close();
        bis.close();
        fos.close();
        bos.close();         
	  }
	public static void  downfileByPath(HttpServletResponse reponse,String file,String saveFileName) throws IOException{
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	    OutputStream fos = null;
        InputStream fis = null;
        String  filepath=file;//本地绝对路径
        File uploadFile = new File(filepath);
        fis = new FileInputStream(uploadFile);
        bis = new BufferedInputStream(fis);
        fos = reponse.getOutputStream();
        bos = new BufferedOutputStream(fos);     
	    reponse.setHeader("Content-disposition","attachment;filename=" +URLEncoder.encode(saveFileName, "utf-8"));
	    int bytesRead = 0;
	    //用输入流进行先读，然后用输出流去写
	    byte[] buffer = new byte[8192];
	    while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
	    	bos.write(buffer, 0, bytesRead);
	    }         
	    bos.flush();
	    fis.close();
        bis.close();
        fos.close();
        bos.close();         
	  }
}
