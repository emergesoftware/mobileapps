package com.tsepo.android.util;

import java.text.DecimalFormat;

public final class NumericUtil {

	/**
	 * Evaluates a string value to be a number or not a number.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNaN(String value) {
		value = value.trim();
		try {
			Double.parseDouble(value);
		}
		
		catch (NumberFormatException ex){
			return true;
		}
		
		return false;
	}
	
	
	public static String formatDecimal(double value) {
		
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(value);
		
	}
}

