package com.Ap.demo.service;

import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.DAO.IResultadoDAO;
import com.Ap.demo.exception.GanadorInvalidoException;
import com.Ap.demo.exception.PartidoNoEncontradoException;
import com.Ap.demo.exception.ResultadoYaExisteException;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Resultado;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class ResultadoService {

    private final IResultadoDAO resultadoDAO;
    private final IPartidoDAO partidoDAO;

    public ResultadoService(IResultadoDAO resultadoDAO, IPartidoDAO partidoDAO) {
        this.resultadoDAO = resultadoDAO;
        this.partidoDAO = partidoDAO;
    }

    public boolean partidoFinalizado(int idPartido) {
        return resultadoDAO.existsById(idPartido);
    }

    public Map<Integer, Resultado> obtenerResultadosMap() {
        return StreamSupport.stream(resultadoDAO.findAll().spliterator(), false)
                .collect(Collectors.toMap(Resultado::getIdPartido, r -> r));
    }

    public Iterable<Resultado> obtenerTodos() {
        return resultadoDAO.findAll();
    }

    public List<Partido> obtenerPartidosSinResultado() {
    // 1. Seguimos obteniendo los que ya tienen resultado cargado
    Set<Integer> idsConResultado = StreamSupport.stream(resultadoDAO.findAll().spliterator(), false)
            .map(Resultado::getIdPartido)
            .collect(Collectors.toSet());

    return StreamSupport.stream(partidoDAO.findAll().spliterator(), false)
            .filter(p -> p.getActivo()== 1) // <--- AGREGAR ESTO: Solo los abiertos
            .filter(p -> !idsConResultado.contains(p.getId_partido()))
            .collect(Collectors.toList());
}

    public void agregarResultado(int idPartido, String ganador) {

        validarPartidoExiste(idPartido);
        validarResultadoNoExiste(idPartido);
        validarGanador(ganador);

        Resultado resultado = new Resultado(idPartido, ganador, idPartido);
        resultadoDAO.save(resultado);
    }

    // =====================
    // VALIDACIONES
    // =====================

    private void validarPartidoExiste(int idPartido) {
        if (!partidoDAO.existsById(idPartido)) {
            throw new PartidoNoEncontradoException(idPartido);
        }
    }

    private void validarResultadoNoExiste(int idPartido) {
        if (resultadoDAO.existsById(idPartido)) {
            throw new ResultadoYaExisteException(idPartido);
        }
    }

    private void validarGanador(String ganador) {
        if (ganador == null
                || (!ganador.equals("local") && !ganador.equals("visitante"))) {
            throw new GanadorInvalidoException();
        }
    }

    public Partido obtenerPartidoPorId(int id) {
    // Usamos el DAO aquí adentro
    // .orElse(null) hace que si no lo encuentra, devuelva null en lugar de un Optional vacío
    return partidoDAO.findById(id).orElse(null);
}
}
