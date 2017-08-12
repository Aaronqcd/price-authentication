<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="../included/all_css.jsp"></jsp:include>
<jsp:include page="../included/all_js.jsp"></jsp:include>
</head>
<body>
<div class="clear"></div>
<div class="title1"><span class="sy">首页</span><span class="jt">我的工作台</span></div>
<!--  查询区域   -->
<div class="kuang2-1">
<div class="daiban"><div class="jtk"><span class="left">我的运维</span><span class="right" style=" font-weight:normal; color:#333333">您有${pager.totalSize }条故障代办 <a  href="${pageContext.request.contextPath}/dolist/dolistList.action">&nbsp;查看</a></span></div>

<s:if test="pager.totalSize==0">当前没有需要处理的单据！！！</s:if>

<div style="width: 660px;">
<table style="width: 660px;table-layout:fixed">


</table>
<div align="right"><a href="${pageContext.request.contextPath}/dolist/dolistList.action">更多...</a></div>
</div>


</div>
<div class="clear"></div>

</div>
</body>
</html>

<script type="text/javascript">


</script>