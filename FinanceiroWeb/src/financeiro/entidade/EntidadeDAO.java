package financeiro.entidade;

import java.util.List;

public interface EntidadeDAO {
	
	List<Entidade> listarPorNome(String nome);
	Entidade buscarPorNome(String nome);
}
