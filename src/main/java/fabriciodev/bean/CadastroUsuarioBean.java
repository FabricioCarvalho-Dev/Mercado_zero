package fabriciodev.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import fabriciodev.model.Perfil;
import org.omnifaces.util.Messages;
import org.primefaces.event.RowEditEvent;

import fabriciodev.dao.UsuarioDao;
import fabriciodev.model.Usuario;

import java.util.Arrays;
import java.util.List;

@ManagedBean
@ViewScoped
public class CadastroUsuarioBean {

	private Usuario usuario = new Usuario();
	private Perfil perfilSelecionado;
	private UsuarioDao usuarioDao = new UsuarioDao();
	private Usuario usuarioSelecionado;
	

	public void salvar() {

		try {
			usuario.setData_cadastro(new String());

			this.usuarioDao.inserir(usuario);

			Messages.addGlobalInfo("Usuario salvo com sucesso!");

			this.limpar();

		} catch (Exception e) {

			Messages.addGlobalError("Impossivel cadastrar o usuario " + e.getMessage());
			e.printStackTrace();
			this.limpar();
		}
	}

	public void onRowEdit(RowEditEvent event) {
	
		usuario.setNome(String.valueOf(event.getObject()));
		usuario.setEmail(String.valueOf(((Usuario)event.getObject()).getEmail()));
		usuario.setEndereco(String.valueOf(((Usuario)event.getObject()).getEndereco()));
		usuario.setEndereco2(String.valueOf(((Usuario)event.getObject()).getEndereco2()));
		usuario.setPerfil(Perfil.valueOf(String.valueOf(((Usuario)event.getObject()).getPerfil())));
		usuario.setData_cadastro(String.valueOf(((Usuario)event.getObject()).getData_cadastro()));

		try {

			//usuarioDao.usuarioSelecionado(Long.valueOf(((Usuario) event.getObject()).getId()));			
			usuarioDao.obterPorId(Long.valueOf(((Usuario) event.getObject()).getId()));
			usuarioDao.inserir(usuario);
			Messages.addGlobalInfo("Alteração de Usuario Realizada!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onRowCancel(RowEditEvent event) {
		Messages.addGlobalInfo("Alteração Cancelada!");

	}

	public Usuario getUsuario() {

		if (this.usuario == null) {
			this.usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private void limpar() {

		this.usuario = new Usuario();
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public Perfil getPerfilSelecionado() {
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}

	public Perfil[] getPerfis() {
		return Perfil.values();
	}
}
