<!-- 
	实物勘测
	/PEMng/WebRoot/jsp/cms/cms_artcls_rec.jsp
 -->
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
		</script>
	</head>
	
	<div class="box3" id="artclsRecDiv">
		<!--实物勘验-->
		<div align="left">
		 <form action="${pageContext.request.contextPath}/cms/uploadFiles.action" id="relatedAtForm" name="relatedAtForm" method="post" enctype="multipart/form-data">
		 	<s:hidden name="attachment.TCommission.id" id="cmsId"/>
			<s:hidden name="attachment.atTp" id="atTp" value="3"/>
		  	勘验文件：&nbsp;<input type="file" name="_file" id="_file" class="zinput" >
		  	<input name="button2" type="button" class="anniu2" value="上传" id="upArtclsRec"/>
		  	备注： &nbsp;<s:textfield name="saveDto.remark" id="remark" class="input1" />
		  </form>
		  </div>
		  <div id="artclsRec" > 
			  	<jsp:include page="/jsp/cms/attachment.jsp"></jsp:include>
		  </div>
		</div>  
		  
<script type="text/javascript">	
	var unqCd;
	var cmsId;
	var flag = false;
	//实物勘验开始
	$("#upArtclsRec").click(function(){
		if(flag){return;}
		cmsId = window.parent.getCmsId();
		if($("#_file").val()==''){
			alert('请先点击浏览');
			return;
		}
		if(cmsId != undefined && cmsId > 0){
			document.getElementById("cmsId").value = cmsId;
			document.getElementById("relatedAtForm").submit();
			flag = true;
		}
	});
	//实物勘验结束
	
	$(window.parent.document).find("#artclsRecIframe").height($("#artclsRecDiv").height()+20);
</script>
