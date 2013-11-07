package financeiro.usuario;

import java.util.List;

import financeiro.util.DAOFactory;

public class UsuarioBO {
	
	private UsuarioDAO usuarioDAO;
	
	public UsuarioBO() {
		usuarioDAO = DAOFactory.criarUsuarioDAO();
	}
	
	public Usuario carregar(Integer codigo) {
		return usuarioDAO.carregar(codigo);
	}
	
	public Usuario buscarPorLogin(String login) {
		return usuarioDAO.buscarPorLogin(login);
	}
	
	public void salvar(Usuario usuario) { 
		Integer codigo = usuario.getCodigo();
		if (codigo == null || codigo == 0) {
			usuario.getPermissao().add("ROLE_USUARIO");
			usuarioDAO.salvar(usuario);
		} else {
			usuarioDAO.atualizar(usuario);
		}
	}
	
	public void excluir(Usuario usuario) {
		usuarioDAO.excluir(usuario);
	}
	
	public List<Usuario> listar() {
		return usuarioDAO.listar();
	}
}
