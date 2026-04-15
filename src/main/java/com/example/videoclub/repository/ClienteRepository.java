package com.example.videoclub.repository;

import com.example.videoclub.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "clientes", collectionResourceRel = "clientes")
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @RestResource(path = "por-email", rel = "por-email")
    Optional<Cliente> findByEmail(String email);
}