package financeiro.usuario;

import java.util.List;
import java.util.Locale;

import financeiro.categoria.CategoriaBO;
import financeiro.util.BOException;
import financeiro.util.DAOFactory;
import financeiro.util.UtilException;
import financeiro.web.util.EmailUtil;
import financeiro.web.util.MensagemUtil;

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
			
			CategoriaBO categoriaBO = new CategoriaBO();
			categoriaBO.salvaEstruturaPadrao(usuario);
		} else {
			usuarioDAO.atualizar(usuario);
		}
	}
	
	public void excluir(Usuario usuario) {
		CategoriaBO categoriaBO = new CategoriaBO();
		categoriaBO.excluir(usuario);
		
		usuarioDAO.excluir(usuario);
	}
	
	public List<Usuario> listar() {
		return usuarioDAO.listar();
	}
	
	public void enviarEmailPosCadastramento(Usuario usuario) throws BOException {
		
		// Enviando um email conforme o idioma do usuario
		String[] info = usuario.getIdioma().split("_");
		Locale locale = new Locale(info[0], info[1]);
		String titulo = MensagemUtil.getMensagem(locale, "email_titulo");
		String mensagem = MensagemUtil.getMensagem(locale, "email_mensagem", usuario.getNome(), usuario.getLogin(), usuario.getSenha());
		
		try {
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.enviarEmail(null, usuario.getEmail(), titulo, mensagem);
		} catch (UtilException e) {
			throw new BOException(e);
		}
	}
}
