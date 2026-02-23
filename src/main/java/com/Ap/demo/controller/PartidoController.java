/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.controller;

import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.PartidoCuotaFija;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.DAO.IResultadoDAO;
import com.Ap.demo.logica.Resultado;
import com.Ap.demo.logica.Usuario;
import com.Ap.demo.service.PartidoService;
import com.Ap.demo.service.ResultadoService;
import com.Ap.demo.service.SesionService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PartidoController {

    private final PartidoService partidoService;
    private final ResultadoService resultadoService;
    private final SesionService sesionService;
    
    public PartidoController(
            PartidoService partidoService,
            ResultadoService resultadoService,
            SesionService sesionService
    ) {
        this.partidoService = partidoService;
        this.resultadoService = resultadoService;
        this.sesionService = sesionService;
    }

    @GetMapping("/Admin/ShowAgregarPartido")
    public String showagregarpartido (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);             
        return "agregarpartido";
    }
    
    @GetMapping("/Admin/ShowPaginaEdicion/{id}")
    public String editar(@PathVariable int id, Model model, HttpSession session) {
        model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
        model.addAttribute("partido", partidoService.obtenerPorId(id));
        return "editarpartido";
    }
    
    @GetMapping("/Admin/VistaPartidosAdmin")
        public String partidosadmin(HttpSession session, Model model,
                                    @RequestParam("accion") String accion) {

            model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
            model.addAttribute("partidos", partidoService.obtenerPartidosActivos(partidoService.obtenerTodosPartidos()));
            model.addAttribute("accion", accion);

            return "partidosmostrarAdmin";
        }

    
    @GetMapping("/Admin/EliminarPartido/{id}")
        public String eliminarPartido(HttpSession session, Model model,
                                      @PathVariable int id,
                                      @RequestParam String accion) {

            partidoService.eliminarPartido(id);

            model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
            model.addAttribute("partidos", partidoService.obtenerPartidosActivos(partidoService.obtenerTodosPartidos()));
            model.addAttribute("accion", accion);

            return "partidosmostrarAdmin";
        }

    
        @PostMapping("/Admin/editarPartido")
        public String editarPartido(@RequestParam String local,
                                    @RequestParam String visitante,
                                    @RequestParam String fecha,
                                    @RequestParam int id,
                                    Model model,
                                    HttpSession session) {

            partidoService.editarPartido(id, local, visitante, fecha);

            model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
            model.addAttribute("partidos", partidoService.obtenerPartidosActivos(partidoService.obtenerTodosPartidos()));

            return "partidosmostrar";
        }

    
     @PostMapping("/Admin/agregarPartido")
        public String agregarPartido(@RequestParam String local,
                                     @RequestParam String visitante,
                                     @RequestParam String fecha,
                                     Model model,
                                     HttpSession session) {

            partidoService.agregarPartido(local, visitante, fecha);

            model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
            model.addAttribute("partidos", partidoService.obtenerPartidosActivos(partidoService.obtenerTodosPartidos()));

            return "partidosmostrar";
        }

    
    @GetMapping("/partidos")
        public String partidos(HttpSession session, Model model) {

            model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
            model.addAttribute("partidos", partidoService.obtenerPartidosActivos(partidoService.obtenerTodosPartidos()));
            model.addAttribute("resultadosMap", resultadoService.obtenerResultadosMap());

            return "partidosmostrar";
        }

    
}