/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.exception;

public class PasswordNoCoincideException extends RuntimeException {
    public PasswordNoCoincideException() {
        super("Las contraseñas no coinciden");
    }
}