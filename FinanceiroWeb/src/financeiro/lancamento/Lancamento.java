package financeiro.lancamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import financeiro.categoria.Categoria;
import financeiro.cheque.Cheque;
import financeiro.conta.Conta;
import financeiro.entidade.Entidade;
import financeiro.usuario.Usuario;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "codigo")
	private Integer lancamento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario", nullable = false)
	@ForeignKey(name = "fk_lancamento_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "conta", nullable = false)
	@ForeignKey(name = "fk_lancamento_conta")
	private Conta conta;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "categoria", nullable = false)
	@ForeignKey(name = "fk_lancamento_categoria")
	private Categoria categoria;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private String descricao;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal valor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "entidade", nullable = true)
	@ForeignKey(name = "fk_lancamento_entidade")
	private Entidade entidade;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "lancamento")
	private Cheque cheque;
	
	private Double avaliacao;

	public Integer getLancamento() {
		return lancamento;
	}

	public void setLancamento(Integer lancamento) {
		this.lancamento = lancamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public Double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Double avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avaliacao == null) ? 0 : avaliacao.hashCode());
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((cheque == null) ? 0 : cheque.hashCode());
		result = prime * result + ((conta == null) ? 0 : conta.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((entidade == null) ? 0 : entidade.hashCode());
		result = prime * result
				+ ((lancamento == null) ? 0 : lancamento.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (avaliacao == null) {
			if (other.avaliacao != null)
				return false;
		} else if (!avaliacao.equals(other.avaliacao))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (cheque == null) {
			if (other.cheque != null)
				return false;
		} else if (!cheque.equals(other.cheque))
			return false;
		if (conta == null) {
			if (other.conta != null)
				return false;
		} else if (!conta.equals(other.conta))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (entidade == null) {
			if (other.entidade != null)
				return false;
		} else if (!entidade.equals(other.entidade))
			return false;
		if (lancamento == null) {
			if (other.lancamento != null)
				return false;
		} else if (!lancamento.equals(other.lancamento))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}
