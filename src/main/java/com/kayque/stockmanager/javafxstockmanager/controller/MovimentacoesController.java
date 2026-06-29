package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.MovimentacaoDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Movimentacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.kayque.stockmanager.javafxstockmanager.service.LogService;

public class MovimentacoesController {

    @FXML private TextField campoProdutoId;
    @FXML private TextField campoQuantidade;
    @FXML private ComboBox<String> comboTipo;
    @FXML private Label labelMensagem;

    @FXML private TableView<Movimentacao> tabelaMovimentacoes;
    @FXML private TableColumn<Movimentacao, Integer> colunaId;
    @FXML private TableColumn<Movimentacao, String> colunaProduto;
    @FXML private TableColumn<Movimentacao, String> colunaTipo;
    @FXML private TableColumn<Movimentacao, Integer> colunaQuantidade;


    @FXML
    public void initialize() {
        comboTipo.getItems().addAll("ENTRADA", "SAIDA");

        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaProduto.setCellValueFactory(new PropertyValueFactory<>("produtoNome"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        carregarHistorico();
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
                labelMensagem.setText("Movimentação registrada com sucesso!");
                LogService.registrar(
                        "Registrou movimentação " + tipo +
                                " no produto ID " + produtoId +
                                " - quantidade " + quantidade
                );
                limparCampos();
                carregarHistorico();
            } else {
                labelMensagem.setText("Erro: produto inexistente ou estoque insuficiente.");
            }

        } catch (NumberFormatException e) {
            labelMensagem.setText("ID e quantidade devem ser números.");
        }
    }

    @FXML
    public void carregarHistorico() {
        MovimentacaoDAO dao = new MovimentacaoDAO();

        ObservableList<Movimentacao> lista = FXCollections.observableArrayList(
                dao.listar()
        );

        tabelaMovimentacoes.setItems(lista);
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