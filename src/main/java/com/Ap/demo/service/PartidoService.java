package com.Ap.demo.service;

import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.exception.DatosPartidoInvalidosException;
import com.Ap.demo.exception.PartidoNoEncontradoException;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.PartidoCuotaFija;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PartidoService {

    private final IPartidoDAO partidoDAO;

    public PartidoService(IPartidoDAO partidoDAO) {
        this.partidoDAO = partidoDAO;
    }

    public List<Partido> obtenerPartidosActivos(Iterable<Partido> partidos) {
        List<Partido> partidosActivos = new ArrayList<>();

        for (Partido partido : partidos) {
            if (partido.getActivo() == 1 && partido.getId_partido() > 1) {
                partidosActivos.add(partido);
            }
        }
        return partidosActivos;
    }

    public Iterable<Partido> obtenerTodosPartidos() {
        return partidoDAO.findAll();
    }

    public Partido obtenerPorId(int id) {
        return partidoDAO.findById(id)
                .orElseThrow(() -> new PartidoNoEncontradoException(id));
    }

    public void eliminarPartido(int id) {
        Partido partido = obtenerPorId(id);
        partido.setActivo(0);
        partidoDAO.save(partido);
    }

    public void guardar(Partido partido) {
        partidoDAO.save(partido);
    }

    public void editarPartido(int id, String local, String visitante, String fecha) {
        validarDatos(local, visitante, fecha);

        Partido partido = obtenerPorId(id);

        partido.setLocal(local);
        partido.setVisitante(visitante);
        partido.setFecha(fecha);

        partidoDAO.save(partido);
    }

    public void agregarPartido(String local, String visitante, String fecha) {
        validarDatos(local, visitante, fecha);

        PartidoCuotaFija partido = new PartidoCuotaFija();
        partido.setLocal(local);
        partido.setVisitante(visitante);
        partido.setFecha(fecha);
        partido.setBalance(0);
        partido.setActivo(1);
        partido.setCuotaLocal(
                partido.calcularCuotaLocal(local, visitante)
        );
        partido.setCuotaVisitante(
                partido.calcularCuotaVisitante(local, visitante)
        );

        partidoDAO.save(partido);
    }

    // =====================
    // VALIDACIONES
    // =====================

    private void validarDatos(String local, String visitante, String fecha) {
        if (local == null || local.isBlank()
                || visitante == null || visitante.isBlank()
                || fecha == null || fecha.isBlank()) {
            throw new DatosPartidoInvalidosException();
        }
    }
}
