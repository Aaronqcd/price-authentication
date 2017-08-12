/**
 * @author WangJian
 */

 
/************************* JS validation begin ************************************/

//检查一个输入field是否为空
function IsEmptyValue(value){
	var num=0;
	var i=0;
	if(value==null)
		return true;
	if(value.length==0){
		return true;
	}

	for(i=0;i<value.length;i++){
		num=value.charCodeAt(i)
		if((num!=32)&&(num!=13)&&(num!=10))
			return false;
	}//end for
	return true
}//end IsEmptyValue
//------------------------------------------------------------------------------------------
//将一option选项清空
function clearOption(opt){
	for(var x=opt.length-1;x>=0;x--){
    				opt.options[x] = null;
    	}//end for
}//end clearOption
//-------------------------------------------------------------------------------------------
//数据合法性检查
function checkValidator(theForm){
	var valipatters=new Array("zipPat","integerPat","floatPat","numberPat",
				  "yearPat","timePat","datePat","dateTimePat",
				  "monthPat","stringPat"
				  );
	var PatternsDict = new Object();
	PatternsDict.zipPat = /^\d{6}$/;// matches zip codes
	PatternsDict.integerPat = /^\d{1,30}$/;//正整数检查
	PatternsDict.floatPat= /^\d{0,13}(\.|\d{0})\d{0,2}$/;//positive float  check
	// matches  -14,281,545.45 or ...
	PatternsDict.numberPat=/^(\-|d{0})\d{0,13}(\.|\d{0})\d{0,4}$/;
	PatternsDict.yearPat = /^\d{4}$/;//year检查
	// matches 5:04 or 12:34 but not 75:83
	PatternsDict.timePat=/^(0[0-9]|[1-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5]{0,}\d$/; //check time format
	// matches 1999-01-01 but not 99-01-01, 99-1-1, 99-01-1, 99-1-01, 1999-1-1, 1999-01-1, 1999-01-01
	PatternsDict.datePat0 =/^(19[0-9][0-9]|20[0-9][0-9])-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1])$/;
	PatternsDict.datePat =/^(19[0-9][0-9]|20[0-9][0-9])-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
	PatternsDict.dateTimePat =/^(19[0-9][0-9]|200[0-9])-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1])\s{1,}([1-9]|1[0-2]):[0-5]\d$/;
	//month,match "YYYY-MM" format
	PatternsDict.monthPat =/^(19[0-9][0-9]|200[0-9])-([1-9]|0[1-9]|1[0-2])$/;
	// matches "" string
	PatternsDict.stringPat=/\S{1,400}/;
	var column = theForm.elements; // get all elements of the form into array
	var i=0;
	for(i = 0; i < column.length; i++){
		// for each element of the form...
		with(column[i]){
			if(column[i].style.display=='none'){
				continue;
			}
			if((column[i].getAttribute('nullfalg')==1)||(column[i].getAttribute('nullfalg')==2)){  //if input field can be null;
				if(IsEmptyValue(column[i].value)){
					//if((column[i].valipat=="numberPat")||(column[i].valipat=="floatPat"))
						//if(column[i].nullflag==1)
						//	column[i].value="0";
					continue;
				}
			}//end if
			var v = column[i].getAttribute('valipat'); // get valipat, if any
			if(!v) continue; // no valipat property, skip
			if(clearFindArrayPosi(valipatters,v)<0){//不是有效的校验模式
				//alert("不是有效的校验模式:["+v+"],请检查输入域["+column[i].name+"]");
				messageError("["+column[i].name+"] Error:["+v+"], please check.");
				column[i].select();
				return false;
			}

			var thePat = PatternsDict[v]; // select the validating regular expr
			if(IsEmptyValue(column[i].value)){
				//alert("错误提示:"+column[i].name+"\n\r["+column[i].message + "]不能输入为空,请检查");
				messageError("["+column[i].getAttribute('message')+"] Could not null, please check.");
				if(IsInputField(column[i])==1)
					column[i].select();
				return false;
			}
			else if(!thePat.exec(value)){
				//alert("错误提示:"+column[i].name+"\n\r["+column[i].message + "]输入错误,请检查");
				messageError("["+column[i].getAttribute('message') + "] Data type error, please check.");
				if(IsInputField(column[i])==1)
					column[i].select();
				return false;
			}
		}//end with
	}//end for

	//在提交之前将所有输入项enabled
	for(var x=0;x<column.length;x++){
		//if(IsInputField(column[x])>=0)
		//	column[x].disabled=false;
		var v = column[x].getAttribute('valipat'); // get valipat, if any
		if(!v) continue; // no valipat property, skip
	}
	return true;
}//end checkValidator
//--------------------------------------------------------------------------------------
//对为数字输入field但允许为空，用户输入为空时置0
function checkNumberField(theForm){
	var column = theForm.elements;
	var i=0;
	for(i=0;i<column.length;i++){
		if(!IsEmptyValue(column[i].value))
			continue;
		var v = column[i].getAttribute('valipat'); // get valipat
			if(!v) continue; // no valipat property, skip
		if((v=="numberPat")||(v=="floatPat")||(v=="integerPat"))
			if(IsEmptyValue(column[i].value))
				column[i].value="0";
	}//end for
}//end checkNumberField
//-------------------------------------------------------------------------------------
//对number数保留小数点后两位返回
//对number数保留小数点后两位返回
function trimFloat(num){
	var i,j,len,n;
	var substr="";
	var str=""+num;
	i=str.indexOf(".");
	len=str.length;
	if(i<0)
		return num;
	if(len<i+3)
		return num;
	substr=str.substring(0,i+3);
	n=parseInt(str.charAt(i+3));

	if(n>=5){
		var num0=parseInt(parseFloat(num)*100);
		num0=num0+1;
		num0=num0/100;
		substr=''+num0;
	}
	return substr;
}//end trimFloat

//-------------------------------------------------------------------------------------
//返回当前网页中指定唯一property字段的value
function findFieldValue(frm,property){
	var column = frm.elements;
	var i=0;
	for(i=0;i<column.length;i++){
		if(column[i].keyfield==property)
			return column[i].value;
	}//end for
	return null;
}//end findFieldValue
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
//返回系统当前时间
function getCurrentTime(){
	var tt=new Date();
	var str="";
	var hours=tt.getHours();
	var minutes=tt.getMinutes();
	if(hours<10)
		str=str+"0";
	str=str+hours+":";

	if(minutes<10)
		str=str+"0";
	str=str+minutes;
	return str;
}//end getCurrentTime
//-------------------------------------------------------------------------------------
//返回系统当前日期
function getCurrentDate(){
	var tt=new Date();
	var str="";
	var year=tt.getFullYear();
	var month=tt.getMonth();
	month=month+1;
	var date=tt.getDate();
	str=year+"-";

	if(month<10)
		str=str+"0";
	str=str+month+"-";

	if(date<10)
		str=str+"0";

	str=str+date;
	return str;
}//end getCurrentDate
//------------------------------------------------------------------------------------
//从数组中查找给定变量的位置,不区分大小写
function findArrayPosi(Arr,str){
	if(str==null)
		return -1;
	var i=0;
	str=str.toUpperCase();
	//alert(str);
	for(i=0;i<Arr.length;i++)
		if(Arr[i]==str)
			return i;
	return -1
}


//对数据四舍五入
function  parseNum(num){
	if(IsEmptyValue(''+num))
		return 0.000;
	num=num*1000;
	num=Math.round(num);
	num=num/1000;
	return num;
}


/*在提交form时,将document中该disable的元素进行disable*/
function documentDisable(){
	var frm=document.forms;
	for(var x=0;x<frm.length;x++){
		var column=frm[x].elements;
		for(var y=0;y<column.length;y++){
			if((column[y].type=="button")||(column[y].type=="submit")||(column[y].type=="reset")){
				column[y].disabled=true;
			}
		}
	}
	return true;
}

//从数组中查找给定变量的位置,区分大小写
function clearFindArrayPosi(Arr,str){
	if(str==null)
		return -1;
	var i=0;
	for(i=0;i<Arr.length;i++)
		if(Arr[i]==str)
			return i;
	return -1
}

//千分位数字转换成正常数值
function comNumToNum(obj){
	if(IsEmptyValue(obj.value))
		return;

	var str=obj.value;
	var n=str.indexOf(',');
	while(n>0){
		str=str.substring(0,n)+str.substring(n+1);
		n=str.indexOf(',');
	}
	obj.value=str;
	return true;
}


//对对象进行check,是否属于输入field
function IsInputField(object){
  	if(object.type=="button")
		return -1;
   	if(object.type=="submit")
		return -1;
	if(object.type=="reset")
		return -1;
	if(object.type=="hidden")
		return 0;
	if(object.type=="select-one"){
		if(object.code!=null)
			return 2;
		if(object.Acode!=null)
			return 3;
		return 4;
	}
	if(object.type=="select-multiple"){
		if(object.code!=null)
			return 5;
		if(object.Acode!=null)
			return 6;
 		return 7;
	}//end if
   	return 1;
}//end IsInputField


//响应窗口提交按钮事件
function runsubmit(form){
	if(!checkValidator(form)){
		return false;
	}
	return true;
}

/************************* JS validation end ************************************/
