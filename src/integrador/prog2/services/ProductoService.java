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
            if (prod.isEliminado()) {
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

    public boolean editar(Long id, String nuevoNombre, String nuevaDescripcion, double nuevoPrecio, int nuevoStock, String nuevaImagen, boolean nuevaDisponibilidad, Categoria nuevaCategoria) {
        Producto encontrado = buscarPorId(id);
        if (encontrado != null) {
            encontrado.setNombre(nuevoNombre);
            encontrado.setDescripcion(nuevaDescripcion);
            encontrado.setPrecio(nuevoPrecio);
            encontrado.setStock(nuevoStock);
            encontrado.setImagen(nuevaImagen);
            encontrado.setDisponible(nuevaDisponibilidad);
            encontrado.setCategoria(nuevaCategoria);
            return true;
        }
        return false;
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
