package com.Ap.demo.DAO;

import com.Ap.demo.logica.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPersonaDAO extends CrudRepository<Persona, Integer> {

}
