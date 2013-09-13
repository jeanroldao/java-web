package com.jeanroldao.webminer.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SentidoAsync {
	private String value;
	private String text;
	private Future<List<LinhaAsync>> linhasAsync;
	
	
	public SentidoAsync(String v, String t) {
		
		value = v;
		text = t;
		
		linhasAsync = SoulAsync.pool.submit(new Callable<List<LinhaAsync>>() {
			@Override
			public List<LinhaAsync> call() throws Exception {
				List<LinhaAsync> linhas = new ArrayList<LinhaAsync>();
				
				//Document htmlSentido = SoulAsync.getDocumentFromUrl("http://www.soul.com.br/site/itinerarios.php?sentido=" + value).get();
				Document htmlSentido = Jsoup.connect("http://www.soul.com.br/site/itinerarios.php?sentido=" + value).get();
				
				for (Element linha : htmlSentido.select("#linha option")) {
					if (linha.val().length() > 0) {
						List<DiaAsync> dias = new ArrayList<DiaAsync>();
						LinhaAsync linhaAsync = new LinhaAsync(linha.val(), linha.text(), SentidoAsync.this, dias);
						for (Element dia : htmlSentido.select("#dia option")) {
							if (dia.val().length() > 0) {
								dias.add(new DiaAsync(dia.val(), dia.text(), linhaAsync));
							}
						}
						linhas.add(linhaAsync);
					}
				}
				
				return linhas;
			}
			
		});
	}
	
	public String getValue() {
		return value;
	}
	public String getText() {
		return text;
	}
	
	public List<LinhaAsync> getLinhas() {
		try {
			return linhasAsync.get();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<LinhaAsync>();
		}
	}
}
