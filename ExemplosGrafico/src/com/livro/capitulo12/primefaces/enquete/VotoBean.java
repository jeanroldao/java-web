package com.livro.capitulo12.primefaces.enquete;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "votoBean")
@RequestScoped
public class VotoBean {
	private List<Voto> votos;
	
	public VotoBean() {
		votos = new ArrayList<>(3);
		votos.add(new Voto("Marca A", 70));
		votos.add(new Voto("Marca B", 20));
		votos.add(new Voto("Marca C", 30));
	}
	
	public List<Voto> getVotos() {
		int valor1 = (int) (Math.random() * 1000);
		int valor2 = (int) (Math.random() * 1000);
		int valor3 = (int) (Math.random() * 1000);
		
		votos.get(0).setTotal(valor1);
		votos.get(1).setTotal(valor2);
		votos.get(2).setTotal(valor3);
		
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}
}
