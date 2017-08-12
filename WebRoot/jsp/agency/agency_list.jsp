<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!--下面内容开始-->
<div class="box1">
	<form action="${pageContext.request.contextPath}/agency/agencyCmsList.action" method="post" name="searchForm" id="searchForm">
		<input type="hidden" name="cmsDto.flag" id="flag" value="${cmsDto.flag }">
	</form>
	<form  method="post" name="deleteCmsForm" id="deleteCmsForm">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th width="15%">鉴定文号</th>
					<th width="25%">委托内容</th>
					<th width="15%">委托单位简称</th>
					<th width="7%">委托日期</th>
					<th width="7%">鉴定基准日</th>
					<th width="10%">委托书类别</th>
					<th width="9%">受理员</th>
					<th width="7%">当前流程</th>
					<th width="5%">是否归档</th>
				</tr> 
				<s:iterator value="agencyCmsList" status="status">
					<tr <s:if test="prtDt != null">class="tablerowhighlight"</s:if><s:elseif test="%{#status.index%2==0}">class="td1"</s:elseif>>
						<td>
							<s:if test="cmsDto.flag==1 || cmsDto.flag==2 || cmsDto.flag==6">
								<a href="${pageContext.request.contextPath}/cms/toUpdateCms.action?saveDto.id=<s:property value="id" />&cmsFlag=1" >
							</s:if>	
							<s:else>
								<a href="${pageContext.request.contextPath}/cms/toAduitCms.action?saveDto.id=<s:property value="id" />&cmsFlag=1" >
							</s:else>
								<s:if test="prcAprislDocmsNo != null && prcAprislDocmsNo.toString().length() > 20">
			            	 	<span title="<s:property value="prcAprislDocmsNo"/>">
			            	 		<s:property value="prcAprislDocmsNo.toString().substring(0,20)"/>...
			            	 	</span>
			            	 </s:if>
			            	 <s:else>
			            	 	<s:property value="prcAprislDocmsNo"/>
			            	 </s:else>
								</a>
							
						</td>
						<td>
							<s:if test="cmsCnt != null && cmsCnt.toString().length() > 20">
			            	 	<span title="<s:property value="cmsCnt"/>">
			            	 		<s:property value="cmsCnt.toString().substring(0,20)"/>...
			            	 	</span>
			            	 </s:if>
			            	 <s:else>
			            	 	<s:property value="cmsCnt"/>
			            	 </s:else>
						</td>
						<td>
						 	<s:if test="cmsTp eq 3">
						 		<s:if test="cmsUnitNm != null && cmsUnitNm.toString().length() > 10">
				            	 	<span title="<s:property value="cmsUnitNm"/>">
				            	 		<s:property value="cmsUnitNm.toString().substring(0,10)"/>...
				            	 	</span>
			            	 	</s:if>
				            	<s:else>
				            	 	<s:property value="cmsUnitNm"/>
				            	</s:else>
						 	</s:if>
						 	<s:else>
						 		<s:if test="tunitsInfo.shortName != null && tunitsInfo.shortName.toString().length() > 10">
				            	 	<span title="<s:property value="tunitsInfo.shortName"/>">
				            	 		<s:property value="tunitsInfo.shortName.toString().substring(0,10)"/>...
				            	 	</span>
			            	 	</s:if>
				            	<s:else>
				            	 	<s:property value="tunitsInfo.shortName"/>
				            	</s:else>
						 	</s:else>
						</td>
						<td>
							<s:date name="crtTime" format="yyyy-MM-dd" />
						</td>
						<td>
							<s:date name="prcAprislBaseDt" format="yyyy-MM-dd" />
						</td>
						<td>
							<s:property value="@com.pemng.serviceSystem.common.utils.PeUtil@getTpStr(cmsTp)"/>
						</td>
						<td>
							<s:property value="acptUsrId.name"/>
						</td>
						<td>
							<s:property value="@com.pemng.serviceSystem.common.utils.PeUtil@getStStr(st)"/>
						</td>
						<td>
							<s:property value="@com.pemng.serviceSystem.common.utils.PeUtil@getAchivStr(achiv)"/>
						</td>
					</tr>
				</s:iterator> 
				</table>
	</form>
</div> 
<div class="kuang"><div class="box2">
	<table border="0" cellpadding="0" cellspacing="0"><tr class="td1"><td>
		<s:form name="form2" id="form2" method="post">
			<jsp:include page="/jsp/included/ajax_page.jsp" flush="true"></jsp:include>
		</s:form>
	</td></tr>
	</table>
</div>
</div>
<script type="text/javascript">
	$(window.parent.document).find("#mainframe").height($("#agencyDiv").height()+20);
</script>