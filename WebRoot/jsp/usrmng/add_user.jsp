<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script src='${pageContext.request.contextPath}/js/jquery.multiselects.packed.js'
			type='text/javascript'></script> 
		<script language="javascript" type="text/javascript">
		jQuery(document).ready(function(){
		 	$("#username").addClass("validate[required],maxUtf8Size[20]");
		 	$("#name").addClass("validate[required],maxUtf8Size[20] ");
		 	"<s:if test="saveDto!=null && saveDto.id!=null">"
		 		$("#password").addClass("validate[minSize[8],maxSize[18],custom[checkpass]]");
		 	"</s:if>"
		 	"<s:if test="saveDto==null || saveDto.id==null">"
		 		$("#password").addClass("validate[required],validate[minSize[8],maxSize[18],custom[checkpass]]");
		 	"</s:if>"
		 	$("#tel").addClass("validate[custom[simplePhone]]");//电话 
		 	$("#status").addClass("validate[funcCall[checkStatus]]");//状态
		    jQuery("#userForm").validationEngine();

		    $("#select_left").attr("multiple","multiple");
			$("#select_right").attr("multiple","multiple");
	
		}); 
		
		 function checkStatus(){
			var checkValue = $("#status").val();
			if(checkValue == "-1"){
				return "请选择状态";
			}else{
				return ;
			}
		}
		 
	    $(function() { 
	         $(":button[id^=allLeft]").click(function(){
	             var toAdds; 
	             if($(this).attr("id") == "allLeft"){ //添加全部   
	                toAdds = $("#select_right option");   
	             }else{    
	                toAdds = $("#select_right option:selected");
	             }   
	            toAdds.each(function(){   
	                $("#select_left").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option");   
	                $(this).remove();     
	            });   
	        });
	        
	         $(":button[id^=allRight]").click(function(){
	             var toAdds;
	             if($(this).attr("id") == "allRight"){ //添加全部   
	                toAdds = $("#select_left option");   
	             }else{ //添加选中   
	                toAdds = $("#select_left option:selected");
	             }   
	            toAdds.each(function(){   
	                $("#select_right").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option");   
	                $(this).remove();     
	            });   
	        });
				 
	        $('#options_left').click(function(){
	           var todeletes = $("#select_right option:selected"); 
	           if(todeletes.length == 0){   
	                   alert("请选择要删除的角色！");   
	                 return;   
	            }
	            todeletes.each(function(){  
	             $("#select_left").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");     
	             $(this).remove();     
	            }) ;
	         });
	         
	          $('#options_right').click(function(){
	           var todeletes = $("#select_left option:selected"); 
	           if(todeletes.length == 0){   
	                   alert("请选择要增加的角色！");   
	                 return;   
	            }
	            todeletes.each(function(){  
	             $("#select_right").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");     
	             $(this).remove();     
	            });
	         });
	    }); 
	     
	   function PSclear(){
	   		$("#password").val("");
	   }
	   
	   var flag = false;
	   function inserOrUpdate(){
		   if(flag) {
			   return;
		   }
		    if (!jQuery("#userForm").validationEngine('validate')) {return;}
		         username= $("#username").val();
		         user_id= $("#userId").val(); 
		         //option 的值 
		        $("#select_right option").each(function(){
					$("#userForm").append("<input type=\"hidden\" name=\"saveDto.rightValues\" value=\""+$(this).val()+"\"/>");   
				}); 
	              $.ajax({
			        type: "POST",
					url:"${pageContext.request.contextPath}/usrmng/onlyUser.action",
					cache:false,
					data:{
					   "userDto.username":username,
					   "userDto.id":user_id
					},
					success: function(msg){
						if(msg == '1'){
							alert("已存在该用户名");
							return false;
						}else{
							/*
				      		var userId=$("#userId").val();
				         	// 以输入框是否有值为判断跳转条件
				         	if(userId == '' || userId == null ){
					          		var selectRole=$("#select_right").find("option").length;
							  		var splitValue="";
								 if(selectRole>0) {
								
								 }else{
								 	alert('请选择角色 ');
								 	return ;
								 }
					         }else{ 
								 var splitValue="";
								 var selectRole=document.getElementById("select_right").value;
								 if(selectRole==null){
									alert('请增加角色');
									return;
								 }
					        } */
				            document.userForm.action="${pageContext.request.contextPath}/usrmng/saveUser.action";
				            document.userForm.submit();
				            flag = true;
						}
				 } 
			});
	   } 

		//判断用户名是否重复
		function checkUser(){
			var param=$("#userForm").serialize();
			$.ajax({
		       url: "${pageContext.request.contextPath}/usrmng/onlyUser.action?tm="+new Date().getTime(),
				type:"POST",
				data:param,
		        async: false,
				dataType:"json",
		        success: function(data){
		        	if(data == 1){
		        		$("#username").addClass("validate[funcCall[returnValidate]]");
		        	}else{
		        		$("#username").removeClass("validate[funcCall[returnValidate]]");
		        	};
				},error:function(a){alert("数据异常！");}
			});
		}
		
		function returnValidate(){
			return "已存在该用户名!";
		}
	   
	 	//关闭按钮 
	   function closeBut(){ 
	        document.userForm.action="${pageContext.request.contextPath}/usrmng/userList.action";
	        document.userForm.submit();
	   }
	</script>
</head>
<s:if test="saveDto!=null && saveDto.id!=null">
		<h3>修改用户</h3>
	</s:if>
	<s:else>
		<h3>创建用户</h3>
	</s:else>
<div class="dkuang">
	<form action="" method="post" id="userForm" name="userForm">
			<div class="buttomk2">
			 <s:if test="saveDto!=null && saveDto.id!=null">
				<input  type="button" value="提交" class="anniu" onclick="inserOrUpdate();" />
			</s:if>
			<s:else>
				<input type="button" value="提交" class="anniu" onclick="inserOrUpdate();" />
			</s:else>	
				<input type="button" value="返回" id="close" class="anniu" onclick="closeBut();" />
			</div>

			<input type="hidden" id="leaderId" name="saveDto.leaderId" value="<s:property value="userDto.leaderId"/>" />
			<s:hidden id="userId" name="saveDto.id"/>
			<div class="box2"> 
			<h4>基本信息</h4>
			<table>
				 <tr>
					<th><span style="color:red">*</span>真实姓名: </th>
					<td>
					<s:textfield name="saveDto.name" cssClass="input1" id="name"/> 
					</td> 
					<th><span style="color:red">*</span>登陆账号:</th>
					<td>
						<s:textfield name="saveDto.username" id="username" cssClass="input1" onblur="javascript:checkUser();"></s:textfield> 
					</td>
					<th><span style="color:red">*</span>用户密码:</th>
					<td>
						<input type="password" name="saveDto.password" id="password" class="input1" onclick="PSclear();"/>
					<s:if test="saveDto==null || saveDto.id==null"></s:if>
					</td>  
				</tr>
				<tr> 
				    <th>电话号码 :</th>
					<td>
					<s:textfield cssClass="input1" name="saveDto.tel" id="tel"/> 
					</td>
					 <th><span style="color:red">*</span>账号状态:</th>
					<td><s:select id="status" name="saveDto.status" list="#{'1':'正常','0':'禁用'}" cssClass="input1" /></td>
					<s:if test="saveDto!=null && saveDto.id!=null">
						<th>用户登陆次数:</th>
						<td><s:property value="saveDto.loginSum"/></td>
					</s:if>
				</tr>
				<s:if test="saveDto!=null && saveDto.id!=null">
					<tr>
						<th>创建时间 :</th>
						<td><s:date name="saveDto.crtTime" format="yyyy-MM-dd HH:mm"/></td>
						<th>创建人 :</th>
						<td><s:property value="saveDto.crtName"/></td>
					</tr>
				</s:if>
				<tr style="height: 20px;"></tr>
				<tr>
					<td style="width: 250px">
						<p>
							<b>未分配角色:</b>
						</p>
						<br>
						<p>
							<s:select list="roleUncheckList" size="6" listValue="name" listKey="id" 
							id="select_left" name="saveDto.leftValues" cssStyle="width:250px;height:200px; background:url(images/input.gif) repeat-x left top; border:#A4A4A4 solid 1px"></s:select>
						</p>
					</td>
					<td style="text-align: center; width: 100px"> 
						<input type="button" value="&lt;&lt;" id="allLeft" name="B3" class="fangniu" />
						<p align="center">
							<input type="button" value="&lt; " name="B5"  id="options_left" class="fangniu" />
							</p>
							<p align="center"><input type="button" value="&gt; " name="B6"  id="options_right" class="fangniu" />
								</p>
								<p align="center">
									<input type="button" value="&gt;&gt;" name="B4"  id="allRight" class="fangniu" />
									</p>
									<p align="center">
					</td>
					<td style="width: 250px">
						<p>
							<b>已有角色:</b>
						</p>
						<br>
						<p>
						<s:if test="roleInfoList!=null && roleInfoList.size()>0">
							<s:select list="roleInfoList" size="6" listValue="name" 
								listKey="id" id="select_right" name="saveDto.rightValues"
								cssStyle="width:250px;height:200px; background:url(images/input.gif) repeat-x left top; border:#A4A4A4 solid 1px"/>
					    </s:if>
					    <s:else>
					    	<select name="saveDto.rightValues" size="6" id="select_right"
					    	style="width:250px;height:200px; background:url(images/input.gif) repeat-x left top; border:#A4A4A4 solid 1px"></select>
					  	</s:else>
					  	
					  </p>
					  </td>
					  <td>&nbsp;</td>
				</tr> 
			</table>
			</div>
	</form>  
</div>
</html>
