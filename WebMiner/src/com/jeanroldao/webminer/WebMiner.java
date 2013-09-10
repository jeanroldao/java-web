package com.jeanroldao.webminer;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebMiner {
	
	public static void main(String[] args) throws IOException {
		String json = Soul.getJson();
		System.out.println(json);
	}
	
	public static void main1(String[] args) throws IOException {
		System.out.println("start...");
		
		String url = "http://www.soul.com.br/site/itinerarios.php?";
		
		Document doc = Jsoup.connect(url).get();
		
		for (Element el : doc.select("#sentido option")) {
			System.out.println(el.text());
		}
		
		System.out.println("...end");
	}
}
