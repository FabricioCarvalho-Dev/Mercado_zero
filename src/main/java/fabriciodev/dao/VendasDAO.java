package fabriciodev.dao;

import fabriciodev.model.Produto;
import fabriciodev.model.Vendas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class VendasDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public VendasDAO() {
        emf = Persistence.createEntityManagerFactory("crud-hibernate"); // Nome da unidade de persistência no arquivo persistence.xml
        em = emf.createEntityManager();
        }

    public String salvar(Vendas venda) {
        try {
            em.getTransaction().begin();
            em.persist(venda);
            em.getTransaction().commit();
            return "Venda salvo com sucesso";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return String.format("Erro ao salvar a venda: %s", e.getMessage());
        }
    }
    public String salvar2(Vendas venda) {
        try {
            em.getTransaction().begin();
            if (venda.getId() == null || venda.getId() == 0L) {
                em.persist(venda);
            } else {
                em.merge(venda);
            }
            em.getTransaction().commit();
            return "Venda salvo com sucesso";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return String.format("Erro ao salvar a venda: %s", e.getMessage());
        }
    }

    public Vendas buscarPorId(Long id) {
        return em.find(Vendas.class, id);
    }

    // Outros métodos de acesso ao banco de dados
}
