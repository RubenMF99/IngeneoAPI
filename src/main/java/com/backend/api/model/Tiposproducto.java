package com.backend.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tiposproducto")
public class Tiposproducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipoproducto", nullable = false)
    private Integer id;

    @Column(name = "idCliente", nullable = false)
    private Integer idCliente;

    @Column(name = "tiposProducto", nullable = false, length = 45)
    private String tiposProducto;

    @Column(name = "transporte", nullable = false, length = 45)
    private String transporte;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getTiposProducto() {
        return tiposProducto;
    }

    public void setTiposProducto(String tiposProducto) {
        this.tiposProducto = tiposProducto;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

}