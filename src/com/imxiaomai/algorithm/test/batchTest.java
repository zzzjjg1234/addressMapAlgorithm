package com.imxiaomai.algorithm.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.imxiaomai.algorithm.analyse.AddressRegexPattern;
import com.imxiaomai.algorithm.data.Constants;
import com.imxiaomai.algorithm.train.wordTrain;
import com.imxiaomai.algorithm.util.DetailLog;

public class batchTest{
	private int zhaohuiNumNo = 0;
	private int zhunqueNumNo = 0;
	private int totalNum = 0;
	private wordTrain tdc = new wordTrain(); 
	public void iniTdc(String filePath){
		tdc.LoadMapWords(filePath);
	}
	public void fileDataTest(String filePath){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String line;	
			AddressRegexPattern adrp = new AddressRegexPattern();
			adrp.setAlgorithmUsed(Constants.ALGORITHM_TRAIN);
			adrp.loadRegexPattern("C:/work/test/addressPattern.txt");
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				String[] array = line.split("\t");
				if(array.length < 3)
					continue;
				array[2] = array[2].replaceAll(" ", "");
				String storeInfo = tdc.getStoreInfo(array[2]);
				totalNum ++;
				if(storeInfo == null){
					storeInfo = adrp.getStoreInfo(array[2]);
					if(storeInfo != null){
						DetailLog.instance().log("regex pattern:\t" + line);
					}else{
						zhaohuiNumNo ++;
						DetailLog.instance().log("nozhaohui:\t" + line);
						//totalNum ++;
						continue;
					}
				}
				String[] sarray = storeInfo.split("\t");
				if(sarray.length <3)
					continue;
				if(!sarray[0].equals(array[0])){
					zhunqueNumNo ++;
					DetailLog.instance().log("nozhunque:\t" + line + "\t" + storeInfo);
					//totalNum ++;
				}
					
			}
		}catch(Exception e){
			
		}
	}
	private void stat(){
		float zhaohuilv =(float) ((float) (totalNum-zhaohuiNumNo)*1.0/totalNum);
		float zhunquelv =(float) ((float) (totalNum-zhaohuiNumNo-zhunqueNumNo)*1.0/(totalNum-zhaohuiNumNo));
		System.out.println("zhaohuino:" + zhaohuiNumNo + "\t" + zhunqueNumNo + "\t" + totalNum + "\t" + zhaohuilv + "\t" + zhunquelv);
	}
	public static void main(String[] args){
		
		String testk = "qinghua daxue";
		testk = testk.replaceAll(" ", "");
		batchTest bt = new batchTest();
		String strPath = "C:/work/test/testAddressStatSortJJ2.txt";
		bt.iniTdc(strPath);
		bt.iniTdc("C:/work/test/commonWords.txt");
		bt.fileDataTest("C:/work/test/testDataAfter1.txt");
		bt.stat();
		System.exit(0);
	}
	
}