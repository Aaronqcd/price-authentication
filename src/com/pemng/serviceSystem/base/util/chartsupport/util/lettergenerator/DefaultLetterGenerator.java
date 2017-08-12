package com.pemng.serviceSystem.base.util.chartsupport.util.lettergenerator;

public class DefaultLetterGenerator implements LetterGenerator {
	private char currentLetter;

	public DefaultLetterGenerator() {
		currentLetter = 'A';
		currentLetter--;
	}

	
	public String getNextLetter() {
		currentLetter++;
		return new String(new char[] { currentLetter });
	}

	
	public void reset() {
		currentLetter = 'A';
		currentLetter--;
	}

	public static void main(String[] args) {
		LetterGenerator g = new DefaultLetterGenerator();
		for (int i = 0; i < 30; i++) {
			System.out.println(g.getNextLetter());
		}
	}
}
