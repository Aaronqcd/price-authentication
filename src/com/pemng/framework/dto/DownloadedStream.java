package com.pemng.framework.dto;

/**    
 * description: 
 * Create on 2010-9-4 上午08:27:36    
 *      
 */    

import java.io.InputStream;
import java.io.Serializable;
   

public class DownloadedStream implements Serializable  {

	private InputStream inputStream;
	private String displayName; //显示的名称
	private ContentType contentType;
	private int streamBufSize = DEFAULT_BUF_SIZE;
	
	public static final int DEFAULT_BUF_SIZE = 8192;
	
	public DownloadedStream(){
		
	}
	
	public DownloadedStream(ContentType contentType, String displayName){
		this.contentType = contentType;
		this.displayName = displayName;
	}
	

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setStreamBufSize(int streamBufSize) {
		this.streamBufSize = streamBufSize;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public int getStreamBufSize() {
		return streamBufSize;
	}
	
	
	public static class ContentType {
		
		/** EXCEL格式contentType */
		public static final ContentType EXCEL_FORMAT = new ContentType("application/vnd.ms-excel;charset=UTF-8");
		public static final ContentType CSV_TXT_FORMAT = new ContentType("text/plain;charset=UTF-8");
		public static final ContentType PDF_FORMAT = new ContentType("application/pdf;charset=UTF-8");
		public static final ContentType WORD_FORMAT = new ContentType("application/msword;charset=UTF-8");
		public static final ContentType PPT_FORMAT = new ContentType("application/vnd.ms-powerpoint;charset=UTF-8");
		public static final ContentType ZIP_FORMAT = new ContentType("application/zip;charset=UTF-8");
		public static final ContentType TEXT_FORMAT = new ContentType("application/text;charset=UTF-8");
		public static final ContentType CSV_FORMAT = new ContentType("application/text;charset=GBK");
		
		private String contentTypeString;
		
		private  ContentType(String contentTypeString){
			this.contentTypeString = contentTypeString;
		}
		
		public String toContentTypeString(){
			return contentTypeString;
		}
		
		public static ContentType getContentTypeByFileName(String fileName){
			String fileExt = null;
			if(fileName != null && fileName.lastIndexOf('.') > 0){
				fileExt = fileName.substring(fileName.lastIndexOf('.')+1);
			}
			return getContentTypeByExtName(fileExt);
		}
		
		public static ContentType getContentTypeByExtName(String fileExt){
			if(fileExt != null){
				if("xls".equalsIgnoreCase(fileExt))
					return EXCEL_FORMAT;
				else if("txt".equalsIgnoreCase(fileExt) || "csv".equalsIgnoreCase(fileExt))
					return CSV_TXT_FORMAT;
				else if("doc".equalsIgnoreCase(fileExt))
					return WORD_FORMAT;
				else if("ppt".equalsIgnoreCase(fileExt))
					return PPT_FORMAT;
				else if("zip".equalsIgnoreCase(fileExt))
					return ZIP_FORMAT;				
			}
			return TEXT_FORMAT;
		}
	}
	
}
   