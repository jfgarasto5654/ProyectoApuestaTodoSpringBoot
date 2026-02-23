/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.exception;

public class UsuarioYaExisteException extends RuntimeException {
    public UsuarioYaExisteException(String username) {
        super("El usuario '" + username + "' ya existe");
    }
}
