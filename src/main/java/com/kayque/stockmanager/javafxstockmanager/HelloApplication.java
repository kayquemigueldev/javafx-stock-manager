package com.kayque.stockmanager.javafxstockmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("Login.fxml")
        );

        Scene scene = new Scene(loader.load(), 900, 600);

        stage.setTitle("Stock Manager");

        URL logoUrl = HelloApplication.class.getResource(
                "/com/kayque/stockmanager/javafxstockmanager/images/logo.png"
        );

        if (logoUrl != null) {
            stage.getIcons().add(
                    new Image(logoUrl.toExternalForm())
            );
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}