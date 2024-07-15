package fabriciodev.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario")

public class Usuario  {
	
	public Usuario() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cpf")
	private long cpf;
	
	@Column(name="email")
	private String email;
	
	@Column(name="endereco")
	private String endereco;
	
	@Column(name="endereco2")
	private String endereco2;
	
	@Column(name="data_cadastro")
	private String data_cadastro;
	
	@Column(name="perfil")
	private Perfil perfil;
	@Column(name="dthr_criacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dthrCriacao;

	@Column(name="estado")
	private String estado;

	@Column(name="ultimo_login")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoLogin;

	@Column(name="senha")
	private String senha;

	@Column(name="usuario")
	private String usuario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(String data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public String getEndereco2() {
		return endereco2;
	}

	public void setEndereco2(String enderec02) {
		this.endereco2 = enderec02;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Date getDthrCriacao() {
		return dthrCriacao;
	}

	public void setDthrCriacao(Date dthrCriacao) {
		this.dthrCriacao = dthrCriacao;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
