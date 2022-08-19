package org.tedros.services.module.plan.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		Integer i = new Integer(2);
		Double d = new Double(15.60);
		
		String s = String.format("%d %.2f%%", i, d);
		
		System.out.println(s);
		System.out.println(String.format("%s%-1s", "abc","b"));
		
		System.out.println(DateFormat
			.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.forLanguageTag("pt"))
			.format(new Date()));
		
		System.out.println(NumberFormat
		.getCurrencyInstance(Locale.forLanguageTag("en"))
		.format(new BigDecimal("22560.567")));
		
		System.out.println(NumberFormat
				.getInstance(Locale.forLanguageTag("pt-br"))
				.format(new BigDecimal("22560.567")));
				
		

	}

}
