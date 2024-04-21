
package com.Ap.demo.DAO;

import com.Ap.demo.logica.Partido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPartidoDAO extends CrudRepository<Partido, Integer> {
    // Métodos adicionales si los necesitas
}
