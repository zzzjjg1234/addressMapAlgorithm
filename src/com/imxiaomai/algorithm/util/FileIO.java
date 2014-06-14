package com.imxiaomai.algorithm.util;
/// @author zhaojiguang
/// @date  2014.5.31
/// read and write file

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileIO {
	public static String readGBKFile(String fname) {
		return readFile(fname, "GBK");
	}
	
	/// read then whole content of a file 
	/// return the content in the file, return "" if fail
	public static String readFile(String fname, String charset) {
		try {
			File file = new File(fname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), charset);
			
			int fileLen = (int)file.length();  /// in bytes
			char[] chars = new char[fileLen];
			int readLen = reader.read(chars);
			reader.close();
			String txt = String.valueOf(chars, 0, readLen);
			return txt;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/// write string to file
	public static void writeFile(String fname, String textToWrite, String charset) {
		try {
			File file = new File(fname);
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), charset);
			writer.write(textToWrite);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/// write byte to file
	public static void writeFile(String fname, byte[] b) {
		try {
		    FileOutputStream file = new FileOutputStream(fname);
		    file.write(b);
		    file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeGBKFile(String fname, String textToWrite) {
		writeFile(fname, textToWrite, "GBK");
	}
	
	/// test
	public static void main(String args[]) {
		String s = "abcd";
	    writeFile("a.html", s, "gbk");
	    
		String s2 = readFile("a.html", "gbk");
		System.out.println(s2);
	}
}
