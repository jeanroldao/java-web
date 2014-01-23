package com.jeanroldao.webminer.async;


public class WebMinerAsync {
	
	public static final boolean DEBUG = true;

	public static void main(String[] args) {
		long ini_ms = System.currentTimeMillis();
		//System.out.println("start...");
		
		String json = SoulAsync.getJson();
		
		if (DEBUG) {
			System.out.println("total: " + SoulAsync.loadFromSoul().size());
			
			long total_ms = System.currentTimeMillis() - ini_ms;
			System.out.println("...end ("+total_ms+")");
		} else {
			System.out.println(json);
		}
	}

}
