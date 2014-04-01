package com.livro.capitulo14.autenticacao;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class AutenticaUsuario extends Authenticator {
	private String usuario;
	private String senha;
	
	public AutenticaUsuario(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}
	
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(usuario, senha);
	}
}
