package fabriciodev.dao;
import fabriciodev.model.Categoria;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class CategoriaDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public CategoriaDAO() {
        emf = Persistence.createEntityManagerFactory("crud-hibernate"); // Nome da unidade de persistência no arquivo persistence.xml
        em = emf.createEntityManager();
    }

    public String salvar(Categoria categoria) {
        try {
            em.getTransaction().begin();

            if (categoria.getId() == null || categoria.getId() == 0) {
                em.persist(categoria);
            } else {
                em.merge(categoria);
            }

            em.getTransaction().commit();
            return "Categoria salva com sucesso.";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "Erro ao salvar categoria: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    public Categoria buscarCategoriaPeloId(Long id) {
        return em.find(Categoria.class, id);
    }

    public Categoria buscarCategoriaPeloNome(String nome) {
        Query query = em.createQuery("SELECT c FROM Categoria c WHERE c.nome = :nome");
        query.setParameter("nome", nome);
        return (Categoria) query.getResultList().stream().findFirst().orElse(null);
    }

    public List<Categoria> todasCategorias() {
        Query query = em.createQuery("SELECT c FROM Categoria c");
        return query.getResultList();
    }

    public String deleteCategoriaPeloId(Long id) {
        Categoria categoria = em.find(Categoria.class, id);
        if (categoria != null) {
            try {
                em.getTransaction().begin();
                em.remove(categoria);
                em.getTransaction().commit();
                return "Categoria deletada com sucesso.";
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                return "Erro ao deletar categoria: " + e.getMessage();
            } finally {
                em.close();
            }
        } else {
            return "Categoria não encontrada.";
        }
    }
}
