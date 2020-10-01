package com.cortezhac.greendaoaplication.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

// Define a la clase como una entidad de greedao (Basicamente es tu tabla de datos)
@Entity /*(indexes = {@Index (value = "codigo", unique = true)}) llave primaria*/
public class Articulos {
    @Id // Anotacion Id necesita un valor Long para trabajar correctamente
    private Long codigo;
    @NotNull
    private String descripcion;
    @NotNull
    private double precio;

    public Articulos(){

    }

    @Generated(hash = 2121908509)
    public Articulos(Long codigo, @NotNull String descripcion, double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
