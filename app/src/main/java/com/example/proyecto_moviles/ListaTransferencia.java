package com.example.proyecto_moviles;

public class ListaTransferencia {
    public String color;
    public String titulo;
    public String Subtitulo;
    public String img;
    public String valor;

    public ListaTransferencia(String color, String titulo, String subtitulo, String img, String valor) {
        this.color = color;
        this.titulo = titulo;
        Subtitulo = subtitulo;
        this.img = img;
        this.valor = valor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return Subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        Subtitulo = subtitulo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
