
package com.Ap.demo.controller;


import com.Ap.demo.logica.Apuesta;
import com.Ap.demo.DAO.IApuestaDAO;
import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.DAO.IResultadoDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Resultado;
import com.Ap.demo.logica.Usuario;
import com.Ap.demo.service.ApuestaService;
import com.Ap.demo.service.PartidoService;
import com.Ap.demo.service.ResultadoService;
import com.Ap.demo.service.SesionService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller

public class ApuestaController {
    

     @Autowired
     private ApuestaService apuestaService;
     @Autowired
     private PartidoService partidoservice;
     @Autowired
     private SesionService sesionservice;
     @Autowired
     private ResultadoService resultadoservice;

     
     

     @GetMapping("/Apuesta/{id}")
            public String mostrarApuesta(
                    @PathVariable("id") int partidoId,
                    Model model,
                    HttpSession session
) {

            Usuario usuario = sesionservice.obtenerUsuario(session);
            model.addAttribute("userLogueado", usuario);

            if (usuario == null) {
                model.addAttribute("errorMessage", "Inicie sesión para apostar");
                model.addAttribute("partidos", partidoservice.obtenerPartidosActivos(partidoservice.obtenerTodosPartidos()));
                return "partidosmostrar";
            }

            if (resultadoservice.partidoFinalizado(partidoId)) {
                model.addAttribute("errorMessage", "Partido finalizado");
                model.addAttribute("partidos", partidoservice.obtenerPartidosActivos(partidoservice.obtenerTodosPartidos()));
                model.addAttribute("resultadosMap", resultadoservice.obtenerResultadosMap());
                model.addAttribute("resultados", resultadoservice.obtenerTodos());
                return "partidosmostrar";
            }

            Partido partido = partidoservice.obtenerPorId(partidoId);
            model.addAttribute("partido", partido);

            return "apuesta";
        }

        
     
     @GetMapping("/ApuestasUsuario")
public String apuestasUsuario(Model model, HttpSession session) {

    Usuario usuario = (Usuario) session.getAttribute("userLogueado");

    List<Apuesta> apuestas =
            apuestaService.procesarApuestasUsuario(usuario);

    model.addAttribute("apuestas", apuestas);
    model.addAttribute("userLogueado", usuario);
    model.addAttribute("partidos", partidoservice.obtenerTodosPartidos());

    return "mostrarApuestas";
}

    
    @PostMapping("/procesarApuesta")
        public String procesarApuesta(
                Model model,
                @RequestParam int monto,
                @RequestParam String por,
                @RequestParam int idPartido,
                HttpSession session
        ) {

            Usuario usuario = sesionservice.obtenerUsuario(session);

            try {
                Apuesta apuesta =
                        apuestaService.crearApuesta(usuario, idPartido, monto, por);

                model.addAttribute("apuesta", apuesta);
                model.addAttribute("userLogueado", usuario);
                model.addAttribute("partido", partidoservice.obtenerPorId(idPartido));

                return "apuestaCreada";

            } catch (RuntimeException e) {
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("partido",
                        partidoservice.obtenerPorId(idPartido));
                model.addAttribute("userLogueado", usuario);
                return "apuesta";
            }
        }

            }
