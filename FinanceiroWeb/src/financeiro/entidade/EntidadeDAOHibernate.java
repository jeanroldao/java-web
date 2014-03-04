package financeiro.entidade;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import financeiro.util.GenericDAOHibernate;

public class EntidadeDAOHibernate extends GenericDAOHibernate<Entidade> implements EntidadeDAO {
	
	public EntidadeDAOHibernate() {
		super(Entidade.class);
	}
	
	public List<Entidade> listarPorNome(String nome) {
		@SuppressWarnings("unchecked")
		List<Entidade> lista = session.createCriteria(type).add(Restrictions.ilike("nome", nome + "%")).list();
		return lista;
	}
	
	public Entidade buscarPorNome(String nome) {
		Criteria criteria = session.createCriteria(type);
		criteria.add(Restrictions.eq("nome", nome));
		Entidade entidade = (Entidade) criteria.uniqueResult();
		
		if (entidade == null) {
			entidade = new Entidade();
			entidade.setNome(nome);
			salvar(entidade);
		}
		
		return entidade;
	}
}
