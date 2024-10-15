package com.AppFSPH.AppFSPH.models;

import java.io.Serializable;
import java.time.LocalDateTime; // Use LocalDateTime para data e hora
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne; // Import necessário para o relacionamento
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
    private LocalDateTime dataAbertura; // Alterado para LocalDateTime

    private String origemNaoConformidade;

    @NotNull
    private int tipoNaoConformidadeId;

    @NotNull
    private int statusId;

    private int grausSeveridadeId;

    private int departamentoId;

    @NotNull
    private String usuarioNome; // Nome do usuário que criou a não conformidade

    // Novo campo para o usuário criador
    @ManyToOne // Define o relacionamento com a entidade User
    private User usuarioCriador;

    private LocalDateTime dataHoraCriacao; // Data e hora da criação

    private LocalDateTime dataHoraUltimaAlteracao; // Data e hora da última alteração

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

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) { // Alterado para LocalDateTime
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

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
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

    // Novo método para obter o usuário criador
    public User getUsuarioCriador() {
        return usuarioCriador;
    }

    // Novo método para definir o usuário criador
    public void setUsuarioCriador(User usuarioCriador) {
        this.usuarioCriador = usuarioCriador; // Define o usuário criador
    }

    @Override
    public String toString() {
        return "NaoConformidades{" +
                "id=" + id +
                ", assunto=" + assunto +
                ", descricao='" + descricao + '\'' +
                ", dataAbertura=" + dataAbertura +
                ", origemNaoConformidade='" + origemNaoConformidade + '\'' +
                ", tipoNaoConformidadeId=" + tipoNaoConformidadeId +
                ", statusId=" + statusId +
                ", grausSeveridadeId=" + grausSeveridadeId +
                ", departamentoId=" + departamentoId +
                ", usuarioNome='" + usuarioNome + '\'' +
                ", usuarioCriador=" + usuarioCriador + // Incluído no toString
                ", dataHoraCriacao=" + dataHoraCriacao +
                ", dataHoraUltimaAlteracao=" + dataHoraUltimaAlteracao +
                '}';
    }
}
