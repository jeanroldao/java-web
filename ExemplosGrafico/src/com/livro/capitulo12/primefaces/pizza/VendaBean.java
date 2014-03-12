package com.livro.capitulo12.primefaces.pizza;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "vendaBean")
@RequestScoped
public class VendaBean {
	
	private List<Venda> vendaPais;
	
	public VendaBean() {
		List<Venda> vendaPais = new ArrayList<>();
		vendaPais.add(new Venda("Brasil", 540.50f));
		vendaPais.add(new Venda("Estados Unidos", 500.52f));
		vendaPais.add(new Venda("Inglaterra", 4750.30f));
		vendaPais.add(new Venda("França", 500));
		vendaPais.add(new Venda("Alemanha", 397.33f));
		this.vendaPais = vendaPais;
	}

	public List<Venda> getVendaPais() {
		return vendaPais;
	}

}
