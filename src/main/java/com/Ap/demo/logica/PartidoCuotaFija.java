
package com.Ap.demo.logica;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("FIJO")
public class PartidoCuotaFija extends Partido {

    @Column(name = "cuotaLocal")
    private Double cuotaLocal;

    @Column(name = "cuotaVisitante")
    private Double cuotaVisitante;


    // Constructor requerido por JPA
    public PartidoCuotaFija() {
    }

    public void setCuotaLocal(double cuotaLocal) {
        this.cuotaLocal = 2.0;
    }

    public void setCuotaVisitante(double cuotaVisitante) {
        this.cuotaVisitante = 2.0;
    }
    
    

    @Override
    public double calcularCuotaLocal(String local, String visitante) {
        return cuotaLocal != null ? cuotaLocal : 2.0;
    }

    @Override
    public double calcularCuotaVisitante(String local, String visitante) {
        return cuotaVisitante != null ? cuotaVisitante : 2.0;
    }


    }
