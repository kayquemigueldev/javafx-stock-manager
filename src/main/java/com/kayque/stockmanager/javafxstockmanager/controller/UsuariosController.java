package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.UsuarioDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UsuariosController {

    @FXML private TextField campoNome;
    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoSenha;
    @FXML private ComboBox<String> comboPerfil;
    @FXML private Label labelMensagem;

    @FXML private TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colunaId;
    @FXML private TableColumn<Usuario, String> colunaNome;
    @FXML private TableColumn<Usuario, String> colunaUsuario;
    @FXML private TableColumn<Usuario, String> colunaPerfil;

    private Usuario usuarioSelecionado;

    @FXML
    public void initialize() {
        comboPerfil.getItems().addAll("ADMIN", "FUNCIONARIO");

        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colunaPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));

        carregarUsuarios();

        tabelaUsuarios.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        usuarioSelecionado = novo;
                        campoNome.setText(novo.getNome());
                        campoUsuario.setText(novo.getUsuario());
                        comboPerfil.setValue(novo.getPerfil());
                        campoSenha.clear();
                    }
                });
    }

    @FXML
    public void salvarUsuario() {
        String nome = campoNome.getText();
        String usuarioTexto = campoUsuario.getText();
        String senha = campoSenha.getText();
        String perfil = comboPerfil.getValue();

        if (nome.isEmpty() || usuarioTexto.isEmpty() || senha.isEmpty() || perfil == null) {
            labelMensagem.setText("Preencha todos os campos.");
            return;
        }

        Usuario usuario = new Usuario(nome, usuarioTexto, senha, perfil);
        UsuarioDAO dao = new UsuarioDAO();

        if (dao.salvar(usuario)) {
            labelMensagem.setText("Usuário salvo com sucesso!");
            limparCampos();
            carregarUsuarios();
        } else {
            labelMensagem.setText("Erro ao salvar usuário.");
        }
    }

    @FXML
    public void atualizarUsuario() {
        if (usuarioSelecionado == null) {
            labelMensagem.setText("Selecione um usuário.");
            return;
        }

        usuarioSelecionado.setNome(campoNome.getText());
        usuarioSelecionado.setUsuario(campoUsuario.getText());
        usuarioSelecionado.setPerfil(comboPerfil.getValue());

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.atualizar(usuarioSelecionado)) {
            labelMensagem.setText("Usuário atualizado com sucesso!");
            limparCampos();
            carregarUsuarios();
            usuarioSelecionado = null;
        } else {
            labelMensagem.setText("Erro ao atualizar usuário.");
        }
    }

    @FXML
    public void excluirUsuario() {
        if (usuarioSelecionado == null) {
            labelMensagem.setText("Selecione um usuário.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.excluir(usuarioSelecionado.getId())) {
            labelMensagem.setText("Usuário excluído com sucesso!");
            limparCampos();
            carregarUsuarios();
            usuarioSelecionado = null;
        } else {
            labelMensagem.setText("Erro ao excluir usuário.");
        }
    }

    @FXML
    public void carregarUsuarios() {
        UsuarioDAO dao = new UsuarioDAO();
        ObservableList<Usuario> lista = FXCollections.observableArrayList(dao.listar());
        tabelaUsuarios.setItems(lista);
    }

    private void limparCampos() {
        campoNome.clear();
        campoUsuario.clear();
        campoSenha.clear();
        comboPerfil.setValue(null);
    }

    @FXML
    public void voltar() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("Dashboard.fxml")
            );

            Stage stage = (Stage) campoNome.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 900, 600);

            stage.setScene(scene);
            stage.setTitle("Stock Manager - Dashboard");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}