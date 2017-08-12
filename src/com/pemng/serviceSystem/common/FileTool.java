package com.pemng.serviceSystem.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.pemng.serviceSystem.base.util.UUIDHexGenerator;
/**
 * 文件读写
 */
public class FileTool {
	
	public static String convertInputStreamToString(InputStream is,String contentType) throws IOException{
		if (is != null) {
			Writer writer = new StringWriter(); 
			char[] buffer = new char[1024]; 
			try { 
				Reader reader = new BufferedReader(new InputStreamReader(is, contentType)); 
				int n; 
				while ((n = reader.read(buffer)) != -1) { 
					writer.write(buffer, 0, n); 
				} 
				return writer.toString();
			}catch(Exception e){
				e.printStackTrace();
			}finally { 
				is.close(); 
			}
		} 
		return "";
	}

	
	public static void writeStringToFile(String content,String fileName){
		try {
			File f = new File(fileName);
			FileWriter fw = new FileWriter(f , false);
			fw.write(content);
			fw.flush();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void writeStringToFile(String content,String fileName,String encode){
		try {
			File file = new File(fileName);
			if(file.exists()==false){
				if (!file.getParentFile().isDirectory()) {
					new File(file.getParent()).mkdirs();
				}
				file.createNewFile();   
			}
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file.getPath()),encode));
            out.write(content);
            
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String readFileToString(String fileName,String encode) {
		    StringBuilder sb = new StringBuilder();
		    try {
		    	InputStreamReader input=new InputStreamReader(new FileInputStream(
				        new File(fileName).getAbsoluteFile()),encode);
		      BufferedReader in= new BufferedReader(input);
		      try {
		        String s;
		        while((s = in.readLine()) != null) {
		          sb.append(s);
		          sb.append("\n");
		        }
		      } finally {
		        in.close();
		      }
		    } catch(IOException e) {
		      throw new RuntimeException(e);
		    }
		    return sb.toString();
	 }
	public static String findContent(String html){   
        Pattern p = Pattern.compile("<(\\S*?)[^>]*>.*?| <.*? />"); 
        Matcher m = p.matcher(html);  
        
        String rs = new String(html);   
        // 去掉所有标签
        while (m.find()) {   
            System.out.println(m.group());   
            rs = rs.replace(m.group(), "");   
        }

        return rs;
    }
	
	//去掉<!DOCTYPE>
	public static String toHtml(String str){
		return getTxtStr(str, "<!DOCTYPE((?!<!DOCTYPE).)*\"> |\t|\r|\n");
	}
	
	public static String getTxtWithOutTRN(String str){
        return getTxtStr(str, "\t|\r|\n");
	}
	
	public static String getTxtStr(String str, String pattern){
		Pattern p = Pattern.compile(pattern); 
		return p.matcher(str).replaceAll("");
	}
	
	public static String convertDoc2Html(File inputFile, String output) throws Exception{
		
		File outputFile = new File(output);
		Document doc = null;
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		try{	
			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
			//读取doc
			doc = Jsoup.parse(outputFile, "gbk", "");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (connection != null) {
					connection.disconnect();
					connection = null;
				}
			} catch (Exception e) {
				 System.err.println("文件转换出错，请检查OpenOffice服务是否启动。"); 
				 throw e;
			}
		}
		/** 
		 * html文本过滤    
		 * 
		 * */
		String c = "";
		try {
			c= toHtml(doc.html());
			//c = StringHtml.findContent(doc.html());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return c;
		
	}
	public static String getAutoFileName(String suffix) {
		return (String)(new UUIDHexGenerator()).generate() + "." + suffix.trim();
	}

	public static String getSuffixName(String fileName) {
		int i = fileName.lastIndexOf(".");
		if (i == -1) {
			return "";
		} else {
			return fileName.substring(i + 1);
		}
	}
	
	public static boolean delFile(String fileName) {
		File file = new File(fileName);
		return file.delete();
	}

	public static boolean copyFile(File inputFile, String outputFileDir) throws Exception {
		File file = new File(outputFileDir);
		if(file.exists()==false){
			if (!file.getParentFile().isDirectory()) {
				new File(file.getParent()).mkdirs();
			}
			file.createNewFile();   
		}
		FileInputStream fis = new FileInputStream(inputFile);
		FileOutputStream fos = new FileOutputStream(file.getPath());

		byte[] buffer = new byte[1024];
		int len = 0;

		try {
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			fis.close();
			fos.close();
		}
		return new File(outputFileDir).exists();
	}
	
	/** 
     * 重命名文件或文件夹 
     * @param resFilePath 源文件路径 
     * @param newFilePath 目标文件路径 
     * @param newFileName 重命名 
     * @return 操作成功标识 
     */ 
	public static boolean renameFile(String resFilePath, String newFilePath) { 
           File resFile = new File(resFilePath); 
           File newFile = new File(newFilePath); 
           return resFile.renameTo(newFile); 
   } 
}
