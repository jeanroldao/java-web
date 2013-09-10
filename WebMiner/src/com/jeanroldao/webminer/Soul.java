package com.jeanroldao.webminer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Soul {

	public static List<Horario> loadFromSoul() throws IOException {
		List<Horario> horarios = new ArrayList<>();
		
		for (Sentido sentido : loadSentidos()) {
			for (Linha linha : sentido.getLinhas()) {
				for (Dia dia : linha.getDias()) {
					for (Horario horario : dia.getHorarios()) {
						horarios.add(horario);
					}
				}
			}
		}
		
		return horarios;
	}
	
	private static List<Sentido> loadSentidos() throws IOException {
		List<Sentido> sentidos = new ArrayList<>();
		Document html = Jsoup.connect("http://www.soul.com.br/site/itinerarios.php?").get();
		
		List<String> tipos = Arrays.asList("ALV-POA", "POA-ALV");
		for (Element option : html.select("#sentido option")) {
			//System.out.println("\""+option.text()+"\"");
			if (tipos.indexOf(option.text()) >= 0) {
				//System.out.println("\""+option.text()+"\" - \""+option.val()+"\"");
				sentidos.add(new Sentido(option.val(), option.text()));
			}
		}
		
		return sentidos;
	}

	public static String getJson() throws IOException {
		StringBuilder sb = new StringBuilder();
		
		String comma = "";
		sb.append("[");
		
		for (Horario h : loadFromSoul()) {
			sb.append(comma);
			comma = ", ";
			sb.append(h.toJson());
		}
		
		sb.append("]");
		
		return sb.toString();
	}

}
