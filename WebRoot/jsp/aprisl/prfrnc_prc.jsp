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
 
	//参考价格
	function getPrcList(){
		var ids=document.getElementsByName('minDto.ids');
		var checked=false;
      		for(var i=0;i<ids.length;i++){
         		if(ids[i].checked ){
           		checked=true;
         	}
      	}if(!checked){
			alert("请选择引用价格项");
			return;
		}
		//if (confirm ("确定这些选择引用价格项吗？")){	
			var params = $("#priceForm").serialize();
			window.opener.addPrfrmc(params);
			window.close();
		  	//document.deleteUnitForm.action="${pageContext.request.contextPath}/agency/getPrcList.action?mid="+document.getElementById("mid").value;
            //document.deleteUnitForm.submit();
	  	//}
	}
	
	function getMining(id){
		window.parent.getMining(id);
	}
	
	function closeBut() {
		window.close(); 
	}
	
	 //全选
	check_All = function(obj){
    	var checkboxs = $(":input[name='minDto.ids']");
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
    
    function searchPrc(){
			var params=$('#searchForm').serialize();
			$.ajax({
	           	url: "${pageContext.request.contextPath}/aprisl/miningPriceList.action?mid="+document.getElementById("mid").value,
	           	type:"POST",
	           	cache:false,
	           	dataType:"html",
	            data:params,
	           	success: function(html){
	           		$("#tagContent0").html('').html(html);
	           	}
	       	});
		}
    
</script>
</head>
	<body>
			<div class="box1">
				<form  method="post" name="priceForm" id="priceForm">
					<s:hidden name="mid"/> 
					<table border="0" cellpadding="0" cellspacing="0" style="width:98%">
						<tr>
							<th width="5%" style="text-align: center"><input type="checkbox" name="checkAll1" id="checkAll1" onclick="javascript:check_All(this);" />全选</th>
							<th width="12%" style="text-align: center">采价地点</th>
							<th width="8%" style="text-align: center">价格基准日</th>
							<th width="8%" style="text-align: center">采价日期</th>
							<th width="15%" style="text-align: center">分类（品名）</th>
							<th width="15%" style="text-align: center">型号（类型）</th>
							<th width="15%" style="text-align: center">厂家指导价</th>
							<th width="15%" style="text-align: center">经销商价格</th>
							<th width="5%" style="text-align: center">状态</th>
						</tr> 
						<s:iterator value="list" status='st'>
							<tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
								<td align="center" id="checkId_<s:property value='id'/>">
									<input type="checkbox" value="<s:property value="id" />" id="id" name="minDto.ids" />
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
								<s:if test="guidePrice != null && guidePrice.toString().length() > 7">
				            	 	<span title="<s:property value="guidePrice"/>">
				            	 		<s:property value="guidePrice.toString().substring(0,7)"/>...
				            	 	</span>
				            	 </s:if>
				            	 <s:else>
				            	 	<s:property value="guidePrice"/>
				            	 </s:else>
							</td>
							<td align="center">
								<s:if test="miningPrice != null && miningPrice.toString().length() > 7">
				            	 	<span title="<s:property value="miningPrice"/>">
				            	 		<s:property value="miningPrice.toString().substring(0,7)"/>...
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
							</tr>
						</s:iterator> 
					</table>
				</form>
				
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
