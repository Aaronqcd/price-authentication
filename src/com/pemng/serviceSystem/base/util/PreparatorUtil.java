package com.pemng.serviceSystem.base.util;  
  
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
  
/** 
 * 文件穿透 
 * @author shaojie 
 * 
 */  
public class PreparatorUtil {  
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();       
      
    private PreparatorUtil(){}       
    static{       
        getAllFileType(); //初始化文件类型信息       
    }       
           
    /**    
     * Created on 2011-02-15     
     * <p>Discription:[getAllFileType,常见文件头信息]</p>    
     * @author:wbin    
     */       
    private static void getAllFileType()       
    {       
        FILE_TYPE_MAP.put("jpg", "FFD8FF"); //JPEG (jpg)       
        FILE_TYPE_MAP.put("png", "89504E47"); //PNG (png)       
        FILE_TYPE_MAP.put("gif", "47494638"); //GIF (gif)       
        FILE_TYPE_MAP.put("tif", "49492A00"); //TIFF (tif)       
        FILE_TYPE_MAP.put("bmp", "89504E470D0A1A0A0000000D4948445200000060000000600806000000E2987738000000017352474200AECE1CE900000004"); //Windows Bitmap (bmp)     
        FILE_TYPE_MAP.put("dwg", "41433130"); //CAD (dwg)       
        FILE_TYPE_MAP.put("html", "68746D6C3E"); //HTML (html)       
        FILE_TYPE_MAP.put("htm", "3C21444F435459504520"); //HTML (html)  
        FILE_TYPE_MAP.put("rtf", "7B5C727466"); //Rich Text Format (rtf)       
        FILE_TYPE_MAP.put("xml", "3C3F786D6C");       
        FILE_TYPE_MAP.put("zip", "504B03040A00000000009");         
        FILE_TYPE_MAP.put("rar", "52617221");       
        FILE_TYPE_MAP.put("psd", "38425053"); //Photoshop (psd)       
        FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A"); //Email [thorough only] (eml)       
        FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F"); //Outlook Express (dbx)       
        FILE_TYPE_MAP.put("pst", "2142444E"); //Outlook (pst)       
        FILE_TYPE_MAP.put("xls", "D0CF11E0A1B11AE1000000000000000000000000000000003E000300FEFF0900060000000000000000000000010000000100"); //MS Word       
        FILE_TYPE_MAP.put("xlsx", "504B030414000600080000002100C8A3");        
        FILE_TYPE_MAP.put("doc", "D0CF11E0A1B11AE1000000000000000000000000000000003E000300FEFF09000600000000000000000000004E0000005600");   
        FILE_TYPE_MAP.put("docx", "504B030414000600080000002100729");       
        FILE_TYPE_MAP.put("pptx", "504B03041400060008000000210036F7");     
        FILE_TYPE_MAP.put("ppt", "D0CF11E0A1B11AE1000000000000000000000000000000003E000300FEFF0900060000000000000000000000020000000100");    
        FILE_TYPE_MAP.put("mdb", "5374616E64617264204A"); //MS Access (mdb)       
        FILE_TYPE_MAP.put("wpd", "FF575043"); //WordPerfect (wpd)        
        FILE_TYPE_MAP.put("eps", "252150532D41646F6265");       
        FILE_TYPE_MAP.put("ps", "252150532D41646F6265");       
        FILE_TYPE_MAP.put("pdf", "255044462D312E"); //Adobe Acrobat (pdf)       
        FILE_TYPE_MAP.put("qdf", "AC9EBD8F"); //Quicken (qdf)       
        FILE_TYPE_MAP.put("pwl", "E3828596"); //Windows Password (pwl)       
        FILE_TYPE_MAP.put("wav", "57415645"); //Wave (wav)       
        FILE_TYPE_MAP.put("avi", "41564920");       
        FILE_TYPE_MAP.put("ram", "2E7261FD"); //Real Audio (ram)       
        FILE_TYPE_MAP.put("rm", "2E524D46"); //Real Media (rm)       
        FILE_TYPE_MAP.put("mpg", "000001BA"); //       
        FILE_TYPE_MAP.put("mov", "6D6F6F76"); //Quicktime (mov)       
        FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); //Windows Media (asf)       
        FILE_TYPE_MAP.put("mid", "4D546864"); //MIDI (mid)       
        FILE_TYPE_MAP.put("sql", "73656C656374200D0");      
        FILE_TYPE_MAP.put("txt", "73656C6563742032303");    
        FILE_TYPE_MAP.put("java", "7061636B61676520636F6D2E");     
    }       
      
      
      
      
    /** 
     * 穿透offic文档 
     * @param path 
     * @return 
     */  
    public static String readOffic(String path) {  
        File inputFile = new File(path);   
        POITextExtractor extractor = null;  
        try {  
            extractor = ExtractorFactory.createExtractor(inputFile);  
        } catch (InvalidFormatException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (OpenXML4JException e) {  
            e.printStackTrace();  
        } catch (XmlException e) {  
            e.printStackTrace();  
        }  
        return extractor.getText().trim();  
    }  
      
    /** 
     * 穿透PDF文件 
     * @param path 
     * @return 
     * @throws Exception 
     */  
//    public static String readPdf(String path) throws Exception {  
//        StringBuffer content = new StringBuffer("");// 文档内容  
//        PDDocument pdfDocument = null;    
//        try {    
//            FileInputStream fis = new FileInputStream(path);    
//            PDFTextStripper stripper = new PDFTextStripper();    
//            pdfDocument = PDDocument.load(fis);    
//            StringWriter writer = new StringWriter();    
//            stripper.writeText(pdfDocument, writer);    
//            content.append(writer.getBuffer().toString());    
//            fis.close();    
//        } catch (java.io.IOException e) {    
//            System.err.println("IOException=" + e);    
//            System.exit(1);    
//        } finally   
//        {    
//            if (pdfDocument != null) {    
//                COSDocument cos = pdfDocument.getDocument();    
//                cos.close();    
//                pdfDocument.close();    
//            }    
//        }  
//        return content.toString().trim();  
//    }  
      
  
      
    /** 
     * 穿透html 保留html标签和css样式 
     * @param urlString 
     * @return 
     */  
    public static String readHtml(String urlString) {  
  
        StringBuffer content = new StringBuffer("");  
        File file = new File(urlString);  
        FileInputStream fis = null;  
        BufferedReader reader = null;  
        try {  
            fis = new FileInputStream(file);  
            // 读取页面  
            reader = new BufferedReader(new InputStreamReader(fis,"utf-8"));//这里的字符编码要注意，要对上html头文件的一致，否则会出乱码  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                content.append(line + "\n");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try {  
                reader.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        String contentString = content.toString();  
        return contentString;  
    }  
      
      
     
      
    /** 
     * 穿透txt 
     * @param path 
     * @return 
     */  
    public static String readTxt(String path) {  
        StringBuffer content = new StringBuffer("");// 文档内容  
        FileReader reader = null;  
        BufferedReader br = null;  
        try {  
            reader = new FileReader(path);  
            br = new BufferedReader(reader);  
            String s1 = null;  
  
            while ((s1 = br.readLine()) != null) {  
                content.append(s1 + "\r");  
            }  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try {  
                br.close();  
                reader.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return content.toString().trim();  
    }  
      
      
      
    public static String getFileHexString(byte[] b)       
    {       
        StringBuilder stringBuilder = new StringBuilder();       
        if (b == null || b.length <= 0)       
        {       
            return null;       
        }       
        for (int i = 0; i < b.length; i++)       
        {       
            int v = b[i] & 0xFF;       
            String hv = Integer.toHexString(v);       
            if (hv.length() < 2)       
            {       
                stringBuilder.append(0);       
            }       
            stringBuilder.append(hv);       
        }       
        return stringBuilder.toString();       
    }       
      
    public static String getFileTypeByStream(byte[] b)   
    {   
        String filetypeHex = String.valueOf(getFileHexString(b));   
        Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();   
        while (entryiterator.hasNext()) {   
            Entry<String,String> entry = entryiterator.next();   
            String fileTypeHexValue = entry.getValue();   
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {   
                return entry.getKey();   
            }   
        }   
        return null;   
    }   
      
    /** 
     * 判断文件类型 
     * @param file 
     * @return 
     */  
    public static String getfiletypeByFile(File file)   
    {   
        String filetype = null;   
        byte[] b = new byte[50];   
        InputStream is = null;   
        try   
        {   
            is = new FileInputStream(file);  
            is.read(b);   
            filetype = getFileTypeByStream(b);   
               
        }   
        catch (FileNotFoundException e)   
        {   
            e.printStackTrace();   
        }   
        catch (IOException e)   
        {   
            e.printStackTrace();   
        }  
        finally  
        {  
            try {  
                is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return filetype;   
    }   
      
      
      
    //---------html 去掉标签和css样式等   start-------  
    public static String html2text(String html) {  
        StringBuffer sb = new StringBuffer(html.length());  
        char[] data = html.toCharArray();  
        int start = 0;  
        boolean previousIsPre = false;  
        Token token = null;  
        for(;;) {  
            token = parse(data, start, previousIsPre);  
            if(token==null)  
                break;  
            previousIsPre = token.isPreTag();  
            sb = sb.append(token.getText());  
            start += token.getLength();  
        }  
        return sb.toString();  
    }     
      
    private static Token parse(char[] data, int start, boolean previousIsPre) {  
        if(start>=data.length)  
            return null;  
        // try to read next char:  
        char c = data[start];  
        if(c=='<') {  
            // this is a tag or comment or script:  
            int end_index = indexOf(data, start+1, '>');  
            if(end_index==(-1)) {  
                // the left is all text!  
                return new Token(Token.TOKEN_TEXT, data, start, data.length, previousIsPre);  
            }  
            String s = new String(data, start, end_index-start+1);  
            // now we got s="<...>":  
            if(s.startsWith("<!--")) { // this is a comment!  
                int end_comment_index = indexOf(data, start+1, "-->");  
                if(end_comment_index==(-1)) {  
                    // illegal end, but treat as comment:  
                    return new Token(Token.TOKEN_COMMENT, data, start, data.length, previousIsPre);  
                }  
                else  
                    return new Token(Token.TOKEN_COMMENT, data, start, end_comment_index+3, previousIsPre);  
            }  
            String s_lowerCase = s.toLowerCase();  
            if(s_lowerCase.startsWith("<script")) { // this is a script:  
                int end_script_index = indexOf(data, start+1, "</script>");  
                if(end_script_index==(-1))  
                    // illegal end, but treat as script:  
                    return new Token(Token.TOKEN_SCRIPT, data, start, data.length, previousIsPre);  
                else  
                    return new Token(Token.TOKEN_SCRIPT, data, start, end_script_index+9, previousIsPre);  
            }  
            else { // this is a tag:  
                return new Token(Token.TOKEN_TAG, data, start, start+s.length(), previousIsPre);  
            }  
        }  
        // this is a text:  
        int next_tag_index = indexOf(data, start+1, '<');  
        if(next_tag_index==(-1))  
            return new Token(Token.TOKEN_TEXT, data, start, data.length, previousIsPre);  
        return new Token(Token.TOKEN_TEXT, data, start, next_tag_index, previousIsPre);  
    }     
      
      
    private static int indexOf(char[] data, int start, String s) {  
        char[] ss = s.toCharArray();  
        // TODO: performance can improve!  
        for(int i=start; i<(data.length-ss.length); i++) {  
            // compare from data[i] with ss[0]:  
            boolean match = true;  
            for(int j=0; j<ss.length; j++) {  
                if(data[i+j]!=ss[j]) {  
                    match = false;  
                    break;  
                }  
            }  
            if(match)  
                return i;  
        }  
        return (-1);  
    }     
  
    private static int indexOf(char[] data, int start, char c) {  
        for(int i=start; i<data.length; i++) {  
            if(data[i]==c)  
                return i;  
        }  
        return (-1);  
    }}@SuppressWarnings("unchecked")  
    class Token {      
    public static final int TOKEN_TEXT    = 0; // html text.  
    public static final int TOKEN_COMMENT = 1; // comment like <!--  
             // comments... -->  
    public static final int TOKEN_TAG     = 2; // tag like <pre>, <font>,  
             // etc.  
    public static final int TOKEN_SCRIPT = 3;    private static final char[] TAG_BR = "<br".toCharArray();  
    private static final char[] TAG_P   = "<p".toCharArray();  
    private static final char[] TAG_LI = "<li".toCharArray();  
    private static final char[] TAG_PRE = "<pre".toCharArray();  
    private static final char[] TAG_HR = "<hr".toCharArray();      
    private static final char[] END_TAG_TD = "</td>".toCharArray();  
    private static final char[] END_TAG_TR = "</tr>".toCharArray();  
    private static final char[] END_TAG_LI = "</li>".toCharArray();     
    private static final Map SPECIAL_CHARS = new HashMap();      
    private int type;  
    private String html;           // original html  
    private String text = null;    // text!  
    private int length = 0;        // html length  
    private boolean isPre = false; // isPre tag?   
    static {  
      
//        SPECIAL_CHARS.put(""", "\"");  
        SPECIAL_CHARS.put("<",   "<");  
        SPECIAL_CHARS.put(">",   ">");  
        SPECIAL_CHARS.put("&", "&");  
        SPECIAL_CHARS.put("?", "(r)");  
        SPECIAL_CHARS.put("?", "(c)");  
        SPECIAL_CHARS.put(" ", " ");  
        SPECIAL_CHARS.put("￡", "?");  
    }     
public Token(int type, char[] data, int start, int end, boolean previousIsPre) {  
        this.type = type;  
        this.length = end - start;  
        this.html = new String(data, start, length);  
        //System.out.println("[Token] html=" + html + ".");  
        parseText(previousIsPre);  
        //System.out.println("[Token] text=" + text + ".");  
    }    public int getLength() {  
        return length;  
    }    public boolean isPreTag() {  
        return isPre;  
    }    private void parseText(boolean previousIsPre) {  
        if(type==TOKEN_TAG) {  
            char[] cs = html.toCharArray();  
            if(compareTag(TAG_BR, cs) || compareTag(TAG_P, cs))  
                text = "\n";  
            else if(compareTag(TAG_LI, cs))  
                text = "\n* ";  
            else if(compareTag(TAG_PRE, cs))  
                isPre = true;  
            else if(compareTag(TAG_HR, cs))  
                text = "\n--------\n";  
            else if(compareString(END_TAG_TD, cs))  
                text = "\t";  
            else if(compareString(END_TAG_TR, cs) || compareString(END_TAG_LI, cs))  
                text = "\n";  
        }  
        // text token:  
        else if(type==TOKEN_TEXT) {  
            text = toText(html, previousIsPre);  
        }  
    }      
    public String getText() {  
        return text==null ? "" : text;  
    }     
      
    private String toText(String html, final boolean isPre) {  
        char[] cs = html.toCharArray();  
        StringBuffer buffer = new StringBuffer(cs.length);  
        int start = 0;  
        boolean continueSpace = false;  
        char current, next;  
        for(;;) {  
            if(start>=cs.length)  
                break;  
            current = cs[start]; // read current char  
            if(start+1<cs.length) // and next char  
                next = cs[start+1];  
            else  
                next = '\0';  
            if(current==' ') {  
                if(isPre || !continueSpace)  
                    buffer = buffer.append(' ');  
                continueSpace = true;  
                // continue loop:  
                start++;  
                continue;  
            }  
            // not ' ', so:  
            if(current=='\r' && next=='\n') {  
                if(isPre)  
                    buffer = buffer.append('\n');  
                // continue loop:  
                start+=2;  
                continue;  
            }  
            if(current=='\n' || current=='\r') {  
                if(isPre)  
                    buffer = buffer.append('\n');  
                // continue loop:  
                start++;  
                continue;  
            }  
            // cannot continue space:  
            continueSpace = false;  
            if(current=='&') {  
                // maybe special char:  
                int length = readUtil(cs, start, ';', 10);  
                if(length==(-1)) { // just '&':  
                    buffer = buffer.append('&');  
                    // continue loop:  
                    start++;  
                    continue;  
                }  
                else { // check if special character:  
                    String spec = new String(cs, start, length);  
                    String specChar = (String)SPECIAL_CHARS.get(spec);  
                    if(specChar!=null) { // special chars!  
                        buffer = buffer.append(specChar);  
                        // continue loop:  
                        start+=length;  
                        continue;  
                    }  
                    else { // check if like '?':  
                        if(next=='#') { // maybe a char  
                            String num = new String(cs, start+2, length-3);  
                            try {  
                                int code = Integer.parseInt(num);  
                                if(code>0 && code<65536) { // this is a  
                // special char:  
                                    buffer = buffer.append((char)code);  
                                    // continue loop:  
                                    start++;  
                                    continue;  
                                }  
                            }  
                            catch(Exception e) {}  
                            // just normal char:  
                            buffer = buffer.append("&#");  
                            // continue loop:  
                            start+=2;  
                            continue;  
                        }  
                        else { // just '&':  
                            buffer = buffer.append('&');  
                            // continue loop:  
                            start++;  
                            continue;  
                        }  
                    }  
                }  
            }  
            else { // just a normal char!  
                buffer = buffer.append(current);  
                // continue loop:  
                start++;  
                continue;  
            }  
        }  
        return buffer.toString();  
    }    // read from cs[start] util meet the specified char 'util',  
    // or null if not found:  
      
    private int readUtil(final char[] cs, final int start, final char util, final int maxLength) {  
        int end = start+maxLength;  
        if(end>cs.length)  
            end = cs.length;  
        for(int i=start; i<start+maxLength; i++) {  
            if(cs[i]==util) {  
                return i-start+1;  
            }  
        }  
        return (-1);  
    }    // compare standard tag "<input" with tag "<INPUT value=aa>"  
      
    private boolean compareTag(final char[] ori_tag, char[] tag) {  
        if(ori_tag.length>=tag.length)  
            return false;  
        for(int i=0; i<ori_tag.length; i++) {  
            if(Character.toLowerCase(tag[i])!=ori_tag[i])  
                return false;  
        }  
        // the following char should not be a-z:  
        if(tag.length>ori_tag.length) {  
            char c = Character.toLowerCase(tag[ori_tag.length]);  
            if(c<'a' || c>'z')  
                return true;  
            return false;  
        }  
        return true;  
    }     
      
    private boolean compareString(final char[] ori, char[] comp) {  
        if(ori.length>comp.length)  
            return false;  
        for(int i=0; i<ori.length; i++) {  
            if(Character.toLowerCase(comp[i])!=ori[i])  
                return false;  
        }  
        return true;  
    }    public String toString() {  
        return html;  
    }  
    //------------------end ------------------  
      
      
} 