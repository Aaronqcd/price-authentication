<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>单位管理</title>
		<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script>
			jQuery(document).ready(function(){
		    	var id =  $('#selected').val()
				changeSort(id);
				var sId = '<s:property value="unitsDto.TUnitsSort.id"/>';
				if(sId == null || sId == "")
				{
					sId = -1;
				}
				$('#son').val(sId);
			}); 
		</script>
		<script type="text/javascript">
		
 		//删除选中的部门
    	function del(){
   			var ids=document.getElementsByName('unitsDto.ids');
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
			var param=$("#deleteUnitForm").serialize();
            $.ajax({
		       url: "${pageContext.request.contextPath}/units/deleteUnit.action?time="+new Date().getTime(),
				type:"get",
				data:param,
		        async: false,
				dataType:"json",
		        success: function(result){
		        	if(result.message =="0"){
		        		alert("删除成功！");
					}else{
						alert("单位中有与委托书单位关联，不能删除！");
					}
					document.searchForm.action="${pageContext.request.contextPath}/units/unitsList.action";
   	 				document.searchForm.submit();
				},error:function(a){alert("数据异常！");}
			});
	  	}
     } 
	 
	function searchList(){
		document.searchForm.action="${pageContext.request.contextPath}/units/unitsList.action?tm=" + new Date().getTime();
	 	//$("#searchForm").attr("action","${pageContext.request.contextPath}/units/unitsList.action?tm=" + new Date().getTime());
	  	document.searchForm.submit();
	   //	$("#searchForm").submit();
	}  
	
	 function addUnitInfo(){
	 		location.href="${pageContext.request.contextPath}/units/preAdd.action";
	 }
		 
	  function changeSort(selectId){
	     var aaa=[];
	     <c:forEach items="${sonList}" var="son">
	     var sort={id:"${son.id }",pid:'${son.TUnitsSort.id }',name:'${son.sortName }'};
	    aaa.push(sort);
	    </c:forEach>
			var son = document.getElementById("son");
			son.innerHTML = "";
			son.add(new Option('全部','-1'));
			for(var i=0;i < aaa.length;i++)
			{
				if(aaa[i].pid == selectId)
				{
			    	son.add(new Option(aaa[i].name,aaa[i].id));
				}
			}
			
		}
  
  //全选
	check_All = function(obj){
  	var checkboxs = $(":input[name='unitsDto.ids']");
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
  
 	var windowParams = "height=500, innerHeight=300, width=400, innerWidth=400, scrollbars=yes, left=330, top=100, location=no, status=no, resizable=no, toolbar=no, menubar=no";
	function queryUnitSorts(id){
		var sortId = "";
 	 	//取一级菜单ID,根据一级菜单ID查询该ID下所有有效二级菜单,未取到则查询所有一级有效菜单
 	 	if(id==2){
 	 		sortId = $("#selected").val();
 	 		if(sortId =='-1'){
 	 			alert("请选择一级单位类型");
 	 			return;
 	 		}
 	 	}
		var url = "${pageContext.request.contextPath}/units/queryUnitsSorts.action?sortDto.id="+sortId + "&tm=" + new Date().getTime() ;
		window.open(url, "unitSortWidonw", windowParams);
	}

	</script>
	</head> 
	<body>
	<h3>单位管理</h3>
		<!--下面内容开始-->
		<div class="box3-tc" id="searchCloseDiv">
		<form method="post" name="searchForm" action="${pageContext.request.contextPath}/units/unitsList.action">
						<table>
							<tr>
								<th>单位名称：</th>
								<td>
									<s:textfield name="unitsDto.name" id="name" cssClass="input1"/>
								</td>
								<th>单位编码：</th>
								<td>
									<s:textfield  name="unitsDto.no" id="no" cssClass="input1" />
								</td>
								<th>单位简称：</th>
								<td>
									<s:textfield name="unitsDto.shortName" id="shortName" cssClass="input1" />
								</td>
							</tr>
							<tr>
								<th>单位类型：</th>
								<td><s:select list="sortList" listKey="id" id="selected" 
												listValue="sortName" name="unitsDto.TUnitsSort.TUnitsSort.id" 
												cssClass="input1" cssStyle="margin-right:2px ;" headerKey="-1" headerValue="全部" onchange="changeSort(this.value);"/>
									<input type="button" name="round" class="anniu2" value="维护" onclick="javascript:queryUnitSorts(1);"  style="margin-right:20px ;"/>	
									 <select id="son" name="unitsDto.TUnitsSort.id" class="input1"  style="margin-right:2px ;"> 
									 </select>
									 <input type="button" name="round" class="anniu2" value="维护" onclick="javascript:queryUnitSorts(2);"/>
								</td>
								<th>鉴定类型:</th>
								<td>
									<s:select id="type" name="unitsDto.type" cssClass="input1" headerKey="-1" headerValue="全部"
									list="#{'1':'刑事','2':'民事','3':'行政','4':'价格认定','5':'价格评估'}"/>
								</td>
							</tr>
							<tr>
								<td colspan="8" align="right">
								<div class="buttomk1">
								 <input type="submit" value="查询" class="anniu" onclick="searchList();"/>
								<input name="Submit2" type="button" class="anniu" value="创建" onclick="javascript:addUnitInfo();"/>
								<input type="button" value="删除" class="anniu" onclick="del();"/>
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
								<th width="5%"><input type="checkbox" name="checkAll1" id="checkAll1" onclick="javascript:check_All(this);" />全选</th>
								<th width="20%">单位名称</th>
								<th width="12%">单位编码</th>
								<th width="20%">单位简称</th>
								<th width="8%">单位类型</th>
								<th width="12%">鉴定类别</th>
								<th width="8%">电话</th>
								<th width="8%">操作</th>
							</tr> 
							<s:iterator value="unitsInfoList" status='st'>
								 <tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
									<td id="checkId_<s:property value='id'/>">
										<input type="checkbox" value="<s:property value="id" />" id="id" name="unitsDto.ids"/>
									</td>
									<td>
										<s:if test="name != null && name.toString().length() > 10">
						            	 	<span title="<s:property value="name"/>">
						            	 		<s:property value="name.toString().substring(0,10)"/>...
						            	 	</span>
						            	 </s:if>
						            	 <s:else>
						            	 	<s:property value="name" />
						            	 </s:else>
									</td>
									<td>
										<s:property value="no" />
									</td >
									<td>
										<s:property value="shortName" />
									</td>
									<td>
										<s:if test="TUnitsSort.sortName eq \"无\"">
											<s:property value="TUnitsSort.TUnitsSort.sortName" />
										</s:if>
										<s:else>
											<s:property value="TUnitsSort.sortName" />
										</s:else>
									</td>
									<td>
									<s:set name="type" value="%{type}" />
										<s:if test="#type==1">刑事</s:if>
										<s:if test="#type==2">民事</s:if>
										<s:if test="#type==3">行政</s:if>
										<s:if test="#type==4">价格认定</s:if>
										<s:if test="#type==5">价格评估</s:if>
									</td>
									<td>
										<s:property value="contactTel" />
									</td>
									<td>
										<a href="${pageContext.request.contextPath}/units/getUnit.action?id=${id}" id="modifyUser">修改</a>
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
