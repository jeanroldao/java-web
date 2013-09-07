package com.livro.capitulo3.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.livro.capitulo3.util.HibernateUtil;

public abstract class GenericDAO<T> {
	private Session sessao;
	private Transaction transacao;
	private Class<T> type;
	
	protected GenericDAO(Class<T> type) {
		this.type = type;
	}
	
	public void salvar(T entity) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.save(entity);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel inserir " + type.getName() + ". Erro: " + e.getMessage());
		} finally {
			try {
				if (sessao.isOpen()) {
					sessao.close();
				}
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operação de inserção. Mensagem: " + e.getMessage());
			}			
		}
	}
	
	public void atualizar(T entity) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.update(entity);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel alterar " + type.getName() + ". Erro: " + e.getMessage());
		} finally {
			try {
				if (sessao.isOpen()) {
					sessao.close();
				}
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operação de atualização. Mensagem: " + e.getMessage());
			}			
		}
	}
	
	public void excluir(T entity) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.delete(entity);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel excluir " + entity.getClass().getName() + ". Erro: " + e.getMessage());
		} finally {
			try {
				if (sessao.isOpen()) {
					sessao.close();
				}
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operação de exclusão. Mensagem: " + e.getMessage());
			}			
		}
	}
	
	public T buscarPorCodigo(Integer codigo) {
		T entity = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(type);
			filtro.add(Restrictions.idEq(codigo));
			entity = type.cast(filtro.uniqueResult());
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
				System.out.println("Erro ao fechar operação de busca. Mensagem: " + e.getMessage());
			}
		}
		
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listar() {
		List<T> entities = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(type);
			entities = filtro.list();
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
				System.out.println("Erro ao fechar operação de listar. Mensagem: " + e.getMessage());
			}
		}
		
		return entities;
	}

}
