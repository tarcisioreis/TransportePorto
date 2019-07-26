package com.transporteporto.apitransporteporto.dto;

import com.transporteporto.apitransporteporto.entity.Linha;

import java.io.Serializable;

public class LinhaDTO implements Serializable {

    private Long id;
    private String codigo;
    private String nome;

    public LinhaDTO(Long id, String codigo, String nome) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Linha valueOf() {
        return new Linha(getId(), getCodigo(), getNome());
    }

    public static LinhaDTO valueOf(Linha linha) {
        return new LinhaDTO(linha.getId(), linha.getCodigo(), linha.getNome());
    }

}
