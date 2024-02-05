package com.backend.api.repository;

import com.backend.api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    Cliente findByEmailAndPassword(String email, String password);
    Cliente findByEmail(String email);
}
