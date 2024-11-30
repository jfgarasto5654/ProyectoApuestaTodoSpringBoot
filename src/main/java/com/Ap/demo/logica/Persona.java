
package com.Ap.demo.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class Persona implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private int id_persona;
    private String dni;
    private String nombre;
    private String apellido;
    private int edad;
    private int fk_id_usuario;
    

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Persona{" + "id_persona=" + id_persona + ", dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", fk_id_ususario=" + fk_id_usuario + '}';
    }
    
    
}
