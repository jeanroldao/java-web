package financeiro.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import financeiro.categoria.Categoria;
import financeiro.conta.Conta;
import financeiro.lancamento.Lancamento;
import financeiro.lancamento.LancamentoBO;
import financeiro.web.util.ContextoUtil;

@ManagedBean(name = "lancamentoBean")
@ViewScoped
public class LancamentoBean {
	private List<Lancamento> lista;
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

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}

	public List<Double> getSaldos() {
		saldos.get(0).doubleValue();
		return saldos;
	}

	public float getSaldoGeral() {
		return saldoGeral;
	}
}
