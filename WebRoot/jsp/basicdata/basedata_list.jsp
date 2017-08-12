<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html> 
<head>
<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
<script type="text/javascript">
jQuery(document).ready(function(){
   	$("input[id^=theKey_]").addClass("validate[required],maxSize[20] text-input");
   	$("input[id^=value_]").addClass("validate[required],maxUtf8Size[180] text-input");
      jQuery("#searchForm").validationEngine();
  });
 function del(){
		//删除选中的数据
		var checkboxs=$("input[name='viewDto.delIds']:checked");
		if(checkboxs == null || checkboxs.length==0){
			alert("请选择删除项！");
			return;
		}
		if(confirm("确认是否要删除！")){
			$(checkboxs).each(function(){
				var id = $(this).val();//行号，为删除行
				if(id == ""){
					$(this).parent().parent().remove();
				}
			});
			checkboxs=$("input[name='viewDto.delIds']:checked");
			if(checkboxs != null && checkboxs.length>0){
			  	document.searchForm.action="${pageContext.request.contextPath}/basicdata/deleteBasic.action";
			  	document.searchForm.target="_self";
	            searchForm.submit();
			}
		}	
     }

 function search(){
    	document.searchForm.action="${pageContext.request.contextPath}/basicdata/queryBasicData.action";
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
 function save(){
    if (!jQuery("#searchForm").validationEngine('validate')) {return;}
   	document.searchForm.action="${pageContext.request.contextPath}/basicdata/saveBasicData.action";
    document.searchForm.submit();
 }
  var n='<s:property value="basicDataList.size"/>';
  function add(){
	   var trText1='<tr>';
       trText1+='<td><input type="checkbox" id="userId" value="" name="viewDto.delIds"/><input type="hidden" name="basicDataList['+n+'].TBasicData.id" value="${viewDto.id}"/>';
       trText1+='<td><input class="zinput validate[required] text-input" style="background-color:transparent;border:0px;width:100%" id="theKey_'+n+'" name="basicDataList['+n+'].theKey" /></td>';
       trText1+='<td><input class="zinput validate[required] text-input" style="background-color:transparent;border:0px;width:100%" id="value_'+n+'" name="basicDataList['+n+'].value" /></td>';
       trText1+='<td><input class="zinput validate[] text-input" style="background-color:transparent;border:0px;width:100%" id="value2_'+n+'" name="basicDataList['+n+'].value2" /></td>';
       trText1+='<td><input class="zinput validate[] text-input" style="background-color:transparent;border:0px;width:100%" id="value3_'+n+'" name="basicDataList['+n+'].value3" /></td>';
       trText1+='</tr>';
		$("#tableSell tbody").append(trText1);//jQuery("#formId").validationEngine();
	   n++;
  }
  function toBack(){
  	window.location.href="${pageContext.request.contextPath}/basicdata/viewCodeList.action";
  }
</script>
</head>
  
  <body>
  <h3>数据字典管理</h3>
  
 <form action="${pageContext.request.contextPath}/basicdata/viewCodeContent.action" method="post" name="searchForm" id="searchForm">
   <div class="buttomk1">
   <input name="Submit2" type="button" class="anniu" value="保存" onclick="save()" buttonAction="update">
    <input name="Submit2" type="button" class="anniu" value="添加" onclick="add()" buttonAction="add">
    <input name="Submit2" type="button" class="anniu" value="删除" onclick="del()" buttonAction="del">
	<input name="Submit2" type="button" class="anniu" value="返回" onclick="javascript:toBack()">
  </div>
 <s:hidden name="viewDto.id"/>
	<div class="clear"></div>
<div class="box1">
<table id="tableSell" border="0" cellpadding="0" cellspacing="0">
<thead>
  <tr >
    <th width="2%"  align="left"><input type="checkbox" name="selectAll" id="selectAll" value="checkbox" onclick="checkAll('viewDto.delIds');" /></th>
    <th width="10%" align="left">键</th>
    <th width="25%" align="left">值</th>
    <th width="25%" align="left">值2</th>
    <th width="25%" align="left">值3</th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="basicDataList" status="st">
  <tr <s:if test="%{#st.index%2==0}">class="td1"</s:if>>
  <s:hidden name="basicDataList[%{#st.index}].id"/>
  <s:hidden name="basicDataList[%{#st.index}].TBasicData.id"/>
  <s:hidden name="basicDataList[%{#st.index}].markForDel"/>
    <td><input type="checkbox" value="<s:property value="id" />" id="userId" name="viewDto.delIds" /></td>
    <td><s:textfield name="basicDataList[%{#st.index}].theKey" id="theKey_%{#st.index}" cssStyle="background-color:transparent;border:0px;width:100%"/></td>
    <td><s:textfield name="basicDataList[%{#st.index}].value" id="value_%{#st.index}" cssStyle="background-color:transparent;border:0px;width:100%"/></td>
    <td><s:textfield name="basicDataList[%{#st.index}].value2" id="value2_%{#st.index}" cssStyle="background-color:transparent;border:0px;width:100%"/></td>
    <td><s:textfield name="basicDataList[%{#st.index}].value3" id="value3_%{#st.index}" cssStyle="background-color:transparent;border:0px;width:100%"/></td>
  </tr>
  </s:iterator>
  </tbody>
</table>
</div>
    </form>
<div class="kuang"><div class="box2"><table border="0" cellpadding="0" cellspacing="0"><tr class="td1"><td>
    <s:form name="form2" id="form2" method="post">
    <jsp:include page="/jsp/included/page.jsp" flush="true"></jsp:include>
    </s:form></td></tr></table></div></div>
</body>
</html>