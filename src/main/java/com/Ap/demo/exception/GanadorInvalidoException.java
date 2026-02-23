package com.Ap.demo.exception;

public class GanadorInvalidoException extends RuntimeException {

    public GanadorInvalidoException() {
        super("El ganador indicado no es válido");
    }
}
