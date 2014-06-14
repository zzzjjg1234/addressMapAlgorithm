/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.analyse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


import  com.imxiaomai.algorithm.util.File;
import  com.imxiaomai.algorithm.util.Search; 
/**
 * 子字典，每个子字典是字典的一个部分，子字典的每个单词长度都相等
 * @author 吴建强
 *
 */
public class SubDictionary {
	/**
	 * 字典单词的长度
	 */
	private int wordLen = 0;
	/**
	 * 字典数据
	 */
	private List data = null;
	/**
	 * 字典文件位置
	 */
	private String url = "";
	
	/**
	 * 初始化子字典
	 * @param url，子字典的url
	 */
	public SubDictionary(String url){
		this.url = url;
		data = new ArrayList();
	}
	
	/**
	 * 加载文件
	 * @param url，文件的位置
	 */
	public void load(){
		Word word = null;
		BufferedReader br = null;
		String temp = "";
		
		if (data == null) data = new ArrayList();
		br = File.openByReader(url);
		if (br != null){
			try {
				while(true){
					temp = br.readLine();
					if (temp == null) break;
					word = new Word(temp);
					data.add(word);
					wordLen = word.getLength();
				}
				br.close();
			} catch (IOException e) {
				System.out.println("dictionary load error, " + url);
			}
		}
		
	}
	/**
	 * 导入单词，检查word的长度是否和当前字典的一致
	 * @param word，要导入的单词
	 * @return
	 */
	public boolean insert(Word word){
		if (word.getLength() != wordLen) return false;
		int pos = Search.binarySearch(data, word);
		if (pos == -1) return false;
		data.add(pos, word);		
		return true;
	}
	/**
	 * 保存字典
	 *
	 */
	public void save(){
		if (data.size() == 0) return;
		
		Word word = null;
		StringWriter strWriter = new StringWriter();
		BufferedWriter bw = new BufferedWriter(strWriter);
		try {
			for (int i = 0; i < data.size(); i++){
				word = (Word)data.get(i);
				bw.write(word.getContent());
				bw.newLine();
			}
			bw.close();
			File.writeFile(url, strWriter.getBuffer().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 在字典查找word，找到返回true
	 * @param word
	 * @return
	 */
	public boolean search(Word word){
		if (-1 == Search.binarySearch(data, word)){
			return true;
		}else {
			return false;
		}
			
	}
	/**
	 * 在字典查找word，找到返回true
	 * @param word
	 * @return
	 */
	public boolean search(String word){
		return search(new Word(word));
	}
	/**
	 * 返回字典的单词长度
	 * @return
	 */
	public int getWordLen() {
		return wordLen;
	}
	/**
	 * 设置字典的单词长度
	 * @param wordLen
	 */
	public void setWordLen(int wordLen) {
		this.wordLen = wordLen;
	}
}
