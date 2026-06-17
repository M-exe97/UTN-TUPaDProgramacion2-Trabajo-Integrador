package integrador.prog2.entities;

import integrador.prog2.enums.Estado;
import integrador.prog2.enums.FormaPago;
import integrador.prog2.interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido() {
        super();
        this.detalles = new ArrayList<>();
        this.fecha = LocalDate.now();
    }

    public Pedido(Long id, LocalDate fecha, Estado estado, double total, FormaPago formaPago, Usuario usuario) {
        super(id);
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }

    @Override
    public double calcularTotal() {
        double acumulado = 0.0;
        for (int i = 0; i < this.detalles.size(); i++) {
            acumulado += this.detalles.get(i).getSubtotal();
        }
        this.total = acumulado;
        return acumulado;
    }

    public void addDetallePedido(int cantidad, double precioUnitario, Producto producto) {
        long idDetalle = this.detalles.size() + 1;
        double subtotal = cantidad * precioUnitario;
        DetallePedido detalle = new DetallePedido(idDetalle, cantidad, subtotal, producto);
        this.detalles.add(detalle);
        this.calcularTotal();
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (int i = 0; i < this.detalles.size(); i++) {
            DetallePedido detalle = this.detalles.get(i);
            if (detalle.getProducto() != null && detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido encontrado = findeDetallePedidoByProducto(producto);
        if (encontrado != null) {
            this.detalles.remove(encontrado);
            this.calcularTotal();
        }
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    @Override
    public String toString() {
        String nombreUsuario;
        if (this.usuario != null) {
            nombreUsuario = this.usuario.getNombre() + " " + this.usuario.getApellido();
        } else {
            nombreUsuario = "Usuario Anonimo";
        }
        String estadoTexto;
        if (this.isEliminado()) {
            estadoTexto = "Eliminado";
        } else {
            estadoTexto = "Activo";
        }

        return "Pedido ID: " + this.getId() + "\nFecha: " + this.fecha + "\nCliente: " + nombreUsuario + "\nEstado: " + this.estado + "\nPago: " + this.formaPago + "\nTotal: $" + this.total + "\nItems: " + this.detalles.size() + "\nEstado Registro: " + estadoTexto;
    }
}
