<!-- 委托书的相关附件模块 -->
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
				<!--相关附件-->
				<div class="box3" id="relateAtDiv">
				  <h4>相关附件</h4>
				  <div id="relateAtta">
				  	<jsp:include page="/jsp/cms/attachment.jsp"></jsp:include>
				  </div>
				  <div align="left">
				  	 <form action="${pageContext.request.contextPath}/cms/uploadFiles.action" id="relatedAtForm" name="relatedAtForm" method="post" enctype="multipart/form-data">
				  		&nbsp;<input type="file" name="_file" id="_file" class="zinput" >
				  		<s:hidden name="attachment.tmpCmsId" id="tmpCmsId"/>
				  		<s:hidden name="attachment.TCommission.id" id="cmsId"/>
				  		<s:hidden name="attachment.atTp" id="atTp" value="1"/>
				  		<input name="button2" type="button" class="anniu2" value="新增附件" id="upRelateAt"/>
				  	</form>
				  </div>
				</div>
<script type="text/javascript">	
	var unqCd;
	var cmsId;
	var flag = false;
	//相关附件开始
	$("#upRelateAt").click(function(){
		if(flag){return;}
		cmsId = window.parent.getCmsId();
		
		if(cmsId == undefined || cmsId <= 0){	//新增时使用
			alert("请先保存委托书再上传文件!");
			return;
			unqCd = window.parent.getUnqCd();
			if(unqCd != undefined && unqCd > 0){
				document.getElementById("tmpCmsId").value = unqCd;
			}
		}else{//修改时用
			cmsId = window.parent.getCmsId();
			document.getElementById("cmsId").value = cmsId;
		}
		if($("#_file").val()==''){
			alert('请先点击浏览');
			return;
		}
		document.getElementById("relatedAtForm").submit();
		flag = true;
	});
	//相关附件结束
	$(window.parent.document).find("#relateAtIframe").height($("#relateAtDiv").height()+20);
</script>
