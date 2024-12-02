package com.example.myagenda;

public class Asset{
    private int id;
    private String descripcion;
    private float precio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
            this.id = id;
    }

    public void setId(String id) {
        if (!id.isEmpty())
            this.id = Integer.parseInt(id);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
            this.precio = precio;
    }

    public void setPrecio(String precio) {
        if (!precio.isEmpty())
            this.precio = Float.parseFloat(precio);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }
}
