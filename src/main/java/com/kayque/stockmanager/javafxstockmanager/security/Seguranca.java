package com.kayque.stockmanager.javafxstockmanager.security;

import org.mindrot.jbcrypt.BCrypt;

public class Seguranca {

    public static String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean verificarSenha(String senhaDigitada, String senhaHash) {
        return BCrypt.checkpw(senhaDigitada, senhaHash);
    }
}