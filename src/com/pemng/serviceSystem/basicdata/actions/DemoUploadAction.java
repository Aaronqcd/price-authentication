package com.pemng.serviceSystem.basicdata.actions;

import java.io.File;

import com.pemng.framework.dto.FileBusinesType;
import com.pemng.framework.dto.UploadedFile;
import com.pemng.serviceSystem.base.actions.BaseAction;

public class DemoUploadAction extends BaseAction{

	private File myFile;
	private String myFileFileName;
	private String myFileContentType;
	
	private Long fileEntityId;
	
//	private FileEntityService fileEntityService;
	
	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public String getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

//	public FileEntityService getFileEntityService() {
//		return fileEntityService;
//	}
//
//	public void setFileEntityService(FileEntityService fileEntityService) {
//		this.fileEntityService = fileEntityService;
//	}

	public String upload(){
		UploadedFile upfile = new UploadedFile();
		upfile.setFile(myFile);
		upfile.setFileFileName(myFileFileName);
		upfile.setFileContentType(myFileContentType);
		upfile.setFileBusinessType(FileBusinesType.BASICDATA);//基础数据模块...etc，其他模块请在FileBusinesType中补充
		
//		MaintStation station = new MaintStation();//对应业务的实体类，如“维修站”
		
//		try {
//			FileEntity fileEntity  = fileEntityService.upload(upfile,"备注", station.getId(), "MAINT_STATION_T");//这里的样例为上传维修站的附件
//			fileEntityId = fileEntity.getId();
//		} catch (IOException e) {
//			this.addActionError("文件上传失败");
//		}
		this.addActionMessage("文件上传成功");
		return SUCCESS;
	}

	public Long getFileEntityId() {
		return fileEntityId;
	}

	public void setFileEntityId(Long fileEntityId) {
		this.fileEntityId = fileEntityId;
	}
}
