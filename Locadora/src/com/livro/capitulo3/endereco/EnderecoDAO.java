package com.livro.capitulo3.endereco;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.livro.capitulo3.util.HibernateUtil;

public class EnderecoDAO {
	private Session sessao;
	private Transaction transacao;
	
	public void salvar(Endereco endereco) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.save(endereco);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel inserir o endere�o. Erro: " + e.getMessage());
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
	
	public void atualizar(Endereco endereco) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.update(endereco);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel alterar o endere�o. Erro: " + e.getMessage());
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
	
	public void excluir(Endereco endereco) {
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			sessao.delete(endereco);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("N�o foi possivel excluir o endereco. Erro: " + e.getMessage());
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
	
	public Endereco buscaEndereco(Integer codigo) {
		Endereco endereco = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(Endereco.class);
			filtro.add(Restrictions.eq("endereco", codigo));
			endereco = (Endereco) filtro.uniqueResult();
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
		
		return endereco;
	}
	
	@SuppressWarnings("unchecked")
	public List<Endereco> listar() {
		List<Endereco> enderecos = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().getCurrentSession();
			transacao = sessao.beginTransaction();
			Criteria filtro = sessao.createCriteria(Endereco.class);
			enderecos = filtro.list();
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
		
		return enderecos;
	}

}
