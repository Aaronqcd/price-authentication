<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
<title>打印</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
</head>
<body scroll="no">
	<div align="center">
		<form action="${pageContext.request.contextPath}/cms/savePrintCms.action" id="savePrintCmsForm"  name="savePrintCmsForm"  method="post" >
			<input type="hidden" id="printCmsId" name="printCmsId" value="<s:property value="printCmsId"/>" />
			<input type="hidden" id="printCmsType" name="printCmsType" value="<s:property value="printCmsType"/>" />
			<textarea cols="100" id="cmsContent" name="cmsContent" rows="10">
				<s:property value="cmsContent"/>
			</textarea>
			<script>
			<c:if test="${sessionScope.SESSION_WEBINFO.webUser.username == 'admin'}">
				CKEDITOR.replace( 'cmsContent',{ height:520,width:680,removePlugins: 'elementspath',resize_enabled: false,toolbar : 'PeToolbar'});
			</c:if>
			<c:if test="${sessionScope.SESSION_WEBINFO.webUser.username != 'admin'}">
				<s:if test="cmsSt!=null && !cmsSt.matches(\"13|14\")">
					CKEDITOR.replace( 'cmsContent',{ height:520,width:680,removePlugins: 'elementspath',resize_enabled: false,toolbar : 'PeToolbar'});
				</s:if>
				<s:else>
					CKEDITOR.replace( 'cmsContent',{ height:520,width:680,removePlugins: 'elementspath',resize_enabled: false,toolbar : 'PeToolbar1'});
				</s:else>
			</c:if>
			</script>
		</form>
	</div>
	
	
	<script type="text/javascript">
	<%
		String printSaveResult = (String)session.getAttribute("print_save_result");
		
		if(printSaveResult != null  && printSaveResult.trim().length() > 0)  {%>
			alert("<%=printSaveResult%>");
		<% }%>
	</script>
</body>
</html>