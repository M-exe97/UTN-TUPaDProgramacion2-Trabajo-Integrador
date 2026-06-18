package integrador.prog2.services;

import integrador.prog2.entities.Categoria;
import integrador.prog2.entities.Producto;
import integrador.prog2.exception.CategoriaEnUsoException;

import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    private final List<Categoria> categorias;
    private long ultimoId;
    private ProductoService productoService;

    public CategoriaService(ProductoService productoService) {
        this.categorias = new ArrayList<>();
        this.ultimoId = 0;
        this.productoService = productoService;
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

    public void eliminar(Long id) throws CategoriaEnUsoException{
        Categoria encontrada = buscarPorId(id);
        if (encontrada != null && !encontrada.isEliminado()) {
            List<Producto> productosExistentes = productoService.listar();
            for (int i = 0; i < productosExistentes.size(); i++) {
                Producto producto = productosExistentes.get(i);
                if (!producto.isEliminado() && producto.getCategoria().getId().equals(id)) {
                    throw new CategoriaEnUsoException("No se puede eliminar la categoria " + encontrada.getNombre() + " porque tiene productos asociados activos");
                }
            }
            encontrada.setEliminado(true);
        }
    }
}
