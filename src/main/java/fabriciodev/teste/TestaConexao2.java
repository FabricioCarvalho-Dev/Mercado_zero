package fabriciodev.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fabriciodev.dao.UsuarioDao;
import fabriciodev.model.Usuario;

public class TestaConexao2 {

	public static void main(String args[]) throws Exception {
		
		                  //Inserir
						/*Usuario usuario = new Usuario();
						UsuarioDao usuarioDao = new UsuarioDao();
						usuario.setNome("feras");
						usuario.setEmail("testando@gmail.com");
						usuario.setEndereco("rua 35");
						usuario.setEndereco2("rua cçamba empenada");
						usuario.setPerfil("admin");
						usuario.setData_cadastro("20/05/94");
						
						usuarioDao.inserir(usuario);*/
		
						
						      //listar
						/*UsuarioDao usuarioDao = new UsuarioDao();
						List<Usuario> usuarios = usuarioDao.buscarTodos();
						for (Usuario usuario1 : usuarios) {
							System.out.println(usuario1.getNome());}*/
		
							//buscar por id
					/*	UsuarioDao usuarioDao = new UsuarioDao();
						Usuario usuario = usuarioDao.obterPorId(1);
						System.out.println(usuario.getNome());
						System.out.println(usuario.getCpf());*/
		
				
		                  //alterar
						/*UsuarioDao usuarioDao = new UsuarioDao();
						Usuario usuario = usuarioDao.obterPorId(1);
						usuario.setNome("Carlos");
						usuarioDao.inserir(usuario);
						
						System.out.println(usuario.getNome());*/
		
		
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = usuarioDao.obterPorId(1L);
		usuarioDao.excluir(usuario);
		
		
	}
		
}
		
		
		
		
		/* Usuario usuario = new Usuario();
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = usuarioDao.obterPorId(1);
		usuarioDao.excluir(usuario);
	
	   Usuario u = em.find(Usuario.class, 1);
	   em.close();
	   System.out.println("Nome do usuario: " + u.getNome());
				

		
		List<Usuario> usuarios = usuarioDao2.buscarTodos();
		for (Usuario usuario : usuarios) {
			System.out.println(usuario.getNome());
		}
		
		

		/iniciando jpa
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-hibernate");
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin(); //iniciando transação no bd
		em.persist(usuario); //persistindo dados
		em.getTransaction().commit();
		
		System.out.println("ID do usuario: " + usuario.getId());
		System.out.println("Nome do usuario: " + usuario.getNome());
		
		em.close(); //fechando transação
		usuario.setNome("catarina");
		usuario.setCpf(568877555);*/


