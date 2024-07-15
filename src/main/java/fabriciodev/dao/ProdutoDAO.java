package fabriciodev.dao;

import fabriciodev.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public ProdutoDAO() {
        emf = Persistence.createEntityManagerFactory("crud-hibernate"); // Nome da unidade de persistência no arquivo persistence.xml
        em = emf.createEntityManager();
    }

    public String salvar(Produto produto) {
        try {
            em.getTransaction().begin();
            if (produto.getId() == null || produto.getId() == 0L) {
                em.persist(produto);
            } else {
                em.merge(produto);
            }
            em.getTransaction().commit();
            return "Produto salvo com sucesso";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return String.format("Erro ao salvar o produto: %s", e.getMessage());
        }
    }

    public String actualizarQuantidade(Long idProduto, Integer quantidade, Long usuarioId) {
        try {
            em.getTransaction().begin();
            Produto produto = em.find(Produto.class, idProduto);
            produto.setQuantidade(quantidade);
            produto.getUsuario().setId(usuarioId);
            em.merge(produto);
            em.getTransaction().commit();
            return "Quantidade do produto atualizada com sucesso";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return String.format("Erro ao atualizar a quantidade do produto: %s", e.getMessage());
        }
    }

    public List<Produto> todosProdutos() {
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p", Produto.class);
        return query.getResultList();
    }

    public Produto buscarProdutoPeloId(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> buscarProdutosPeloCategoria(String categoria) {
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.categoria.nome = :categoria", Produto.class);
        query.setParameter("categoria", categoria);
        return query.getResultList();
    }

    public Produto buscarProdutoPeloNome(String nome) {
        TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p WHERE p.nome = :nome", Produto.class);
        query.setParameter("nome", nome);
        List<Produto> produtos = query.getResultList();
        return produtos.isEmpty() ? null : produtos.get(0);
    }

    public String deletaProdutoPeloId(Long id) {
        try {
            em.getTransaction().begin();
            Produto produto = em.find(Produto.class, id);
            if (produto != null) {
                em.remove(produto);
            }
            em.getTransaction().commit();
            return "Produto deletado com sucesso";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return String.format("Erro ao deletar o produto: %s", e.getMessage());
        }
    }
    public List<Produto> findByName(String nome) {
        List<Produto> produtos = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
                produtos.add(produto);
            }
        }
        return produtos;
    }

    public void fecharConexao() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}