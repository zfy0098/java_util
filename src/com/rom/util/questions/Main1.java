package com.rom.util.questions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main1 {
	public static int[][] c = null;
    public static int n = 0;
    public static int m = 0;
    public static int k = 0;
 
    public static int count = 0;
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nmk = br.readLine().split(" ");
        n = Integer.parseInt(nmk[0]);
        m = Integer.parseInt(nmk[1]);
        k = Integer.parseInt(nmk[2]);
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            String[] temp = br.readLine().split(" ");
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Integer.parseInt(temp[j]);
            }
        }
        br.close();
        c = matrix;
         
        backTrace(0, 0, 0, -1);
         
        System.out.println(count);
    }
 
    public static void backTrace(int i, int j, int giftNum, int maxValue) {
        // 结束时判断条件
        if (j == m-1 && i == n-1) {
            if(giftNum == k){
                count = (count+1)% 1000000007;
            }
            return;
        }
        if(giftNum > k){
            return;
        }
        //如果当前礼物大于手中礼物的最大值
        if(c[i][j]>maxValue && giftNum < k){
            //拿该件礼物
            if(i != (n-1)){
                backTrace(i+1, j, giftNum+1, c[i][j]);
            }
            if(j != (m-1)){
                backTrace(i, j+1, giftNum+1, c[i][j]);
            }
        }
        //不拿该件礼物
        if(i != (n-1)){
            backTrace(i+1, j, giftNum, maxValue);
        }
        if(j != (m-1)){
            backTrace(i, j+1, giftNum, maxValue);
        }
    }
 
}
