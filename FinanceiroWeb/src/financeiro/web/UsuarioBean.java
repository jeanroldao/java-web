package financeiro.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import financeiro.conta.Conta;
import financeiro.conta.ContaBO;
import financeiro.usuario.Usuario;
import financeiro.usuario.UsuarioBO;

@ManagedBean(name="usuarioBean")
@RequestScoped
public class UsuarioBean {
	
	private Usuario usuario = new Usuario();
	private String confirmarSenha;
	private List<SelectItem> idiomas;
	private List<Usuario> lista;
	private String destinoSalvar;
	private Conta conta = new Conta();
	
	public String novo() {
		this.destinoSalvar = "usuarioSucesso";
		this.usuario = new Usuario();
		this.usuario.setAtivo(true);
		return "usuario";
	}
	
	public String editar() {
		this.confirmarSenha = this.usuario.getSenha();
		return "/publico/usuario";
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
		
		if (this.conta.getDescricao() != null) {
			this.conta.setUsuario(this.usuario);
			this.conta.setFavorita(true);
			
			ContaBO contaBO = new ContaBO();
			contaBO.salvar(this.conta);
		}
		
		return this.destinoSalvar;
	}

	public String excluir() {
		UsuarioBO usuarioBO = new UsuarioBO();
		usuarioBO.excluir(this.usuario);
		this.lista = null;
		return null;
	}
	
	public String ativar() {
		if (this.usuario.isAtivo()) {
			this.usuario.setAtivo(false);
		} else {
			this.usuario.setAtivo(true);
		}
		
		UsuarioBO usuarioBO = new UsuarioBO();
		usuarioBO.salvar(this.usuario);
		return null;
	}
	
	
	public String atribuiPermissao(Usuario usuario, String permissao) {
		this.usuario = usuario;
		Set<String> permissoes = usuario.getPermissao();
		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		return null;
	}
	
	public List<Usuario> getLista() {
		if (this.lista == null) {
			UsuarioBO usuarioBO = new UsuarioBO();
			this.lista = usuarioBO.listar();
		}
		return this.lista;
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
	public String getDestinoSalvar() {
		return this.destinoSalvar;
	}
	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
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
	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
