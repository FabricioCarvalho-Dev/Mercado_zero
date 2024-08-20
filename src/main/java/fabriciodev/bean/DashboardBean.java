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
    private int quantidade;
    private double precoTotal;
    private double porcentagem;
    private double precoPorUnidade;
    private double precoComPorcentagem;

    // Inicializa o bean
    public DashboardBean() {
        //usuarioDao = new UsuarioDao();
        precoPorUnidade = 0.0;
        precoComPorcentagem = 0.0;
        quantidade = 0;
        precoTotal = 0.0;
        porcentagem = 0.0;
    }

    // Método para calcular o preço por unidade e o preço com porcentagem
    public void calcularPrecos() {
        if (quantidade > 0) {
            precoPorUnidade = precoTotal / quantidade;
            precoComPorcentagem = precoPorUnidade * (1 + porcentagem / 100);
        } else {
            precoPorUnidade = 0.0;
            precoComPorcentagem = 0.0;
        }
        limpaCampos();
    }
    public void limpaCampos(){
        quantidade = 0;
        precoTotal = 0.0;
    }
    public String getPrecoComPorcentagemLabel() {
        return String.format("Preço com %.00f%% de Porcentagem R$: %.2f", porcentagem, precoComPorcentagem);
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
    // Getters e Setters
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public double getPrecoPorUnidade() {
        return precoPorUnidade;
    }

    public double getPrecoComPorcentagem() {
        return precoComPorcentagem;
    }
}
