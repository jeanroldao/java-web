package com.livro.capitulo14.gmail;

import java.util.Date;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.livro.capitulo14.autenticacao.AutenticaUsuario;

@ManagedBean(name = "gmailBean")
@RequestScoped
public class GmailBean {
	
	public static final String SERVIDOR_SMTP = "stmp.gmail.com";
	public static final String PORTA_SERVIDOR_SMTP = "465";
	
	private String usuario;
	private String senha;
	private String para;
	private String assunto;
	private String mensagem;
	
	public void enviarEmail() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		AutenticaUsuario autenticaUsuario = new AutenticaUsuario(this.usuario, this.senha);
		Session session = Session.getDefaultInstance(this.configuracaoEmail(), autenticaUsuario);
		
		// Habilita o LOG das ações executadas durante o envio do email
		session.setDebug(true);
		
		try {
			MimeMessage email = new MimeMessage(session);
			email.setRecipient(Message.RecipientType.TO, new InternetAddress(this.para));
			email.setFrom(new InternetAddress(this.usuario));
			email.setSubject(this.assunto);
			email.setContent(this.mensagem, "text/plain");
			email.setSentDate(new Date());
			
			Transport envio = session.getTransport("smtp");
			
			envio.connect(SERVIDOR_SMTP, this.usuario, this.senha);
			email.saveChanges();
			envio.sendMessage(email, email.getAllRecipients());
			envio.close();
			
		} catch (AddressException e) {
			context.addMessage(null, new FacesMessage("Erro ao montar mensagem de e-Mail! Erro:" + e.getMessage()));
		} catch (MessagingException e) {
			context.addMessage(null, new FacesMessage("Erro ao enviar e-Mail! Erro:" + e.getMessage()));
		}
	}

	private Properties configuracaoEmail() {
		Properties config = new Properties();
		
		// Configuração adicional para servidor proxy
		/*
		config.setProperty("proxySet", "true");
		config.setProperty("socksProxyHost", "127.0.0.1"); // IP do servidor proxy
		config.setProperty("socksProxyPort", "8080"); // Porta do servidor proxy
		//*/
		
		config.setProperty("mail.transport.protocol", "smtp"); 
		config.setProperty("mail.smtp.starttls.enable", "true");
		config.setProperty("mail.smtp.host", SERVIDOR_SMTP); // Servidor SMTP do GMAIL
		config.setProperty("mail.smtp.auth", "true"); // Ativa autenticação
		config.setProperty("mail.smtp.user", this.usuario);
		config.setProperty("mail.debug", "true");
		config.setProperty("mail.smtp.port", PORTA_SERVIDOR_SMTP); // mesma porta para o socket
		config.setProperty("mail.smtp.socketFactory.port", PORTA_SERVIDOR_SMTP);
		config.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		config.setProperty("mail.smtp.socketFactory.fallback", "false");
		
		return config;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
