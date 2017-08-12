package com.pemng.framework.dto;

import java.io.File;
import java.io.Serializable;


public class UploadedFile implements Serializable{
	private File file;
	private String fileFileName;
	private String fileContentType;
	
	private FileBusinesType fileBusinessType = FileBusinesType.TEMP;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public FileBusinesType getFileBusinessType() {
		return fileBusinessType;
	}
	public void setFileBusinessType(FileBusinesType fileBusinessType) {
		this.fileBusinessType = fileBusinessType;
	}
	
	
}
