package fabriciodev.bean;

import org.primefaces.PrimeFaces;

import java.util.HashMap;
import java.util.Map;

public class SelecaoProdutoBean {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void selecionar() {
        // Lógica para selecionar o cliente
        System.out.println("Cliente selecionado: " + nome);
    }
    public void abrirModalCategoria() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 470);

        PrimeFaces.current().dialog().openDynamic("categoria", opcoes, null);
    }
}
