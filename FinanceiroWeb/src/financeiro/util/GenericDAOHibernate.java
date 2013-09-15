package financeiro.util;

import java.util.List;

import org.hibernate.Session;

public class GenericDAOHibernate<T> implements GenericDAO<T> {
	
	private Class<T> type;
	protected Session session;
	
	public GenericDAOHibernate(Class<T> type) {
		this.type = type;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public void salvar(T entity) {
		session.save(entity);
	}

	@Override
	public void atualizar(T entity) {
		session.update(entity);
	}

	@Override
	public void excluir(T entity) {
		session.delete(entity);
	}

	@Override
	public T carregar(Integer codigo) {
		return type.cast(session.get(type, codigo));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listar() {
		return session.createCriteria(type).list();
	}
}
