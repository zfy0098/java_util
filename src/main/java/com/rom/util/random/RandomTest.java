package com.rom.util.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {

	public static void main(String[] args) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int i = 0; i < 0; i++) {
			System.out.println(random.nextInt(10000,99999));
		}
		System.out.println("========================");
		Random rd = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println(rd.nextInt());
		}
	}
}
