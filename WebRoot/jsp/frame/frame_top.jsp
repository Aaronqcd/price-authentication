<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<jsp:include page="../included/all_css.jsp"></jsp:include>
<jsp:include page="../included/all_js.jsp"></jsp:include>
<script type="text/javascript">
<!--
var timeout         = 500;
var closetimer		= 0;
var ddmenuitem      = 0;

// open hidden layer
function mopen(id)
{	
	// cancel close timer
	mcancelclosetime();

	// close old layer
	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';

	// get new layer and show it
	ddmenuitem = document.getElementById(id);
	ddmenuitem.style.visibility = 'visible';

}
// close showed layer
function mclose()
{
	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
}

// go close timer
function mclosetime()
{
	closetimer = window.setTimeout(mclose, timeout);
}

// cancel close timer
function mcancelclosetime()
{
	if(closetimer)
	{
		window.clearTimeout(closetimer);
		closetimer = null;
	}
}

// close layer when click-out
document.onclick = mclose; 
// -->
</script>
</head>

<body>
<div class="top">
<div class="top_1"><p class="top_tz">通知：服务器将在10分钟后停机，请及时做好备份数据</p>
<p class="top_xx"> 欢迎您！</p>
</div>
</div>
<div class="menu"><ul id="sddm">
	<li><a href="#">首页</a>
	</li>
	<s:iterator value="nodes">
	<li><a href="<s:property value="url"/>" target="mainframe" <s:if test="children!=null">onmouseover="mopen('m_<s:property value="id"/>')" onmouseout="mclosetime()"</s:if>><s:property value="title"/></a>
	<s:if test="children!=null">
		<div id="m_<s:property value="id"/>" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
		<s:iterator value="children">
		<a href="http://baidu.com<s:property value="url"/>" target="mainframe" <s:if test="children!=null">onmouseover="mopen('m_<s:property value="id"/>')" onmouseout="mclosetime()"</s:if>><s:property value="title"/></a>
		<s:if test="children!=null">
		<div id="m_<s:property value="id"/>" onmouseover="mopen('m_<s:property value="id"/>')" onmouseout="mclosetime()">
		<s:iterator value="children">
		<a href="<s:property value="url"/>" target="mainframe"><s:property value="title"/></a>
		</s:iterator>
		</div>
		</s:if>
		</s:iterator>
		</div>
	</s:if>
	</li>
	</s:iterator>
</ul><select>
  <option>其他系统</option>
</select></div>
</body>
</html>
