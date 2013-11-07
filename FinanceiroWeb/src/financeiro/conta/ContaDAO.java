package financeiro.conta;

import java.util.List;

import financeiro.usuario.Usuario;
import financeiro.util.GenericDAO;

public interface ContaDAO extends GenericDAO<Conta> {
	
	@Override
	@Deprecated
	List<Conta> listar();
	
	List<Conta> listar(Usuario usuario);
	
	Conta buscarContaFavorita(Usuario usuario);
}
