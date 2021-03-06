package com.livro.capitulo3.categoria;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.livro.capitulo3.util.HibernateUtil;

public class CategoriaDAO {
	private Session sessao;
	private Transaction transacao;
	
	public void salvar(Categoria categoria) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.save(categoria);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel inserir a categoria. Erro: " + e.getMessage());
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
	
	public void atualizar(Categoria categoria) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.update(categoria);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel alterar a categoria. Erro: " + e.getMessage());
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
	
	public void excluir(Categoria categoria) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.delete(categoria);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel excluir a categoria. Erro: " + e.getMessage());
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
	
	public Categoria buscaCategoria(Integer codigo) {
		Categoria categoria = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(Categoria.class);
			filtro.add(Restrictions.eq("categoria", codigo));
			categoria = (Categoria) filtro.uniqueResult();
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
		
		return categoria;
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> listar() {
		List<Categoria> categorias = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(Categoria.class);
			categorias = filtro.list();
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
		
		return categorias;
	}
}
