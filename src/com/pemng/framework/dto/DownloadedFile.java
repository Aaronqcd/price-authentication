package com.pemng.framework.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import com.pemng.serviceSystem.base.exception.BusinessException;


//下载文件封装对象
public class DownloadedFile extends DownloadedStream implements Serializable {

	private String fileName;
	private String fileRelativePath;
	private FileBusinesType fileBusinessType;
	private String absoluteFilePath; //绝对路径优先于相对路径
	
	public DownloadedFile(){
		
	}
	
	public DownloadedFile(String fileName, ContentType contentType){
		this(fileName, contentType, fileName);
	}
	
	public DownloadedFile(String fileName, ContentType contentType, String displayName){
		super(contentType, displayName);
		this.fileName = fileName;
	}
	


//	public InputStream getInputStream() {
//		return inputStream;
//	}
//
//	public void setInputStream(InputStream inputStream) {
//		this.inputStream = inputStream;
//	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileRelativePath() {
		return fileRelativePath;
	}

	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}

	public FileBusinesType getFileBusinessType() {
		return fileBusinessType;
	}

	public void setFileBusinessType(FileBusinesType fileBusinessType) {
		this.fileBusinessType = fileBusinessType;
	}

	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}

	public void setAbsoluteFilePath(String absoluteFilePath) {
		this.absoluteFilePath = absoluteFilePath;
	}

	public InputStream getInputStream() {
		
		InputStream inputStream = super.getInputStream();
		if(inputStream != null)
			return inputStream;
		
		File file = new File(absoluteFilePath);
		try {
			if(!file.exists())file.createNewFile();
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
			throw new BusinessException(e);
		}catch (IOException ex) {
			ex.printStackTrace(); 
			throw new BusinessException(ex);
		}
		return inputStream;
	}
	
	
}
