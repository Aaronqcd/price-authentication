<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<%@ include file="../included/all_css.jsp" %>
<%@ include file="../included/all_js.jsp" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%--
	<head>
		<script type="text/javascript">
		
			$(document).ready(function() {
				var arg = window.dialogArguments;
				for (items in arg.args) {
						$('.showModalDialogForm').append("<input type='hidden' name='"+items+"' value='"+arg.args[items]+"'/>")
					}
				$('.showModalDialogForm').attr('action',arg.url).submit();
			});

		</script>
		
	</head>
  
	<body>
		<form action="" method="post" class="showModalDialogForm">
		</form>
	
	</body>
	 --%>
	 
	<head>
		<script type="text/javascript">
		
			$(document).ready(function() {
				var arg = window.dialogArguments;
				$('body').load(arg.url,arg.args,500);
			});

		</script>
		
	</head>
  
	<body>
	</body>
</html>
