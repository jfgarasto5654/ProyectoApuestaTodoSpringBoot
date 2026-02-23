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

   // ============================================================
    // 1. FLUJO DEL ADMINISTRADOR (El Disparador Principal)
    // ============================================================
    @Transactional
    public void registrarResultadoYLiquidar(int idPartido, String ganador) {
    // 1. Validaciones
    validarPartidoExiste(idPartido);
    validarResultadoNoExiste(idPartido);
    validarGanador(ganador);

    // 2. CREAMOS LA VARIABLE 'resultado' 
    // Asegúrate de que los parámetros del constructor (id, ganador, fk_partido) sean correctos
    Resultado resultado = new Resultado(idPartido, ganador, idPartido);
    resultadoDAO.save(resultado); // Se guarda en la DB

    // 3. Obtenemos el partido
    Partido partido = partidoDAO.findById(idPartido)
            .orElseThrow(() -> new PartidoNoEncontradoException(idPartido));
    partido.setActivo(0);
    // 4. Buscamos las apuestas usando el nuevo método del DAO
    List<Apuesta> apuestas = apuestaDAO.buscarPorPartidoYEstado(idPartido, 'P');

    for (Apuesta apuesta : apuestas) {
        // Buscamos al usuario de la apuesta
        Usuario usuario = usuarioDAO.findById(apuesta.getFkIdUsuario())
                .orElseThrow(() -> new UsuarioNoAutenticadoException());
        
        // PASAMOS LA VARIABLE 'resultado' QUE CREAMOS ARRIBA
        aplicarResolucion(apuesta, usuario, partido, resultado);
    }
}

    @Transactional
        public List<Apuesta> procesarApuestasUsuario(Usuario usuario) {
            // CAMBIO AQUÍ: Usar el nombre del DAO
            // Antes: findByFkIdUsuario(...)
            List<Apuesta> apuestas = apuestaDAO.buscarPorUsuario(usuario.getId_usuario());

            for (Apuesta apuesta : apuestas) {
                if (apuesta.getEstado() == 'P') {
                    // Asegúrate de que getIdPartido() coincida con el nombre de tu atributo fk_id_partido
                    Optional<Resultado> resultadoOpt = resultadoDAO.findById(apuesta.getFk_id_partido());
                    Optional<Partido> partidoOpt = partidoDAO.findById(apuesta.getFk_id_partido());

                    if (resultadoOpt.isPresent() && partidoOpt.isPresent()) {
                        aplicarResolucion(apuesta, usuario, partidoOpt.get(), resultadoOpt.get());
                    }
                }
            }
            return apuestas;
        }

    // ============================================================
    // 3. MOTOR DE RESOLUCIÓN (Polimorfismo Puro)
    // ============================================================
    private void aplicarResolucion(Apuesta apuesta, Usuario usuario, Partido partido, Resultado resultado) {
    if (resultado.getGanador().trim().equalsIgnoreCase(apuesta.getpor_quien().trim())) {
        apuesta.setEstado('G');
        
        // 1. Calculamos ganancia
        double ganancia = partido.calcularGanancia(apuesta, resultado);
        
        // Debug: Mira esto en tu consola de IntelliJ/Eclipse
        System.out.println("DEBUG -> Ganancia calculada: " + ganancia);
        System.out.println("DEBUG -> Saldo anterior: " + usuario.getDinero());

        // 2. Sumamos y guardamos al usuario PRIMERO
        usuario.setDinero(usuario.getDinero() + ganancia);
        
        // Usamos saveAndFlush para obligar a la DB a escribir el cambio de dinero YA
        usuarioDAO.save(usuario);
        
        System.out.println("DEBUG -> Nuevo saldo en memoria: " + usuario.getDinero());
    } else {
        apuesta.setEstado('N');
    }
    apuestaDAO.save(apuesta);
}

    // ============================================================
    // 4. CREACIÓN Y VALIDACIONES
    // ============================================================
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
        // Asumiendo que tienes esta excepción creada
        throw new PartidoNoEncontradoException(idPartido); 
    }
}

private void validarResultadoNoExiste(int idPartido) {
    if (resultadoDAO.existsById(idPartido)) {
        // Si el resultado ya existe, no deberíamos permitir cargarlo de nuevo
        throw new PartidoFinalizadoException(); // O una específica: ResultadoYaCargadoException
    }
}

private void validarGanador(String ganador) {
    // Validamos que el string sea uno de los valores permitidos
    if (ganador == null || 
       (!ganador.equalsIgnoreCase("local") && 
        !ganador.equalsIgnoreCase("visitante") && 
        !ganador.equalsIgnoreCase("empate"))) {
        
        throw new MontoInvalidoException(); // O una nueva: FormatoGanadorInvalidoException
    }
}
}
