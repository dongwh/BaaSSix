package com.digiwes.common.util;

public class NumberUtil {
	
	/**
	 * compare the size of the two numbers
	 * -1 is numOne < numTwo
	 * 0 is numOne = numTwo
	 * 1 is numOne > numTwo
	 */
	public static int compareTheNumber(int numOne, int numTwo){
		if(numOne < numTwo){
			return -1;
		}else if(numOne == numTwo){
			return 0;
		}else{
			return 1;
		}
	}
}
