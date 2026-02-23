package com.Ap.demo.exception;

public class PartidoNoEncontradoException extends RuntimeException {

    public PartidoNoEncontradoException(int id) {
        super("No se encontró el partido con id: " + id);
    }
}
