package fabriciodev.bean;

import fabriciodev.dao.CategoriaDAO;
import fabriciodev.model.Categoria;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class CategoriaBean implements Serializable {

    private CategoriaDAO categoriaDao;
    private List<Categoria> categorias;
    private Categoria categoria = new Categoria();
   // private CategoriaTableModel categoriaTableModel;

    @PostConstruct
    public void init() {
        categoriaDao = new CategoriaDAO();
        //atualizarTabelaCategoria();
    }

    public void salvar() {
        try {
            String mensagem = categoriaDao.salvar(categoria);
            if (mensagem.startsWith("Categoria")) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, ""));
                //atualizarTabelaCategoria();
                categoria = null;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
        }
    }

    public void apagar() {
        try {
            if (categoria != null) {
                String mensagem = categoriaDao.deleteCategoriaPeloId(categoria.getId());
                if (mensagem.startsWith("Categoria")) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, ""));
                    atualizarTabelaCategoria();
                    categoria = null;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Deves selecionar uma categoria na tabela", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
        }
    }

    public void limpar() {
        categoria = null;
    }

    private void atualizarTabelaCategoria() {
        categorias = categoriaDao.todasCategorias();
        //categoriaTableModel = new Categoria(categorias);
    }

    // Getters and setters

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public Categoria getCategoria() {
        if (categoria == null) {
            categoria = new Categoria();
        }
        return categoria;
    }
    public String getNome() {
        return categoria.getNome();
    }

    public void setNome(String nome) {
        categoria.setNome(nome);
    }

    public String getDescricao() {
        return categoria.getDescricao();
    }

    public void setDescricao(String descricao) {
        categoria.setDescricao(descricao);
    }

}