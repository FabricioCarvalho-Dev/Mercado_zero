package fabriciodev.bean;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
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
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class VendasDetalheBean implements Serializable {

    private Long vendaId;
    private int quantidade;
    private BigDecimal valorPago;
    private BigDecimal valorProduto;
    private Date dataInicio;


    private String txtProdutoPesquisar;
    private Vendas venda;
    private List<Vendas> vendas;
    ProdutoDAO produtoDAO = new ProdutoDAO();
    private VendasDAO vendasDAO;
    Produto produto = new Produto();
    BigDecimal valorTotal;

    @PostConstruct
    public void init() {
        vendasDAO = new VendasDAO();
        venda = new Vendas();
        vendas = vendasDAO.todosVendas();

    }
    public Produto buscaProdutoEprencheVenda(){
       produto = produtoDAO.buscarProdutoPeloId(vendaId);
        if (produto == null){
            return null;
        }
        return produto;
    }
    public void buscarVendasPorPeriodo() {
        LocalDateTime dataInicioLocalDateTime = dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        vendas = vendasDAO.vendasPorPeriodo(dataInicioLocalDateTime);
    }

    // Adicione seu método de geração de PDF
    public void gerarPdfVendas() {
        try {
            // Criação do stream de saída
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Cabeçalho
            document.add(new Paragraph("Relatório de Vendas")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());

            // Tabela com os dados
            float[] columnWidths = {1, 3, 3, 3}; // Ajuste conforme necessário
            Table table = new Table(columnWidths);
            table.addHeaderCell("COD. VENDA");
            table.addHeaderCell("Data da Venda");
            table.addHeaderCell("Quantidade dos Produtos");
            table.addHeaderCell("Valor Total");

            BigDecimal totalVendas = BigDecimal.ZERO;

            // Preenchendo a tabela com os dados das vendas
            for (Vendas venda : vendas) {
                int qtdVendas = vendas.size();
                table.addCell(String.valueOf(venda.getId()));
                table.addCell(venda.getDataHoraCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                table.addCell(String.valueOf(qtdVendas));
                table.addCell(String.format("%.2f", venda.getValorTotal()));

                totalVendas = totalVendas.add(venda.getValorTotal());
            }

            // Adicionando a tabela ao documento
            document.add(table);

            // Adicionando o total de vendas
            document.add(new Paragraph("Total de Vendas: R$ " + String.format("%.2f", totalVendas))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold());

            // Finalizando o documento
            document.close();

            // Envio do PDF como resposta HTTP
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(outStream.size());
            response.setHeader("Content-Disposition", "attachment; filename=\"RelatorioVendas.pdf\"");
            InputStream inputStream = new ByteArrayInputStream(outStream.toByteArray());
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            facesContext.responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }


    // Getters and setters
    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
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

    public List<Vendas> getVendas() {
        return vendas;
    }

    public void setVendas(List<Vendas> vendas) {
        this.vendas = vendas;
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

    public String getTxtProdutoPesquisar() {
        return txtProdutoPesquisar;
    }

    public void setTxtProdutoPesquisar(String txtProdutoPesquisar) {
        this.txtProdutoPesquisar = txtProdutoPesquisar;
    }
}
