<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<script language="javascript">
	if(window.top){
		window.top.location.href = "${ctx}/timeout_login.do";
	}else{
		window.location.href= "${ctx}/timeout_login.do";
	}
</script>
