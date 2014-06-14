/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.analyse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.imxiaomai.algorithm.util.File;
import com.imxiaomai.algorithm.util.Sort;


/**
 * 字典文件，支持单词的导入和查找
 * @author 吴建强
 *
 */
public class Dictionary {

	/**
	 * 字典文件名的开始
	 */
	public static final String DICNAME = "DicData";
	/**
	 * 字典数据库，每项为一个SubDictionary
	 */
	private List database = null;
	
	/**
	 * 构造函数，初始化时加载字典
	 *
	 */
	public Dictionary(){
		load();
	}

	/**
	 * 取字典文件所在的路径
	 * @return
	 */
	private String getDicPath(){
		String url = "";
		Class theClass = Dictionary.class;
		url = theClass.getResource("").toString();
		url = url.substring("file:/".length(), url.length());
		url += "/dic";
		return url;
	}
	/**
	 * 加载字典
	 */
	public void load(){
		if (database != null) return;
		SubDictionary sub = null;
		database = new ArrayList();
		java.io.File[] files = File.getFiles(getDicPath(), new DicFileNameFilter());
		for (int i = 0; i < files.length; i++){
			sub = new SubDictionary(files[i].toString());
			sub.load();
			database.add(sub);
		}
	}
	/**
	 * 导入单词，url为要导入单词的文件，每个单词放一行
	 * @param url
	 */
	public int importDic(String url){
		BufferedReader br = null;
		String temp = null;
		Word word = null;
		SubDictionary sub = null;
		int i = 0;
		int count = 0;
		
		br = File.openByReader(url);
		try {
			while(true){
				temp = br.readLine();
				if (temp == null) break;
				if (temp.length() < 2) continue;
				word = new Word(temp);
				sub = findSubDictionary(word);
				if (sub == null){
					sub = new SubDictionary(getDicPath() + "/" + DICNAME + database.size());
					sub.setWordLen(word.getLength());
					database.add(sub);					
				}
				if (sub.insert(word)) count++;
				System.out.println("importing " + word.getContent());
			}
			for (i = 0; i < database.size(); i++){
				sub = (SubDictionary)database.get(i);
				sub.save();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 查找与word长度相等的SubDictionary
	 * @param word
	 * @return
	 */
	private SubDictionary findSubDictionary(Word word){
		SubDictionary sub = null;
		for (int i = 0; i < database.size(); i++){
			sub = (SubDictionary)database.get(i);
			if (sub.getWordLen() == word.getLength()) return sub;
		}
		return null;
	}
	/**
	 * 取得字典每个SubDictionary单词的长度
	 * @return
	 */
	public int[] getDBWordLen(){
		int[] wordLen = new int[database.size()];
		int i = 0;
		for (i = 0; i < wordLen.length; i++){
			wordLen[i] = ((SubDictionary)database.get(i)).getWordLen();
		}
		wordLen = Sort.bubbleDesc(wordLen);
		return wordLen;
	}
	/**
	 * 在当前字典中查找单词src，找到返回true，否则返回false
	 * @param source
	 * @return
	 */
	public boolean find(String src){
		Word word = new Word(src);
		SubDictionary sub =  findSubDictionary(word);
		if (sub == null) return false;
		if (!sub.search(word)) return false;
		return true;
	}
}
