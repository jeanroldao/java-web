package financeiro.conta;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import financeiro.usuario.Usuario;
import financeiro.util.GenericDAOHibernate;

public class ContaDAOHibernate extends GenericDAOHibernate<Conta> implements ContaDAO {
	public ContaDAOHibernate() {
		super(Conta.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Conta> listar(Usuario usuario) {
		Criteria criteria = session.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		return criteria.list();
	}

	@Override
	public Conta buscarFavorita(Usuario usuario) {
		Criteria criteria = session.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		criteria.add(Restrictions.eq("favorita", true));
		return (Conta) criteria.uniqueResult();
	}
}
