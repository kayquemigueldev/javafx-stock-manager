package com.kayque.stockmanager.javafxstockmanager.dao;

import com.kayque.stockmanager.javafxstockmanager.database.Conexao;
import com.kayque.stockmanager.javafxstockmanager.model.LogSistema;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogDAO {

    public boolean registrar(LogSistema log) {
        String sql = """
                INSERT INTO logs
                (usuario, acao)
                VALUES (?, ?)
                """;

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, log.getUsuario());
            stmt.setString(2, log.getAcao());

            stmt.executeUpdate();

            stmt.close();
            conexao.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}