package com.example.videoclub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "cliente", schema = "public")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String apellido;

    @Size(max = 150)
    @Column(nullable = true, unique = true, length = 150)
    private String email;

    @Size(max = 20)
    @Column(nullable = true, length = 20)
    private String telefono;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro = LocalDate.now();

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public LocalDate getFechaRegistro() {return fechaRegistro;}
    public void setFechaRegistro(LocalDate fechaRegistro){this.fechaRegistro = fechaRegistro;}
}
