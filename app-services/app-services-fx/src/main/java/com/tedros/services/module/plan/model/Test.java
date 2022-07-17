package com.tedros.services.module.plan.model;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		Integer i = new Integer(2);
		Double d = new Double(15.60);
		
		String s = String.format("%d %.2f%%", i, d);
		
		System.out.println(s);
		

	}

}
