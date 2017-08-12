<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>委托书创建</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/cascadeSelect.js"></script>
		<script type="text/javascript">
			
		$(document).ready(function(){
           	$("#NO").addClass("validate[required],maxSize[20]");
           	$("#suspktOfens21").addClass("validate[maxSize[200]]");
           	$("#prcAprislBaseDt").addClass("validate[required]");              	//价格基准日
           	$("#aprislTp").addClass("validate[validate[required]]");         	//鉴定类别
           	$("#cmsUser").addClass("validate[maxSize[20]]");
           	$("#cmsUnitAdd").addClass("validate[maxSize[200]]");
           	$("#cmsUsrTel").addClass("validate[maxUtf8Size[11]]"); //手机
           	$("#cmsCnt").addClass("validate[validate[required]]");
           	$("#artclsDetail").addClass("validate[maxSize[200]]");  
            $("#postcode").addClass("validate[custom[postCode],maxSize[10]]");  
            $("#unitPhon").addClass("validate[maxSize[20]]");
            $("#prcAprislRqrms").addClass("validate[maxSize[500]]");
            $("#cmsCnt").addClass("validate[maxSize[300]]");  
            $("#recAdrs").addClass("validate[maxSize[100]]");
            $("#firstTrialOpinis").addClass("validate[maxSize[500]]");
            $("#retrialOpinis").addClass("validate[maxSize[500]]");
            $("#lastTrialOpinis").addClass("validate[maxSize[500]]");
            $("#cmsUnitNm").addClass("validate[maxSize[100]]");
            $("#aprislTp").addClass("validate[funcCall[checkType]]"); 
            //$("#unitId1").addClass("validate[required]"); 
            //$("#unitId2").addClass("validate[required]"); 
            $("#unitId3").addClass("validate[required]"); 
            <s:if test="cmsFlag eq 2">
            	$("#cmsUnitNm").addClass("validate[required]"); 
            </s:if>
            
            jQuery("#cmsForm").validationEngine();
			//下拉选框联动初始化
			 <s:if test="cmsFlag eq 1 || cmsFlag eq 21">
				initSelectComponentValues();
			</s:if>
			<s:if test="null != saveDto && saveDto.id > 0">
				onload();
			</s:if>
			initCheckValue();
			<s:if test='saveDto!=null && saveDto.unitId1!=null'>
				$('#unitId1').val(${saveDto.unitId1});
				selValueChange(0, SelGroups.objsArray[0], ${saveDto.unitId1});
	 		</s:if>
			<s:if test='saveDto!=null && saveDto.unitId2!=null'>
				selValueChange(1, SelGroups.objsArray[0], ${saveDto.unitId2});
				$('#unitId2').val(${saveDto.unitId2});
				$('#unitId3').val(${saveDto.unitId3});
	 		</s:if>
	 		//委托明细初始化
			afterCreateAprisl();
			//委托书类型
			<s:if test='null != saveDto && saveDto.cmsTp == 1'>
				document.getElementsByName('saveDto.cmsTp')[0].checked=true;
			</s:if>
			<s:elseif test='null != saveDto && saveDto.cmsTp == 2'>
				document.getElementsByName('saveDto.cmsTp')[1].checked=true;
			</s:elseif>
			<s:else>
				//document.getElementsByName('saveDto.cmsTp')[0].checked=true;//默认选中男
			</s:else>
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
			 <s:if test='null != succMsg && succMsg==\"1\"'>
				alert("操作已成功");
			</s:if>
			<s:elseif test='null != succMsg'>
				alert("存在疑似重复的委托书，文案号为:"+"${succMsg}"+"，\n请确认");
			</s:elseif>
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
			document.getElementById("cmsUsr2").value=userId;
		}
		
		function sethiddenUser(cmsUsr){
			document.getElementById("userId2").value=cmsUsr;
		}
		
		function setOrgnlCmsId(orgnlCmsId){
			$("#orgnlCmsId").val(orgnlCmsId);
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
			if(checkValue1=='' || checkValue1 == "0"){
				return "请选择委托单位";
			}else{
				return ;
			}
        }
        
        function checkSort2()  {
        	var checkValue2 = $("#unitId2").val();
			if(checkValue2=='' || checkValue2 == "0"){
				return "请选择委托单位";
			}else{
				return ;
			}
        }
        
         function checkSort3()
        {
        	var checkValue3 = $("#unitId3").val();
			if(checkValue3=='' || checkValue3 == "0"){
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
      	
      	function showMoney(){
      		var aprislMnySum = $("#aprislMnySum").val();
      		var ociAprislMny = $("#ociAprislMny").val();
      		if(undefined == aprislMnySum || undefined == ociAprislMny){
      			return;
      		}
      		var aoc = Math.abs(ociAprislMny - aprislMnySum) / aprislMnySum;
      		if(ociAprislMny != ""){
      			if(aoc <= 0.1){
      				document.getElementById("dis").style.display = "";
      				$("#fous").html('是');
      		}else{
      				document.getElementById("dis").style.display = "";
      				$("#fous").html('否');
      			}
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
				$("#suspktOfens1").addClass("validate[required]");
			    if(jQuery("#suspktOfens1").validationEngine('validate')){//true---空
					return false;
			    }
				//alert("请选择涉嫌罪名");
				//return;
			}
			var suspktOfens = document.getElementById("suspktOfens99");
			if(suspktOfens.checked){
				if(suspktOfens2==""){
					$("#suspktOfens21").addClass("validate[required]");
				    if(jQuery("#suspktOfens21").validationEngine('validate')){//true---空
						return false;
				    }
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
					$("#suspktOfens1").addClass("validate[required]");
				    if(jQuery("#suspktOfens1").validationEngine('validate')){//true---空
						return false;
				    }
					//alert("请选择涉嫌罪名");
					//return;
				}
				var suspktOfens = document.getElementById("suspktOfens99");
				if(suspktOfens.checked){
					if(suspktOfens2==""){
						$("#suspktOfens21").addClass("validate[required]");
					    if(jQuery("#suspktOfens21").validationEngine('validate')){//true---空
							return false;
					    }
					}
				}
				var param=$("#cmsForm").serialize();
				$.ajax({
			       url: "${pageContext.request.contextPath}/cms/saveCms.action?tm="+new Date().getTime(),
					type:"POST",
					data:param,
			        async: false,
					dataType:"json",
			        success: function(data){
			        	window.opener.setOrgnlCmsId(data.id); 
			        	alert("操作已成功");
			        	window.close();
					},error:function(a){alert("数据异常！");}
				});
		}
	   		 
        function toAddOrgnlCms(){
        	var orgnlCmsId =  $("#orgnlCmsId").val();
        	window.open("${pageContext.request.contextPath}/cms/toUpdateCms.action?cmsFlag=21"+(orgnlCmsId != undefined? ("&saveDto.id="+orgnlCmsId):"") + "&tm="+ new Date().getTime(), "toAddOrgnlCms");
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
	   function printCms(printCmsTypeValue , attachmentTypeValue) {
	  		document.getElementById("isGenerateCms").value="false";  
	  		<c:if test="${sessionScope.SESSION_WEBINFO.webUser.username != 'admin'}">
		   		<s:if test="saveDto!=null && saveDto.st!=null && !saveDto.st.matches(\"13|14\")">
					if("-1" != printCmsTypeValue) {
			        	if (confirm("是否重新生成")) {  
			        		document.getElementById("isGenerateCms").value="true";
			        	}  
				 	}
		   		</s:if>
		   	</c:if>
	   		<c:if test="${sessionScope.SESSION_WEBINFO.webUser.username == 'admin'}">
				if("-1" != printCmsTypeValue) {
		        	if (confirm("是否重新生成")) {  
		        		document.getElementById("isGenerateCms").value="true";
		        	}  
			 	}
	 		</c:if>
		   document.getElementById("printCmsType").value=printCmsTypeValue;
		   document.getElementById("attachmentType").value=attachmentTypeValue;
		   document.toPrintCmsForm.action="${pageContext.request.contextPath}/cms/toPrintCms.action?tm="+ new Date().getTime();
		   document.toPrintCmsForm.submit();
	   }
	 
		</script>
	</head>
	<s:if test="cmsFlag eq 2">
		<body onload="showMoney();">	
	</s:if>
	<s:else>
		<body>
	</s:else>
	<h3>
			<s:if test="cmsFlag eq 1 && (null == saveDto || !saveDto.id > 0)">价格鉴定/认定委托书创建</s:if>
			<s:elseif test="cmsFlag eq 2 && (null == saveDto || !saveDto.id > 0)">复核裁定委托书创建</s:elseif>
			<s:elseif test="cmsFlag eq 21">原鉴定结论及信息</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"01\"">待受理委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"02\"">补充材料委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"03\"">已受理委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"04\"">待受理委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"05\"">拒绝受理委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"08\"">初审驳回委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"06\"">终止受理委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"13\" && saveDto.achiv eq \"0\"">待归档委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"13\" && saveDto.achiv eq \"1\"">已归档委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"14\"">待送达委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"08\"">初审驳回委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"10\"">复审驳回委托书</s:elseif>
			<s:elseif test="null != saveDto && saveDto.st eq \"12\"">终审驳回委托书</s:elseif>
			</h3>
		<div class="dkuang">
			<div class="buttomk2">
			<s:if test="cmsFlag != 21 &&  null != saveDto && saveDto.id > 0">
			 	<c:if test="${sessionScope.SESSION_WEBINFO.webUser.username == 'admin'}">
					<input name="button32" type="button" class="anniu" value="保存" buttonAction="accept" onclick="javascript:insertOrUpdate();"/>	<!-- 修改 -->
		 		</c:if>
		 		<c:if test="${sessionScope.SESSION_WEBINFO.webUser.username != 'admin'}">
					<s:if test="saveDto.st.matches('13|14')">
						<input name="button32" type="button" class="anniu" value="保存" buttonAction="accept" onclick="javascript:insertOrUpdate();" style="display: none"/>	<!-- 修改 -->
					</s:if>
					<s:else>
						<input name="button32" type="button" class="anniu" value="保存" buttonAction="accept" onclick="javascript:insertOrUpdate();"/>	<!-- 修改 -->
					</s:else>
		 		</c:if>
			 </s:if>
			<s:if test="cmsFlag != 21 && null != saveDto && saveDto.st.matches(\"01|02|03|04|05|06|08|10|12\")">
			  <input name="button32" type="button" class="anniu" value="补充材料" buttonAction="accept_ad" onclick="javascript:auditCms('02');" <s:if test="!saveDto.st.matches(\"01|03|04\")">style="display: none"</s:if>/>
			  <input name="button32" type="button" class="anniu" value="受理"  buttonAction="accept" onclick="javascript:auditCms('03');" <s:if test="!saveDto.st.matches(\"01|02|04\")">style="display: none"</s:if>/>
			  <input name="button32" type="button" class="anniu" value="提交初审" buttonAction="accept_up" onclick="javascript:auditCms('07');" <s:if test="!saveDto.st.matches(\"03|08|10|12\")">style="display: none"</s:if>/>
			  
			  <input name="button2" type="button" class="anniu" value="拒绝" buttonAction="accept_rf" onclick="javascript:auditCms('05');" <s:if test="saveDto.st != '05'">style="display: none"</s:if>/>
			  <input name="button32" type="button" class="anniu" value="终止受理" buttonAction="accept_stop" onclick="javascript:auditCms('06');" <s:if test="!saveDto.st.matches(\"01|04\")">style="display: none"</s:if>/>
			 </s:if>
			 <s:elseif test="null != saveDto && saveDto.st eq \"13\" && saveDto.achiv eq \"0\"">
			  	<input name="button32" type="button" class="anniu" value="归档" buttonAction="accept_achiv" onclick="javascript:auditCms('99');" <s:if test="!saveDto.st.matches(\"13\")">style="display: none"</s:if>/>
			 </s:elseif>
			 
			<s:elseif test="(cmsFlag eq 1 || cmsFlag eq 2) && (null == saveDto || !saveDto.id > 0)">
			  <input name="button2" type="button" class="anniu" value="保存"  onclick="javascript:insertOrUpdate();"/>
			</s:elseif>
			<s:elseif test="cmsFlag eq 21">
			  <input name="button2" type="button" class="anniu" value="保存"  onclick="javascript:insertOrUpdateOci();"/>
			</s:elseif>
		  	<s:if test="cmsFlag eq 2">
		  	<!-- 复核委托书 按钮一直显示,待受理后该结论信息不能修改，只能查看 -->
		 	 	<input name="button32" type="button" class="anniu" value="原鉴定结论及信息" onclick="javascript:toAddOrgnlCms();"/>
		 	</s:if>
			 <input name="button32" type="button" class="anniu" value="送达"  buttonAction="accept_send" onclick="javascript:auditCms('13');" <s:if test="null == saveDto || null ==saveDto.st || !saveDto.st.matches(\"14\")">style="display: none"</s:if>/>
			  <input name="button3" type="button" class="anniu" value="返回" onclick="javascript:closeBut();"/>
			</div>
			<div class="clear"></div>
			<div id="tagContent">
				<form action="" method="post" id="cmsForm" name="cmsForm">
					<s:if test="cmsFlag eq 2">
						<s:hidden name="saveDto.orgnlCmsId" id="orgnlCmsId"/><!-- 原鉴定结论书id -->
					</s:if>
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
						    	<s:if test="cmsFlag eq 2">复核裁定<s:hidden name="saveDto.cmsTp" value="3"/></s:if>
						    	<s:elseif test="cmsFlag eq 3">
						    		原鉴定结论<s:hidden name="saveDto.cmsTp" value="4"/>
						    	</s:elseif>
						    	<s:else>
	          						<s:radio list="#{'1':'鉴定','2':'认定'}" name="saveDto.cmsTp" id="cmsTp" theme="simple"></s:radio>
						    	</s:else>
						    </td>
						    <th><span style="color:red">*</span>有无实物：</th>
						    <td>
						    	<s:radio list="#{1:'有实物',0:'无实物'}" name="saveDto.isEnty" id="isEnty"></s:radio>
						    </td>
						    <th><span style="color:red">*</span>鉴定文号： </th>
			 				<td>京价<s:textfield cssClass="input1" name="saveDto.prcAprislDocmsNo" cssStyle="width:70px" size="6" id="NO"/>
			 				<s:if test="null != saveDto && saveDto.id > 0">
			 					<s:property value="saveDto.padCode"/>
			 				</s:if>
			 				<s:else>
			 					【<s:property value="currentYear"/>】
			 					<label id="pd"></label>
			 				</s:else>
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
							 <s:if test="cmsFlag eq 2 ">
										<s:select name="saveDto.cmsUnitAreaId" id="cmsUnitAreaId" cssClass="input1" 
										list="@com.pemng.serviceSystem.base.util.WSUtils@getBasicDataList('001')" 
										listValue="value" listKey="id" headerKey="-1" headerValue="请选择">
										</s:select> 
										<s:textfield name="saveDto.cmsUnitNm" size="40" cssClass="input1" id="cmsUnitNm"></s:textfield>
						    </s:if>
							<s:else>
										<select class="input1" name="unitId1" id="unitId1" style="width:120px;">
										 		<option value="0">请选择</option>
										</select>
									 	<select name="unitId2" class="input1" id="unitId2" style="width:120px;">
									   		<option value="0">请选择</option>
									 	</select>
									 	<select name="saveDto.tunitsInfo.id" id="unitId3" class="input1" style="width:160px;">
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
						   <s:if test="cmsFlag eq 2 ">
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
						    <th><span style="color:red"></span>鉴定标的描述：</th>
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
						    <input value="选择相关案件" type="button" class="zinput" onclick="goCase()"/>
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
					  <h5><a href="#" onclick="toCreateAprisl()">添加鉴定物品</a></h5>
					  
					  </div>
					<!--相关附件-->
					<iframe src="${pageContext.request.contextPath}/cms/relateAtList.action?attachment.atTp=1&attachment.TCommission.id=<s:property value='saveDto.id'/>" width="100%" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="relateAtIframe" ></iframe>
					<s:if test="null != saveDto && saveDto.id > 0">
					<div class="box3">
					 <h4>实物勘验</h4>
					  <div align="left">
						  查验人员：  <s:textfield name="saveDto.userNames" id="userId" onclick="goUser()" cssClass="input1" />
						  
						&nbsp; 查验日期： <input type="text" name="saveDto.recDt" id="recDt"
								value="<s:date name="saveDto.recDt" format="yyyy-MM-dd"/>"
								readonly="readonly" class="input1"/>
										<img onclick="WdatePicker({el:'recDt',readOnly:true});" src="${pageContext.request.contextPath}/images/ril.png"
											width="20px" height="18px" border="0" style="margin-bottom:-5px;margin-left:-25px;"/>
						&nbsp;查验地点：<s:textfield type="text" name="saveDto.recAdrs" id="recAdrs" cssClass="input1" />
						<input type="hidden" id="cmsUsr" name="saveDto.recUsrIds" >
						<input type="hidden" id="userId2">
						<input type="hidden" id="cmsUsr2" >
					  </div>
					  <br/>
					  
					  	<!--实物勘验附件-->
						<iframe src="${pageContext.request.contextPath}/cms/artclsRecList.action?attachment.atTp=3&attachment.TCommission.id=<s:property value='saveDto.id'/>" width="100%" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="artclsRecIframe" ></iframe>
					
					  <div align="center">
					  
					  	<input name="button2" type="button" class="anniu" value="打印委托书" onclick="javascript:printCms('1' , '0')" 
					  	<s:if test="saveDto==null || saveDto.st==null || saveDto.st.matches(\"01|02|04|05|06\")">
							<c:if test="${sessionScope.SESSION_WEBINFO.webUser.username != 'admin'}">
							  	style="display: none"
					 		</c:if>
					  	</s:if>/>
					  	<input name="button2" type="button" class="anniu" value="取件通知书" onclick="javascript:printCms('2' , '0')" 
					  	<s:if test="saveDto==null || saveDto.st==null || saveDto.st.matches(\"01|02|04|05|06\")"><c:if test="${sessionScope.SESSION_WEBINFO.webUser.username != 'admin'}">
							  	style="display: none"
					 		</c:if></s:if>/>
					  	<input name="button2" type="button" class="anniu" value="生成送达回证" onclick="javascript:printCms('3' , '0')" 
					  	<s:if test="saveDto==null || saveDto.st==null || !saveDto.st.matches(\"14\")"><c:if test="${sessionScope.SESSION_WEBINFO.webUser.username != 'admin'}">
							  	style="display: none"
					 		</c:if></s:if>/>
					  	<input name="button2" type="button" class="anniu" value="生成鉴定结论" onclick="javascript:printCms('4' , '0')" 
					  		<s:if test="saveDto==null || saveDto.gnlSendBkDt==null"><c:if test="${sessionScope.SESSION_WEBINFO.webUser.username != 'admin'}">
							  	style="display: none"
					 		</c:if></s:if>/>
					  </div>
					</div>
					<!--技术报告-->
					<iframe src="${pageContext.request.contextPath}/cms/techReportList.action?attachment.atTp=2&attachment.TCommission.id=<s:property value='saveDto.id'/>" width="100%" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="techReportIframe" ></iframe>
					<!--技术报告待删除-->
					<div class="box2"> 
						<h4>审批信息</h4>
						<table border="0" cellSpacing="0" cellPadding="0">
							<s:if test="saveDto.st.matches(\"07|08\")">
							  <tr>
							    <th><!-- 初审 -->审批意见：</th>
							    <td><s:textarea cssClass="kktextarea1" name="saveDto.firstTrialOpinis" id="firstTrialOpinis"></s:textarea></td>
							  </tr>
							 </s:if>
							 <s:elseif test="saveDto.st.matches(\"09|10\")">
								  <tr>
								    <th><!-- 复审 -->审批意见：</th>
								    <td><s:textarea cssClass="kktextarea1" name="saveDto.retrialOpinis" id="retrialOpinis"></s:textarea></td>
								  </tr>
							 </s:elseif>
							 <s:elseif test="saveDto.st.matches(\"11|12|13\")">
							  <tr>
							    <th><!-- 终审 -->审批意见：</th>
							    <td><s:textarea cssClass="kktextarea1" name="saveDto.lastTrialOpinis" id="lastTrialOpinis"></s:textarea></td>
							  </tr>
							 </s:elseif>
						</table>
					</div>
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
			<form action="" id="toPrintCmsForm" name="toPrintCmsForm"  method="post" target="_blank">
				<input type="hidden" name="printCmsId" id="printCmsId" value="<s:property value="saveDto.id"/>" />
				<s:hidden name="printCmsType" id="printCmsType"/>
				<s:hidden name="attachmentType" id="attachmentType"/>
				<s:hidden name="isGenerateCms" id="isGenerateCms"/>
			</form>
			<form action="" id="auditForm" name="auditForm" method="post">
				<s:hidden name="cmsFlag" id="cmsFlag_"/>
				<s:hidden name="saveDto.id" id="cmsId"/>
				<s:hidden name="saveDto.st" id="st"/>
				<s:hidden name="saveDto.achiv" id="achiv"/>
			</form>
			
		</div>
	</div>
	<div class="clear"></div>
<script type="text/javascript">
	//关闭按钮 
   	function closeBut(){ 
		<s:if test="null != saveDto && (saveDto.achiv eq \"1\" || saveDto.st eq \"06\")">
			//历史委托书
			document.cmsForm.action="${pageContext.request.contextPath}/cms/hisCmsList.action";
			document.cmsForm.submit();
		</s:if>
   		<s:elseif test="cmsFlag eq 1">
   			//鉴定或认定委托书
   	 		document.cmsForm.action="${pageContext.request.contextPath}/cms/aprislCertList.action";
   	 		document.cmsForm.submit();
   		</s:elseif>
		<s:elseif test="cmsFlag eq 2">
			//复核裁定委托书
			document.cmsForm.action="${pageContext.request.contextPath}/cms/reviewRuleList.action";
			document.cmsForm.submit();
		</s:elseif>
		<s:elseif test="cmsFlag eq 21">
			//原鉴定结论书
			window.close();
		</s:elseif>
  	 }
	
	//2,3,5,6
	function auditCms(st){
		if (st == '99') {
			$("#achiv").val("1");
		}else{
			$("#st").val(st);
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