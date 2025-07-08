package com.Ap.demo.logica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class Registro_dinero implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_registro;
    private double monto;
    private String tipo;
    @Column(name = "fk_id_usuario")
    private int fkIdUsuario;

    
    public Registro_dinero (){}

    public Registro_dinero(double monto, String tipo, int fkIdUsuario) {
        this.monto = monto;
        this.tipo = tipo;
        this.fkIdUsuario = fkIdUsuario;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }

    public int getFkIdUsuario() {
        return fkIdUsuario;
    }
    
    
}