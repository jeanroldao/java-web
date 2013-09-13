package com.jeanroldao.webminer.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SoulAsync {
	
	public static final ExecutorService pool = Executors.newFixedThreadPool(16);
	
	public static Future<Document> getDocumentFromUrl(final String url) {
		return pool.submit(new Callable<Document>() {
			@Override
			public Document call() throws Exception {
				return Jsoup.connect(url).timeout(10000).get();
			}
		});
	}

	public static String getJson() {
		StringBuilder sb = new StringBuilder();
		
		String comma = "";
		sb.append("[");
		
		for (HorarioAsync h : loadFromSoul()) {
			sb.append(comma);
			comma = ", ";
			sb.append(h.toJson());
		}
		
		sb.append("]");
		
		return sb.toString();
	}

	public static List<HorarioAsync> loadFromSoul() {
		List<HorarioAsync> horarios = new ArrayList<HorarioAsync>();
		
		for (SentidoAsync sentido : loadSentidos()) {
			for (LinhaAsync linha : sentido.getLinhas()) {
				for (DiaAsync dia : linha.getDias()) {
					for (HorarioAsync horario : dia.getHorarios()) {
						horarios.add(horario);
					}
				}
			}
		}
		
		return horarios;
	}
	
	public static List<SentidoAsync> loadSentidos() {
		try {
			List<SentidoAsync> sentidos = new ArrayList<SentidoAsync>();
			
			Document html = Jsoup.connect("http://www.soul.com.br/site/itinerarios.php?").get();
			List<String> tipos = Arrays.asList("ALV-POA", "POA-ALV");
			for (Element option : html.select("#sentido option")) {
				//System.out.println("\""+option.text()+"\"");
				if (tipos.indexOf(option.text()) >= 0) {
					//System.out.println("\""+option.text()+"\" - \""+option.val()+"\"");
					sentidos.add(new SentidoAsync(option.val(), option.text()));
				}
			}
			
			return sentidos;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<SentidoAsync>();
		}
		
	}

}
