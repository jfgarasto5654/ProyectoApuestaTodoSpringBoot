/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.exception;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("credenciales incorrectas");
    }
}