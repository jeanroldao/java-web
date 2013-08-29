package com.livro.capitulo3.crudannotations;

import java.sql.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.livro.capitulo3.conexao.HibernateUtil;

public class ContatoCrudAnnotations {
	
	public void salvar(ContatoAnnotations contato) {
		Session sessao = null;
		Transaction transacao = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.save(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel inserir o contato. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operação de inserção. Mensagem: " + e.getMessage());
			}
		}
	}
	
	public void atualizar(ContatoAnnotations contato) {
		Session sessao = null;
		Transaction transacao = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.update(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel alterar o contato. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operação de atualização. Mensagem: " + e.getMessage());
			}
		}
	}
	
	public void excluir(ContatoAnnotations contato) {
		Session sessao = null;
		Transaction transacao = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.update(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel excluir o contato. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operação de exclusão. Mensagem: " + e.getMessage());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ContatoAnnotations> listar() {
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<ContatoAnnotations> resultado = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato");
			resultado = consulta.list();
			transacao.commit();
			return resultado;
		} catch (HibernateException e) {
			System.out.println("Não foi possivel selecionar contatos. Erro: " + e.getMessage());
			throw new HibernateException(e);
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operação de consulta. Mensagem: " + e.getMessage());
			}
		}
	}
	
	public ContatoAnnotations buscaContato(int valor) {
		ContatoAnnotations contato = null;
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato where codigo = :parametro");
			consulta.setInteger("parametro", valor);
			contato = (ContatoAnnotations)consulta.uniqueResult();
			transacao.commit();
			return contato;
		} catch (HibernateException e) {
			System.out.println("Não foi possivel buscar o contato. Erro: " + e.getMessage());
			throw new HibernateException(e);
		} finally {
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operação de buscar. Mensagem: " + e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		ContatoCrudAnnotations contatoCrudAnnotations = new ContatoCrudAnnotations();
		
		String[] nomes = {"Solano", "Lunare", "Venusiana"};
		String[] fones = {"(47) 4132-4213", "(47) 7777-7777", "(47) 3422-6632"};
		String[] emails = {"solano@javapro.com.br", "lunare@javapro.com.br", "venusiana@javapro.com.br"};
		String[] observacoes = {"Novo cliente", "Cliente em dia", "Ligar na sexta"};
		
		ContatoAnnotations contato = null;
		
		for (int i = 0; i < nomes.length; i++) {
			contato = new ContatoAnnotations();
			contato.setNome(nomes[i]);
			contato.setTelefone(fones[i]);
			contato.setEmail(emails[i]);
			contato.setDataCadastro(new Date(System.currentTimeMillis()));
			contato.setObservacao(observacoes[i]);
			contatoCrudAnnotations.salvar(contato);
		}
		System.out.println("(Annotations) Total de registros cadastrados: " + contatoCrudAnnotations.listar().size());
	}
}
