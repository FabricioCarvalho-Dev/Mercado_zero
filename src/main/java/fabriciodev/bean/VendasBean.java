package fabriciodev.bean;

import fabriciodev.dao.ProdutoDAO;
import fabriciodev.dao.VendasDAO;
import fabriciodev.model.ItemVenda;
import fabriciodev.model.Produto;
import fabriciodev.model.Usuario;

import fabriciodev.model.Vendas;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class VendasBean implements Serializable {

    private Long produtoId;
    private int quantidade;
    private BigDecimal valorPago;
    private BigDecimal valorProduto;

    private Vendas venda;
    private List<Produto> cesto;
    List<ItemVenda> cestoItem = new ArrayList<>();
    private ItemVenda itens;
    ProdutoDAO produtoDAO = new ProdutoDAO();
    private VendasDAO vendasDAO;
    Produto produto = new Produto();
    BigDecimal valorTotal;
    ItemVenda itemVenda = new ItemVenda();
    BigDecimal vlrPagoSum = BigDecimal.valueOf(0.0);
    BigDecimal total = BigDecimal.ZERO;

    @PostConstruct
    public void init() {
        venda = new Vendas();
        cesto = new ArrayList<>();
        vendasDAO = new VendasDAO();
    }
    public Produto buscaProdutoEprencheVenda(){
       produto = produtoDAO.buscarProdutoPeloId(produtoId);
        if (produto == null){
            return null;
        }
        return produto;
    }

    public void adicionarProduto() {
        // Lógica para buscar o produto pelo ID
        produto = buscaProdutoEprencheVenda();
        if (produto != null) {
            try {
                if (quantidade == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "a Quantidade não pode ser 0."));
                    produto = null;
                }
                assert produto != null;
                produto.setQuantidade(quantidade);
                cesto.add(produto);
                itemVenda.setProduto(produto);
                itemVenda.setQuantidade(quantidade);
                vlrPagoSum = vlrPagoSum.add(valorPago);
                calcularTotais();
                if (valorTotal.compareTo(vlrPagoSum) > 0){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "O valor total da venda excende o valor Pago."));
                }
                itemVenda.setTotal(valorTotal);
                itemVenda.setVenda(venda);
                cestoItem.add(itemVenda);
                limpaCamposAddProd();
            } catch (Exception e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Produto não adcionado."));
                itemVenda = null;
                cesto.remove(produto);
            }
        }
    }
    public void removerProduto() {
        if (produto != null) {
            itemVenda.setTotal(valorTotal);
            itemVenda.setVenda(venda);
            cesto.remove(produto);
            calcularTotais();
            produto = null; // Limpa a seleção após a remoção
        }
    }
    public void limpaCamposAddProd(){
        produto = new Produto();
        produtoId = null;
        quantidade = 0;
        valorPago = BigDecimal.valueOf(0.0);
    }

    public void finalizarVenda() {
        try {
            Usuario usuarioPadrao= new Usuario();
            usuarioPadrao.setId(1L);
            usuarioPadrao.setNome("CLIENTE PADRAO");
            venda.setDataHoraCriacao(LocalDateTime.now());
            venda.setCliente(usuarioPadrao);
            venda.setItens(cestoItem);
            venda.setValorPago(vlrPagoSum);
            vendasDAO.salvar(venda);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Venda realizada com sucesso."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao realizar Venda: " + e.getMessage()));
        }
    }

    private void calcularTotais() {
        total = (total != null && total.compareTo(BigDecimal.ZERO) != 0) ? BigDecimal.ZERO : total;
        for (Produto produto : cesto) {
            total = total.add(produto.getPreco().multiply(new BigDecimal(produto.getQuantidade())));
        }
        venda.setValorTotal(total);
        venda.setTroco(vlrPagoSum.subtract(total));
        valorTotal = total;
    }


    private Produto buscarProdutoPorId(Long id) {
        // Implementação para buscar o produto pelo ID
        return new Produto(); // Placeholder, substitua com a lógica real
    }

    // Getters and setters
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public BigDecimal getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Vendas getVenda() {
        return venda;
    }

    public void setVenda(Vendas venda) {
        this.venda = venda;
    }

    public List<Produto> getCesto() {
        return cesto;
    }

    public void setCesto(List<Produto> cesto) {
        this.cesto = cesto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getVlrPagoSum() {
        return vlrPagoSum;
    }

    public void setVlrPagoSum(BigDecimal vlrPagoSum) {
        this.vlrPagoSum = vlrPagoSum;
    }
}
