/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.util;
/**
 * 字符检查,检查是否是有效字符,比如是否是中文,英文字符等,包括全角和半角
 * @author 吴建强
 *
 */
public class CharTest {
	/**其它字符*/
	public static final int OTHER 		= -1; 
	/**文本块的文本属性是数字，包含全角和半角*/
	public static final int NUMBER 		= 0; 
	/**文本块的文本属性是英文字母，包含全角和半角*/
	public static final int CHAR 		= 1; 
	/**文本块的文本属性是中文数字，如一*/
	public static final int CHNNUMBER 	= 2; 
	/**文本块的文本属性是中文字符*/
	public static final int CHINESE 	= 3; 
	public static final int SPECIALSYMBOL = 4;
	
	/**
	 * 检查c是否是中文字符,如果是返回true
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c){
		int i = (int)c;
		if (i >= 19968 && i <= 40869) return true;
		else return false;
	}
	/**
	 * 检查c是否是0——9，包括全角和半角,如果是返回true
	 * @param c
	 * @return
	 */
	public static boolean isNumber(char c){
		int i = (int)c;
		if ((i >= 48 && i <= 57) || (i >= 65296 && i <= 65305 ))
			return true;
		else return false;
	}
	/**
	 * 检查c是否是字母，包括大小写，全角和半角,如果是返回true
	 * @param c
	 * @return
	 */
	public static boolean isChar(char c){
		int i = (int)c;
		if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122) ||
				(i >= 65313 && i <= 65338) || (i >= 65345 && i <= 65370))
			return true;
		else return false;
	}
	/**
	 * 检查c是否是中文的一至九,如果是返回true
	 * @param c
	 * @return
	 */
	public static boolean isChnNumber(char c){
		if (c == '一') return true;
		if (c == '二') return true;
		if (c == '三') return true;
		if (c == '四') return true;
		if (c == '五') return true;
		if (c == '六') return true;
		if (c == '七') return true;
		if (c == '八') return true;
		if (c == '九') return true;
		else return false;
	}
	/**
	 * 检查一个字符属于什么类型
	 * @param c
	 * @return
	 */
	public static int testChar(char c){
		if (isChinese(c)) return CHINESE;
		if (isChar(c)) return CHAR;
		if (isNumber(c)) return NUMBER;
		if (isSpecilSymbol(c)) return SPECIALSYMBOL;
		else return OTHER;
	}
	public static boolean isSpecilSymbol(char c){
		if (c == '(') return true;
		if (c == ')') return true;
		if (c == '（') return true;
		if (c == '）') return true;
		if (c == '，') return true;
		if (c == ',') return true;
		if (c == ' ') return true;
		//if (c == '-') return true;
		return false;
		
		
	}
}
