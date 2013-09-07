package com.livro.capitulo3.produto;

import com.livro.capitulo3.dao.GenericDAO;

public class ProdutoDAO extends GenericDAO<Produto> {
	
	public ProdutoDAO() {
		super(Produto.class);
	}
}
