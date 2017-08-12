<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script type="text/javascript">
	function searchUser() {
		document.searchForm.action = "${pageContext.request.contextPath}/usrmng/selectName.action";
		searchForm.submit();
	}
	function reallysure(){
		var userId=window.opener.document.getElementById("cmsUsr2").value;
		var userId2=window.opener.document.getElementById("userId2").value;
		window.opener.document.getElementById("userId").value = userId;
		window.opener.document.getElementById("cmsUsr").value = userId2;
		window.close();
	}
	function selectUser(obj,userId,cmsUsr) {
		var userIds = window.opener.document.getElementById("userId2").value;
		var cmsUsrs = window.opener.document.getElementById("cmsUsr2").value;
		if (userId == '') {
			messageError("你选的用户无效");
			obj.checked = false;
		} else {
			var p = userIds.split(",");
			for (j = 0; j < p.length; j++) {
				
					if (p[j] == userId) {
						p.splice(j, 1);
					}
				}
				if(p[0]==''){
					p.splice(0, 1);
				}
				userIds = p;
			if (obj.checked) {
				userIds += ',' + userId;
				var p = userIds.split(",");
				if(p[0]==''){
					p.splice(0, 1);
				}
				userIds = p;
			}
		}
		window.opener.sethiddenUser(userIds);
		
		if (cmsUsr == '') {
			messageError("你选的用户无效");
			obj.checked = false;
		} else {
			var p = cmsUsrs.split(",");
			for (j = 0; j < p.length; j++) {
					if (p[j] == cmsUsr) {
						p.splice(j, 1);
					}
				}
				if(p[0]==''){
					p.splice(0, 1);
				}
				cmsUsrs = p;
			if (obj.checked) {
				cmsUsrs += ',' + cmsUsr;
				var p = cmsUsrs.split(",");
				if(p[0]==''){
					p.splice(0, 1);
				}
				cmsUsrs = p;
			}
		}
		window.opener.setUser(cmsUsrs);
	}	
	function checkboxss(){
		var checkboxes = document.getElementsByName("ids");
		var selectID=window.opener.document.getElementById("userId").value;
		var	phones = window.opener.document.getElementById("cmsUsr").value;
		var p = phones.split(",");
		for(var i=0;i<checkboxes.length;i++){
			for(var j=0;j<p.length;j++){
				if(p[j]!='' && checkboxes[i].value==p[j]){
					checkboxes[i].checked=true;
				}
			}
		}
	}
	$(document).ready(function() {
		$("#clear").bind("click", function() {
			$("div#searchId .zinput").val("");
			$("div#searchId select").val("");
		});
		checkboxss();
	});
	
    
    //全选
	check_All = function(obj){
    	var checkboxs = $(":input[name='ids']");
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
	<h3>选择查验人员</h3>
		<!--下面内容开始-->
		<div class="box3-tc" id="searchCloseDiv">
	<form action="${pageContext.request.contextPath}/usrmng/selectName.action"
							method="post" name="searchForm">
						<table>
								<tr>
									<th>
										用户名：
									</th>
									<td>
										<s:textfield name="userDto.username" id="username"
											cssClass="input1"></s:textfield>
									</td>
									<th>
										姓名：
									</th>
									<td>
										<s:textfield name="userDto.name" id="saveuname"
											cssClass="input1" />
									</td>
									<th>
										状态:
									</th>
									<td>
										<s:select name="userDto.status"
											list="#{'1':'在职','2':'离职','0':'禁用'}" cssClass="input1"
											headerKey="-1" headerValue="全部" cssStyle="margin-right:5px;" />
										
									</td>
									
								</tr>
								<tr>
								<td colspan="6" >
								<div>
								<input name="button" type="button" class="anniu" value="查询"
											onclick="javascript:searchUser();" />
								<input type="button" class="anniu" value="选择"
							onclick="reallysure();">
							<input type="button" class="anniu" value="关闭"
							onclick="window.close();">
								</div>
								</td>
							</tr>
							</table>
						</form>
					</div>
				
					<div class="clear"></div>
				
					<div class="box1">
				<form  method="post" name="deleteUnitForm" id="deleteUnitForm">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<th width="4%">
									<input type="checkbox" name="checkAll1" id="checkAll1" onclick="javascript:check_All(this);" />全选
								</th>
								<th width="10%">
									用户名
								</th>
								<th width="10%">
									姓名
								</th>
								<th width="10%">
									部门
								</th>
								<th width="10%">
									电话
								</th>
								<th width="10%">
									Email
								</th>
								<th width="10%">
									状态 
								</th>
								<th width="10%">
									创建日期
								</th>
							</tr> 
							<s:iterator value="pager.data" status='st'>
								<tr <s:if test="%{#st.index%2==0}"> class="td1"</s:if>>
											<td>

												<input type="checkbox" onclick="selectUser(this,<s:property value="id"/>,'<s:property value="name"/>');"
													value="<s:property value="id" />"
													name="ids" />
											</td>
											<td>
												<s:property value="username" />
											</td>
											<td>
												<s:property value="name" />
											</td>
											<td>
												<s:property value="deptInfo.deptName" />
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
			</form>
					</div>
		<div class="kuang"><div class="box2">
			<table border="0" cellpadding="0" cellspacing="0"><tr class="td1"><td>
				<s:form name="form2" id="form2" method="post">
					<jsp:include page="/jsp/included/page.jsp" flush="true"></jsp:include>
				</s:form>
			</td></tr>
			</table>
		</div></div>
</body>
</html>
