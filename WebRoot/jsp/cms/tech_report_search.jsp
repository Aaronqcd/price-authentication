<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>搜索同标的技术报告</title>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		
		<script type="text/javascript">
 		function searchList(){
	 		$("#searchForm").attr("action","${pageContext.request.contextPath}/aprisl/referTech.action?tm=" + new Date().getTime());
	   		$("#searchForm").submit();
		}
		function viewCms(id){
			window.open("${pageContext.request.contextPath}/cms/viewCms.action?saveDto.id="+id, "viewCms");
		}
	</script>
	</head> 
	<body>
	<h3>搜索同标的技术报告</h3> 
		<!--下面内容开始-->
		<div class="box3-tc" id="searchCloseDiv">
		<form method="post" name="searchForm" action="${pageContext.request.contextPath}/aprisl/referTech.action">
						<table>
							<tr>
								<th>鉴定文号:</th>
								<td>
									<s:textfield name="aprislDto.TCommission.prcAprislDocmsNo" id="prcAprislDocmsNo" cssClass="input1"></s:textfield>
								</td>
								<th>鉴定基准日：</th>
								<td>
									<s:textfield name="aprislDto.beginTime1" id="beginTime1"
										readonly="true" cssClass="input1" /> 
									<img onclick="WdatePicker({el:'beginTime1',readOnly:true,maxDate: '#F{$dp.$D(\'endTime1\',{d:0})}'});" src="${pageContext.request.contextPath}/images/ril.png"
										width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;" />
									&nbsp;至
									<s:textfield name="aprislDto.endTime1" id="endTime1" 
										readonly="true" cssClass="input1" />
									<img onclick="WdatePicker({el:'endTime1',readOnly:true,minDate: '#F{$dp.$D(\'beginTime1\',{d:0})}'});"
										src="${pageContext.request.contextPath}/images/ril.png" width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"  />
								</td>
								<th>委托日期：</th>
								<td>
									<s:textfield name="aprislDto.beginTime2" id="beginTime2"
										readonly="true" cssClass="input1" /> 
									<img onclick="WdatePicker({el:'beginTime2',readOnly:true,maxDate: '#F{$dp.$D(\'endTime2\',{d:0})}'});" src="${pageContext.request.contextPath}/images/ril.png"
										width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"/>
									&nbsp;至
									<s:textfield name="aprislDto.endTime2" id="endTime2" 
										readonly="true" cssClass="input1" />
									<img onclick="WdatePicker({el:'endTime2',readOnly:true,minDate: '#F{$dp.$D(\'beginTime2\',{d:0})}'});"
										src="${pageContext.request.contextPath}/images/ril.png" width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"  />
								</td>
							</tr>
							<tr>
								<th>物品名称：</th>
								<td>
									<s:textfield name="aprislDto.artclNm" id="artclNm" cssClass="input1"></s:textfield>
								</td>
								<th>鉴定金额：</th>
								<td>
									<s:textfield name="aprislDto.aprislPrc1" id="aprislPrc1" cssClass="input1"></s:textfield>
									至
									<s:textfield name="aprislDto.aprislPrc2" id="aprislPrc2" cssClass="input1"></s:textfield>
								</td>
								
								<td colspan="2" align="left">
								 <input type="submit" value="查询" class="anniu" onclick="javascript:searchList(this);"/>
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
								<th width="12%" align="center">鉴定文号</th>
								<th width="12%" align="center">物品内容</th>
								<th width="12%" align="center">基准日</th>
								<th width="8%" align="center">鉴定金额</th>
								<th width="12%" align="center">委托日期</th>
							</tr> 
							<s:iterator value="aprislList" status='st'>
								 <tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
									<td align="center">
										<a href="#" onclick="viewCms(<s:property value="TCommission.id"/>)"><s:property value="TCommission.prcAprislDocmsNo"/></a>
									</td>
									<td style="text-align: left">
										<s:property value="artclNm"/>
									</td>
									<td align="center">
										<s:date name="TCommission.prcAprislBaseDt" format="yyyy-MM-dd" />
									</td >
									<td style="text-align: left">
										<s:property value="aprislPrc"/>
									</td>
									<td align="center">
										<s:date name="TCommission.crtTime" format="yyyy-MM-dd" />
									</td>
									
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
