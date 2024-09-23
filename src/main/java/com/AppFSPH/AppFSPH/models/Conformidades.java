package com.AppFSPH.AppFSPH.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Conformidades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Integer assunto;

    @NotNull
    private String descricao;

    private Date dataAbertura;

    private String origemConformidade;

    private int tipoConformidade;

    private int statusId;

    private int grausSeveridadeId;

    private int departamentoId;

    private LocalDateTime dataHoraCriacao;

    private LocalDateTime dataHoraUltimaAlteracao;

    public Conformidades() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAssunto() {
        return assunto;
    }

    public void setAssunto(Integer assunto) {
        this.assunto = assunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getOrigemConformidade() {
        return origemConformidade;
    }

    public void setOrigemConformidade(String origemConformidade) {
        this.origemConformidade = origemConformidade;
    }

    public int getTipoConformidade() {
        return tipoConformidade;
    }

    public void setTipoConformidade(int tipoConformidade) {
        this.tipoConformidade = tipoConformidade;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getGrausSeveridadeId() {
        return grausSeveridadeId;
    }

    public void setGrausSeveridadeId(int grausSeveridadeId) {
        this.grausSeveridadeId = grausSeveridadeId;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public LocalDateTime getDataHoraUltimaAlteracao() {
        return dataHoraUltimaAlteracao;
    }

    public void setDataHoraUltimaAlteracao(LocalDateTime dataHoraUltimaAlteracao) {
        this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
    }
}
