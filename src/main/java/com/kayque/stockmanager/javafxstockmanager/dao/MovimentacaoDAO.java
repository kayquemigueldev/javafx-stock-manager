package com.kayque.stockmanager.javafxstockmanager.dao;

import com.kayque.stockmanager.javafxstockmanager.database.Conexao;
import com.kayque.stockmanager.javafxstockmanager.model.Movimentacao;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}