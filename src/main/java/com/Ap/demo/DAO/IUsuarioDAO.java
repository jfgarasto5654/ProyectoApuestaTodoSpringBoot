
package com.Ap.demo.DAO;

import com.Ap.demo.logica.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUsuarioDAO extends CrudRepository<Usuario, Integer> {
    Usuario findByUsuarioAndContrasenia(String username, String contrasenia);
}
