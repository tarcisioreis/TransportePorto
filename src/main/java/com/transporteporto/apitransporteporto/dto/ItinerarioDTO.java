package com.transporteporto.apitransporteporto.dto;

import com.transporteporto.apitransporteporto.entity.Itinerario;
import com.transporteporto.apitransporteporto.entity.Linha;

import java.io.Serializable;

public class ItinerarioDTO implements Serializable {

    private Long id;
    private double latitude;
    private double longitude;
    private Long idLinha;

    public ItinerarioDTO(Long id, double latitude, double longitude, Long idLinha) {
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

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Long getIdLinha() {
        return idLinha;
    }
    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
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
