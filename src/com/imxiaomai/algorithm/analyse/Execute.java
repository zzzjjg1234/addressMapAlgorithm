package com.imxiaomai.algorithm.analyse;

import com.imxiaomai.algorithm.util.File;
/**
 * 必须先导入词库，才能开始分词
 * @author Administrator
 *
 */
public class Execute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		//导入词库，test.txt是词库文件，每个单词一行
		Dictionary dictionary = new Dictionary();
		long start = System.currentTimeMillis();
		int count = dictionary.importDic("c:/dic.txt");
		long end = System.currentTimeMillis();
		System.out.println("导入完成，用时：" + (end - start) + " 共导入单词：" + count);
		*/
		/*
		//指定文件分词，source.txt是要分词的文本文件，必须先导入词库
		String str = File.open("c:/source.txt");
		Dictionary dictionary = new Dictionary();
		MaxMatchSegment seg = new MaxMatchSegment();
		int length = str.length();
		long start = System.currentTimeMillis();
		str = seg.segment(str, dictionary);
		long end = System.currentTimeMillis();
		System.out.println(str);
		System.out.println("用时：" + (end - start) + " 字符数：" + length);
		*/
		
		//分解字符串，必须先导入词库
		Dictionary dictionary = new Dictionary();
		String str = "他的态度和服务很差";
		MaxMatchSegment seg = new MaxMatchSegment();
		int length = str.length();
		long start = System.currentTimeMillis();
		str = seg.segment(str, dictionary);
		long end = System.currentTimeMillis();
		System.out.println(str);
		System.out.println("用时：" + (end - start) + " 字符数：" + length);
		

	}

}
