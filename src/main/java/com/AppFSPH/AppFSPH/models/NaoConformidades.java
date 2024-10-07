package com.AppFSPH.AppFSPH.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class NaoConformidades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Integer assunto;

    @NotNull
    private String descricao;

    @NotNull
    private String dataAbertura; // Altere para LocalDate ou String se o formato for "yyyy-MM-dd"

    private String origemNaoConformidade;

    @NotNull
    private int tipoNaoConformidadeId; // Atualizado para 'tipoNaoConformidadeId' para refletir o JSON

    @NotNull
    private int statusId;

    private int grausSeveridadeId;

    private int departamentoId;

    private LocalDateTime dataHoraCriacao;

    private LocalDateTime usuarioUltimaAlteracao; // Corrigido para refletir o JSON

    private LocalDateTime dataHoraUltimaAlteracao;

    public NaoConformidades() {
    }

    // Getters e Setters
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

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) { // Atualizado para String
        this.dataAbertura = dataAbertura;
    }

    public String getOrigemNaoConformidade() {
        return origemNaoConformidade;
    }

    public void setOrigemNaoConformidade(String origemNaoConformidade) {
        this.origemNaoConformidade = origemNaoConformidade;
    }

    public int getTipoNaoConformidadeId() {
        return tipoNaoConformidadeId;
    }

    public void setTipoNaoConformidadeId(int tipoNaoConformidadeId) {
        this.tipoNaoConformidadeId = tipoNaoConformidadeId;
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

    public LocalDateTime getUsuarioUltimaAlteracao() {
        return usuarioUltimaAlteracao; // Corrigido para o campo correto
    }

    public void setUsuarioUltimaAlteracao(LocalDateTime usuarioUltimaAlteracao) {
        this.usuarioUltimaAlteracao = usuarioUltimaAlteracao; // Corrigido para o campo correto
    }

    public LocalDateTime getDataHoraUltimaAlteracao() {
        return dataHoraUltimaAlteracao;
    }

    public void setDataHoraUltimaAlteracao(LocalDateTime dataHoraUltimaAlteracao) {
        this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
    }

    @Override
    public String toString() {
        return "NaoConformidades{" +
                "id=" + id +
                ", assunto=" + assunto +
                ", descricao='" + descricao + '\'' +
                ", dataAbertura='" + dataAbertura + '\'' +
                ", origemNaoConformidade='" + origemNaoConformidade + '\'' +
                ", tipoNaoConformidadeId=" + tipoNaoConformidadeId +
                ", statusId=" + statusId +
                ", grausSeveridadeId=" + grausSeveridadeId +
                ", departamentoId=" + departamentoId +
                ", dataHoraCriacao=" + dataHoraCriacao +
                ", dataHoraUltimaAlteracao=" + dataHoraUltimaAlteracao +
                '}';
    }
}
