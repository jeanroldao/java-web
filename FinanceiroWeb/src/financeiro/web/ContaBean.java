package financeiro.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import financeiro.conta.Conta;
import financeiro.conta.ContaBO;
import financeiro.web.util.ContextoUtil;

@ManagedBean(name = "contaBean")
@RequestScoped
public class ContaBean {
	
	private Conta selecionada = new Conta();
	private List<Conta> lista = null;
	
	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		selecionada.setUsuario(contextoBean.getUsuarioLogado());
		ContaBO contaBO = new ContaBO();
		contaBO.salvar(selecionada);
		selecionada = new Conta();
		lista = null;
	}
	
	public void editar() {}
	
	public void tornarFavorita() {}

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	public List<Conta> getLista() {
		return lista;
	}

	public void setLista(List<Conta> lista) {
		this.lista = lista;
	}
}
