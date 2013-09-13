package com.jeanroldao.webminer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Sentido extends Option {
	
	private List<Linha> linhas;

	public Sentido(String value, String text) {
		super(value, text);
	}

	public List<Linha> getLinhas() throws IOException {
		if (linhas != null) {
			return linhas;
		} else {
			linhas = new ArrayList<Linha>();
			
			Document htmlSentido = Jsoup.connect("http://www.soul.com.br/site/itinerarios.php?sentido=" + getValue()).get();
			
			for (Element linha : htmlSentido.select("#linha option")) {
				if (linha.val().length() > 0) {
					List<Dia> dias = new ArrayList<Dia>();
					for (Element dia : htmlSentido.select("#dia option")) {
						if (dia.val().length() > 0) {
							dias.add(new Dia(dia.val(), dia.text()));
						}
					}
					linhas.add(new Linha(linha.val(), linha.text(), this, dias));
				}
			}
			
			return linhas;
		}
	}
	
}
