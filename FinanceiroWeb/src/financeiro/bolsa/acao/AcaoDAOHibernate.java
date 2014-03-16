package financeiro.bolsa.acao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import financeiro.usuario.Usuario;
import financeiro.util.GenericDAOHibernate;

public class AcaoDAOHibernate extends GenericDAOHibernate<Acao> implements AcaoDAO {
	public AcaoDAOHibernate() {
		super(Acao.class);
	}

	@Override
	public Acao carregar(String codigo) {
		return (Acao) session.get(type, codigo);
	}

	@Override
	public List<Acao> listar(Usuario usuario) {
		@SuppressWarnings("unchecked")
		List<Acao> lista = session.createCriteria(type).add(Restrictions.eq("usuario", usuario)).list();
		return lista;
	}
}
