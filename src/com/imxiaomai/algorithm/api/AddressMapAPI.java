package com.imxiaomai.algorithm.api;

import java.net.URL;

import com.imxiaomai.algorithm.analyse.AddressRegexPattern;
import com.imxiaomai.algorithm.data.Constants;
import com.imxiaomai.algorithm.test.batchTest;
import com.imxiaomai.algorithm.train.wordTrain;
import com.imxiaomai.algorithm.util.DetailLog;

public class AddressMapAPI{
	private static wordTrain wordtrain = new wordTrain();
	private static AddressRegexPattern adrp = new AddressRegexPattern();
	private AddressMapAPI(){}
	
	static{
		init();
	}
	public static void init(){
		wordtrain.setAlgorithmUsed(Constants.ALGORITHM_RELEASE);
		String strPrePath = "data/";
		URL u = AddressMapAPI.class.getClassLoader().getResource(strPrePath);
		if(u != null){
			String dirPath = strPrePath;
			String trainWords = dirPath + "testAddressStatSortJJ2.txt";
			String commonWords = dirPath + "commonWords.txt";
			String regexPattern = dirPath + "addressPattern.txt";
			wordtrain.LoadMapWords(trainWords);
			wordtrain.loadCommonWords(commonWords);
			adrp.loadRegexPattern(regexPattern);
		}
		else{
			System.out.println("load 地址有误");
			return;
		}
		
	}
	public static String getStoreInfo(String address){
		String storeInfo = null;
		storeInfo = wordtrain.getStoreInfo(address);
		if(storeInfo == null){
			DetailLog.instance().log("words no match:\t" + address);
			storeInfo = adrp.getStoreInfo(address);
			if(storeInfo != null){
				DetailLog.instance().log("regex match:\t" + address);
			}
		}
		if(storeInfo == null){
			DetailLog.instance().log("all no match:\t" + address);
		}
		return storeInfo;
	}
	public static void main(String[] args){
		//AddressMapAPI.init();
		String address = "学院路30号 北科大 ";
		String storeInfo = AddressMapAPI.getStoreInfo(address);
		if(storeInfo == null){
			System.out.println("no match");
		}
		else
			System.out.println("match :" + storeInfo);
		
	}
}