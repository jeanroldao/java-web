package com.livro.capitulo14.commonsmail;

import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

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
			MultiPartEmail email = new MultiPartEmail();
			
			// Informações do servidor
			email.setHostName(SERVIDOR_SMTP);
			email.setSmtpPort(PORTA_SERVIDOR_SMTP);
			
			// Autenticando no servidor
			email.setAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
			
			// Montando o e-mail
			email.setFrom(this.de, "Servidor localhost");
			email.addTo(this.para, this.para);
			email.setSubject(this.assunto);
			email.setMsg(this.mensagem);
			
			List<InternetAddress> normais = montaDestinatarios(this.destinatariosNormais);
			if (normais != null) {
				email.setCc(normais);
			}
			
			List<InternetAddress> ocultos = montaDestinatarios(this.destinatariosOcultos);
			if (ocultos != null) {
				email.setBcc(ocultos);
			}
			
			// Cria o anexo
			if (this.anexo != null && this.anexo.trim().length() > 0) {
				EmailAttachment anexoEmail = new EmailAttachment();
				anexoEmail.setPath(this.anexo);
				anexoEmail.setDisposition(EmailAttachment.ATTACHMENT);
				
				// Adiciona o anexo ao e-mail
				email.attach(anexoEmail);
			}
			email.send();
			
			context.addMessage(null, new FacesMessage("E-mail autenticado enviado com sucesso"));
			
		} catch (EmailException e) {
			context.addMessage(null, new FacesMessage("Erro ao enviar e-Mail! Erro:" + e.getMessage()));
		} catch (AddressException e) {
			context.addMessage(null, new FacesMessage("Erro ao montar mensagem de e-Mail! Erro:" + e.getMessage()));
		}
	}
	
	public void enviarSimples() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			
			// Cria o email preparado para os anexos
			SimpleEmail email = new SimpleEmail();
			
			// Informações do servidor
			email.setHostName(SERVIDOR_SMTP);
			email.setSmtpPort(PORTA_SERVIDOR_SMTP);
			
			// Montando o e-mail
			email.setFrom(this.de, "Servidor localhost");
			email.addTo(this.para, this.para);
			email.setSubject(this.assunto);
			email.setMsg(this.mensagem);
			
			List<InternetAddress> normais = montaDestinatarios(this.destinatariosNormais);
			if (normais != null) {
				email.setCc(normais);
			}
			
			List<InternetAddress> ocultos = montaDestinatarios(this.destinatariosOcultos);
			if (ocultos != null) {
				email.setBcc(ocultos);
			}
			
			email.send();
			
			context.addMessage(null, new FacesMessage("E-mail simples enviado com sucesso"));
			
		} catch (EmailException e) {
			context.addMessage(null, new FacesMessage("Erro ao enviar e-Mail! Erro:" + e.getMessage()));
		} catch (AddressException e) {
			context.addMessage(null, new FacesMessage("Erro ao montar mensagem de e-Mail! Erro:" + e.getMessage()));
		}
	}
	
	private List<InternetAddress> montaDestinatarios(String destinatarios) throws AddressException {
		InternetAddress[] emails = null;
		if (destinatarios != null && destinatarios.trim().length() > 0) {
			String[] lista = destinatarios.split(";");
			emails = new InternetAddress[lista.length];
			for (int i = 0; i < lista.length; i++) {
				emails[i] = new InternetAddress(lista[i]);
			}
		}
		return Arrays.asList(emails);
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getDestinatariosNormais() {
		return destinatariosNormais;
	}

	public void setDestinatariosNormais(String destinatariosNormais) {
		this.destinatariosNormais = destinatariosNormais;
	}

	public String getDestinatariosOcultos() {
		return destinatariosOcultos;
	}

	public void setDestinatariosOcultos(String destinatariosOcultos) {
		this.destinatariosOcultos = destinatariosOcultos;
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

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
}
