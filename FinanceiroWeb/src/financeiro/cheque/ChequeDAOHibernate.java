package financeiro.cheque;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import financeiro.conta.Conta;
import financeiro.util.GenericDAOHibernate;

public class ChequeDAOHibernate extends GenericDAOHibernate<Cheque> implements ChequeDAO {
	
	public ChequeDAOHibernate() {
		super(Cheque.class);
	}

	@Override
	public Cheque carregar(ChequeID chequeID) {
		return (Cheque) session.get(type, chequeID);
	}

	@Override
	public List<Cheque> listar(Conta conta) {
		@SuppressWarnings("unchecked")
		List<Cheque> lista = session.createCriteria(type)
									.add(Restrictions.eq("conta", conta))
									.list();
		return lista;
	}
}
