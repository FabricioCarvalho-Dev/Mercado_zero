package fabriciodev.bean;

import fabriciodev.dao.CategoriaDAO;
import fabriciodev.dao.ProdutoDAO;
import fabriciodev.dao.UsuarioDao;
import fabriciodev.model.Categoria;
import fabriciodev.model.Produto;
import fabriciodev.model.Usuario;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.PrimeFaces;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

        private static final long serialVersionUID = 1L;

    private Produto produto;
    private List<Produto> produtos;
    private ProdutoDAO produtoDAO;
    private String txtProdutoPesquisar;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidade;
    private Categoria categoria;
    private Long categoriaId;
    private List<Categoria> categorias;
    CategoriaDAO categoriaDAO = new CategoriaDAO();
    Usuario usuario = new Usuario();
    UsuarioDao usuarioDAO = new UsuarioDao();


        @PostConstruct
        public void init() {

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            produtoDAO = new ProdutoDAO();
            produtos = produtoDAO.todosProdutos();
            produto = new Produto();
            //actualizarTabela(produtoDao.todosProdutos());
            categorias = obterCategorias();
        }
    public void salvar() {
        try {
            //Usuario usr = obterUsuarioPadrao();
            produto.setCategoria(categoriaDAO.buscarCategoriaPeloId(categoriaId));
            produto.setUsuario(obterUsuarioPadrao2().get(0));
            produto.setDataHoraCriacao(LocalDateTime.now());
            produtoDAO.salvar(produto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Produto adicionado com sucesso."));
            limparCampos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao adicionar produto: " + e.getMessage()));
        }
    }

    public void adicionar() {
        produto = new Produto();
        abrirDialogo2();
    }

    public void editar() {
        if (produto != null) {
            openDialog();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Selecione um produto para editar", null));
        }
    }
    public void apagar() {
        if (produto != null) {
            produtoDAO.deletaProdutoPeloId(produto.getId());
            produtos.remove(produto);
            produto = new Produto();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto removido com sucesso", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Selecione um produto para apagar", null));
        }
    }
    public void pesquisarProdutos() {
        produtos = produtoDAO.findByName(txtProdutoPesquisar);
    }
    public void cancelar() {
        produto = new Produto();
        limparCampos();
        closeDialog();
    }
    private void limparCampos() {
        produto = new Produto();
        this.nome = null;
        this.descricao = null;
        this.preco = 0.0;
        this.quantidade = 0;
        this.categoria = null;
    }
    private List<Categoria> obterCategorias() {
        // Implemente a lógica para obter as categorias do banco de dados
        // Exemplo simplificado:
        return categoriaDAO.todasCategorias(); // Método fictício para listar todas as categorias
    }

    private void openDialog() {
        PrimeFaces.current().executeScript("PF('dialogProduto').show();");
    }

    private void closeDialog() {
        PrimeFaces.current().executeScript("PF('dialogProduto').hide();");
    }
    public void abrirDialogo2() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);

        PrimeFaces.current().dialog().openDynamic("modalAdcionarProduto", opcoes, null);
    }
    public void abrirModalCategoria() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);
        PrimeFaces.current().dialog().openDynamic("categoria", opcoes, null);
    }

    public Usuario obterUsuarioPadrao() {
        Usuario userPadrao = new Usuario();
        List<Usuario> usuarios = usuarioDAO.buscarTodos();
        if (!usuarios.isEmpty()) {
            userPadrao = usuarios.get(0);
        }
        return userPadrao;
    }
    private List<Usuario> obterUsuarioPadrao2() {
        return usuarioDAO.buscaTodosUsuarios();
    }



        public void mostrarTelaProduto() {
           // this.dashboard.getDialogProduto().pack();
            //this.dashboard.getDialogProduto().setLocationRelativeTo(dashboard);
            //this.dashboard.getDialogProduto().setVisible(true);
        }

        public void mostrarTelaCategoria() {
            //this.dashboard.getDialogCategoria().pack();
            //this.dashboard.getDialogCategoria().setLocationRelativeTo(dashboard);
           // this.dashboard.getDialogCategoria().setVisible(true);
            ocultaTelaProduto();
        }

        public void ocultaTelaProduto() {
           // this.dashboard.getDialogProduto().setVisible(false);
        }


     // Getters e setters se necessário
     public Produto getProduto() {
         return produto;
     }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public String getTxtProdutoPesquisar() {
        return txtProdutoPesquisar;
    }

    public void setTxtProdutoPesquisar(String txtProdutoPesquisar) {
        this.txtProdutoPesquisar = txtProdutoPesquisar;
    }
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}


