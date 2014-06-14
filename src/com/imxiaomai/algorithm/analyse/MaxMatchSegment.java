/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.analyse;

import java.util.ArrayList;
import java.util.List;
import com.imxiaomai.algorithm.util.CharTest;
import com.imxiaomai.algorithm.util.File;
import com.imxiaomai.algorithm.util.Search;


/**
 * 最大匹配分词，对每个单句按正向和逆向分词，取最小分词数的结果，如果分词数量相等，取逆向分词的结果
 * @author 吴建强
 *
 */
public class MaxMatchSegment implements Segment {
	/**
	 * 文本块存放的缓冲区
	 */
	private List lstTextBlock = null;
	/**
	 * 用于分词的字典
	 */
	private Dictionary dictionary = null;
	
	/**
	 * 实现分词的接口，可以用不同的字典对不同文本分词
	 */
	public String segment(String source, Dictionary dictionary) {
		String result = "";
		this.dictionary = dictionary;
		filter(source);
		result = make();
		return result;
	}
	/**
	 * 根据lstTextBlock的过滤内容生成拆分字符串
	 * @return
	 */
	private String make(){
		String result = "";
		TextBlock block = null;
		int i = 0;
		if (lstTextBlock == null) return null;
		for (i = 0; i < lstTextBlock.size(); i++){
			block = (TextBlock)lstTextBlock.get(i);
			result += operate(block);
		}
		return result;
	}
	/**
	 * 处理block数据，当是中文字符时拆分，其它情况均不拆分
	 * @param block
	 * @return
	 */
	private String operate(TextBlock block){
		String result = "";
		List list1 = null;
		List list2 = null;
		if (block.getProperty() != CharTest.CHINESE){
			result = block.getText() + " ";
		}else{
			list1 = segmentChnLToR(block.getText());
			list2 = segmentChnRToL(block.getText());
			result = compare(list1, list2);
		}
		return result;
	}
	/**
	 * 比较正向和反向两个最大匹配，返回分词结果，当两个方向的分词结果一致，返回字符串，
	 * 当不一致，返回长度小的，当长度一致，返回反向的
	 * @param list1
	 * @param list2
	 * @return
	 */
	private String compare(List list1, List list2){
		List list = null;
		String result = "";
		int i;
		String temp = "";
		boolean equal = true;
		int len1 = 0;
		int len2 = 0;
		int len = 0;
		
		if (list1 == null) return "";
		if (list2 == null) return "";
		//测试用，当两个list长度不相等时，输出两种分词结果，便于分析
		if (list1.size() != list2.size()) equal = false;
		else{
			for (i = 0; i < list1.size(); i++){
				if (!((String)list1.get(i)).equals((String)list2.get(i))){
					equal = false;
					break;
				}
			}
		}
		if (!equal){
			temp = "";
			for (i = 0; i < list1.size(); i++){
				temp += list1.get(i) + " ";
			}
			System.out.println("正向分词：" + temp);
			temp = "";
			for (i = 0; i < list2.size(); i++){
				temp += list2.get(i) + " ";
			}
			System.out.println("反向分词：" + temp);
			
		}
		
		
		if (list1.size() < list2.size()){
			list = list1;
		}else if (list1.size() > list2.size()){
			list = list2;
		}else{
			for (i = 0; i < list1.size(); i++){
				len = ((String)list1.get(i)).length();
				if (len1 < len) len1 = len;
				len = ((String)list2.get(i)).length();
				if (len2 < len) len2 = len;
			}
			if (len1 > len2) list = list1;
			else list = list2;
		}
		for (i = 0; i < list.size(); i++){
			result += list.get(i) + " ";
		}
		return result;
	}
	/**
	 * 拆分一段仅仅包含中文字符的串，按从右至左反向匹配
	 * @param source
	 * @return
	 */
	private List segmentChnRToL(String source){
		//String result = "";
		String temp = "";
		List list = new ArrayList();
		List list1 = new ArrayList();
		int[] wordLen = dictionary.getDBWordLen();
		int start = 0;
		int length = source.length();
		int minReadLen = 0;
		if (wordLen.length == 0) return null;
		minReadLen = wordLen[0];
		while (start < length){
			minReadLen = wordLen[wordLen.length - 1];
			if (minReadLen > (length - start)){
				minReadLen = length - start;
			}
			while(minReadLen > 1){
				if (!Search.binarySearch(wordLen, minReadLen)){
					minReadLen--;
					continue;
				}
				temp = source.substring(length - start - minReadLen, length - start);
				if (dictionary.find(temp)){
					break;
				}else{
					minReadLen--;
				}
			}
			temp = source.substring(length - start - minReadLen, length - start);
			list.add(temp);
			start += minReadLen;
		}
		for (int i = list.size() - 1; i >= 0; i--){
			temp = (String)list.get(i);
			list1.add(temp);
		}
		return list1;
	}
	/**
	 * 拆分一段仅仅包含中文字符的串，按从左至右正向匹配
	 * @param source
	 * @return
	 */
	private List segmentChnLToR(String source){
		//String result = "";
		String temp = "";
		List list = new ArrayList();
		int[] wordLen = dictionary.getDBWordLen();
		int start = 0;
		int length = source.length();
		int minReadLen = 0;
		if (wordLen.length == 0) return null;
		minReadLen = wordLen[0];
		while (start < length){
			minReadLen = wordLen[wordLen.length - 1];
			if (minReadLen > (length - start)){
				minReadLen = length - start;
			}
			while(minReadLen > 1){
				if (!Search.binarySearch(wordLen, minReadLen)){
					minReadLen--;
					continue;
				}
				temp = source.substring(start, start + minReadLen);
				if (dictionary.find(temp)){
					break;
				}else{
					minReadLen--;
				}
			}
			temp = source.substring(start, start + minReadLen);
			list.add(temp);
			start += minReadLen;
		}
		//return result;
		return list;
	}
	/**
	 * 过滤要分割的串，过滤后的结果存放于lstTextBlock
	 * @param source
	 */
	private void filter(String source){
		lstTextBlock = new ArrayList();
		TextBlock block = null;
		String temp = "";
		int type = 0;
		int tmpType = 0;
		int i = 0;
		char c;
		c = source.charAt(0);
		type = CharTest.testChar(c);
		temp += c;
		for (i = 1; i < source.length(); i++){
			c = source.charAt(i);
			tmpType = CharTest.testChar(c);
			if (tmpType == type){
				temp += c;
			}else{
				block = new TextBlock(temp, type);
				lstTextBlock.add(block);
				type = tmpType;
				temp = "" + c;
			}
		}
		block = new TextBlock(temp, type);
		lstTextBlock.add(block);
	}

	
}
