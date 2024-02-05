package com.backend.api.repository;

import com.backend.api.model.Tiposproducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TiposProductoRepositorio extends JpaRepository<Tiposproducto, Integer> {
    @Query("SELECT t FROM Tiposproducto t WHERE t.idCliente = :idCliente")
    List<Tiposproducto> findByClienteId(Integer idCliente);
}
