package com.livro.capitulo14.commonsmail;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "commonsMailBean")
@RequestScoped
public class CommonsMailBean {
	public static final String SERVIDOR_SMTP = "localhost";
	public static final int PORTA_SERVIDOR_SMTP = 25;
	
	private static final String CONTA_PADRAO = "livrojava@localhost";
	private static final String SENHA_CONTA_PADRAO = "123";
	
	private String de;
	private String para;
	private String destinatariosNormais;
	private String destinatariosOcultos;
	private String assunto;
	private String mensagem;
	private String anexo;
	
	public void enviarAutenticado() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			// Cria o email preparado para os anexos
			//MultiPartEmail email;
		} catch (Exception e) {}
	}
}
