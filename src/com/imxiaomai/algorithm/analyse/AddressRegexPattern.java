package com.imxiaomai.algorithm.analyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.imxiaomai.algorithm.data.Constants;
import com.imxiaomai.algorithm.data.StorePattern;
import com.imxiaomai.algorithm.data.StorePatternInfo;
import com.imxiaomai.algorithm.util.StringListIO;

public class AddressRegexPattern{
	private int minLength = 1000;
	private int maxLength = 0;
	private HashMap<String, StorePattern> storePatternMap = new HashMap<String, StorePattern>();
	private ArrayList<StorePatternInfo> arrayPatterns = new ArrayList<StorePatternInfo>();
	private int algorithmUsed = 1;//算法默认为训练使用
	
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
	public void loadRegexPattern(String patternFile){
		ArrayList<String> lines = new ArrayList<String>();
    	if(this.algorithmUsed == Constants.ALGORITHM_TRAIN)
    		lines = StringListIO.loadCommon(patternFile,"UTF-8");
    	if(this.algorithmUsed == Constants.ALGORITHM_RELEASE)
    		lines = StringListIO.load(patternFile,"UTF-8");
    	for(String line : lines){
    		String[] array = line.split("\t"); 
    		if(array.length<4)
    			continue;
    		StorePatternInfo storePatternInfo = new StorePatternInfo();
    		storePatternInfo.setStrPattern(line);
    		storePatternInfo.setPattern(Pattern.compile(array[3]));
    		arrayPatterns.add(storePatternInfo);
    		Integer num = Integer.parseInt(array[1]);
    		if(storePatternMap.get(array[0])==null){
    			StorePattern storePattern = new StorePattern();
    			storePattern.setName(array[0]+"\t" + array[2]);
    			storePattern.setStoreId(num);
    			storePattern.setPatterns(array[3]);
    			storePatternMap.put(array[0], storePattern);
    			//addressMap.put(array[0]+"\t" + array[1] + "\t" + array[2], num);
    		}else{
    			StorePattern storePattern = storePatternMap.get(array[0]);
    			storePattern.setPatterns(array[3]);
    		}
    		if(array[0].length() < minLength){
    			minLength = array[0].length();
    		}
    		if(array[0].length() > minLength){
    			maxLength = array[0].length();
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
	public String getStoreInfo(String address){
		String storeinfo = null;
		for(StorePatternInfo pattern : arrayPatterns){
			Matcher matcher = pattern.getPattern().matcher(address);
			if (matcher.find() ){//&& matcher.groupCount() >= 1){
				String[] array = pattern.getStrPattern().split("\t");
				if(array.length<4)
					continue;
				return array[1] + "\t" + array[2] + "\t" +array[3];
			}
		}	
		return storeinfo;
		
	}
	public static void main(String[] args){
		AddressRegexPattern adrp = new AddressRegexPattern();
		adrp.setAlgorithmUsed(0);
		adrp.loadRegexPattern("C:/work/test/addressPattern.txt");
		String storeinfo = adrp.getStoreInfo("aa北京邮电大学dd");
		
		if(storeinfo == null)
		if(storeinfo != null){
			System.out.println("match:\t" + storeinfo);
		}
		else
			System.out.println("not match");
		
		System.exit(0);
		
	}
}