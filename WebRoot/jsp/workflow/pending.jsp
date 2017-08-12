<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/right.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/lrtk.css">
<script src="js/tagselect.js" language="javascript" type="text/javascript"></script>
<%@ include file="../included/all_css.jsp" %>
<%@ include file="../included/all_js.jsp" %>

</head>
<script language="javascript" type="text/javascript">
  function openwin(url,id,taskid){
	  var newUrl = '${pageContext.request.contextPath}';
	  newUrl +=url+'?formId='+id+'&taskId='+taskid;
	  window.open(newUrl);
  }
</script>
<body>
<!--下面内容开始-->

<h3>待办事项</h3>
<div class="zmdb">您当前共有 <span><s:property value="list.size"/> </span>个待办事项。</div>
<div class="zmdbts"><ul>
<s:iterator value="list">
  <li><p>[<s:property value="crtTime"/>]<s:property value="formName"/></p><div class="zmdbbm"><s:property value="formDesc"/></div><div class="zmdbbm"> <s:property value="deptName"/></div><div class="zmdbbm"><s:property value="userName"/></div><a href="javascript:openwin('<s:property value="formUrl"/>','<s:property value="formId"/>','<s:property value="taskId"/>');">点击处理</a></li>
</s:iterator>
</ul></div>
</body>
</html>
