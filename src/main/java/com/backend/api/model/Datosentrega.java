package com.backend.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "datosentrega")
public class Datosentrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddatosentrega", nullable = false)
    private Integer id;

    @Column(name = "idtransporte", nullable = false, length = 60)
    private String idtransporte;

    @Column(name = "numguia", nullable = false)
    private Integer numguia;

    @Column(name = "idLogistica", nullable = false)
    private Integer idLogistica;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdtransporte() {
        return idtransporte;
    }

    public void setIdtransporte(String idtransporte) {
        this.idtransporte = idtransporte;
    }

    public Integer getNumguia() {
        return numguia;
    }

    public void setNumguia(Integer numguia) {
        this.numguia = numguia;
    }

    public Integer getIdLogistica() {
        return idLogistica;
    }

    public void setIdLogistica(Integer idLogistica) {
        this.idLogistica = idLogistica;
    }

}