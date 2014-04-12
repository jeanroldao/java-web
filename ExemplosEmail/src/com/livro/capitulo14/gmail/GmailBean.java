package com.livro.capitulo14.gmail;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
import com.sun.mail.smtp.SMTPMessage;

@ManagedBean(name = "gmailBean")
@RequestScoped
public class GmailBean {
	
	public static final String SERVIDOR_SMTP = "smtp.gmail.com";
	public static final String PORTA_SERVIDOR_SMTP = "465";
	
	private static int contador = 0;
	
	private static Executor pool = Executors.newSingleThreadExecutor();
	
	private String usuario;
	private String senha;
	private String para;
	private String assunto;
	private String mensagem;
	
	public void enviarEmailAsync() {
		final String usuario = this.usuario, 
					 senha = this.senha,
					 para = this.para,
					 assunto = this.assunto,
					 mensagem = this.mensagem;
		pool.execute(new Runnable() {
			public void run() {
				//FacesContext context = FacesContext.getCurrentInstance();
				
				AutenticaUsuario autenticaUsuario = new AutenticaUsuario(usuario, senha);
				Properties config = new Properties();
				
				config.setProperty("mail.transport.protocol", "smtp"); 
				config.setProperty("mail.smtp.starttls.enable", "true");
				config.setProperty("mail.smtp.host", SERVIDOR_SMTP); // Servidor SMTP do GMAIL
				config.setProperty("mail.smtp.auth", "true"); // Ativa autentica��o
				config.setProperty("mail.smtp.user", usuario);
				config.setProperty("mail.debug", "true");
				config.setProperty("mail.smtp.port", PORTA_SERVIDOR_SMTP); // mesma porta para o socket
				config.setProperty("mail.smtp.socketFactory.port", PORTA_SERVIDOR_SMTP);
				config.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				config.setProperty("mail.smtp.socketFactory.fallback", "false");
				
				Session session = Session.getDefaultInstance(config, autenticaUsuario);
				
				// Habilita o LOG das a��es executadas durante o envio do email
				session.setDebug(true);
				
				try {
					Transport envio = session.getTransport("smtp");					
					envio.connect(SERVIDOR_SMTP, usuario, senha);
					
					MimeMessage email = new MimeMessage(session);
					email.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
					email.setFrom(new InternetAddress(usuario));
					email.setSubject(assunto + " (" + (contador++) + ")");
					email.setContent(mensagem, "text/plain");
					email.setSentDate(new Date());
					email.saveChanges();
					
					SMTPMessage smtpMessage = new SMTPMessage(email);
					smtpMessage.setHeader("Return-Receipt-To", usuario);
					smtpMessage.setHeader("Disposition-Notification-To", usuario);
					smtpMessage.setReturnOption(SMTPMessage.RETURN_FULL);
					smtpMessage.setNotifyOptions(SMTPMessage.NOTIFY_SUCCESS);
					
					envio.sendMessage(smtpMessage, email.getAllRecipients());
					
					envio.close();
					
					//context.addMessage(null, new FacesMessage("E-mail enviado com sucesso"));
					
				} catch (AddressException e) {
					//context.addMessage(null, new FacesMessage("Erro ao montar mensagem de e-Mail! Erro:" + e.getMessage()));
				} catch (MessagingException e) {
					//context.addMessage(null, new FacesMessage("Erro ao enviar e-Mail! Erro:" + e.getMessage()));
				}
			}
		});
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("E-mail enviado async"));
	}
	
	public void enviarEmail() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		AutenticaUsuario autenticaUsuario = new AutenticaUsuario(this.usuario, this.senha);
		Session session = Session.getDefaultInstance(this.configuracaoEmail(), autenticaUsuario);
		
		// Habilita o LOG das a��es executadas durante o envio do email
		session.setDebug(true);
		
		try {
			MimeMessage email = new MimeMessage(session);
			email.setRecipient(Message.RecipientType.TO, new InternetAddress(this.para));
			email.setFrom(new InternetAddress(this.usuario));
			email.setSubject(this.assunto + " (" + (contador++) + ")");
			email.setContent(this.mensagem, "text/plain");
			email.setSentDate(new Date());
			
			Transport envio = session.getTransport("smtp");
			
			envio.connect(SERVIDOR_SMTP, this.usuario, this.senha);
			email.saveChanges();
			envio.sendMessage(email, email.getAllRecipients());
			envio.close();
			
			context.addMessage(null, new FacesMessage("E-mail enviado com sucesso"));
			
		} catch (AddressException e) {
			context.addMessage(null, new FacesMessage("Erro ao montar mensagem de e-Mail! Erro:" + e.getMessage()));
		} catch (MessagingException e) {
			context.addMessage(null, new FacesMessage("Erro ao enviar e-Mail! Erro:" + e.getMessage()));
		}
	}

	private Properties configuracaoEmail() {
		Properties config = new Properties();
		
		// Configura��o adicional para servidor proxy
		/*
		config.setProperty("proxySet", "true");
		config.setProperty("socksProxyHost", "127.0.0.1"); // IP do servidor proxy
		config.setProperty("socksProxyPort", "8080"); // Porta do servidor proxy
		//*/
		
		config.setProperty("mail.transport.protocol", "smtp"); 
		config.setProperty("mail.smtp.starttls.enable", "true");
		config.setProperty("mail.smtp.host", SERVIDOR_SMTP); // Servidor SMTP do GMAIL
		config.setProperty("mail.smtp.auth", "true"); // Ativa autentica��o
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
