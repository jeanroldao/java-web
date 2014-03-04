package financeiro.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import financeiro.categoria.Categoria;
import financeiro.conta.Conta;
import financeiro.entidade.Entidade;
import financeiro.entidade.EntidadeBO;
import financeiro.lancamento.Lancamento;
import financeiro.lancamento.LancamentoBO;
import financeiro.web.util.ContextoUtil;

@ManagedBean(name = "lancamentoBean")
@ViewScoped
public class LancamentoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Lancamento> lista;
	private List<Lancamento> listaAteHoje;
	private List<Lancamento> listaFuturos;
	private List<Double> saldos = new ArrayList<Double>();
	private float saldoGeral;
	
	private Lancamento editado;
	
	public LancamentoBean() {
		novo();
	}
	
	public void novo() {
		editado = new Lancamento();
		editado.setData(new Date(System.currentTimeMillis()));
	}
	
	public void editar() {
		
	}
	
	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		editado.setUsuario(contextoBean.getUsuarioLogado());
		editado.setConta(contextoBean.getContaAtiva());
		
		LancamentoBO lancamentoBO = new LancamentoBO();
		lancamentoBO.salvar(editado);
		
		novo();
		lista = null;
	}
	
	public void excluir() {
		LancamentoBO lancamentoBO = new LancamentoBO();
		editado = lancamentoBO.carregar(editado.getLancamento());
		lancamentoBO.excluir(editado);
		novo();
		lista = null;
	}
	
	public List<Entidade> autocompleteEntidade(String query) {
		List<Entidade> lista = new EntidadeBO().listarPorNome(query);
		return lista;
	}
	
	public List<Lancamento> getLista() {
		if (lista == null) {
			saldos = new ArrayList<Double>();
			
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar dataSaldo = new GregorianCalendar();
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);
			
			Calendar inicio = new GregorianCalendar();
			inicio.add(Calendar.MONTH, -1);
			
			LancamentoBO lancamentoBO = new LancamentoBO();
			saldoGeral = lancamentoBO.saldo(conta, dataSaldo.getTime());
			lista = lancamentoBO.listar(conta, inicio.getTime(), null);
			
			Categoria categoria = null;
			double saldo = saldoGeral;
			for (Lancamento lancamento : lista) {
				categoria = lancamento.getCategoria();
				saldo += lancamento.getValor().floatValue() * categoria.getFator();
				saldos.add(saldo);
			}
		}
		return lista;
	}
	
	public List<Lancamento> getListaAteHoje() {
		if (listaAteHoje == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar hoje = new GregorianCalendar();
			
			LancamentoBO lancamentoBO = new LancamentoBO();
			listaAteHoje = lancamentoBO.listar(conta, null, hoje.getTime());
		}
		return listaAteHoje;
	}


	public List<Lancamento> getListaFuturos() {
		if (listaFuturos == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar amanha = new GregorianCalendar();
			amanha.add(Calendar.DAY_OF_MONTH, 1);
			
			LancamentoBO lancamentoBO = new LancamentoBO();
			listaFuturos = lancamentoBO.listar(conta, amanha.getTime(), null);
		}
		return listaFuturos;
	}
	
	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}

	public List<Double> getSaldos() {
		return saldos;
	}

	public float getSaldoGeral() {
		return saldoGeral;
	}
}
