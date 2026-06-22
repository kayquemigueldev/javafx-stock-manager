package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    @FXML private Label labelTotalProdutos;
    @FXML private Label labelEstoqueBaixo;
    @FXML private Label labelSemEstoque;
    @FXML private Label labelValorTotal;

    @FXML
    public void initialize() {
        carregarMetricas();
    }

    private void carregarMetricas() {
        ProdutoDAO dao = new ProdutoDAO();

        labelTotalProdutos.setText("Produtos cadastrados: " + dao.contarProdutos());
        labelEstoqueBaixo.setText("Produtos com estoque baixo: " + dao.contarEstoqueBaixo());
        labelSemEstoque.setText("Produtos sem estoque: " + dao.contarSemEstoque());

        double valorTotal = dao.calcularValorTotalEstoque();
        labelValorTotal.setText(String.format("Valor total em estoque: R$ %.2f", valorTotal));
    }

    @FXML
    public void abrirProdutos() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("Produtos.fxml")
            );

            Stage stage = (Stage) labelTotalProdutos.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 900, 600);

            stage.setScene(scene);
            stage.setTitle("Stock Manager - Produtos");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sair() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("Login.fxml")
            );

            Stage stage = (Stage) labelTotalProdutos.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 900, 600);

            stage.setScene(scene);
            stage.setTitle("Stock Manager - Login");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}