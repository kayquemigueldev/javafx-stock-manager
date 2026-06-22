package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.dao.UsuarioDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label labelMensagem;

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
            labelMensagem.setText("Bem-vindo, " + usuarioLogado.getNome() + "!");
        } else {
            labelMensagem.setText("Usuário ou senha inválidos.");
        }
    }
}