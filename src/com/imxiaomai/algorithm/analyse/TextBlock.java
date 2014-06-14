/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.analyse;
/**
 * 分词的文本块，每个文本块存放一组合法的文本，没有标点符号或其它非法文本，
 * 只有全角和半角的0——9，A——Z，a——z和中文
 * @author 吴建强
 *
 */
public class TextBlock {

	/**
	 * 标识文本块内的属性
	 */
	private int property = -1;
	/**
	 * 文本存放缓冲区 
	 */
	private String text = "";
	/**
	 * 初始化
	 * @param text，要放置的文本
	 * @param property，该文本属性
	 */
	public TextBlock(String text, int property){
		this.text = text;
		this.property = property;
	}
	/**
	 * 去文本
	 * @return
	 */
	public String getText() {
		return text;
	}
	/**
	 * 设置文本
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 返回文本的属性
	 * @return
	 */
	public int getProperty() {
		return property;
	}
	/**
	 * 设置文本的属性
	 * @param property
	 */
	public void setProperty(int property) {
		this.property = property;
	}
	
}
