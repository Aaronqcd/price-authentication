<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户列表</title>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
<script type="text/javascript">
	<!--
	var timeout         = 500;
	var closetimer		= 0;
	var ddmenuitem      = 0;
	
	// open hidden layer
	function mopen(id)
	{	
		// cancel close timer
		mcancelclosetime();
	
		// close old layer
		if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
	
		// get new layer and show it
		ddmenuitem = document.getElementById(id);
		ddmenuitem.style.visibility = 'visible';
	
	}
	// close showed layer
	function mclose()
	{
		if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
	}
	
	// go close timer
	function mclosetime()
	{
		closetimer = window.setTimeout(mclose, timeout);
	}
	
	// cancel close timer
	function mcancelclosetime()
	{
		if(closetimer)
		{
			window.clearTimeout(closetimer);
			closetimer = null;
		}
	}
	
	// close layer when click-out
	document.onclick = mclose; 
	// -->
 	function del(){
      var ids=document.getElementsByName('userDto.ids');
      var checked=false;
      for(var i=0;i<ids.length;i++){
         if(ids[i].checked ){
           checked=true;
         }
      }if(!checked){
			alert("请选择删除项");
			return;
		}
		if (confirm ("确定要提交吗？")){		
		  	document.deleteUserForm.action="${pageContext.request.contextPath}/usrmng/deleteUser.action";
            document.deleteUserForm.submit();
	  	}
     } 
	 
	function searchUser(o){
	  	document.searchForm.action="${pageContext.request.contextPath}/usrmng/userList.action";
	   	document.searchForm.submit();
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
 
	 function toCreateUser(){
	        document.createForm.action="${pageContext.request.contextPath}/usrmng/toCreateUser.action?tm="+ new Date().getTime();
	      	document.createForm.submit();
	 }
	  
	 //修改用户
	 function queryUser(uId){
		if(uId <= 0){
			var userId= $("#saveId").val();
			if(userId==null||userId==""){ 
				alert("请选择用户查看 ");
				return;
			}
		}else{
			$("#saveId").val(uId);
		}
		document.userUpdate.action="${pageContext.request.contextPath}/usrmng/queryUser.action?tm="+ new Date().getTime();
		document.userUpdate.submit();
	 }
 
	   //全选
		check_All = function(obj){
	    	var checkboxs = $(":input[name='userDto.ids']");
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
	   
    $(document).ready(function() {
		 $("td[id^=checkId_]").click(function(e){
			e = window.event || e;
	        if(document.all){
	            e.cancelBubble = true;
	        } else {
	            e.stopPropagation();
	        }
		 });
	});  
    
</script>
	</head> 
	<body>
	<h3>用户管理</h3>
		<!--下面内容开始-->
		<div class="box3-tc" id="searchCloseDiv">
				<form action="${pageContext.request.contextPath}/usrmng/userList.action" method="post" name="searchForm">
						<table>
							<tr>
								<th>真实姓名：</th>
								<td>
									<s:textfield name="userDto.name" id="name" cssClass="input1" />
								</td>
								<th>登陆账号：</th>
								<td>
									<s:textfield name="userDto.username" id="username" cssClass="input1"></s:textfield>
								</td>
								<th>账号状态:</th>
								<td>
									<s:select name="userDto.status" onchange="selected();"  headerKey="-1" headerValue="全部"
											list="#{'1':'正常','0':'禁用'}" cssClass="input1" id="selected"  />
									<input buttonAction="list" name="button" type="button" class="anniu" value="查询" onclick="javascript:searchUser(this);" />
									<input buttonAction="add" name="button2" type="button" class="anniu" value="创建" onclick="javascript:toCreateUser();" />
									<input buttonAction="del" name="button3" type="button" class="anniu" value="删除" onclick="del();" /> 
							</td>
							</tr>
						</table>
							</form>
					</div>
			
					<div class="clear"></div>
				<form  method="post" name="deleteUserForm" id="deleteUserForm">
					<div class="box1">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<th width="5%" align="center"><input type="checkbox" name="checkAll1" id="checkAll1" onclick="javascript:check_All(this);" />全选</th>
								<th width="12%" align="center">真实姓名</th>
								<th width="12%" align="center">登陆账号</th> 
								<th width="12%" align="center">电话</th>
								<th width="8%" align="center">账号状态</th>
								<th width="12%" align="center">最后登陆</th>
								<th width="8%" align="center">累计登陆</th>
								<th width="8%" align="center">操作</th>
							</tr> 
							<s:iterator value="pager.data" status='st'>
								<tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
									<td id="checkId_<s:property value='id'/>" align="center">
										<input type="checkbox" value="<s:property value="id" />" id="userId" name="userDto.ids" />
									</td>
									<td align="center">
										<s:property value="name" />
									</td>
									<td align="center">
										<s:property value="username" />
									</td>
									<td align="center">
										<s:property value="tel" />
									</td>
									<td align="center">
										<s:property value="statusStr" />
									</td>
									<td align="center">
										<s:date name="lastDate" format="yyyy-MM-dd HH:mm" />
									</td>
									<td align="center">
										<s:property value="loginSum" />
									</td>
									<td align="center">
										<a href="#" id="modifyUser"  onclick="queryUser('<s:property value='id'/>')">修改</a>
									</td>
								</tr>
							</s:iterator> 
						</table>
				</div>
			</form>
			<form action="" id="createForm" name="createForm"></form>
			<form action="" id="userUpdate" name="userUpdate">
				<s:hidden name="saveDto.id" id="saveId"/>
			</form>
		<div class="kuang"><div class="box2">
			<table border="0" cellpadding="0" cellspacing="0"><tr class="td1"><td>
				<s:form name="form2" id="form2" method="post">
					<jsp:include page="/jsp/included/page.jsp" flush="true"></jsp:include>
				</s:form>
	
			</table>
		</div></div>
</body>
</html>
