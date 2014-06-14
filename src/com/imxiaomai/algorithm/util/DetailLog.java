/// @author lizhide
/// @date 2012.04
/// detail log, many many lines, thread safe

package com.imxiaomai.algorithm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

class DetailLogDebug {
	public static synchronized FileLog instance() {
		if (singleton == null) {
			singleton = new FileLog("./detail.log");
		}
		return singleton;
	}
	
	private DetailLogDebug() {
	}
	
	public void close() {
		singleton.close();
	}
	
	public synchronized void log(String line) {
		singleton.log(line);
	}
	
	private static FileLog singleton;
}

public class DetailLog {
	public static synchronized DetailLog instance() {
		if (singleton == null) {
			singleton = new DetailLog();
		}
		return singleton;
	}
	
	private DetailLog() {
	}
	
	public void close() {
		if (debug) {
			DetailLogDebug.instance().close();
		}
	}
	
	public synchronized void log(String line) {
		System.out.print(curTime());
		System.out.print("\t");
		System.out.println(line);
		
		if (debug) {
			DetailLogDebug.instance().log(line);
		}
	}
	
	public static String curTime() {
		SimpleDateFormat df = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss:SSS]");
		return df.format(new Date());
	}
	
	private static DetailLog singleton;
	private boolean debug = true;
}
