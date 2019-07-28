package com.transporteporto.apitransporteporto.entity;

//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "itinerario")
public class Itinerario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="latitude", nullable=false)
    private Double latitude;

    @Column(name="longitude", nullable=false)
    private Double longitude;

    @Column(name="idlinha", nullable=false)
    private Long idLinha;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idlinha", insertable = false, updatable = false)
//    @Fetch(FetchMode.JOIN)
//    private Linha linha;

    public Itinerario() { super(); }

    public Itinerario(Long id, Double latitude, Double longitude, Long idLinha) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.idLinha = idLinha;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getIdLinha() {
        return idLinha;
    }
    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
    }

}
