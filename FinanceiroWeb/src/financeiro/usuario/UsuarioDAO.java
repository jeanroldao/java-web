package financeiro.usuario;

import financeiro.util.GenericDAO;

public interface UsuarioDAO extends GenericDAO<Usuario> {
	public Usuario buscaPorLogin(String login);
}
