package financeiro.util;

import java.util.List;

public interface GenericDAO<T> {
	public void salvar(T entity);
	public void atualizar(T entity);
	public void excluir(T entity);
	public T carregar(Integer codigo);
	public List<T> listar();
}
