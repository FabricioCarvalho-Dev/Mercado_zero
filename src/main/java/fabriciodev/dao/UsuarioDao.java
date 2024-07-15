package fabriciodev.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fabriciodev.model.Categoria;
import fabriciodev.model.Usuario;

public class UsuarioDao {

	private EntityManagerFactory emf;
	private EntityManager em;

	public UsuarioDao() {
		emf = Persistence.createEntityManagerFactory("crud-hibernate");
		em = emf.createEntityManager();
	}

	public void inserir(Usuario u) {
		try {
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void excluir(Usuario u) {
		try {
			em.getTransaction().begin();
			u = em.merge(u);
			em.remove(u);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage());
		} finally {
			em.close();
		}
	}
	public List<Usuario> buscaTodosUsuarios() {
		Query query = em.createQuery("SELECT u FROM Usuario u");
		return query.getResultList();
	}

	public List<Usuario> buscarTodos() {
		try {
			em.getTransaction().begin();
			Query consulta = em.createQuery("select usuario from Usuario usuario");
			List<Usuario> usuarios = consulta.getResultList();
			em.getTransaction().commit();
			return usuarios;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao buscar todos os usuários: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public Usuario obterPorId(Long id) {
		try {
			em.getTransaction().begin();
			Usuario usuario = em.find(Usuario.class, id);
			em.getTransaction().commit();
			return usuario;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao obter usuário por ID: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public Usuario obterPorCpf(long cpf) {
		try {
			em.getTransaction().begin();
			Usuario usuario = em.find(Usuario.class, cpf);
			em.getTransaction().commit();
			return usuario;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao obter usuário por CPF: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void alterarUsuario(Usuario usuario) {
		try {
			em.getTransaction().begin();
			em.merge(usuario);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao alterar usuário: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public List<Usuario> porNomeSemelhante(String nome) {
		try {
			em.getTransaction().begin();
			List<Usuario> usuarios = em.createQuery("from Usuario where nome like :nome", Usuario.class)
					.setParameter("nome", "%" + nome + "%").getResultList();
			em.getTransaction().commit();
			return usuarios;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao buscar usuários por nome semelhante: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	public void buscUsuario(long cpf, String nome, String dataCadastro) {
	}

	// Outros métodos não implementados ainda, como buscUsuario, excluir(long id), etc.
}
