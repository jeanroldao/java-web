package financeiro.cheque;

import java.util.Date;
import java.util.List;

import financeiro.conta.Conta;
import financeiro.lancamento.Lancamento;
import financeiro.util.BOException;
import financeiro.util.DAOFactory;

public class ChequeBO {
	
	private ChequeDAO chequeDAO;
	
	public ChequeBO() {
		chequeDAO = DAOFactory.criaChequeDAO();
	}
	
	public void salvar(Cheque cheque) {
		chequeDAO.salvar(cheque);
	}
	
	public int salvarSequencia(Conta conta, Integer chequeInicial, Integer chequeFinal) {
		Cheque cheque = null;
		Cheque chequeVerifica = null;
		ChequeId chequeId = null;
		int contaTotal = 0;
		
		for (int i = chequeInicial; i <= chequeFinal; i++) {
			chequeId = new ChequeId();
			chequeId.setCheque(i);
			chequeId.setConta(conta.getConta());
			chequeVerifica = carregar(chequeId);
			
			if (chequeVerifica == null) {
				cheque = new Cheque();
				cheque.setChequeId(chequeId);
				cheque.setSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO);
				cheque.setDataCadastro(new Date(System.currentTimeMillis()));
				salvar(cheque);
				contaTotal++;
			}
		}
		return contaTotal;
	}
	
	public void excluir(Cheque cheque) throws BOException {
		if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_NAO_EMITIDO) {
			chequeDAO.excluir(cheque);
		} else {
			throw new BOException("Não é possível exluir cheque, status não permitido para operação. ");
		}
	}
	
	public Cheque carregar(ChequeId chequeId) {
		return chequeDAO.carregar(chequeId);
	}
	
	public List<Cheque> listar(Conta conta) {
		return chequeDAO.listar(conta);
	}
	
	public void cancelarCheque(Cheque cheque) throws BOException {
		if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_NAO_EMITIDO
				|| cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_CANCELADO) {
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_CANCELADO);
			chequeDAO.salvar(cheque);
		} else {
			throw new BOException("Não é possível cancelar cheque, status não permitido para operação. ");
		}
	}
	
	public void baixarCheque(ChequeId chequeId, Lancamento lancamento) {
		Cheque cheque = carregar(chequeId);
		if (cheque != null) {
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_BAIXADO);
			cheque.setLancamento(lancamento);
			chequeDAO.salvar(cheque);
		}
	}
	
	public void desvinculaLancamento(Conta conta, Integer numeroCheque) throws BOException {
		ChequeId chequeId = new ChequeId();
		chequeId.setCheque(numeroCheque);
		chequeId.setConta(conta.getConta());
		Cheque cheque = carregar(chequeId);
		
		if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_CANCELADO) {
			throw new BOException("Não é possivel usar cheque cancelado");
		} else {
			cheque.setSituacao(Cheque.SITUACAO_CHEQUE_NAO_EMITIDO);
			cheque.setLancamento(null);
			salvar(cheque);
		}
	}
}
