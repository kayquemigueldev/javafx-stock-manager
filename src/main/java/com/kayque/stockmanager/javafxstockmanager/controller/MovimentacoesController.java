package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.MovimentacaoDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Movimentacao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MovimentacoesController {

    @FXML private TextField campoProdutoId;
    @FXML private TextField campoQuantidade;
    @FXML private ComboBox<String> comboTipo;
    @FXML private Label labelMensagem;

    @FXML
    public void initialize() {
        comboTipo.getItems().addAll("ENTRADA", "SAIDA");
    }

    @FXML
    public void registrarMovimentacao() {
        try {
            int produtoId = Integer.parseInt(campoProdutoId.getText());
            int quantidade = Integer.parseInt(campoQuantidade.getText());
            String tipo = comboTipo.getValue();

            if (tipo == null) {
                labelMensagem.setText("Selecione o tipo.");
                return;
            }

            Movimentacao movimentacao = new Movimentacao(produtoId, tipo, quantidade);
            MovimentacaoDAO dao = new MovimentacaoDAO();

            if (dao.registrar(movimentacao)) {
                labelMensagem.setText("Movimentação registrada com sucesso!");
                limparCampos();
            } else {
                labelMensagem.setText("Erro: produto inexistente ou estoque insuficiente.");
            }

        } catch (NumberFormatException e) {
            labelMensagem.setText("ID e quantidade devem ser números.");
        }
    }

    private void limparCampos() {
        campoProdutoId.clear();
        campoQuantidade.clear();
        comboTipo.setValue(null);
    }

    @FXML
    public void voltar() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("Dashboard.fxml")
            );

            Stage stage = (Stage) campoProdutoId.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 900, 600);

            stage.setScene(scene);
            stage.setTitle("Stock Manager - Dashboard");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}