function showObj(id){
	$("#"+id).show();
}
function hideObj(id){
	$("#"+id).hide();
}
//必须先导入jquery的js
	function printScreen(block,title){
		//为其中的表单赋值，如果直接打印的话，input里面手动填写的字段没有值 start
		
		var inputArray=$("#"+block +" input");
		  var value2 = document.getElementById(block).innerHTML;
		if (inputArray != null && inputArray.length > 0) {
			for (i = 0; i < inputArray.length; i++) {
				var item=inputArray[i];
				var itmeType=item.type;
				var itemId=item.id;
				if("text"==itmeType){
					$("#"+itemId).attr("value", inputArray[i].value);
				}else if("checkbox"==itmeType){
					$("#"+itemId).attr("checked", inputArray[i].checked);
				}else if("radio"==itmeType){
					$("#"+itemId).attr("checked", inputArray[i].checked);
				}
			}
			
		}
		var oldValue = document.getElementById(block).innerHTML;
		var selectArray=$("#"+block +" select");
			if (selectArray != null && selectArray.length > 0) {
			for (j = 0; j < selectArray.length; j++) {
				var item=selectArray[j];
				var itemId=item.id;
				
				var options=$("#"+itemId);
				var chooseOption=$("#"+itemId +"> option[value="+selectArray[j].value+"]");
				chooseOption.attr("selected", "selected");
			}
			
		}
		//为其中的表单赋值，如果直接打印的话，input里面手动填写的字段没有值 start
   var value = document.getElementById(block).innerHTML;
   var printdetail=window.open("","printDetail","");
    printdetail.document.open();
    printdetail.document.write("<HTML><head></head><BODY onload='window.print()'>");
    printdetail.document.write("<PRE>");
    if(title != null)
   		 printdetail.document.write("<div style='text-align: center; font-size: 18px;font-weight: bold;'>"+title+"</div>");
    printdetail.document.write("<div style='text-align: center;'>"+value+"</div>");
    printdetail.document.write("</PRE>");
    printdetail.document.close("</BODY></HTML>");
    return true;
  }
	
	/* 查询驻外中心 返回ID  ***/   
  function searchAbroadC(abroadCenterId,abroadCenterName,maintStationCode,maintStationName){
	var abroadCenterNameValue= document.getElementById(abroadCenterName).value;
	var url=encodeURI(contextPath+"/abroadmaint/queryAbroadCenterAction.action");//?abroadName="+escape(abroadCenterNameValue)+"");
	var abroadValue = window.showModalDialog(url,abroadValue,"");
	if(typeof abroadValue != "undefined"){
		document.getElementById(abroadCenterId).value=abroadValue.split("@")[1];
		document.getElementById(abroadCenterName).value=abroadValue.split("@")[0];
	}
	if(typeof maintStationCode != "undefined"  && typeof maintStationName != "undefined"){
		document.getElementById(maintStationCode).value='';
		document.getElementById(maintStationName).value='';
	}
}

	  /* 查询维修站    返回ID ***/  
  function searchMaintS(maintStationId,maintStationName,abroadCenterName){
	var maintStationNameValue= document.getElementById(maintStationName).value;
	var abName= '';
	if(abroadCenterName != undefined && typeof abroadCenterName != "undefined"){
		abName= document.getElementById(abroadCenterName).value;
		if(IsEmptyValue(abName)){
			alert('请先选择驻外中心！');
			return;
		}
	}
	var url=encodeURI(contextPath+"/abroadmaint/queryMaintStationAction.action?abroadName="+escape(abName) );//+"&maintName="+escape(maintStationNameValue));
	var maintStationValue = window.showModalDialog(url,maintStationValue,"");
	if(typeof maintStationValue != "undefined"){
		document.getElementById(maintStationId).value=maintStationValue.split("@")[1];
		document.getElementById(maintStationName).value=maintStationValue.split("@")[0];
	}
}
  
  
  /* 查询驻外中心    返回CODE***/   
  function searchCodeAbroadC(abroadCenterCode,abroadCenterName,maintStationCode,maintStationName){
	var abroadCenterNameValue= document.getElementById(abroadCenterName).value;
	var url=encodeURI(contextPath+"/abroadmaint/queryAbroadCenterCodeAction.action");//?abroadName="+escape(abroadCenterNameValue));
	var abroadValue = window.showModalDialog(url,abroadValue,"");
	if(typeof abroadValue != "undefined"){
		document.getElementById(abroadCenterCode).value=abroadValue.split("@")[1];
		document.getElementById(abroadCenterName).value=abroadValue.split("@")[0];
	}
	if(typeof maintStationCode != "undefined"  && typeof maintStationName != "undefined"){
		document.getElementById(maintStationCode).value='';
		document.getElementById(maintStationName).value='';
	}
}

	  /* 查询维修站   返回CODE***/  
  function searchCodeMaintS(maintStationCode,maintStationName,abroadCenterName){
	var maintStationNameValue= document.getElementById(maintStationName).value;
	var abName= '';
	if(typeof abroadCenterName != "undefined"){
		abName= document.getElementById(abroadCenterName).value;
		if(IsEmptyValue(abName)){
			alert('请先选择驻外中心！');
			return;
		}
	}
	var url=encodeURI(contextPath+"/abroadmaint/queryMaintStationCodeAction.action?abroadName="+escape(abName));//+"&maintName="+escape(maintStationNameValue));
	var maintStationValue = window.showModalDialog(url,maintStationValue,"");
	if(typeof maintStationValue != "undefined"){
		document.getElementById(maintStationCode).value=maintStationValue.split("@")[1];
		document.getElementById(maintStationName).value=maintStationValue.split("@")[0];
	}
 }
  
  
  /* 查询服务对象   ***/  
  function searchEngineFactory(engineFactoryId,engineFactoryName){
	var engineFactoryNameValue= document.getElementById(engineFactoryName).value;
	var url=encodeURI(contextPath+"/costapproval/queryEngineFactoryAction.action?engineFactoryName="+engineFactoryNameValue+"");
	var engineFactoryValue = window.showModalDialog(url,engineFactoryValue,"");
	document.getElementById(engineFactoryId).value=engineFactoryValue.split("@")[1];
	document.getElementById(engineFactoryName).value=engineFactoryValue.split("@")[0];
 }
  
  
  /* 查询意见   ***/  
  function searchAdvice(adviceId){
	var engineFactoryNameValue= document.getElementById(adviceId).value;
	var url=encodeURI(contextPath+"/costapproval/queryAdviceAction.action?advice="+adviceValue+"");
	var adviceValue = window.showModalDialog(url,adviceValue,"");
	if(adviceValue.toString()!=undefined){
		document.getElementById(adviceId).value=adviceValue;
	}			
 }
  
  
  //清空查询条件
  function clean(){
	  $(":input[type=text]").each(function(){
          this.value="";     
      });
	  $(":input[type=hidden]").each(function(){
          this.value="";     
      });
	  
	  $("select").each(function(){
		$(this).find("option[value='']").attr("selected",true);		
	  });	  		 
  }
//加载工作流，流转记录
  function loadWorkFlowCycleInfo(divId,formType,formId){
  	$("#"+divId).load(contextPath+"/workflow/listCycleInfoAction.action?cycleInfo.formType="+formType+"&cycleInfo.formId="+formId+"&time="+new Date().getTime());
  }
  /**
   * 根据驻外中心id查询驻外中心的名字
   * ctName 驻外中心名字的id
   * span:显示值元素的id
   * 
   */
  function getCtName(id,divId){
		if(id!=""){
			var url=contextPath+"/common/getCtNameAction.action?abCenterNo="+id;
			url+="&ts="+new Date().getTime(); 
			$(document).ready(function(){ 
			    $.ajax({           
			       url:url,
				   type:'post',
				   dataType:'json',                    
			       success:function(data){                 
					  if(data==""){                                               
					  }else if (data!= ""){                       
						  $("#"+divId).html(data);
					  } 
					}                                          
			    }); 
			 });
		 }
  }
  
  /**
   * 根据审批人的id查询审批人的name
   * approvalUserName：审批人的id
   * span:显示值元素的id
   * 
   */
  function getUserName(userId,divId){
		if(userId!=""){
			var url=contextPath+"/common/getUserNameAction.action?userId="+userId;
			url+="&ts="+new Date().getTime();  
				  $.ajax({           
			       url:url,
				   type:'post',
				   dataType:'json',                    
			       success:function(data){                 
					  if(data==""){                                               
					  }else if (data!= ""){ 
						  $("#"+divId).html(data);
					  } 
					}                                          
			    }); 
		 }
  }
  
  
  function Days(day1, day2){
      var y1, y2, m1, m2, d1, d2;//year, month, day;
      y1 = parseInt(day1.split('-')[0]);
      y2 = parseInt(day2.split('-')[0]);
      m1 = parseInt(day1.split('-')[1]);
      m2 = parseInt(day2.split('-')[1]);   
      d1 = parseInt(day1.split('-')[2]);
      d2 = parseInt(day2.split('-')[2]); 
      var date1 = new Date(y1, m1, d1);
      var date2 = new Date(y2, m2, d2);                 //用距标准时间差来获取相距时间
      var minsec = Date.parse(date1) - Date.parse(date2);
      var days = minsec / 1000 / 60 / 60 / 24; //factor: second / minute / hour / day                 return days;
  } 

  /**
   * 弹出备注窗口
   * type 01:结算 02:维修站汇总 03:驻外中心汇总
   * no 根据type传入维修站或驻外中心编号
   * id 为要获取返回值的jquery对象
   * 调用示例：
   * showSettleRemarks('01','1001',$(this).next());
   */
  function showSettleRemarks(type,no,id) {
	  
	  var url=encodeURI(contextPath+"/settle/findSettleRemarksAction.action");
	  var re;
	  if(type == '01' || type == '02') {
		  re = window.showModalDialog(url + '?settleRemark.maintStationNo='+no+'&settleRemark.remarkType='+type);
	  }else if(type == '03') {
		  re = window.showModalDialog(url + '?settleRemark.abroadCenterNo='+no+'&settleRemark.remarkType='+type);
	  }else {
		  return;
	  }
	  
	  id.val(re);
	  
  }
  
  /**
   * 数字转化成大写中文
   */
  function parseChinese(n) {
	  if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
	  return "数据非法";
	  var unit = "千佰拾亿千佰拾万千百拾圆角分", str = "";
	  n += "00";
	  var p = n.indexOf('.');
	  if (p >= 0)
	  n = n.substring(0, p) + n.substr(p+1, 2);
	  unit = unit.substr(unit.length - n.length);
	  for (var i=0; i < n.length; i=i+1) {
		  str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
	  }
	  return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
  }
  
  function printDiv(id){
		var body=document.getElementById(id);
		document.body.innerHTML=body.innerHTML;
		window.print();
	}
  
  function openWindow(url,name,iWidth,iHeight){
	  var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	  var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	  if(name==null){
	  	name="";
	  }
	  window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
  }