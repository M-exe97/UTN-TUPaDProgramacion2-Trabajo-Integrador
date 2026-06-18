package integrador.prog2.services;

import integrador.prog2.entities.Pedido;
import integrador.prog2.entities.Producto;
import integrador.prog2.entities.Usuario;
import integrador.prog2.enums.Estado;
import integrador.prog2.enums.FormaPago;
import integrador.prog2.exception.StockInsuficienteException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private final List<Pedido> pedidos;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private long ultimoIdPedido;

    public PedidoService(ProductoService productoService, UsuarioService usuarioService) {
        this.pedidos = new ArrayList<>();
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.ultimoIdPedido = 0;
    }

    public Pedido crearPedido(Long idUsuario, List<Long> idProductos, List<Integer> cantidades, FormaPago formaPago) throws StockInsuficienteException{
        Usuario usuario = this.usuarioService.buscarPorId(idUsuario);
        if (usuario == null || usuario.isEliminado()) {
            return null;
        }
        this.ultimoIdPedido++;
        Pedido nuevoPedido = new Pedido(this.ultimoIdPedido, LocalDate.now(), Estado.PENDIENTE, 0.0, formaPago, usuario);
        for (int i = 0; i < idProductos.size(); i++) {
            Long idProducto = idProductos.get(i);
            int cantidad = cantidades.get(i);
            Producto producto = this.productoService.buscarPorId(idProducto);
            if (producto != null && !producto.isEliminado() && producto.isDisponible()) {
                if (producto.getStock() >= cantidad) {
                    int nuevoStock = producto.getStock() - cantidad;
                    boolean disponible = nuevoStock > 0;
                    this.productoService.editar(producto.getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), nuevoStock, producto.getImagen(), disponible, producto.getCategoria());
                    nuevoPedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
                } else {
                    this.ultimoIdPedido--;
                    throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
                }
            }
        }
        if (!nuevoPedido.getDetalles().isEmpty()) {
            this.pedidos.add(nuevoPedido);
            return nuevoPedido;
        }
        this.ultimoIdPedido--;
        return null;

    }

    public List<Pedido> listarActivos() {
        List<Pedido> activos = new ArrayList<>();
        for (int i = 0; i < this.pedidos.size(); i++) {
            Pedido pedido = this.pedidos.get(i);
            if (!pedido.isEliminado()) {
                activos.add(pedido);
            }
        }
        return activos;
    }

    public Pedido buscarPorId(Long id) {
        for (int i = 0; i < this.pedidos.size(); i++) {
            Pedido pedido = this.pedidos.get(i);
            if (pedido.getId().equals(id)) {
                return pedido;
            }
        }
        return null;
    }

    public boolean actualizarEstadoYPago(Long idPedido, Estado nuevoEstado, FormaPago nuevaForma) {
        Pedido encontrado = buscarPorId(idPedido);
        if (encontrado != null && !encontrado.isEliminado()) {
            encontrado.setEstado(nuevoEstado);
            encontrado.setFormaPago(nuevaForma);
            return true;
        }
        return false;
    }

    public boolean eliminar(Long idPedido) {
        Pedido encontrado = buscarPorId(idPedido);
        if (encontrado != null) {
            encontrado.setEliminado(true);
            return true;
        }
        return false;
    }
}
