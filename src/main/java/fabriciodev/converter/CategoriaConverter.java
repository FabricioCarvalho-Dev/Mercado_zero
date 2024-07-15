package fabriciodev.converter;

import fabriciodev.dao.CategoriaDAO;
import fabriciodev.model.Categoria;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoriaConverter")
public class CategoriaConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long id = Long.valueOf(value);
            CategoriaDAO categoriaDao = new CategoriaDAO();
            return categoriaDao.buscarCategoriaPeloId(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro de conversão: ID inválido", e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof Categoria) || ((Categoria) value).getId() == null) {
            return null;
        }

        return String.valueOf(((Categoria) value).getId());
    }
}
