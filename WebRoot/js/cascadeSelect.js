   function SelectObj(ele, eleVal, headerKey, headerValue, myObjVal){
      this.ele = ele;
	  this.headerKey = headerKey == null ?  '请选择' : headerKey;
	  this.headerValue = headerValue == null ?  '请选择' : headerValue;
	  this.eleVal = eleVal;
	  this.myObjVal = myObjVal;
   }
   
      //级联对象
   var SelGroups = {
   	    groupArray:[],  //下拉组
   		objsArray:[],  //下拉对象
		addSelect: function(eleId, eleVal, groupName,headerKey, headerValue){  //元素，请求url，请求参数，headerKey，headerValue，默认值，父参数名称，组名称
		   	var ele = $('#' + eleId);
			var selObj = new SelectObj(ele, eleVal, headerKey, headerValue);
			var index = $.inArray(groupName, SelGroups.groupArray);
			if(index == -1){ //新的
				index = SelGroups.groupArray.push(groupName)-1;
				SelGroups.objsArray[index] = new Array();
			}
			SelGroups.objsArray[index].push(selObj);
			
		},
		init: function(allVals){ //初始化
			var obj;
			$.each(SelGroups.groupArray, function(n, groupName){ //组循环
				$.each(SelGroups.objsArray[n], function(m, selectObj){ //组内循环
					if (m < SelGroups.objsArray[n].length - 1) { //最后一个不需要绑定				
						selectObj.ele.change(function(){
							valueChange(m, SelGroups.objsArray[n], allVals);
						});
					}
				});
   			});		
			
			for(var i=0; i< SelGroups.objsArray.length; i++){
				SelGroups.objsArray[i][0].myObjVal = allVals;
				createOptions(0, SelGroups.objsArray[i]);  //TODO 多个时初始化值
			}
		}

   };
 
   function valueChange(changeIdx, groups){
  		for(var i=changeIdx+1; i<groups.length; i++){
			//全部清除
			groups[i].ele.empty();
			groups[i].ele[0].options[0] = new Option(groups[i].headerKey, groups[i].headerValue);
		}

		if (changeIdx + 1 < groups.length) {
			var selVal = groups[changeIdx].ele.val(); //变化后的值
			//var selText = groups[changeIdx].ele.find('option:selected').text();
			//var search = selVal + ":" + selText;	
			$.each(groups[changeIdx].myObjVal, function(k, v){ //便利节点寻找
			    var nodeVal = k.split(":");
				if(nodeVal[0] == selVal){
			//	if (k == search) {
					groups[changeIdx+1].myObjVal = v;
					return false; //退出each
				}
			});
			
			createOptions(changeIdx + 1, groups);
		}
  }
   
   function selValueChange(changeIdx, groups, selVal){
 		for(var i=changeIdx+1; i<groups.length; i++){
			//全部清除
			groups[i].ele.empty();
			groups[i].ele[0].options[0] = new Option(groups[i].headerKey, groups[i].headerValue);
		}

		if (changeIdx + 1 < groups.length) {
			if (selVal == 0) {
				selVal = groups[changeIdx].ele.val(); //变化后的值
			}
			//var selText = groups[changeIdx].ele.find('option:selected').text();
			//var search = selVal + ":" + selText;	
			$.each(groups[changeIdx].myObjVal, function(k, v){ //便利节点寻找
			    var nodeVal = k.split(":");
				if(nodeVal[0] == selVal){
			//	if (k == search) {
					groups[changeIdx+1].myObjVal = v;
					return false; //退出each
				}
			});
			
			createOptions(changeIdx + 1, groups);
		}
 }
  
   function createOptions(idx, groups){    //获取父类的值, 如果本身只有一个值，或者本身的下拉值为选中状态、则需要构造子节点下拉框,优化下，下拉框属性存储下个节点的值，避免再次从父节点开始搜索		
	var optionIdx = 0;
	var curSelVal; //当前选中的值
	var ele = groups[idx].ele[0];
	$.each(groups[idx].myObjVal, function(k, v){ //构造节点
		var splitVal = k.split(":");
		optionIdx = optionIdx +1;
		ele.options[optionIdx] = new Option(splitVal[1], splitVal[0]);	
		if(splitVal[0] == groups[idx].eleVal){
			ele.options[optionIdx].selected = true;
			curSelVal = splitVal[0];
			if(idx+1 < groups.length){  //如果选中，则子节点的值也确认
				groups[idx+1].myObjVal = v;
			}
		}
		
	});
	
	if(curSelVal && idx+1 < groups.length){  //判断下一个下拉框是否要读取数据
		createOptions(idx+1, groups);  
	}
		
   }
