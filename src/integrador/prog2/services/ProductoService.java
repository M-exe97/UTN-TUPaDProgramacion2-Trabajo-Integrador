package integrador.prog2.services;

import integrador.prog2.entities.Categoria;
import integrador.prog2.entities.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final List<Producto> productos;
    private long ultimoId;

    public ProductoService() {
        this.productos = new ArrayList<>();
        this.ultimoId = 0;
    }

    public Producto crear(String nombre, String descripcion, double precio, int stock, String imagen, boolean disponible, Categoria categoria) {
        this.ultimoId++;
        Producto nuevo = new Producto(this.ultimoId, nombre, descripcion, precio, stock, imagen, disponible, categoria);
        this.productos.add(nuevo);
        return nuevo;
    }

    public List<Producto> listar() {
        List<Producto> activos = new ArrayList<>();
        for (int i = 0; i < this.productos.size(); i++) {
            Producto prod = this.productos.get(i);
            if (!prod.isEliminado()) {
                activos.add(prod);
            }
        }
        return activos;
    }

    public Producto buscarPorId(Long id) {
        for (int i = 0; i < this.productos.size(); i++) {
            Producto prod = this.productos.get(i);
            if (prod.getId().equals(id)) {
                return prod;
            }
        }
        return null;
    }

    public void editar (Long id, String nombre, String descripcion, double precio, int stock, String imagen, boolean disponible, Categoria categoria) {
        Producto encontrado = buscarPorId(id);
        if (encontrado != null && !encontrado.isEliminado()) {
            encontrado.setNombre(nombre);
            encontrado.setDescripcion(descripcion);
            encontrado.setPrecio(precio);
            encontrado.setImagen(imagen);
            encontrado.setCategoria(categoria);
            encontrado.setStock(stock);
            if (stock > 0) {
                encontrado.setDisponible(true);
            } else {
                encontrado.setDisponible(false);
            }
        }
    }

    public boolean eliminar(Long id) {
        Producto encontrado = buscarPorId(id);
        if (encontrado != null) {
            encontrado.setEliminado(true);
            return true;
        }
        return false;
    }

}
