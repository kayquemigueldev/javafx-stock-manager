package com.kayque.stockmanager.javafxstockmanager;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {
        Connection conexao = Conexao.conectar();

        if (conexao != null) {
            System.out.println("Teste finalizado: banco conectado.");
        } else {
            System.out.println("Teste finalizado: falha na conexão.");
        }
    }
}