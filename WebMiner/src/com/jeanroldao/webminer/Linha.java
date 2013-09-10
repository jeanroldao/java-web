package com.jeanroldao.webminer;

import java.util.ArrayList;
import java.util.List;

public class Linha extends Option {
	
	private List<Dia> dias;
	private Sentido sentido;
	
	public Linha(String value, String text, Sentido sentido, List<Dia> dias) {
		super(value, text);
		this.sentido = sentido;
		setDias(dias);
	}

	public List<Dia> getDias() {
		if (dias == null) {
			dias = new ArrayList<>();
		}
		return dias;
	}

	public void setDias(List<Dia> dias) {
		this.dias = dias;
		for (Dia d : dias) {
			d.setLinha(this);
		}
	}

	public Sentido getSentido() {
		return sentido;
	}

	public void setSentido(Sentido sentido) {
		this.sentido = sentido;
	}
}
