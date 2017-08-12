//公式一
function fnGs1(btn){
	var input1 = $v("gs1_input1");
	var input2 = $v("gs1_input2");
	var input3 = $v("gs1_input3");
	var input4 = $v("gs1_input4");
	
	$E("gs1_result").value = (parseFloat(input1) - parseFloat(input2) - parseFloat(input3) - parseFloat(input4)).toFixed(2);
}

//公式二
function fnGs2(btn){
	var input1 = $v("gs2_input1");
	var input2 = $v("gs2_input2");
	var input3 = $v("gs2_input3");
	var input4 = $v("gs2_input4");
	
	$E("gs2_result").value = (parseFloat(input1) * Math.pow((parseFloat(input2) / parseFloat(input3)),parseFloat(input4))).toFixed(2);
}

//公式三
function fnGs3(btn){
	var input1 = $v("gs3_input1");
	var input2 = $v("gs3_input2");
	var input3 = $v("gs3_input3");
	
	$E("gs3_result").value = (parseFloat(input1) * (parseInt(input2) / parseInt(input3))).toFixed(2);
}

//公式四
function fnGs4(btn){
	var input1 = $v("gs4_input1");
	var input2 = $v("gs4_input2");
	var input3 = $v("gs4_input3");
	var input4 = $v("gs4_input4");
	var input5 = $v("gs4_input5");
	
	$E("gs4_result").value = (parseFloat(input1) - parseFloat(input2) - parseFloat(input3) - parseFloat(input4) - parseFloat(input5)).toFixed(2);
}

//公式五
function fnGs5(btn){
	var input1 = $v("gs5_input1");
	var input2 = $v("gs5_input2");
	
	$E("gs5_result").value = (parseFloat(input1) * (parseFloat(input2) / 100)).toFixed(2);
}

//公式六
function fnGs6(btn){
	var input1 = $v("gs6_input1");
	var input2 = $v("gs6_input2");
	
	$E("gs6_result").value = (1 - (parseFloat(input1) / parseFloat(input2))).toFixed(2) * 100;
}

//公式七
function fnGs7(btn){
	var input1 = $v("gs7_input1");
	var input2 = $v("gs7_input2");
	
	$E("gs7_result").value = (parseFloat(input1) * (parseFloat(input2) / 100)).toFixed(2);
}

//公式八
function fnGs8(btn){
	var input1 = $v("gs8_input1");
	var input2 = $v("gs8_input2");
	
	$E("gs8_result").value = (parseFloat(input1) * parseFloat(input2)).toFixed(2);
}

//公式九
function fnGs9(btn){
	var input1 = $v("gs9_input1");
	var input2 = $v("gs9_input2");
	var input3 = $v("gs9_input3");
	
	$E("gs9_result").value = (parseFloat(input1) * (1 + (parseFloat(input2) / 1000 * parseFloat(input3)))).toFixed(2);
}

//公式十
function fnGs10(btn){
	var input1 = $v("gs10_input1");
	var input2 = $v("gs10_input2");
	var input3 = $v("gs10_input3");
	
	$E("gs10_result").value = (parseFloat(input1) * Math.pow(1 + (parseFloat(input2) / 100),-parseFloat(input3))).toFixed(2);
}

//公式十一
function fnGs11(btn){
	var type = document.getElementsByName("type");
	var input1 = $v("gs11_input1");
	var input2 = $v("gs11_input2");
	var input3 = $v("gs11_input3");
	
	if(type[0].checked)
		$E("gs11_result").value = (parseFloat(input1) * (1 + parseInt(input2) * (parseInt(input3) / 100))).toFixed(2);
	else
		$E("gs11_result").value = (parseFloat(input1) * Math.pow(1 + (parseFloat(input3) / 100),parseFloat(input2))).toFixed(2);	
}

//公式十二
function fnGs12(btn){
	//var input1 = parseFloat($v("gs12_input1"));
	var input2 = parseFloat($v("gs12_input2"));
	var input3 = parseFloat($v("gs12_input3"));
	var input4 = parseFloat($v("gs12_input4"));
	var input5 = parseInt($v("gs12_input5"));
	var input6 = parseInt($v("gs12_input6"));
	
	var sum = 0.0;
	var temp = 0.0;
	for(var i = input5 ; i >= 1 ; i--){
		temp = input2 * Math.pow((1 + input3 / 100),-i);
		sum += temp;
	}
	
	$E("gs12_result").value = (sum + input4 * Math.pow((1 + input3 / 100),-input6)).toFixed(2);
}

//公式十三
function fnGs13(btn){
	var input1 = parseInt($v("gs13_input1"));
	var input2 = parseFloat($v("gs13_input2"));
	
	$E("gs13_result").value = (input1 * input2).toFixed(2);
}

//公式十四
function fnGs14(btn){
	var input1 = parseFloat($v("gs14_input1"));
	var input2 = parseFloat($v("gs14_input2"));
	
	$E("gs14_result").value = (input1 / (input2 / 100)).toFixed(2);
}

//公式十五
function fnGs15(btn){
	var input1 = parseFloat($v("gs15_input1"));
	var input2 = parseFloat($v("gs15_input2"));
	var input3 = parseFloat($v("gs15_input3"));
	
	$E("gs15_result").value = (input1 / ((input2/100 - input3/100))).toFixed(2);
}

//公式十六
function fnGs16(btn){
	var input1 = parseFloat($v("gs16_input1"));
	var input2 = parseFloat($v("gs16_input2"));
	
	$E("gs16_result").value = (input1 / (input2/100)).toFixed(2);
}

//公式十七
function fnGs17(btn){
	var input1 = parseInt($v("gs17_input1"));
	var input2 = parseFloat($v("gs17_input2"));
	var input3 = parseFloat($v("gs17_input3"));
	
	var sum = 0.0;
	var temp = 0.0;
	for(var i = 1 ; i <= input1 ; i++){
		temp = input2 / Math.pow(1 + input3 / 100,i);
		sum += temp;
	}
	$E("gs17_result").value = sum.toFixed(2);
}