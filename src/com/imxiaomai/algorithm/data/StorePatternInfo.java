package com.imxiaomai.algorithm.data;

import java.util.regex.Pattern;

public class StorePatternInfo{
	String strPattern;
	/**
	 * @return the strPattern
	 */
	public String getStrPattern() {
		return strPattern;
	}
	/**
	 * @param strPattern the strPattern to set
	 */
	public void setStrPattern(String strPattern) {
		this.strPattern = strPattern;
	}
	/**
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}
	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	Pattern pattern;
	
}