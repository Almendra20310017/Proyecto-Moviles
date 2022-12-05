package com.example.proyecto_moviles;

import java.io.Serializable;

public class ListaContactos implements Serializable {
    public String nombre;
    public String alias;
    public int tipoCuenta;
    public boolean isSelected;
    public String correo;

    public ListaContactos(String correo, String nombre, String alias, int tipoCuenta, boolean isSelected) {
        this.correo = correo;
        this.nombre = nombre;
        this.alias = alias;
        this.tipoCuenta = tipoCuenta;
        this.isSelected = isSelected;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(int tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}
