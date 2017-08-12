<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>采价导入</title>

<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>

<SCRIPT type="text/javascript">

$(document).ready(function(){
	<s:if test='tmpFlag == 1'>
		alert("请选择上传文件!");
	</s:if>
	<s:elseif test='tmpFlag == 2'>
		alert("您上传的文件类型不正确!");
	</s:elseif>
	<s:elseif test='tmpFlag == 3'>
		alert("您上传的文件过大了!");
	</s:elseif>
	<s:elseif test='tmpFlag == 99'>
		alert("${succMsg}");
	</s:elseif>
	<s:elseif test='tmpFlag == 0'>
		alert("导入成功!");
		closeWidown();
	</s:elseif>
});
		
function closeWidown(){
	window.close();
}
</SCRIPT>

</head>
<body>
<h3>采价导入</h3>
<form action="impMinings.action" method="post" target="" name="impMiningsForm" id="impMiningsForm" enctype="multipart/form-data" onsubmit="return checkForm()">
	<table cellpadding="0" cellspacing="0" border="0" width="760" style="margin: 0 0 0 10px;">
		<tr height="40px">
			<td colspan="2">
				<div align="center">
					导入说明
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div>
				&nbsp;&nbsp;&nbsp;导入步骤说明：<br/>
				&nbsp;&nbsp;&nbsp;1.下载导入模版，填写需要导入的采价信息<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目录名称有上一级目录，则要用“/”分割，如：汽车/奥迪/一汽奥迪；<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价格准日、采价日期格式用“/”，如：2013/12/12；<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目录名称、采价日期、分类（品名）、经销商价格为必填项，否则会导致本次导入不成功；<br/>
				&nbsp;&nbsp;&nbsp;2.导入完成后，在价格库管理相应目录可以查看到新导入的数据；<br/><br/>
				</div>
			</td>
		</tr>
		<tr>
			 <td colspan="2">&nbsp;&nbsp;&nbsp;<a href="dwnMiningsTmp.action" target="_self" style="text-decoration: underline">采价导入模板下载</a></td>
		</tr>
	</table>
	<div style="border-bottom:#416678 solid 1px;height:10px; padding-top:10px; padding-left:25px;"></div>
	<div style="padding-top:10px;">&nbsp;&nbsp;&nbsp;请选择文件:&nbsp;&nbsp;<input type="file" name="_file" id="_file" class="zinput"/></div>
	<div style="padding-top:10px;">
		&nbsp;&nbsp;&nbsp;<input type="submit" class="anniu" onclick="" value="导入采价"/>
		<input type="button" class="anniu" s onclick="closeWidown();" value="取消"/>
	</div>
</form>
</body>
</HTML>