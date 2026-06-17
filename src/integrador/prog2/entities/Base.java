package integrador.prog2.entities;

import java.time.LocalDate;

public abstract class Base {
    protected Long id;
    protected boolean eliminado;
    protected LocalDate createdAt;

    public Base() {
        this.eliminado = false;
        this.createdAt = LocalDate.now();
    }

    public Base(Long id) {
        this.id = id;
        this.eliminado = false;
        this.createdAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
