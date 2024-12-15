
package com.Ap.demo.DAO;

import com.Ap.demo.logica.Apuesta;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IApuestaDAO extends CrudRepository<Apuesta, Integer> {
    List<Apuesta> findByFkIdUsuario(int fk_id_usuario);
}

