package com.kayque.stockmanager.javafxstockmanager.dao;

import com.kayque.stockmanager.javafxstockmanager.database.Conexao;
import com.kayque.stockmanager.javafxstockmanager.model.Movimentacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {

    public boolean registrar(Movimentacao movimentacao) {
        String sqlMovimentacao = """
                INSERT INTO movimentacoes
                (produto_id, tipo, quantidade)
                VALUES (?, ?, ?)
                """;

        String sqlEntrada = """
                UPDATE produtos
                SET quantidade = quantidade + ?
                WHERE id = ?
                """;

        String sqlSaida = """
                UPDATE produtos
                SET quantidade = quantidade - ?
                WHERE id = ? AND quantidade >= ?
                """;

        try {
            Connection conexao = Conexao.conectar();

            PreparedStatement stmtEstoque;

            if (movimentacao.getTipo().equalsIgnoreCase("ENTRADA")) {
                stmtEstoque = conexao.prepareStatement(sqlEntrada);
                stmtEstoque.setInt(1, movimentacao.getQuantidade());
                stmtEstoque.setInt(2, movimentacao.getProdutoId());
            } else {
                stmtEstoque = conexao.prepareStatement(sqlSaida);
                stmtEstoque.setInt(1, movimentacao.getQuantidade());
                stmtEstoque.setInt(2, movimentacao.getProdutoId());
                stmtEstoque.setInt(3, movimentacao.getQuantidade());
            }

            int linhasAtualizadas = stmtEstoque.executeUpdate();

            if (linhasAtualizadas == 0) {
                stmtEstoque.close();
                conexao.close();
                return false;
            }

            PreparedStatement stmtMovimentacao = conexao.prepareStatement(sqlMovimentacao);
            stmtMovimentacao.setInt(1, movimentacao.getProdutoId());
            stmtMovimentacao.setString(2, movimentacao.getTipo());
            stmtMovimentacao.setInt(3, movimentacao.getQuantidade());

            stmtMovimentacao.executeUpdate();

            stmtMovimentacao.close();
            stmtEstoque.close();
            conexao.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Movimentacao> listar() {

        List<Movimentacao> movimentacoes = new ArrayList<>();

        String sql = """
            SELECT m.*,
                   p.nome AS produto_nome
            FROM movimentacoes m
            INNER JOIN produtos p
            ON p.id = m.produto_id
            ORDER BY m.data_movimentacao DESC
            """;

        try {

            Connection conexao = Conexao.conectar();

            PreparedStatement stmt = conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Movimentacao mov = new Movimentacao();

                mov.setId(rs.getInt("id"));
                mov.setProdutoId(rs.getInt("produto_id"));
                mov.setProdutoNome(rs.getString("produto_nome"));
                mov.setTipo(rs.getString("tipo"));
                mov.setQuantidade(rs.getInt("quantidade"));

                movimentacoes.add(mov);
            }

            rs.close();
            stmt.close();
            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movimentacoes;
    }

}