package com.jeanroldao.webminer;

public class Horario {
	private String sentido;
	private String linha;
	private String dia;
	private String hora;
	private String descricao;
	
	public Horario() {
		super();
	}
	
	public Horario(String sentido, String linha, String dia, String hora, String descricao) {
		super();
		this.sentido = sentido;
		this.linha = linha;
		this.dia = dia;
		this.hora = hora;
		this.descricao = descricao;
	}
	
	public String getSentido() {
		return sentido;
	}
	public void setSentido(String sentido) {
		this.sentido = sentido;
	}
	public String getLinha() {
		return linha;
	}
	public void setLinha(String linha) {
		this.linha = linha;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String toJson() {
		return "{\"sentido\": \""+sentido+"\", \"linha\":\""+linha+"\", \"dia\":\""+dia+"\","
				+ "\"hora\":\""+hora+"\", \"descricao\":\""+descricao+"\"}"; 
	}
}
