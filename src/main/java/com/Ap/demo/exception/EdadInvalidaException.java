/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.exception;

public class EdadInvalidaException extends RuntimeException {
    public EdadInvalidaException() {
        super("La edad debe ser mayor o igual a 18");
    }
}
