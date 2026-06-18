package integrador.prog2.services;

import integrador.prog2.entities.Usuario;
import integrador.prog2.enums.Rol;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> usuarios;
    private long ultimoId;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.ultimoId = 0;
    }

    public Usuario crear (String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        for (int i = 0; i < this.usuarios.size(); i++) {
            Usuario user = this.usuarios.get(i);
            if (!user.isEliminado() && user.getMail().equalsIgnoreCase(mail)) {
                System.out.println("Error: Ya existe un usuario registrado con el email " + mail);
                return null;
            }
        }

        this.ultimoId++;
        Usuario nuevo = new Usuario(this.ultimoId, nombre, apellido, mail, celular, contrasenia, rol);
        this.usuarios.add(nuevo);
        return nuevo;
    }

    public List<Usuario> listar() {
        List<Usuario> activos = new ArrayList<>();
        for (int i = 0; i < this.usuarios.size(); i++) {
            Usuario user = this.usuarios.get(i);
            if (!user.isEliminado()) {
                activos.add(user);
            }
        }
        return activos;
    }

    public Usuario buscarPorId(Long id) {
        for (int i = 0; i < this.usuarios.size(); i++) {
            Usuario user = this.usuarios.get(i);
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public boolean editar(Long id, String nuevoNombre, String nuevoApellido, String nuevoMail, String nuevoCelular, String nuevaContrasenia, Rol nuevoRol) {
        Usuario encontrado = buscarPorId(id);
        if (encontrado != null) {
            encontrado.setNombre(nuevoNombre);
            encontrado.setApellido(nuevoApellido);
            encontrado.setMail(nuevoMail);
            encontrado.setCelular(nuevoCelular);
            encontrado.setContrasenia(nuevaContrasenia);
            encontrado.setRol(nuevoRol);
            return true;
        }
        return false;
    }

    public boolean eliminar(Long id) {
        Usuario encontrado = buscarPorId(id);
        if (encontrado != null) {
            encontrado.setEliminado(true);
            return true;
        }
        return false;
    }
}
