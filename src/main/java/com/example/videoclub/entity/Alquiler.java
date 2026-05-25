package com.example.videoclub.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "alquiler", schema = "public")
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "fecha_alquiler")
    private LocalDate fechaAlquiler = LocalDate.now();

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @Column(name = "precio_total")
    private BigDecimal precioTotal;

    @Column(nullable = true)
    private boolean devuelta = false;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public LocalDate getFechaAlquiler() {return fechaAlquiler;}
    public void setFechaAlquiler(LocalDate fechaAlquiler) {this.fechaAlquiler = fechaAlquiler;}

    public LocalDate getFechaDevolucion() {return fechaDevolucion;}
    public void setFechaDevolucion(LocalDate fechaDevolucion) {this.fechaDevolucion = fechaDevolucion;}

    public BigDecimal getPrecioTotal() {return precioTotal;}
    public void setPrecioTotal(BigDecimal precioTotal) {this.precioTotal = precioTotal;}

    public boolean isDevuelta() {return devuelta;}
    public void setDevuelta(boolean devuelta) {this.devuelta = devuelta;}

    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public Pelicula getPelicula() {return pelicula;}
    public void setPelicula(Pelicula pelicula) {this.pelicula = pelicula;}
}
