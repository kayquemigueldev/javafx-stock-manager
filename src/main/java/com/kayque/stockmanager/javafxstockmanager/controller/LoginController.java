package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.UsuarioDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Usuario;
import com.kayque.stockmanager.javafxstockmanager.security.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoSenha;
    @FXML private Label labelMensagem;

    @FXML
    public void entrar() {
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {
            labelMensagem.setText("Preencha usuário e senha.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuarioLogado = dao.autenticar(usuario, senha);

        if (usuarioLogado != null) {
            SessaoUsuario.iniciarSessao(usuarioLogado);
            abrirDashboard();
        } else {
            labelMensagem.setText("Usuário ou senha inválidos.");
        }
    }

    private void abrirDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("Dashboard.fxml")
            );

            Stage stage = (Stage) campoUsuario.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 900, 600);

            stage.setScene(scene);
            stage.setTitle("Stock Manager - Dashboard");

        } catch (Exception e) {
            e.printStackTrace();
            labelMensagem.setText("Erro ao abrir dashboard.");
        }
    }
}