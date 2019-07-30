package com.transporteporto.apitransporteporto.dto;

import com.transporteporto.apitransporteporto.entity.Itinerario;

import java.io.Serializable;

public class ItinerarioDTO implements Serializable {

    private Long id;
    private Double latitude;
    private Double longitude;
    private Long idLinha;
    private Double raio;

    public ItinerarioDTO() { super(); }

    public ItinerarioDTO(Long id, Double latitude, Double longitude, Long idLinha) {
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

    public Double getRaio() {
        return raio;
    }
    public void setRaio(Double raio) {
        this.raio = raio;
    }

    public Itinerario valueOf() {
        return new Itinerario(getId(), getLatitude(), getLongitude(), getIdLinha());
    }

    public static ItinerarioDTO valueOf(Itinerario itinerario) {
        return new ItinerarioDTO(itinerario.getId(),
                                 itinerario.getLatitude(),
                                 itinerario.getLongitude(),
                                 itinerario.getIdLinha());
    }

}
