<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>价格库修改</title>

<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
<script type="text/javascript">
function closeMiningDialog()
{
	window.history.back(-1);
}
</script>
</head>
<body>
<form id="miningForm" action="${pageContext.request.contextPath}/aprisl/modifyPrc.action" method="post">
				<input id="miningId" name="minDto.id" type="hidden" value="${miningPriceInfo.id}">
				<table>
				<tr>
					<th style="text-align: right;height: 30px">编号（如00001）：</th>
					<td>
						<input type="text" id="documentNo" name="minDto.documentNo" value="${miningPriceInfo.documentNo}">
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px">分类（品名）：</th>
					<td>
						<input type="text" id="miningSortName" name="minDto.sortName" value="${miningPriceInfo.sortName}">
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px">型号（类型）：</th>
					<td>
						<input type="text" id="typeName" name="minDto.typeName" value="${miningPriceInfo.typeName }">
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px">厂家指导价：</th>
					<td>
						<input class="validate[maxSize[1000]]" type="text" id="guidePrice" name="minDto.guidePrice" value="${miningPriceInfo.guidePrice }">
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px"><span style="color:red">*</span>经销商价格：</th>
					<td>
						<input class="validate[required,maxSize[1000]]" type="text" id="miningPrice" name="minDto.miningPrice" value="${miningPriceInfo.miningPrice}">
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px">采价地点：</th>
					<td>
						<input type="text" id="miningAddress" name="minDto.miningAddress" value="${miningPriceInfo.miningAddress }">
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px">状态：</th><td style="text-align: left;">
						<input type="radio" name="minDto.status" value="2" id="status_2" value="${miningPriceInfo.status }">
						<label for="status_2">退市</label>
						<input type="radio" checked="checked" name="minDto.status" value="1" id="status_1"><label for="status_1">正常</label>
				</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px"><span style="color:red">*</span>价格基准日：</th>
					<td style="text-align: left;">
						<input class="validate[custom[date]]" type="text" id="referenceDate" name="minDto.referenceDate" style="width: 100px" value="${miningPriceInfo.referenceDate }">
						<img onclick="WdatePicker({el:'referenceDate',readOnly:false});" src="${pageContext.request.contextPath}/images/ril.png" width="16" height="16" border="0" /> 
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px"><span style="color:red">*</span>采价日：</th>
					<td style="text-align: left;">
						<input class="validate[required,custom[date]]" type="text" id="miningDate" name="minDto.miningDate" style="width: 100px" value="${miningPriceInfo.miningDate }">
						<img onclick="WdatePicker({el:'miningDate',readOnly:false});" src="${pageContext.request.contextPath}/images/ril.png" width="16" height="16" border="0" /> 
					</td>
				</tr>
			
				<tr>
					<td colspan="2" style="height: 50px">
						<input type="submit" value="确定" class="anniu" id="miningSubmit"/>
						<input type="button" value="取消" class="anniu" onclick="closeMiningDialog();" />
					</td>
				</tr>
			</table>
				</form>
			</body>
		

	</html>
			