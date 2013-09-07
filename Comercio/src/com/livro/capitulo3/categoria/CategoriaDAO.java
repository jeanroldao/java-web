package com.livro.capitulo3.categoria;

import com.livro.capitulo3.dao.GenericDAO;

public class CategoriaDAO extends GenericDAO<Categoria> {
	
	public CategoriaDAO() {
		super(Categoria.class);
	}
}
