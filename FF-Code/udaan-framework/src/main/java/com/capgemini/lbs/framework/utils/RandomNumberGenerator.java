package com.capgemini.lbs.framework.utils;

import java.util.Random;
/**
 * @author anwar
 * 
 */
public class RandomNumberGenerator {

	private static Random rgen = new Random();
	private static byte  numValue;
	
	/**
	 * @param length
	 * @return
	 */
	public static synchronized String generateRandomNo(int length)
	{
		StringBuilder sb = new StringBuilder();
		while(sb.length() < length)
		{
			
			numValue = (byte)rgen.nextInt(10);
			
			sb.append( ( numValue ) );
		}
		return sb.toString();
	}
}
