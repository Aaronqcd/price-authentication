<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>error</title>
<link href="../../css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="../js/common.js" />"></script>
<script type="text/javascript">
	var prvUrl = '<%/*=((com.cfets.brms.core.model.Request)request.getSession().getAttribute("SESSION_PRIVIOUS_REQUEST")).getUrlString()*/%>';
</script>

</head>
<body>
 <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#99B1CE" >
  <tr>
  <td valign="top" align="center" bgcolor="#EFF3F8" class="td-main">
   <table height="100" width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr valign="top">
           <td align="left" style="color:red;FONT-SIZE:16PX;">系统错误</td>
         </tr>
         <tr valign="top">
              <td align="left" style="color:red;FONT-SIZE:12PX;">
                <s:property value="exception" />
              </td>
         </tr>
    </table>
   <table width="100%" height="60">
	<tr valign="bottom">
		<td align="left">
		<s:a href="#" onclick="url2FormSubmit(prvUrl)">
		<input type="button" value="返  回"/>
		</s:a>
		</td>
	</tr>
   </table>
</td>
</tr>
</table>
</body>
</html>
