package com.fdmgroup.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Generates random numbers for games and ensures they do not overlap
 */
public class PinGenerator {
	private static Random random = new Random();
	private static Set<Integer> pins = new HashSet<>();
	
	public static int generatePin() {
		// a random positive 5 digit number
		int max = 99999;
		int min = 10000;
		
		int pin = random.nextInt((max - min) + 1) + min;
		while (pins.contains(pin)) {
			pin = random.nextInt(100000);
		}
		return pin;
	}
	
	public static boolean removePin(int pin) {
		return pins.remove(pin);
	}
}
