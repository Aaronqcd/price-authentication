<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE HTML>
<html> 
<head>
<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
<script type="text/javascript">

   	function del(){
 		var ids=document.getElementsByName('sortDto.ids');
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
			var param=$("#searchForm").serialize();
            $.ajax({
		       url: "${pageContext.request.contextPath}/units/deleteUnitSort.action?time="+new Date().getTime(),
				type:"get",
				data:param,
		        async: false,
				dataType:"json",
		        success: function(result){
		        	if(result.message =="0"){
		        		alert("删除成功！");
		        		window.opener.searchList();
		        		window.close();
					}else{
						alert("有单位使用到改单位类型，不能删除！");
					}
					//document.searchForm.action="${pageContext.request.contextPath}/units/unitsList.action";
   	 				//document.searchForm.submit();
				},error:function(a){alert("数据异常！");}
			});
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
	 
	 function save(){
	    if (!jQuery("#searchForm").validationEngine('validate')) {return;}
	    var param=$("#searchForm").serialize();
        $.ajax({
	       	url: "${pageContext.request.contextPath}/units/saveUnitsSort.action?time="+new Date().getTime(),
			type:"get",
			data:param,
	        async: false,
			dataType:"json",
	        success: function(result){
	        	if(result.message =="1"){
	        		alert("保存成功！");
		        	window.close();
	        		window.opener.searchList();
				}else{
					alert("保存失败！");
				}
			},error:function(a){alert("数据异常！");}
		});
	 }
 
	  var n='<s:property value="sortList.size"/>';
	  
	  function add(){
		   var trText1='<tr>';
	       trText1+='<td style="align:left"><input type="checkbox" id="sortId" value="" name="sortDto.ids"/>';
	       trText1+='<input type="hidden" name="sortList['+n+'].TUnitsSort.id" value="${sortDto.id}"/>';
	       trText1+='<td><input class="zinput validate[required] text-input" style="background-color:transparent;border:0px;width:100%" id="theKey_'+n+'" name="sortList['+n+'].sortName" /></td>';
	       trText1+='</tr>';
			$("#tableSell tbody").append(trText1);//jQuery("#formId").validationEngine();
		   n++;
	  }
	  
	  function toBack(){
	  	window.close();
	  }

</script>
</head>
<body>
<h3>单位类型维护</h3>
  
<form action="${pageContext.request.contextPath}/units/queryUnitsSorts.action" method="post" name="searchForm" id="searchForm">
   <div class="buttomk1">
   <input name="Submit2" type="button" class="anniu" value="保存" onclick="save()" buttonAction="update">
    <input name="Submit2" type="button" class="anniu" value="添加" onclick="add()" buttonAction="add">
    <input name="Submit2" type="button" class="anniu" value="删除" onclick="del()" buttonAction="del">
	<input name="Submit2" type="button" class="anniu" value="返回" onclick="javascript:toBack()">
  </div>
 	<s:hidden name="sortDto.id"/>
	<div class="clear"></div>
	<div class="box1">
		<table id="tableSell" border="0" cellpadding="0" cellspacing="0">
			<thead>
			  <tr >
			    <th width="3%" style="align:center"><input type="checkbox" name="selectAll" id="selectAll" value="checkbox" onclick="checkAll('sortDto.ids');" />全选</th>
			    <th width="15%" align="left">单位类型</th>
			  </tr>
			  </thead>
		  <tbody>
			  <s:iterator value="sortList" status="st">
				  <tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
				  <s:hidden name="sortList[%{#st.index}].id"/>
				  <s:hidden name="sortList[%{#st.index}].TUnitsSort.id"/>
				  <s:hidden name="sortList[%{#st.index}].markForDel"/>
				    <td style="align:left"><input type="checkbox" value="<s:property value="id" />" id="userId" name="sortDto.ids" /></td>
				    <td><s:textfield name="sortList[%{#st.index}].sortName" id="theKey_%{#st.index}" cssStyle="background-color:transparent;border:0px;width:100%"/></td>
				  </tr>
			  </s:iterator>
		  </tbody>
		</table>
	</div>
    </form>