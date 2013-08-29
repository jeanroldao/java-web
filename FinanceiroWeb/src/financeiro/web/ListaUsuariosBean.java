package financeiro.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class ListaUsuariosBean {
	
	List<Usuario> usuarios = new ArrayList<Usuario>();
	
	public void save(Usuario usuario) {
		usuarios.add(usuario);
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public static ListaUsuariosBean getInstance() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication()
				.evaluateExpressionGet(context, "#{listaUsuariosBean}", ListaUsuariosBean.class);
	}
}
