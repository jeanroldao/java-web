package financeiro.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import financeiro.conta.Conta;
import financeiro.conta.ContaBO;
import financeiro.usuario.Usuario;
import financeiro.usuario.UsuarioBO;

@ManagedBean(name = "contextoBean")
@SessionScoped
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogado = null;
	private Conta contaAtiva = null;
	private Locale localizacao = null;
	private List<Locale> idiomas;
	
	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		
		if (usuarioLogado == null || !usuarioLogado.getLogin().equals(login)) {
			if (login != null) {
				UsuarioBO usuarioBO = new UsuarioBO();
				usuarioLogado = usuarioBO.buscarPorLogin(login);
				contaAtiva = null;
			}
		}
		
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuario) {
		usuarioLogado = usuario;
	}
	
	public Conta getContaAtiva() {
		if (contaAtiva == null) {
			Usuario usuario = getUsuarioLogado();
			ContaBO contaBO = new ContaBO();
			contaAtiva = contaBO.buscarFavorita(usuario);
			if (contaAtiva == null) {
				List<Conta> contas = contaBO.listar(usuario);
				if (contas != null) {
					for (Conta conta : contas) {
						contaAtiva = conta;
						break;
					}
				}
			}
		}
		return contaAtiva;
	}
	
	public void setContaAtiva(ValueChangeEvent event) {
		Integer conta = Integer.valueOf(event.getNewValue().toString());
		ContaBO contaBO = new ContaBO();
		contaAtiva = contaBO.carregar(conta);
	}
	
	public Locale getLocaleUsuario() {
		if (localizacao == null) {
			String idioma = getUsuarioLogado().getIdioma();
			String[] info = idioma.split("_");
			localizacao = new Locale(info[0], info[1]);
		}
		return localizacao;
	}
	
	public List<Locale> getIdiomas() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<Locale> locales = context.getApplication().getSupportedLocales();
		idiomas = new ArrayList<Locale>();
		while (locales.hasNext()) {
			idiomas.add(locales.next());
		}
		return idiomas;
	}
	
	public void setIdiomaUsuario(String idioma) {
		UsuarioBO usuarioBO = new UsuarioBO();
		usuarioLogado = usuarioBO.carregar(getUsuarioLogado().getCodigo());
		usuarioLogado.setIdioma(idioma);
		usuarioBO.salvar(usuarioLogado);
		
		String[] info = idioma.split("_");
		Locale locale = new Locale(info[0], info[1]);
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(locale);
	}
}
