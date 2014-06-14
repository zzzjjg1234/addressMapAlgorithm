package com.imxiaomai.algorithm.train;
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
import com.imxiaomai.algorithm.util.*;

public class wordTrain{
	private HashMap<String, String> storeMap = new HashMap<String, String>();
	private HashMap<String, Integer> addressMap = new HashMap<String, Integer>();
	private HashMap<String, String> storeWordsMap = new HashMap<String, String>();
	private HashMap<String, String> deleteWordsMap = new HashMap<String, String>();
	private int minLength = 1000;
	private int maxLength = 0;
	private int validStatusNum = 0;
	private int algorithmUsed = 0;//算法默认为训练使用
	
	/**
	 * @return the algorithmUsed
	 */
	public int getAlgorithmUsed() {
		return algorithmUsed;
	}
	/**
	 * @param algorithmUsed the algorithmUsed to set
	 */
	public void setAlgorithmUsed(int algorithmUsed) {
		this.algorithmUsed = algorithmUsed;
	}
	public void readDeleteMap(String deletePath){
		ArrayList<String> lines = StringListIO.loadCommon(deletePath,"UTF-8");
		for(String line : lines){
			String[] array = line.split("\t");
			if(array.length > 3){
				array[2] = array[2].replaceAll(" ", "");
				deleteWordsMap.put(array[2], line);
			}
		}
		
	}
	public void readStoreMap(String storeFoleName){
		ArrayList<String> lines = StringListIO.loadCommon(storeFoleName,"UTF-8");
		for(String line : lines){
			String[] array = line.split("\t");
			if(array.length > 7){
				storeMap.put(array[0], array[1]);
			}
			
		}
	}
	public void writeFile(String storeDeliveryFileName){
		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(storeDeliveryFileName), "UTF-8"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/work/test/testDataAfter1.txt"), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				if(validStatusNum == 14997){
					System.out.println("test");
				}
				String[] array = line.split("\t"); 
				if(array.length >20){
					String storeName = storeMap.get(array[2]);
					String address = array[9];
					//Integer status = Integer.parseInt(array[12]);
					/*if(status == 120){
						
						continue;
					}*/
					validStatusNum ++;
					if(array[2] != null && array[9] != null && array[9].length()>6){
						String lineAddress = array[2] + "\t" +storeName + "\t" + address;
						bw.write(lineAddress + "\n");
						if(addressMap.containsKey(lineAddress)){
							Integer num = addressMap.get(lineAddress);
							num ++;
							addressMap.put(lineAddress, num);
						}else{
							Integer num = 1;
							addressMap.put(lineAddress, num);
						}
					}
				}
			}
			bw.close();
			br.close();
		}catch(Exception e){
			System.out.println("error");
		}
	}
	public void writeAddressStat(){
		try {
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/work/test/testAddressStat.txt"), "UTF-8"));
			Iterator<String> keyIter = addressMap.keySet().iterator();
			while(keyIter.hasNext()) {
				String key = keyIter.next();
				Integer value = addressMap.get(key);
				//System.out.println(keyIter.toString());
				bw.write(key + "\t" + value + "\n");
			}
			bw.close();
			
		}catch(Exception e){
			System.out.println("error");
		}
	}
	private boolean isAccurateJudge(ArrayList<String> storeInfos){
		boolean isAccurate = true;
		int storeId = 0;
		int i = 0;
		int[] istore = new int[500];
		for(int m = 0; m < 500; m++){
			istore[m] = 0;
		}
		for(String storeinfo : storeInfos){
			String[] array = storeinfo.split("\t");
			if(array.length<3)
				continue;
			Integer si = Integer.parseInt(array[0]);
			istore[si]++;
		}
		for(int j = 0; j < storeInfos.size();j++){
				String storeinfo = storeInfos.get(j);
				String[] array = storeinfo.split("\t");
				if(array.length<4)
					continue;
				Integer is = Integer.parseInt(array[0]);
				Integer num = Integer.parseInt(array[3]);
				if(istore[is] == 1 && storeInfos.size()>1){
					num --;
					this.storeWordsMap.put(array[2],array[0]+"\t" + array[1]+"\t"+array[2] + "\t"+num);
				}else{
					num ++;
					this.storeWordsMap.put(array[2],array[0]+"\t" + array[1]+"\t"+array[2] + "\t"+num);
				}
		}
		return isAccurate;
	}
	public void dataCleanSimilar(List<Entry<String, Integer>> arrayList){
		//HashMap<String, String> addressesMap = new HashMap<String, String>();
		for(Entry<String, Integer> entry : arrayList){
			String[] array = entry.getKey().split("\t");
			if(array.length<3)
				continue;
			if(array[2].length()<minLength){
				minLength = array[2].length();
			}
			if(array[2].length()>maxLength){
				maxLength = array[2].length();
			}
			//去掉空格
			array[2] = array[2].replaceAll(" ", "");
			if(deleteWordsMap.get(array[2]) != null)
				continue;
			ArrayList<String> storeInfos =  getStoreInfos(array[2]);
			if(storeInfos != null && storeInfos.size() >0){
				if(isAccurateJudge(storeInfos)){
					
				}else{
					for(String storeInfo : storeInfos){ 
						DetailLog.instance().log("error_match_address:\t"+array[2] + "\t" + storeInfo);
					}
					storeWordsMap.put(array[2], entry.getKey()+"\t" + entry.getValue());
				}
			}else{
				storeWordsMap.put(array[2], entry.getKey()+"\t" + entry.getValue());
			}
		}
		
	}
	public void deleteRepeatString(List<Entry<String, Integer>> arrayList){
		List<Entry<String, Integer>> arrayListRemove = new ArrayList<Entry<String, Integer>>();
		int size = arrayList.size();
		for(int i = 0; i < size; i++){
			Entry<String, Integer> entry = arrayList.get(i);
			if(entry.getValue() == -1)
				continue;
			int num = entry.getValue();
			for(int j = i+1; j < size; j++){
				Entry<String, Integer> sentry = arrayList.get(j);
				if(sentry.getValue()!=-1 && sentry.getKey().contains(entry.getKey()) && !sentry.getKey().equals(entry.getKey())){
					arrayListRemove.add(entry);
					num += sentry.getValue();
					int indexI = arrayList.indexOf(sentry);
					sentry.setValue(-1);
					arrayList.set(indexI, sentry);
				}
			}
			entry.setValue(num);
			arrayList.set(i, entry);
			//arrayList.removeAll(arrayListRemove);
			//size = arrayList.size();
		}
	}
	private boolean isGarbage(String str){
		boolean isGarbage = false;
		int numNumber = 0;
		int engNumber = 0;
		for (int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			if(CharTest.isNumber(c)){
				numNumber++;
			}
			if(CharTest.isChar(c)){
				engNumber ++;
			}
		}
		if(numNumber == str.length() || engNumber == str.length())
			return true;
		
		return isGarbage;
		
	}
	public void writeAddressStatBySort(){
		try {
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/work/test/testAddressStatSortJJ2.txt"), "UTF-8"));
			List<Entry<String, Integer>> arrayList = sortKeywordMap(this.addressMap);
			//相似计算方法一
			dataCleanSimilar(arrayList);
			List<Entry<String, String>> arrayList1 = new ArrayList<Entry<String, String>>(  
					storeWordsMap.entrySet()); 
			
			HashMap<String, Integer> outMap = new HashMap<String, Integer>();
			for(Entry<String, String> entry : arrayList1){
				String[] array = entry.getValue().split("\t");
				if(array.length<4)
					continue;
				Integer inum = Integer.parseInt(array[3]);
				outMap.put(entry.getValue(), inum);
			}
			List<Entry<String, Integer>> arrayList2 = sortKeywordMap(outMap);
			
			for(Entry<String, Integer> entry : arrayList2){
				String[] array = entry.getKey().split("\t");
				if(array.length<4)
					continue;
				Integer inum = Integer.parseInt(array[3]);
				
				if(inum <= 0 || isGarbage(array[2]))
					DetailLog.instance().log("delete address:\t"+entry.getValue());
				else
					bw.write( entry.getKey() + "\n");
			}
			//相似计算方法二
			/*deleteRepeatString(arrayList);
			
			for(Entry<String, Integer> entry : arrayList){
				if(entry.getValue() != -1)
				bw.write(entry.getKey() + "\t" + entry.getValue() + "\n");
			}*/
			bw.close();
			
		}catch(Exception e){
			System.out.println("error");
		}
	}
	 /** 
     * 进行按value值排序，从大到小 
     *  
     * @param keywordMap 
     *            搜索关键词的hashMap 
     * @return 
     */  
    public List<Entry<String, Integer>> sortKeywordMap(  
            HashMap<String, Integer> keywordMap) {  
        List<Entry<String, Integer>> arrayList = new ArrayList<Entry<String, Integer>>(  
                keywordMap.entrySet());  
        Collections.sort(arrayList, new Comparator<Entry<String, Integer>>() {  
            public int compare(Entry<String, Integer> e1,  
                    Entry<String, Integer> e2) {  
                return (e2.getValue()).compareTo(e1.getValue());  
            }  
        });  
       /* for (Entry<String, Integer> entry : arrayList) {  
            System.out.println(entry.getKey() + "  " + entry.getValue());  
        }  */
        return arrayList;  
    }  
    /** 
     * 对搜索关键词的hashMap 进行按value值排序，从大到小 
     *  
     * @return 
     */  
    //public List<Entry<String, Integer>> sortKeywordMap(HashMap<String, Integer> keywordMap) {  
   //     return sortKeywordMap(addressMap);  
    //}  
    public void LoadAddresses(String mapWordsPath){
    	ArrayList<String> lines = StringListIO.loadCommon(mapWordsPath,"UTF-8");
    	for(String line : lines){
    		String[] array = line.split("\t"); 
    		if(array.length<4)
    			continue;
    		Integer num = Integer.parseInt(array[3]);
    		if(Integer.parseInt(array[3]) > 0){
    			addressMap.put(array[0]+"\t" + array[1] + "\t" + array[2], num);
    		}
    		
    	}
    }
    public void loadCommonWords(String mapWordsPath){
    	LoadMapWords(mapWordsPath);
    }
    public void LoadMapWords(String mapWordsPath){
    	ArrayList<String> lines = new ArrayList<String>();
    	if(this.algorithmUsed == Constants.ALGORITHM_TRAIN)
    		lines = StringListIO.loadCommon(mapWordsPath,"UTF-8");
    	if(this.algorithmUsed == Constants.ALGORITHM_RELEASE)
    		lines = StringListIO.load(mapWordsPath,"UTF-8");
    	for(String line : lines){
    		String[] array = line.split("\t"); 
    		if(array.length<4)
    			continue;
    		if(Integer.parseInt(array[3]) > 1){
    			storeWordsMap.put(array[2], line);
    		}
    		if(array[2].length() < minLength){
    			minLength = array[2].length();
    		}
    		if(array[2].length() > maxLength){
    			maxLength = array[2].length();
    		}
    	}
    }
    private ArrayList<String> getSubString(String block){
    	ArrayList<String> subStrings = new ArrayList<String>();
    	for(int i = 0; i <= block.length()-minLength; i++ ) {
    		for(int j = minLength; j <= block.length(); j++){
    			if(j-i >= minLength && j-i <= maxLength){
    				String subString = block.substring(i, j);
    				subStrings.add(subString);
    			}
    		}
    	}
    	return subStrings;
    }
    private ArrayList<String> getAllSubString(ArrayList<String> blocks){
    	ArrayList<String> allSubStrings = new ArrayList<String>();
    	for(String block : blocks){
    		if(block.length()<minLength)
    			continue;
    		ArrayList<String> subStrings = getSubString(block);
    		if(subStrings.size()>0){
    			allSubStrings.addAll(subStrings);
    		}
    	}
    	return allSubStrings;
    }
    private ArrayList<String> getStoreInfos(ArrayList<String> allSubStrings, HashMap<String, String> wordsMap){
    	 ArrayList<String> storeInfos = new ArrayList<String>();
    	 for(String subString : allSubStrings){
    		 String storeInfo = wordsMap.get(subString);
    		 if(storeInfo != null){
    			 storeInfos.add(storeInfo);
    		 }	 
    	 }
    	 return storeInfos;
    }
    public ArrayList<String> getStoreInfos(String address){
    	ArrayList<String> blocks = new ArrayList<String>();
    	int type = -1;
		int tmpType = 0;
    	String block = "";
    	for (int i = 0; i < address.length(); i++){
			char c = address.charAt(i);
			tmpType = CharTest.testChar(c);
			if(tmpType == -1){
				blocks.add(block);
				block = "";
			}else{
				block += c;
			}
    	}
    	blocks.add(block);
    	ArrayList<String> allSubStrings = getAllSubString(blocks);
    	if(allSubStrings.size()>0){
    		return  getStoreInfos(allSubStrings,this.storeWordsMap);	
    	}
    	return null;
    }
    public String getStoreInfo(String address){
    	String storeInfo = null;
    	if(address.length()<minLength )
    		return null;
    	ArrayList<String> blocks = new ArrayList<String>();
    	int type = -1;
		int tmpType = 0;
    	String block = "";
    	for (int i = 0; i < address.length(); i++){
			char c = address.charAt(i);
			tmpType = CharTest.testChar(c);
			if(tmpType == -1){
				blocks.add(block);
				block = "";
			}else{
				block += c;
			}
    	}
    	blocks.add(block);
    	ArrayList<String> allSubStrings = getAllSubString(blocks);
    	if(allSubStrings.size()>0){
    		ArrayList<String> storeInfos = getStoreInfos(allSubStrings,this.storeWordsMap);
    		for(String store : storeInfos){
    			System.out.println(store);
    			
    		}
    		storeInfo = getSingleStoreInfo(storeInfos);
    	}
    	
    	return storeInfo;
    }
    public String getSingleStoreInfo(ArrayList<String> storeInfos){
    	String singleStoreInfo = null;
    	int storeId = 0;
		int i = 0;
		int[] istore = new int[500];
		int maxStoreId = 0;
		int maxNum = 0 ;
		for(int m = 0; m < 500; m++){
			istore[m] = 0;
		}
		for(String storeinfo : storeInfos){
			String[] array = storeinfo.split("\t");
			if(array.length<3)
				continue;
			Integer si = Integer.parseInt(array[0]);
			istore[si]++;
			if(istore[si]>maxNum){
				maxNum = istore[si]++;
				maxStoreId = si;
			}
		}
		int maxLength = 0;
		for(int j = 0; j < storeInfos.size();j++){
				String storeinfo = storeInfos.get(j);
				String[] array = storeinfo.split("\t");
				if(array.length < 3)
					continue;
				Integer sn = Integer.parseInt(array[0]);
				if( maxNum > 1 && sn == maxStoreId){
					if(array[2].length() > maxLength){
						maxLength = array[2].length();
						singleStoreInfo = storeinfo;
					}
				}
				if(maxNum == 1){
					if(array[2].length() > maxLength){
						maxLength = array[2].length();
						singleStoreInfo = storeinfo;
					}
				}
				
		}    	
		return singleStoreInfo;
    }
	public static void main(String args[]){
		if(args.length<2){
			System.out.println("params need 2");
			return;
		}
		wordTrain testdc = new wordTrain();
		testdc.readStoreMap(args[1]);
		//testdc.writeFile(args[0]);
		//test ceshidata
		//testdc.writeFile("C:/work/wendang/文档/data/testsql.txt");
		//testdc.writeAddressStat();
		//testdc.writeAddressStatBySort();
		//train
		//testdc.LoadAddresses("C:/work/test/testAddressStatSort.txt");
		//testdc.writeAddressStatBySort();
		//test接口
		
		/*testdc.LoadMapWords("C:/work/test/testAddressStatSortJJ2.txt");
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
			System.out.println("match :" + storeInfo);*/
	}
}