<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>价格库管理</title>

<jsp:include page="/jsp/included/all_css.jsp"></jsp:include>
<jsp:include page="/jsp/included/all_js.jsp"></jsp:include>


<link rel="stylesheet" href="${pageContext.request.contextPath}/css/demo.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.exhide-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.exedit-3.5.js"></script>


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
				//removeHoverDom: removeHoverDom,
				//addDiyDom: addDiyDom
			},
			callback: {
				onClick: onClick,
				onRightClick: OnRightClick,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename,
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
			sortName = treeNode.name;
			initMinPrc(sortId, sortName);
			//loadMiningList(treeNode.id);
		}
		//右键事件
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
		}
		function showRemoveBtn(treeId, treeNode) {
			return !treeNode.isFirstNode;
		}
		function showRenameBtn(treeId, treeNode) {
			return !treeNode.isLastNode;
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
		function addTreeNodeDialog(){
			$("#sort_dialog").dialog(
			{
				hide:true, //点击关闭是隐藏,如果不加这项,关闭弹窗后再点就会出错.
				modal:true,
				draggable:true,
				width:200,
				height:150,
				title:"创建分类",
				dialogClass:"my-dialog"
			});
		}
		function addTreeNode() {
			//隐藏右键菜单
			hideRMenu();
			//隐藏弹出菜单
			$("#sort_dialog").dialog('close');//关闭弹出窗
			var sortName = $("#sortName").val();
			$("#sortName").val("");
			//创建分类
			$.ajax({
		       url: "${pageContext.request.contextPath}/mining/addMiningSort.action",
				type:"post",
				data:{
					"miningPriceSort.sortName":sortName,
					"miningPriceSort.TMiningPriceSort.id":zTree.getSelectedNodes()[0].id,
					"miningPriceSort.sortLevel":parseInt(zTree.getSelectedNodes()[0].level)+1
				},
		        async: false,
				dataType:"json",
		        success: function(data){
		        	if(data.result == 'success'){//创建分类成功
						var newNode = {id:data.obj.id,pId:data.obj.parentId,name:data.obj.sortName};
						if (zTree.getSelectedNodes()[0]) {
							newNode.checked = zTree.getSelectedNodes()[0].checked;
							zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
						} else {
							zTree.addNodes(null, newNode);
						}
					}else if(data == '1'){
		        		alert("物品分类已经存在！");	
					}else{//
						alert("创建物品分类失败!");
					}
				},error:function(a){alert("创建物品分类失败！");}
			});
		}
		function removeTreeNode() {
			hideRMenu();
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length>0) {
				if (nodes[0].children && nodes[0].children.length > 0) {
					alert("要删除的节点是父分类，需要先删除子分类！");
					//var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
					//if (confirm(msg)==true){
					//	zTree.removeNode(nodes[0]);
					//}
				} else {//删除分类
					$.getJSON("${pageContext.request.contextPath}/mining/getMiningCount.action", { id: nodes[0].id }, function(json){
					  //alert("JSON Data: " + json.result+" == "+json.count);
					  if(json.result == 'success'){
					  	if(json.count == 0){
							var msg = "请确认是否要删除该分类！";
							if (confirm(msg)==true){
								$.ajax({
							       url: "${pageContext.request.contextPath}/mining/removeMiningSort.action",
									type:"post",
									data:{
										"miningPriceSort.id":nodes[0].id
									},
							        async: false,
									dataType:"text",
							        success: function(data){
							        	if(data == 'success'){
											zTree.removeNode(nodes[0]);
										}else{//
											alert("删除物品分类失败！");
										}
									},error:function(a){alert("删除物品分类失败！");}
								});
							}
					  	}else{
					  		alert("请删除分类下的采价信息，再删除分类！");
					  	}
					  }else{
					  	alert("获取分类下采价个数失败，请稍候重试！");
					  }
					});
				}
			}
		}
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
			initMinPrc("");
		});
		function initZtree(){
			zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			//zTree = $.fn.zTree.init($("#treeDemo"), setting);
			rMenu = $("#rMenu");
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			//showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		}
		function onRemove(e, treeId, treeNode) {
			//showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		}
		function onRename(e, treeId, treeNode, isCancel) {
			//alert("修改成功："+treeNode.name);
			//创建分类
			$.ajax({
		       url: "${pageContext.request.contextPath}/mining/renameMiningSort.action",
				type:"post",
				data:{
					"miningPriceSort.id":treeNode.id,
					"miningPriceSort.sortName":treeNode.name
				},
		        async: false,
				dataType:"text",
		        success: function(data){
		        	if(data == 'success'){
		        		
		        	}else if(data == '1'){
		        		treeNode.name = $("#treeNodeNameBak").val();
		        		alert("物品分类已经存在！");
					}else{//
						treeNode.name = $("#treeNodeNameBak").val();
						alert("修改物品分类失败！");
					}
				},error:function(a){alert("修改物品分类失败！");}
			});
			//showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode);}, 10);
				return false;
			}
			return true;
		}
		function edit() {
			hideRMenu();
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes(),
			treeNode = nodes[0];
			if (nodes.length == 0) {
				alert("请先选择一个分类");
				return;
			}
			zTree.editName(treeNode);
			$("#treeNodeNameBak").val(treeNode.name);
		}
		function loadMiningList(sortId){
			initMinPrc(sortId, "0");
			//$("#miningframe").attr("src","${pageContext.request.contextPath}/mining/miningList.action?id="+sortId);
		}
		function closeSortDialog(){
			$("#sort_dialog").dialog('close');//关闭弹出窗
		}
		function openMiningDialog(title,sortName){
			jQuery("#miningForm").validationEngine('hide');
			$("#mining_dialog").dialog(
			{
				hide:true, //点击关闭是隐藏,如果不加这项,关闭弹窗后再点就会出错.
				modal:true,
				draggable:true,
				width:350,
				height:430,
				title:title,
				dialogClass:"my-dialog"
			});
			if(title == '新增采价'){
				//$("#miningSubmit1").attr("onclick","addMining()");
				$("#miningSubmit1").unbind('click')
	         	//$("#miningSubmit1").click();
	         	$("#miningSubmit1").bind('click', function(){addMining();});
	         	$("#miningId").val('');
	         	//$("#documentNo").val('');
	         	$("#miningAddress").val('');
	         	$("input[name='miningPriceInfo.status'][value=1]").attr("checked",'checked');
	         	$("#referenceDate").val('');
	         	$("#miningDate").val('');
	         	$("#miningPrice").val('');
	         	$("#fenlei").text(sortName);
	         	$("#miningSortName").val(sortName);
	         	$("#fenName").val('');
	         	$("#typeName").val('');
	         	$("#guidePrice").val('');
	         	//$("#userId").find("option[value="+data.userId+"]").attr("selected",true);
	         	$("#userId").val('');
			}
			if(title == '修改采价'){
				$("#miningSubmit1").unbind('click')
	         	//$("#miningSubmit1").click();
	         	$("#miningSubmit1").bind('click', function(){updateMining();});
	         	
				//$("#miningSubmit1").attr("onclick","updateMining()");
	         	//$("#miningSubmit1").click();
	         	//$("#miningSubmit1").bind('click', function(){updateMining();});
			}
		}
		function closeMiningDialog(){
			$("#mining_dialog").dialog('close');//关闭弹出窗
		}
		jQuery(document).ready(function(){
		    jQuery("#miningForm").validationEngine();
		});
		var sortId=0; 
		var sortName = '';
		function addMining(){
         if (!jQuery("#miningForm").validationEngine('validate')) {
                return;
         }
	     $.ajax({
	         url: "${pageContext.request.contextPath}/mining/addMiningPrice.action",
			 type:"POST",
			 data:{
				'miningPriceInfo.TMiningPriceSort.id':sortId,
				//'miningPriceInfo.documentNo':$("#documentNo").val(),
				'miningPriceInfo.miningAddress':$("#miningAddress").val(),
				'miningPriceInfo.status':$("input[name='miningPriceInfo.status']:checked").val(),
				'miningPriceInfo.referenceDate':$("#referenceDate").val(),
				'miningPriceInfo.miningDate':$("#miningDate").val(),
				'miningPriceInfo.miningPrice':$("#miningPrice").val(),
				'miningPriceInfo.TUserInfo.id':$("#userId").val(),
				'miningPriceInfo.sortName':$("#miningSortName").val(),
				'miningPriceInfo.name':$("#fenName").val(),
				'miningPriceInfo.typeName':$("#typeName").val(),
				'miningPriceInfo.guidePrice':$("#guidePrice").val()
				},
	         async: false,
	         //cache : false,
			 dataType:"text",
	         success: function(data){
	        	if(!checkSession(data,'${pageContext.request.contextPath}','html')){
			        	return;
			    };
	 			if(data == 'success'){//成功
	 				closeMiningDialog();
	 	 			alert("采价新增成功！");
	 	 			initMinPrc(sortId, "0");
					//$("#miningframe").attr("src","${pageContext.request.contextPath}/mining/miningList.action?id="+sortId);
	  			}else{//失败
	  			    alert(data);
	  			}
			},
			error:function(e){
				alert("采价新增发生错误，系统内部异常，请稍候重试！");
				//alert("采价新增发生错误！"+JSON.stringify(e));
			}
	     });
		}
		function updateMining(){
         if (!jQuery("#miningForm").validationEngine('validate')) {
                return;
         }
	     $.ajax({
	         url: "${pageContext.request.contextPath}/mining/updateMiningPrice.action",
			 type:"POST",
			 data:{
				'miningPriceInfo.id':$("#miningId").val(),
				//'miningPriceInfo.documentNo':$("#documentNo").val(),
				'miningPriceInfo.miningAddress':$("#miningAddress").val(),
				'miningPriceInfo.status':$("input[name='miningPriceInfo.status']:checked").val(),
				'miningPriceInfo.referenceDate':$("#referenceDate").val(),
				'miningPriceInfo.miningDate':$("#miningDate").val(),
				'miningPriceInfo.miningPrice':$("#miningPrice").val(),
				'miningPriceInfo.TUserInfo.id':$("#userId").val(),
				'miningPriceInfo.sortName':$("#miningSortName").val(),
				'miningPriceInfo.name':$("#fenName").val(),
				'miningPriceInfo.typeName':$("#typeName").val(),
				'miningPriceInfo.guidePrice':$("#guidePrice").val()
				},
	         async: false,
	         //cache : false,
			 dataType:"text",
	         success: function(data){
	 			if(data == 'success'){//成功
	 				closeMiningDialog();
	 	 			alert("采价修改成功！");
	 	 			//$("#miningframe").attr("src","${pageContext.request.contextPath}/mining/miningList.action?id="+sortId);
	 	 			initMinPrc(sortId, "0");
	  			}else{//失败
	  			    alert(data);
	  			}
			},
			error:function(e){
				alert("采价修改发生错误，系统内部异常，请稍候重试！");
				//alert("采价新增发生错误！"+JSON.stringify(e));
			}
	     });
		}
		
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
	         	//$("#documentNo").val(data.documentNo);
	         	$("#miningAddress").val(data.miningAddress);
	         	$("input[name='miningPriceInfo.status'][value="+data.status+"]").attr("checked",'checked');
	         	$("#referenceDate").val(data.referenceDate);
	         	$("#miningDate").val(data.miningDate);
	         	$("#miningPrice").val(data.miningPrice);
	         	$("#fenlei").text(data.sortName);
	         	$("#miningSortName").val(data.sortName);
	         	$("#fenName").val(data.name);
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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getNodes();
			//zTree.expandAll(false);//关闭所有节点
			//zTree = $.fn.zTree.init($("#treeDemo"), setting, nodes);
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
				//zTree.expandAll(true);
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
		});
		
		function initMinPrc(sortId, sortName) {
			if(sortName!="0"){
				$("#sortName_").val(sortName);
			}
			$("#sortId_").val(sortId);
			$.ajax({
            	url: "${pageContext.request.contextPath}/mining/miningList.action?"+"&id="+sortId,
            	cache:false,
            	dataType:"html",
            	data:{
	            	"time":new Date().getTime(),
	            	"sortId":sortId
            	},
            	success: function(html){
            		checkSession(html,'${pageContext.request.contextPath}','html');
            		$("#tagContent0").html('').html(html);
            	}
        	});
		}
		
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
<h3>价格库管理</h3>
<div id="rMenu">
	<ul>
		<li id="m_add" onclick="addTreeNodeDialog();">增加分类</li>
		<li id="m_set" onclick="edit();">修改名称</li>
		<li id="m_del" onclick="removeTreeNode();">删除</li>
	</ul>
</div>
<div id="sort_dialog" style="text-align: center;display: none;position:relative;">
<ul>
<li>分类名称：<input type="text" id="sortName" style="width: 100px"></li>
<li style="padding-top: 20px">
<input  type="button" value="提交" class="anniu" onclick="addTreeNode();" />
<input  type="button" value="取消" class="anniu" onclick="closeSortDialog();" />
</li>
</ul>
</div>
	<div id="mining_dialog" style="text-align: center;display: none;" valign="top">
		<s:hidden id="treeNodeNameBak" name="treeNodeNameBak"/>
		<form id="miningForm" action="" method="post">
		<input id="miningId" name="miningPriceInfo.id" type="hidden">
		<input type="hidden"  id="miningSortName" name="miningPriceInfo.sortName">
			<table>
				<tr><th style="text-align: right;height: 30px">目录名称：</th><td id="fenlei"></td></tr>
				<tr><th style="text-align: right;height: 30px">分类（品名）：</th><td><input type="text" id="fenName" name="miningPriceInfo.name"></td></tr>
				<tr><th style="text-align: right;height: 30px">型号（类型）：</th><td><input type="text" id="typeName" name="miningPriceInfo.typeName"></td></tr>
				<tr><th style="text-align: right;height: 30px">厂家指导价：</th><td><input class="validate[maxSize[1000]]" type="text" id="guidePrice" name="miningPriceInfo.guidePrice"></td></tr>
				<tr><th style="text-align: right;height: 30px"><span style="color:red">*</span>经销商价格：</th><td><input class="validate[required,maxSize[1000]]" type="text" id="miningPrice" name="miningPriceInfo.miningPrice"></td></tr>
				<tr><th style="text-align: right;height: 30px">采价地点：</th><td><input type="text" id="miningAddress" name="miningPriceInfo.miningAddress"></td></tr>
				<tr>
					<th style="text-align: right;height: 30px">状态：</th><td style="text-align: left;">
					<input type="radio" name="miningPriceInfo.status" value="2" id="status_2"><label for="status_2">退市</label>
					<input type="radio" checked="checked" name="miningPriceInfo.status" value="1" id="status_1"><label for="status_1">正常</label>
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px">价格基准日：</th><td style="text-align: left;">
					<input class="validate[custom[date]]" type="text" id="referenceDate" name="miningPriceInfo.referenceDate" style="width: 100px">
					<img onclick="WdatePicker({el:'referenceDate',readOnly:false});" src="${pageContext.request.contextPath}/images/ril.png" width="16" height="16" border="0" /> 
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px"><span style="color:red">*</span>采价日：</th><td style="text-align: left;">
					<input class="validate[required,custom[date]]" type="text" id="miningDate" name="miningPriceInfo.miningDate" style="width: 100px">
					<img onclick="WdatePicker({el:'miningDate',readOnly:false});" src="${pageContext.request.contextPath}/images/ril.png" width="16" height="16" border="0" /> 
					</td>
				</tr>
				<tr>
					<th style="text-align: right;height: 30px">采价人：</th><td style="text-align: left;">
					<s:select id="userId" name="miningPriceInfo.TUserInfo.id" list="#request.userList" listKey="id" listValue="name" headerKey="" headerValue="请选择"></s:select>
					</td>
				</tr>
				<tr><td colspan="2" style="height: 50px">
					<input type="button" value="提交" class="anniu" id="miningSubmit1"/>
					<input type="button" value="取消" class="anniu" onclick="closeMiningDialog();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<table style="width: 100%;height: 600px">
		<tr>
			<td width="300px" valign="top">
			标的物目录：<input type="text" id="key" value="" class="empty" />
			<div id="treeDemo" class="ztree" style="width: 300px;height: 450px;overflow-y:auto;"></div>
			</td>
			<td style="height: 100%" valign="top">
				<div class="box3-tc" id="searchCloseDiv">
						<form action="${pageContext.request.contextPath}/mining/miningList.action" method="post" name="searchForm" id="searchForm">
								<s:hidden name="id" id="sortId_"/>
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
										<td colspan="4">
											<div class="buttomk1">
											<input type="hidden" name="sortName_" id="sortName_" value=""/>
											<input  name="button" id="searchButton" type="button"  class="anniu"  value="查询" onclick="javascript:searchMin();"/>
										 	 <input buttonAction="add" name="button2" type="button" class="anniu" id="button" value="新增采价" onclick="javascript:openAddMiningDlg();">
										 	 <input buttonAction="add" name="button2" type="button" class="anniu" id="button" value="采价导入" onclick="javascript:impMinings();">
										</div>
										</td>
									</tr>
								</table>
						</form>
					</div>
					<div class="clear"></div>
					<div id="tagContent0"></div>
			</td>
		</tr>
	</table>
</body>
</HTML>