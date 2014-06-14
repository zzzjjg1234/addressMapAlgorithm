/// @author lizhide
/// @date 2012.04
/// detail log, many many lines, thread safe

package com.imxiaomai.algorithm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLog {
	String lastFileName;
	String baseFileName;
	long lastTime;
	
	
	public FileLog(String filename) {
		this(filename, true);
	}
	
	public FileLog(String filename, boolean append) {
		try {
			this.baseFileName = filename;
			filename +="_" + curTimef(); 
			this.lastFileName = curTimef();
			this.lastTime = (new Date()).getTime();
			mkdirForPathname(filename);
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, append), "GBK"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void mkdirForPathname(String pathname) {
		if (pathname == null) {
			return;
		}
		File f = new File(pathname);
		File parent = f.getParentFile();
		if (parent != null) {
			parent.mkdirs();
		}
	}
	public synchronized void MakeFile( boolean append) {
		try {
			String filename = this.baseFileName +"_"+curTimef();
			lastFileName = curTimef();
			lastTime = (new Date()).getTime();
			mkdirForPathname(filename);
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, append), "GBK"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setAutoFlush(boolean autoFlush) {
		this.autoFlush = autoFlush;
	}
	
	public void flush() {
		try {
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void write(String str) {
		if (str == null) {
			return;
		}
		try {
		    bw.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void log(String line) {
		try {
			String strTime = curTimef();
			if(strTime.equals(this.lastFileName)){
				bw.write(curTime());
				bw.write("\t");
				bw.write(line);
				bw.newLine();
				if (autoFlush) {
				bw.flush();
				}
			}else{
				this.close();
				MakeFile(true);
				bw.write(curTime());
				bw.write("\t");
				bw.write(line);
				bw.newLine();
				if (autoFlush) {
					bw.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String curTime() {
		SimpleDateFormat df = new SimpleDateFormat("[yyyy-MM-dd_HH:mm:ss]");
		return df.format(new Date());
	}
	public static String curTimef() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
		return df.format(new Date());
	}
	private BufferedWriter bw;
	private boolean autoFlush = false;
}
