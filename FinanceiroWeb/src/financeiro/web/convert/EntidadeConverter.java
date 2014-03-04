package financeiro.web.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import financeiro.entidade.Entidade;
import financeiro.entidade.EntidadeBO;

@FacesConverter(forClass = Entidade.class)
public class EntidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return new EntidadeBO().buscarPorNome(value);
			} catch (Exception e) {
				throw new ConverterException("Não foi possivel encontrar a entidade de código " + value + ". " + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((Entidade) value).getNome();
		}
		return null;
	}

}
