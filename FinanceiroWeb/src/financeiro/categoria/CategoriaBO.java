package financeiro.categoria;

import java.util.List;

import financeiro.usuario.Usuario;
import financeiro.util.DAOFactory;

public class CategoriaBO {
	
	private CategoriaDAO categoriaDAO;
	
	public CategoriaBO() {
		categoriaDAO = DAOFactory.criaCategoriaDAO();
	}
	
	public List<Categoria> listar(Usuario usuario) {
		return categoriaDAO.listar(usuario);
	}
	
	
	public Categoria salvar(Categoria categoria) {
		Categoria pai = categoria.getPai();
		
		if (pai == null) {
			String msg = "A Categoria " + categoria.getDescricao() + " deve ter um pai definido";
			throw new IllegalArgumentException(msg);
		}
		
		boolean mudouFator = pai.getFator() != categoria.getFator();
		
		categoria.setFator(pai.getFator());
		categoria = categoriaDAO.salvar(categoria);
		
		if (mudouFator) {
			categoria = categoriaDAO.carregar(categoria.getCodigo());
			replicarFator(categoria, categoria.getFator());
		}
		return categoria;
	}
	
	private void replicarFator(Categoria categoria, int fator) {
		if (categoria.getFilhos() != null) {
			for (Categoria filho : categoria.getFilhos()) {
				filho.setFator(fator);
				categoriaDAO.salvar(filho);
				replicarFator(filho, fator);
			}
		}
	}
	
	public void excluir(Categoria categoria) {
		categoriaDAO.excluir(categoria);
	} 
	
	public void excluir(Usuario usuario) {
		List<Categoria> lista = listar(usuario);
		for (Categoria categoria : lista) {
			excluir(categoria);
		}
	}
	
	public Categoria carregar(Integer categoria) {
		return categoriaDAO.carregar(categoria);
	}
	
	
	public void salvaEstruturaPadrao(Usuario usuario) {
		Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
		despesas = categoriaDAO.salvar(despesas);
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Moradia", -1));
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Alimentação", -1));
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Vestuário", -1));
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Deslocamento", -1));
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Educação", -1));
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Saúde", -1));
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Lazer", -1));
		categoriaDAO.salvar(new Categoria(despesas, usuario, "Despesas Financeiras", -1));
		
		Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
		receitas = categoriaDAO.salvar(receitas);
		categoriaDAO.salvar(new Categoria(receitas, usuario, "Salário", 1));
		categoriaDAO.salvar(new Categoria(receitas, usuario, "Restituições", 1));
		categoriaDAO.salvar(new Categoria(receitas, usuario, "Rendimento", 1));
	}
}
