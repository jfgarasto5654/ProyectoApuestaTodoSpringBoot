/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Ap.demo.exception;

/**
 *
 * @author Juan
 */
public class MontoInvalidoException extends RuntimeException{
    public MontoInvalidoException() {
        super("El monto debe ser mayor a 0");
    }
}
