
package com.Ap.demo.DAO;

import com.Ap.demo.logica.Partido;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IPartidoDAO extends CrudRepository<Partido, Integer> {

    @Query("SELECT p FROM Partido p WHERE p.id >= :id")
    List<Partido> findPartidosDesdeId(@Param("id") Integer id);
}
