<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>委托书审批</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
            $("#artclsDetail").addClass("validate[maxSize[200]]");  
            $("#recAdrs").addClass("validate[maxSize[100]]");
            $("#firstTrialOpinis").addClass("validate[maxSize[500]]");
            $("#retrialOpinis").addClass("validate[maxSize[500]]");
            $("#lastTrialOpinis").addClass("validate[maxSize[500]]");
            $("#aprislTp").addClass("validate[funcCall[checkType]]");           //鉴定类别
            $("#unitId1").addClass("validate[funcCall[checkSort1]]"); 
            $("#unitId2").addClass("validate[funcCall[checkSort2]]"); 
            $("#unitId2").addClass("validate[funcCall[checkSort3]]"); 
            jQuery("#cmsForm").validationEngine();
            onload();
			//下拉选框联动初始化
			initCheckValue();
			<s:if test='saveDto.cmsTp != 3'>
				initSelectComponentValues();
			</s:if>
			<s:if test='saveDto.cmsTp != 3 && saveDto!=null && saveDto.unitId1!=null'>
				$('#unitId1').val(${saveDto.unitId1});
				selValueChange(0, SelGroups.objsArray[0], ${saveDto.unitId1});
	 		</s:if>
			<s:if test='saveDto.cmsTp != 3 && saveDto!=null && saveDto.unitId2!=null'>
				selValueChange(1, SelGroups.objsArray[0], ${saveDto.unitId2});
				$('#unitId2').val(${saveDto.unitId2});
				$('#unitId3').val(${saveDto.unitId3});
	 		</s:if>
	 		$('#_athrzUser').val(${saveDto.athrzUser});//授权
	 		//委托明细初始化
			afterCreateAprisl();
			
		});
	
	
		function onload() 	{
	   		var pad = document.getElementById("pad").value;
	   		var pad1 = pad.substring(6,12);
	   		$("#pd").html(pad1);
	   		
	   		var string = document.getElementById("NO").value;
	   		if(string.indexOf("京")==-1){
	   			return;
	   		}
			else{
				var str1 = '[';
				if(string.indexOf("【") >= 0){
					str1 = '【';
				}				
    			var str_before = string.split(str1)[0];
				var str2 = '价';
   				var str_after = str_before.split(str2)[1];
   				document.getElementById("NO").value = str_after;
			}
	   	}
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
		
		function checkType() {
        	var checkValue = $("#aprislTp").val();
			if(checkValue == "0"){
				return "请选择鉴定类别";
			}else{
				return ;
			}
        }
        function checkSort1()  {
        	var checkValue1 = $("#unitId1").val();	
			if(checkValue1 == "0"){
				return "请选择委托单位";
			}else{
				return ;
			}
        }
        
        function checkSort2()  {
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
      	
      	function checkEnty() {
      		if($(":input[name='saveDto.isEnty'][checked]").val() == undefined) {
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
		 
		//原鉴定结论信息保存或修改
		function insertOrUpdateOci(){
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
        	 window.open("${pageContext.request.contextPath}/cms/toCmsAdd.action?cmsFlag=3&tm="+ new Date().getTime(), "toAddOrgnlCms");
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
	   			$(":input[name='saveDto.suspktOfens1']").attr("disabled","disabled"); 
	   		}else{
	   			document.getElementById("so").style.display ="none";
	   			$(":input[name='saveDto.suspktOfens1']").removeAttr("disabled"); 
	   			$("#suspktOfens21").val("");
	   		}
	   }
	   
	 //打印委托书
	   //printCmsTypeValue 打印的类型
	   function printCms(printCmsTypeValue) {
			document.getElementById("isGenerateCms").value="false";  
	   		<s:if test="saveDto!=null && saveDto.st!=null && !saveDto.st.matches(\"13|14\")">
				if("-1" != printCmsTypeValue) {
		        	if (confirm("是否重新生成")) {  
		        		document.getElementById("isGenerateCms").value="true";
		        	}  
			 	}
	   		</s:if>
		   document.getElementById("printCmsType").value=printCmsTypeValue;
		   document.toPrintCmsForm.action="${pageContext.request.contextPath}/cms/toPrintCms.action?tm="+ new Date().getTime();
		   document.toPrintCmsForm.submit();
	   }
	 
	 	
		</script>
	</head>
	<s:if test=""></s:if>
	<body>
			<h3>
				<s:if test="null != saveDto && saveDto.st eq \"07\"">待初审委托书</s:if>
				<s:elseif test="null != saveDto && saveDto.st eq \"08\"">初审驳回委托书</s:elseif>
				<s:elseif test="null != saveDto && saveDto.st eq \"09\"">待复审委托书</s:elseif>
				<s:elseif test="null != saveDto && saveDto.st eq \"10\"">复审驳回委托书</s:elseif>
				<s:elseif test="null != saveDto && saveDto.st eq \"11\"">待终审委托书</s:elseif>
				<s:elseif test="null != saveDto && saveDto.st eq \"12\"">终审驳回委托书</s:elseif>
				<s:elseif test="null != saveDto && saveDto.st eq \"14\"">待送达委托书</s:elseif>
			</h3>
			<div class="dkuang">
			<div class="buttomk2" >
			<s:if test="null != saveDto && saveDto.st.matches(\"07|09|11\")">
				<c:if test="${sessionScope.SESSION_WEBINFO.webUser.id == saveDto.athrzUser}">
			  	 	<input name="button32" type="button" class="anniu" value="终审驳回" buttonAction="retrial_rf" onclick="javascript:auditCms('12');"/>
					<input name="button32" type="button" class="anniu" value="审批通过" buttonAction="retrial_up" onclick="javascript:auditCms('14');"/>
		 		</c:if>
		 	</s:if>
			 <s:if test="null != saveDto && saveDto.st.matches(\"07|10|12\")">
			  	<input name="button32" type="button" class="anniu" value="初审驳回" buttonAction="first_rf"  onclick="javascript:auditCms('08');"/>
			  	<input name="button32" type="button" class="anniu" value="提交复审" buttonAction="first_up" onclick="javascript:auditCms('09');"/>
			 </s:if>
			<s:elseif test="null != saveDto && saveDto.st.matches(\"09\")">
			 	<input name="button32" type="button" class="anniu" value="复审驳回" buttonAction="retrial_rf" onclick="javascript:auditCms('10');"/>
			  	<input name="button32" type="button" class="anniu" value="提交终审" buttonAction="retrial_up" onclick="javascript:auditCms('11');"/>
			</s:elseif>
			<s:if test="null != saveDto && saveDto.st.matches(\"07|09|11\")">
			 	<input name="button32" type="button" class="anniu" value="终审驳回" buttonAction="last_rf" onclick="javascript:auditCms('12');"/>
			  	<input name="button32" type="button" class="anniu" value="审批通过" buttonAction="last_up" onclick="javascript:auditCms('14');"/>
				<s:if test="null != dataList && dataList.size() > 0">
					<input type="button" name="athrzUserCng" class="anniu" value="授权审批" buttonAction="last_up" onClick="javascript:dispDiv(this.offsetLeft,this.offsetTop);">
					<s:select id="_athrzUser" name="_athrzUser" list="dataList" listKey="id" listValue="name" headerKey="-1" headerValue="请选择" buttonAction="last_up" cssStyle="display:none"></s:select>
					<input name="button32" type="button" id="_athrzUserCfm" class="anniu2" value="确认" buttonAction="last_up" onclick="javascript:doAthrzUser();" style="display:none"/>
				</s:if>
			</s:if>
			  <input name="button3" type="button" class="anniu" value="返回" onclick="javascript:closeBut();"/>
			</div>
			<div class="clear"></div>
			<div id="tagContent">
				<form action="" method="post" id="cmsForm" name="cmsForm">
					<s:hidden name="cmsFlag" id="cmsFlag"/>
					<s:hidden name="saveDto.suspktOfens" id="suspktOfens"/>
					<s:if test="null != saveDto && saveDto.id > 0">
						<s:hidden name="saveDto.id" id="cmsId"/>
					</s:if>
					<s:else>
						<input type="hidden" name="saveDto.tmpCmsId" id="unqCd" value="<s:property value='unqCd'/>"/>
						<input type="hidden" name="saveDto.st" value="01"/> 
					</s:else>
					<div class="tabnr">
					<div class="box2"> 
						<h4>基本信息</h4>
						<table>
						  <tr>
						    <th><span style="color:red">*</span>委托书类型：</th>
						    <td>
						    	<s:if test="saveDto.cmsTp eq 3">复核裁定<s:hidden name="saveDto.cmsTp" value="3"/></s:if>
						    	<s:elseif test="cmsTp eq 4">
						    		原鉴定结论<s:hidden name="saveDto.cmsTp" value="4"/>
						    	</s:elseif>
						    	<s:else>
	          						<s:radio list="#{'1':'鉴定','2':'认定'}" name="saveDto.cmsTp" id="cmsTp" theme="simple"></s:radio>
							    	<span style="color:red">*</span>
						    	</s:else>
						    </td>
						    <th><span style="color:red">*</span>有无实物：</th>
						    <td>
						    	<s:radio list="#{1:'有实物',0:'无实物'}" name="saveDto.isEnty" id="isEnty"></s:radio>					    	
						    </td>
						    <th><span style="color:red">*</span> 鉴定文号： </th>
			 				<td>京价<s:textfield cssClass="input1" name="saveDto.prcAprislDocmsNo" cssStyle="width:70px" size="6" id="NO"/>【<%
						    	Date currentTime = new Date();
						    	int year = currentTime.getYear()+1900;                                                        
						    	out.print(year); %>】
						    	<label id="pd"></label>
								<s:hidden name="saveDto.padCode" id="pad"/>号
						   		</td>
						   		
						   </tr>
						  <tr>
						    <th><span style="color:red">*</span>涉嫌罪名：</th>
						    <td colspan="5">
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
								    		</span>
								    </s:if>
								    <s:else>
								       <input type="checkbox" id="suspktOfens99" value="99" onclick="showIn();" name="ck" />2:其他案件
								    		<span id="so" style="display: none">
								    		<s:textfield name="saveDto.suspktOfens2" id="suspktOfens21" cssClass="input1"></s:textfield>
								    		</span>
								    </s:else>
						    	</td>
						    </tr>
						    <tr>
						    <th><span style="color:red">*</span>委托单位：</th>
							<td colspan="3">
								<s:if test="saveDto.cmsTp == 3">
									<s:select name="saveDto.cmsUnitAreaId" cssClass="input1" 
										list="@com.pemng.serviceSystem.base.util.WSUtils@getBasicDataList('001')" 
										listValue="value" listKey="id"  headerKey="-1" headerValue="请选择">
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
									 	</select>
								</s:else>
							</td>
							<th>委托人:</th>
							<td>
							<s:textfield name="saveDto.cmsUsr"  cssClass="input1" id="cmsUser"></s:textfield>
							</td>
						    </tr>
						  <tr>
						    <th>委托单位地址：</th>
						    <td><s:textfield name="saveDto.cmsUnitAdd"  cssClass="input1" id="cmsUnitAdd"></s:textfield></td>
						    <th>邮编：</th>
						    <td><s:textfield name="saveDto.postcode" id="postcode" cssClass="input1"></s:textfield></td>
						    <th>单位电话：</th>
						    <td><s:textfield name="saveDto.unitPhon"  cssClass="input1" id="unitPhon"></s:textfield></td>
						    
						    </tr>
						  <tr>
						    <th>价格鉴定要求：</th>
						    <td><s:textfield name="saveDto.prcAprislRqrms"  cssClass="input1" id="prcAprislRqrms"></s:textfield></td>
						    <th><span style="color: red">*</span>价格鉴定基准日：</th>
						    <td>	
						     <input type="text" name="saveDto.prcAprislBaseDt" id="prcAprislBaseDt"
								value="<s:date name="saveDto.prcAprislBaseDt" format="yyyy-MM-dd"/>"
								readonly="readonly" class="input1"/>
							<img onclick="WdatePicker({el:'prcAprislBaseDt',readOnly:true});"
								src="${pageContext.request.contextPath}/images/ril.png" width="20px"
								height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"/>
							</td>
						    <th><span style="color:red">*</span>鉴定类别：</th>
						    <td><s:select list="#{'0':'请选择','11':'刑事','13':'行政执法','12':'纪检监察','14':'其他'}" name="saveDto.aprislTp" id="aprislTp" cssClass="input1"></s:select></td>
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
						    <td colspan="5">
						    <s:textfield name="saveDto.cmsUsrTel"  cssClass="input1" id="cmsUsrTel"/>（用于接收领取“价格鉴定结论书”的通知短信）
						    </td>
						    </tr>
						  <tr>
						    <th><span style="color:red"></span>鉴定标的描述：：</th>
						    <td colspan="5">
						    	<s:textarea  name="saveDto.artclsDetail" id="artclsDetail" cols="100" rows="4"></s:textarea>
						    </td>
						  </tr>
						  <tr>
						    <th><span style="color:red">*</span>委托内容：</th>
						    <td colspan="5">
						    	<s:textarea  name="saveDto.cmsCnt" id="cmsCnt" cols="100" rows="5"></s:textarea>
						    </td>
						  </tr>
						  <tr>
						    <th>历史相关案件：</th>
						    <td colspan="5">
						     <label id="cmsCnt21"><s:property value="saveDto.TCommission.cmsCnt"/></label>
						    <input type="hidden" id="hisCmsId" name="saveDto.TCommission.id">
						    <input type="hidden" id="hisCmsCnt" name="saveDto.TCommission.cmsCnt">
						    <input value="选择相关案件" type="button" class="zinput" onclick="goCase()" buttonAction="accept"/>
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
					  	<jsp:include page="/jsp/cms/cms_detail.jsp"></jsp:include>  
					  </div>
					   <!--<div align="center">
					  	<input name="button2" type="button" class="anniu" value="添加鉴定物品" id="upCmsDetail" onclick="toCreateAprisl()"/>
					  </div>-->
					  <h5  buttonAction="accept"><a href="#" onclick="toCreateAprisl()">添加鉴定物品</a></h5>
					
					</div>
					<!--相关附件-->
					<iframe src="${pageContext.request.contextPath}/cms/relateAtList.action?attachment.atTp=1&attachment.TCommission.id=<s:property value='saveDto.id'/>" width="100%" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="relateAtIframe" ></iframe>
					<s:if test="null != saveDto && saveDto.id > 0">
					<div class="box3">
					  <h4>实物勘验</h4>
					  <div align="left">
						  查验人员：  <s:textfield name="saveDto.userNames"  id="userId" onclick="goUser()" cssClass="input1" />
						  
						&nbsp; 查验日期： <input type="text" name="saveDto.recDt" id="recDt"
								value="<s:date name="saveDto.recDt" format="yyyy-MM-dd"/>"
								readonly="readonly" class="input1"/>
										<img onclick="WdatePicker({el:'recDt',readOnly:true});" src="${pageContext.request.contextPath}/images/ril.png"
											width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"/>
						&nbsp;查验地点：<s:textfield type="text" name="saveDto.recAdrs" id="recAdrs" cssClass="input1" />
						<input type="hidden" id="cmsUsr" name="saveDto.recUsrIds" >
					  </div>
					  <br/>
					  
					  	<!--实物勘验附件-->
						<iframe src="${pageContext.request.contextPath}/cms/artclsRecList.action?attachment.atTp=3&attachment.TCommission.id=<s:property value='saveDto.id'/>" width="100%" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="artclsRecIframe" ></iframe>
					
					  <div align="center">
					  	<input name="button2" type="button" class="anniu" value="打印委托书" onclick="javascript:printCms('1')" />
					  	<input name="button2" type="button" class="anniu" value="取件通知书" onclick="javascript:printCms('2')"  />
					  	<input name="button2" type="button" class="anniu" value="生成送达回证" onclick="javascript:printCms('3')" />
					  	<input name="button2" type="button" class="anniu" value="生成鉴定结论" onclick="javascript:printCms('4')" />
					  </div>
					</div>
					<!--技术报告-->
					<iframe src="${pageContext.request.contextPath}/cms/techReportList.action?attachment.atTp=2&attachment.TCommission.id=<s:property value='saveDto.id'/>" width="100%" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="techReportIframe" ></iframe>
					<!--技术报告待删除-->
					<div class="box2"> 
						<h4>审批信息</h4>
						<table border="0" cellSpacing="0" cellPadding="0">
							<s:if test="saveDto.st eq '07' || saveDto.st eq '08'">
							  <tr>
							    <th><!-- 初审 -->审批意见：</th>
							    <s:hidden name="saveDto.auditFlag" id="auditFlag" value="1"/>
							    <td><s:textarea name="saveDto.firstTrialOpinis" id="firstTrialOpinis" cols="100" rows="5"></s:textarea></td>
							  </tr>
							 </s:if>
							 <s:elseif test="saveDto.st eq '09' || saveDto.st eq '10'">
							  <s:hidden name="saveDto.auditFlag" id="auditFlag" value="2"/>
								  <tr>
								    <th><!-- 复审 -->审批意见：</th>
								    <td><s:textarea name="saveDto.retrialOpinis" id="retrialOpinis" cols="100" rows="5"></s:textarea></td>
								  </tr>
							 </s:elseif>
							 <s:elseif test="saveDto.st eq '11' || saveDto.st eq '12' || saveDto.st eq '13'">
							  <s:hidden name="saveDto.auditFlag" id="auditFlag" value="3"/>
							  <tr>
							    <th><!-- 终审 -->审批意见：</th>
							    <td><s:textarea name="saveDto.lastTrialOpinis" id="lastTrialOpinis" cols="100" rows="5"></s:textarea></td>
							  </tr>
							 </s:elseif>
						</table>
					</div>
					<div class="box3">
					 	<h4>审批记录</h4>
						<table  border="0" cellSpacing="0" cellPadding="0">
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
							    <td>
							    	<s:property value="@com.pemng.serviceSystem.common.utils.PeUtil@getStStr(approvalTp)"/>
							    </td>
							    <td>
							    	<s:if test="remark != null && remark.toString().length() > 50">
										<span title="<s:property value="remark"/>" style="color:black">
											<s:property value="remark.toString().substring(0,50)"/>...
										</span>
									</s:if>
									<s:else>
										<s:property value="remark"/>
								    </s:else>
							    </td>
							  </tr>
							 </s:iterator>
							 </s:else>
						</table>
					</div>
				</div>
				</s:if>
			</form>
			<!-- 原鉴定结论及信息创建表单 -->
			<form action="" id="ociForm" name="ociForm" method="post">
				<s:hidden name="cmsFlag" id="ociCmsFlag" value="21"/>
			</form>
			<form action="" id="toPrintCmsForm" name="toPrintCmsForm"  method="post" >
				<input type="hidden" name="printCmsId" id="printCmsId" value="<s:property value="saveDto.id"/>" />
				<s:hidden name="printCmsType" id="printCmsType"/>
				<s:hidden name="isGenerateCms" id="isGenerateCms"/>
			</form>
			<form action="" id="auditForm" name="auditForm" method="post">
				<s:hidden name="cmsFlag" id="cmsFlag"/>
				<s:hidden name="saveDto.id" id="cmsId"/>
				<s:hidden name="saveDto.st" id="st"/>
				<s:hidden name="saveDto.achiv" id="achiv"/>
				<s:hidden name="saveDto.auditFlag" id="auditFlag_"/>
				<s:hidden name="saveDto.firstTrialOpinis" id="firstTrialOpinis_" />
				<s:hidden name="saveDto.retrialOpinis" id="retrialOpinis_" />
				<s:hidden name="saveDto.lastTrialOpinis" id="lastTrialOpinis_" />
			</form>
			<form action="" id="athrzUserForm" name="athrzUserForm" method="post">
				<s:hidden name="saveDto.st" id="athrzSt" value="-99"/>
				<s:hidden name="saveDto.id" id="athrzCmsId"/>
				<s:hidden name="saveDto.athrzUser" id="athrzUser"/>
			</form>
			
		</div>
	</div>
	<div class="clear"></div>

	<script type="text/javascript">
	//关闭按钮 
   	function closeBut(){ 
		//鉴定或认定委托书
	 	document.cmsForm.action="${pageContext.request.contextPath}/cms/auditList.action";
	 	document.cmsForm.submit();
  	 }
	
	//2,3,5,6
	function auditCms(st){
		if (st == '99') {
			$("#achiv").val("1");
		}else{
			$("#st").val(st);
		}
		var auditValue = $("#auditFlag").val();
		$("#auditFlag_").val(auditValue);
		if(auditValue!= undefined && auditValue == 1){
			$("#firstTrialOpinis_").val($("#firstTrialOpinis").val());
		}else if(auditValue!= undefined && auditValue == 2){
			$("#retrialOpinis_").val($("#retrialOpinis").val());
		}else if(auditValue!= undefined && auditValue == 3){
			$("#lastTrialOpinis_").val($("#lastTrialOpinis").val());
		}
		var param=$("#auditForm").serialize();
		$.ajax({
	       url: "${pageContext.request.contextPath}/cms/auditCms.action?tm="+new Date().getTime(),
			type:"POST",
			data:param,
	        async: false,
			dataType:"json",
	        success: function(result){
	        	if(result.message =="0"){
					 alert("操作失败");
				}else{
					alert("操作已成功");
					closeBut();
				}
			},error:function(a){alert("数据异常！");}
		});
	};
	
	function doAthrzUser(){
		$("#athrzUser").val($("#_athrzUser").val());
		$("#_athrzUserCfm").hide();
		$("#_athrzUser").hide();
		if($("#_athrzUser").val() == -1){
			alert("请选择复审人");
			return;
		}
		var param=$("#athrzUserForm").serialize();
		$.ajax({
	       url: "${pageContext.request.contextPath}/cms/auditCms.action?tm="+new Date().getTime(),
			type:"POST",
			data:param,
	        async: false,
			dataType:"json",
	        success: function(result){
	        	if(result.message =="0"){
					 alert("操作失败");
				}else{
					alert("操作已成功");
					closeBut();
				}
			},error:function(a){alert("数据异常！");}
		});
	}
	
	function dispDiv(PositionLeft,PositionTop){
		$("#_athrzUser").attr("style", "display:''");
		$("#_athrzUserCfm").attr("style", "display:''");
		//document.getElementById("athrzUserDiv").style.display="block";
		//document.getElementById("athrzUserDiv").style.left=800;
		//document.getElementById("athrzUserDiv").style.top=800;
	}
</script>
</body>	
</html>