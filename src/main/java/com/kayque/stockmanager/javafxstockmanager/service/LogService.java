package com.kayque.stockmanager.javafxstockmanager.service;

import com.kayque.stockmanager.javafxstockmanager.dao.LogDAO;
import com.kayque.stockmanager.javafxstockmanager.model.LogSistema;
import com.kayque.stockmanager.javafxstockmanager.security.SessaoUsuario;

public class LogService {

    public static void registrar(String acao) {
        try {
            String usuario = "Sistema";

            if (SessaoUsuario.getUsuarioLogado() != null) {
                usuario = SessaoUsuario.getUsuarioLogado().getUsuario();
            }

            LogSistema log = new LogSistema(usuario, acao);
            LogDAO dao = new LogDAO();

            dao.registrar(log);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}