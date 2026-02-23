package com.Ap.demo.exception;

public class UsuarioNoAutenticadoException extends RuntimeException {

    public UsuarioNoAutenticadoException() {
        super("Debe iniciar sesión");
    }
}
