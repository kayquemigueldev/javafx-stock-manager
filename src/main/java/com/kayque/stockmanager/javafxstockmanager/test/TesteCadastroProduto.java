package com.kayque.stockmanager.javafxstockmanager.test;

import com.kayque.stockmanager.javafxstockmanager.dao.ProdutoDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Produto;

public class TesteCadastroProduto {

    public static void main(String[] args) {

        Produto produto = new Produto(
                "Notebook Dell",
                "Notebook para uso empresarial",
                "Informática",
                3500.00,
                10
        );

        ProdutoDAO dao = new ProdutoDAO();

        if (dao.salvar(produto)) {
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar produto.");
        }
    }
}