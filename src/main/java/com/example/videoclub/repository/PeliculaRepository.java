package com.example.videoclub.repository;

import com.example.videoclub.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "peliculas", collectionResourceRel = "peliculas")
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    @RestResource(path = "por-titulo", rel = "por-titulo")
    List<Pelicula> findByTitulo(String titulo);

    @RestResource(path = "por-genero", rel = "por-genero")
    List<Pelicula> findByGenero(String genero);
}