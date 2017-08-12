/**
 * File name:      NoAccessPrivilegeException.java
 * Date:           2011-9-2
 * Description:    // 用于详细说明此程序文件完成的主要功
 *                 // 能与其他模块或函数的接口，输出值、
 *                 // 取值范围、含义及参数间的关系
 * Modify History:     Date             Programmer       Notes
 *                    ---------        ---------------  ---------
 *                    2011-9-2             何指剑                         Initial
 **********************************************************************
 */
package com.pemng.framework.exceptions;

import com.pemng.serviceSystem.base.exception.BusinessException;

/**
 * Created on 2011-9-2
 * <p>Description: [描述该类概要功能介绍]</p>
 * @version        1.0
 */
public class NoAccessPrivilegeException extends BusinessException{
  public NoAccessPrivilegeException(){
    super("No User Session");
  }
  
  public NoAccessPrivilegeException(String msg){
    super(msg);
  }
}
