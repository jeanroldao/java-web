package financeiro.cheque;

import java.util.List;

import financeiro.conta.Conta;

public interface ChequeDAO {
	
	void salvar(Cheque cheque);
	void excluir(Cheque cheque);
	Cheque carregar(ChequeID chequeID);
	List<Cheque> listar(Conta conta);
}
