<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>委托书预览</title>
	
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/cascadeSelect.js"></script>
		<script type="text/javascript">

		$(document).ready(function(){
           	$("#NO").addClass("validate[required],maxSize[20]");
           	$("#suspktOfens21").addClass("validate[maxSize[200]]");
           	$("#prcAprislBaseDt").addClass("validate[required]");              	//价格基准日
           	$("#aprislTp").addClass("validate[validate[required]]");          	//鉴定类别
           	$("#cmsUser").addClass("validate[maxSize[20]]");
           	$("#cmsUnitAdd").addClass("validate[maxSize[200]]");
           	$("#cmsUsrTel").addClass("validate[maxUtf8Size[11]]"); //手机
           	$("#cmsCnt").addClass("validate[validate[required]]");
            $("#postcode").addClass("validate[custom[postCode],maxSize[10]]");  
            $("#unitPhon").addClass("validate[maxSize[20]]");
            $("#prcAprislRqrms").addClass("validate[maxSize[500]]");
            $("#cmsCnt").addClass("validate[maxSize[300]]");  
            $("#recAdrs").addClass("validate[maxSize[100]]");
            $("#firstTrialOpinis").addClass("validate[maxSize[500]]");
            $("#retrialOpinis").addClass("validate[maxSize[500]]");
            $("#lastTrialOpinis").addClass("validate[maxSize[500]]");
            $("#aprislTp").addClass("validate[funcCall[checkType]]");           //鉴定类别
            $("#unitId1").addClass("validate[funcCall[checkSort1]]"); 
            $("#unitId2").addClass("validate[funcCall[checkSort2]]"); 
            $("#unitId2").addClass("validate[funcCall[checkSort3]]"); 
            jQuery("#cmsForm").validationEngine();
			//下拉选框联动初始化
			initSelectComponentValues();
			initCheckValue();
			<s:if test='saveDto!=null && saveDto.unitId1!=null'>
				$('#unitId1').val(${saveDto.unitId1});
				selValueChange(0, SelGroups.objsArray[0], ${saveDto.unitId1});
	 		</s:if>
			<s:if test='saveDto!=null && saveDto.unitId2!=null'>
				selValueChange(1, SelGroups.objsArray[0], ${saveDto.unitId2});
				$('#unitId2').val(${saveDto.unitId2});
				$('#unitId3').val(${saveDto.unitId3});
				var text=$("#unitId1").find("option:selected").text()+"  "+$("#unitId2").find("option:selected").text()+"  "+$("#unitId3").find("option:selected").text();
				$("#unit").text('').text(text);
	 		</s:if>
	 		
	 		//涉嫌罪名
			<s:if test='null != saveDto && saveDto.otherCase'>
   				$(":input[name='saveDto.suspktOfens1']").attr("disabled","disabled"); 
			</s:if>
			
			 $(":input[name='saveDto.suspktOfens1").click(function(e){
					var clen = $(":input[name='saveDto.suspktOfens1'][checked]").length;
		   			if(clen >0){
		   				document.getElementById("so").style.display ="none";
			   			$(":input[name='saveDto.suspktOfens1']").removeAttr("disabled"); 
			   			$("#suspktOfens21").val("");
		   			};
			 });
			
			$("#shexian").text('').text();
			var suspktOfens = document.getElementById("suspktOfensABC").value;
			$("#shexian").text('').text(suspktOfens);
			
			<s:if test='atFlag == 99'>
				alert("文件不存在!");
			</s:if>
		});
	
		//添加鉴定物品以后返回
		function afterCreateAprisl(){
			$.ajax({
		            url: "${pageContext.request.contextPath}/cms/getAprislList.action",
		            cache:false,
		            dataType:"html",
		            data: {"aprislDto.tmpCmsId":getUnqCd(),"aprislDto.TCommission.id":getCmsId()},
		            success: function(html){
		            	$("#cmsDetail").html('').html(html);
		            }
		        });
		}
		
		function initSelectComponentValues(){
			SelGroups.addSelect('unitId1', '', 'group1', null, '');
			SelGroups.addSelect('unitId2', '', 'group1', null, '');
			SelGroups.addSelect('unitId3', '', 'group1', null, '');
			SelGroups.init(${selectComponetValue});
			valueChange(0, SelGroups.objsArray[0]);
		}

		//价格鉴定
		function goAprisl(id) {
			window.open("${pageContext.request.contextPath}/aprisl/prcAprisl.action?aprislDto.id="+id, "goAprisl");
		}
		
		//添加鉴定物品
		function toCreateAprisl(){
			<s:if test="null != saveDto && saveDto.id > 0 " >
				window.open("${pageContext.request.contextPath}/aprisl/toCreateAprisl.action?aprislDto.TCommission.id="+getCmsId(), "toCreateAprisl");
			</s:if>
			<s:else>
				window.open("${pageContext.request.contextPath}/aprisl/toCreateAprisl.action?aprislDto.tmpCmsId="+getUnqCd(), "toCreateAprisl");
			</s:else>
			
		}

		function goCase() {
			window.open("${pageContext.request.contextPath}/cms/hisCase.action", "goCase");
		}
		
		function setCms(cnt,cmsId) {
			document.getElementById("hisCmsId").value = cmsId;
			$("#cmsCnt21").html(cnt);
			$("#hisCmsCnt").val(cnt);
		}
		
		function goUser(){
			window.open("${pageContext.request.contextPath}/usrmng/selectName.action", "goUser");
		}
		
	
		function setUser(userId){
			document.getElementById("userId").value=userId;
		}
		
		function sethiddenUser(cmsUsr){
			document.getElementById("cmsUsr").value=cmsUsr;
		}
		
		function getUnqCd(){
			var unqCd = $("#unqCd").val();
			if(unqCd != undefined){
				return unqCd;
			}else{
				return 0;
			}
		}
		function getCmsId(){
			var cmsId = $("#cmsId").val();
			if(cmsId != undefined){
				return cmsId;
			}else{
				return 0;
			}
		}
		
		function checkType()
        {
        	var checkValue = $("#aprislTp").val();
			if(checkValue == "0"){
				return "请选择鉴定类别";
			}else{
				return ;
			}
        }
        function checkSort1()
        {
        	var checkValue1 = $("#unitId1").val();	
			if(checkValue1 == "0"){
				return "请选择委托单位";
			}else{
				return ;
			}
        }
        
         function checkSort2()
        {
        	var checkValue2 = $("#unitId2").val();
			if(checkValue2 == "0"){
				return "请选择委托单位";
			}else{
				return ;
			}
        }
        
         function checkSort3()
        {
        	var checkValue3 = $("#unitId2").val();
			if(checkValue3 == "0"){
				return "请选择委托单位";
			}else{
				return ;
			}
        }
      
      	function checkTp() {
      		if($(":input[name='saveDto.cmsTp'][checked]").val() == undefined) {
				return "*请选择有无实物";
			} else{
				return;
			}
      	}
      	
      	function checkEnty()
      	{
      		if($(":input[name='saveDto.isEnty'][checked]").val() == undefined)
      		{
				return "*请选择有无实物";
			}
      	}
		 //增加/修改按钮      
       	function insertOrUpdate(){
	        if (!jQuery("#cmsForm").validationEngine('validate')) {
            	return;
         	}
         	var suspktOfens2 = document.getElementById("suspktOfens21").value;
        	var checkBox = $('input:checkbox').filter(':checked');
			if(checkBox.length<=0 && suspktOfens2==""){
				alert("请选择涉嫌罪名");
				return;
			}
			var suspktOfens = document.getElementById("suspktOfens99");
			if(suspktOfens.checked){
				if(suspktOfens2==""){
					alert("请填写其他案件");
					return;
				}
			}
	     	document.cmsForm.action="${pageContext.request.contextPath}/cms/saveCms.action";
	     	document.cmsForm.submit();
	   	} 
	   		 
        function toAddOrgnlCms(){
        	 document.ociForm.action="${pageContext.request.contextPath}/cms/toCmsAdd.action?tm="+ new Date().getTime();
		     document.ociForm.submit();
        }
        
	   	function onload() 	{
	   		var pad = document.getElementById("pad").value;
	   		var pad1 = pad.substring(6,12);
	   		$("#pd").html(pad1);
	   		
	   		var string = document.getElementById("NO").value;
	   		if(string.indexOf("京")==-1){
	   			return;
	   		}
			else{
				var str1 = '鉴';
    			var str_before = string.split(str1)[0];
				var str2 = '价';
   				var str_after = str_before.split(str2)[1];
   				document.getElementById("NO").value = str_after;
			}
	   	}
	   
	   function trim(str){ //删除左右两端的空格
　　    	 return str.replace(/(^\s*)|(\s*$)/g, "");
　　 		}

	   function initCheckValue()
	   {
	   		var ck = document.getElementById("suspktOfens21").value;
	   		if(ck != ""){
	   			document.getElementById("suspktOfens99").checked = true;
	   			document.getElementById("so").style.display ="";
	   		}else{
	   			document.getElementById("so").style.display ="none";
	   		}
	   		
	   		var suspktOfens = document.getElementById("suspktOfens").value;
	   		var cks = document.getElementsByName("saveDto.suspktOfens1");
	   		if(ck == "" && suspktOfens != ""){
	   			var p = suspktOfens.split(",");
				for(var i=0;i<cks.length;i++){
					for(var j=0;j<p.length;j++){
						if(p[j]!="" && (trim(cks[i].value))==(trim(p[j]))){
							cks[i].checked=true;
						}
					}
				}
	   		}
	   }
	   
	   function showIn(){
	   		var ck = document.getElementById("suspktOfens99");
	   		if(ck.checked){
	   			document.getElementById("so").style.display ="";
	   		}else{
	   			document.getElementById("so").style.display ="none";
	   		}
	   }
	   
	    //打印委托书
	   //printCmsTypeValue 打印的类型
	   function printCms(printCmsTypeValue , attachmentTypeValue) {
			document.getElementById("isGenerateCms").value="false";  
	   		<s:if test="saveDto!=null && saveDto.st!=null && !saveDto.st.matches(\"13|14\")">
				if("-1" != printCmsTypeValue) {
		        	if (confirm("是否重新生成")) {  
		        		document.getElementById("isGenerateCms").value="true";
		        	}  
			 	}
	   		</s:if>
		   document.getElementById("printCmsType").value=printCmsTypeValue;
		   document.getElementById("attachmentType").value=attachmentTypeValue;
		   document.toPrintCmsForm.action="${pageContext.request.contextPath}/cms/toPrintCms.action?tm="+ new Date().getTime();
		   document.toPrintCmsForm.submit();
	   }
		</script>
	</head>
	<body>
		<div class="dkuang">
			
			
			<div class="clear"></div>
			<div id="tagContent">
				<form action="" method="post" id="cmsForm" name="cmsForm">
					<s:hidden name="cmsFlag" id="cmsFlag"/>
					<s:hidden name="saveDto.suspktOfens" id="suspktOfens"/>
					<s:hidden name="saveDto.suspktOfens" id="suspktOfensABC"/>
					
					<s:if test="null != saveDto && saveDto.id > 0">
						<s:hidden name="saveDto.id" id="cmsId"/>
					</s:if>
					<s:else>
						<input type="hidden" name="saveDto.tmpCmsId" id="unqCd" value="<s:property value='unqCd'/>"/>
					</s:else>
					<div class="tabnr">
					<div class="box2"> 
						<h4>基本信息</h4>
						<table>
						  <tr>
						    <th>委托书类型：</th>
						    <td>
						    <s:if test="1==saveDto.cmsTp">鉴定</s:if>
						    <s:elseif test="2==saveDto.cmsTp">认定</s:elseif>
						     <s:elseif test="3==saveDto.cmsTp">复核裁定</s:elseif>
						    </td>
						    <th>有无实物：</th>
						    <td>
						    <s:if test="1==saveDto.isEnty">有实物</s:if>
						    <s:else>无实物</s:else>
						    </td>
						    <th> 鉴定文号： </th>
			 				<td><s:property value="saveDto.prcAprislDocmsNo" /></td>
						   </tr>
						  <tr>
						    <th>涉嫌罪名：</th>
						    <td colspan="5" id="shexian">
								   1:刑事案件
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens1" value="1"/>盗窃罪
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens2" value="2"/>抢劫罪
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens3" value="3"/>故意毁坏财物
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens4" value="4"/>破坏生产经营罪
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens5" value="5"/>受贿罪
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens6" value="6"/>扒窃罪
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens7" value="7"/>寻衅滋事罪
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens8" value="8"/>诈骗罪
								    <input type="checkbox" name="saveDto.suspktOfens1" id="suspktOfens9" value="9"/>生产销售假冒伪劣产品罪
								    <s:if test='null != saveDto && saveDto.otherCase'>
								       <input type="checkbox" id="suspktOfens99" value="99" onclick="showIn();" name="ck" checked="checked"/>2:其他案件
								    		<span id="so">
								    		<s:textfield name="saveDto.suspktOfens2" id="suspktOfens21" cssClass="input1"></s:textfield>
								    		</span><span style="color:red">*</span>
								    </s:if>
								    <s:else>
								       <input type="checkbox" id="suspktOfens99" value="99" onclick="showIn();" name="ck" />2:其他案件
								    		<span id="so" style="display: none">
								    		<s:textfield name="saveDto.suspktOfens2" id="suspktOfens21" cssClass="input1"></s:textfield>
								    		</span><span style="color:red">*</span>
								    </s:else>
						    	</td>
						    </tr>
						    <tr>
						    <th>委托单位：</th>
							<td colspan="3" id="unit">
							    <s:if test="saveDto.cmsTp == 3">
							    	<s:select name="saveDto.cmsUnitAreaId" cssClass="input1" 
										list="@com.pemng.serviceSystem.base.util.WSUtils@getBasicDataList('001')" 
										listValue="value" listKey="id" headerKey="-1" headerValue="请选择">
										</s:select> 
										<s:textfield name="saveDto.cmsUnitNm" size="40" cssClass="input1" id="cmsUnitNm"></s:textfield>
							    </s:if>
							    <s:else>
										<select class="input1" name="unitId1" id="unitId1" style="width:120px;" onchange="checkSort1()">
										 		<option value="0">请选择</option>
										</select>
									 	<select name="unitId2" class="input1" id="unitId2" style="width:120px;" onchange="checkSort2()">
									   		<option value="0">请选择</option>
									 	</select>
									 	
									 	<select name="saveDto.tunitsInfo.id" id="unitId3" class="input1" style="width:160px;" onchange="checkSort3()">
									   		<option value="0">请选择</option>
									 	</select><span style="color:red">*</span>
							    </s:else>
							</td>
							<th>委托人:</th>
							<td><s:property value="saveDto.cmsUsr"/></td>
						    </tr>
						  <tr>
						    <th>委托地区：</th>
						    <td><s:property value="saveDto.cmsUnitAdd"/></td>
						    <th>邮编：</th>
						    <td><s:property value="saveDto.postcode"/></td>
						    <th>单位电话：</th>
						    <td><s:property value="saveDto.unitPhon"/></td>
						    </tr>
						  <tr>
						    <th>价格鉴定要求：</th>
						    <td><s:property value="saveDto.prcAprislRqrms"/></td>
						    <th>价格鉴定基准日：</th>
						    <td><s:date name="saveDto.prcAprislBaseDt" format="yyyy-MM-dd"/></td>
						    <th>鉴定类别：</th>
						    <td>
						    <s:if test="11==saveDto.aprislTp">刑事
						    </s:if>
						   	<s:elseif test="13==saveDto.aprislTp">纪检监察
						   	</s:elseif>
						   	<s:elseif test="13==saveDto.aprislTp">行政执法
						   	</s:elseif>
						   	<s:elseif test="13==saveDto.aprislTp">其他
						   	</s:elseif>
						   	<s:else>
						   	</s:else>
						    </tr>
						     <s:if test="saveDto.cmsTp == 3">
							     <tr>
								    <th>原单位鉴定金额：</th>
								    <td colspan="5">
								    <s:textfield name="saveDto.ociAprislMny"  cssClass="input1" id="ociAprislMny"/>
								    <span style="display:none" id="dis">是否维持原结论：<label id="fous"></label><font color="red">（金额与原鉴定结论金额相差上下不超过10%）</font></span>
								    </td>
							    </tr>
						    </s:if>
						  <tr>
						    <th>委托人手机：</th>
						    <td colspan="5"><s:property value="saveDto.cmsUsrTel"/></td>
						    </tr>
						  <tr>
						    <th>鉴定标的描述：</th>
						    <td colspan="3"><s:property value="saveDto.artclsDetail"/></td>
						  </tr>
						  <tr>
						    <th>委托内容：</th>
						    <td colspan="5"><s:property value="saveDto.cmsCnt"/></td>
						  </tr>
						  <tr>
						    <th>历史相关案件：</th>
						    <td colspan="5">
						     <label id="cmsCnt21"><s:property value="saveDto.TCommission.cmsCnt"/></label>
						    <input type="hidden" id="hisCmsId" name="saveDto.TCommission.id">
						    <input type="hidden" id="hisCmsCnt" name="saveDto.TCommission.cmsCnt">
						    </td>
						  </tr>
						  <tr>
						    <th>委托日期：</th>
						    <td><s:date name="saveDto.crtTime" format="yyyy-MM-dd"/></td>
						     <th>价格鉴定基准日：</th>
						    <td><s:date name="saveDto.prcAprislBaseDt" format="yyyy-MM-dd"/></td>
						     <th>价格鉴定文书号：</th>
						    <td><s:property value="saveDto.prcAprislDocmsNo"/> </td>
						  </tr>
						  <tr>
						    <th>受理员：</th>
						    <td><s:property value="saveDto.acptUsrId.name"/></td>
						     <th>受理日期：</th>
						    <td><s:date name="saveDto.acptDt" format="yyyy-MM-dd"/></td>
						     <th>案件处理剩余天数：</th>
						    <td><s:if test="saveDto.overDueNum < 0"><span style="color:red"><s:property value="saveDto.overDueNum"/> 天</span></s:if><s:else><s:property value="saveDto.overDueNum"/> 天</s:else></td>
						  </tr>
						</table>
					</div>
					<div class="box3">
					  <h4>委托明细</h4>
					  <div id="cmsDetail">
					  	<table id="detail_Tbl" border="0" cellSpacing="0" cellPadding="0">
							<tr>
								<th width="5%">序号</th>
								<th width="20%">标的名称</th>
								<th width="5%">数量</th>
								<th width="15%">价格鉴定基准日</th>
								<th width="10%">市场价格</th>
								<th width="15%">购买时间</th>
								<th width="5%">成新率</th>
								<th width="5%">配件</th>
								<th width="10%">鉴定金额</th>
							</tr>
							<s:if test="null eq saveDto.TAprislArtclsInfos || saveDto.TAprislArtclsInfos.size() eq 0">
								<tr class="td1" class="td1">
									<td colspan="10" style="text-align: left;">找不到相符的内容或信息。</td>
								</tr>
							</s:if>
							<s:else>
							<s:iterator value="saveDto.TAprislArtclsInfos" status='st_'>
									<tr>
										<td><s:property value="%{#st_.index+1}"/></td>
										<td>
										<s:if test="artclNm != null && artclNm.toString().length() > 10">
											<span title="<s:property value="artclNm"/>" style="color:black">
												<s:property value="artclNm.toString().substring(0,10)"/>...
											</span>
										</s:if>
										<s:else>
											<s:property value="artclNm"/>
										</s:else>	
										</td>
										<td><s:property value="qnty" /></td>
										<td><s:date name="prcArtclRefDt" format="yyyy-MM-dd" /></td>
										<td class="td_right"><s:property value="orgnlPrc" /><s:if test="null != orgnlPrc">元</s:if></td>
										<td><s:property value="buyTm" /></td>
										<td><s:property value="newRate" /><s:if test="null != newRate">%</s:if></td>
										<td>
											<s:if test="null == TCmsArtclsAccesorses || TCmsArtclsAccesorses.size()<=0">无</s:if>
											<s:else>有</s:else>
										</td>
										<td><s:property value="aprislPrc" /><s:if test="null != aprislPrc">元</s:if></td>
									</tr>
							</s:iterator>
							</s:else>
						</table>
					  </div>
					</div>
							
					
					<!--相关附件-->
				<div class="box3" id="relateAtDiv">
				  <h4>相关附件</h4>
				  <div id="relateAtta">
				  	<table border="0" cellSpacing="0" cellPadding="0">
					    <tr>
					     <th width="5%">序号</th>
					      <th width="10%">类型</th>
					      <th width="30%">附件名称</th>
					      <th width="10%">大小</th>
						  <th width="10%">上传人</th>
					      <th width="20%">上传时间</th>
					      <th width="5%">下载统计</th>
					    </tr>
					    <s:if test="null eq atList1 || atList1.size() eq 0">
							<tr class="td1" class="td1">
								<td colspan="8" style="text-align: left;">找不到相符的内容或信息。</td>
							</tr>
						</s:if>
						<s:else>
							<s:iterator value="atList1" status="st" id="rep">
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
								</tr>
							</s:iterator>
						</s:else>     
					  </table>
				  </div>
				</div>
					<s:if test="null != saveDto && saveDto.id > 0">
					<div class="box3">
					  <h4>实物勘验</h4>
					  <div align="left">
						  查验人员：<s:property value="saveDto.userNames" />
						&nbsp; 查验日期：<s:date name="saveDto.recDt" format="yyyy-MM-dd"/>
						&nbsp;查验地点：<s:property value="saveDto.recAdrs"/>
						<input type="hidden" id="cmsUsr" name="saveDto.recUsrIds" >
					  </div>
					  <br/>
					  
					  	<!--实物勘验附件-->
					 <div>
				  	<table border="0" cellSpacing="0" cellPadding="0">
					    <tr>
					      <th width="5%">序号</th>
					      <th width="10%">类型</th>
					      <th width="30%">附件名称</th>
					      <th width="10%">大小</th>
						  <th width="10%">上传人</th>
					      <th width="20%">上传时间</th>
					      <th width="5%">下载统计</th>
					    </tr>
					    <s:if test="null eq atList2 || atList2.size() eq 0">
							<tr class="td1" class="td1">
								<td colspan="8" style="text-align: left;">找不到相符的内容或信息。</td>
							</tr>
						</s:if>
						<s:else>
							<s:iterator value="atList2" status="st" id="rep">
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
								</tr>
							</s:iterator>
						</s:else>     
					  </table>
				  </div>
					  
					  <div align="center">
					  	<input name="button2" type="button" class="anniu" value="打印委托书" onclick="javascript:printCms('1' , '0')"/>
					  	<input name="button2" type="button" class="anniu" value="取件通知书" onclick="javascript:printCms('2' , '0')"/>
					  	<input name="button2" type="button" class="anniu" value="生成送达回证" onclick="javascript:printCms('3' , '0')"/>
					  	<input name="button2" type="button" class="anniu" value="生成鉴定结论" onclick="javascript:printCms('4' , '0')"/>
					  </div>
					  
					</div>
					<!--技术报告-->
				<div class="box3" >
				  <h4>技术报告</h4>
				  <div >
				  	<table border="0" cellSpacing="0" cellPadding="0">
					    <tr>
					      <th width="5%">序号</th>
					      <th width="10%">类型</th>
					      <th width="30%">附件名称</th>
					      <th width="10%">大小</th>
						  <th width="10%">上传人</th>
					      <th width="20%">上传时间</th>
					      <th width="5%">下载统计</th>
					    </tr>
					    <s:if test="null eq atList3 || atList3.size() eq 0">
							<tr class="td1" class="td1">
								<td colspan="8" style="text-align: left;">找不到相符的内容或信息。</td>
							</tr>
						</s:if>
						<s:else>
							<s:iterator value="atList3" status="st" id="rep">
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
								</tr>
							</s:iterator>
						</s:else>     
					  </table>
				  </div>
				</div>
					<!--技术报告待删除-->
					
					
					
					<div class="box3">
					 	<h4>审批记录</h4>
						<table border="0" cellSpacing="0" cellPadding="0">
							 <tr>
							    <th width="15%"><!-- 初审 -->创建时间</th>
							    <th width="10%"><!-- 复审 -->操作人</th>
							    <th width="20%"><!-- 终审 -->操作</th>
							    <th width="55%"><!-- 终审 -->备注</th>
							  </tr>
							  <s:if test="null eq saveDto.TApprovalInfos || saveDto.TApprovalInfos.size() eq 0">
								 <tr class="td1" class="td1">
									<td colspan="4" style="text-align: left;">找不到相符的内容或信息。</td>
								 </tr>
							  </s:if>
							  <s:else>
							  <s:iterator value="saveDto.TApprovalInfos">
							  <tr>
							    <td><s:property value="crtTime"/></td>
							    <td><s:property value="TUserInfo.name"/></td>
							    <td><s:property value="@com.pemng.serviceSystem.common.utils.PeUtil@getStStr(approvalTp)"/></td>
							    <td>
							    	<s:if test="remark != null && remark.toString().length() > 50">
										<span title="<s:property value="remark"/>" style="color:black">
											<s:property value="remark.toString().substring(0,50)"/>...
										</span>
									</s:if>
									<s:else>
										<s:property value="remark"/>
								    </s:else>
							  </tr>
							 </s:iterator>
							 </s:else>
						</table>
					</div>
				</div>
				</s:if>
			</form>
			<!-- 原鉴定结论及信息创建表单 -->
			<form action="" id="ociForm" name="ociForm">
				<s:hidden name="cmsFlag" id="ociCmsFlag" value="3"/>
			</form>
		</div>
	</div>
	<div class="clear"></div>
	<form action="" id="toPrintCmsForm" name="toPrintCmsForm"  method="post" target="_blank">
		<input type="hidden" name="printCmsId" id="printCmsId" value="<s:property value="saveDto.id"/>" />
		<s:hidden name="printCmsType" id="printCmsType"/>
		<s:hidden name="attachmentType" id="attachmentType"/>
		<s:hidden name="isGenerateCms" id="isGenerateCms"/>
	</form>
<script type="text/javascript">
	//下载附件
	function downLoadAt(atId){
		window.location.href="${pageContext.request.contextPath}/cms/downloadAttachment.action?atId=" + atId + "&hisCmsFlag=H";
		//window.parent.downLoadAt(atId);
	}

	/**
	function iframeResize(iframe) {
		try {
	        var idocumentElement = iframe.contentWindow.document.documentElement;
	        iframe.height = idocumentElement.scrollHeight;
	        if($.browser.msie){//IE浏览器
	        	 //iframe.height -= 5;
			}else{//其他浏览器
				 //iframe.height =- 5; 
			}
	    }
	    catch (e) {
	        window.status = 'Error: ' + e.number + '; ' + e.description;
	    }
	}*/
</script>
</body>	
</html>