package com.kayque.stockmanager.javafxstockmanager.dao;

import com.kayque.stockmanager.javafxstockmanager.database.Conexao;
import com.kayque.stockmanager.javafxstockmanager.model.Usuario;
import com.kayque.stockmanager.javafxstockmanager.security.Seguranca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
            stmt.setString(3, Seguranca.gerarHash(usuario.getSenha()));
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
                SELECT *
                FROM usuarios
                WHERE usuario = ?
                """;

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");

                if (Seguranca.verificarSenha(senha, senhaHash)) {
                    Usuario usuarioEncontrado = new Usuario();

                    usuarioEncontrado.setId(rs.getInt("id"));
                    usuarioEncontrado.setNome(rs.getString("nome"));
                    usuarioEncontrado.setUsuario(rs.getString("usuario"));
                    usuarioEncontrado.setSenha(rs.getString("senha"));
                    usuarioEncontrado.setPerfil(rs.getString("perfil"));

                    rs.close();
                    stmt.close();
                    conexao.close();

                    return usuarioEncontrado;
                }
            }

            rs.close();
            stmt.close();
            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT id, nome, usuario, perfil
                FROM usuarios
                ORDER BY id DESC
                """;

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setPerfil(rs.getString("perfil"));

                usuarios.add(usuario);
            }

            rs.close();
            stmt.close();
            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean atualizar(Usuario usuario) {
        String sql = """
                UPDATE usuarios
                SET nome = ?,
                    usuario = ?,
                    perfil = ?
                WHERE id = ?
                """;

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getPerfil());
            stmt.setInt(4, usuario.getId());

            stmt.executeUpdate();

            stmt.close();
            conexao.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, id);
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