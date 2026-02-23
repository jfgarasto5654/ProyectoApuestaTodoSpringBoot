package com.Ap.demo.exception;

public class PerfilNoEncontradoException extends RuntimeException {

    public PerfilNoEncontradoException(int idUsuario) {
        super("No se encontró el perfil para el usuario con id: " + idUsuario);
    }
}
