package com.Ap.demo.exception;

public class ResultadoYaExisteException extends RuntimeException {

    public ResultadoYaExisteException(int idPartido) {
        super("El partido con id " + idPartido + " ya tiene resultado cargado");
    }
}
