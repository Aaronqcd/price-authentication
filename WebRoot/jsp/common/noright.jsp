<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link href="${ctx}/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="../js/common.js" />"></script>
</head>

<body>

<s:form action="loginsubmit" namespace="/" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="580"><table border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="527" height="273" background="${ctx}/img/loginbg.jpg"><br />
          <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="50" height="25" align="left">&nbsp;</td>
            <td width="55" align="left"></td>
            <td align="left"><font size="4" color="red">系统无权限，请联系管理员!</font></td>
            <td align="left"><s:actionerror/></td>
          </tr>
          <tr>
            <td width="50" height="25" align="left">&nbsp;</td>
            <td width="55" align="left"></td>
            <td align="left"><s:a href="#" onclick="history.back()">返回</s:a></td>
            <td align="left"></td>
          </tr>

        </table></td>
      </tr>
    </table>
    <br /></td>
  </tr>
</table>
</s:form>
</body>
</html>
