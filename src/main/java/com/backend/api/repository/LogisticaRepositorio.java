package com.backend.api.repository;

import com.backend.api.model.Logistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogisticaRepositorio extends JpaRepository<Logistica, Integer> {
    @Query("SELECT l FROM Logistica l WHERE l.idCliente = :idCliente")
    List<Logistica> findByClienteId(Integer idCliente);
}
