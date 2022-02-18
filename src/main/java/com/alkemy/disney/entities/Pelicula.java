package com.alkemy.disney.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Pelicula implements Serializable{
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String imagen;
    private String titulo;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    
    private Integer calificacion;
    
    @ManyToOne
    private Genero genero;

    public Pelicula() {
    }

    public Pelicula(String id, String imagen, String titulo, Date fechaCreacion, Integer calificacion, Genero genero) {
        this.id = id;
        this.imagen = imagen;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.genero = genero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    public String cambiarFecha(Date fecha){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
        String date = formato.format(fecha);
        return date;
    }
           


    @Override
    public String toString() {
        return "Pelicula{" + "id=" + id + ", imagen=" + imagen + ", titulo=" + titulo + ", fechaCreacion= " + cambiarFecha(fechaCreacion) + ", calificacion=" + calificacion + ", genero=" + genero + '}';
    }
    
    
    
}
