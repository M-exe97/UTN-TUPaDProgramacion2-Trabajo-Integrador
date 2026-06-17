package integrador.prog2.services;

import integrador.prog2.entities.DetallePedido;
import integrador.prog2.entities.Pedido;
import integrador.prog2.entities.Producto;
import integrador.prog2.entities.Usuario;
import integrador.prog2.enums.Estado;
import integrador.prog2.enums.FormaPago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private final List<Pedido> pedidos;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private long ultimoIdPedido;
    private long ultimoIdDetalle;

    public PedidoService(ProductoService productoService, UsuarioService usuarioService) {
        this.pedidos = new ArrayList<>();
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.ultimoIdPedido = 0;
        this.ultimoIdDetalle = 0;
    }

    public Pedido crearPedido(Long idUsuario, List<Long> idProductos, List<Integer> cantidades, FormaPago formaPago) {
        Usuario usuario = this.usuarioService.buscarPorId(idUsuario);
        if (usuario == null || usuario.isEliminado()) {
            return null;
        }

        this.ultimoIdPedido++;
        Pedido nuevoPedido = new Pedido(this.ultimoIdPedido, LocalDate.now(), Estado.PENDIENTE, 0.0, formaPago, usuario);
        double totalAcumulado = 0.0;

        for (int i = 0; i < idProductos.size(); i++) {
            Long idProducto = idProductos.get(i);
            int cantidad = cantidades.get(i);

            Producto producto = productoService.buscarPorId(idProducto);
            if (producto != null && !producto.isEliminado()) {
                this.ultimoIdDetalle++;
                double subtotal = cantidad * producto.getPrecio();
                DetallePedido detalle = new DetallePedido(this.ultimoIdDetalle, cantidad, subtotal, producto);
                nuevoPedido.getDetalles().add(detalle);
                totalAcumulado += subtotal;
            }
        }

        nuevoPedido.setTotal(totalAcumulado);
        this.pedidos.add(nuevoPedido);
        return nuevoPedido;
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
