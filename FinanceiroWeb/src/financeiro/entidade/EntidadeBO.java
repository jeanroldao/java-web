package financeiro.entidade;

import java.util.List;

import financeiro.util.DAOFactory;

public class EntidadeBO {
	private EntidadeDAO entidadeDAO;
	
	public EntidadeBO() {
		entidadeDAO = DAOFactory.criaEntidadeDAO();
	}
	
	public List<Entidade> listarPorNome(String nome) {
		return entidadeDAO.listarPorNome(nome);
	}
	
	public Entidade buscarPorNome(String nome) {
		return entidadeDAO.buscarPorNome(nome);
	}
}
