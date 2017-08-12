$(function() {
	
	
	/*** 全局变量 ***/
	
	/**
	 * 对应项目对象
	 */
	var faceProjectObj = null;
	
	/**
	 * 父窗口对象
	 */
	var parentWindow = window.parent;
	
	/**
	 * 页面加载完毕后得到服务地点内容
	 */
	var loadServAddr = $(this).children("option:selected").val();
	if(loadServAddr != "" && typeof(loadServAddr) != "undefined") {
		servAddrComplete(servAddr);
	}
	
	
	
	/***  维修主信息 ***/
	
	
	/**
	 * 服务地点改变事件
	 */
	$("#repairReportServAddr").change(function() {
		var servAddr = $(this).children("option:selected").val();
		if(servAddr == "") {
			return false;
		}
		
		servAddrComplete(servAddr);
	});
	
	/***  维修项目 ***/
	
	/**
	 * 维修项目信息  -- 添加维修项目信息
	 */
	$("#repairItemAdd").click(function() {
	
		var maintenanceList = window.showModalDialog('maintenanceList.action', window, 
				"dialogHeight:600px; dialogWidth:800px; center:yes; help:no; resizable:no; status:no; location:no;");
		
		if(maintenanceList == null || typeof(maintenanceList) == "undefinded") {
			return false;
		}
		
		var count = maintenanceList.length;
		var tableObj = $("#showMaintenanceTable");
		var combineContent = "", currentNo = 0, index = 0;
		
		if(tableObj.find("tr").length > 1) {
			currentNo = tableObj.find("tr:last").children().eq(1).html();
			currentNo = Number(currentNo);
		}
		
		for(var i = 0; i<count; i = i + 1) {
			
			var list = maintenanceList[i];
			index = currentNo + i + 1;
			
			//判断是否重复添加.如果重复添加则绕过
			var notFirstTr = tableObj.find("tr:not(:first)");
			var prjNo = list.code;
			var isAdd = true;
			if(notFirstTr != null) {
				notFirstTr.each(function() {
					var oldPrjNo = $(this).children().eq(2).find("input").val();
					if(prjNo == oldPrjNo) {
						isAdd = false;
					}
				});
				
			}
			
			if(isAdd) {
				var prjUnit = list.prjUnit;
				var numLimit = Number(list.numLimt);
				if(isNaN(numLimit)) {
					numLimit = 0;
				}
				
				var appendSelect = "";
				
				if(numLimit > 0) {
					appendSelect = "<select name='repairItemList[" + (index - 1) + "].addedArrt' class='addedAttrSelect zselect'";
					for(var j = 0; j < numLimit; j = j + 1) {
						appendSelect += ("<option value='" + (j + 1) + "'>" + (j + 1) + prjUnit + "</option>");
					}
					appendSelect += "</select>";
				}
				
				combineContent = "<tr>" + 
									"<td><input class='addMaintenance' type='checkbox' /></td>" + 
									"<td>" + index + "</td>" + 
									"<td><input type='hidden' class='prjNos' name='repairItemList[" + (index - 1) + "].itemCode' value='" + prjNo + "'/>" + prjNo + "</td>" + 
									"<td><input type='hidden' name='repairItemList[" + (index - 1) + "].itemName' value='" + list.name + "'/>" + list.name + "</td>" + 
									"<td>" + appendSelect + "</td>" + 
									"<td><input type='hidden' class='prjHours' name='repairItemList[" + (index - 1) + "].hours' value='" + list.hours + "'/><span style='color: null'>" + list.hours + "<span></td>" + 
									"<td class='addDutyTd'><input type='hidden' class='textareaContent' /><textarea class='kktextarea' style='height: 37px; width: 264px;' rows='2' cols='20'></textarea><input name='repairItemResps' type='hidden' /></td>" + 
									"<td><a href='javascript:void(0);' class='addRepairExchangeDetailA'>添加换件明细</a><input type='hidden' value='" + list.id + "' />" +
										"<input type='hidden' name='repairItemList[" + (index - 1) + "].unit' value=" + prjUnit + "/>" + 
										"<input type='hidden' name='repairItemList[" + (index - 1) + "].numLimit' value=" + numLimit + "/>" +
									"</td>" + 
								 "</tr>";
				tableObj.append(combineContent);
				
				appendSelect = "";
				combineContent = "";
				
			}
			
		}

		
	});
	
	/**
	 * 维修项目信息  -- 附加属性改变事件
	 */
	$("select[class^='addedAttrSelect']").live("change", function() {
		var vals = $(this).children("option:selected").html();
		var currentHour = vals.charAt(0);
		var nextObj = $(this).parent().next();
		nextObj.find("input").val(currentHour);
		nextObj.find("span").html(currentHour);
		
		//重新计算
		calculateForRepairReport();
	});
	
	/**
	 * 维修项目信息  -- 删除维修项目信息
	 */
	$("#repairItemDel").click(function() {
		var maintenance = $("input[class='addMaintenance']");
		if(maintenance != null && maintenance.length > 0) {
			maintenance.each(function() {
				if($(this).is(":checked")) {
					$(this).parent().parent().remove();
					if($("#showMaintenanceTable").find("tr").length == 1) {
						$("#maintenanceMainCheckbox").removeAttr("checked");
						$("#showMaintenanceTable").hide();
					}
				}
			});
		} else {
			alert("请先选择将要删除的项目");
		}
	});

	
	/**
	 * 维修项目中全选checkbox
	 */
	$("#maintenanceMainCheckbox").click(function() {
		if($(this).is(":checked")) {
			$(this).attr("checked", "true");
			$("input[class='addMaintenance']").each(function() {
				$(this).attr("checked", "true");
			});
			
		} else {
			$(this).removeAttr("checked");
			$("input[class='addMaintenance']").each(function() {
				$(this).removeAttr("checked");
			});
		}
	});
	
	
	/***  责任认定 ***/
	
	/**
	 * 添加责任认定
	 */
	$("td[class='addDutyTd'] textarea").live("focus", function() {
		
		var repairItemId = $(this).parent().parent().find("td").eq(2).find("input").val();
		var dutyIds = $(this).next().val();
		var contextPath = document.location.pathname;
		contextPath = contextPath.substring(1);
		contextPath = contextPath.substring(0, contextPath.indexOf("/") + 1);
		
		var dutys = window.showModalDialog("/" + contextPath + "/auditticket/queryRespHoldListAction.action?respJson=" + dutyIds, window, 
					"dialogHeight:800px; dialogWidth:800px; center:yes; help:no; resizable:no; status:no; location:no;");
		
		if(dutys == null || typeof(dutys) == "undefined") {
			return false;
		}
		
		var names = "", returnArray = new Array();
		
		var respContent = repairItemId + "_";
		for(var i = 0; i < dutys.length; i = i + 1) {
			var dutyObj = {};
			var duty = dutys[i];
			names = names + duty.name + "、";
			dutyObj["respId"] = duty.code;
			dutyObj["respName"] = duty.name;
			returnArray.push(dutyObj);
			
			respContent += (duty.code + ";");
		}
		
		alert("责任认定内容："  + respContent);
		names = names.substring(0, names.length - 1);
		$(this).prev().val(names);
		$(this).next().val(respContent);
		$(this).val(names);
		
	});
	
	
	/***  换件明细 ***/
	
	
	/**
	 * 预换件明细 -- （旧件-换件）
	 */
	$("#oldPartChange").click(function() {
		var engineSeries = $("#exchangeDetailEngineSeries").val();
		var partsNo = window.showModalDialog("oldPartChange.action?engineSeries=" + engineSeries, "", "height=800px,width=2000px,status=yes,toolbar=no,menubar=no,location=no");
		
		if(partsNo == "" || typeof(partsNo) == "undefined") {
			return false;
		}
		
		partChangeProcess(partsNo, true);
	
	});
	
	/**
	 * 预换件明细 -- （旧件-换件） 除数据删
	 */
	$("a[class='delPartsMainDataA']").live("click", function() {
		var trObj = $(this).parent().parent();
		trObj.next().remove();
		trObj.remove();
	});
	
	
	/**
	 * 配件编号改变事件
	 */
	$("td[class='partsNoTd'] input").live("change", function() {
		var partsNo = $(this).val();
		var partsCount = $("td[class='accountTd'] input").val();
		var supplierCode = $("td[class='factoryCodesTd'] select").children("option:selected").val();
		$(this).parent().nextAll("td[class='exchangeAmount']").html(findComputePartsCost(partsNo, partsCount, supplierCode));
	});
	
	/**
	 * 数量改变事件
	 */
	$("td[class='countTd'] input").live("change", function() {
		var partsNo = $("td[class='partsNoTd'] input").val();
		var partsCount = $(this).val();
		var supplierCode = $("td[class='factoryCodesTd'] select").children("option:selected").val();
		$(this).parent().nextAll("td[class='exchangeAmount']").html(findComputePartsCost(partsNo, partsCount, supplierCode));
	});
	
	/**
	 * 供应商编码改变事件
	 */
	$("td[class='factoryCodesTd'] select").live("change", function() {
		var partsNo = $("td[class='partsNoTd'] input").val();
		var partsCount = $("td[class='accountTd'] input").val();
		var supplierCode = $(this).children("option:selected").val();
		$(this).parent().nextAll("td[class='exchangeAmount']").html(findComputePartsCost(partsNo, partsCount, supplierCode));
	});
	
	
	/**
	 * 预换件明细 -- （旧件-未换件）
	 */
	$("#oldPartNotChange").click(function() {
		var engineSeries = $("#exchangeDetailEngineSeries").val();
		var partsNo = window.showModalDialog("oldPartNotChange.action?engineSeries=" + engineSeries, "", "height=800,width=2000px,status=yes,toolbar=no,menubar=no,location=no");
		
		if(partsNo == "" || typeof(partsNo) == "undefined") {
			return false;
		}
		
		partChangeProcess(partsNo, false);
		
	});
	
	/**
	 * 预换件明细 -- （旧件-未换件） 除数据删
	 */
	$("a[class='delPartsMainDataNotA']").live("click", function() {
		$(this).parent().parent().remove();
	});
	
	/**
	 * 配件编号改变事件
	 */
	$("td[class='partsNoNotTd'] input").live("change", function() {
		var partsNo = $(this).val();
		var partsCount = $("td[class='countNotTd'] input").val();
		var supplierCode = $("td[class='factoryCodesNotTd'] select").children("option:selected").val();
		$(this).parent().nextAll("td[class='exchangeAmountNot']").html(findComputePartsCost(partsNo, partsCount, supplierCode));
	});
	
	/**
	 * 数量改变事件
	 */
	$("td[class='countNotTd'] input").live("change", function() {
		var partsNo = $("td[class='partsNoNotTd'] input").val();
		var partsCount = $(this).val();
		var supplierCode = $("td[class='factoryCodesNotTd'] select").children("option:selected").val();
		$(this).parent().nextAll("td[class='exchangeAmountNot']").html(findComputePartsCost(partsNo, partsCount, supplierCode));
	});
	
	/**
	 * 供应商编码改变事件
	 */
	$("td[class='factoryCodesTd'] select").live("change", function() {
		var partsNo = $("td[class='partsNoNotTd'] input").val();
		var partsCount = $("td[class='countNotTd'] input").val();
		var supplierCode = $(this).children("option:selected").val();
		$(this).parent().nextAll("td[class='exchangeAmountNot']").html(findComputePartsCost(partsNo, partsCount, supplierCode));
	});
	
	/**
	 * 添加换件明细
	 */
	$("a[class='addRepairExchangeDetailA']").live("click", function() {
		var engineNo = $("#repairReportEngineNo").val();
		if(engineNo == "") {      
			return false;
		}
		
		var engineSeries = $("#repairReportEngineSeries").val();
		var thisTds = $(this).parent().parent().find("td");
		var proNo = thisTds.eq(2).find("input").val();
		var proName = thisTds.eq(3).find("input").val();

		//--TODO test
		////// test begin //////////////////////////////////////////////////////////////////
		engineSeries = "1";
		////// test end //////////////////////////////////////////////////////////////////
		
		if(engineSeries == "" || typeof(engineSeries) == "undefined") {
			return false;
		}
		
		var partsMainData = window.showModalDialog("exchangeDetail.action?engineSeries=" + engineSeries, window, "dialogHeight:600px; dialogWidth:1000px; center:yes; help:no; resizable:no; status:no; location:no;");
		
		if(partsMainData == "" || typeof(partsMainData) == "undefined") {
			return false;
		}
		
		var content = "";
		var dataSize = partsMainData.length - 1; //
		var count = 1;
		if($("#saveExchangeDetailTable").find("tr").length > 1) {
			var currentCount = Number($("#saveExchangeDetailTable").find("tr:last td:first").html());
			if(!isNaN(currentCount)) {
				count = currentCount + 1;
			}
		}

		for(var i = 0; i < dataSize; i = i + 1) {
			var detail = partsMainData[i];
			var nextDetail = partsMainData[i + 1];
			var rowIndex = detail.rowIndex;
			var nextRowIndex = nextDetail.rowIndex;

			if(rowIndex == nextRowIndex) {
				content = "<tr><td rowspan='2'>" + count + "</td>" + addRepairExchangeDetailStr(false, count - 1, proNo, proName, detail, "1") + "<td rowspan='2'><a href='javascript:void(0);' class='partsChangeDetailDelA'>删除</a></td></tr>";
				content += ("<tr><td style='display: none;'>" + count + "</td>" + addRepairExchangeDetailStr(true, count, proNo, proName, detail, "1") + "</tr>");
				i = i + 1;
			} else {
				content = "<tr><td>" + count + "</td>" + addRepairExchangeDetailStr(true, count, proNo, proName, detail, "0") + "<td><a href='javascript:void(0);' class='partsNotChangeDetailDelA'>删除</a></td></tr>";
			}
			count = count + 1;

			$("#saveExchangeDetailTable").append(content);
			$("#saveExchangeDetailTable").show();
		}
		
		//重新计算
		calculateForRepairReport();
		
		//计算总和
		calculateAllForRepairReport(false);
		
	});
	
	/**
	 * 保存维修报告页（旧件-换件） 删除数据
	 */
	$("a[class='partsChangeDetailDelA']").live("click", function() {
		var trObj = $(this).parent().parent();
		var preTrObj = trObj.prev();
		trObj.next().remove();
		trObj.remove();
		if($("#saveExchangeDetailTable").find("tr").length == 1) {
			// hide table ?
		} else {
			var index = Number(preTrObj.children("td:first").html());
			if(isNaN(index)) {
				index = 0;
			}
			preTrObj.nextAll("tr").each(function() {
				index = index + 1;
				$(this).children("td:first").html(index);
			});
		}
		
		//重新计算
		calculateForRepairReport();
		
	});
	
	
	
	/***  维修费用 ***/
	
	
	//--TODO 维修费用  ------------
	
	/**
	 * 添加其他费用信息
	 */
	$("#repairCostAdd").click(function() {
		var trObj = $("#otherCostTemp tr:first").clone();
		var index = findMaxIndexForCost();
		changeCloneTableContent(trObj, index, true);
		
		//在维修费用后面添加其他费用
		
		var otherCostTrobj = $(".otherCostTr:last");
		if(otherCostTrobj.length <= 0) {
			var specialTrObj = $(".specialCostTr:last");
			if(specialTrObj.length <= 0) {
				$("#repairCostContentTable").append(trObj);
			} else {
				specialTrObj.after(trObj);
			}
		} else {
			otherCostTrobj.after(trObj);
		}
		
		//修改第一行其他费用tr的rowspan 和 隐藏与否
		
		var nameTdObj = $(".otherCostTr:first td").eq(1);
		if(nameTdObj.is(":hidden")) {
			nameTdObj.show();
		}
		var rowspanVal = Number(nameTdObj.attr("rowspan"));
		if(isNaN(rowspanVal)) {
			rowspanVal = 0;
		}
		nameTdObj.attr("rowspan", rowspanVal + 1);
		
		//改变otherCostTr最后一行以后的所有索引为本索引依次加一
		trsObj = $(".otherCostTr:last").nextAll();
		changeCloneTableContent(trsObj, forSum(index, 1), false);
		
		//计算总和
		calculateAllForRepairReport(true);
		
	});
	
	/**
	 * 删除其他费用信息
	 */
	$("a[class='otherPircesContentDel']").live("click", function() {
		var delTrObj = $(this).parent().parent();
		var preTrObj = delTrObj.prev();
		
		var rowspanVal = Number($(".otherCostTr:first td").eq(1).attr("rowspan"));
		
		delTrObj.remove();
		
		var trsObj = $(".otherCostTr");
		if(trsObj.length <= 0) {
			//改变otherCostTr最后一行以后的所有索引为本索引依次加一
			changeCloneTableContent($(".specialCostTr"), 0, false);
		} else {
			var nameTdObj = $(".otherCostTr:first td").eq(1);
			nameTdObj.attr("rowspan", rowspanVal - 1);
			if(nameTdObj.is(":hidden")) {
				nameTdObj.show();
			}
			
			var index = Number(preTrObj.children("td:first").html());
			if(isNaN(index)) {
				index = 0;
			} else {
				index = forSum(index, 1);
			}
			
			//改变otherCostTr最后一行以后的所有索引为本索引依次加一
			changeCloneTableContent(preTrObj.nextAll(), index, false);
		}
		
		//计算总和
		calculateAllForRepairReport(true);
	});
	
	/**
	 * 其他费用数量离开焦点事件
	 */
	$("td[class='costCount'] input").live("blur", function() {
		var count = $(this).val();
		var unitPrice = $(this).parent().next().find("input").val();
		
		var amountInput = $(this).parent().next().next().find("input");
		amountInput.val(forQuotient(count, unitPrice));
		amountInput.prev().html(forQuotient(count, unitPrice));
		
		//计算总和
		calculateAllForRepairReport(true);
	});
	
	/**
	 * 其他费用价格离开焦点事件
	 */
	$("td[class='costUnitPrice'] input").live("blur", function() {
		var count = $(this).parent().prev().find("input").val();
		var unitPrice = $(this).val();
		var amountInput = $(this).parent().next().find("input");
		amountInput.val(forQuotient(count, unitPrice));
		amountInput.prev().html(forQuotient(count, unitPrice));
		
		//计算总和
		calculateAllForRepairReport(true);
	});
	
	
	/**
	 * 对应项目
	 */
	$("textarea[class='kktextareaself']").live("click", function(e) {
		
		faceProjectObj = $(this);
		
		if($("#showMaintenanceTable").find("tr").length == 1) {
			alert("请先添加维修项目");
			return false;
		}
		
		var tableClone = $("#showMaintenanceTable").clone();
		tableClone.removeAttr("id");
		var trsObj = tableClone.find("tr");
		
		var oldItemName = faceProjectObj.val();
		var oldItemNames = "";
		trsObj.each(function(rowNum) {
			var tdsObj = $(this).find("td");
			if(rowNum == 0) {
				tdsObj.eq(0).find("input").attr("id", "showReportItemsMain");
			} else {
				tdsObj.eq(0).find("input").attr("class", "showReportItems");
				var tdObj = tdsObj.eq(2);
				tdObj.html(tdObj.find("input").val());
				tdObj = tdsObj.eq(3);
				var itemName = tdObj.find("input").val();
				tdObj.html(itemName);
				tdObj = tdsObj.eq(4);
				tdObj.html(tdObj.find("select").children("option:selected").val());
				tdObj = tdsObj.eq(5);
				tdObj.html(tdObj.find("input").val());
				
				//此处要注意，textarea中的值怎样都获得不了，所以只能先放入一个input中，然后获得。
				tdObj = tdsObj.eq(6);
				tdObj.html(tdObj.find("input[class='textareaContent']").val());
				
				//判断是否已存在
				if(oldItemName != "") {
					oldItemNames = oldItemName.split(",");
					var addCheck = $.inArray(itemName, oldItemNames);
					if(addCheck >= 0) {
						tdsObj.eq(0).find("input").attr("checked", true);
					}
				}
			}
			
			tdsObj.eq(7).remove();
		});
		
		parentWindow.$("#repairItemTempDiv").html("");
		parentWindow.$("#repairItemTempDiv").append("<p style='cursor: pointer'>关闭</p>");
		parentWindow.$("#repairItemTempDiv").append(tableClone);
		
		showDialog("repairItemTempDiv", "dialogOverlay", $("#showMaintenanceTable").innerWidth(), $("#showMaintenanceTable").innerHeight(), e.pageX, e.pageY);
	});
	
	/**
	 * 动态弹出对应项目层维修项目全选
	 */
	parentWindow.$("#showReportItemsMain").live("click", function() {
		if($(this).is(":checked")) {
			$(this).attr("checked", "true");
			parentWindow.$("input[class='showReportItems']").each(function() {
				$(this).attr("checked", "true");
			});
			
		} else {
			parentWindow.$(this).removeAttr("checked");
			parentWindow.$("input[class='showReportItems']").each(function() {
				$(this).removeAttr("checked");
			});
		}
	});
	
	/**
	 * 关闭对应项目层
	 */
	window.parent.$("#repairItemTempDiv p").live("click", function() {
		closeDialog("repairItemTempDiv", "dialogOverlay");
		
		var reportItemNames = "";
		parentWindow.$("input[class='showReportItems']").each(function() {
			if($(this).is(":checked")) {
				var vals = $(this).parent().parent().find("td").eq(3).html();
				if(vals != null && vals != "") {
					reportItemNames = (reportItemNames + vals + ",");
				}
			}
		});
		
		//添加
		faceProjectObj.val(reportItemNames);
		
	});
	
	
	
	
		
	
	/***  故障照片 ***/
	
	
	/**
	 * 上传图片
	 */
	$("#repairReportAddImg").click(function() {
		var fileVal = $("#repairReportFile").val();
		if(fileVal == "") {
			alert("上传图片文件不能为空.");
			return false;
		}
		
		$.ajaxFileUpload({
			url: "updateImgFile.action",
			fileElementId: "repairReportFile",
			dataType: "json",
			data: {"repairNo": $("#repairReportRepairNo").val()},
			success: function(data, status) {
				
				if(data != null) {
					var filenameAndSelfRepairNo = data.filename.split("_");
					var filename = filenameAndSelfRepairNo[0];
					var selfRepairNo = filenameAndSelfRepairNo[1];
					//设置自定义repairNo
					$("#slefRepairNo").val(selfRepairNo);
					
					if(filename != "") {
						var shortFilename = filename.substring(filename.lastIndexOf("/") + 1);
						
						var tableObj = $("#repairReportImgTable");
						var index = 1;
						var content = "";
						var currentIndex = tableObj.find("tr").length;
						var imgDesc = $("#photoDescListSelect").children("option:selected").html();
						var imgCode = $("#photoDescListSelect").children("option:selected").val();
						if(currentIndex > 1) {
							index = Number(tableObj.find("tr:last").children("td:first").html());
							index = index + 1;
						}
						
						content = "<tr>" + 
									"<td>" + index + "</td>" + 
									"<td>" + imgDesc + "</td>" + 
									"<td>" + shortFilename + "<input type='hidden' value='" + filename + "' /></td>" + 
									"<td>" +
										"<a class='repairReportAddImg' href='javascript:void(0);'>查看</a>　<a class='repairReportDelImg' href='javascript:void(0);'>删除</a>" +
										"<input type='hidden' name='fileNames' value=" + filename + '_' + imgCode + '_' + imgDesc + "/>" + 
									"</td>" + 
								 "</tr>";
						
						tableObj.append(content);
						content = "";
						
						$("#repairReportFile").val("");
					}
				}
			},
			error: function(data, status, e) {
				alert(e);
			}
		 });
		
	});
	
	
	/**
	 * 查看上传图片
	 */
	$("a[class='repairReportAddImg']").live("click", function() {
		var filename = $(this).parent().parent().find("td").eq(2).find("input").val();
		window.showModalDialog("showImgFile.action?fileNames=" + filename + "&type=goto", window, 
				"dialogHeight:600px; dialogWidth:1000px; center:yes; help:no; resizable:no; status:no; location:no;");
	});
	
	/**
	 * 删除上传图片
	 */
	$("a[class='repairReportDelImg']").live("click", function() {
		if(confirm("确定删除该图片?")) {
			var delTrObj = $(this).parent().parent();
			if($("#repairReportId").val() != "") {
				var delFileName = $("#delFileNames").val();
				$("#delFileNames").val(delFileName + ";" + $(this).parent().prev().find("input").val());
				delTrObj.remove();
			} else {
				var tableObj = $("#repairReportImgTable");
				var preTrObj = delTrObj.prev();
				$.ajax({
					url:'deleteImgFile.action',
					cache: false,
					async: false,
					data: {"fileName": delTrObj.find("input").val()},
					success:function(data){
						//data == "success"
					}
				});
				delTrObj.remove();
				
				
				if(tableObj.find("tr").length == 1) {
					//$("#repairReportImgTable").hide();
				} else {
					var index = Number(preTrObj.children("td:first").html());
					if(isNaN(index)) {
						index = 0;
					}
					preTrObj.nextAll("tr").each(function() {
						index = index + 1;
						$(this).children("td:first").html(index);
					});
					
				}
			}
		}
	});
	
	
	
	
	
	/***  工具 ***/
	
	
	/**
	 * 服务地点改变方法
	 */
	function servAddrComplete(servAddr) {
		if(servAddr == "01") { //站内
			showOrHide(false, $("#repairReportTrouAddr"), $("#repairReportTravelLine"), $("#repairReportTravelMileage"), $("#repairReportTravelWay"));
			// 维修费用计算
			calculateForRepairReport(true);
			
		} else {
			showOrHide(true, $("#repairReportTrouAddr"), $("#repairReportTravelLine"), $("#repairReportTravelMileage"), $("#repairReportTravelWay"));
			// 维修费用计算
			calculateForRepairReport(true);
		}
	}
	
	/**
	 * 显示或者隐藏(不定参数) true 为显示 false 为隐藏, 第一个是判断,以后的参数为对象
	 */
	function showOrHide(showOrHide) {
		var args = arguments;
		if(args) {
			for(var i=1; i<args.length; i=i+1) {
				var obj = $(args[i]).parent();
				if(showOrHide) {
					obj.show();
					obj.prev().show();
				} else {
					obj.hide();
					obj.prev().hide();
				}
			}
		}
		
	}
	
	/**
	 * 得到活动select
	 */
	function findRenewCodeList(slectName) {
		var renewCodeListSelect = "";
		$.ajax({
			type: "POST", 
			url: "findRenewCodeList.action", 
			async: false,
			success: function(data) {
				var renewCodeList = JSON.parse(data);
				renewCodeListSelect = "<select class='zselect' name='" + slectName + ">";
				for(var i = 0; i < renewCodeList.length; i = i + 1) {
					var renewCode = renewCodeList[i];
					renewCodeListSelect += ("<option value='" + renewCode.theKey + "'>" + renewCode.value + "</option>");
				}
				renewCodeListSelect += "</select>";
			}
		});
		return renewCodeListSelect;
	}
	
	/**
	 * 得到工厂代码
	 */
	function findFactoryCodes(slectName, partsNo) {
		var findFactoryCodesSelect = "";
		$.ajax({
			type: "POST", 
			url: "findFactoryCodes.action", 
			async: false,
			data: {"partsNo" : partsNo},
			success: function(data) {
				var findFactoryCodeList = JSON.parse(data);
				findFactoryCodesSelect = "<select class='zselect' name='" + slectName + "'>";
				for(var i = 0; i < findFactoryCodeList.length; i = i + 1) {
					var findFactoryCode = findFactoryCodeList[i];
					findFactoryCodesSelect += ("<option value='" + findFactoryCode.supplrCode + "'>" + findFactoryCode.supplrCode + "</option>");
				}
				findFactoryCodesSelect += "</select>";
			}
		});
		return findFactoryCodesSelect;
	}
	
	/**
	 * 得到故障信息select
	 */
	function findFailureModes(slectName, partsNo) {
		var findFailureModesSelect = "";
		$.ajax({
			type: "POST", 
			url: "findFailureModes.action", 
			async: false,
			data: {"partsNo" : partsNo},
			success: function(data) {
				if(data != "") {
					var findFailureModeList = JSON.parse(data);
					findFailureModesSelect = "<select class='zselect' name='" + slectName + "'>";
					for(var i = 0; i < findFailureModeList.length; i = i + 1) {
						var findFailureMode = findFailureModeList[i];
						findFailureModesSelect += ("<option value='" + findFailureMode.failCode + "'>" + findFailureMode.failName + "</option>");
					}
					findFailureModesSelect += "</select>";
				}
			}
		});
		return findFailureModesSelect;
	}
	
	/**
	 * 根据配件件号,配件数量,供应商代码得到金额
	 */
	function findComputePartsCost(partsNo, partsCount, supplierCode) {
		var result = "";
		$.ajax({
			type: "POST", 
			url: "findComputePartsCost.action",
			async: false,
			data: {"partsNo" : partsNo, "partsCount" : partsCount, "supplierCode" : supplierCode},
			success: function(data) {
				if(data != "") {
					result = data;
				}
			}
		});
		return result;
	}
	
	/**
	 * 通过配件件号到装机档案中判断是否存在该数据,存在则继续,否则不做任何操作
	 */
	function judgeInstallRecordSubinfos(partsNo) {
		var isGo = true;
		$.ajax({
			type: "POST",
			dataType: "json",
			data: {"partsNo" : partsNo},
			async: false,
			url: "installRecordSubinfos.action",
			success: function(data) {
				if(data != "success") {
					isGo = false;
				}
			}
		});
		return isGo;
	}
	
	/**
	 * 预换件 (旧件-换件 or 旧件-未换件)
	 */
	function partChangeProcess(partsNo, changeOrNot) {
		if(judgeInstallRecordSubinfos()) { //通过配件件号到装机档案中判断是否存在该数据,存在则继续,否则不做任何操作
			$.ajax({
				type: "POST",
				data: {"partsNo" : partsNo},
				async: false,
				url: "findPartsMainData.action",
				error: function(msg) {alert(msg.responseText);},
				success: function(data) {
					if(data != "") {
						var partsMainDataList = JSON.parse(data);
						partsMainData = partsMainDataList[0];
						
						var isAdd = true;
						var contentTrs = $("#exchangeDetailTable").find("tr:not(:first)");
						contentTrs.each(function() {
							var currentPartsNo = $(this).children("td").eq(2).html();
							if(currentPartsNo == partsNo) {
								isAdd = false;
							}
						});
						
						if(isAdd) {
							var renewCodeListSelect = findRenewCodeList("renewCode");
							var findFactoryCodesSelect = findFactoryCodes("factoryCode", partsNo);
							var findFailureModesSelect = findFailureModes("failureMode", partsNo);
							
							var content = "";
							if(changeOrNot) {
								content = "<tr>" + 
											"<td>" + renewCodeListSelect + "</td>" + 
											"<td><input class='zinput' type='text' readonly='readonly' value='" + partsMainData.partsUnitNo + "'/></td>" + 
											"<td>" + partsMainData.partsName + "</td>" + 
											"<td>" + partsMainData.partsUnit + "</td>" + 
											"<td class='countTd'><input class='zinput' type='text' /></td>" + 
											"<td class='partsNoTd'><input class='zinput' type='text' value='" + partsNo + "'/></td>" + 
											"<td class='factoryCodesTd'>" + findFactoryCodesSelect + "</td>" + 
											"<td>" + findFailureModesSelect + "</td>" + 
											"<td><select class='zselect'><option value='0'>是</option><option value='1'>否</option></select></td>" + 
											"<td class='exchangeAmount'></td>" + 
											"<td rowspan='2'><a href='javascript: void(0);' class='delPartsMainDataA'>删除</a></td>" + 
										  "</tr>" + 
										  "<tr class='sontr'>" + 
											"<td>" + renewCodeListSelect + "</td>" + 
											"<td><input class='zinput' type='text' readonly='readonly' value='" + partsMainData.partsUnitNo + "'/></td>" + 
											"<td>" + partsMainData.partsName + "</td>" + 
											"<td>" + partsMainData.partsUnit + "</td>" + 
											"<td class='countTd'><input class='zinput' type='text' /></td>" + 
											"<td class='partsNoTd'><input class='zinput' type='text' value='" + partsNo + "'/></td>" + 
											"<td class='factoryCodesTd'>" + findFactoryCodesSelect + "</td>" + 
											"<td>" + findFailureModesSelect + "</td>" + 
											"<td><select class='zselect'><option value='0'>是</option><option value='1'>否</option></select></td>" + 
											"<td class='exchangeAmount'></td>" + 
										  "</tr>";
							} else {
								content = "<tr>" + 
											"<td>" + renewCodeListSelect + "</td>" + 
											"<td><input class='zinput' type='text' readonly='readonly' value='" + partsMainData.partsUnitNo + "'/></td>" + 
											"<td>" + partsMainData.partsName + "</td>" + 
											"<td>" + partsMainData.partsUnit + "</td>" + 
											"<td class='countNotTd'><input class='zinput' type='text' /></td>" + 
											"<td class='partsNoNotTd'><input class='zinput' type='text' value='" + partsNo + "'/></td>" + 
											"<td class='factoryCodesNotTd'>" + findFactoryCodesSelect + "</td>" + 
											"<td>" + findFailureModesSelect + "</td>" + 
											"<td><select class='zselect'><option value='0'>是</option><option value='1'>否</option></select></td>" + 
											"<td class='exchangeAmountNot'></td>" + 
											"<td><a href='javascript: void(0);' class='delPartsMainDataNotA'>删除</a></td>" + 
										  "</tr>"; 
							}
							$("#exchangeDetailTable").append(content);
							content = "";
						}
						
					}
				}
			});
		}
	}
	
	/**
	 * 添加换件明细dom 
	 */
	function addRepairExchangeDetailStr(addInput, count, proNo, proName, detail, isBackOldPart) {
		var isBackOldPartContent = "<td style='display: none;'>";
		if(isBackOldPart == "1") {
			isBackOldPartContent = "<input type='hidden' name='repairExchangeDetailList[" + (count) + "].isBackOldPart' value='1'/>";
									"</td>";
		} else if(isBackOldPart == "0") {
			isBackOldPartContent = "<td style='display: none;'><input type='hidden' name='repairExchangeDetailList[" + (count) + "].isBackOldPart' value='0'/></td>";
		}
		isBackOldPartContent += ("<input type='hidden' name='repairExchangeDetailList[" + (count) + "].isEngineParts' value='1'/><input type='hidden' name='repairExchangeDetailList[" + (count) + "].isGreatCause' value='0'/>");
		isBackOldPartContent += "</td>";
		
		if(addInput) {
			return  "<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].itemCode' value='" + proNo + "'/>" + proNo + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].itemName' value='" + proName + "'/>" + proName + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].activityCode' value='" + detail.renewCode  + "'/>" + detail.renewCode + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].fittingNo' value='" + detail.partsUnitNo  + "'/>" + detail.partsUnitNo + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].fittingName' value='" + detail.partsName  + "'/>" + detail.partsName + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].unit' value='" + detail.partsUnit  + "'/></>" + detail.partsUnit + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].quantity' class='detailPartsCountInput' value='" + detail.partsCount  + "'/>" + detail.partsCount + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].fittngAmount' class='detailAmountInput' value='" + detail.amount  + "'/>" + detail.amount + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].isCause' value='" + detail.causeCode  + "'/>" + detail.cause + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].supplierCode' value='" + detail.renewCode  + "'/>" + detail.renewCode + "</td>" +
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].trouReason' value='" + detail.failureMode  +  "'/>" + detail.failureMode + "</td>" + 
						"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].fittingCode' value='" + detail.partsNo  + "'/>" + detail.partsNo + "</td>" +
						isBackOldPartContent;
		} else {
			return  "<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].itemCode' value='" + proNo + "'/>" + proNo + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].itemName' value='" + proName + "'/>" + proName + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].activityCode' value='" + detail.renewCode  + "'/>" + detail.renewCode + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].fittingNo' value='" + detail.partsUnitNo  + "'/>" + detail.partsUnitNo + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].fittingName' value='" + detail.partsName  + "'/>" + detail.partsName + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].unit' value='" + detail.partsUnit  + "'/></>" + detail.partsUnit + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].quantity' class='detailPartsCountInputShow' value='" + detail.partsCount  + "'/>" + detail.partsCount + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].fittngAmount' class='detailAmountInputShow' value='" + detail.amount  + "'/>" + detail.amount + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].isCause' value='" + detail.causeCode  + "'/>" + detail.cause + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].supplierCode' value='" + detail.renewCode  + "'/>" + detail.renewCode + "</td>" +
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].trouReason' value='" + detail.failureMode  +  "'/>" + detail.failureMode + "</td>" + 
					"<td><input type='hidden' name='repairExchangeDetailList[" + (count) + "].fittingCode' value='" + detail.partsNo  + "'/>" + detail.partsNo + "</td>" +
					isBackOldPartContent;
		}
		
	}
	
	/**
	 * 维修费用计算 -- 除了 其它费用外
	 */
	function calculateForRepairReport() {
		
		var params = {};
		params['engineNo'] = $("#repairReportEngineNo").val();
		params['servAddr'] = $("#repairReportServAddr").children("option:selected").val();
		
		var itemCodes = "", codeAndCounts = "", itemHours = "", totalHours = 0;
		$("#showMaintenanceTable tr:not(:first)").each(function() {
			
			var tdsObj = $(this).find("td");
			var itemCode = tdsObj.eq(2).find("input").val();
			var itemHour = tdsObj.eq(5).find("input").val();
			var counts = tdsObj.eq(4).find("select").children("option:selected").val().charAt(0);
			
			alert(itemCode + itemHour + counts);
			
			itemCodes = itemCodes + itemCode + ";";
			codeAndCounts = codeAndCounts + itemCode + "," + counts + ";";
			itemHours = itemHours + itemHour + ";";
			totalHours = forSum(itemHour, totalHours);
		});
		params['totalHours'] = totalHours;
		params['codeAndCounts'] = codeAndCounts;
		params['itemHours'] = itemHours;
		params['finishTime'] = $("#repairReportFinishTime").val();
		params['trouDate'] = $("#repairReportTrouDate").val();
		
		var travelWay = $("#repairReportTravelWay").children("option:selected").val();
		var isDirveCar = "";
		if(travelWay == "") {
			isDirveCar = "0";
		} else {
			isDirveCar = "1";
		}
		params['isDirveCar'] = isDirveCar;
		
		params['mileage'] = forQuotient($("#repairReportTravelMileage").val(), 2);
		params['stationCode'] = $("#repairReportStationCode").val();
		
		//计算新添加的总配件金额
		var partsAmountAll = 0;
		$("input[class='detailAmountInput']").each(function() {
			var amounts = Number($(this).val());
			if(isNaN(amounts)) {
				amounts = 0;
			}
			partsAmountAll = forSum(partsAmountAll, amounts);
		});
		
		params['partsAmountAll'] = partsAmountAll;
		
		$.ajax({
			type: "POST",
			data: params,
			async: false,
			url: "findListFromDataProcess.action",
			success: function(data) {
				if(data != "") {
					var dataMap = JSON.parse(data);
					var domObj = null;
					
					var isAppend = false;
					if($(".otherCostTr").length <= 0 || $(".otherCostTr:first td:first").html() == 0) {
						isAppend = true;
					}
					
					//清空特殊费用trs
					$("#repairCostContentTable").find("tr[class='specialCostTr']").remove();
					
					//如果其他费用不存在或者其他费用在前那么得到其他费用最大索引,否则索引为0
					var index = $(".otherCostTr:last td:first").html();
					if(index != null && isAppend) {
						index = forSum(Number(index), 1);
					} else {
						index = 0;
					}
					
					var currentCount = 0;
					
					for(var categoryName in dataMap) {
						if(categoryName != "totle") {
							var feeDetails = dataMap[categoryName];
							var feeDetail = null;
							var feetrslength = feeDetails.length;
							var changeRowspanObj = null;
							
							for(var i = 0; i < feetrslength; i = i + 1) {
								
								feeDetail = feeDetails[i];
								
								
								//添加费用
								var trObj = $("#specialCostTemp tr:first").clone();
								trObj.attr("class", "specialCostTr");
								if(i == 0) {
									changeRowspanObj = trObj;
								}
								
								var tdsObj = trObj.find("td");
								
								tdsObj.eq(0).attr("class", "costIndex");
								tdsObj.eq(0).html(index);
								tdsObj.eq(1).html(categoryName);
								tdsObj.eq(2).html(feeDetail.feeName);
								domObj = tdsObj.eq(4).find("input");
								domObj.attr("name", "accountingCostDetailsList[" + index + "].amount");
								domObj.val(feeDetail.quantity);
								domObj.prev().html(feeDetail.quantity);
								domObj = tdsObj.eq(5).find("input");
								domObj.attr("name", "accountingCostDetailsList[" + index + "].univalent");
								domObj.val(feeDetail.unitPrice);
								domObj.prev().html(feeDetail.unitPrice);
								domObj = tdsObj.eq(6).find("input");
								domObj.attr("name", "accountingCostDetailsList[" + index + "].money");
								domObj.val(feeDetail.totalPrice);
								domObj.prev().html(feeDetail.totalPrice);
								
								//如果总价格为负数并且为第一行 那么隐藏第一行.
								if(i == 0 && feeDetail.totalPrice < 0) {
									trObj.hide();
									currentCount ++;
								}
								
								if(i == currentCount && feeDetail.totalPrice > 0) {
									tdsObj.eq(1).attr("rowspan", feetrslength);
								} else {
									tdsObj.eq(1).hide();
								}
								
								//在维修费用后面添加其他费用
								if(isAppend) {
									$("#repairCostContentTable").append(trObj);
								} else {
									if($(".specialCostTr").length <= 0) {
										$("#repairCostSecondTr").after(trObj);
									} else {
										$(".specialCostTr:last").after(trObj);
									}
								}
								
								
								trObj = null;
								index = index + 1;
							}
							
							var tdObj = changeRowspanObj.find("td").eq(1);
							tdObj.attr("rowspan", feetrslength);
							if(tdObj.is(":hidden")) {
								tdObj.show();
							}
							
							changeRowspanObj = null;
						}
						
					}
					
					//校验索引是否排列正确
					var specialCostLastIndex = $(".specialCostTr:last td:first").html();
					if(specialCostLastIndex == null) {
						specialCostLastIndex = -1;
					} else {
						specialCostLastIndex = Number(specialCostLastIndex);
					}
					
					var otherCostFirstIndex = $(".otherCostTr:first td:first").html();
					if(otherCostFirstIndex == null) {
						otherCostFirstIndex = -1;
					} else {
						otherCostFirstIndex = Number(otherCostFirstIndex);
					}
					
					var costFirstIndexVal = forSum(specialCostLastIndex, 1);
					
					if(specialCostLastIndex > -1 && otherCostFirstIndex > -1 && costFirstIndexVal != otherCostFirstIndex) {
						//改变otherCostTr最后一行以后的所有索引为本索引依次加一
						changeCloneTableContent($(".specialCostTr"), costFirstIndexVal, false);
					}
					
					//内容计算
					var nameAndPrices = dataMap["totle"];
					for(var index in nameAndPrices) {
						var dbDetail = nameAndPrices[index];
						var dbCategoryName = dbDetail.categoryName;
						if(dbCategoryName == "材料费") {
							$("#materialCost").find("span").html(dbDetail.totalPrice);
							$("#materialCost").find("input").val(dbDetail.totalPrice);
							$("#materialCost").show();
						} else if(dbCategoryName == "服务费") {
							$("#serviceCost").find("span").html(dbDetail.totalPrice);
							$("#serviceCost").find("input").val(dbDetail.totalPrice);
							$("#serviceCost").show();
						} else if(dbCategoryName == "调整费") {
							$("#mofifyCost").find("span").html(dbDetail.totalPrice);
							$("#mofifyCost").find("input").val(dbDetail.totalPrice);
							$("#mofifyCost").show();
						}
					}
					
					//计算总费用templateTable
					calculateAllForRepairReport(true);
				}
			}
		});
		
	}
	
	/**
	 * 维修费用计算 -- 维修费用 材料总费、服务总费
	 */
	function calculateAllForRepairReport(isCalculateOtherCost) {
		if(isCalculateOtherCost) {
			var otherAmount = 0, vals = 0;
			$(".costAmount").each(function() {
				vals = $(this).find("input").val();
				if(vals != null) {
					vals = Number(vals);
				}
				otherAmount = forSum(otherAmount, vals);
			});
			$("#otherCost").find("input").val(otherAmount);
			$("#otherCost").find("span").html(otherAmount);
			if($("#otherCost").is(":hidden")) {
				$("#otherCost").show();
			}
		}
		var amountAll = 0;
		amountAll = forSum($("#materialCost").find("input").val(), $("#otherCost").find("input").val());
		amountAll = forSum(amountAll, $("#serviceCost").find("input").val());
		amountAll = forSum(amountAll, $("#mofifyCost").find("input").val());
		
		$("#costTotal").find("span").html(amountAll);
		$("#costTotal").find("input").val(amountAll);
	}
	
	/**
	 * 维修费用计算 -- 得到维修费用 其它费用的 最大索引
	 * @param result 返回其它费用最大索引
	 */
	function findMaxIndexForCost() {
		
		var result = -1;
		
		//对比其他费用最后一行和特殊费用第一行索引
		var otherCostLastIndex = $(".otherCostTr:last td:first").html();
		if(otherCostLastIndex == null) {
			otherCostLastIndex = -1;
		} else {
			otherCostLastIndex = Number(otherCostLastIndex);
		}
		
		var specialCostFirstIndex = $(".specialCostTr:first td:first").html();
		if(specialCostFirstIndex == null) {
			specialCostFirstIndex = -1;
		} else {
			specialCostFirstIndex = Number(specialCostFirstIndex);
		}
		var specialCostLastIndex = $(".specialCostTr:last td:first").html();
		if(specialCostLastIndex == null) {
			specialCostLastIndex = -1;
		} else {
			specialCostLastIndex = Number(specialCostLastIndex);
		}
		
		if(otherCostLastIndex <= -1 && specialCostFirstIndex > -1) { // 只有特殊费用
			result = specialCostLastIndex;
		} else {
			result = otherCostLastIndex;
		}

		return forSum(result, 1);
	}
	
	/**
	 * 维修费用计算 -- 修改
	 */
	function changeCloneTableContent(trsObj, index, ischangeClass) {
		trsObj.each(function() {
			tdsObj = $(this).find("td");
			if(ischangeClass) {
				$(this).attr("class", "otherCostTr");
				tdsObj.eq(0).attr("class", "costIndex");
			}
			tdsObj.eq(0).html(index);
			tdsObj.eq(4).find("input").attr("name", "accountingCostDetailsList[" + index + "].amount");
			tdsObj.eq(5).find("input").attr("name", "accountingCostDetailsList[" + index + "].univalent");
			tdsObj.eq(6).find("input").attr("name", "accountingCostDetailsList[" + index + "]].money");
			tdsObj.eq(10).find("textarea").attr("name", "accountingCostDetailsList[" + index + "]].itemInfo");
			index = forSum(index, 1);
		});
	}
	
	/**
	 * 计算 --- 加法
	 */
	function forSum(paramOne, paramTwo) {
		var val01, val02, val;
		try {
			val01 = paramOne.toString().split(".")[1].length;
		} catch(e) {
			val01 = 0;
		}
		try {
			val02 = paramTwo.toString().split(".")[1].length;
		} catch(e) {
			val02 = 0;
		}
		val = Math.pow(10, Math.max(val01, val02));
		return (paramOne * val + paramTwo * val) / val;
	}
	
	/**
	 * 乘法
	 */
	function forQuotient(paramOne, paramTwo) {
		var val01 = paramOne.toString(), val02 = paramTwo.toString(), val = 0;
		try {
			val = val + val01.split(".")[1].length;
		} catch(e) {}
		try {
			val = val + val02.split(".")[1].length;
		} catch(e) {}

		return Number(val01.replace(".", "")) * Number(val02.replace(".", "")) / Math.pow(10, val);
	}
	
	/**
	 * 弹出层
	 */
	function showDialog(dialogId, dialogOverId, myWidth, myHeight, pageX, pageY) {
	    
	    var maskHeight = $(document).height();
	    var maskWidth = $(window).width();   
	    var dialogTop =  pageY - myHeight;
	    var dialogLeft = pageX - myWidth / 2;
	    parentWindow.$("#" + dialogId).css({
	    	"width": myWidth/2,
	    	"top": dialogTop,
	    	"left": dialogLeft,
	    	"position": "absolute",
	    	"z-index" : "500"
	    }).slideDown("fast");
	    
	    parentWindow.$("#" + dialogOverId).css({height: maskHeight, width: maskWidth, "z-index" : "1"}).show(); //遮罩层
	};

	/**
	 * 关闭弹出层
	 * @param dialogId     弹出的 div 层Id
	 * @param dialogOverId 遮罩层 id
	 */
	function closeDialog(dialogId, dialogOverId) {
		parentWindow.$("#" + dialogId).hide();
		parentWindow.$("#" + dialogOverId).hide();
	};
	
	
});