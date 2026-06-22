package com.kayque.stockmanager.javafxstockmanager.dao;

import com.kayque.stockmanager.javafxstockmanager.database.Conexao;
import com.kayque.stockmanager.javafxstockmanager.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

            String hash = com.kayque.stockmanager.javafxstockmanager.security.Seguranca
                    .gerarHash(usuario.getSenha());

            stmt.setString(3, hash);

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

    public Usuario autenticar(String usuario, String senha) {

        String sql = """
            SELECT * FROM usuarios
            WHERE usuario = ?
            """;

        try {

            Connection conexao = Conexao.conectar();

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String senhaHash = rs.getString("senha");

                boolean senhaCorreta =
                        com.kayque.stockmanager.javafxstockmanager.security.Seguranca
                                .verificarSenha(senha, senhaHash);

                if (senhaCorreta) {

                    Usuario usuarioEncontrado = new Usuario();

                    usuarioEncontrado.setId(rs.getInt("id"));
                    usuarioEncontrado.setNome(rs.getString("nome"));
                    usuarioEncontrado.setUsuario(rs.getString("usuario"));
                    usuarioEncontrado.setSenha(rs.getString("senha"));
                    usuarioEncontrado.setPerfil(rs.getString("perfil"));

                    return usuarioEncontrado;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
}