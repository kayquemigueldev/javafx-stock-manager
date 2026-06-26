package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Map;

public class DashboardController {

    @FXML private Label labelTotalProdutos;
    @FXML private Label labelEstoqueBaixo;
    @FXML private Label labelSemEstoque;
    @FXML private Label labelValorTotal;

    @FXML private PieChart graficoStatus;
    @FXML private BarChart<String, Number> graficoCategorias;

    @FXML
    public void initialize() {
        carregarMetricas();
        carregarGraficos();
    }

    private void carregarMetricas() {
        ProdutoDAO dao = new ProdutoDAO();

        labelTotalProdutos.setText("Produtos cadastrados: " + dao.contarProdutos());
        labelEstoqueBaixo.setText("Produtos com estoque baixo: " + dao.contarEstoqueBaixo());
        labelSemEstoque.setText("Produtos sem estoque: " + dao.contarSemEstoque());

        double valorTotal = dao.calcularValorTotalEstoque();
        labelValorTotal.setText(String.format("Valor total em estoque: R$ %.2f", valorTotal));
    }

    private void carregarGraficos() {
        ProdutoDAO dao = new ProdutoDAO();

        int totalProdutos = dao.contarProdutos();
        int estoqueBaixo = dao.contarEstoqueBaixo();
        int semEstoque = dao.contarSemEstoque();
        int normal = totalProdutos - estoqueBaixo - semEstoque;

        graficoStatus.setData(FXCollections.observableArrayList(
                new PieChart.Data("Normal", normal),
                new PieChart.Data("Estoque Baixo", estoqueBaixo),
                new PieChart.Data("Sem Estoque", semEstoque)
        ));

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Produtos");

        Map<String, Integer> categorias = dao.contarProdutosPorCategoria();

        for (String categoria : categorias.keySet()) {
            serie.getData().add(
                    new XYChart.Data<>(categoria, categorias.get(categoria))
            );
        }

        graficoCategorias.getData().clear();
        graficoCategorias.getData().add(serie);
    }

    @FXML
    public void abrirProdutos() {
        trocarTela("Produtos.fxml", "Stock Manager - Produtos");
    }

    @FXML
    public void abrirUsuarios() {
        trocarTela("Usuarios.fxml", "Stock Manager - Usuários");
    }

    @FXML
    public void abrirMovimentacoes() {
        trocarTela("Movimentacoes.fxml", "Stock Manager - Movimentações");
    }

    @FXML
    public void sair() {
        trocarTela("Login.fxml", "Stock Manager - Login");
    }

    private void trocarTela(String arquivo, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(arquivo)
            );

            Stage stage = (Stage) labelTotalProdutos.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 900, 600);

            stage.setScene(scene);
            stage.setTitle(titulo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}