package com.Ap.demo.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class Resultado implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_resultado;
    private String ganador;
    private int fk_id_partido;

    public void setIdResultado(int idResultado) {
        this.id_resultado = idResultado;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public void setIdPartido(int idPartido) {
        this.fk_id_partido = idPartido;
    }

    public int getIdResultado() {
        return id_resultado;
    }

    public String getGanador() {
        return ganador;
    }

    public int getIdPartido() {
        return fk_id_partido;
    }
}