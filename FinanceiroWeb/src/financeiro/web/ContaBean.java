package financeiro.web;

import java.util.ArrayList;
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

	
	public void excluir() {
		ContaBO contaBO = new ContaBO();
		contaBO.excluir(selecionada);
		selecionada = new Conta();
		lista = null;
	}
	
	public void tornarFavorita() {
		ContaBO contaBO = new ContaBO();
		contaBO.tornarFavorita(selecionada);
		selecionada = new Conta();
	}

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	public List<Conta> getLista() {
		if (lista == null) {
			ContaBO contaBO = new ContaBO();
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			lista = contaBO.listar(contextoBean.getUsuarioLogado());
		}
		return lista;
	}
}
