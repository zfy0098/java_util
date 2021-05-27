package com.rom.util.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by hadoop on 2018/3/26.
 *
 * @author hadoop
 */
public class QuickSortCase {


    public static void main(String[] args) {

        Integer[] x = new Integer[20];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 20; i++) {
            x[i] = random.nextInt(1, 1000);
        }
        System.out.println(Arrays.toString(x));

        quickSort(x, 0, x.length - 1);

        System.out.println(Arrays.toString(x));

    }

    public static void quickSort(Integer[] number, int start, int end) {

        if (start < end) {
            int base = number[start];
            int temp;
            int i = start;
            int j = end;
            do {
                while (number[i] < base && i < end) {
                    i++;
                }
                while (number[j] > base && j > start) {
                    j--;
                }
                if (i <= j) {
                    temp = number[i];
                    number[i] = number[j];
                    number[j] = temp;
                    i++;
                    j--;
                }
            } while (i < j);
            if (start < j) {
                quickSort(number, start, j);
            }
            if (end > i) {
                quickSort(number, i, end);
            }
        }
    }
}
