/**
 * 
 */
package com.pemng.serviceSystem.base.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * 唯一随机数生成器
 * 
 * @author luo Hanbin
 * 
 */
public class UniqueNumberGenerator implements IdentifierGenerator {

	private static short sequence = 1;

	private static long lastCounter = 1;

	public UniqueNumberGenerator() {

	}

	protected static short getCount(int numDigit) {
		synchronized (UniqueNumberGenerator.class) {
			int numDigitPad = numDigit - 13;
			if (sequence >= Math.pow(10.0, numDigitPad) - 1)
				sequence = 1;
			return sequence++;
		}
	}

	public static String getUniqueID(int numDigit) {
		long time = System.currentTimeMillis();
		String result;
		if (numDigit > 13) {
			int numDigitPad = numDigit - 13;
			// if numdigit is 15, then need to pad 0-99
			Integer IntSeq = new Integer(getCount(numDigit));
			String stringSeq = IntSeq.toString();
			stringSeq = StringUtils.leftPad(stringSeq, numDigitPad, '0');
			result = String.valueOf(time).concat(stringSeq);
		} else {
			// in case the time given is the same with previous, need advance
			// the counter to make them diff
			// lastCounter should be less tha current time, if it is bigger,
			// means it is counted advanced.
			if (lastCounter >= time) {
				time = lastCounter + 1;
			}
			lastCounter = time;
			result = String.valueOf(time).substring(13 - numDigit);
		}
		return result;
	}

	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		return getUniqueID(20);
	}

	public static void main(String[] args) throws Exception {

		System.out.println("UniqueID is "
				+ UniqueNumberGenerator.getUniqueID(20));
	}

}
