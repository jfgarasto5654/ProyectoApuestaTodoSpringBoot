
package com.Ap.demo.DAO;

import com.Ap.demo.logica.Apuesta;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IApuestaDAO extends CrudRepository<Apuesta, Integer> {
    List<Apuesta> findByFkIdUsuario(int fk_id_usuario);


    // Buscamos por el atributo real: fk_id_partido
    @Query("SELECT a FROM Apuesta a WHERE a.fk_id_partido = :idPartido")
    List<Apuesta> buscarPorPartido(@Param("idPartido") int idPartido);

    // Este es el que necesitamos para el Administrador (Filtra por partido y estado Pendiente)
    @Query("SELECT a FROM Apuesta a WHERE a.fk_id_partido = :idPartido AND a.estado = :estado")
    List<Apuesta> buscarPorPartidoYEstado(@Param("idPartido") int idPartido, @Param("estado") char estado);

    // Para el usuario
    @Query("SELECT a FROM Apuesta a WHERE a.fkIdUsuario = :idUsuario")
    List<Apuesta> buscarPorUsuario(@Param("idUsuario") int idUsuario);
}

