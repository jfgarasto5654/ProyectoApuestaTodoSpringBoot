package com.Ap.demo.DAO;

import com.Ap.demo.logica.Resultado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IResultadoDAO extends CrudRepository<Resultado, Integer> {

}