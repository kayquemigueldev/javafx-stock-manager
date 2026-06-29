package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.LogDAO;
import com.kayque.stockmanager.javafxstockmanager.model.LogSistema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class LogsController {

    @FXML private TableView<LogSistema> tabelaLogs;
    @FXML private TableColumn<LogSistema, Integer> colunaId;
    @FXML private TableColumn<LogSistema, String> colunaUsuario;
    @FXML private TableColumn<LogSistema, String> colunaAcao;
    @FXML private TableColumn<LogSistema, LocalDateTime> colunaDataHora;

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colunaAcao.setCellValueFactory(new PropertyValueFactory<>("acao"));
        colunaDataHora.setCellValueFactory(new PropertyValueFactory<>("dataHora"));

        carregarLogs();
    }

    @FXML
    public void carregarLogs() {
        LogDAO dao = new LogDAO();
        ObservableList<LogSistema> lista = FXCollections.observableArrayList(
                dao.listar()
        );

        tabelaLogs.setItems(lista);
    }

    @FXML
    public void voltar() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("Dashboard.fxml")
            );

            Stage stage = (Stage) tabelaLogs.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 900, 600);

            stage.setScene(scene);
            stage.setTitle("Stock Manager - Dashboard");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}