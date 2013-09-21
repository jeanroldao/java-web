package financeiro.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import financeiro.usuario.Usuario;
import financeiro.usuario.UsuarioBO;

@ManagedBean(name="usuarioBean")
@RequestScoped
public class UsuarioBean {
	private Usuario usuario = new Usuario();
	private String confirmarSenha;
	private List<SelectItem> idiomas;
	
	public String novo() {
		this.usuario = new Usuario();
		this.usuario.setAtivo(true);
		return "usuario";
	}
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		String senha = this.usuario.getSenha();
		if (!senha.equals(this.confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha não foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		
		UsuarioBO usuarioBO = new UsuarioBO();
		usuarioBO.salvar(this.usuario);
		
		return "usuarioSucesso";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	public List<SelectItem> getIdiomas() {
		if (idiomas == null) {
			idiomas = new ArrayList<SelectItem>();
			idiomas.add(new SelectItem("pt_BR", "Português"));
			idiomas.add(new SelectItem("en_US", "English"));
			idiomas.add(new SelectItem("es_ES", "Español"));
			idiomas.add(new SelectItem("jp_JP", "日本語"));
		}
		return idiomas;
	}
}
