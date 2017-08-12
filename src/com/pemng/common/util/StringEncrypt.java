/**
 * File name:      StringEncrypt.java
 * Date:           2012-3-13
 * Description:    // 用于详细说明此程序文件完成的主要功
 *                 // 能与其他模块或函数的接口，输出值、
 *                 // 取值范围、含义及参数间的关系
 * Modify History:     Date             Programmer       Notes
 *                    ---------        ---------------  ---------
 *                    2012-3-13             jeff.he        Initial
 **********************************************************************
 */
package com.pemng.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 2012-3-13
 * <p>Description: [描述该类概要功能介绍]</p>
 * @version        1.0
 */
public class StringEncrypt {

  /**
   * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
   * 
   * @param strSrc
   *            要加密的字符串
   * @param encName
   *            加密类型
   * @return
   */
  public static String Encrypt(String strSrc, String encName) {
      MessageDigest md = null;
      String strDes = null;

      byte[] bt = strSrc.getBytes();
      try {
          if (encName == null || encName.equals("")) {
              encName = "SHA-256";
          }
          md = MessageDigest.getInstance(encName);
          md.update(bt);
          strDes = bytes2Hex(md.digest()); // to HexString
      } catch (NoSuchAlgorithmException e) {
          return null;
      }
      return strDes;
  }

  public static String bytes2Hex(byte[] bts) {
      String des = "";
      String tmp = null;
      for (int i = 0; i < bts.length; i++) {
          tmp = (Integer.toHexString(bts[i] & 0xFF));
          if (tmp.length() == 1) {
              des += "0";
          }
          des += tmp;
      }
      return des;
  }
  
  public static void main(String args[]){
   String s=StringEncrypt.Encrypt("888888", "");
   System.out.println(s);
  }

}