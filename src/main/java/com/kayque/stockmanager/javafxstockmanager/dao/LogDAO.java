package com.kayque.stockmanager.javafxstockmanager.dao;

import com.kayque.stockmanager.javafxstockmanager.database.Conexao;
import com.kayque.stockmanager.javafxstockmanager.model.LogSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public java.util.List<LogSistema> listar() {
        java.util.List<LogSistema> logs = new java.util.ArrayList<>();

        String sql = "SELECT * FROM logs ORDER BY id DESC";

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LogSistema log = new LogSistema();

                log.setId(rs.getInt("id"));
                log.setUsuario(rs.getString("usuario"));
                log.setAcao(rs.getString("acao"));
                log.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());

                logs.add(log);
            }

            rs.close();
            stmt.close();
            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return logs;
    }

}