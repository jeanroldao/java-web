package com.jeanroldao.webminer;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Dia extends Option {
	
	private List<Horario> horarios;
	private Linha linha;
	
	public Dia(String value, String text) {
		super(value, text);
	}
	
	public Dia(String value, String text, Linha linha) {
		this(value, text);
		this.linha = linha;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public List<Horario> getHorarios() throws IOException {
		if (horarios != null) {
			return horarios;
		} else {
			horarios = new ArrayList<Horario>();
			
			String sentido = this.linha.getSentido().getValue();
			String linha = this.linha.getValue();
			String dia = this.getValue();
			
			String linhaEncoded = URLEncoder.encode(linha, Charset.defaultCharset().displayName());
			Document htmlTabela = Jsoup.connect("http://www.soul.com.br/site/itinerarios.php?sentido=" + sentido + "&linha=" + linhaEncoded + "&dia=" + dia).get();
			
			for (Element linhaTr : htmlTabela.select("tr[bgcolor]")) {
				String hora = linhaTr.select("td:eq(0)").text().trim();
				String desc = linhaTr.select("td:eq(1)").text().replaceAll("&nbsp;", " ").trim();
				
				horarios.add(new Horario(this.linha.getSentido().getText(), this.linha.getText(), getText(), hora, desc));
			}
			
			return horarios;
		}
	}
}
