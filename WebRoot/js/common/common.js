//自动生成随机密码代码 start
function getRandomNum(lbound, ubound){
    return (Math.floor(Math.random() * (ubound - lbound)) + lbound);
}

function getRandomChar(number, lower, upper, other){
    var numberChars = "0123456789";
    var lowerChars = "abcdefghijklmnopqrstuvwxyz";
    var upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //var otherChars = "!@#$%^&*()";
	
	//根据要求是数字键0-9之上的特殊字符
    var otherChars = ")(*&^%$#@!";
    var charSet = '';
    if (number == true) 
        charSet += numberChars;
    if (lower == true) 
        charSet += lowerChars;
    if (upper == true) 
        charSet += upperChars;
    if (other == true) 
        charSet += otherChars;
    return charSet.charAt(getRandomNum(0, charSet.length));
}
//参数含义：length:密码长度 numberExcept:是否不要数字 lowerExcept：是否不要小写字母 upperExcept：是否不要大写字母 otherExcept：是否不要特殊字符
function getPassword(length, numberExcept, lowerExcept, upperExcept, otherExcept){
    var rc = "";
    if (length > 0) 
        rc = rc + getRandomChar(numberExcept, lowerExcept, upperExcept, otherExcept);
    for (var idx = 1; idx < length; ++idx) {
        rc = rc + getRandomChar(numberExcept, lowerExcept, upperExcept, otherExcept);
    }
    return rc;
}

function setUserPwd(length,numberExcept, lowerExcept, upperExcept, otherExcept,id){
	var regex=/(?=(?:.*?[a-zA-Z]){1})(?=(?:.*?\d){1})(?=(?:.*?[!@#$%*()_+^&}{:;?.]){1})/;
	var password=getPassword(length, numberExcept, lowerExcept, upperExcept, otherExcept);
	if(regex.test(password)){
		document.getElementById(id).value=password;
		return;
	}else{
		setUserPwd(length,numberExcept, lowerExcept, upperExcept, otherExcept,id);
	}
	
	
	
	
}
//自动生成随机密码代码 end