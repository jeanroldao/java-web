package com.livro.capitulo3.filme;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.livro.capitulo3.util.HibernateUtil;

public class FilmeDAO {
	private Session sessao;
	private Transaction transacao;
	
	public void salvar(Filme filme) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.save(filme);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel inserir o filme. Erro: " + e.getMessage());
		} finally {
			try {
				if (sessao.isOpen()) {
					sessao.close();
				}
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de inser��o. Mensagem: " + e.getMessage());
			}			
		}
	}
	
	public void atualizar(Filme filme) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.update(filme);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel alterar o filme. Erro: " + e.getMessage());
		} finally {
			try {
				if (sessao.isOpen()) {
					sessao.close();
				}
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de atualiza��o. Mensagem: " + e.getMessage());
			}			
		}
	}
	
	public void excluir(Filme filme) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.delete(filme);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel excluir o filme. Erro: " + e.getMessage());
		} finally {
			try {
				if (sessao.isOpen()) {
					sessao.close();
				}
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de exclus�o. Mensagem: " + e.getMessage());
			}			
		}
	}
	
	public Filme buscaFilme(Integer codigo) {
		Filme filme = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(Filme.class);
			filtro.add(Restrictions.eq("filme", codigo));
			filme = (Filme) filtro.uniqueResult();
			transacao.commit();
		} catch (Throwable e) {
			if (transacao.isActive()) {
				transacao.rollback();
			}
		} finally {
			try {
				if (sessao.isOpen()) {
					sessao.close();
				}
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de busca. Mensagem: " + e.getMessage());
			}
		}
		
		return filme;
	}
	
	@SuppressWarnings("unchecked")
	public List<Filme> listar() {
		List<Filme> filmes = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(Filme.class);
			filmes = filtro.list();
			transacao.commit();
		} catch (Throwable e) {
			if (transacao.isActive()) {
				transacao.rollback();
			}
		} finally {
			try {
				if (sessao.isOpen()) {
					sessao.close();
				}
			} catch (Throwable e) {
				System.out.println("Erro ao fechar opera��o de listar. Mensagem: " + e.getMessage());
			}
		}
		
		return filmes;
	}

}
