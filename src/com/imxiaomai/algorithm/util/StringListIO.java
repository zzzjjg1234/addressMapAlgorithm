/// @date 2014-05
/// load string list from a file

package com.imxiaomai.algorithm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class StringListIO {
	/// return empty ArrayList if no line
	public static ArrayList<String> loadFileFailExit(String fileName) {
		BufferedReader br = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("fail to open file " + fileName);
			e.printStackTrace();
			System.exit(-1);
			return result;
		}

		String line;
		try {
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				result.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	
	public static ArrayList<String> load(String fileName) {
		return load(fileName, "GBK");
	}
	
	public static ArrayList<String> loadCommon(String fileName, String charset) {
		BufferedReader br = null;
		InputStream is = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));
		//	fileName = "data/testAddressStatSortJJ2.txt";
		//	DetailLog.instance().log("load filename:\t" + fileName);
		//	is = StringListIO.class.getClassLoader().getResourceAsStream(fileName);
			
		//	br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			
			String line = null;
			while((line = br.readLine()) != null) {
				result.add(line);
				//DetailLog.instance().log("line:\t" + line);
			}
			
			if(br != null) {
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/// return empty ArrayList if no line
	public static ArrayList<String> load(String fileName, String charset) {
		BufferedReader br = null;
		InputStream is = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			//br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));
		//	fileName = "data/testAddressStatSortJJ2.txt";
			DetailLog.instance().log("load filename:\t" + fileName);
			is = StringListIO.class.getClassLoader().getResourceAsStream(fileName);
			
			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			
			String line = null;
			while((line = br.readLine()) != null) {
				result.add(line);
				//DetailLog.instance().log("line:\t" + line);
			}
			
			if(br != null) {
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static void save(String file, ArrayList<String> list) {
		save(file, list, "GBK");
	}
	
	public static void save(String file, ArrayList<String> list, String charset) {
		if (list == null) {
			return;
		}

		BufferedWriter fw = null;
		try {
			 fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
			for (String line : list) {
				fw.write(line);
				fw.newLine();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public static void main(String args[]) {
		ArrayList<String> list = load("data/proxy.txt");
		System.out.println(list.toString());
	}
}
