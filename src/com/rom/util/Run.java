package com.rom.util;

import java.util.Scanner;



public class Run {

	public static void main(String[] args) {
			int num = new Scanner(System.in).nextInt();
	        System.out.println(func(num));
	    }
	    /**
	     * 递归调用方法
	     * 思路是从最高位开始判断
	     * 如果大于1，则包含该位的所有1，然后根据倍数关系求出数量
	     * 如果小于等于1，则出现除了该位的剩下数字+1次
	     * @param num
	     * @return
	     */
	    private static int func(int num) {
	        if (num < 10){
	        	return 1;
	        }
	        int max = getMax(num);
	        System.out.println("max:"+max);
	        int rest = num - max * getN0(num);
	        System.out.println("rest:"+rest);
	        int result = 0;
	        if (max > 1) {
	            result = 1 * getN0(num);
	        } else {
	            result = rest + 1;
	        }
	        return result + func(1 * getN0(num) - 1) * (max + 1);
	    }
	 
	    /**
	     * 举例:给234返回100，给1234返回1000，给定99返回10
	     * @param num
	     * @return
	     */
	    private static int getN0(int num) {
	        int len = ("" + num).length();
	        int result = 1;
	        for (int i = 1; i < len; i++) {
	            result *= 10;
	        }
	        return result;
	    }
	    /**
	     * 求一个数字的最高位是几
	     * @param num
	     * @return
	     */
	    private static int getMax(int num) {
	        while (num / 10 != 0) {
	            num /= 10;
	        }
	        return num;
	    }
	
	
	
	
}
