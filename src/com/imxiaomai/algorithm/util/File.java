/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
/**
 * 文件常用操作类
 * @author 吴建强
 *
 */
public class File {

	/**
	 * 打开文件，返回打开的BufferedReader
	 * @param url,文件地址
	 * @return
	 */
	public static BufferedReader openByReader(String url){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(url));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			br = null;
		}
		return br;
	}
	/**
	 * 打开文件，返回读取的文本，文本以GBK读取
	 * @param url,文件地址
	 * @return, 返回strng，用GBK读取
	 */
	public static String open(String url){
		FileInputStream fis = null;
		String result = "";
		byte[] buffer = new byte[10 * 1024];
		byte[] temp = null;
		int count = 0;
		try {
			fis = new FileInputStream(url);
			while(true){
				count = fis.read(buffer);
				if (count == -1) break;
				temp = new byte[count];
				System.arraycopy(buffer, 0, temp, 0, count);
				result += new String(temp, "GBK");
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 写文件，按GBK编码格式写入
	 * @param url，文件名
	 * @param content，写入的内容
	 * @return
	 */
	public static boolean writeFile(String url, String content){
		java.io.File file = new java.io.File(url);
		String parent = file.getParent();
		java.io.File dir = new java.io.File(parent);
		if (!dir.exists()) dir.mkdirs();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(url);
			fos.write(content.getBytes("GBK"));
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("file not found, " + url);
			return false;
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException, " + url);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 取得某一路径下的所有文件
	 * @param path
	 * @return
	 */
	public static java.io.File[] getFiles(String path, FilenameFilter filter){
		java.io.File file = new java.io.File(path);
		return file.listFiles(filter);
	}

}
