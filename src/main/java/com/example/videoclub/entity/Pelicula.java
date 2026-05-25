package com.example.videoclub.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pelicula", schema = "public")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String titulo;

    @Size(max = 60)
    @Column(nullable = true, length = 60)
    private String genero;

    @Size(max = 100)
    @Column(nullable = true, length = 100)
    private String director;

    @Min(1)
    @Column(nullable = false)
    private int stock = 1;

    @Column(nullable = false, name = "precio_dia")
    private double precio = 2.50;

    @Column(name = "anyo_estreno")
    private Integer fechaEstreno;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getGenero() {return genero;}
    public void setGenero(String genero) {this.genero = genero;}

    public String getDirector() {return director;}
    public void setDirector(String director) {this.director = director;}

    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    public double getPrecio() {return precio;}
    public void setPrecio(double precio) {this.precio = precio;}

    public Integer getFechaEstreno() {return fechaEstreno;}
    public void setFechaEstreno(Integer fechaEstreno) {this.fechaEstreno = fechaEstreno;}
}
