/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.exception;


public class PersonaNoEncontradaException extends RuntimeException {
    public PersonaNoEncontradaException(int idUsuario) {
        super("No se encontró la persona para el usuario con id: " + idUsuario);
    }
}
