package fabriciodev.model;

public enum Perfil {
    ADMIN("Administrador"),
    PADRAO("Padrão");

    private String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
