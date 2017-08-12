package com.pemng.serviceSystem.base.util.chartsupport.util.lettergenerator;

/**
 * 信息地雷标签生成器
 *
 * 用于生成一个event的letter
 */
public interface LetterGenerator {
	/**
	 * 重置生成器
	 */
	void reset();
	
	/**
	 * 生成一个letter
	 * @return 生成的letter
	 */
	String getNextLetter();
}
