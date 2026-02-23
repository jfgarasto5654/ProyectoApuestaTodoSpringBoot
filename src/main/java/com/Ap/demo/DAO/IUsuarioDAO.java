
package com.Ap.demo.DAO;

import com.Ap.demo.logica.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUsuarioDAO extends CrudRepository<Usuario, Integer> {
    Usuario findByUsuarioAndContrasenia(String username, String contrasenia);
    @Query(value = "SELECT id_usuario FROM usuario ORDER BY id_usuario DESC LIMIT 1", nativeQuery = true)
    int findLastUsuarioId();

    public boolean existsByUsuario(String usuario);

}
