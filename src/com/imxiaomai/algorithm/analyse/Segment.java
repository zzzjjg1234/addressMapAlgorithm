/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.analyse;

/**
 * 分词接口
 * @author 吴建强
 *
 */

public interface Segment {
	/**
	 * 按给定的字典对文本分词
	 * @param source，要分词的文本
	 * @param dictionary，给定的字典
	 * @return
	 */
	public String segment(String source, Dictionary dictionary);
}
