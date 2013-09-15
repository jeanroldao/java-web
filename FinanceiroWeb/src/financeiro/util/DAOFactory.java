package financeiro.util;

import financeiro.usuario.UsuarioDAO;
import financeiro.usuario.UsuarioDAOHibernate;

public class DAOFactory {
	
	public static UsuarioDAO criaUsuarioDAO() {
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}
}
