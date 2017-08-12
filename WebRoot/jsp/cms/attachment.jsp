<!-- 相关附件， 实物勘验， 技术报告 附件相关列表 -->
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table border="0" cellSpacing="0" cellPadding="0">
    <tr>
      <th width="5%">序号</th>
      <th width="10%">类型</th>
      <th width="30%">附件名称</th>
      <th width="10%">大小</th>
	  <th width="10%">上传人</th>
      <th width="20%">上传时间</th>
      <th width="5%">下载统计</th>
      <th width="10%">操作</th>
    </tr>
    <s:if test="null eq atList || atList.size() eq 0">
		<tr class="td1" class="td1">
			<td colspan="8" style="text-align: left;">找不到相符的内容或信息。</td>
		</tr>
	</s:if>
	<s:else>
	 	<s:iterator value="atList" status="st" id="rep">
			<tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
				 <td><s:property value="%{#st.index+1}"/></td>
			     <td><s:property value="fileTp"/></td>
			     <td><a href="#" style="text-decoration:underline" onclick="javascript:downLoadAt(<s:property value="id"/>)">
			     <s:if test="fileNm != null && fileNm.toString().length() > 20">
					<span title="<s:property value="fileNm"/>" style="color:black">
						<s:property value="fileNm.toString().substring(0,20)"/>...
					</span>
				 </s:if>
				 <s:else>
					<s:property value="fileNm"/>
				 </s:else>
			     </a></td>
			     <td class="td_right"><s:property value="fileSize"/></td>
			     <td class="td_midder"><s:property value="TUserInfo.name"/></td>
			     <td><s:date name="crtTime" format="yyyy-MM-dd"/></td>
			     <td><s:property value="downloads"/></td>
			     <td><a href="#" style="text-decoration:underline" onclick="javascript:delAt(<s:property value="id"/>);">删除</a></td>
			</tr>
		</s:iterator>
	</s:else>     
  </table>
  <form  method="post" name="deleteAtForm" id="deleteAtForm">
  	<s:hidden name="attachment.id" id="atId"/>
  	<s:hidden name="attachment.TCommission.id" id="cmsId"/>
  	<s:hidden name="attachment.tmpCmsId" id="unqCd"/>
  	<s:hidden name="attachment.atTp" id="_atTp"/>
  </form>
<script type="text/javascript">
	//删除附件
	function delAt(atId){
		if(confirm("确定删除附件？")){
			unqCd = window.parent.getUnqCd();
			cmsId = window.parent.getCmsId();
			if(unqCd!= undefined && unqCd>0){
				$("#unqCd").val(unqCd);
			}else if(cmsId!= undefined  && cmsId>0){
				$("#cmsId").val(cmsId);
			}
			$("#atId").val(atId);
			$("#_atTp").val($("#atTp").val());
			document.deleteAtForm.action="${pageContext.request.contextPath}/cms/delAt.action";
	        document.deleteAtForm.submit();
		}
	};
	
	//下载附件
	function downLoadAt(atId){
		window.location.href="${pageContext.request.contextPath}/cms/downloadAttachment.action?atId=" + atId;
		//window.parent.downLoadAt(atId);
	}
</script>