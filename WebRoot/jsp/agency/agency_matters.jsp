<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代办事项</title>
	<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
	<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>
</head>
<body id="agencyDiv">
<div class="clear"></div>
<!--下面内容开始-->
	<div class="ykuang01">
		<!--tab页面开始-->
		<div class="tabsJ">
		  <ul>
		    <li class="tabsJdq"  id="tab0"><a onclick="queryAgency(1);selectTag(0,this,6);" href="javascript:void(0)"><span>待受理委托书(${agencyMap.con1})</span></a></li>
		    <li id="tab1"><a onclick="queryAgency(2);selectTag(1,this,6);" href="javascript:"><span>待鉴定委托书(${agencyMap.con2})</span></a></li>
		    <li id="tab2"><a onclick="queryAgency(3);selectTag(2,this,6);" href="javascript:void(0)"><span>待初审委托书(${agencyMap.con3})</span></a></li>
		    <li id="tab3"><a onclick="queryAgency(4);selectTag(3,this,6);" href="javascript:void(0)"><span>待复审委托书(${agencyMap.con4})</span></a></li>
		    <li id="tab4"><a onclick="queryAgency(5);selectTag(4,this,6);" href="javascript:void(0)"><span>待终审委托书(${agencyMap.con5})</span></a></li>
		    <li id="tab5"><a onclick="queryAgency(6);selectTag(5,this,6);" href="javascript:void(0)"><span>待归档委托书(${agencyMap.con6})</span></a></li>
		  </ul>
		</div>
	</div>
	<div class="clear"></div>
    <div id=tagContent0 style="display:">
	</div>
<div class="clear"></div>
</body>
</html>
<script type="text/javascript">
		queryAgency(0);
		function queryAgency(flag){
			if(flag==0 || document.getElementById("tab"+(flag-1)).className!="tabsJdq"){
				if(flag==0)flag=1;
				$.ajax({
		            url: "${pageContext.request.contextPath}/agency/agencyCmsList.action",
					type:"POST",
		            cache:false,
		            dataType:"html",
		            data:{"cmsDto.flag":flag},
		            success: function(html){
		            	checkSession(html,'${pageContext.request.contextPath}','html');
		            	$("#tagContent0").html('').html(html);
		            }
		        });
			};	
		} 
</script>


