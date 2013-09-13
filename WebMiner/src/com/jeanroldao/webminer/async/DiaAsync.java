package com.jeanroldao.webminer.async;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DiaAsync {
	
	private String value;
	private String text;
	private LinhaAsync linha;
	private Future<List<HorarioAsync>> horariosAsync;
	
	public DiaAsync(String v, String t, LinhaAsync l) {
		value = v;
		text = t;
		linha = l;
		
		horariosAsync = SoulAsync.pool.submit(new Callable<List<HorarioAsync>>() {
			@Override
			public List<HorarioAsync> call() throws Exception {
				List<HorarioAsync> horarios = new ArrayList<HorarioAsync>();
				
				String sentido = linha.getSentido().getValue();
				String dia = getValue();
				
				String linhaEncoded = URLEncoder.encode(linha.getValue(), Charset.defaultCharset().displayName());
				Document htmlTabela = Jsoup.connect("http://www.soul.com.br/site/itinerarios.php?sentido=" + sentido + "&linha=" + linhaEncoded + "&dia=" + dia).timeout(SoulAsync.TIME_OUT).get();
				
				for (Element linhaTr : htmlTabela.select("tr[bgcolor]")) {
					String hora = linhaTr.select("td:eq(0)").text().trim();
					String desc = linhaTr.select("td:eq(1)").text().replaceAll("&nbsp;", " ").trim();
					
					horarios.add(new HorarioAsync(linha.getSentido().getText(), linha.getText(), getText(), hora, desc));
				}
				
				return horarios;
			}
		});
	}
	
	public void setLinha(LinhaAsync l) {
		linha = l;
	}
	
	public LinhaAsync getLinha() {
		return linha;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}
	
	public List<HorarioAsync> getHorarios() {
		try {
			return horariosAsync.get();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<HorarioAsync>();
		}
	}
}
