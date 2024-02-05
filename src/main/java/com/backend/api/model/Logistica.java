package com.backend.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "logistica")
public class Logistica {
    
    public Logistica() {
    }
    public Logistica(String fecharegistro, String fechaentrega, Integer cantidadproducto, Float precioenvio,
            Double descuento, Integer idCliente, Integer idtipoproducto, Integer idAlmacen) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fecharegistro = LocalDate.parse(fecharegistro,formatter);
        this.fechaentrega = LocalDate.parse(fechaentrega,formatter);
        this.cantidadproducto = cantidadproducto;
        this.precioenvio = precioenvio;
        this.descuento = descuento;
        this.idCliente = idCliente;
        this.idtipoproducto = idtipoproducto;
        this.idAlmacen = idAlmacen;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLogistica", nullable = false)
    private Integer id;

    @Column(name = "fecharegistro", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fecharegistro;

    @Column(name = "fechaentrega", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaentrega;

    @Column(name = "cantidadproducto", nullable = false)
    private Integer cantidadproducto;

    @Column(name = "precioenvio", nullable = false)
    private Float precioenvio;

    @Column(name = "descuento", nullable = false)
    private Double descuento;

    @Column(name = "idCliente", nullable = false)
    private Integer idCliente;

    @Column(name = "idtipoproducto", nullable = false)
    private Integer idtipoproducto;

    @Column(name = "idAlmacen", nullable = false)
    private Integer idAlmacen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(LocalDate fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public LocalDate getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(LocalDate fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public Integer getCantidadproducto() {
        return cantidadproducto;
    }

    public void setCantidadproducto(Integer cantidadproducto) {
        this.cantidadproducto = cantidadproducto;
    }

    public Float getPrecioenvio() {
        return precioenvio;
    }

    public void setPrecioenvio(Float precioenvio) {
        this.precioenvio = precioenvio;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double d) {
        this.descuento = d;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdtipoproducto() {
        return idtipoproducto;
    }

    public void setIdtipoproducto(Integer idtipoproducto) {
        this.idtipoproducto = idtipoproducto;
    }

    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

}