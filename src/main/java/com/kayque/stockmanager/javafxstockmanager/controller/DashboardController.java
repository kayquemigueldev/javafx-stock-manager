package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    public void abrirProdutos() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("Produtos.fxml")
            );

            Stage stage = (Stage) javafx.stage.Window.getWindows()
                    .stream()
                    .filter(window -> window instanceof Stage)
                    .findFirst()
                    .orElse(null);

            if (stage != null) {
                Scene scene = new Scene(loader.load(), 900, 600);
                stage.setScene(scene);
                stage.setTitle("Stock Manager - Produtos");
            }

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

            Stage stage = (Stage) javafx.stage.Window.getWindows()
                    .stream()
                    .filter(window -> window instanceof Stage)
                    .findFirst()
                    .orElse(null);

            if (stage != null) {
                Scene scene = new Scene(loader.load(), 900, 600);
                stage.setScene(scene);
                stage.setTitle("Stock Manager - Login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}