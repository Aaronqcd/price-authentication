<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>价格鉴定/认定管理</title>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script type="text/javascript">
		function closeBtn() {
			window.close();
		}
		
		function searchCms(){
			document.searchForm.action="${pageContext.request.contextPath}/cms/hisCase.action";
			document.searchForm.submit();
		}
		
		function selectCms(){
	    	var cnt=$('#cnt').val();
	    	var cmsId=$('#cmsId').val();
	    	if(cmsId == ""){
	    		cmsId = "0";
	    	}
	   	//把隐藏
	   	window.opener.setCms(cnt,cmsId);
	   	window.close();
	 }   
	   
	function setCms1(cmsId,cnt){
	   $('#cmsId').val(cmsId); 
	   $('#cnt').val(cnt);
	}
		
	
	    
	</script>
	</head> 
	<body>
		<!--下面内容开始--> 
		<h3>选择历史案件</h3>
		<div class="box3-tc" id="searchCloseDiv">
				<form method="post" name="searchForm" action="${pageContext.request.contextPath}/cms/hisCase.action">
				<s:hidden name="cmsId" />
				<s:hidden name="cnt" id="cnt"/>
						<table>
							<tr>
								<th>委托内容：</th>
								<td>
									<s:textfield name="cmsDto.cmsCnt" id="cmsCnt" cssClass="input1"></s:textfield>
								</td>
								<th>受理员:</th>
								<td>
									<s:textfield name="cmsDto.acptUsrNm" id="acptUsrNm" cssClass="input1"></s:textfield>
								</td>
								<td>
								 	<input name="button" type="button" class="anniu" value="查询" onclick="javascript:searchCms(this);"/>
									<input name="button" type="button" class="anniu" value="确定" onclick="javascript:selectCms(this);"/>
									<input name="button" type="button" class="anniu" value="取消" onclick="javascript:closeBtn(this);"/>
								</td>
							</tr>
						</table>
					</div>
		
				</form>
				<div class="clear"></div>
			
				<form  method="post" name="deleteCmsForm" id="deleteCmsForm">
					<div class="box1">
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th width="5%" >选择</th>
							<th width="30%">鉴定文书号</th> 
							<th width="50%">委托内容</th>
							<th width="5%">受理员</th>
						</tr>
							<s:iterator value="cmList" status='st'>
								 <tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>
								 onclick="setCms1(<s:property value="id"/>,'<s:property value="cmsCnt"/>')">
									<td align="center" id="checkId_<s:property value='id'/>">
										<input type="radio" value="<s:property value="id" />"
										id="id" name="cmsDto.ids" ondblclick="javascript:selectCms();"/>
									</td>
									<td>
										<s:property value="prcAprislDocmsNo"/>
									</td>
									<td width="10%" style="text-align: left"> 
										 <s:if test="cmsCnt != null && cmsCnt.toString().length() > 40">
						            	 	<span title="<s:property value="cmsCnt"/>">
						            	 		<s:property value="cmsCnt.toString().substring(0,40)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="cmsCnt"/>
						            	 </s:else>
								</td>
								<td width="10%" align="center">
									<s:property value="acptUsrId.name"/>
								</td>
								</tr>
							</s:iterator> 
						</table>
				</div>
			</form>
		
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