package com.imxiaomai.algorithm.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.imxiaomai.algorithm.analyse.AddressRegexPattern;
import com.imxiaomai.algorithm.data.Constants;
import com.imxiaomai.algorithm.train.wordTrain;
import com.imxiaomai.algorithm.util.*;

public class testDataCleaning{
	
	public static void main(String args[]){
		if(args.length<2){
			System.out.println("params need 2");
			return;
		}
		wordTrain testdc = new wordTrain();
		testdc.setAlgorithmUsed(Constants.ALGORITHM_TRAIN);
		testdc.readStoreMap(args[1]);
		
		testdc.writeFile(args[0]);
		//test ceshidata
		//testdc.writeFile("C:/work/wendang/文档/data/testsql.txt");
		//testdc.writeAddressStat();
		//testdc.writeAddressStatBySort();
		//train
		//testdc.LoadAddresses("C:/work/test/testAddressStatSort.txt");
		//testdc.readDeleteMap("C:/work/test/deleteWords.txt");
		//testdc.writeAddressStatBySort();
		//test接口
		
		testdc.LoadMapWords("C:/work/test/testAddressStatSortJJ2.txt");
		testdc.loadCommonWords("C:/work/test/commonWords.txt");
		String strTest = "清华大学"; 
		String storeInfo = testdc.getStoreInfo(strTest);
		AddressRegexPattern adrp = new AddressRegexPattern();
		adrp.loadRegexPattern("C:/work/test/addressPattern.txt");
		if(storeInfo == null){
			storeInfo = adrp.getStoreInfo(strTest);
		}
		
		if(storeInfo == null){
			System.out.println("no match");
		}
		else
			System.out.println("match :" + storeInfo);
	}
}