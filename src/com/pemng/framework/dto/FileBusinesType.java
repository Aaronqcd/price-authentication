package com.pemng.framework.dto;

import com.pemng.serviceSystem.base.util.Propertiesconfiguration;

public enum FileBusinesType {
	TEMP("temp", "temp"), 
	OTHER("fileEntity", "other"),
	BASICDATA("fileEntity", "basicdata"),
	PROMAINPORLEM("fileEntity","promainporlem"),
	OLDPARTSRETURN("fileEntity" , "oldPartsReturn"),
	VSTATION("fileEntity","vstation"),
	SALES("fileEntity","sales");
	//TODO 其余模块
	
	private String storageType;
	private String businessFolder;
	
	FileBusinesType(String storageType, String businessFolder){ //默认私有
		this.storageType = storageType;
		this.businessFolder = businessFolder;
	}
	
	/**
	 * 业务文件夹名称
	 * @return
	 */
	public String getBusinessFolder(){
		return businessFolder;
	}
		
	/**
	 *文件路径
	 * @return
	 */
	public String getBusinessFilePath(){
		if("temp".equals(storageType))
			return Propertiesconfiguration.getFileTempDir();
		else
			return Propertiesconfiguration.getFileStorageDir() + "/" + businessFolder;
	}
}
