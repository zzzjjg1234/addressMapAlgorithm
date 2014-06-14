package com.imxiaomai.algorithm.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import com.imxiaomai.algorithm.analyse.AddressRegexPattern;
import com.imxiaomai.algorithm.api.AddressMapAPI;
import com.imxiaomai.algorithm.data.Constants;
import com.imxiaomai.algorithm.train.wordTrain;
import com.imxiaomai.algorithm.util.DetailLog;

public class testMGJ{
	private int zhaohuiNumNo = 0;
	private int zhunqueNumNo = 0;
	private int totalNum = 0;
	
	public void fileDataTest(String filePath){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/work/test/xiaomaMGJTest.txt"), "UTF-8"));
			String line;	
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				line = line.replaceAll(" ", "");
				String storeInfo = AddressMapAPI.getStoreInfo(line);
				if(storeInfo == null){
					System.out.println("no match");
					bw.write(line + "\tnomath\n");
					zhaohuiNumNo ++;
				}
				else{
					String[] array = storeInfo.split("\t");
					if(array.length<3)
						return;
					bw.write(line + "\tmatch\t" + array[1] + "\n");
					System.out.println("match :" + storeInfo);
				}
			}
			bw.close();
			br.close();
		}catch(Exception e){
			
		}
	}
	public void fileDataTest1(String filePath){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/work/test/xiaomaMGJTest.txt"), "UTF-8"));
			String line;	
			ArrayList<String> resultList = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				String[] array = line.split("\t");
				if(array.length <2)
					return;
				String tempLine = line;
				line = line.replaceAll(" ", "");
				String storeInfo = AddressMapAPI.getStoreInfo(array[1]);
				String entity = "";
				if(storeInfo == null){
					System.out.println("no match");
					entity = array[1] + "\t" + array[0] + "\t" + "否" + "\t\n" ;
					zhaohuiNumNo ++;
				}
				else{
					String[] sarray = storeInfo.split("\t");
					if(sarray.length<3)
						continue;
					entity = array[1] + "\t" + array[0] + "\t" + "是\t" + sarray[1] + "\n";
					System.out.println("match :" + storeInfo);
				}
				resultList.add(entity);
			}
			Collections.shuffle(resultList);
			for(int i = 0; i < 200; i++){
				bw.write(resultList.get(i));
				
			}
			bw.close();
			br.close();
		}catch(Exception e){
			
		}
	}
	private void stat(){
		float zhaohuilv =(float) ((float) (totalNum-zhaohuiNumNo)*1.0/totalNum);
		float zhunquelv =(float) ((float) (totalNum-zhaohuiNumNo-zhunqueNumNo)*1.0/(totalNum-zhaohuiNumNo));
		System.out.println("zhaohuino:" + zhaohuiNumNo + "\t" + zhunqueNumNo + "\t" + totalNum + "\t" + zhaohuilv + "\t" + zhunquelv);
	}
	public static void main(String[] args){
		testMGJ mgj = new testMGJ();
		mgj.fileDataTest1("C:/work/test/mgjtest.txt");
		mgj.stat();
		System.exit(0);
	}
}