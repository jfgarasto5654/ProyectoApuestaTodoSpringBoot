
package com.Ap.demo.DAO;

import com.Ap.demo.logica.Apuesta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IApuestaDAO extends CrudRepository<Apuesta, Integer> {
    // Métodos adicionales si los necesitas
}

