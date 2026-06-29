package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashController {

    @FXML
    private ImageView logoImage;

    @FXML
    public void initialize() {
        carregarLogo();
        abrirLoginDepois();
    }

    private void carregarLogo() {
        var url = HelloApplication.class.getResource(
                "/com/kayque/stockmanager/javafxstockmanager/images/logo.png"
        );

        if (url != null) {
            logoImage.setImage(new Image(url.toExternalForm()));
        }
    }

    private void abrirLoginDepois() {
        PauseTransition pausa = new PauseTransition(Duration.seconds(2));

        pausa.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        HelloApplication.class.getResource("Login.fxml")
                );

                Stage stage = (Stage) logoImage.getScene().getWindow();
                Scene scene = new Scene(loader.load(), 900, 600);

                stage.setScene(scene);
                stage.setTitle("Stock Manager");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        pausa.play();
    }
}