/**
 * File name:      CfetsConfigurer.java
 * Date:           2011-9-11
 * Description:    // 用于详细说明此程序文件完成的主要功
 *                 // 能与其他模块或函数的接口，输出值、
 *                 // 取值范围、含义及参数间的关系
 * Modify History:     Date             Programmer       Notes
 *                    ---------        ---------------  ---------
 *                    2011-9-11             何指剑                         Initial
 **********************************************************************
 */
package com.pemng.framework.configurer;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.pemng.common.util.DescUtil;

/**
 * Created on 2011-9-11
 * <p>Description: [描述该类概要功能介绍]</p>
 * @version        1.0
 */
public class CfetsConfigurer extends PropertyPlaceholderConfigurer{
  @Override
  protected void processProperties(ConfigurableListableBeanFactory beanFactory,Properties props)
    throws BeansException{
    String password = props.getProperty("hibernate.connection.password");
    if(password!=null && !password.trim().equals("")){
      props.setProperty("hibernate.connection.password", DescUtil.decrypt(password));
    }
    super.processProperties(beanFactory, props);
  }
}
