<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>采价信息列表</title>
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
	
	function openAddMiningDlg(){
		if(undefined == $("#sortName_") || $("#sortName_").val()=='' || $("#sortName_").val()=="标的物目录"){
			alert("请先点击左边标的物目录");
			return;
		}
		openMiningDialog("新增采价",sortName);
	}
	
	function removeMining(id){
	    if(confirm("确定删除采价吗？")){
		     $.ajax({
		         url: "${pageContext.request.contextPath}/mining/removeMining.action",
				 type:"POST",
				 data:{
					'id':id
				 },
		         async: false,
				 dataType:"text",
		         success: function(data){
		        	if(!checkSession(data,'${pageContext.request.contextPath}','html')){
		        		return;
		        	};
		 			if(data == 'success'){//成功
		 	 			alert("采价删除成功！");
		 	 			initMinPrc(sortId, "0");
						//$("#searchButton").click();
		  			}else{//失败
		  			    alert(data);
		  			}
				},
				error:function(e){
					alert("采价删除失败，请稍候重试！");
					//alert("采价新增发生错误！"+JSON.stringify(e));
				}
		     });
		}
	}
	
    function searchMin(){
    	$("#sortName_").val("");
		var params=$('#searchForm').serialize();
		$.ajax({
           	url: "${pageContext.request.contextPath}/mining/miningList.action",
           	type:"POST",
           	cache:false,
           	dataType:"html",
            data:params,
           	success: function(html){
           		checkSession(html,'${pageContext.request.contextPath}','html');
           		$("#tagContent0").html('').html(html);
           	}
       	});
	}
	
	var impWindow;
	var impFeatures=" width=660 height=360 scrollbars=1  left = 250 top = 150 location=no status=no resizable=no  toolbar=no menubar=no";
	//标的物导入
    function impMinings(){
    	if (!impWindow || impWindow.closed) {
       		impWindow = window.open("${pageContext.request.contextPath}/mining/toImpMinings.action", "impWindow", impFeatures);
       	} else {
       		impWindow.focus( );
       	}
    }
</script>
</head>
	<body>
		<!--下面内容开始-->
					<div class="box1">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<th align="center">序号</th>
								<th align="center">采价地点</th>
								<th align="center">采价人</th>
								<th align="center">价格基准日</th>
								<th align="center">采价日期</th>
								<th align="center">分类（品名）</th>
								<th align="center">型号（类型）</th>
								<th align="center">厂家指导价</th>
								<th align="center">经销商价格</th>
								<th align="center">状态</th>
								<th align="center">操作</th>
							</tr> 
							<s:iterator value="list" status='st'>
								<tr <s:if test="%{#st.index%2==0}">class="td1"</s:if> bgcolor="#FFFFFF">
									<td id="checkId_<s:property value='id'/>" align="center">
										<s:property value="id"/>
									</td>
									<td align="center">
										<s:if test="miningAddress != null && miningAddress.toString().length() > 6">
						            	 	<span title="<s:property value="miningAddress"/>">
						            	 		<s:property value="miningAddress.toString().substring(0,6)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="miningAddress"/>
						            	 </s:else>
									</td>
									<td align="center">
										<s:if test="TUserInfo.name != null && TUserInfo.name.toString().length() > 4">
						            	 	<span title="<s:property value="TUserInfo.name"/>">
						            	 		<s:property value="TUserInfo.name.toString().substring(0,4)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="TUserInfo.name"/>
						            	 </s:else>
									</td>
									<td align="center">
										<s:date name="referenceDate" format="yyyy-MM-dd" />
									</td>
									<td align="center">
										<s:date name="miningDate" format="yyyy-MM-dd" />
									</td>
									<td align="center">
										<s:if test="name != null && name.toString().length() > 8">
						            	 	<span title="<s:property value="name"/>">
						            	 		<s:property value="name.toString().substring(0,8)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="name"/>
						            	 </s:else>
									</td>
									<td align="center">
										<s:if test="typeName != null && typeName.toString().length() > 8">
						            	 	<span title="<s:property value="typeName"/>">
						            	 		<s:property value="typeName.toString().substring(0,8)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="typeName"/>
						            	 </s:else>
									</td>
									<td align="center">
										<s:if test="guidePrice != null && guidePrice.toString().length() > 6">
						            	 	<span title="<s:property value="guidePrice"/>">
						            	 		<s:property value="guidePrice.toString().substring(0,6)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="guidePrice"/>
						            	 </s:else>
									</td>
									<td align="center">
										<s:if test="miningPrice != null && miningPrice.toString().length() > 6">
						            	 	<span title="<s:property value="miningPrice"/>">
						            	 		<s:property value="miningPrice.toString().substring(0,6)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="miningPrice"/>
						            	 </s:else>
									</td>
									<td align="center">
										<s:if test="status == 1">正常</s:if>
										<s:if test="status == 2"><label style="color: red;">退市</label></s:if>
									</td>
									<td align="center">
										<a href="javascript:void(0)" id="modifyUser" onclick="getMining(<s:property value='id'/>)">修改</a>
										<a href="#" id="modifyUser"  onclick="javascript:removeMining('<s:property value='id'/>')">删除</a>
									</td>
								</tr>
							</s:iterator> 
						</table>
				</div>
				<div class="kuang"><div class="box2">
					<table border="0" cellpadding="0" cellspacing="0"><tr class="td1"><td>
						<s:form name="form2" id="form2" method="post">
							<jsp:include page="/jsp/included/ajax_page.jsp" flush="true"></jsp:include>
						</s:form>
					</td></tr>
					</table>
				</div>
</body>
</html>
