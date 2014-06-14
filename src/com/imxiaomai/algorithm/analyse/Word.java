/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.analyse;
/**
 * 描述一个单词的类
 * @author 吴建强
 *
 */
public class Word implements Comparable {

	/**单词的内容*/
	private String content = "";
	/**单词每个字符的整型数值和，用于二分排序*/
	private int value = 0;
	/**每个单词的字数*/
	private int length = 0;
	
	/**
	 * 默认构造函数
	 *
	 */
	public Word(){
		
	}
	/**
	 * 构造函数，根据content初始化单词
	 * @param content
	 */
	public Word(String content){
		setContent(content);
	}
	/**
	 * 返回单词的长度
	 * @return
	 */
	public int getLength() {
		return length;
	}
	/**
	 * 返回单词的内容
	 * @return
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置单词的文本，根据文本计算单词的value和length
	 * @param content
	 */
	public void setContent(String content) {
		int i;
		this.content = content;
		value = 0;
		for (i = 0; i < content.length(); i++){
			value += content.charAt(i);
		}
		length = content.length();
	}
	/**
	 * 取单词的整数值
	 * @return
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 比较两个word是否完全相等
	 * @param word
	 * @return
	 */	
	public boolean equals(Word word){
		if (!content.equals(word.getContent())) return false;
		return true;
	}
	/**
	 * 比较方法，大于返回1，小于返回-1，等于返回0
	 * @param arg0
	 * @return
	 */
	public int compareTo(Object obj) {
		Word word = (Word)obj;
		if (equals(word)) return 0;
		if (value > word.getValue()) return 1;
		else return -1;
	}
}
