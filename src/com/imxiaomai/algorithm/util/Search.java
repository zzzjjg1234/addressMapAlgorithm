/*
 * ChnSeg1.0（中文分词）版权归作者所有，对于任何商业用途产生的后果作者概不负责。
 * 如果您在使用的过程中发现bug，请联系作者。
 * email：wu_j_q@126.com
 * QQ：12537862
 */

package com.imxiaomai.algorithm.util;

import java.util.List;

import com.imxiaomai.algorithm.analyse.Word;

/**
 * 常用查找
 * @author 吴建强
 *
 */

public class Search {
	
	/**
	 * 二分查找，已经排序的list，查找c，list升序排列
	 * @param list
	 * @param c
	 * @return，如果找到返回-1，否则返回c可以插入list的位置
	 */
	public static int binarySearch(List list, Word c){
		int i, j, mid;
		Word temp = null;
		int pos = 0;
		
		if (list.size() == 0){
			return 0;
		}		    
		i = 0;
		j = list.size() - 1;
		mid = 0;
		while (i <= j){
			mid = (i + j) / 2;
			temp = (Word)list.get(mid);
			/*小于*/
			if (temp.getValue() < c.getValue()) {
				i = mid + 1;
				pos = mid + 1;
				continue;
			}
			/*大于*/
			if (temp.getValue() > c.getValue()) {
				j = mid - 1;
				pos = mid;
				continue;
			}
			/*等于*/
			if (temp.getValue() == c.getValue()) {
				break;
			}
		}
		if (temp.getValue() == c.getValue()) {
			for (i = mid; i >= 0; i--){
				temp = (com.imxiaomai.algorithm.analyse.Word)list.get(i);
				if (temp.equals(c)) return -1;
				if (temp.getValue() != c.getValue()) break;
			}
			for (i = mid + 1; i < list.size(); i++){
				temp = (com.imxiaomai.algorithm.analyse.Word)list.get(i);
				if (temp.equals(c)) return -1;
				if (temp.getValue() != c.getValue()) break;
			}
			pos = i;
		}
		return pos;
	}
	/**
	 * 二分查找，已经排序的list，查找dest，list升序排列
	 * @param list
	 * @param dest
	 * @return，如果相等返回true，否则返回false
	 */
	public static boolean binarySearch(int[] arr, int dest){
		int i, j, mid;
		int temp;
		if (arr == null) return false;
		i = 0;
		j = arr.length - 1;
		while (i <= j){
			mid = (i + j) / 2;
			temp = arr[mid];
			/*小于*/
			if (temp < dest) {
				i = mid + 1;
				continue;
			}
			/*大于*/
			if (temp > dest) {
				j = mid - 1;
				continue;
			}
			/*等于*/
			if (temp == dest) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] arg){
		int[] arr = new int[5];
		int pos = 0;
		arr[pos++] = 10;
		arr[pos++] = 20;
		arr[pos++] = 30;
		arr[pos++] = 50;
		arr[pos++] = 98;
		System.out.println(Search.binarySearch(arr, 95));

	}
}
