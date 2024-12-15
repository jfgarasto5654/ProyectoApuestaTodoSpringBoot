package com.Ap.demo.logica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;


@Entity
public class Apuesta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idApuesta;
    private int monto;
    private String por_quien;
    @Column(name = "fk_id_usuario")
    private int fkIdUsuario;
    private int fk_id_partido;
    private char estado;
    private int fk_id_resultado;

    public Apuesta() {
    }
    
    public Apuesta(String local, String visitante, String fecha, int monto, String por_quien) {
        this.monto = monto;
        this.por_quien = por_quien;
    }
    
    public Apuesta(int monto, String por_quien, int idUsuario, int idPartido, int fk_id_resultado) {
        this.monto = monto;
        this.por_quien = por_quien;
        this.fkIdUsuario = idUsuario;
        this.fk_id_partido = idPartido;
        this.fk_id_resultado = fk_id_resultado;
    }

    public Apuesta(int monto, int idPartido, String local, String visitante) {
        this.monto = monto;
        this.fk_id_partido = idPartido;
    }

    public Apuesta(int monto, String por_quien, int idUsuario, int idPartido, char estado, int fk_id_resultado) {
        this.monto = monto;
        this.por_quien = por_quien;
        this.fkIdUsuario = idUsuario;
        this.fk_id_partido  = idPartido;
        this.estado = estado;
        this.fk_id_resultado = fk_id_resultado;
    }
    

    public int getFk_id_resultado() {
        return fk_id_resultado;
    }

    public void setFk_id_resultado(int fk_id_resultado) {
        this.fk_id_resultado = fk_id_resultado;
    }
    
    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    public int getIdApuesta() {
        return idApuesta;
    }

    public void setFk_id_partido(int fk_id_partido) {
        this.fk_id_partido = fk_id_partido;
    }

    public int getMonto() {
        return monto;
    }

    public String getpor_quien() {
        return por_quien;
    }

    public int getIdUsuario() {
        return fkIdUsuario;
    }

    public int getIdPartido() {
        return fk_id_partido ;
    }

    public void setIdApuesta(int idApuesta) {
        this.idApuesta = idApuesta;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public void setPor_quien(String por_quien) {
        this.por_quien = por_quien;
    }

    public void setIdUsuario(int idUsuario) {
        this.fkIdUsuario = idUsuario;
    }

    public void setIdPartido(int idPartido) {
        this.fk_id_partido  = idPartido;
    }

    @Override
    public String toString() {
        return "Apuesta{" + "idApuesta=" + idApuesta + ", monto=" + monto + ", por_quien=" + por_quien + ", idUsuario=" + fkIdUsuario + ", idPartido=" + fk_id_partido  + ", estado=" + estado + ", fk_id_resultado=" + fk_id_resultado + '}';
    }


}