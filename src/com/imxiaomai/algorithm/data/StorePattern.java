package com.imxiaomai.algorithm.data;

import java.util.ArrayList;

public class StorePattern {
	String name;
	ArrayList<String> patterns = new ArrayList<String>();
	int storeId;
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setStoreId(int id){
		this.storeId = id;
	}
	public int getStoreId(){
		return storeId;
	}
	public void setPatterns(String pattern){
		this.patterns.add(pattern);
	}
	public ArrayList<String> getPatterns(){
		return patterns;
	}
}