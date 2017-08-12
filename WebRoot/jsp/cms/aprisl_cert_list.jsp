<!-- 
	价格鉴定/认定管理
	/PEMng/WebRoot/jsp/cms/aprisl_cert_list.jsp
 -->
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>价格鉴定/认定管理</title>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/cascadeSelect.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//下拉选框联动初始化
			initSelectComponentValues();
			<s:if test='cmsDto!=null && cmsDto.unitId1!=null'>
				$('#unitId1').val(${cmsDto.unitId1});
				selValueChange(0, SelGroups.objsArray[0], ${cmsDto.unitId1});
	 		</s:if>
			<s:if test='cmsDto!=null && cmsDto.unitId2!=null'>
				selValueChange(1, SelGroups.objsArray[0], ${cmsDto.unitId2});
				$('#unitId2').val(${cmsDto.unitId2});
				$('#unitId3').val(${cmsDto.unitId3});
	 		</s:if>
	 		
	 		    
			//下载导出
			$("#cmsExport").click(function(){
				var $info = $("#cms_info");
				if($info.html().indexOf("<td ") < 0 && $info.html().indexOf("<TD ") < 0){
					alert("没有导出的数据1！");
					return;
				}
				var urlParam = $("#searchForm").serialize();
				location.href = "${pageContext.request.contextPath}/cms/downLoadCms.action?" + urlParam;
			});
			
		});
	
		function initSelectComponentValues(){
			SelGroups.addSelect('unitId1', '', 'group1', null, '');
			SelGroups.addSelect('unitId2', '', 'group1', null, '');
			SelGroups.addSelect('unitId3', '', 'group1', null, '');
			SelGroups.init(${selectComponetValue});
			valueChange(0, SelGroups.objsArray[0]);
		}
		
		function delCms(){
	      	var ids=document.getElementsByName('cmsDto.ids');
      		var checked=false;
      		for(var i=0;i<ids.length;i++){
         		if(ids[i].checked ){
           			checked=true;
         		}
      		}
      		if(!checked){
				alert("请选择删除项");
				return;
			}
			if (confirm ("确定要删除吗？")){		
		  		document.cmsForm.action="${pageContext.request.contextPath}/cms/delCms.action";
            	document.cmsForm.submit();
	  		}
	    } 
		 
		function searchCms(){
			document.searchForm.action="${pageContext.request.contextPath}/cms/aprislCertList.action";
			document.searchForm.submit();
		}
		
		function onFile(){
			
			
		}
	  
		function checkAll(formvalue) {  
	          var roomids = document.getElementsByName(formvalue);  
	          for (var j = 0; j < roomids.length; j++) {  
	               if (roomids.item(j).checked == false) {  
	                   roomids.item(j).checked = true;  
	               }else{
	                   roomids.item(j).checked = false ;
	               }
	           }  
	     }
	 
		 function toCmsAdd(){
			 document.createForm.action="${pageContext.request.contextPath}/cms/toCmsAdd.action?tm="+ new Date().getTime();
		     document.createForm.submit();
		 }
		  
		 //修改价格鉴定/认定管理
		 function queryCms(aId){
			if(aId <= 0){
				var cmsId= $("#saveId").val();
				if(cmsId==null||cmsId==""){ 
					alert("请选择委托书 ");
					return;
				}
			}else{
				$("#saveId").val(aId);
			}
			document.cmsUpdateForm.action="${pageContext.request.contextPath}/cms/toUpdateCms.action?tm="+ new Date().getTime();
			document.cmsUpdateForm.submit();
		 }
			
		//归档
		function achivCms(){
	      	var ids=document.getElementsByName('cmsDto.ids');
      		var checked=false;
      		for(var i=0;i<ids.length;i++){
         		if(ids[i].checked ){
           			checked=true;
         		}
      		}
      		if(!checked){
				alert("请选择归档项");
				return;
			}
			if (confirm ("确定要归档吗？")){	
				var param=$("#cmsForm").serialize();
				$.ajax({
			       url: "${pageContext.request.contextPath}/cms/achiveCms.action?tm="+new Date().getTime(),
					type:"POST",
					data:param,
			        async: false,
					dataType:"json",
			        success: function(result){
			        	if(result.message =="0"){
							 alert("操作失败");
						}else{
							alert("操作已成功");
							searchCms();
						}
					},error:function(a){alert("数据异常！");}
				});
	  		}
	    } 
	 
	    //全选
		check_All = function(obj){
	    	var checkboxs = $(":input[name='cmsDto.ids']");
	    	if(checkboxs == undefined){
	          	return;
	        }else if(checkboxs != undefined && checkboxs.length == undefined ){
	          	checkboxs.checked = obj.checked;
	          	$('checkAll1').checked = obj.checked;
	        }else{
		    	for(var i=0;i<checkboxs.length;i++){
		    		checkboxs[i].checked = obj.checked;
		    	}
	          	$('checkAll1').checked = obj.checked;
	        }
	    }
	    
	</script>
	</head> 
	<body>
		<h3>鉴定/认定管理</h3>
		
		<!--下面内容开始-->
	<div class="box3-tc" id="searchCloseDiv">
		<form action="${pageContext.request.contextPath}/cms/aprislCertList.action" method="post" name="searchForm" id="searchForm">
				<s:hidden name="cmsDto.cmsType" id="cmsDto.cmsType" value="1"/>
				<s:hidden name="cmsDto.flag" id="cmsDto.flag" value="1"/>
				<table>
					<tr>
						<th>委托内容：</th>
						<td>
							<s:textfield name="cmsDto.cmsCnt" id="cmsCnt" cssClass="input1"></s:textfield>
						</td>
						<th>委托人:</th>
						<td>
							<s:textfield name="cmsDto.cmsUsr" id="cmsUsr" cssClass="input1"></s:textfield>
						</td>
						<th>状态:</th>
						<td>
							<s:select id="st" name="cmsDto.st" headerKey="01" cssClass="input1"
							list="#{'-1':'全部','01':'待受理','02':'补充材料','03':'已受理','05':'拒绝受理','13':'送达'}"/>
						</td>
						<th>涉嫌罪名:</th>
						<td>
							<s:textfield name="cmsDto.suspktOfens" id="suspktOfens" cssClass="input1"></s:textfield>
						</td>
					</tr>
					<tr>
						<th>鉴定文号:</th>
						<td>
							<s:textfield name="cmsDto.prcAprislDocmsNo" id="prcAprislDocmsNo" cssClass="input1"></s:textfield>
						</td>
						<th>受理员:</th>
						<td>
							<s:textfield name="cmsDto.acptUsrNm" id="acptUsrNm" cssClass="input1"></s:textfield>
						</td>
						<th>标的物名称:</th>
						<td>
							<s:textfield name="cmsDto.aprislArtclsNm" id="aprislArtclsNm" cssClass="input1"></s:textfield>
						</td>
						<th>委托书类别:</th>
						<td>
							<s:select id="cmsTp" name="cmsDto.cmsTp" cssClass="input1" headerKey="-1" headerValue="全部" list="#{'1':'价格鉴定委托书','2':'价格认定委托书'}"/>
						</td>
					</tr>
					<tr>
						<th>鉴定基准日:</th>
						<td colspan="3">
							<s:textfield name="cmsDto.beginTime" id="beginTime" readonly="true" cssClass="input1" /> 
							<img onclick="WdatePicker({el:'beginTime',readOnly:true,maxDate: '#F{$dp.$D(\'endTime\',{d:0})}'});" src="${pageContext.request.contextPath}/images/ril.png"
								width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"/>
							&nbsp;至
							<s:textfield name="cmsDto.endTime" id="endTime" readonly="true" cssClass="input1" />
							<img onclick="WdatePicker({el:'endTime',readOnly:true,minDate: '#F{$dp.$D(\'beginTime\',{d:0})}'});"
								src="${pageContext.request.contextPath}/images/ril.png" width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"/>
						</td>
						<th>委托单位：</th>
						<td colspan="3">
							<select class="input1" name="cmsDto.unitId1" id="unitId1">
							 		<option value="">请选择</option>
							</select>
						 	<select name="cmsDto.unitId2" class="input1" id="unitId2">
						   		<option value="">请选择</option>
						 	</select>
						 	<select name="cmsDto.unitId3" class="input1" id="unitId3">
						   		<option value="">请选择</option>
						 	</select>
						</td>
					</tr>
					<tr>
						<th>委托日期:</th>
						<td colspan="7">
							<s:textfield name="cmsDto.beginTime1" id="beginTime1"readonly="true" cssClass="input1" /> 
							<img onclick="WdatePicker({el:'beginTime1',readOnly:true,maxDate: '#F{$dp.$D(\'endTime1\',{d:0})}'});" src="${pageContext.request.contextPath}/images/ril.png"
								width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"/>
							&nbsp;至
							<s:textfield name="cmsDto.endTime1" id="endTime1" readonly="true" cssClass="input1" />
							<img onclick="WdatePicker({el:'endTime1',readOnly:true,minDate: '#F{$dp.$D(\'beginTime1\',{d:0})}'});"
								src="${pageContext.request.contextPath}/images/ril.png" width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"/>
						</td>
					</tr>
					<tr>
						<td colspan="8" align="right">
						<div class="buttomk1">
						 	<input  name="button" type="button"  class="anniu"  value="查询" onclick="javascript:searchCms();"/>
							<input buttonAction="add" id="cmsExport" name="button2" type="button"  class="anniu"  value="导出"/>
						 	 <input type="button" class="anniu" id="button" value="新增" onclick="javascript:toCmsAdd();">
							<input buttonAction="add" name="button2" type="button"  class="anniu"  value="归档" onclick="javascript:achivCms();" />
							<input buttonAction="del" name="button3" type="button"  class="anniu"  value="删除" onclick="javascript:delCms();" /> 
						</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
			<div class="box1">
				<form  method="post" name="cmsForm" id="cmsForm">
					<s:hidden name="cmsFlag" value="1"/>
						<table id="cms_info" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<th width="5%" align="center"><input type="checkbox" name="checkAll1" id="checkAll1" onclick="javascript:check_All(this);" />全选</th>
								<th width="10%" align="center">鉴定文号</th>
								<th width="20%" align="center">委托内容</th>
								<th width="10%" align="center">委托单位简称</th>
								<th width="8%" align="center">委托日期</th>
								<th width="8%" align="center">鉴定基准日</th>
								<th width="8%" align="center">鉴定金额</th>
								<th width="9%" align="center">委托书类别</th>
								<th width="8%" align="center">受理员</th>
								<th width="6%" align="center">状态</th>
								<th width="6%" align="center">是否归档</th>
							</tr> 
							<s:iterator value="cmList" status="status">
								<tr <s:if test="prtDt != null">class="tablerowhighlight"</s:if><s:elseif test="%{#status.index%2==0}">class="td1"</s:elseif>>
									<td id="checkId_<s:property value='id'/>" align="center">
										<input type="checkbox" value="<s:property value="id" />" id="cmsId" name="cmsDto.ids" />
									</td>
									<td style="text-align: center" nowrap>
										<a href="#" id="modifyCms"  onclick="javascritp:queryCms('<s:property value='id'/>')"><s:property value="prcAprislDocmsNo"/></a>
									</td>
									<td style="text-align: center" nowrap>
										<s:if test="cmsCnt != null && cmsCnt.toString().length() > 20">
						            	 	<span title="<s:property value="cmsCnt"/>">
						            	 		<s:property value="cmsCnt.toString().substring(0,20)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="cmsCnt"/>
						            	 </s:else>		</td>
									<td style="text-align: center" nowrap>
									 	<s:property value="tunitsInfo.shortName"/>
									</td>
									<td align="center">
										<s:date name="crtTime" format="yyyy-MM-dd" />
									</td>
									<td align="center">
										<s:date name="prcAprislBaseDt" format="yyyy-MM-dd" />
									</td>
									<td style="text-align: center">
										<s:property value="aprislMnySum"/>
									</td>
									<td align="center">
										<s:property value="@com.pemng.serviceSystem.common.utils.PeUtil@getTpStr(cmsTp)"/>
									</td>
									<td align="center">
										<s:property value="acptUsrId.name"/>
									</td>
									<td align="center">
										<s:property value="@com.pemng.serviceSystem.common.utils.PeUtil@getStStr(st)"/>
									</td>
									<td align="center">
										<s:property value="@com.pemng.serviceSystem.common.utils.PeUtil@getAchivStr(achiv)"/>
									</td>
								</tr>
							</s:iterator> 
							<s:if test="cmList != null && cmList.size()>0">
								<tr class="tablecount">
									<td width="15%" colspan="11" style="text-align: left">&nbsp;&nbsp;&nbsp;统计&nbsp;&nbsp;&nbsp;数量:${dataMap.numCnt}&nbsp;&nbsp;&nbsp;金额:${dataMap.mnyCnt}</td>
								</tr> 
							</s:if>
						</table>
			</form>
			<form action="" id="createForm" name="createForm">
				<s:hidden name="cmsFlag" value="1"/>
			</form>
			<form action="" id="cmsUpdateForm" name="cmsUpdateForm">
				<s:hidden name="saveDto.id" id="saveId"/>
				<s:hidden name="cmsFlag" value="1"/>
			</form>
		</div> 
		<div class="kuang"><div class="box2">
			<table border="0" cellpadding="0" cellspacing="0"><tr class="td1"><td>
				<s:form name="form2" id="form2" method="post">
					<jsp:include page="/jsp/included/page.jsp" flush="true"></jsp:include>
				</s:form>
			</td></tr>
			</table>
		</div>
		</div>
</body>
</html>
