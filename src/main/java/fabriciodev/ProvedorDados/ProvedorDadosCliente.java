package fabriciodev.ProvedorDados;

public class ProvedorDadosCliente {
    private Long id;
    private String nome;

    public ProvedorDadosCliente() {
    }
    public ProvedorDadosCliente(Long id, String nome) {
        this.id = 1L;
        this.nome = "PADRAO CLIENTE";
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
