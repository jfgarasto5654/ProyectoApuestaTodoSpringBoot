package com.Ap.demo.service;

import com.Ap.demo.DAO.IApuestaDAO;
import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.DAO.IResultadoDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.exception.MontoInvalidoException;
import com.Ap.demo.exception.PartidoFinalizadoException;
import com.Ap.demo.exception.PartidoNoEncontradoException;
import com.Ap.demo.exception.SaldoInsuficienteException;
import com.Ap.demo.exception.UsuarioNoAutenticadoException;
import com.Ap.demo.logica.Apuesta;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Resultado;
import com.Ap.demo.logica.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void registrarResultadoYLiquidar(int idPartido, String ganador) {
    // 1. Validaciones
    validarPartidoExiste(idPartido);
    validarResultadoNoExiste(idPartido);
    validarGanador(ganador);


    Resultado resultado = new Resultado(idPartido, ganador, idPartido);
    resultadoDAO.save(resultado); // Se guarda en la DB


    Partido partido = partidoDAO.findById(idPartido)
            .orElseThrow(() -> new PartidoNoEncontradoException(idPartido));
    partido.setActivo(0);

    List<Apuesta> apuestas = apuestaDAO.buscarPorPartidoYEstado(idPartido, 'P');

    for (Apuesta apuesta : apuestas) {

        Usuario usuario = usuarioDAO.findById(apuesta.getFkIdUsuario())
                .orElseThrow(() -> new UsuarioNoAutenticadoException());
        

        aplicarResolucion(apuesta, usuario, partido, resultado);
    }
}

    @Transactional
        public List<Apuesta> procesarApuestasUsuario(Usuario usuario) {

            List<Apuesta> apuestas = apuestaDAO.buscarPorUsuario(usuario.getId_usuario());

            for (Apuesta apuesta : apuestas) {
                if (apuesta.getEstado() == 'P') {
                    
                    Optional<Resultado> resultadoOpt = resultadoDAO.findById(apuesta.getFk_id_partido());
                    Optional<Partido> partidoOpt = partidoDAO.findById(apuesta.getFk_id_partido());

                    if (resultadoOpt.isPresent() && partidoOpt.isPresent()) {
                        aplicarResolucion(apuesta, usuario, partidoOpt.get(), resultadoOpt.get());
                    }
                }
            }
            return apuestas;
        }


    private void aplicarResolucion(Apuesta apuesta, Usuario usuario, Partido partido, Resultado resultado) {
    if (resultado.getGanador().trim().equalsIgnoreCase(apuesta.getpor_quien().trim())) {
        apuesta.setEstado('G');
        

        double ganancia = partido.calcularGanancia(apuesta, resultado);
        

        System.out.println("DEBUG -> Ganancia calculada: " + ganancia);
        System.out.println("DEBUG -> Saldo anterior: " + usuario.getDinero());


        usuario.setDinero(usuario.getDinero() + ganancia);
        

        usuarioDAO.save(usuario);
        
        System.out.println("DEBUG -> Nuevo saldo en memoria: " + usuario.getDinero());
    } else {
        apuesta.setEstado('N');
    }
    apuestaDAO.save(apuesta);
}


    @Transactional
    public Apuesta crearApuesta(Usuario usuario, int idPartido, int monto, String por) {
        validarTodo(usuario, monto, idPartido);

        Apuesta apuesta = new Apuesta(monto, por, usuario.getId_usuario(), idPartido, 'P', idPartido);
        usuario.setDinero(usuario.getDinero() - monto);
        
        apuestaDAO.save(apuesta);
        usuarioDAO.save(usuario);
        return apuesta;
    }

    private void validarTodo(Usuario usuario, int monto, int idPartido) {
        if (usuario == null) throw new UsuarioNoAutenticadoException();
        if (monto <= 0) throw new MontoInvalidoException();
        if (usuario.getDinero() < monto) throw new SaldoInsuficienteException();
        if (resultadoDAO.existsById(idPartido)) throw new PartidoFinalizadoException();
    }
    
 // ======================
// VALIDACIONES DE ADMIN
// ======================

private void validarPartidoExiste(int idPartido) {
    if (!partidoDAO.existsById(idPartido)) {
        throw new PartidoNoEncontradoException(idPartido); 
    }
}

private void validarResultadoNoExiste(int idPartido) {
    if (resultadoDAO.existsById(idPartido)) {
        // Si el resultado ya existe, no deberíamos permitir cargarlo de nuevo
        throw new PartidoFinalizadoException();
    }
}

private void validarGanador(String ganador) {
    // Validamos que el string sea uno de los valores permitidos
    if (ganador == null || 
       (!ganador.equalsIgnoreCase("local") && 
        !ganador.equalsIgnoreCase("visitante") && 
        !ganador.equalsIgnoreCase("empate"))) {
        
        throw new MontoInvalidoException();
    }
}
}
