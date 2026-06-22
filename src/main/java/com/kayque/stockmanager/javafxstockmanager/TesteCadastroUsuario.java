package com.kayque.stockmanager.javafxstockmanager;

public class TesteCadastroUsuario {

    public static void main(String[] args) {

        Usuario usuario = new Usuario(
                "Kayque",
                "kayque",
                "1234",
                "ADMIN"
        );

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.salvar(usuario)) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar usuário.");
        }
    }
}