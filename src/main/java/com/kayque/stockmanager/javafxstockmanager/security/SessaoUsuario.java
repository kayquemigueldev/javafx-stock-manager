package com.kayque.stockmanager.javafxstockmanager.security;

import com.kayque.stockmanager.javafxstockmanager.model.Usuario;

public class SessaoUsuario {

    private static Usuario usuarioLogado;

    public static void iniciarSessao(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static boolean isAdmin() {
        return usuarioLogado != null
                && "ADMIN".equalsIgnoreCase(usuarioLogado.getPerfil());
    }

    public static boolean isFuncionario() {
        return usuarioLogado != null
                && "FUNCIONARIO".equalsIgnoreCase(usuarioLogado.getPerfil());
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }
}