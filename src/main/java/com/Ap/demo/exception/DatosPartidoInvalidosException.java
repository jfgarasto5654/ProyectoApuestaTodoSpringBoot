package com.Ap.demo.exception;

public class DatosPartidoInvalidosException extends RuntimeException {

    public DatosPartidoInvalidosException() {
        super("Los datos del partido son inválidos");
    }
}
