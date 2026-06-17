package integrador.prog2.services;

import integrador.prog2.entities.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    private final List<Categoria> categorias;
    private long ultimoId;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.ultimoId = 0;
    }

    public Categoria crear(String nombre, String descripcion) {
        this.ultimoId++;
        Categoria nueva = new Categoria(this.ultimoId, nombre, descripcion);
        this.categorias.add(nueva);
        return nueva;
    }

    public List<Categoria> listar() {
        List<Categoria> activas = new ArrayList<>();
        for (int i = 0; i < this.categorias.size(); i++) {
            Categoria cat = this.categorias.get(i);
            if (!cat.isEliminado()) {
                activas.add(cat);
            }
        }
        return activas;
    }

    public Categoria buscarPorId(Long id) {
        for (int i = 0; i < this.categorias.size(); i++) {
            Categoria cat = this.categorias.get(i);
            if (cat.getId().equals(id)) {
                return cat;
            }
        }
        return null;
    }

    public boolean editar(Long id, String nuevoNombre, String nuevaDescripcion) {
        Categoria encontrada = buscarPorId(id);
        if (encontrada != null) {
            encontrada.setNombre(nuevoNombre);
            encontrada.setDescripcion(nuevaDescripcion);
            return true;
        }
        return false;
    }

    public boolean eliminar(Long id) {
        Categoria encontrada = buscarPorId(id);
        if (encontrada != null) {
            encontrada.setEliminado(true);
            return true;
        }
        return false;
    }
}
