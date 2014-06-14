/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.util;
/**
 * 排序算法类
 * @author 吴建强
 *
 */
public class Sort {

	/**
	 * 冒泡排序
	 * @param source
	 * @return
	 */
	public static int[] bubbleDesc(int[] source){
		int i = 0, j = 0;
		int k = 0;
		for (i = 1; i < source.length; i++){
			for (j = 0; j < source.length - i; j++){
				if (source[j] > source[j + 1]){
					k = source[j + 1];
					source[j + 1] = source[j];
					source[j] = k;
				}
			}
		}
		return source;
	}


}
