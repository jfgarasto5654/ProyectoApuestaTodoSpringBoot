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

    @Autowired
    private IPartidoDAO partidoDAO;
    @Autowired
    private IResultadoDAO resultadoDAO;
    
    public Usuario obtenerSesion (HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");        
        return usuario;
    }
    
         public List<Partido> obtenerPartidosActivos(Iterable<Partido> partidos){
         List<Partido> partidosActivos = new ArrayList<>();
         for (Partido partidoact : partidos) {
                if (partidoact.getActivo() == 1 && partidoact.getId_partido() > 1) {
                    partidosActivos.add(partidoact);
                    }
                }
         return partidosActivos;
     }

    @GetMapping("/Admin/ShowAgregarPartido")
    public String showagregarpartido (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);             
        return "agregarpartido";
    }
    
    @GetMapping("/Admin/ShowPaginaEdicion/{id}")
    public String showPaginaEdicion (HttpSession session, Model model, @PathVariable("id") int partidoId,  @RequestParam String accion){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario); 
        Partido partido = partidoDAO.findById(partidoId).orElse(null);
         model.addAttribute("partido", partido);
        return "editarpartido";
    }

    
     @GetMapping("/Admin/VistaPartidosAdmin")
    public String partidosadmin (HttpSession session, Model model, @RequestParam("accion") String accion){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
 
        Iterable<Partido> partidos = partidoDAO.findAll();
        List<Partido> partidosActivos = new ArrayList<>();
        
        partidosActivos = obtenerPartidosActivos(partidos);
         model.addAttribute("partidos", partidosActivos);
         model.addAttribute("accion", accion);
        return "partidosmostrarAdmin";
    }
    
    @GetMapping("/Admin/EliminarPartido/{id}")
    public String partidosadmin (HttpSession session, Model model, @PathVariable("id") int partidoId,  @RequestParam String accion){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
 
        Partido partido = partidoDAO.findById(partidoId).orElse(null);
        partido.setActivo(0);
        partidoDAO.save(partido);
        Iterable<Partido> partidos = partidoDAO.findAll();
        List<Partido> partidosActivos = new ArrayList<>();
        
        partidosActivos = obtenerPartidosActivos(partidos);
         model.addAttribute("partidos", partidosActivos);
         model.addAttribute("accion", accion);
        return "partidosmostrarAdmin";
    }
    
    
    
    @PostMapping("/Admin/editarPartido")
    public String editarPartido(Model model, @RequestParam("local") String local,
                                 @RequestParam("visitante") String visitante,
                                 @RequestParam("fecha") String fecha, 
                                 @RequestParam("id") int partidoId,HttpSession session) {
        
        Partido partido = new PartidoCuotaFija();
        partido.setLocal(local);
        partido.setVisitante(visitante);
        partido.setFecha(fecha);
        partido.setBalance(0);
        partido.setId_partido(partidoId);
        partido.setActivo(1);

        Iterable<Partido> partidos = partidoDAO.findAll();
        
        List<Partido> partidosActivos = new ArrayList<>();
        
        partidosActivos = obtenerPartidosActivos(partidos);
            
        model.addAttribute("partidos", partidosActivos);
        Usuario usuario = obtenerSesion(session);
        model.addAttribute("userLogueado", usuario);
        partidoDAO.save(partido);
        
        return "partidosmostrar";
    }
    
     @PostMapping("/Admin/agregarPartido")
    public String agregarPartido(Model model, @RequestParam("local") String local,
                                 @RequestParam("visitante") String visitante,
                                 @RequestParam("fecha") String fecha, HttpSession session) {
        // Verifica que no se reciban valores nulos
        if (local == null || local.isEmpty() || visitante == null || visitante.isEmpty() || fecha == null || fecha.isEmpty()) {
            // Maneja el error si alguno de los campos está vacío
            return "error"; // Puedes redirigir a una página de error
        }

        // Crear el objeto Partido y guardarlo
        PartidoCuotaFija partido = new PartidoCuotaFija();
        partido.setLocal(local);
        partido.setVisitante(visitante);
        partido.setFecha(fecha);
        partido.setBalance(0);
        partido.setActivo(1);
        partido.setCuotaLocal(partido.calcularCuotaLocal(local, visitante));
        partido.setCuotaVisitante(partido.calcularCuotaVisitante(local, visitante));

        partidoDAO.save(partido);
        
        Iterable<Partido> partidos = partidoDAO.findAll();
        
        List<Partido> partidosActivos = new ArrayList<>();
        
        partidosActivos = obtenerPartidosActivos(partidos);

        Usuario usuario = obtenerSesion(session);
        model.addAttribute("userLogueado", usuario);
        model.addAttribute("partidos", partidosActivos);

        return "partidosmostrar"; // Redirige a la página de confirmación
    }
    
    @GetMapping("/partidos")
    public String partidos (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
        
        Iterable<Partido> partidos = partidoDAO.findAll();
        List<Partido> partidosActivos = new ArrayList<>();
        partidosActivos = obtenerPartidosActivos(partidos);
        model.addAttribute("partidos", partidosActivos);
        
        Iterable<Resultado> resultados = resultadoDAO.findAll();
       
       List<Resultado> listaResultados = new ArrayList<>();
        resultados.forEach(listaResultados::add);
       
       Map<Integer, Resultado> mapaResultados = listaResultados.stream()
        .collect(Collectors.toMap(Resultado::getIdPartido, resultado -> resultado));
        
        model.addAttribute("resultadosMap", mapaResultados);
        model.addAttribute("resultados", resultados);
        
        return "partidosmostrar";
    }
    
}