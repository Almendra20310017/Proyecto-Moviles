package com.example.proyecto_moviles;

public class ListaContactos {
    public String nombre;
    public String cuenta;
    public String alias;
    public int tipoCuenta;
    public boolean isSelected;

    public ListaContactos(String nombre, String cuenta, String alias, int tipoCuenta, boolean isSelected) {
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.alias = alias;
        this.tipoCuenta = tipoCuenta;
        this.isSelected = isSelected;
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

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
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
