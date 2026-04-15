package com.example.videoclub.repository;

import com.example.videoclub.entity.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(path = "alquileres", collectionResourceRel = "alquileres")
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    @RestResource(path = "por-cliente", rel = "por-cliente")
    List<Alquiler> findByClienteId(Long clienteId);

    @Modifying
    @Transactional
    @Query("UPDATE Alquiler a SET a.devuelta = true WHERE a.id = :alquilerId")
    void marcarComoDevuelta(@Param("alquilerId") Long alquilerId);
}
