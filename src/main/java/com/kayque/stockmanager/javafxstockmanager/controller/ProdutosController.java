package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.ProdutoDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.kayque.stockmanager.javafxstockmanager.util.GeradorPDF;

public class ProdutosController {

    @FXML private TextField campoNome;
    @FXML private TextField campoDescricao;
    @FXML private TextField campoCategoria;
    @FXML private TextField campoPreco;
    @FXML private TextField campoQuantidade;
    @FXML private TextField txtPesquisar;

    @FXML private Label labelMensagem;

    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, Integer> colunaId;
    @FXML private TableColumn<Produto, String> colunaNome;
    @FXML private TableColumn<Produto, String> colunaCategoria;
    @FXML private TableColumn<Produto, Double> colunaPreco;
    @FXML private TableColumn<Produto, Integer> colunaQuantidade;
    @FXML private TableColumn<Produto, String> colunaDescricao;
    @FXML private TableColumn<Produto, String> colunaStatus;

    private Produto produtoSelecionado;

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        carregarProdutos();

        tabelaProdutos.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        produtoSelecionado = novo;

                        campoNome.setText(novo.getNome());
                        campoDescricao.setText(novo.getDescricao());
                        campoCategoria.setText(novo.getCategoria());
                        campoPreco.setText(String.valueOf(novo.getPreco()));
                        campoQuantidade.setText(String.valueOf(novo.getQuantidade()));
                    }
                });
    }

    @FXML
    public void salvarProduto() {
        try {
            String nome = campoNome.getText();
            String descricao = campoDescricao.getText();
            String categoria = campoCategoria.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            int quantidade = Integer.parseInt(campoQuantidade.getText());

            if (nome.isEmpty() || categoria.isEmpty()) {
                labelMensagem.setText("Preencha nome e categoria.");
                return;
            }

            Produto produto = new Produto(nome, descricao, categoria, preco, quantidade);
            ProdutoDAO dao = new ProdutoDAO();

            if (dao.salvar(produto)) {
                labelMensagem.setText("Produto salvo com sucesso!");
                limparCampos();
                carregarProdutos();
            } else {
                labelMensagem.setText("Erro ao salvar produto.");
            }

        } catch (NumberFormatException e) {
            labelMensagem.setText("Preço e quantidade devem ser números.");
        }
    }

    @FXML
    public void atualizarProduto() {
        if (produtoSelecionado == null) {
            labelMensagem.setText("Selecione um produto na tabela.");
            return;
        }

        try {
            produtoSelecionado.setNome(campoNome.getText());
            produtoSelecionado.setDescricao(campoDescricao.getText());
            produtoSelecionado.setCategoria(campoCategoria.getText());
            produtoSelecionado.setPreco(Double.parseDouble(campoPreco.getText()));
            produtoSelecionado.setQuantidade(Integer.parseInt(campoQuantidade.getText()));

            ProdutoDAO dao = new ProdutoDAO();

            if (dao.atualizar(produtoSelecionado)) {
                labelMensagem.setText("Produto atualizado com sucesso!");
                limparCampos();
                carregarProdutos();
                produtoSelecionado = null;
            } else {
                labelMensagem.setText("Erro ao atualizar produto.");
            }

        } catch (Exception e) {
            labelMensagem.setText("Dados inválidos.");
        }
    }

    @FXML
    public void excluirProduto() {
        if (produtoSelecionado == null) {
            labelMensagem.setText("Selecione um produto.");
            return;
        }

        ProdutoDAO dao = new ProdutoDAO();

        if (dao.excluir(produtoSelecionado.getId())) {
            labelMensagem.setText("Produto excluído com sucesso!");
            limparCampos();
            carregarProdutos();
            produtoSelecionado = null;
        } else {
            labelMensagem.setText("Erro ao excluir produto.");
        }
    }

    @FXML
    public void carregarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        ObservableList<Produto> lista = FXCollections.observableArrayList(dao.listar());
        tabelaProdutos.setItems(lista);
    }

    @FXML
    public void pesquisarProduto() {
        String pesquisa = txtPesquisar.getText();

        ProdutoDAO dao = new ProdutoDAO();
        ObservableList<Produto> lista = FXCollections.observableArrayList(
                dao.buscarPorNome(pesquisa)
        );

        tabelaProdutos.setItems(lista);
    }

    private void limparCampos() {
        campoNome.clear();
        campoDescricao.clear();
        campoCategoria.clear();
        campoPreco.clear();
        campoQuantidade.clear();
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

    @FXML
    public void exportarPDF() {

        ProdutoDAO dao = new ProdutoDAO();

        GeradorPDF.gerar(
                dao.listar()
        );

        labelMensagem.setText(
                "PDF gerado com sucesso!"
        );
    }
}