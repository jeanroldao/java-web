package com.jeanroldao.webminer.async;

import java.util.List;

public class LinhaAsync {
	
	private String value;
	private String text;
	private SentidoAsync sentido;
	private List<DiaAsync> dias;
	
	public LinhaAsync(String v, String t, SentidoAsync s, List<DiaAsync> d) {
		value = v;
		text = t;
		sentido = s;
		dias = d;
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public SentidoAsync getSentido() {
		return sentido;
	}

	public List<DiaAsync> getDias() {
		return dias;
	}
}
