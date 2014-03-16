package financeiro.bolsa.acao;

import java.util.List;

import financeiro.usuario.Usuario;

public interface AcaoDAO {
	
	void salvar(Acao acao);
	
	void excluir(Acao acao);
	
	Acao carregar(String codigo);
	
	List<Acao> listar(Usuario usuario);
}
