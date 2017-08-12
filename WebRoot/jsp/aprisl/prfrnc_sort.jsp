<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>参考价格库</title>

<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>


<link rel="stylesheet" href="${pageContext.request.contextPath}/css/demo.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle.css" type="text/css">




	<!--
	<script type="text/javascript" src="../../../js/jquery.ztree.exedit-3.5.js"></script>
	-->
	<SCRIPT type="text/javascript">
		<!--
		var zTreeId = "treeDemo";
		var className = "dark";
		var setting = {
			async: {
				enable: true,
				url: "${pageContext.request.contextPath}/mining/miningSort.action?time="+new Date().getTime()
			},
			check: {
				//enable: true,
				autoCheckTrigger: true
			},/*
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn
			},*/
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false,
				fontCss: getFontCss
			},
			callback: {
				onClick: onClick,
				beforeExpand: beforeExpand/*,
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError*/
			},
			async: true,
			asyncUrl: "${pageContext.request.contextPath}/mining/miningSort.action",
			asyncParam: ["name","id","pid","open"]  //获取节点数据时，必须的数据名称，例如：id、name	
		};
		var zNodes =[
			{ id:1, pId:0, name:"标的物管理", open:true},
			{ id:2, pId:1, name:"电子产品", open:true},
			{ id:3, pId:1, name:"手机", open:true},
			{ id:4, pId:3, name:"三星", open:true},
		];
		function beforeExpand(treeId, treeNode) {
			if (!treeNode.isAjaxing) {
				startTime = new Date();
				treeNode.times = 1;
				//ajaxGetNodes(treeNode, "refresh");
				return true;
			} else {
				alert("zTree 正在下载数据中，请稍后展开节点。。。");
				return false;
			}
		}
		function ajaxGetNodes(treeNode, reloadType) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			if (reloadType == "refresh") {
				treeNode.icon = "${pageContext.request.contextPath}/css/zTreeStyle/img/loading.gif";
				zTree.updateNode(treeNode);
			}
			zTree.reAsyncChildNodes(treeNode, reloadType, true);
		}
		//树checked点击事件
		function onClick(e, treeId, treeNode) {
			sortId = treeNode.id;
			$("#sortId").val(sortId);
			begin(sortId);
		}
		
		function loadData(){
			$.ajax({
		       url: "${pageContext.request.contextPath}/mining/miningSort.action?time="+new Date().getTime(),
				type:"get",
				data:{},
		        async: false,
				dataType:"json",
		        success: function(data){
		        	zNodes = data;
		        	initZtree();
				},error:function(a){alert("加载分类数据失败！");}
			});
		}
		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			if (type=="root") {
				$("#rMenu").hide();
				$("#m_del").hide();
				$("#m_set").hide();
				$("#m_add").hide();
			} else {
				$("#rMenu").show();
				$("#m_del").show();
				$("#m_set").show();
				$("#m_add").show();
			}
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

			$("body").bind("mousedown", onBodyMouseDown);
		}
		function hideRMenu() {
			if (rMenu) rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
		}
		var addCount = 1;
		 
		function zTreeBeforeAsync(treeId, treeNode) {
			
		}
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			
		}
		function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
		var zTree,rMenu;
		$(document).ready(function(){
			loadData();
			begin("");
		});
		function initZtree(){
			zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			//zTree = $.fn.zTree.init($("#treeDemo"), setting);
			rMenu = $("#rMenu");
		}
		
		var sortId=0;
		 		 
		function getMining(miningPriceId){
	     $.ajax({
	         url: "${pageContext.request.contextPath}/mining/getMiningPrice.action",
			 type:"POST",
			 data:{
				'id':miningPriceId
			 },
	         async: false,
			 dataType:"json",
	         success: function(data){
	         	$("#miningId").val(miningPriceId);
	         	$("#documentNo").val(data.documentNo);
	         	$("#miningAddress").val(data.miningAddress);
	         	$("input[name='miningPriceInfo.status'][value="+data.status+"]").attr("checked",'checked');
	         	$("#referenceDate").val(data.referenceDate);
	         	$("#miningDate").val(data.miningDate);
	         	$("#miningPrice").val(data.miningPrice);
	         	$("#miningSortName").val(data.sortName);
	         	$("#typeName").val(data.typeName);
	         	$("#guidePrice").val(data.guidePrice);
	         	//$("#userId").find("option[value="+data.userId+"]").attr("selected",true);
	         	$("#userId").val(data.userId);
	         	//
	         	openMiningDialog("修改采价");
			},
			error:function(e){
				alert("获取采价信息失败，请稍候重试！");
				//alert("采价新增发生错误！"+JSON.stringify(e));
			}
	     });
		}
		
		function begin(sortId) {
			$.ajax({
            	url: "${pageContext.request.contextPath}/aprisl/miningPriceList.action?mid="+document.getElementById("mid").value+"&id="+sortId,
            	cache:false,
            	dataType:"html",
            	data:{
            	"time":new Date().getTime(),
            	"sortId":sortId
            	},
            	success: function(html){
            	$("#tagContent0").html('').html(html);
            	}
        	});
		}
		
		function focusKey(e) {
			if (key.hasClass("empty")) {
				key.removeClass("empty");
			}
		}
		function blurKey(e) {
			if (key.get(0).value === "") {
				key.addClass("empty");
			}
		}
		var lastValue = "", nodeList = [], fontCss = {};
		function clickRadio(e) {
			lastValue = "";
			searchNode(e);
		}
		function searchNode(e) {
			//alert("searchNode:" + $.fn.zTree);
			zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			//zTree.expandAll(false);//关闭所有节点
			var value = $.trim(key.get(0).value);
			var keyType = "name";
			
			if (key.hasClass("empty")) {
				value = "";
			}
			if (lastValue === value) return;
			lastValue = value;
			if (value === "") return;
			updateNodes(false);
			nodeList = zTree.getNodesByParamFuzzy(keyType, value);
			updateNodes(true);
		}
		
		function updateNodes(highlight) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			for( var i=0, l=nodeList.length; i<l; i++) {
				nodeList[i].highlight = highlight;
				
				var node_ = nodeList[i].getParentNode();
				zTree.expandNode(node_, true, null, null, false);
				
				zTree.updateNode(nodeList[i]);
			}
		}
		
		function getFontCss(treeId, treeNode) {
			return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
		}
		function filter(node) {
			return !node.isParent && node.isFirstNode;
		}
		
		var key;
		$(document).ready(function(){
			//$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			key = $("#key");
			key.bind("focus", focusKey)
			.bind("blur", blurKey)
			.bind("propertychange", searchNode)
			.bind("input", searchNode);
			$("#name").bind("change", clickRadio);
		
			$("#getNodesByParamFuzzy").bind("change", clickRadio);
			
		});
	
	
		//-->
	</SCRIPT>
<style type="text/css">
div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
div#rMenu ul li{
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
}
</style>
</head>
<body>
<div>
<input type="hidden" value="${mid }" id="mid"/>
<table style="width: 100%;height: 600px">
<tr>
<td width="300px" valign="top">
	
		目录名称：<input type="text" id="key" value="" class="empty" />
	
<div id="treeDemo" class="ztree" style="width: 300px;height: 450px;overflow-y:auto;"></div>
</td>

<td style="height: 100%" valign="top">
	<div class="box3-tc" id="searchCloseDiv">
		<form action="${pageContext.request.contextPath}/aprisl/miningPriceList.action" method="post" name="searchForm" id="searchForm">
				<s:hidden id="sortId" name="id"/>
				<table style="width: 95%">
					<tr>
						<th>采价日期：</th>
						<td>
							<input name="beginTime" id="beginTime" value="<s:date name="beginTime" format="yyyy-MM-dd"/>"  size="11"/>
						    <img onclick="WdatePicker({el:'beginTime',readOnly:false,maxDate: '#F{$dp.$D(\'endTime\',{d:0})}'});" 
						    src="${pageContext.request.contextPath}/images/ril.png" width="16" height="16" border="0" /> 
						    	至  
						    <input name="endTime" id="endTime" value="<s:date name="endTime" format="yyyy-MM-dd"/>"  size="11"/>
						    <img onclick="WdatePicker({el:'endTime',readOnly:false,minDate: '#F{$dp.$D(\'beginTime\',{d:0})}'});" 
						    src="${pageContext.request.contextPath}/images/ril.png" width="16" height="16" border="0" />
						</td>
						<th>采价人：</th>
						<td>
							<s:select cssClass="validate[required]" id="userId" name="userId" list="#request.userList" listKey="id" listValue="name" headerKey="" headerValue="全部"></s:select>
						</td>
						<th>采价地点:</th>
						<td>
						<s:textfield name="address" id="address" cssClass="input1"></s:textfield>
						</td>
					</tr>
					<tr>
						<th>分类（品名）:</th>
						<td>
							<s:textfield name="name" id="name" cssClass="input1"></s:textfield>
						</td>
						<td>
							<div class="buttomk1">
								<input type="button" value="确认" class="anniu" onclick="javascript:getPrcList();"/>
								<input type="button" value="取消" class="anniu" onclick="javascript:closeBut();"/>
								<input name="button" id="searchButton" type="button"  class="anniu"  value="查询" onclick="javascript:searchPrc();"/>
							</div>
						</td>
						<td colspan="3">&nbsp;</td>
					</tr>
				</table>
		</form>
	</div>
	<div class="clear"></div>
	<div id="tagContent0"></div>
</td>

</tr>
</table>
</div>
</body>
</HTML>