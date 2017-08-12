<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE HTML>
<html>
<head>
				<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
		<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/cascadeSelect.js"></script>

		<script language="javascript" type="text/javascript">
 				jQuery(document).ready(function(){
             	$("#no").addClass("validate[required],maxUtf8Size[20]");             //单位编码
             	$("#name").addClass("validate[required],maxUtf8Size[300]");           //单位名称
             	$("#shortName").addClass("validate[required],maxUtf8Size[300]");      //简称
             	$("#contactTel").addClass("validate[custom[simplePhone]]");          //电话
             	$("#address").addClass("validate[maxUtf8Size[200]");             //地址
             	$("#remark").addClass("validate[maxUtf8Size[500]");             //备注
             	$("#post").addClass("validate[custom[postCode]]");                   //邮件
             	$("#sort").addClass("validate[funcCall[checkSort]]");                //单位类型
             	$("#type").addClass("validate[funcCall[checkType]]");                //鉴定类别
                jQuery("#unitForm").validationEngine();
            });
        function checkSort()
        {
        	var checkValue = $("#sort").val();
			if(checkValue == "-1"){
				return "请选择单位类型";
			}else{
				return ;
			}
        }
        function checkType()
        {
        	var checkValue = $("#type").val();
			if(checkValue == "0"){
				return "请选择鉴定类别";
			}else{
				return ;
			}
        }
       //增加按钮      
       function insert(){
	        if (!jQuery("#unitForm").validationEngine('validate')) {
            return;
         	}
	     	document.unitForm.action="${pageContext.request.contextPath}/units/saveUnit.action";
	     	document.unitForm.submit();
	   } 
	
	 	//返回按钮 
	   function closeBut(){ 
	   		var son = document.getElementById("son");
	   		son.innerHTML = "";
	        document.unitForm.action="${pageContext.request.contextPath}/units/unitsList.action";
	        document.unitForm.submit();
	   }
	  
	</script>
	<script>
  function changeSort(selectId){
     var aaa=[];
     <c:forEach items="${sonList}" var="son">
     var sort={id:"${son.id }",pid:'${son.TUnitsSort.id }',name:'${son.sortName }'};
    aaa.push(sort);
    </c:forEach>
			var son = document.getElementById("son");
			son.innerHTML = "";
			for(var i=0;i < aaa.length;i++)
			{
				if(aaa[i].pid == selectId)
				{
			    	son.add(new Option(aaa[i].name,aaa[i].id));
				}
			}
	}
	</script>
</head>
<body>
<h3>单位创建</h3>

<div class="dkuang">
	<form action="" method="post" id="unitForm" name="unitForm">
			<div class="buttomk2">
			 <input type="button" value="保存"  class="anniu" onclick="insert();"/>
		     <input type="button" value="返回"  class="anniu" onclick="closeBut();"/>
			</div>
			<div class="box2"> 
			<h4>基本信息</h4>
			<table>
				<tr>
					<th><span style="color:red">*</span>单位编码: </th>
					<td>
						<input type="text" class="input1" name="saveDto.no" id="no" />
					</td> 
					<th><span style="color:red">*</span>单位类型:</th>
					<td>
						<s:select list="sortList" listKey="id" id="sort" cssClass="input1"
							listValue="sortName" name="saveDto.TUnitsSort.TUnitsSort.id" headerKey="-1" headerValue="请选择" onchange="changeSort(this.value);"/>
				         <select id="son" name="saveDto.TUnitsSort.id" class="input1">
				           </select>
					</td>
					<th><span style="color:red">*</span>单位名称：</th>
					<td>
					<input type="text" class="input1" name="saveDto.name" id="name" />
					</td>  
					
				</tr>
				<tr> 
					<th><span style="color:red">*</span>简称：</th>
					<td>
						<input type="text" class="input1" name="saveDto.shortName" id="shortName" />
					</td>  
				    <th><span style="color:red">*</span>鉴定类别：</th>
					<td>
						<s:select id="type" name="saveDto.type"  list="#{'0':'请选择','1':'刑事','2':'民事','3':'行政','4':'价格认定','5':'价格评估'}"  cssClass="input1" />
					</td>
					<th>联系人：</th>
					<td><input type="text" class="input1" name="saveDto.contactMan" id="contactMan" /></td>
				
				</tr>
				<tr>
					<th>联系电话：</th>
					<td><input type="text" class="input1" name="saveDto.contactTel" id="contactTel"/></td>
					<th>地址 :</th>
					<td><input type="text" class="input1" name="saveDto.address" id="address" /></td>
					<th>邮政编码   ：</th>
					<td><input type="text" class="input1" name="saveDto.post" id="post" />	</td>
				</tr>
				<tr>
					<th>备注:</th>
					<td colspan="5">
						<s:textarea name="saveDto.remark" id="remark" cols="70" rows="6">
						</s:textarea>
					</td>
				</tr>
			</table>
			</div>
	</form>  
</div>
</html>
