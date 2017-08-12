/*
 * Copyright (c) 2012  GEONG INTERNATIONAL (GEONG Business  Networks LTD.) All rights reserved. 
 * For commercial use, you must accept this license.  http://www.geong.com/license
 */

/**
 * 新增审批费用（其它费用）
 */
function createAuditCost(){
	
	var row = $('#template_tr').clone();
	$(row).find('input,select').show();
	
	//寻找审批费用表格最后一行最后一个单元格中的数据标识
	var feeCateId = $('#auditCostTb tr:last').find('td:last').find('.feeCateId').val();
	
	if(feeCateId==FEE_CATE_OTHERS){
		$(row).find('.title').remove();
	}
	
	//如果存在“其它费用”单元格
	var obj = $('#auditCostTb').find('.title');
	if(obj != null && obj != undefined){
		var rowspan = Number($(obj).attr('rowSpan'));
		$(obj).attr('rowspan', Number(rowspan+1));
	}
	
	$('#auditCostTb').append($(row).html());
	
}

/**
 * 删除审批费用，只删除“其它费用”不删除材料费与服务费
 * @param {Object} obj
 */
function deleteAuditCost(obj, acId){
	if(!confirm('确认删除记录？')){
		return;
	}
	
	var tb = $(obj).parents('table');
	var tr = $(obj).parents('tr');
	//表头对象
	var title = $(tr).find('.title');
	
	if(null != title && undefined != title){
		var _title = $(tb).find('.title');
		var rowspan = $(_title).attr('rowspan');
		var nextTr = $(tr).next();
		rowspan = Number(rowspan)-1;
		//
		if($(title).html()==null){
			$(tr).remove();
			$(_title).attr('rowspan', rowspan);
		//如果当前ROW 包含表头，需要将表头取出来放到下一行	
		}else{
			var td_html = $(title).html();
			td_html = '<td class="title" rowspan='+rowspan+'>' + td_html + '</td>';
			$(tr).remove();
			$(nextTr).children('td:first').before(td_html);
		}
		
	}
	
	//FIXME
	if(undefined==acId || null==acId){
		//$(obj).parents('tr').remove();
	}else{
		$.ajax({
			method:'POST',
			url:'deleteAuditCost.action?auditCost.id='+acId,
			cache: false,
			success:function(data){
				data = JSON.parse(data);
				if(eval(data.result)){
					//$(obj).parents('tr').remove();
				}
			}
		});
	}
}

/**
 * 新增维修项目
 * @param {Object} data		包含ID和Name的数据对象
 */
function addAuditItem(data){
	//处理模板内容
	var row = $('.repairItem').clone();
	$(row).find('input,select').show();
	
	$(row).find('.numLimit').val(data.numLimit);
	$(row).find('.engineSeries').val(data.engineSeries);
	$(row).find('.engineAttu').val(data.engineAttu);
	$(row).find('.useTypeId').val(data.useTypeId);
	$(row).find('.itemCode').val(data.id);
	$(row).find('.itemName').val(data.name);
	$(row).find('.hours').val(data.hours);

	var prjUnit = data.prjUnit;
	var numLimit = data.numLimt;
	
	var select = $(row).find('.addedAttr');
	for(var i=1; i<=Number(numLimit); i++){
		var opt = document.createElement("OPTION");
		opt.value = i;
		opt.text = i + prjUnit;
		//不兼容FF
		//$(select).get(0).options.add(opt);
		select[0].options.add(opt);
	}
	
	var _html = $(row).html();
	
	//填模板
	$('#auditItemTb').append(_html);
	
	//计算Sequence
	writeTableSequence('auditItemTb', 1);
}


/**
 * 删除维修项目
 */
function deleteAuditItem(){
	if(!confirm('删除维修项目会删除对应的换件信息，确认要删除吗？')){
		return false;
	}
	
	$('#auditItemTb tr').each(function(rowId){
		var isBreak = false;
		if(isBreak){
			return;
		}
		var _checked = $(this).find('.auditItem').is(':checked');
		var _auditItemId = $(this).find('.auditItem').val();
		var _itemCode = $(this).find('.itemCode').val();
		
		if(_checked){
			if(null!=_auditItemId && undefined!=_auditItemId && ''!=_auditItemId){
				$.ajax({
					method:'POST',
					url:'deleteAuditItem.action?auditItem.id='+_auditItemId,
					cache:false,
					success:function(data){
//console.log('deleteAuditItem data: ', data);						
						data = JSON.parse(data);
						if(eval(data.result)){
							//刷新整个页面
							//window.location.reload();
							//删除该维修项目对应的换件信息
							deleteRelatedByItemCode(_itemCode);
							//删除成功后重新计算审批费用
							ajaxCalcItemCost();
							$(this).remove();
						}
					}
				});
			}else{
				$(this).remove();
				isBreak = true;
			}
			
			//级联删除刷新表格
			deleteRelatedByItemCode(_itemCode);
		}
	});
	
	//删除后重新排序
	tableSequenceReOrder('auditItemTb', 1);
}


/**
 * 级联删除
 * @param {Object} itemCode
 */
function deleteRelatedByItemCode(itemCode){
	
	$('#preExchangeTb tr').each(function(rowId){
		if(rowId>0){
			var _itemCode = $(this).find('.itemCode').val();
//console.log('deleteRelatedByItemCode _itemCode: ', _itemCode);		
			if(itemCode==_itemCode){
				var changeId = $(this).find('.id').val();
				if(null!=changeId && undefined!=changeId && ''!=changeId){
					$.ajax({
						type:'POST',
						url:'ajaxDelPreExchange.action?id='+changeId,
						cache:false,
						success:function(data){
							data = JSON.parse(data);
							if(eval(data.result)){
								//删除成功后重新计算审批费用
								ajaxCalcItemCost();
								$(this).remove();
							}
						}
					});
				}else{
					$(this).remove();
				}
			}
		}
	});
	
	//重写数据
	writeLogicData();
}

/**
 * 根据数量和单价计算其它费用
 * @param {Object} obj
 */
function calcOtherFee(obj){
	var tr = $(obj).parent().parent();
	var qty = $(tr).find('.qty').val();
	var price = $(tr).find('.price').val();
	var amount = Number(qty) * Number(price);
	if(isNaN(amount)){
		messageError('请输入有效的数量与单价');
		return;
	}
	$(tr).find('.amount').val(amount);
}

/**
 * 打开查找维修项目页面(点击项目编号旁边小按钮)
 * @param {Object} obj Button对象
 */
function openAuditItemPage(obj){
	
//console.log('添加维修项目  exec openAuditItemPage');	
	
	var engineSeries = $('#engineSeries').val();
	var trouDate = $('#trouDate').val();
	var useType = $('#useType').val();
	var engineAttu = $('#engineAttu').val();
	var useTypeId = $('#useTypeId').val();
	
	var param = 'audit.engineSeries=' + engineSeries;
	param += '&audit.trouDate=' + trouDate;
	param += '&audit.prdctUseId=' + useType;
	param += '&audit.engineAttu=' + engineAttu;
	
	var val = window.showModalDialog('maintenanceList.action?'+param, window, 
		"dialogHeight:500px; dialogWidth:750px; center:yes; help:no; resizable:no; status:no;");
	
//console.log('openAuditItemPage val: ', JSON.stringify(val));	
	
	if(null==val || undefined==val){
		return;
	}

	//Item 的所有数据
	var allItem = $('#auditItemData').val();
	if(null!=allItem && undefined!=allItem && ''!=allItem){
		allItem = JSON.parse(allItem);
	}else{
		allItem = new Array();
	}

	var code = '';
	var name = '';
	for(var i=0; i<val.length; i++){
		//判断当前页面是否存在选定的值
		var exists = false;
		for(var j=0; i<allItem.length; j++){
			var _obj = allItem[j];
			if(undefined!=_obj && null!=_obj){
				if(_obj.itemCode==val[i].id){
					exists = true;
					break;
				}
			}
		}
		if(!exists){
			code += val[i].id + ',';
			name += val[i].name + '、';
		}
	}
	//触发“新增”按钮，选中几行添加几行
	for(var i=0; i<val.length; i++){
		var exists = false;
		for(var j=0; i<allItem.length; j++){
			var _obj = allItem[j];
			if(_obj.itemCode==val[i].id){
				exists = true;
				break;
			}
		}
		if (!exists) {
			val[i]['numLimit'] = val[i].numLimt;
			val[i]['engineSeries'] = engineSeries;
			val[i]['engineAttu'] = engineAttu;
			val[i]['useTypeId'] = useTypeId;
			addAuditItem(val[i]);
		}
	}
	
	//计算故障程度 falultLev标识
	calcTrouLevel(val);
	
	writeLogicData();
	
	//触发附加属性的change事件，计算项目费用
	$('.addedAttr').trigger('change');
}

/**
 * 删除所有审批费用
 */
function ajaxDeleteAllFee(){
	//准备JSON数据
	writeLogicData();
	var costJson = $('#auditCostData').val();
	var costArr = JSON.parse(costJson);
	var ids = '';
	for(var i=0; i<costArr.length; i++){
		if(costArr[i].feeCateId!=FEE_CATE_OTHERS){
			var id = costArr[i].id;
			if(null!=id && undefined!=id){
				ids += id + ',';
			}	
		}
	}

	$.ajax({
		type:'POST',
		url:'deleteAuditAllCost.action?ids=' + ids,
		cache:false,
		success: function(data){
			//data = JSON.parse(data);
			if(eval(data.result)){
				//清空除其它费用费用
				clearAuditCostWithoutOther();
				//重新计算隐藏域数据
				writeLogicData();
			}
		}
	});
}

/**
 * 计算审批费用
 */
function ajaxCalcItemCost(){

//console.log('exec func ajaxCalcItemCost');
	
	var trouDate = $('#trouDate').val();
	if(null==trouDate || ''==trouDate){
		messageError('故障日期不能为空');
		return;
	}
	
	//删除所有费用重新计算
	ajaxDeleteAllFee();
	
	//准备JSON数据
	writeLogicData();

	var formData = $('#auditForm').serialize();

	$.ajax({
		type:'POST',
		url: 'calcItemCost.action',
		data: encodeURI(formData),
		cache: false,
		success:function(data){
			//data = JSON.parse(data);		
			var auditCostArr = data.resultList;
//console.log('ajaxCalcItemCost auditCostArr: ', JSON.stringify(auditCostArr));			
			//填充结果
			fillAuditCost(auditCostArr);
			
			//重新写入配件费
			reWriteMaterialCostFee();
		},
		error:function(msg){
			messageError('计算失败，会话超时或系统错误');
			console.log(msg);
		}
	});
}

/**
 * 附加属性改变时重新计算维修工时
 */
function ajaxCalcItemHours(obj){
	var engineSeries = $('#engineSeries').val();
	var useClass = $('#useType').val();
	var engineAttru = $('#engineAttu').val();
	var trouDate = $('#trouDate').val();
	var attuCount = obj.value;
	var itemCode = $(obj).parents('tr').find('.itemCode').val();
	
//console.log('ajaxCalcItemHours trouDate: ', trouDate);	

	if(null==trouDate || ''==trouDate){
		messageError('故障日期不能为空');
		return;
	}
	
	$.ajax({
		type:'POST',
		url:'ajaxCalcRepairHours.action',
		data:{'engineSeries':engineSeries, 'useClass':useClass, 'engineAttru':engineAttru, 
				'trouDate':trouDate, 'attuCount':attuCount, 'itemCode':itemCode},
		cache:false,
		success:function(data){
//console.log('ajaxCalcItemHours: ', data.result);			
			if(eval(data.result)){
				var hours = data.hours;
				$(obj).parents('tr').find('.hours').val(hours);
				//将计算后的数据重新写入隐藏域
				writeLogicData();
				//重新计算费用
				ajaxCalcItemCost();				
			}
		}
	});
}


/**
 * 填充费用计算结果
 */
function fillAuditCost(auditCostArr) {

//console.log('fillAuditCost auditCostArr: ', JSON.stringify(auditCostArr));	
	if(null == auditCostArr || typeof(auditCostArr) == "undefined"){
		return false;
	}

	var tmp_html = $('.calc_template').html();
	//材料费TR
	//var _tr = $('#auditCostTb tr:first');
	var _tr;
	var feeCateArr = $('#auditCostTb').find('.feeCateId');
//console.log('fillAuditCost feeCateArr.length: ', feeCateArr.length);	
	for(var i=0; i<feeCateArr.length; i++){
		//FIXME 用常量替换
		if($(feeCateArr[i]).val()==1){
			var _feeObj = feeCateArr[i];
//console.log('fillAuditCost _feeObj: ', $(_feeObj).html());			
			_tr = $(_feeObj).parents('tr');
			break;
		}
	}
	
	//如果找不到就为表头
	if(null==_tr || undefined==_tr){
		_tr = $('#auditCostTb tr:first');
	}
//alert(_tr.html());	
	
	var _title = $('#auditCostTb').find('.title');

	for(var i=0; i<auditCostArr.length; i++){
		var $tmp = $(tmp_html).clone();
		$($tmp).find('input,select').show();
//console.log('fillAuditCost auditCostArr[i].costInfoName: ', auditCostArr[i].costInfoName);
		$($tmp).find('.feeCateId').val(auditCostArr[i].feeCateId);
		$($tmp).find('.costCateName').html(auditCostArr[i].costCateName);
		$($tmp).find('.feeInfoId').val(auditCostArr[i].feeInfoId);
		$($tmp).find('.costInfoName').html(auditCostArr[i].costInfoName);
		$($tmp).find('.qty').val(auditCostArr[i].quantity);
		$($tmp).find('.unit').val(auditCostArr[i].unit);
		$($tmp).find('.price').val(auditCostArr[i].price);
		$($tmp).find('.amount').val(auditCostArr[i].amount);
		$($tmp).find('.remark').val(auditCostArr[i].remark);	

		//将计算的结果添加到表格中
		//如果是材料费
		//FIXME 用常量代替
		if(auditCostArr[i].feeCateId==1){
			$(_tr).after($tmp);
		}else{
			//如果存在其它费用，插入到其它费用前面，表格中间
			if(null!=_title && undefined!=_title){
				$(_title).parents('tr').before($tmp);
			//追加到表格最后一行	
			}else{
				$('#auditCostTb').append($tmp);
			}
		}
	}

	writeLogicData();
	
	//合并表头
	mergeTbRows('auditCostTb', 0, 'feeCateId');
}

/**
 * 打开维修项目责任认定（text 对象）
 * @param {Object} obj
 */
function openAuditItemRespPage(obj){
	
	var respJson = $(obj).parent().find('.auditItemResps').val();
	
	var val = window.showModalDialog('queryRespHoldListAction.action?respJson='+respJson, window, 
		"dialogHeight:300px; dialogWidth:400px; center:yes; help:no; resizable:no; status:no; location:no;");
	
	if(null == val || undefined==val){
		return;
	}
	
	var name = '';
	var objArr = new Array();
	for(var i=0; i<val.length; i++){
		var _obj = {};
		//code += val[i].id + ',';
		name += val[i].name + '、';
		_obj['respId'] = val[i].code;
		_obj['respName'] = val[i].name;
		objArr.push(_obj);
	}
	
	$(obj).val(cutLast(name));
	$(obj).prev('input').val(JSON.stringify(objArr));
}

/**
 * 打开已填好的维修项目列表（点击对应项目文本框）
 * @param {Object} obj	文本框对象
 */
function openAuditItemListPage(obj){
	var _data = serializeItemTable('auditItemTb');
	//data = JSON.stringify(_data);

	//检验维修项目数据的正确性
	for(var i=0; i<_data.length; i++){
		if(_data[i].addedAttr==null || _data[i].addedAttr==''){
			messageError('维修项目->附加属性 不能为空');
			return false;
		}
		if(_data[i].hours==null || _data[i].hours==''){
			messageError('维修项目->维修工时 不能为空');
			return false;
		}
		if(_data[i].auditItemResps==null || _data[i].auditItemResps==''){
			messageError('维修项目->责任认定 不能为空');
			return false;
		}
	}
	
	//单条审批费用对应的AuditItem	
	var _costMappingItem = $(obj).parent().find('.auditItemJson').val();
	var mappingObj = {};
	
	//全部Item
	mappingObj['allItem'] = _data;
	if(null != _costMappingItem && ''!=_costMappingItem){
		_costMappingItem = JSON.parse(_costMappingItem);
		//映射Item
		mappingObj['mappingItem'] = _costMappingItem;
	}
	
	var mappingData = JSON.stringify(mappingObj);
	
	var _val = window.showModalDialog('selectAuditItemList.action?auditItemJson='+mappingData, window,
		"dialogHeight:500px; dialogWidth:750px; center:yes; help:no; resizable:no; status:no;");
	
	if(undefined==_val || null==_val){
		return;
	}
	
	var _itemArr = JSON.parse(_val);
	//var ids = "";
	var names = "";
	//隐藏域的责任认定数组
	var itemArr = new Array();
	for(var i=0; i<_itemArr.length; i++){
		var _obj = {};
		//ids += _itemArr[i].id + ",";
		names += _itemArr[i].name + "、";
		var itemObj = {};
		itemObj['id'] = _itemArr[i].id;
		itemObj['itemCode'] = _itemArr[i].code;
		itemObj['itemName'] = _itemArr[i].name;
		_obj['auditItem'] = itemObj;
		//_obj['itemCode'] = _itemArr[i].code;
		//_obj['itemName'] = _itemArr[i].name;
		itemArr.push(_obj);
	}
	$(obj).parents('td').children('.auditItemJson').val(JSON.stringify(itemArr));
	$(obj).val(cutLast(names));
}

/**
 * 打开预换件明细列表（点击换件按钮）
 * @param {Object} obj
 */
function openAuditPreExchangePage(obj, auditItemId){
	
	var preChangeDetailJson = $(obj).parent().find('.preExchangeDetails').val();	
	preChangeDetailJson = escape(encodeURIComponent(preChangeDetailJson));

	var val = window.showModalDialog('repairPreExchangeManageAction.action?' + 'preChangeDetailJson='+preChangeDetailJson, '', 
		"dialogHeight:600px; dialogWidth:800px; center:yes; help:no; resizable:no; status:no; location:no;");

//console.log('添加换件 openAuditPreExchangePage val: ', JSON.stringify(val));
	
	var itemCode = $(obj).parent().parent().find('.itemCode').val();
	var itemName = $(obj).parent().parent().find('.itemName').val();

	if(null==val || undefined==val){
		return false;
	}
	
	var repairNo = $('#repairNo').val();
	//配件Arr
	var changeArr = new Array();
	var partCostArr = new Array();
	
	for(var i=0; i<val.length; i++){
		var data = val[i];
		data['itemCode'] = itemCode;
		data['itemName'] = itemName;
		changeArr.push(data);
	
		//配件费
		var partsCost = {};
		partsCost['repairNo'] = repairNo;
		//FIXME 用常量替代
		partsCost['feeCateId'] = '1';
		if(data.isEngineParts==1){
			partsCost['feeInfoId'] = '14';
			partsCost['costInfoName'] = '非发动机件';
		}else if(data.isEngineParts==0){
			partsCost['feeInfoId'] = '1';
			partsCost['costInfoName'] = '配件费';
		}
		partsCost['amount'] = data.fittingAmount;
		partsCost['quantity'] = data.quantity;
		partsCost['costCateName'] = '材料费';
		
		partCostArr.push(partsCost);
	}
	
	//写入隐藏域
	$(obj).parent().find('.preExchangeDetails').val(JSON.stringify(changeArr));
	
	refreshChangeTb(auditItemId);
	
	//填充配件费
	fillMaterialCostFee(partCostArr);
}

/**
 * 重新计算配件费
 */
function reWriteMaterialCostFee(){
	var valArr = serializeItemTable('preExchangeTb');
	
	var partCostArr = new Array();
	
	var repairNo = $('#repairNo').val();
	
	for(var i=0; i<valArr.length; i++){
		var data = valArr[i];
	
		//配件费
		var partsCost = {};
		partsCost['repairNo'] = repairNo;
		//FIXME 用常量替代
		partsCost['feeCateId'] = '1';
		if(data.isEngineParts==1){
			partsCost['feeInfoId'] = '14';
			partsCost['costInfoName'] = '非发动机件';
		}else if(data.isEngineParts==0){
			partsCost['feeInfoId'] = '1';
			partsCost['costInfoName'] = '配件费';
		}
		partsCost['amount'] = data.fittingAmount;
		partsCost['quantity'] = data.quantity;
		partsCost['costCateName'] = '材料费';
		
		partCostArr.push(partsCost);
	}
	if(null!=valArr && valArr.length>0){
		//valArr = JSON.parse(valArr);
//console.log('reWriteMaterialCostFee valArr:', JSON.stringify(valArr));		
		fillMaterialCostFee(partCostArr);
	}
}

/**
 * 写入配件费
 * @param {Object} mCost
 */
function fillMaterialCostFee(partCostArr){
	
	//合并材料费
	var mergeCostArr = mergeMaterial(partCostArr);
	
	if(null==mergeCostArr || undefined==mergeCostArr){
		return;
	}
	
	var newWriteArr = new Array();
	for(var i=0; i<mergeCostArr.length; i++){
		var feeInfoArr = $('#auditCostTb').find('.feeInfoId');
		var exgist = false;
		for(var j=0; j<feeInfoArr.length; j++){
/*			
console.log('mCostArr[i].feeInfoId: ', mCostArr[i].feeInfoId);			
console.log('$(feeInfoArr[i]).val(): ', $(feeInfoArr[j]).val());			
console.log("feeInfoArr[j]==mCostArr[i].feeInfoId: ", $(feeInfoArr[j]).val()==mCostArr[i].feeInfoId);
*/			
			//表格中存在，直接更新
			if($(feeInfoArr[j]).val()==mergeCostArr[i].feeInfoId){	
				$(feeInfoArr[j]).parents('tr:first').find('.qty').val(mergeCostArr[i].quantity);
				$(feeInfoArr[j]).parents('tr:first').find('.amount').val(mergeCostArr[i].amount);
				exgist = true;
				break;
			}
		}
		
		//表格中不存在，需要调用其它方法插入
		if(!exgist){
			newWriteArr.push(mergeCostArr[i]);
		}
	}
	
	if(newWriteArr.length>0){
//console.log('fillMaterialCostFee newWriteArr: ', JSON.stringify(newWriteArr));
		fillAuditCost(newWriteArr);
	}
	
	//将页面数据写入隐藏域
	writeLogicData();	
}

/**
 * 合并材料费
 * @param {Object} arr
 */
function mergeMaterial(arr){
	
	//clearMeaterialFeeAndCalcPartFee();

//console.log('func mergeMaterial arr: ', JSON.stringify(arr));
	
	if(null==arr || undefined==arr || arr.length==0){
		return;
	}
	
	var cateMap = new Map();
	
	var cateArr = new Array();
	
	for(var i=0; i<arr.length; i++){
		if(null==arr[i] || undefined==arr[i]){
			continue;
		}
		
		var obj = cateMap.get(arr[i].feeInfoId);
		if(null!=obj && undefined!=obj){
			obj['amount'] =  Number(obj['amount']) + Number(arr[i].amount);
			obj['quantity'] =  Number(obj['quantity']) + Number(arr[i].quantity);
		}else{
			cateMap.put(arr[i].feeInfoId, arr[i]);
		}
	}
	
	cateMap.each(function(key, value, index){
		cateArr.push(value);
	});
	
//console.log('cateArr: ', JSON.stringify(cateArr));	

	//var costVal = JSON.stringify(cateArr);
	//$('#auditCostData').val(costVal);
		
	return cateArr;
}

function clearMeaterialFeeAndCalcPartFee(){
	
	//清空材料费
	$('#auditCostTb tr').each(function(rowId){
		if(rowId>0){
			var cateId = $(this).find('.feeCateId').val();
			if(cateId==1){
				$(this).remove();
			}
		}
	});
	
	//计算材料费
	//ajaxCalcItemCost();
}

/**
 * 查找隐藏域中的材料费
 */
function findMaterialObject(){
	var obj;
	var existsArr = $('#auditCostData').val();
//console.log('func existsArr: ', existsArr);	
	if(null!=existsArr && ''!=existsArr){
		existsArr = JSON.parse(existsArr);
		for(var i=0; i<existsArr.length; i++){
			var _cost = existsArr[i];
			//材料费 ID为FEE_CATE_MATERIAL
			if(_cost.feeCateId==FEE_CATE_MATERIAL){
				obj = _cost;
				break;
			}
		}
	}
	return obj;
}

/**
 * 刷新预换件明细列表
 * @param {Object} auditItemId
 */
function refreshChangeTb(auditItemId){
	var changeArrsObj = $('#auditItemTb').find('.preExchangeDetails');
	var changeArrs = new Array();
	for(var i=0; i<changeArrsObj.length; i++){
		var json = $(changeArrsObj[i]).val();
		if(null!=json && undefined !=json && ''!=json){
			changeArrs.push(JSON.parse(json));
		}
	}
	
	var allChangeArr = new Array();
	for(var i=0; i<changeArrs.length; i++){
		
		if(null != changeArrs[i] && undefined!=changeArrs[i] && ''!=changeArrs[i]){
			var _arr = changeArrs[i];
			for(var j=0; j<_arr.length; j++){
				allChangeArr.push(_arr[j]);
			}
		}
	}
	
	//清空表格数据
	$('#preExchangeTb tbody').html('');
	var rowLength = $('#preExchangeTb tr').length;
	var template = $('.preExchangeTemplate').html();
	//写入数据
	for(var i=0; i<allChangeArr.length; i++){
		var data = allChangeArr[i];	
		
		var tr = $(template).clone();
		$(tr).children('.sequence').html(rowLength+i);
		$(tr).find('.itemCode').val(data.itemCode);
		$(tr).find('.itemName').val(data.itemName);
		$(tr).find('.fittingNo').val(data.fittingNo);
		$(tr).find('.fittingName').val(data.fittingName);
		$(tr).find('.unit').val(data.unit);
		$(tr).find('.quantity').val(data.quantity);
		$(tr).find('.fittingAmount').val(data.fittingAmount);
		var cause = data.isCause==0?'是':'否';
		$(tr).find('.isCauseLabel').val(cause);
		$(tr).find('.isCause').val(data.isCause);
		$(tr).find('.supplierCode').val(data.supplierCode);
		$(tr).find('.fittingCode').val(data.fittingCode);
		$(tr).find('.isEngineParts').val(data.isEngineParts);
		$(tr).find('.auditItem').val(auditItemId);
		
		$('#preExchangeTb').append(tr);
	}
}

/**
 * 更新换件表格中的数据
 * @param {Object} tbId		表格ID
 * @param {Object} data		换件数据对象
 * @param {Object} tr		表格行
 * @param {Object} itemCode	维修项目编号
 */
function updateChangeData2Tb(tbId, data, tr, itemCode){
	$('#'+tbId + ' tr').each(function(rowId){
		if(rowId>0){
			var tbItemCode = $(this).find('.itemCode').val();
			var tbFittingNo = $(this).find('.fittingNo').val();
			//如果是新增项目的换件
			if(undefined==tbItemCode || null==tbItemCode || ''==tbItemCode){
				$('#preExchangeTb').append(tr);
			}else{
				//是新增
				if(undefined==tbFittingNo ||  null==tbFittingNo || ''==tbFittingNo){
					$('#preExchangeTb').append(tr);
				//是修改
				}else{
					$(this).html($(tr).unwrap().html());
				}
			}
		}else{
			$('#preExchangeTb').append(tr);
		}
	});
	
	writeTableSequence(tbId.valueOf(), 0);
}

/**
 * 准备页面逻辑数据
 */
function writeLogicData(){
	//准备JOSN数据
	var auditItemData = serializeItemTable('auditItemTb');
	var auditCostData = serializeItemTable('auditCostTb');
	$('#auditItemData').val(JSON.stringify(auditItemData));
	$('#auditCostData').val(JSON.stringify(auditCostData));
}

/**
 * 故障审批单  新增修改提交前准备JSON数据
 */
function prepareSubmitForm(){
	
	writeLogicData();
	
	//校验表单
	return runsubmit(document.forms['auditForm']);
}

/**
 * 提交审批
 */
function submitApproval(){
	$('#audit_status').val(AUDIT_STATUS_WAIT);
	writeLogicData();
	document.forms['auditForm'].submit();
}

/**
 * 打印审批单
 */
function printAudit(){
	console.log('printAudit');
}

/**
 * 关闭
 */
function closeAudit(){
	_formData = $('#auditForm').serialize();
	if(formData != _formData){
		if(confirm('页面数据没有保存，确定要离开当前页面？')){
			window.close();
		}
	}else{
		if(confirm('确定要离开当前页面？')){
			window.close();
		}
	}
}

/**
 * 计算故障程度 falultLev
 * @param {Object} arr
 */
function calcTrouLevel(arr){

//console.log('计算故障程度 exec calcTrouLevel');
	
	var levelArr = new Array();
	for(var i=0; i<arr.length; i++){
		levelArr[i] = arr[i].falultLev;
	}
	
	for(var i=0; i<levelArr.length; i++){
		for(var j=0; j<levelArr.length-1; j++){
			if(Number(levelArr[i])<Number(levelArr[j])){
				var temp = levelArr[j];
				levelArr[j] = levelArr[i];
				levelArr[i] = temp;
			}
		}
	}
	
//console.log('calcTrouLevel levelArr.length: ', levelArr.length);

	if(levelArr.length>0){
		var level = levelArr[0];
		$.ajax({
			type:'POST',
			url:contextPath+'/auditticket/ajaxGetTrouLevel.action?trouLevel='+level,
			cache:false,
			success:function(data){
				if(eval(data.result)){
					var levelName = data.trouLevelName;
					$('#trouLvl').val(level);
					$('#trouLvlName').val(levelName);
				}
			},
			error:function(msg){
				messageError('会话超时或系统错误');
				console.log(msg);
			}
		});
	}
}



/***************************** 	公共函数  	******************************************/


/**
 * 表格填值
 * @param {Object} tbId		表格ID
 * @param {Object} row		行号	从0开始
 * @param {Object} col		列号从0开始
 * @param {Object} val		值
 */
function fillTableVal(tbId, row, col, val){
	$('#'+tbId+' tr').each(function(rowId){
		if(rowId==row){
			$(this).children('td').each(function(colId){
				if(colId==col){
					$(this).children('.zinput').val(val);
				}
			});
		}
	});
}

/**
 * 获取表格中的某个对象
 * @param {Object} tbId		表格ID
 * @param {Object} row		行号	从0开始
 * @param {Object} col		列号从0开始
 * @param {Object} _class	CSS class
 */
function getTableObj(tbId, row, col, _class){
	var obj = null;
	var isBreak = false;
	$('#'+tbId+' tr').each(function(rowId){
		if(isBreak){
			return;
		}
		if(rowId==row){
			$(this).children('td').each(function(colId){
				if(colId==col){
					obj = $(this).children('.' + _class.valueOf());
					isBreak = true;
					return;
				}
			});
		}
	});
	
	return obj;
}


/**
 *序列化表格 
 */
function serializeItemTable(tbId){
	
	var data = new Array();
	var isBreak = false;
	$('#'+tbId+' tr').each(function(rowId){
		if(rowId>0){
			var row = {};
			if(isBreak){
				return false;
			}
			$(this).children('td').each(function(colId){
				var inputs = $(this).children('input');
				if(isBreak){
					return false;
				}
				for(var i=0; i<inputs.length; i++){
					row[$(inputs[i]).attr('name')] = $(inputs[i]).val();
				}
				var selects = $(this).children('select');
				for(var i=0; i<selects.length; i++){
					row[$(selects[i]).attr('name')] = $(selects[i]).val();
				}
			});
			
			data.push(row);
		}
	});

	return data;
}

/**
 * 表格排序
 * @param {Object} tbId			表格编号
 * @param {Object} col			第几列
 * @param {Object} _class		数据class
 */
function sortTbRow(tbId, col, _class){
	
	var dataArr = new Array();
	
	//计算合并数据
	$('#'+tbId + ' tr').each(function(rowId){
		$(this).find('td').each(function(colId){
			if(col==colId){
				var feeCateId = $(this).find('.'+_class.valueOf()).val();
				//alert(feeCateId);
				var obj = null;
				
				//先从数据中去取，如果取不到就是第一次加载 
				for(var i=0; i<dataArr.length; i++){
					//obj = dataArr[i];
					if(dataArr[i]['logicId']==feeCateId){
						obj = dataArr[i];
						dataArr[i]['count'] = dataArr[i].count +1;
						break;
					}
				}
				
				//首次加载
				if(null==obj || undefined==obj){
					obj = {};
					//类别 
					obj['logicId'] = feeCateId;
					//行号
					obj['rowId'] = rowId;
					//出现了几次，合并多少行
					obj['count'] = 1;
					dataArr.push(obj);
				}
				
			}
			
		});
	});
	
	
	//排序
	var trs = $('#'+tbId + ' tr');

	$('#'+tbId + ' tr').each(function(pRowId){
		var ptr = $(this);
		$('#'+tbId + ' tr').each(function(rowId){
			var tr = (this);
			$(ptr).find('td').each(function(pColId){
				var ptd = (this);
				if(pColId==col){
					var feeCateId = $(ptd).find('.'+_class.valueOf()).val();
					$(tr).find('td').each(function(colId){
						var td = $(this);
						var _feeCateId = $(td).find('.'+_class.valueOf()).val();
						if(eval(feeCateId)>eval(_feeCateId)){
							var temp = trs[pRowId];
							trs[pRowId] = trs[rowId];
							trs[rowId] = temp;
						}
					});
				}
			});
		});
		
	});
	
	$('#'+tbId).html('');
	$('#'+tbId).append(trs);
}


/**
 * 合并表格行
 * @param {Object} tbId		表格ID
 * @param {Object} col		合并第几列
 * @param {Object} class	字段class
 */
function mergeTbRows(tbId, col, _class){
	//var rowsCount = $('#'+tbId + ' tr').length;
	var dataArr = new Array();
	
	//计算合并数据
	$('#'+tbId + ' tr').each(function(rowId){
		if(rowId==0){
			return;
		}
		$(this).find('td').each(function(colId){
			if(col==colId){
				var feeCateId = $(this).find('.'+_class.valueOf()).val();
				var obj = null;
				
				//先从数据中去取，如果取不到就是第一次加载 
				for(var i=0; i<dataArr.length; i++){
					//obj = dataArr[i];	
					if(dataArr[i]['logicId']==feeCateId){
						obj = dataArr[i];
						dataArr[i]['count'] = dataArr[i].count +1;
						break;
					}
				}
				
				//首次加载
				if(null==obj || undefined==obj){
					obj = {};
					//类别 
					obj['logicId'] = feeCateId;
					//行号
					obj['rowId'] = rowId;
					//出现了几次，合并多少行
					obj['count'] = 1;
					dataArr.push(obj);
				}
			}
		});
	});
	
	//合并表格
	$('#'+tbId + ' tr').each(function(rowId){
		if(rowId==0){
			 return;
		}
		$(this).find('td').each(function(colId){
			if (col == colId){
				var feeCateId = $(this).find('.' + _class.valueOf()).val();
				for(var i=0; i<dataArr.length; i++){
					var _obj = dataArr[i];
					//如果找不到，表示已经合并了，不需要再合并
					if(undefined==feeCateId || null==feeCateId || ''==feeCateId){
						return;
					}
					if(undefined==_obj['logicId'] || null==_obj['logicId'] || ''==_obj['logicId']){
						return;
					}
					if(feeCateId == _obj['logicId']){
				/*		
				console.log('mergeTbRows feeCateId: ', feeCateId);
				console.log("mergeTbRows _obj['count']: ", _obj['count']);
				console.log("mergeTbRows _obj['rowId']: ", _obj['rowId']);
				console.log("mergeTbRows rowId: ", rowId);
				console.log("mergeTbRows rowId== :", rowId==_obj['rowId']);
				console.log('');
				*/
						//如果要合并的行数大于1而且第一次出现的行数和当前行相同就执行合并
						if(Number(_obj['count'])>1 && _obj['rowId']==rowId){
							$(this).attr('rowspan', _obj['count']);
						}
						//如果合并的行数大于0，且当前行不与标识中的首行不相同，且当前行不是第一行，则移除
						else if(Number(_obj['count'])>1 && _obj['rowId']!=rowId){
							$(this).remove();
						}
					}
				}
			}
		});
	});
	
}


/**
 * 根据class寻找表格中的数据
 * @param {Object} tbId		表格ID
 * @param {Object} rowIdx	行索引, -1 代表最后一行
 * @param {Object} colIdx	列索引, -1 代表后后一个
 * @param {Object} _class	数据class
 */
function getDataFromTb(tbId, rowIdx, colIdx, _class){

}

/**
 * 清空除其它费用的全部费用
 */
function clearAuditCostWithoutOther(){
	$('#auditCostTb tr').each(function(rowId){
		if(rowId>0){
			var feeCateId =  $(this).find('.feeCateId').val();
//console.log('func clearAuditCostWithoutOther feeCateId: ', feeCateId);
			//删除除其它费用以外的费用
			if(feeCateId !=3){
				$(this).remove();
			}
		}
	});
}

/**
 * 清空表格，不包括表头
 * @param {Object} tbId
 */
function clearTableWithoutTitle(tbId){
	$('#' + tbId + ' tr').each(function(rowId){
		if(rowId>0){
			$(this).remove();
		}
	});
}


/**
 * 重新排列表格序号（当表格某行删除以后）
 * @param {Object} tbId		表格ID
 * @param {Object} col		列号，从0开始
 */
function tableSequenceReOrder(tbId, col){
	$('#'+tbId+' tr').each(function(rowId){
		if(rowId>0){
			$(this).children('td').each(function(colId){
				if(colId==col){
					$(this).html(rowId);
				}		
			});	
		}
	});
}

/**
 * 计算表格行数 (表格添加行后计算最大行号)
 * @param {Object} tbId		表格ID
 * @param {Object} col		列号，从0开始
 */
function writeTableSequence(tbId, col){
	var len = $('#'+tbId+' tr').length;
	$('#'+tbId+' tr').each(function(rowId){
		if(rowId==len-1){
			$(this).children('td').each(function(colId){
				if(colId==col){
					$(this).html(len-1);
				}
			});
		}
	});
}

/**
 * 截掉字符串后一位
 * @param {Object} str
 */
function cutLast(str){
	if(undefined == str || null==str){
		return "";
	}
	if(str.length>2){
		str = str.substring(0, str.length-1);
	}
	return str;
}

if (!Array.prototype.indexOf)
{
    Array.prototype.indexOf = function(elt /*, from*/)
    {
        var len = this.length;
  
        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;
  
        for (; from < len; from++)
        {
            if (from in this && this[from] === elt)
                return from;
        }
        return -1;
    };
}


function Map() {  
    /** 存放键的数组(遍历用到) */  
    this.keys = new Array();  
    /** 存放数据 */  
    this.data = new Object();  
      
    /** 
     * 放入一个键值对 
     * @param {String} key 
     * @param {Object} value 
     */  
    this.put = function(key, value) {  
        if(this.data[key] == null){  
            this.keys.push(key);  
        }  
        this.data[key] = value;  
    };  
      
    /** 
     * 获取某键对应的值 
     * @param {String} key 
     * @return {Object} value 
     */  
    this.get = function(key) {  
        return this.data[key];  
    };  
      
    /** 
     * 删除一个键值对 
     * @param {String} key 
     */  
    this.remove = function(key) {  
        this.keys.remove(key);  
        this.data[key] = null;  
    };  
      
    /** 
     * 遍历Map,执行处理函数 
     *  
     * @param {Function} 回调函数 function(key,value,index){..} 
     */  
    this.each = function(fn){  
        if(typeof fn != 'function'){  
            return;  
        }  
        var len = this.keys.length;  
        for(var i=0;i<len;i++){  
            var k = this.keys[i];  
            fn(k,this.data[k],i);  
        }  
    };  
      
    /** 
     * 获取键值数组(类似Java的entrySet()) 
     * @return 键值对象{key,value}的数组 
     */  
    this.entrys = function() {  
        var len = this.keys.length;  
        var entrys = new Array(len);  
        for (var i = 0; i < len; i++) {  
            entrys[i] = {  
                key : this.keys[i],  
                value : this.data[i]  
            };  
        }  
        return entrys;  
    };  
      
    /** 
     * 判断Map是否为空 
     */  
    this.isEmpty = function() {  
        return this.keys.length == 0;  
    };  
      
    /** 
     * 获取键值对数量 
     */  
    this.size = function(){  
        return this.keys.length;  
    };  
      
    /** 
     * 重写toString  
     */  
    this.toString = function(){  
        var s = "{";  
        for(var i=0;i<this.keys.length;i++,s+=','){  
            var k = this.keys[i];  
            s += k+"="+this.data[k];  
        }  
        s+="}";  
        return s;  
    };  
} 
