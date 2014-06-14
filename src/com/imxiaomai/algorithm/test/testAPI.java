package com.imxiaomai.algorithm.test;

import com.imxiaomai.algorithm.api.AddressMapAPI;

public class testAPI{
	public static void main(String[] args){
		String address = "清华大学";
		String storeInfo = AddressMapAPI.getStoreInfo(address);
		if(storeInfo == null){
			System.out.println("no match");
		}
		else
			System.out.println("match :" + storeInfo);
	}
}