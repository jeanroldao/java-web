package financeiro.web.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import financeiro.categoria.Categoria;
import financeiro.categoria.CategoriaBO;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				CategoriaBO categoriaBO = new CategoriaBO();
				return categoriaBO.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("N�o foi possivel encontrar a categoria de c�digo " + value + ". " + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Categoria categoria = (Categoria) value;
			return categoria.getCodigo().toString();
		}
		return null;
	}
	
}
