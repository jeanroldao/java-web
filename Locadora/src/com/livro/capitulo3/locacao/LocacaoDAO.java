package com.livro.capitulo3.locacao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.livro.capitulo3.util.HibernateUtil;


public class LocacaoDAO {
	private Session sessao;
	private Transaction transacao;
	
	public void salvar(Locacao locacao) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.save(locacao);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel inserir a loca��o. Erro: " + e.getMessage());
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
	
	public void atualizar(Locacao locacao) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.update(locacao);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel alterar a loca��o. Erro: " + e.getMessage());
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
	
	public void excluir(Locacao locacao) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.delete(locacao);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel excluir a loca��o. Erro: " + e.getMessage());
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
	
	public Locacao buscaLocacao(Integer codigo) {
		Locacao locacao = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(Locacao.class);
			filtro.add(Restrictions.eq("locacao", codigo));
			locacao = (Locacao) filtro.uniqueResult();
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
		
		return locacao;
	}
	
	@SuppressWarnings("unchecked")
	public List<Locacao> listar() {
		List<Locacao> locacoes = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(Locacao.class);
			locacoes = filtro.list();
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
		
		return locacoes;
	}
}
