package com.fdmgroup.util;

import java.util.Random;

/**
 * A class that generates random IDs.
 */
public class IdGenerator {

	private static Random random = new Random();
	
	/**
	 * Returns a random id
	 * @return A random id
	 */
	public static int generate() {
		return Math.abs(random.nextInt());
	}

}
