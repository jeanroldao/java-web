package financeiro.bolsa.acao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import financeiro.usuario.Usuario;
import financeiro.util.BOException;
import financeiro.util.DAOFactory;
import financeiro.web.util.YahooFinanceUtil;

public class AcaoBO {
	private AcaoDAO acaoDAO;
	
	public AcaoBO() {
		acaoDAO = DAOFactory.criaAcaoDAO();
	}
	
	public void salvar(Acao acao) {
		acaoDAO.salvar(acao);
	}
	
	public void excluir(Acao acao) {
		acaoDAO.excluir(acao);
	}
	
	public Acao carregar(String codigo) {
		return acaoDAO.carregar(codigo);
	}
	
	public List<Acao> listar(Usuario usuario) {
		return acaoDAO.listar(usuario);
	}
	
	public List<AcaoVirtual> listarAcaoVirtual(Usuario usuario) throws BOException {
		try {
			List<AcaoVirtual> listaAcaoVirtual = new ArrayList<AcaoVirtual>();
			
			for (Acao acao : listar(usuario)) {
				AcaoVirtual acaoVirtual = new AcaoVirtual();
				acaoVirtual.setAcao(acao);
				String cotacao = retornaCotacao(YahooFinanceUtil.ULTIMO_PRECO_DIA_ACAO_INDICE, acao);
				if (cotacao != null) {
					float ultimoPreco = Float.parseFloat(cotacao);
					int quantidade = acao.getQuantidade();
					float total = ultimoPreco * quantidade;
					acaoVirtual.setUltimoPreco(ultimoPreco);
					acaoVirtual.setTotal(total);
					listaAcaoVirtual.add(acaoVirtual);
				}
			}
			return listaAcaoVirtual;
			
		} catch (BOException e) {
			throw new BOException("Não foi possivel listar ações. Erro: " + e.getMessage());
		}
	}
	
	public String retornaCotacao(int indiceCotacao, Acao acao) throws BOException {
		try {
			YahooFinanceUtil yahooFinanceUtil = new YahooFinanceUtil(acao);
			return yahooFinanceUtil.retornaCotacao(indiceCotacao, acao.getSigla());
		} catch (IOException e) {
			throw new BOException("Não foi possível recuperar cotação. Erro: " + e.getMessage());
		}
	}
	
	public String montaLinkAcao(Acao acao) {
		String link;
		if (acao != null) {
			if (acao.getOrigem() != null) {
				if (acao.getOrigem() == YahooFinanceUtil.ORIGEM_BOVESPA) {
					link = acao.getSigla() + YahooFinanceUtil.POSFIXO_ACAO_BOVESPA;
				} else {
					link = acao.getSigla();
				}
			} else {
				link = YahooFinanceUtil.INDICE_BOVESPA;
			}
		} else {
			link = YahooFinanceUtil.INDICE_BOVESPA;
		}
		return link;
	}
}
