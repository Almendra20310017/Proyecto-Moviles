package com.example.proyecto_moviles;

public class Usuario {
    private String correo;
    private String nombre;
    private String contrasenia;
    private double balance;
    private boolean registrado;

    public Usuario(String correo, String nombre, String contrasenia, double balance, boolean registrado) {
        this.correo = correo;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.balance = balance;
        this.registrado = registrado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isRegistrado() {
        return registrado;
    }

    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }
}
