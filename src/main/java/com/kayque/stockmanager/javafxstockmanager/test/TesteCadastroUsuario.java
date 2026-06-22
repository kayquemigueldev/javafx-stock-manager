package com.kayque.stockmanager.javafxstockmanager.test;

import com.kayque.stockmanager.javafxstockmanager.dao.UsuarioDAO;
import com.kayque.stockmanager.javafxstockmanager.model.Usuario;

public class TesteCadastroUsuario {

    public static void main(String[] args) {

        Usuario usuario = new Usuario(
                "Administrador",
                "admin",
                "1234",
                "ADMIN"
        );

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.salvar(usuario)) {
            System.out.println("Usuário admin cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar usuário admin.");
        }
    }
}