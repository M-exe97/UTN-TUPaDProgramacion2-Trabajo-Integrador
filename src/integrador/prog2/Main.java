package integrador.prog2;

import integrador.prog2.enums.Estado;
import integrador.prog2.enums.Rol;
import integrador.prog2.enums.FormaPago;

import integrador.prog2.services.CategoriaService;
import integrador.prog2.services.PedidoService;
import integrador.prog2.services.ProductoService;
import integrador.prog2.services.UsuarioService;

import integrador.prog2.entities.Categoria;
import integrador.prog2.entities.Producto;
import integrador.prog2.entities.Usuario;
import integrador.prog2.entities.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author .exe
 */
public class Main {

    public static void main(String[] args) {
        ProductoService productoService = new ProductoService();
        CategoriaService categoriaService = new CategoriaService(productoService);
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService(productoService, usuarioService);

        Scanner scanner = new Scanner(System.in);
        String opcion = "";

        while (!opcion.equals("0")) {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    menuCategorias(scanner, categoriaService);
                    break;
                case "2":
                    menuProductos(scanner, productoService, categoriaService);
                    break;
                case "3":
                    menuUsuarios(scanner, usuarioService);
                    break;
                case "4":
                    menuPedidos(scanner, pedidoService, productoService, usuarioService);
                    break;
                case "0":
                    System.out.println("Saliendo... Adios!");
                    break;
                default:
                    System.out.println("Opcion invalida. Intenta nuevamente");
                    break;
            }
        }
        scanner.close();
    }

    private static void menuCategorias(Scanner scanner, CategoriaService categoriaService) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println("\n=== CATEGORIAS ===");
            System.out.println("1. Listar categorias");
            System.out.println("2. Crear nueva categoria");
            System.out.println("3. Editar categoria");
            System.out.println("4. Eliminar categoria");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\n=== LISTADO DE CATEGORIAS ===");
                    List<Categoria> lista = categoriaService.listar();
                    if (lista.isEmpty()) {
                        System.out.println("No hay categorias disponibles");
                    } else {
                        for (int i = 0; i < lista.size(); i++) {
                            System.out.println(lista.get(i).toString());
                        }
                    }
                    break;
                case "2":
                    System.out.println("\n=== NUEVA CATEGORIA ===");
                    System.out.println("Ingresa el nombre de la categoria: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingresa la descripcion de la categoria: ");
                    String descripcion = scanner.nextLine();

                    if (nombre.isEmpty() || descripcion.isEmpty()) {
                        System.out.println("Error. El nombre y la descripcion no pueden estar vacios");
                    } else {
                        Categoria nueva = categoriaService.crear(nombre, descripcion);
                        System.out.println("Categoria creada correctamente. ID: " + nueva.getId());
                    }
                    break;
                case "3":
                    System.out.println("\n=== EDITAR CATEGORIA ===");
                    Long idEditar = leerLong(scanner, "Ingresa el ID de la categoria a editar: ");
                    Categoria categoriaEditar = categoriaService.buscarPorId(idEditar);

                    if (categoriaEditar == null || categoriaEditar.isEliminado()) {
                        System.out.println("Error: El ID no existe o la categoria ya fue eliminada");
                    } else {
                        System.out.println("Datos actuales: " + categoriaEditar.toString());
                        System.out.println("Nuevo nombre (o deja en blanco para no cambiar): ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.println("Nueva descripcion (o deja en blanco para no cambiar): ");
                        String nuevaDescripcion = scanner.nextLine();

                        if (nuevoNombre.isEmpty()) {
                            nuevoNombre = categoriaEditar.getNombre();
                        }
                        if (nuevaDescripcion.isEmpty()) {
                            nuevaDescripcion = categoriaEditar.getDescripcion();
                        }

                        categoriaService.editar(idEditar, nuevoNombre, nuevaDescripcion);
                        System.out.println("Categoria editada correctamente");
                    }
                    break;
                case "4":
                    System.out.println("\n=== ELIMINAR CATEGORIA ===");
                    Long idEliminar = leerLong(scanner, "Ingresa el ID de la categoria a eliminar: ");
                    Categoria categoriaEliminar = categoriaService.buscarPorId(idEliminar);

                    if (categoriaEliminar == null || categoriaEliminar.isEliminado()) {
                        System.out.println("Error: El ID no existe o la categoria ya fue eliminada");
                    } else {
                        System.out.println("Estas seguro de eliminar la categoria: " + categoriaEliminar.getNombre() + " (S/N)");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("S")) {
                            categoriaService.eliminar(idEliminar);
                            System.out.println("Categoria eliminada correctamente");
                        } else {
                            System.out.println("Operacion cancelada");
                        }
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion invalida. Intenta nuevamente");
            }
        }
    }

    private static void menuUsuarios(Scanner scanner, UsuarioService usuarioService) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println("\n=== USUARIOS ===");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Crear nuevo usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\n=== LISTADO DE USUARIOS ===");
                    List<Usuario> lista = usuarioService.listar();

                    if (lista.isEmpty()) {
                        System.out.println("No hay usuarios disponibles");
                    } else {
                        for (int i = 0; i < lista.size(); i++) {
                            System.out.println(lista.get(i).toString());
                        }
                    }
                    break;
                case "2":
                    System.out.println("\n=== NUEVO USUARIO ===");
                    System.out.println("Ingresa el nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingresa el apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.println("Ingresa el mail: ");
                    String mail = scanner.nextLine();
                    System.out.println("Ingresa el celular: ");
                    String celular = scanner.nextLine();
                    System.out.println("Ingresa la contraseña: ");
                    String contrasenia = scanner.nextLine();

                    System.out.println("Selecciona el Rol (1: ADMIN, 2: USUARIO): ");
                    String rolEntrada = scanner.nextLine();
                    Rol rolSeleccionado = Rol.USUARIO;
                    if (rolEntrada.equals("1")) {
                        rolSeleccionado = Rol.ADMIN;
                    }
                    if (nombre.isEmpty() || mail.isEmpty()) {
                        System.out.println("Error: El nombre y el mail no pueden estar vacios");
                    } else {
                        Usuario nuevo = usuarioService.crear(nombre, apellido, mail, celular, contrasenia, rolSeleccionado);
                        if (nuevo != null) {
                            System.out.println("Usuario creado correctamente. ID: " + nuevo.getId());
                        }
                    }
                    break;
                case "3":
                    System.out.println("\n=== EDITAR USUARIO ===");
                    Long idEditar = leerLong(scanner, "Ingresa el ID del usuario a editar: ");
                    Usuario usuarioEditar = usuarioService.buscarPorId(idEditar);

                    if (usuarioEditar == null || usuarioEditar.isEliminado()) {
                        System.out.println("Error: El ID no existe o el usuario ya fue eliminado");
                    } else {
                        System.out.println("Datos actuales: " + usuarioEditar.toString());
                        System.out.println("Nuevo nombre (o deja en blanco para mantener): ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.println("Nuevo apellido (o deja en blanco para mantener): ");
                        String nuevoApellido = scanner.nextLine();
                        System.out.println("Nuevo mail (o deja en blanco para mantener): ");
                        String nuevoMail = scanner.nextLine();
                        System.out.println("Nuevo celular (o deja en blanco para mantener): ");
                        String nuevoCelular = scanner.nextLine();
                        System.out.println("Nueva contraseña (o deja en blanco para mantener): ");
                        String nuevaContrasenia = scanner.nextLine();

                        if (nuevoNombre.isEmpty()) {
                            nuevoNombre = usuarioEditar.getNombre();
                        }
                        if (nuevoApellido.isEmpty()) {
                            nuevoApellido = usuarioEditar.getApellido();
                        }
                        if (nuevoMail.isEmpty()) {
                            nuevoMail = usuarioEditar.getMail();
                        }
                        if (nuevoCelular.isEmpty()) {
                            nuevoCelular = usuarioEditar.getCelular();
                        }
                        if (nuevaContrasenia.isEmpty()) {
                            nuevaContrasenia = usuarioEditar.getContrasenia();
                        }
                        usuarioService.editar(idEditar, nuevoNombre, nuevoApellido, nuevoMail, nuevoCelular, nuevaContrasenia, usuarioEditar.getRol());
                        System.out.println("Usuario editado correctamente");
                    }
                    break;
                case "4":
                    System.out.println("\n=== ELIMINAR USUARIO");
                    Long idEliminar = leerLong(scanner, "Ingresa el ID del usuario a eliminar: ");
                    Usuario usuarioEliminar = usuarioService.buscarPorId(idEliminar);

                    if (usuarioEliminar == null || usuarioEliminar.isEliminado()) {
                        System.out.println("Error: El ID no existe o el usuario ya fue eliminado");
                    } else {
                        System.out.println("Estas seguro de eliminar al usuario: " + usuarioEliminar.getNombre() + " (S/N)");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("S")) {
                            usuarioService.eliminar(idEliminar);
                            System.out.println("Usuario eliminado correctamente");
                        } else {
                            System.out.println("Operacion cancelada");
                        }
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }

    private static void menuProductos(Scanner scanner, ProductoService productoService, CategoriaService categoriaService) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println("\n=== PRODUCTOS ===");
            System.out.println("1. Listar productos");
            System.out.println("2. Crear nuevo producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\n=== PRODUCTOS ===");
                    List<Producto> lista = productoService.listar();
                    if (lista.isEmpty()) {
                        System.out.println("No hay productos disponibles");
                    } else {
                        for (int i = 0; i < lista.size(); i++) {
                            System.out.println(lista.get(i).toString());
                        }
                    }
                    break;
                case "2":
                    System.out.println("\n=== NUEVO PRODUCTO ===");
                    System.out.println("Ingresa el nombre del producto: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingresa la descripcion: ");
                    String descripcion = scanner.nextLine();
                    double precio = leerDouble(scanner, "Ingresa el precio: ");
                    int stock = leerEntero(scanner, "Ingresa el stock inicial: ");
                    System.out.println("Ingresa la ruta o nombre de la imagen: ");
                    String imagen = scanner.nextLine();
                    Long idCategoria = leerLong(scanner, "Ingresa el ID de la categoria: ");
                    Categoria categoria = categoriaService.buscarPorId(idCategoria);

                    if (nombre.isEmpty()) {
                        System.out.println("Error: El nombre no puede estar vacio");
                    } else if (categoria == null || categoria.isEliminado()) {
                        System.out.println("Error: La categoria no existe o fue eliminada");
                    } else {
                        Producto nuevo = productoService.crear(nombre, descripcion, precio, stock, imagen, true, categoria);
                        System.out.println("Producto creado correctamente. ID: " + nuevo.getId());
                    }
                    break;
                case "3":
                    System.out.println("\n=== EDITAR PRODUCTO ===");
                    Long idEditar = leerLong(scanner, "Ingresa el ID del producto a editar: ");
                    Producto productoEditar = productoService.buscarPorId(idEditar);

                    if (productoEditar == null || productoEditar.isEliminado()) {
                        System.out.println("Error: El ID no existe o el producto ya fue eliminado");
                    } else {
                        System.out.println("Datos actuales: " + productoEditar.toString());
                        System.out.println("Nuevo nombre (o deja en blanco para mantener): ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.println("Nueva descripcion (o deja en blanco para mantener): ");
                        String nuevaDescripcion = scanner.nextLine();
                        System.out.println("Nuevo precio (o deja en blanco para mantener): ");
                        String nuevoPrecio = scanner.nextLine();
                        System.out.println("Nuevo stock (o deja en blanco para mantener): ");
                        String nuevoStock = scanner.nextLine();
                        System.out.println("Nueva imagen (o deja en blanco para mantener): ");
                        String nuevaImagen = scanner.nextLine();

                        if (nuevoNombre.isEmpty()) {
                            nuevoNombre = productoEditar.getNombre();
                        }
                        if (nuevaDescripcion.isEmpty()) {
                            nuevaDescripcion = productoEditar.getDescripcion();
                        }
                        if (nuevaImagen.isEmpty()) {
                            nuevaImagen = productoEditar.getImagen();
                        }
                        double precioFinal = productoEditar.getPrecio();
                        int stockFinal = productoEditar.getStock();
                        if (!nuevoPrecio.isEmpty()) {
                            precioFinal = Double.parseDouble(nuevoPrecio);
                        }
                        if (!nuevoStock.isEmpty()) {
                            stockFinal = Integer.parseInt(nuevoStock);
                        }
                        productoService.editar(idEditar, nuevoNombre, nuevaDescripcion, precioFinal, stockFinal, nuevaImagen, productoEditar.isDisponible(), productoEditar.getCategoria());
                        System.out.println("Producto editado correctamente");
                    }
                    break;
                case "4":
                    System.out.println("\n=== ELIMINAR PRODUCTO ===");
                    Long idEliminar = leerLong(scanner, "Ingresa el ID del producto a eliminar: ");
                    Producto productoEliminar = productoService.buscarPorId(idEliminar);

                    if (productoEliminar == null || productoEliminar.isEliminado()) {
                        System.out.println("Error: El ID no existe o el producto ya fue eliminado");
                    } else {
                        System.out.println("Estas seguro de eliminar al producto: " + productoEliminar.getNombre() + " (S/N)");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("S")) {
                            productoService.eliminar(idEliminar);
                            System.out.println("Producto eliminado correctamente");
                        } else {
                            System.out.println("Operacion cancelada");
                        }
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }

    private static void menuPedidos(Scanner scanner, PedidoService pedidoService, ProductoService productoService, UsuarioService usuarioService) {
        String opcion = "";
        while (!opcion.equals("0")) {
            System.out.println("\n=== PEDIDOS ===");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Editar estado y pago");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\n=== LISTADO DE PEDIDOS ===");
                    List<Pedido> lista = pedidoService.listarActivos();
                    if (lista.isEmpty()) {
                        System.out.println("No hay pedidos disponibles");
                    } else {
                        for (int i = 0; i < lista.size(); i++) {
                            Pedido pedido = lista.get(i);
                            System.out.println(pedido.toString());
                            System.out.println("Detalles del pedido: ");
                            for (int j = 0; j < pedido.getDetalles().size(); j++) {
                                System.out.println(" " + pedido.getDetalles().get(j).toString());
                            }
                        }
                    }
                    break;
                case "2":
                    System.out.println("\n=== NUEVO PEDIDO ===");
                    Long idUsuario = leerLong(scanner, "Ingresa el ID del usuario(Cliente): ");
                    Usuario cliente = usuarioService.buscarPorId(idUsuario);
                    if (cliente == null || cliente.isEliminado()) {
                        System.out.println("Error: El ID no existe o el usuario ya fue eliminado");
                        break;
                    }
                    List<Long> idProductos = new ArrayList<>();
                    List<Integer> cantidades = new ArrayList<>();

                    String continuar = "S";
                    while (continuar.equalsIgnoreCase("S")) {
                        Long idProducto = leerLong(scanner, "Ingresa el ID del producto: ");
                        Producto producto = productoService.buscarPorId(idProducto);
                        if (producto == null || producto.isEliminado()) {
                            System.out.println("Error: El ID no existe o el producto ya fue eliminado");
                        } else {
                            System.out.println("Producto seleccionado: " + producto.getNombre() + "\nPrecio: $" + producto.getPrecio());
                            int cantidad = leerEntero(scanner, "Ingresa la cantidad: ");
                            idProductos.add(idProducto);
                            cantidades.add(cantidad);
                            System.out.println("Producto añadido al carrito");
                        }
                        System.out.println("Desea agregar otro producto? (S/N)");
                        continuar = scanner.nextLine();
                    }

                    if (!idProductos.isEmpty()) {
                        System.out.println("Selecciona la Forma de Pago (1: EFECTIVO, 2: TARJETA, 3: TRANSFERENCIA): ");
                        String pagoEntrada = scanner.nextLine();
                        FormaPago formaPago = FormaPago.EFECTIVO;
                        if (pagoEntrada.equals("2")) {
                            formaPago = FormaPago.TARJETA;
                        } else if (pagoEntrada.equals("3")) {
                            formaPago = FormaPago.TRANSFERENCIA;
                        }
                        Pedido nuevoPedido = pedidoService.crearPedido(idUsuario, idProductos, cantidades, formaPago);
                        if (nuevoPedido != null) {
                            System.out.println("Pedido creado correctamente. ID: " + nuevoPedido.getId() + "\nTotal: $" + nuevoPedido.getTotal());
                        } else {
                            System.out.println("Error al crear el pedido");
                        }
                    } else {
                        System.out.println("Pedido cancelado: No se seleccionaron productos validos");
                    }
                    break;
                case "3":
                    System.out.println("\n=== ACTUALIZAR ESTADO Y PAGO ===");
                    Long idPedido = leerLong(scanner, "Ingresa el ID del pedido: ");

                    Pedido pedido = pedidoService.buscarPorId(idPedido);
                    if (pedido == null || pedido.isEliminado()) {
                        System.out.println("Error: El ID no existe o el pedido ya fue eliminado");
                    } else {
                        System.out.println("Estado actual: " + pedido.getEstado() + "\nPago actual: " + pedido.getFormaPago());
                        System.out.println("Selecciona el nuevo estado (1: PENDIENTE, 2: CONFIRMADO, 3: TERMINADO, 4: CANCELADO): ");
                        String estadoEntrada = scanner.nextLine();;
                        Estado nuevoEstado = pedido.getEstado();
                        if (estadoEntrada.equals("1")) {
                            nuevoEstado = Estado.PENDIENTE;
                        }
                        if (estadoEntrada.equals("2")) {
                            nuevoEstado = Estado.CONFIRMADO;
                        }
                        if (estadoEntrada.equals("3")) {
                            nuevoEstado = Estado.TERMINADO;
                        }
                        if (estadoEntrada.equals("4")) {
                            nuevoEstado = Estado.CANCELADO;
                        }
                        System.out.println("Selecciona la forma de pago (1: EFECTIVO, 2: TARJETA, 3: TRANSFERENCIA): ");
                        String pagoEntrada = scanner.nextLine();
                        FormaPago formaPago = pedido.getFormaPago();
                        if (pagoEntrada.equals("1")) {
                            formaPago = FormaPago.EFECTIVO;
                        }
                        if (pagoEntrada.equals("2")) {
                            formaPago = FormaPago.TARJETA;
                        }
                        if (pagoEntrada.equals("3")) {
                            formaPago = FormaPago.TRANSFERENCIA;
                        }
                        pedidoService.actualizarEstadoYPago(idPedido, nuevoEstado, formaPago);
                        System.out.println("Pedido actualizado correctamente");
                    }
                    break;
                case "4":
                    System.out.println("\n=== ELIMINAR PEDIDO ===");
                    Long idEliminar = leerLong(scanner, "Ingresa el ID del pedido a eliminar: ");

                    Pedido pedidoEliminar = pedidoService.buscarPorId(idEliminar);
                    if (pedidoEliminar == null || pedidoEliminar.isEliminado()) {
                        System.out.println("Error: El ID no existe o el pedido ya fue eliminado");
                    } else {
                        System.out.println("Estas seguro de eliminar al pedido: " + pedidoEliminar.getId() + " (S/N)");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("S")) {
                            pedidoService.eliminar(idEliminar);
                            System.out.println("Pedido eliminado correctamente");
                        } else {
                            System.out.println("Operacion cancelada");
                        }
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Tenes que ingresar un numero entero valido, intenta nuevamente.");
            }
        }
    }

    private static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Tenes que ingresar un numero decimal valido (Ejemplo: 550.50, intenta nuevamente.");
            }
        }
    }

    private static Long leerLong(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ID invalido. Tenes que ingresar un numero entero valido, intenta nuevamente.");
            }
        }
    }

}
