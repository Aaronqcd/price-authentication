<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<frameset rows="120px,50%,30%">

<frame src="${pageContext.request.contextPath}/frame/frameTopAction.action" scrolling="no" frameborder="0">


<frameset cols="25%,75%">
<frame name="left" src="${pageContext.request.contextPath}/frame/frameLeftAction.action" scrolling="no" frameborder="0">
<frame name="mainframe" src="${pageContext.request.contextPath}/workflow/pendingAction.action" frameborder="0">
</frameset>
<frame name="bottom" src="${pageContext.request.contextPath}/frame/frameBottomAction.action" scrolling="no" frameborder="0">

</frameset>

</html>