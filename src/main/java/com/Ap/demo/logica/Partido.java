
package com.Ap.demo.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class Partido implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_partido;
    private String local;
    private String visitante;
    private String fecha;
    private int balance;

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
    
    
}
