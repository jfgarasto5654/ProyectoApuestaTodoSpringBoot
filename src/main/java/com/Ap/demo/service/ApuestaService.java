package com.Ap.demo.service;

import com.Ap.demo.DAO.IApuestaDAO;
import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.DAO.IResultadoDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Ap.demo.logica.Apuesta;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Resultado;
import com.Ap.demo.logica.Usuario;

@Service
public class ApuestaService {

    @Autowired
    private IApuestaDAO apuestaDAO;

    @Autowired
    private IResultadoDAO resultadoDAO;

    @Autowired
    private IPartidoDAO partidoDAO;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Transactional
    public List<Apuesta> procesarApuestasUsuario(Usuario usuario) {

        List<Apuesta> apuestas =
                apuestaDAO.findByFkIdUsuario(usuario.getId_usuario());

        for (Apuesta apuesta : apuestas) {

            if (apuesta.getEstado() != 'P') continue;

            Optional<Resultado> resultadoOpt =
                    resultadoDAO.findById(apuesta.getIdPartido());

            Optional<Partido> partidoOpt =
                    partidoDAO.findById(apuesta.getIdPartido());

            if (resultadoOpt.isEmpty() || partidoOpt.isEmpty()) continue;

            Resultado resultado = resultadoOpt.get();
            Partido partido = partidoOpt.get();

            // Apuesta ganada
            if (resultado.getGanador().equals(apuesta.getpor_quien())) {

                apuesta.setEstado('G');

                double ganancia;

                if ("local".equals(resultado.getGanador())) {
                    ganancia = apuesta.getMonto() * partido.getCuotaLocal();
                } else {
                    ganancia = apuesta.getMonto() * partido.getCuotaVisitante();
                }

                usuario.setDinero(usuario.getDinero() + ganancia);
                usuarioDAO.save(usuario);

            } else {
                // Apuesta perdida
                apuesta.setEstado('N');
            }

            apuesta.setFk_id_partido(apuesta.getIdPartido());
            apuestaDAO.save(apuesta);
        }

        return apuestas;
    }
}
