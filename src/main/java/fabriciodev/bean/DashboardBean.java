package fabriciodev.bean;

import fabriciodev.dao.UsuarioDao;
import fabriciodev.model.Usuario;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class DashboardBean implements Serializable {

    private UsuarioDao usuarioDao;
    private Usuario usuario;
    private static final String TELA_PRODUTOS = "produtos";
    
    // Inicializa o bean
    public DashboardBean() {
        usuarioDao = new UsuarioDao();
        // Inicialização adicional se necessário
    }
    
    // Métodos de ação
    public String goToHome() {
        return "home?faces-redirect=true";
    }

    public String goToClientes() {
        return "clientes?faces-redirect=true";
    }

    public String goToProdutos() {
        return TELA_PRODUTOS;
    }

    public String goToVendas() {
        return "vendas?faces-redirect=true";
    }

    public String goToUsuarios() {
        return "usuarios?faces-redirect=true";
    }

    public String sair() {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout", "Você saiu com sucesso!"));
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
    
    // Getters e Setters para propriedades necessárias

}
