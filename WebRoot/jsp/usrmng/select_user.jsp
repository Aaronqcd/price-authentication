<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
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
   
	function searchUser(){
	  	document.searchForm.action="${pageContext.request.contextPath}/usrmng/popuUser.action";
	  	searchForm.submit();
	}
	
	 function selectUser(){
	    var name=$('#selectName').val();
	    var pUserId=$('#pUserId').val();
	    if(pUserId == ""){
	    	pUserId = "0";
	    }
	   //把隐藏
	   window.opener.setUser(name,pUserId);
	   window.close();
	 }   
	   
	function setUser1(pUserId,selectName){
	   $('#pUserId').val(pUserId); 
	   $('#selectName').val(selectName);
	}
	
	   //全选
	check_All = function(obj){
    	var checkboxs = $(":input[name='userDto.id']");
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
	   
	$(document).ready(function(){
			$("#clear").bind("click",function(){
			$("div#searchId .zinput").val("");
			$("div#searchId select").val("");
		});
	});      
</script>
	</head> 
	<body>
	<div class="content_wrap" style="width: 100%"> 
		<div style="float: left;">
		<div class="kuang">
				<div class="box1" id="searchShrinkDiv">
					<s:hidden name="pUserId" />
					<form action="${pageContext.request.contextPath}/usrmng/popuUser.action" method="post" name="searchForm">
						<s:hidden name="name" id="selectName"/>
						<s:hidden name="userDto.dutyCode" id="dutyCode"/>
					<table>
						<tr>
							<th>用户名：</th>
							<td>
								<s:textfield name="userDto.username" id="username" cssClass="input1"></s:textfield>
							</td>
							<th>姓名：</th>
							<td>
								<s:textfield name="userDto.name" id="saveuname" cssClass="input1" />
							</td>
							<th>状态:</th>
							<td>
								<s:select name="userDto.status" list="#{'1':'在职','2':'离职','0':'禁用'}" cssClass="input1" headerKey="-1" headerValue="全部" cssStyle="margin-right:5px;"/>
							   <input name="button" type="button" class="buttom" value="查询" onclick="javascript:searchUser();" />
							</td>
						</tr>
					</table>
					</form>
				</div>
				<div class="kuang">
					<div class="title2">查询结果</div>
					<form action="${pageContext.request.contextPath}/usrmng/popuUser.action" method="post" name="searchForm2" id="searchForm2">
					<div class="box2">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<th width="5%" ><input type="checkbox" name="checkAll1" id="checkAll1" onclick="javascript:check_All(this);" />全选</th>
							<th width="10%">用户名</th> 
							<th width="10%">姓名</th>
							<th width="10%">电话</th>
							<th width="10%">Email</th>
							<th width="10%">状态</th>
							<th width="10%">创建日期</th>
						</tr>
		
						<s:iterator value="pager.data" status='st'>
							<tr <s:if test="%{#st.index%2==0}"> class="td1"</s:if>
								onclick="setUser1(<s:property value="id"/>,'<s:property value="name"/>')">
								<td>
									<input type="radio" value="<s:property value="id" />"
										id="userId" name="userDto.id" ondblclick="javascript:selectUser();"/>
								</td> 
								<td>
									<s:property value="username"/>
								</td>
								<td>
									<s:property value="name"/>
								</td>
								<td>
									<s:property value="phone" />
								</td>
								<td>
									<s:property value="email" />
								</td>
								<td>
										<s:property value="statusStr" />
								</td>
								<td>
									<s:date name="crtTime" format="yyyy-MM-dd" />
								</td>
							</tr>
						</s:iterator>
					</table>
					</div>
					</form>
				<div class="kuang"><div class="box2">
				<table border="0" cellpadding="0" cellspacing="0"><tr class="td1"><td>
					<s:form name="form2" id="form2" method="post">
						<jsp:include page="/jsp/included/page.jsp" flush="true"></jsp:include>
					</s:form>
				</td></tr>
				</table>
			</div></div>
				 <input type="button" value="选择" class="anniu" onclick="selectUser();"><input type="button" class="anniu" value="关闭" onclick="window.close();">		
			</div> 
		</div>
	</div>
</body>
</html>