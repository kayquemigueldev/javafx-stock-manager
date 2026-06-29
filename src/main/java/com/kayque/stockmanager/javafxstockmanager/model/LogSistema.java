package com.kayque.stockmanager.javafxstockmanager.model;

import java.time.LocalDateTime;

public class LogSistema {

    private int id;
    private String usuario;
    private String acao;
    private LocalDateTime dataHora;

    public LogSistema() {
    }

    public LogSistema(String usuario, String acao) {
        this.usuario = usuario;
        this.acao = acao;
    }

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAcao() {
        return acao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}