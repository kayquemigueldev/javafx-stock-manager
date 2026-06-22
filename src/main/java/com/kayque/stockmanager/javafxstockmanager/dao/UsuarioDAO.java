package com.kayque.stockmanager.javafxstockmanager.dao;

import com.kayque.stockmanager.javafxstockmanager.database.Conexao;
import com.kayque.stockmanager.javafxstockmanager.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsuarioDAO {

    public boolean salvar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios
                (nome, usuario, senha, perfil)
                VALUES (?, ?, ?, ?)
                """;

        try {

            Connection conexao = Conexao.conectar();

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil());

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