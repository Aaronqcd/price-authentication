<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>数据字典管理</title>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>

<script>
  jQuery(document).ready(function(){
		$("#button_list").click(function(){
			$("#searchForm").submit();
		});
	});
	
	function searchData(){
	   document.searchForm.action="${pageContext.request.contextPath}/basicdata/viewCodeList.action";
	   document.searchForm.submit();
	}
	</script>
	</head> 
	<body>
	<h3>数据字典管理</h3>
		<!--下面内容开始-->
		<div class="box3-tc" id="searchCloseDiv">
		<form action="${pageContext.request.contextPath}/basicdata/viewCodeContent.action" method="post" name="searchForm">
						<table>
							<tr>
								<th>编号：</th>
								<td>
									<s:textfield name="viewDto.code" id="code" cssClass="input1"/>
								</td>
								<th>单位编码：</th>
								<td>
									<s:textfield  name="viewDto.remark" id="remark" cssClass="input1" />
								</td>
								<td align="right">
								<div class="buttomk1">
								 <input type="submit" value="查询" class="anniu" onclick="searchData();"/>
								</div>
								</td>
							</tr>
						</table>
						</form>
					</div>
				
					<div class="clear"></div>
				
					<div class="box1">
				<form  method="post" name="deleteUnitForm" id="deleteUnitForm">
				
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<th width="30%" align="center">编号</th>
								<th width="70%" align="center">备注</th>
							</tr> 
							<s:iterator value="dataList" status='st'>
								 <tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
									<td align="center">
										<A href="${pageContext.request.contextPath}/basicdata/viewCodeContent.action?viewDto.id=<s:property value="id" />" ><s:property value="theCode" /></A>
									</td>
									<td align="center">
										<s:property value="remark" />
									</td >
								</tr>
							</s:iterator> 
						</table>
			</form>
					</div>
		<div class="kuang"><div class="box2">
			<table border="0" cellpadding="0" cellspacing="0"><tr class="td1"><td>
				<s:form name="form2" id="form2" method="post">
					<jsp:include page="/jsp/included/page.jsp" flush="true"></jsp:include>
				</s:form>
			</td></tr>
			</table>
		</div></div>
</body>
</html>
