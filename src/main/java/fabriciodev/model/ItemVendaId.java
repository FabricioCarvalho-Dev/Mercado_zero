package fabriciodev.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ItemVendaId implements Serializable {

    @Column(name = "venda_id")
    private Long vendaId;


    public ItemVendaId() {}

    public ItemVendaId(Long vendaId) {
        this.vendaId = vendaId;

    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVendaId that = (ItemVendaId) o;
        return Objects.equals(vendaId, that.vendaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendaId);
    }
}
