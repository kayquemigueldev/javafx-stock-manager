package com.kayque.stockmanager.javafxstockmanager.controller;

import com.kayque.stockmanager.javafxstockmanager.HelloApplication;
import com.kayque.stockmanager.javafxstockmanager.dao.ProdutoDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Produto;
import com.kayque.stockmanager.javafxstockmanager.util.GeradorPDF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.kayque.stockmanager.javafxstockmanager.security.SessaoUsuario;
import javafx.scene.control.Button;


import java.io.File;

public class ProdutosController {

    @FXML private TextField campoNome;
    @FXML private TextField campoDescricao;
    @FXML private TextField campoCategoria;
    @FXML private TextField campoPreco;
    @FXML private TextField campoQuantidade;
    @FXML private TextField campoEstoqueMinimo;
    @FXML private TextField txtPesquisar;
    @FXML private Button botaoExcluir;
    @FXML private Button botaoPDF;
    @FXML private Label labelMensagem;
    @FXML private ImageView imagemProduto;
    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, Integer> colunaId;
    @FXML private TableColumn<Produto, String> colunaImagem;
    @FXML private TableColumn<Produto, String> colunaNome;
    @FXML private TableColumn<Produto, String> colunaCategoria;
    @FXML private TableColumn<Produto, Double> colunaPreco;
    @FXML private TableColumn<Produto, Integer> colunaQuantidade;
    @FXML private TableColumn<Produto, String> colunaDescricao;
    @FXML private TableColumn<Produto, String> colunaStatus;
    @FXML private Button botaoSalvar;
    @FXML private Button botaoAtualizar;
    @FXML private Button botaoEscolherImagem;

    private Produto produtoSelecionado;
    private String caminhoImagemSelecionada;

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaImagem.setCellValueFactory(new PropertyValueFactory<>("imagem"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        configurarColunaStatus();
        configurarColunaImagem();
        carregarProdutos();
        aplicarPermissoes();

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
                        campoEstoqueMinimo.setText(String.valueOf(novo.getEstoqueMinimo()));

                        caminhoImagemSelecionada = novo.getImagem();
                        carregarImagem(caminhoImagemSelecionada);
                    }
                });
    }

    private void configurarColunaStatus() {
        colunaStatus.setCellFactory(coluna -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(status);

                if (status.equals("Normal")) {
                    setStyle("-fx-text-fill: #15803d; -fx-font-weight: bold;");
                } else if (status.equals("Estoque Baixo")) {
                    setStyle("-fx-text-fill: #ca8a04; -fx-font-weight: bold;");
                } else if (status.equals("Sem Estoque")) {
                    setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    private void configurarColunaImagem() {
        colunaImagem.setCellFactory(coluna -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String caminhoImagem, boolean empty) {
                super.updateItem(caminhoImagem, empty);

                if (empty || caminhoImagem == null || caminhoImagem.isEmpty()) {
                    setGraphic(null);
                    return;
                }

                File arquivo = new File(caminhoImagem);

                if (arquivo.exists()) {
                    Image imagem = new Image(arquivo.toURI().toString());

                    imageView.setImage(imagem);
                    imageView.setFitWidth(45);
                    imageView.setFitHeight(45);
                    imageView.setPreserveRatio(true);

                    setGraphic(imageView);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    @FXML
    public void escolherImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolher imagem do produto");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );

        File arquivo = fileChooser.showOpenDialog(campoNome.getScene().getWindow());

        if (arquivo != null) {
            caminhoImagemSelecionada = arquivo.getAbsolutePath();
            carregarImagem(caminhoImagemSelecionada);
            labelMensagem.setText("Imagem selecionada: " + arquivo.getName());
        }
    }

    private void carregarImagem(String caminho) {
        try {
            if (caminho == null || caminho.isEmpty()) {
                imagemProduto.setImage(null);
                return;
            }

            File arquivo = new File(caminho);

            if (arquivo.exists()) {
                Image imagem = new Image(arquivo.toURI().toString());
                imagemProduto.setImage(imagem);
            } else {
                imagemProduto.setImage(null);
            }

        } catch (Exception e) {
            imagemProduto.setImage(null);
        }
    }

    @FXML
    public void salvarProduto() {
        try {
            String nome = campoNome.getText();
            String descricao = campoDescricao.getText();
            String categoria = campoCategoria.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            int quantidade = Integer.parseInt(campoQuantidade.getText());
            int estoqueMinimo = Integer.parseInt(campoEstoqueMinimo.getText());

            if (nome.isEmpty() || categoria.isEmpty()) {
                labelMensagem.setText("Preencha nome e categoria.");
                return;
            }

            System.out.println("CAMINHO DA IMAGEM: " + caminhoImagemSelecionada);

            Produto produto = new Produto(
                    nome,
                    descricao,
                    categoria,
                    preco,
                    quantidade,
                    estoqueMinimo,
                    caminhoImagemSelecionada
            );

            ProdutoDAO dao = new ProdutoDAO();

            if (dao.salvar(produto)) {
                labelMensagem.setText("Produto salvo com sucesso!");
                limparCampos();
                carregarProdutos();
            } else {
                labelMensagem.setText("Erro ao salvar produto.");
            }

        } catch (NumberFormatException e) {
            labelMensagem.setText("Preço, quantidade e estoque mínimo devem ser números.");
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
            produtoSelecionado.setEstoqueMinimo(Integer.parseInt(campoEstoqueMinimo.getText()));
            produtoSelecionado.setImagem(caminhoImagemSelecionada);

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
        campoEstoqueMinimo.clear();

        caminhoImagemSelecionada = null;
        produtoSelecionado = null;
        imagemProduto.setImage(null);

        tabelaProdutos.getSelectionModel().clearSelection();
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

        GeradorPDF.gerar(dao.listar());

        labelMensagem.setText("PDF gerado com sucesso!");
    }

    private void aplicarPermissoes() {
        if (!SessaoUsuario.isAdmin()) {
            botaoSalvar.setVisible(false);
            botaoSalvar.setManaged(false);

            botaoAtualizar.setVisible(false);
            botaoAtualizar.setManaged(false);

            botaoExcluir.setVisible(false);
            botaoExcluir.setManaged(false);

            botaoEscolherImagem.setVisible(false);
            botaoEscolherImagem.setManaged(false);

            botaoPDF.setVisible(false);
            botaoPDF.setManaged(false);
        }
    }
}