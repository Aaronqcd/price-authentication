/*
 * Copyright (c) 2012  GEONG INTERNATIONAL (GEONG Business  Networks LTD.) All rights reserved. 
 * For commercial use, you must accept this license.  http://www.geong.com/license
 */
package com.pemng.serviceSystem.base.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * 
 * @author WangJian
 * @since 2012-11-29 下午3:48:46
 */
public class SqlDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final transient Log logger = LogFactory.getLog(SqlDao.class);
	
	
	/**
	 * 执行存储过程
	 * @param spName	带包的存储过程名
	 * @param inParams	输入参数
	 * @param outParams	输出参数数量
	 * @return java.util.List<Object>
	 */
	@Deprecated
	public List<Object> exeSpWithParameter(String spName, Object[] inParams, int outParams) throws SQLException{
		Connection conn = null;
		CallableStatement cstmt = null;
		
		List<Object> resultList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("{ call " + spName + "(");
		int count = inParams.length + outParams;
		for(int i=0; i<count; i++){
			if(i==count-1){
				sb.append(" ? ");
			}else{
				sb.append("?, ");
			}
		}
		sb.append(") }");
		
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			cstmt = conn.prepareCall(sb.toString());
			for(int i=0; i<inParams.length; i++){
				if("".equals(inParams[i])){
					cstmt.setString(i+1, null);
				}else{
					cstmt.setObject(i+1, inParams[i]);
				}
			}
			
			for(int i=0; i<outParams; i++){
				cstmt.registerOutParameter(inParams.length + i+1, Types.VARCHAR);
			}
			
			cstmt.execute();
			
			for(int i=0; i<outParams; i++){
				Object result = cstmt.getObject(inParams.length + i+1);
				resultList.add(result);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if(null != cstmt){
					cstmt.close();
				}
				if(null != conn){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultList;
	}
	
	/**
	 * 执行存储过程
	 * @param spName	带包的存储过程名
	 * @param inParams	输入参数
	 * @param outParams	输出参数数量
	 * @return java.util.List<Object>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> exeSpWithParam(String spName, final Object[] inParams, final int outParams) throws SQLException{
		final List<Object> resultList = new ArrayList<Object>();
		
		final StringBuffer sb = new StringBuffer();
		sb.append("{ call " + spName + "(");
		int count = inParams.length + outParams;
		for(int i=0; i<count; i++){
			if(i==count-1){
				sb.append(" ? ");
			}else{
				sb.append("?, ");
			}
		}
		sb.append(") }");
		
		logger.warn("Stored Procedure Name: " + sb.toString());
		logger.warn("Parameters: " + printArr(inParams));
		
		jdbcTemplate.execute(new CallableStatementCreator(){
			@Override
			public CallableStatement createCallableStatement(Connection conn) throws SQLException {
				CallableStatement cstmt = conn.prepareCall(sb.toString());
				for(int i=0; i<inParams.length; i++){
					if("".equals(inParams[i])){
						cstmt.setString(i+1, null);
					}else{
						cstmt.setObject(i+1, inParams[i]);
					}
				}
				
				for(int i=0; i<outParams; i++){
					cstmt.registerOutParameter(inParams.length + i+1, Types.VARCHAR);
				}
				return cstmt;
			}}, 
				
			new CallableStatementCallback(){
				@Override
				public Object doInCallableStatement(CallableStatement cstmt) throws SQLException, DataAccessException {
					
					cstmt.execute();
					
					for(int i=0; i<outParams; i++){
						Object result = cstmt.getObject(inParams.length + i+1);
						resultList.add(result);
					}
					return resultList;
				}});
		
		return resultList;
	}
	

	/**
	 * @param inParams
	 * @return
	 */
	private String printArr(Object[] inParams) {
		if(null == inParams){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i=0; i<inParams.length; i++){
			sb.append(inParams[i] + ",");
		}
		String result = sb.toString();
		if(sb.length()>1){
			result = result.substring(0, result.length()-1) + "]";
		}
		
		return result;
	}

	//getter and setters
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
