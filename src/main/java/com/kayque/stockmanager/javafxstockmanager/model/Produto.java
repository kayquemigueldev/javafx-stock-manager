package com.kayque.stockmanager.javafxstockmanager.model;

public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private String categoria;
    private double preco;
    private int quantidade;
    private int estoqueMinimo;
    private String imagem;

    public Produto() {
    }

    public Produto(String nome,
                   String descricao,
                   String categoria,
                   double preco,
                   int quantidade) {

        this(nome, descricao, categoria, preco, quantidade, 5, null);
    }

    public Produto(String nome,
                   String descricao,
                   String categoria,
                   double preco,
                   int quantidade,
                   String imagem) {

        this(nome, descricao, categoria, preco, quantidade, 5, imagem);
    }

    public Produto(String nome,
                   String descricao,
                   String categoria,
                   double preco,
                   int quantidade,
                   int estoqueMinimo,
                   String imagem) {

        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.imagem = imagem;
    }

    public String getStatus() {
        if (quantidade == 0) {
            return "Sem Estoque";
        }

        if (quantidade <= estoqueMinimo) {
            return "Estoque Baixo";
        }

        return "Normal";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }


    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}