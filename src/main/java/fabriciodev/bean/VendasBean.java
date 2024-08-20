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
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    ProdutoDAO produtoDAO = new ProdutoDAO();
    private VendasDAO vendasDAO;
    Produto produto = new Produto();
    BigDecimal valorTotal;
    ItemVenda itemVenda = new ItemVenda();
    BigDecimal vlrPagoSum = BigDecimal.valueOf(0.0);
    BigDecimal total = BigDecimal.ZERO;
    private List<Map.Entry<Long, Integer>> qtdEIdProdt;

    @PostConstruct
    public void init() {
        venda = new Vendas();
        cesto = new ArrayList<>();
        vendasDAO = new VendasDAO();
        qtdEIdProdt = new ArrayList<>();
    }
    public Produto buscaProdutoEprencheVenda(){
       produto = produtoDAO.buscarProdutoPeloId(produtoId);
        if (produto == null){
            return null;
        }
        return produto;
    }

    public void adicionarProduto() {
        produto = buscaProdutoEprencheVenda();
        if (produto != null) {
            qtdEIdProdt.add(new AbstractMap.SimpleEntry<>(produto.getId(), produto.getQuantidade()));
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
    public void limpaCamposVendaFinalzd(){
        produto = new Produto();
        venda = new Vendas();
        cesto.clear();
        total = BigDecimal.ZERO;
        valorTotal = null;
        vlrPagoSum = null;

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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Venda realizada com sucesso."));
            for (Map.Entry<Long, Integer> parQtdId : qtdEIdProdt){
                Long idProcurado = parQtdId.getKey();
                Integer qtd = parQtdId.getValue();
                for (Produto itemPdt : cesto){
                    if (itemPdt.getId().equals(idProcurado)) {
                        int qtdAtualizada = qtd - itemPdt.getQuantidade();
                        produtoDAO.actualizarQuantidade(itemPdt.getId(), qtdAtualizada, 1L);
                        break;
                    }
                }
            }
            limpaCamposVendaFinalzd();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao realizar Venda: " + e.getMessage()));
        }

    }

    private void calcularTotais() {
        total = (total != null && total.compareTo(BigDecimal.ZERO) != 0) ? BigDecimal.ZERO : total;
        for (Produto produto : cesto) {
            assert total != null;
            total = total.add(produto.getPreco().multiply(new BigDecimal(produto.getQuantidade())));
        }
        venda.setValorTotal(total);
        venda.setTroco(vlrPagoSum.subtract(total));
        valorTotal = total;
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

    public ItemVenda getItemVenda() {
        return itemVenda;
    }

    public void setItemVenda(ItemVenda itemVenda) {
        this.itemVenda = itemVenda;
    }

    public List<ItemVenda> getCestoItem() {
        return cestoItem;
    }

    public void setCestoItem(List<ItemVenda> cestoItem) {
        this.cestoItem = cestoItem;
    }
}
