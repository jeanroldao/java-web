package financeiro.web;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="usuarioBean")
@RequestScoped
public class UsuarioBean {
	private String nome;
	private String email;
	private String senha;
	private String confirmaSenha;
	
	public String inicio() {
		return "index";
	}
	
	public String novo() {
		return "usuario";
	}
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (!senha.equalsIgnoreCase(confirmaSenha)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Senha confirmada incorretamente", ""));
			return null;
		}
		
		//salva o usuario
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		context.getApplication()
		       .evaluateExpressionGet(context, "#{listaUsuariosBean}", ListaUsuariosBean.class)
		       .save(usuario);
		
		return "mostraUsuario";
	}
	
	public String verUsuario(Usuario usuario) {
		nome = usuario.getNome();
		email = usuario.getEmail();
		senha = confirmaSenha = usuario.getSenha();
		
		return "mostraUsuario";
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
}
