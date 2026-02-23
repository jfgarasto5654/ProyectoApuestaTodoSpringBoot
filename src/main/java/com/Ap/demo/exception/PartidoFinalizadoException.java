package com.Ap.demo.exception;

public class PartidoFinalizadoException extends RuntimeException {

    public PartidoFinalizadoException() {
        super("El partido ya finalizó");
    }
}
