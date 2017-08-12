/**    
 * description: 
 *      
 */
package com.pemng.serviceSystem.base.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.xmlbeans.impl.util.Base64;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import com.pemng.framework.dto.DownloadedFile;
import com.pemng.serviceSystem.base.exception.BusinessException;

/**
 * @author luohanbin</a>
 * @version 1.0
 */

public abstract class BaseDownloadAction extends BaseAction {

	private static final int DEFAULT_BUF_SIZE = 1024;

	private int streamBufSize = DEFAULT_BUF_SIZE;

	// private int fileType;

	// private String fileName;

	// private InputStream inputStream;

	// public abstract InputStream getInputStream() throws IOException;
	//
	// public abstract String getFileName();
	//
	// public abstract FileType getFileType();

	protected void doDownload(String fileName, InputStream inputStream,
			FileType fileType) {

		OutputStream outputStrem = null;
		try {
			if (inputStream == null) {
				throw new BusinessException("inputStream is null");
			}
			String agent = this.getRequest().getHeader("USER-AGENT");
			this.getResponse().setBufferSize(streamBufSize);
			// this.getResponse().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType(this.getContentType(fileType));
			
			if (agent != null && agent.indexOf("MSIE") == -1) {
				//FF
				String enableFileName = "=?UTF-8?B?" + (new String(Base64.encode(fileName.getBytes("UTF-8")))) + "?=";
				this.getResponse().setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
			}else{
				this.getResponse().setHeader(
						"Content-disposition",
						"attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));//encodeFileName(fileName));
			}

			outputStrem = this.getResponse().getOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0) {
				outputStrem.write(buffer, 0, len);
			}
			outputStrem.flush();
		}catch(Exception e){ 
			e.printStackTrace();
			log.error(e);
			throw new BusinessException(e);
		}finally {
			if (outputStrem != null) {
				try {
					outputStrem.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private String encodeFileName(String fileName) throws UnsupportedEncodingException {
		String agent = this.getRequest().getHeader("USER-AGENT");
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			return URLEncoder.encode(fileName, "UTF-8");
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
			return MimeUtility.encodeText(fileName, "UTF-8", "B");
		} else {
			return fileName;
		}
	}

	private String getContentType(FileType fileType) {
		if (fileType == FileType.EXCEL_TYPE) { // excel
			return ContentType.EXCEL_FORMAT.toContentTypeString();
		} else if (fileType == FileType.CSV_TYPE) { // csv
			return ContentType.CSV_FORMAT.toContentTypeString();
		} else if (fileType == FileType.PDF_TYPE) { // PDF
			return ContentType.PDF_FORMAT.toContentTypeString();
		}else if (fileType == FileType.OTHER_TYPE) { // 
			return ContentType.TEXT_FORMAT.toContentTypeString();
		} else if(fileType == FileType.DOC_TYPE) {
			return ContentType.WORD_FORMAT.toContentTypeString();
		}else {
			throw new BusinessException("unsupport format [" + fileType
					+ "]");
		}
	}

	public static class FileType {
		private FileType() {
		};

		public static FileType EXCEL_TYPE = new FileType();
		public static FileType CSV_TYPE = new FileType();
		public static FileType PDF_TYPE = new FileType();
		public static FileType OTHER_TYPE = new FileType();
		
		public static FileType DOC_TYPE = new FileType();
	}

	// ==================================setter/getter=======================================

	public int getStreamBufSize() {
		return streamBufSize;
	}

	public void setStreamBufSize(int streamBufSize) {
		this.streamBufSize = streamBufSize;
	}
	
	public static class ContentType {
		
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

	/**
	 * 下载文件，供action调用
	 */
	protected void downloadFile(DownloadedFile downloadedFile) {
		if (downloadedFile == null) {
			throw new BusinessException("inputStream is null");
		}

		OutputStream outputStrem = null;
		InputStream inputStream = null;
		try {
			inputStream = downloadedFile.getInputStream();

			if (inputStream == null) {
				this.getResponse().sendError(404, "File not found!");
				return;
			}

			this.getResponse().setBufferSize(downloadedFile.getStreamBufSize());
			this.getResponse().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType(downloadedFile.getContentType().toContentTypeString());
			String fileName = encodeFileName(downloadedFile.getDisplayName());

			this.getResponse().addHeader("Content-disposition", "attachment;filename=" + fileName);
			// this.getResponse().addHeader("Content-disposition",
			// "attachment;filename=" + new
			// String(downloadedFile.getFileName().getBytes("gbk"),
			// "ISO-8859-1"));

			// this.getResponse().reset(); 
			outputStrem = this.getResponse().getOutputStream();
			byte[] buffer = new byte[8192];
			int len;
			while ((len = inputStream.read(buffer)) > 0) {
				outputStrem.write(buffer, 0, len);
			}
			outputStrem.flush();
		} catch (Exception e) {
			throw new BusinessException(e);
		} finally {
			if (outputStrem != null) {
				try {
					outputStrem.close();
				} catch (IOException e) {
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
