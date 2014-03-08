package financeiro.web;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import financeiro.cheque.Cheque;
import financeiro.cheque.ChequeBO;
import financeiro.conta.Conta;
import financeiro.util.BOException;
import financeiro.web.util.ContextoUtil;
import financeiro.web.util.MensagemUtil;

@ManagedBean(name = "chequeBean")
@RequestScoped
class ChequeBean {
	private Cheque selecionado = new Cheque();
	private List<Cheque> lista = null;
	private Integer chequeInicial;
	private Integer chequeFinal;

	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		Conta conta = contextoBean.getContaAtiva();
		
		int totalCheques = 0;
		if (chequeInicial == null || chequeFinal == null) {
			String texto = MensagemUtil.getMensagem("cheque_erro_sequencia");
			FacesMessage msg = new FacesMessage(texto);
			context.addMessage(null, msg);
		} else {
			ChequeBO chequeBO = new ChequeBO();
			totalCheques = chequeBO.salvarSequencia(conta, chequeInicial, chequeFinal);
			String texto = MensagemUtil.getMensagem("cheque_info_cadastro", chequeFinal, totalCheques);
			FacesMessage msg = new FacesMessage(texto);
			context.addMessage(null, msg);
			lista = null;
		}
	}
	
	public void excluir() {
		ChequeBO chequeBO = new ChequeBO();
		
		try {
			chequeBO.excluir(selecionado);
		} catch (BOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			String texto = MensagemUtil.getMensagem("cheque_erro_excluir");
			FacesMessage msg = new FacesMessage(texto);
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, msg);
		}
		lista = null;
	}
	
	public void cancelar() {
		ChequeBO chequeBO = new ChequeBO();
		
		try {
			chequeBO.cancelarCheque(selecionado);
		} catch (BOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			String texto = MensagemUtil.getMensagem("cheque_erro_cancelar");
			FacesMessage msg = new FacesMessage(texto);
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, msg);
		}
		lista = null;
	}
	
	public List<Cheque> getLista() {
		if (lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			ChequeBO chequeBO = new ChequeBO();
			
			lista = chequeBO.listar(conta);
		}
		return lista;
	}

	public Cheque getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Cheque selecionado) {
		this.selecionado = selecionado;
	}

	public Integer getChequeInicial() {
		return chequeInicial;
	}

	public void setChequeInicial(Integer chequeInicial) {
		this.chequeInicial = chequeInicial;
	}

	public Integer getChequeFinal() {
		return chequeFinal;
	}

	public void setChequeFinal(Integer chequeFinal) {
		this.chequeFinal = chequeFinal;
	}
}
