package com.pemng.serviceSystem.base.util;

/**
 * 科学数字解析。
 * （完全用字符串操作，因此数字长度不受特别约束）
 * 
 * 可以解析简单的科学计数。如 formatNumberOfString("-0.123e-2"); //返回-0.00123
 * 可以将字符串解析为中文描述。如 getChineseNumber("12,345,678.90"); //返回“一千二百三十四万五千六百七十八点九零”
 * 可以将字符串解析为金额。如 getChineseAmount("+5.1230E2"); //返回“伍佰壹拾贰圆参角零分”
 * @author YangMinsheng 
 * @since 2005-06-15
 */
public final class NumAnaysis {
	
	
    //=================================================================
    // 数字格式转换
	// 待转化的数字可以任意长度，汉字最大单位用“亿”。
	// 原理：分为8为一组，单位亿，有A、B两种类型；组类再分为A、B两小组，各四位
    //=================================================================

    /**
     * 获得大写金额数字. 
     * @param num: 输入字符串格式的数字，数字长度任意
     * getChineseAmount("0.03"); //返回：零圆零参分
     * getChineseAmount("2.5"); //返回：贰圆伍角零分
     * getChineseAmount("128.90"); //返回：壹佰贰拾捌圆玖角零分
     * getChineseAmount("10,000,000,500.67"); //返回：壹佰亿零伍佰圆陆角柒分
     * getChineseAmount("1000000"); //返回：壹佰万圆整
     * getChineseAmount("+5.1230E2"); //返回：伍佰壹拾贰圆参角零分
	 * @author YangMinsheng 2005-06-15
     */
    public static String getChineseAmount(String num) throws Exception {
        String correctString = formatNumberOfString(num); //校验并获得正确的数字字符串
        String integerPart = getIntegerPart(correctString); //获得整数部分的数字字符串
        String decimalPart = getDecimalPart(correctString); //获得小数部分的数字字符串
        return getCharacterOfInteger(integerPart, true) + AMOUNT[0] + getCharacterOfAmountDecimal(decimalPart); //获得大写金额数字
    }

    /**
     * 获得小写数字
     * @param num: 输入字符串格式的数字，数字长度任意
     * getChineseNumber("1"); //返回：一
     * getChineseNumber("1000"); //返回：一千
     * getChineseNumber("1000000"); //返回：一百万
     * getChineseNumber("10000000"); //返回：一千万
     * getChineseNumber("100000000"); //返回：一亿
     * getChineseNumber("10000100000000000");//返回：一亿零一千 亿
     * getChineseNumber("1000000000000000000000000"); //返回：一亿亿亿	
     * getChineseNumber("100000000000000000000000000000000000000000000000000000000000000000000000000000000"); //返回：一亿亿亿亿亿亿亿亿亿亿	
     * getChineseNumber("10.23456"); //返回：一十点二三四五六
     * getChineseNumber("12,345,678.90"); //返回：一千二百三十四万五千六百七十八点九零
     * getChineseNumber("-5.123E-10"); //返回：负零点零零零零零零零零零五一二三
     * getChineseNumber("+100500600700"); //返回：一千零五亿零六十万零七百
	 * @author YangMinsheng 2005-06-15
     */
    public static String getChineseNumber(String num) throws Exception {
        String correctString = formatNumberOfString(num); //校验并获得正确的数字字符串
        String integerPart = getIntegerPart(correctString); //获得整数部分并校验
        String decimalPart = getDecimalPart(correctString); //获得小数部分并校验
        return getCharacterOfInteger(integerPart, false) + getCharacterOfNumDecimal(decimalPart); //获得小写汉字数字
    }

    /**
     * 获得一个字符串型的数字。并且判断正负号，e号，逗号，小数点等是否输入正确
     * 
     * formatNumberOfString("0.123e5"); //返回：12300
     * formatNumberOfString("0.123e2"); //返回：12.3
     * formatNumberOfString("0.123e0"); //返回：0.123
     * formatNumberOfString("0.123e-2"); //返回：0.00123
     * formatNumberOfString("-5.123E5"); //返回：-5.12300
     * formatNumberOfString("+5.123E2"); //返回：512.3
     * formatNumberOfString("-5.123E0"); //返回：-5.123
     * formatNumberOfString("-5.123E-10"); //返回：-0.0000000005123
     * formatNumberOfString("1,234,567,890.123,456,789,0"); //返回：1234567890.1234567890
	 * @author YangMinsheng 2005-06-15
     */
    public static String formatNumberOfString(String num) throws Exception {
        //step 1: remove sign + or -
        String sign = "";
        if (num.startsWith("+") || (num.startsWith("-"))) {
            sign = num.substring(0, 1).equals("+") ? "" : "-";
            num = num.substring(1); //截取掉正负号
        }

        //step 2: format e
        String forCheck = formatOperatorE(num);

        //step 3: check general dot
        int dotPosition = forCheck.indexOf(".");
        if (dotPosition != forCheck.lastIndexOf(".")) {
            throw new Exception("输入的数据格式不对, 小数点不能超过1个!");
        }

        //step 4: get integer and decimal part
        String integerPart = forCheck;
        String decimalPart = "";
        String rtn = null;
        if (dotPosition != -1) //说明有小数
            {
            integerPart = forCheck.substring(0, dotPosition); //获得整数部分
            decimalPart = forCheck.substring(dotPosition + 1); //获得小数部分
        }

        //step 5: remove comma
        integerPart = checkIntegerComma(integerPart);
        decimalPart = checkDecimalComma(decimalPart);

        //step 6: check whether all digit
        checkNum(integerPart);
        checkNum(decimalPart);

        //step 7: organize this number.
        if (dotPosition != -1) {
            rtn = sign + integerPart + "." + decimalPart;
        }
        else {
            rtn = sign + integerPart;
        }

        return rtn;
    }

    /**
     * 获得正负号的汉字描述.
	 * @author YangMinsheng 2005-06-15
     */
    private static String getCharacterOfSign(String num, boolean isAmount) throws Exception {
        if ((num.startsWith("-")) && (isAmount)) {
            throw new Exception("金额不能有负号!");
        }

        if (num.startsWith("-")) {
            return NAGATIVE;
        }
        else {
            return "";
        }
    }

    /**
     * 获得整数部分的汉字描述.
     * 要求输入的数值已经校验过.可以有正负号.
     * @param num: 输入的数字 
     * @param isAmount：是否返回大写金额
	 * @author YangMinsheng 2005-06-15
     */
    private static String getCharacterOfInteger(String num, boolean isAmount) throws Exception {
        String sign = getCharacterOfSign(num, isAmount);
        if (num.startsWith("+") || (num.startsWith("-"))) {
            num = num.substring(1); //截取掉正负号
        }

        int length = num.length(); //数字长度
        int team = length / 8; //八位分组的组数
        int residual = length % 8; //取余
        if (residual != 0) //若有余数，则组数要加一
            {
            team++;
        }
        else //若无余数，则改成8用于计算结束位
            {
            residual = 8;
        }
        String rtn = ""; //用于返回
        for (int i = 0; i < team; i++) {
            int bgn = (i - 1) * 8 + residual; //获得起始位
            bgn = bgn > 0 ? bgn : 0; //起始为不能小于0
            int end = i * 8 + residual; //获得结束位

            String amount = "";
            if (i == team - 1) {//最后一组才用get8A
                amount = get8A(num.substring(bgn, end), isAmount);
            }
            else {
                amount = get8B(num.substring(bgn, end), isAmount);
            }

            //当前位零，并且rtn已经后导零，就不能直接把Amount接在rtn后面
            if ((amount.equals(CHARACTERS_NUM[0])) && (rtn.endsWith(CHARACTERS_NUM[0]))) //characters[0]: "零"
                {}
            else {
                rtn += amount;
            }
        }
        rtn = trimLfZeroOfCharacter(rtn);
        rtn = trimRtZeroOfCharacter(rtn);
        if (rtn.length() == 0) {
            rtn = CHARACTERS_NUM[0]; //"零"
        }
        return sign + rtn;
    }

    /**
     * 获得金额小数的汉字描述.
     * 要求输入的数值已经校验过.
	 * @author YangMinsheng 2005-06-15
     */
    private static String getCharacterOfAmountDecimal(String decimal) throws Exception {
        int jiao = 0; //角
        int fen = 0; //分

        //以下获得角、分的数额
        if ((decimal != null) && decimal.length() > 2) {
            throw new Exception("金额的小数位有误!");
        }
        if ((decimal != null) && decimal.length() == 2) {
            jiao = decimal.charAt(0) - 48; //角
            fen = decimal.charAt(1) - 48; //分
        }
        if ((decimal != null) && decimal.length() == 1) {
            jiao = decimal.charAt(0) - 48; //角
        }

        //以下获得金额小数的汉字描述
        if ((jiao == 0) && (fen == 0)) //小数为0则返回"整"
            {
            return EXACTLY;
        }
        else if (jiao == 0) //零几分
            {
            return CHARACTERS_AMOUNT[0] + CHARACTERS_AMOUNT[fen] + AMOUNT[2];
        }
        else if (fen == 0) //几角
            {
            return CHARACTERS_AMOUNT[jiao] + AMOUNT[1] + CHARACTERS_AMOUNT[0] + AMOUNT[2]; //加上零分
        }
        else //几角几分
            {
            return CHARACTERS_AMOUNT[jiao] + AMOUNT[1] + CHARACTERS_AMOUNT[fen] + AMOUNT[2];
        }
    }

    /**
     * 获得数字小数的汉字描述.
     * 要求输入的数值已经校验过.
	 * @author YangMinsheng 2005-06-15
     */
    public static String getCharacterOfNumDecimal(String decimal) //小数
    {
        String rtn = "";
        for (int i = 0; i < decimal.length(); i++) {
            int num = decimal.charAt(i) - 48;
            rtn += CHARACTERS_NUM[num];
        }

        if (rtn.length() > 0) {
            rtn = DOT + rtn; //加上“点”字
        }

        return rtn;
    }

    /**
     * 消除前导汉字"零"
	 * @author YangMinsheng 2005-06-15
     */
    private static String trimLfZeroOfCharacter(String str) {
        if ((str == null) || (str.length() < 1)) {
            return str;
        }
        while ((str.startsWith(CHARACTERS_NUM[0])) && (str.length() > 0)) {
            str = str.substring(1, str.length());
        }
        return str;
    }

    /**
     * 消除后导汉字"零"
	 * @author YangMinsheng 2005-06-15
     */
    private static String trimRtZeroOfCharacter(String str) {
        if ((str == null) || (str.length() < 1)) {
            return str;
        }
        while ((str.endsWith(CHARACTERS_NUM[0])) && (str.length() > 0)) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 获得4位一组A(长度不超过四)
     * 例如：（以数字为例）
     * 1234返回一千二百三十四
     * 0056返回零五十六
     * 0700返回零七百
     * 8009返回八千零九
     * 23返回二十三
	 * @author YangMinsheng 2005-06-15
     */
    //允许前导一个零，不可后导零，中间只可一个零，四个都是0则返回一个零
    private static String get4A(String str, boolean isAmount) throws Exception {
        if (str.length() > 4) {
            throw new Exception("Number convert error!");
        }
        String myChars[];
        String mycarry[] = new String[4];
        if (isAmount) {
            myChars = CHARACTERS_AMOUNT;
            mycarry[3] = CARRY_AMOUNT[2]; //"仟"
            mycarry[2] = CARRY_AMOUNT[1]; //"佰"
            mycarry[1] = CARRY_AMOUNT[0]; //"拾"
            mycarry[0] = "";
        }
        else {
            myChars = CHARACTERS_NUM;
            mycarry[3] = CARRY_NUM[2]; //"千"
            mycarry[2] = CARRY_NUM[1]; //"百"
            mycarry[1] = CARRY_NUM[0]; //"十"
            mycarry[0] = "";
        }

        String rtn = "";
        for (int i = str.length(); i > 0; i--) //必须从个位数开始,才便于解决length<4的情况
            {
            if ((str.charAt(i - 1) == '0') && (!rtn.startsWith(CHARACTERS_NUM[0]))) {
                rtn = CHARACTERS_NUM[0] + rtn;
            }
            else if ((str.charAt(i - 1) != '0')) {
                int num = str.charAt(i - 1) - 48;
                rtn = myChars[num] + mycarry[str.length() - i] + rtn;
            }
        }
        rtn = trimRtZeroOfCharacter(rtn); //不可后导零（不必写在for循环的逻辑中）
        if (rtn.length() == 0) {
            return CHARACTERS_NUM[0];
        }
        else {
            return rtn;
        }
    }

    /**
     * 获得4位一组B
	 * @author YangMinsheng 2005-06-15
     */
    private static String get4B(String str, boolean isAmount) throws Exception {
        String num = get4A(str, isAmount);
        //		if (isAmount)
        //		{
        return num.equals(CHARACTERS_NUM[0]) ? num : num + CARRY_AMOUNT[3];
        //		}
        //		else
        //		{
        //			return num.equals(CHARACTERS_NUM[0]) ? num : num + CARRY_NUM[3];
        //		}
    }

    /**
     * 获得8位一组A
	 * @author YangMinsheng 2005-06-15
     */
    //允许前导一个零，不可后导零，中间只可一个零，8个都是0则返回一个零
    //这里则须考虑不可后导零，其余已经可由 get4A解决
    //汉字数字存在唯一不符合此规律特殊情况,使用亿的时候允许叠加,如一亿亿,上述规则会在叠加亿中出现零,
    //当不是完整的叠加亿时,出现零是正常的,如一亿零一千 亿(1 0000 1000 0000 0000).
    private static String get8A(String str, boolean isAmount) throws Exception {
        String str4A = "";
        String str4B = "";
        if (str.length() <= 4) {
            str4A = str;
        }
        else {
            str4A = str.substring(str.length() - 4, str.length());
            str4B = str.substring(0, str.length() - 4);
        }
        String charStr4B = get4B(str4B, isAmount);
        String charStr4A = get4A(str4A, isAmount);
        if ((charStr4B.equals(CHARACTERS_NUM[0])) && (charStr4A.equals(CHARACTERS_NUM[0]))) //若 4A,4B 都是零
            {
            return CHARACTERS_NUM[0]; //返回一个零，以免连接多个零
        }
        else if (charStr4A.equals(CHARACTERS_NUM[0])) //若4a为零
            {
            return charStr4B; //则返回4b，不要后导零
        }
        else if ((charStr4B.equals(CHARACTERS_NUM[0])) && (charStr4A.startsWith(CHARACTERS_NUM[0]))) //若4b为零，4a以零开头，
            {
            return charStr4A; //返回4a, 不要4b，以防前导多个零
        }
        else {
            return charStr4B + charStr4A;
        }
    }

    /**
     * 获得8位一组B
	 * @author YangMinsheng 2005-06-15
     */
    private static String get8B(String str, boolean isAmount) throws Exception {
        if (str.length() > 8) {
            throw new Exception("Number convert error!");
        }
        String amount = get8A(str, isAmount);
        return amount.equals(CHARACTERS_NUM[0]) ? CARRY_AMOUNT[4] : amount + CARRY_AMOUNT[4];
    }

    /**
     * 去掉逗号 (整数部分的逗号检查，不进行其它检查。)
     * (如果其中有小数点则认为错误数据)
	 * @author YangMinsheng 2005-06-15
     */
    private static String checkIntegerComma(String str) throws Exception {
        String rtn = "";
        //ringTokenizer words = new StringTokenizer(str, ","); //发现StringTokenizer做法不认为连接逗号为错误数据，故不用

        int triBgn = 0; //逗号是以三位为一组的，triBgn用来保存起始位
        int triEnd = str.indexOf(","); //triEnd用来保存结束位
        if (triEnd == -1)
            return str;
        for (int i = triEnd; triEnd != str.length(); i = str.indexOf(",", triBgn)) {
            triEnd = i == -1 ? str.length() : i; //获得结束位
            String tri = str.substring(triBgn, triEnd); //获得三位一组
            if (((triBgn == 0) && (tri.length() > 3)) //整数部分的逗号检查,最高位处的逗号分割不能超过3个数字
                || ((triBgn > 0) && (tri.length() != 3))) //整数部分的逗号检查, 其他地方的逗号分割必须是3个数字
                {
                throw new Exception("输入的数据格式不对!");
            }
            rtn += tri;
            triBgn = i + 1;
        }

        return rtn;
    }

    /**
     * 去掉逗号 (小数部分的逗号检查，不进行其它检查。)
     * (如果其中有小数点则认为错误数据)
	 * @author YangMinsheng 2005-06-15
     */
    private static String checkDecimalComma(String str) throws Exception {
        String rtn = "";
        //ringTokenizer words = new StringTokenizer(str, ","); //发现StringTokenizer做法不认为连接逗号为错误数据，故不用

        int triBgn = 0; //逗号是以三位为一组的，triBgn用来保存起始位
        int triEnd = str.indexOf(","); //triEnd用来保存结束位
        if (triEnd == -1)
            return str;
        for (int i = triEnd; triEnd != str.length(); i = str.indexOf(",", triBgn)) {
            triEnd = i == -1 ? str.length() : i; //获得结束位
            String tri = str.substring(triBgn, triEnd); //获得三位一组
            if (((triBgn == str.lastIndexOf(",") + 1) && (tri.length() > 3)) //小数部分的逗号检查,最低位处的逗号分割不能超过3个数字
                || ((triBgn < str.lastIndexOf(",") + 1) && (tri.length() != 3))) //小数部分的逗号检查, 其他地方的逗号分割必须是3个数字
                {
                throw new Exception("输入的数据格式不对!");
            }
            rtn += tri;
            triBgn = i + 1;
        }

        return rtn;
    }

    /**
     * 把含有E的数字格式化为不含E。
     * 只进行相关检查，比如.号，e号，不进行其它检查，例如正负号，逗号等。
     * 要求输入的num不含正负号。
	 * @author YangMinsheng 2005-06-15
     */
    private static String formatOperatorE(String num) throws Exception {
        int positionE = num.lastIndexOf("E") == -1 ? num.lastIndexOf("e") : num.lastIndexOf("E");
        if (positionE == -1) {
            return num; //不含e或E
        }
        int anotherPositionE = num.indexOf("E") == -1 ? num.indexOf("e") : num.indexOf("E");
        if (anotherPositionE != positionE) {
            throw new Exception("输入的数据格式不对,只能由一个e!");
        }

        int positionDot = num.indexOf(".");
        if (positionDot != 1 || positionDot != num.lastIndexOf(".")) {
            throw new Exception("输入的数据格式不对,小数点不正确!");
        }

        String eCount = num.substring(positionE + 1); //获得E后面的数字，可为正数,0或负数
        String rtn = num.substring(0, positionE); //去掉E及其后面数字
        int carry = 0;
        try {
            carry = Integer.parseInt(eCount);
        }
        catch (NumberFormatException e) {
            throw new Exception("符号e后必须是有效数字!");
        }

        if (carry > 0) {
            rtn = rtn.substring(0, 1) + rtn.substring(2); //去掉小数点
            int zeroCount = carry - rtn.length() + 1;

            if (zeroCount < 0) {
                rtn = rtn.substring(0, rtn.length() + zeroCount) + "." + rtn.substring(rtn.length() + zeroCount);
            }
            else {
                for (int i = 0; i < zeroCount; i++) {
                    rtn += "0";
                }
            }

            if (rtn.startsWith("0"))
                rtn = rtn.substring(1);
        }
        else if (carry < 0) {
            rtn = rtn.substring(0, 1) + rtn.substring(2); //去掉小数点
            int zeroCount = (0 - carry) - 1;
            for (int i = 0; i < zeroCount; i++) {
                rtn = "0" + rtn;
            }
            rtn = "0." + rtn;
        }

        return rtn;
    }

    /**
     * 检查输入的num是否全部是数字。
	 * @author YangMinsheng 2005-06-15
     */
    private static void checkNum(String num) throws Exception {
        char[] chr = num.toCharArray(); //检查其它符号
        for (int i = 0; i < chr.length; i++) {
            if (!Character.isDigit(chr[i])) {
                throw new Exception("输入的数据格式不对!");
            }
        }
    }

    /**
     * 获得整数部分的数字,
     * 要求输入的数据已经完全校验。
	 * @author YangMinsheng 2005-06-15
     */
    private static String getIntegerPart(String value) throws Exception {
        int dotPosition = value.indexOf(".");
        if (dotPosition != -1) //说明有小数
            value = value.substring(0, dotPosition); //截取掉小数
        return value;
    }

    /**
     * 校验并获得小数部分的数组,
     * 要求输入的数据已经完全校验。
	 * @author YangMinsheng 2005-06-15
     */
    private static String getDecimalPart(String value) throws Exception {
        int dotPosition = value.indexOf(".");
        if (dotPosition != -1) //说明是有小数
            return value.substring(dotPosition + 1); //截取掉整数 (+1是去掉小数点)
        else
            return "";
    }
    

	public static void main(String[] args) {
		try {
			System.out.println(getChineseAmount("0.03"));
			System.out.println(getChineseAmount("10,000,000,500.67"));
			System.out.println(getChineseAmount("+5.1230E2"));
			System.out.println(getChineseAmount("1000000"));
			System.out.println(getChineseAmount("100000000000000000000010"));
			System.out.println(getChineseNumber("1000000001.01"));
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

    //数字相关常量
    public final static String CHARACTERS_NUM[] = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    public final static String CARRY_NUM[] = { "十", "百", "千", "万", "亿" };
    public final static String DOT = "点";
    public final static String NAGATIVE = "负";

    //金额相关常量
    public final static String CHARACTERS_AMOUNT[] = { "零", "壹", "贰", "参", "肆", "伍", "陆", "柒", "捌", "玖" };
    public final static String CARRY_AMOUNT[] = { "拾", "佰", "仟", "万", "亿" };
    public final static String[] AMOUNT = { "圆", "角", "分" };
    public final static String EXACTLY  = "整";

}
