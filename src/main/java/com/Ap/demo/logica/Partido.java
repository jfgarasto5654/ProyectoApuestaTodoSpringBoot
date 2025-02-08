
package com.Ap.demo.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class Partido implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_partido;
    private String local;
    private String visitante;
    private String fecha;
    private int balance;
    private int activo;

    public int getId_partido() {
        return id_partido;
    }

    public String getLocal() {
        return local;
    }

    public String getVisitante() {
        return visitante;
    }

    public String getFecha() {
        return fecha;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Partido{" + "id_partido=" + id_partido + ", local=" + local + ", visitante=" + visitante + ", fecha=" + fecha + ", balance=" + balance + '}';
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    

    
    
    
    
    
    
}
