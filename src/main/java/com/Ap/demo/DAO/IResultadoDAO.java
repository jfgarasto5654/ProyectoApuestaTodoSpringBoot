package com.Ap.demo.DAO;

import com.Ap.demo.logica.Resultado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IResultadoDAO extends CrudRepository<Resultado, Integer> {

    @Query(value = "SELECT id_resultado FROM resultado ORDER BY id_resultado DESC LIMIT 1", nativeQuery = true)
    Integer findLastResultadoId();
    
}