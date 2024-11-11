
package com.Ap.demo.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

    @Entity
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_usuario;
    private String usuario;
    private String contrasenia;
    private double dinero;
    private String rol;

    public int getId_usuario() {
        return id_usuario;
    }

    
    
    
}
