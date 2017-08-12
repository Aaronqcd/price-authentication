package com.pemng.serviceSystem.base.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
/**
 * 货币金融计算用工具
 * @author shaojie
 *
 */
public class CurrencyUtil {

	public static BigDecimal add(BigDecimal one, BigDecimal two) {
		if (one == null) {
			return two;
		} else {
			if (two == null) {
				return one;
			} else {
				return one.add(two);
			}
		}
	}

	public static BigDecimal add(BigDecimal priceOne,
			BigDecimal exchangeRateOne, BigDecimal priceTwo,
			BigDecimal exchangeRateTwo, BigDecimal targetExchangeRate) {
		
		return add(adjustExchangeRate(priceOne, exchangeRateOne,targetExchangeRate), 
				adjustExchangeRate(priceTwo, exchangeRateTwo,targetExchangeRate));

	}

	public static BigDecimal adjustExchangeRate(BigDecimal price,
			BigDecimal exchangeRate, BigDecimal targetExchangeRate) {
		if (price == null) {
			return null;
		}

		return price.multiply(CurrencyUtil.getDefaultRate(exchangeRate))
				.divide(CurrencyUtil.getDefaultRate(targetExchangeRate),
						MathContext.DECIMAL128);

	}

	public static BigDecimal divide(BigDecimal one, BigDecimal two) {

		return one.divide(two, MathContext.DECIMAL128);

	}
	
	/**
	 * 根据汇率来调整价格，计算公式为price*rate<br/>
	 * 只要price不为null，就不会返回null;若exchangeRate为null,以汇率1处理
	 * @param price 汇价
	 * @param exchangeRate 汇率
	 * @return 转换后本币价
	 */
	public static BigDecimal adjustExchangeRate(BigDecimal price,
			BigDecimal exchangeRate) {

		return adjustExchangeRate(price, exchangeRate, BigDecimal.valueOf(1));

	}

	/**
	 * 如果参数为null返回1
	 * 
	 * @param rate
	 * @return
	 */
	public static BigDecimal getDefaultRate(BigDecimal rate) {
		return rate == null ? BigDecimal.valueOf(1) : rate;
	}
	
	public static BigDecimal roundToSameScale(BigDecimal toRound, BigDecimal template){
		if(toRound==null||template==null){
			return null;
		}else{
			return toRound.setScale((template.scale()>2?template.scale():2),RoundingMode.HALF_EVEN);
		}
	}
	
	public static BigDecimal roundToScale(BigDecimal toRound, Boolean isToTwo) {
		BigDecimal returnValue = null;
		if (toRound != null) {
			if (isToTwo == null || isToTwo) {
				returnValue = toRound.setScale(2, BigDecimal.ROUND_HALF_UP);
			} else {
				returnValue = toRound.setScale(4, BigDecimal.ROUND_HALF_UP);
			}

		}
		return returnValue;
	}
	
	
	/**
     * 传入金额字符串返回大写金额
     * 参数:String num 字符型金额，如 ‘￥100,200.01’
     * 注意:1、可以加‘￥’和‘,’分割金额,其他符号不识别。
     *      2、金额需在万亿以下,即整数部分只识别13位有效数字。
     *      3、小数只保留2位即‘角’和‘分’，非四舍五入，小于分的单位舍掉。
     * 例子： 传入:￥100,200.01 返回:￥壹拾万零贰佰元壹分   
     * @param num
     * @return
     */	
	public String getChineseNum(String num) {
        String rsNum = "";
        num=num.replace(",","");
        num=num.replace("￥","");
        boolean isNegative =false;
        if(num.indexOf("-")==0)
        {
            isNegative=true;
            num=num.replace("-","");
        }
        num=trimLeftZero(num);
        try{
            Double.parseDouble(num);
        }catch(Exception ex)
        {
            return "无效的数字";
        }
        if (Double.parseDouble(num) != 0) {
            String [] readyNum= new String[2];
            readyNum=num.split("\\.");
            String beforePoint=num;
            //int point2 = num.indexOf(".");
            if(num.indexOf(".")!=-1){
                beforePoint = readyNum[0];
            }
            if(beforePoint.length()>13){
                return "数位过大!";
            }
            String rsBefore = "";
            if (Double.parseDouble(beforePoint) != 0) {
                for (int i = beforePoint.length() - 1; i >= 0; i--) {
                    String beforeTemp = "";
                    beforeTemp += getChinese(Integer.parseInt(beforePoint.charAt(i) + ""));
                    beforeTemp += getChineseUnit(beforePoint.length() - i - 1);
                    rsBefore = beforeTemp + rsBefore;
                }
            }
            String rsAfter = "";
            if(num.indexOf(".")!=-1){
                String afterPoint = readyNum[1];//num.substring(point2 + 1, num.length());
                if (Double.parseDouble(afterPoint) != 0) {
                    rsAfter += getChinese(Integer.parseInt(afterPoint.charAt(0) + "")) + "角";
                    rsAfter += getChinese(Integer.parseInt(afterPoint.charAt(1) + "")) + "分";
                } else {
                    rsAfter = "整";
                }
            }else{
                rsAfter = "整";
            }
            rsNum = rsBefore + rsAfter;
            if(isNegative)
            {
                rsNum="负"+rsNum;
            }
            rsNum="￥"+trimZero(rsNum);
        } else {
            rsNum = "￥零元";
        }
        return rsNum;
    }
	
	/**
     * 按数字转换成大写数字     
     * 时间:2010-04-16
     * @param num
     * @return
     */
    private String getChinese(int num) {
        String chNum = "";
        switch (num) {
            case 0: chNum = "零"; break;
            case 1: chNum = "壹"; break;
            case 2: chNum = "贰"; break;
            case 3: chNum = "叁"; break;
            case 4: chNum = "肆"; break;
            case 5: chNum = "伍"; break;
            case 6: chNum = "陆"; break;
            case 7: chNum = "柒"; break;
            case 8: chNum = "捌"; break;
            case 9: chNum = "玖"; break;
        }
        return chNum;
    }
    /**
     * 将对应位置加上单位
     * @param index
     * @return
     */
    private String getChineseUnit(int index) {
        String chUnit = "";
        switch (index) {
            case 0: chUnit = "元"; break;
            case 1: chUnit = "拾"; break;
            case 2: chUnit = "佰"; break;
            case 3: chUnit = "仟"; break;
            case 4: chUnit = "万"; break;
            case 5: chUnit = "拾"; break;
            case 6: chUnit = "佰"; break;
            case 7: chUnit = "仟"; break;
            case 8: chUnit = "亿"; break;
            case 9: chUnit = "拾"; break;
            case 10: chUnit = "佰"; break;
            case 11: chUnit = "仟"; break;
            case 12: chUnit = "万"; break;
        }
        return chUnit;
    }
    /**
     * 消掉数字左边空格
     * @param num
     * @return
     */
    private String trimLeftZero(String num){
        String newNum="";
        int index=0;
        for(int i=0;i<num.length();i++)
        {
            if(num.charAt(i)!='0'){
                index=i;
                break;
            }
        }
        newNum=num.substring(index,num.length());
        return newNum;
    }
    /**
     * 消掉重复的单位和'零'
     * @param num
     * @return
     */
    private String trimZero(String num) {
        String rsNum =num;
        do {
            rsNum = rsNum.replace("零零", "零");
            rsNum = rsNum.replace("零亿", "亿");
            rsNum = rsNum.replace("亿万", "亿");
            rsNum = rsNum.replace("零万", "万");
            rsNum = rsNum.replace("零仟", "零");
            rsNum = rsNum.replace("零佰", "零");
            rsNum = rsNum.replace("零拾", "零");
            rsNum = rsNum.replace("零元", "元");
            rsNum = rsNum.replace("零角", "");
            rsNum = rsNum.replace("零分", "");
        } while (rsNum.indexOf("零零") != -1);
        rsNum = rsNum.replace("零元", "元");
        rsNum = rsNum.replace("零万元", "元");
        return rsNum;
    }
    
	
	
}
