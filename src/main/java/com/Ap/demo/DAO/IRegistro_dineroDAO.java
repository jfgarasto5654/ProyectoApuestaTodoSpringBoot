
package com.Ap.demo.DAO;

import com.Ap.demo.logica.Apuesta;
import com.Ap.demo.logica.Registro_dinero;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRegistro_dineroDAO extends CrudRepository<Registro_dinero, Integer> {
        List<Registro_dinero> findAllByFkIdUsuario(int fkIdUsuario);
}

