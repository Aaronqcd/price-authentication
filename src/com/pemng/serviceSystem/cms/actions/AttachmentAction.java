package com.pemng.serviceSystem.cms.actions;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.base.util.Propertiesconfiguration;
import com.pemng.serviceSystem.cms.services.AttachmentService;
import com.pemng.serviceSystem.pojo.TAttachment;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;



public class AttachmentAction extends BaseAction {

	private Long id ;
	private AttachmentService attachmentService;
	
	private static final String GIF = "image/gif;charset=UTF-8";// 设定输出的类型  
	  
    private static final String JPG = "image/jpeg;charset=UTF-8";
	
	public void getAttachmentRes() throws IOException {
		HttpServletRequest request  	= ServletActionContext.getRequest();
		HttpServletResponse response 	= ServletActionContext.getResponse();
		
		TAttachment attachment = attachmentService.getAttachment(id);
		
		String attachmentPath = Propertiesconfiguration.getStringProperty("FILE_CMS_ATTR_DIR");;
		String fileName = attachment.getSaveFileNm();
		String filePath = attachmentPath + "/" +fileName;
		
		
		response.reset();  
		  
        OutputStream output = response.getOutputStream();// 得到输出流  
        if (filePath.toLowerCase().endsWith(".jpg"))// 使用编码处理文件流的情况：  
        {  
            response.setContentType(JPG);// 设定输出的类型  
            // 得到图片的真实路径  
  
            // 得到图片的文件流  
            InputStream imageIn = new FileInputStream(new File(filePath));  
            // 得到输入的编码器，将文件流进行jpg格式编码  
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);  
            // 得到编码后的图片对象  
            BufferedImage image = decoder.decodeAsBufferedImage();  
            // 得到输出的编码器  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);  
            encoder.encode(image);// 对图片进行输出编码  
            imageIn.close();// 关闭文件流  
        }  
        if (filePath.toLowerCase().endsWith(".gif"))// 不使用编码处理文件流的情况：  
        {  
            response.setContentType(GIF);  
            ServletContext context = getServletContext();// 得到背景对象  
            InputStream imageIn = context.getResourceAsStream(filePath);// 文件流  
            BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流  
            BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流  
            byte data[] = new byte[4096];// 缓冲字节数  
            int size = 0;  
            size = bis.read(data);  
            while (size != -1) {  
                bos.write(data, 0, size);  
                size = bis.read(data);  
            }  
            bis.close();  
            bos.flush();// 清空输出缓冲流  
            bos.close();  
        }  
        output.close(); 
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	
}
