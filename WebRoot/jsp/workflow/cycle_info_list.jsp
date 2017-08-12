<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><spring:message code="info.audit.history" /></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  	<div class="box">
    <table border="1" width="100%">
    	<tr>
    		<th width="10%"><spring:message code="info.claim.flow_time" /></th>
    		<th width="10%"><spring:message code="info.operate_man" /></th>
    		<th width="10%"><spring:message code="info.deptName" /></th>
    		<th width="30%"><spring:message code="info.operation" /></th>
    		<th width="40%"><spring:message code="info.remark" /></th>
    	</tr>
    	<s:iterator value="list" >
    		<tr>
	    		<td><s:date name="crtTime" format="yyyy-MM-dd"/></td>
	    		<td><s:property value="auditUsrName"/></td>
	    		<td><s:property value="deptName"/></td>
	    		<td>
	    		<s:if test='action=="01"'><spring:message code="info.sumbit" /></s:if>
	    		<s:elseif test='action=="02"'><spring:message code="button.approval_pass" /></s:elseif>
	    		<s:elseif test='action=="03"'><spring:message code="button.refuse" /></s:elseif>
	    		<s:elseif test='action=="04"'><spring:message code="button.according" /></s:elseif>
	    		</td>
	    		<td><s:property value="auditOpinion"/></td>
    	    </tr>
    	</s:iterator>
    </table>
    </div>
  </body>
</html>
