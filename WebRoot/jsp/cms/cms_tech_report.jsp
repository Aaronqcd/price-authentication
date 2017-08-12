<!-- 委托书的技术报告模块 -->
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<head>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script type="text/javascript">
		$(document).ready(function(){
			<s:if test='atFlag == 1'>
				alert("请选择上传文件!");
			</s:if>
			<s:elseif test='atFlag == 2'>
				alert("您上传的文件类型不正确!");
			</s:elseif>
			<s:elseif test='atFlag == 3'>
				alert("您上传的文件过大了!");
			</s:elseif>
			<s:elseif test='atFlag == 99'>
				alert("文件不存在!");
			</s:elseif>
			<s:elseif test='atFlag == 88'>
				alert("文书号不存在!");
			</s:elseif>
		});
		
		//打印委托书
		   //printCmsTypeValue 打印的类型
		   function printCms() {
			
			   document.toPrintCmsForm.action="${pageContext.request.contextPath}/cms/toPrintCms.action?tm="+ new Date().getTime();
			   document.toPrintCmsForm.submit();
		   }
		 
		
		
		</script>
	</head>
		<!--技术报告-->
		<div class="box3"  id="techReportDiv">
			<h4>技术报告</h4>
		  	<div align="left">
			   <form action="${pageContext.request.contextPath}/cms/uploadFiles.action" id="techReportForm" name="techReportForm" method="post" enctype="multipart/form-data">
				  	文件：&nbsp;<input type="file" name="_file" id="_file" class="zinput" >
				  		<s:hidden name="attachment.TCommission.id" id="cmsId"/>
				  		<s:hidden name="attachment.atTp" id="atTp" value="2"/>
				  	<input name="button2" type="button" class="anniu2" value="上传" id="upTechReport"/>
				  	备注： &nbsp;<input type="text" name="attachment.remark" id="remark" class="zinput" >
				</form>
		  	</div>
		  	<div id="techReport">
			   	<jsp:include page="/jsp/cms/attachment.jsp"></jsp:include>
		  	</div>
		  	<div align="center">
		  	<input name="button2" type="button" class="anniu" onclick="javascript:printCms()"  value="生成技术报告" style="display: none"/>
		 	 </div>
		 	 <form action="" id="toPrintCmsForm" name="toPrintCmsForm"  method="post" target="_blank">
				<input type="hidden" name="printCmsId" id="printCmsId" value="<s:property value="attachment.TCommission.id"/>" />
				<s:hidden name="printCmsType" id="printCmsType" value="-1"/>
				<s:hidden name="attachmentType" id="attachmentType" value="2"/>
				<s:hidden name="isGenerateCms" id="isGenerateCms" value="false"/>
			</form>
		</div>
<script type="text/javascript">	
	var unqCd;
	var cmsId;
	var flag = false;
	//技术报告开始
	$("#upTechReport").click(function(){
		if(flag){return;}
		cmsId = window.parent.getCmsId();
		if($("#_file").val()==''){
			alert('请先点击浏览');
			return;
		}
		if(cmsId != undefined && cmsId > 0){
			document.getElementById("cmsId").value = cmsId;
			document.getElementById("techReportForm").submit();
			flag = true;
		}
	});
	//技术报告结束
	$(window.parent.document).find("#techReportIframe").height($("#techReportDiv").height()+20);
</script>
