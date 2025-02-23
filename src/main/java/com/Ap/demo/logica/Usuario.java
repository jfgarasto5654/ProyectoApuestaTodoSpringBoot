
package com.Ap.demo.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

    @Entity
public class Usuario implements Serializable{
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;
    private String usuario;
    private String contrasenia;
    private double dinero;
    private String rol;
    
    public Usuario() {
    }

    public Usuario(String usuario, String contrasenia, double dinero, String rol) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.dinero = dinero;
        this.rol = rol;
    }
    
    

    public int getId_usuario() {
        return id_usuario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id_usuario=" + id_usuario + ", usuario=" + usuario + ", contrasenia=" + contrasenia + ", dinero=" + dinero + ", rol=" + rol + '}';
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public String getRol() {
        return rol;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
}
