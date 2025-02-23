/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.controller;

import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.logica.Usuario;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PartidoController {

    @Autowired
    private IPartidoDAO partidoDAO;
    
    public Usuario obtenerSesion (HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");        
        return usuario;
    }

    @GetMapping("/ShowAgregarPartido")
    public String showagregarpartido (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);             
        return "agregarpartido";
    }
    
    @GetMapping("/ShowPaginaEdicion")
    public String showPaginaEdicion (HttpSession session, Model model, @RequestParam("id") int partidoId){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario); 
        Partido partido = partidoDAO.findById(partidoId).orElse(null);
         model.addAttribute("partido", partido);
        return "editarpartido";
    }
    
    @GetMapping("/ShowEliminarPartido")
    public String showEliminarPartido (HttpSession session, Model model){
        Usuario usuario = obtenerSesion(session);
        model.addAttribute("userLogueado", usuario); 
        Iterable<Partido> partidos = partidoDAO.findAll();
         model.addAttribute("partidos", partidos);
        return "partidosmostrar_elim";
    }
    
     @GetMapping("/EliminarPartido")
    public String eliminarPartido (HttpSession session, Model model, @RequestParam("id") int partidoId){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
 
        Partido partido = partidoDAO.findById(partidoId).orElse(null);
        partido.setActivo(0);
        partidoDAO.save(partido);
        Iterable<Partido> partidos = partidoDAO.findAll();
         model.addAttribute("partidos", partidos);
        return "partidosmostrar";
    }
    
    @GetMapping("/ShowListaEditar")
    public String showListaEditar (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);        
        Iterable<Partido> partidos = partidoDAO.findAll();
        List<Partido> partidosActivos = new ArrayList<>();
        
            for (Partido partidoact : partidos) {
                if (partidoact.getActivo() == 1 && partidoact.getId_partido() > 1) {
                    partidosActivos.add(partidoact);
                    }
                }
        model.addAttribute("partidos", partidosActivos);
        return "partidosmostrar_edit";
    }
    
    @PostMapping("/editarPartido")
    public String editarPartido(Model model, @RequestParam("local") String local,
                                 @RequestParam("visitante") String visitante,
                                 @RequestParam("fecha") String fecha, 
                                 @RequestParam("id") int partidoId,HttpSession session) {
        
        Partido partido = new Partido();
        partido.setLocal(local);
        partido.setVisitante(visitante);
        partido.setFecha(fecha);
        partido.setBalance(0);
        partido.setId_partido(partidoId);
        partido.setActivo(1);

        Iterable<Partido> partidos = partidoDAO.findAll();
        
         List<Partido> partidosActivos = new ArrayList<>();
        
            for (Partido partidoact : partidos) {
                if (partidoact.getActivo() == 1 && partidoact.getId_partido() > 1) {
                    partidosActivos.add(partidoact);
                    }
                }
            
        model.addAttribute("partidos", partidosActivos);
        Usuario usuario = obtenerSesion(session);
        model.addAttribute("userLogueado", usuario);
        partidoDAO.save(partido);
        
        return "partidosmostrar"; // Redirige a una página donde muestres los partidos
    }
    
     @PostMapping("/agregarPartido")
    public String agregarPartido(Model model, @RequestParam("local") String local,
                                 @RequestParam("visitante") String visitante,
                                 @RequestParam("fecha") String fecha, HttpSession session) {
        // Verifica que no se reciban valores nulos
        if (local == null || local.isEmpty() || visitante == null || visitante.isEmpty() || fecha == null || fecha.isEmpty()) {
            // Maneja el error si alguno de los campos está vacío
            return "error"; // Puedes redirigir a una página de error
        }

        // Crear el objeto Partido y guardarlo
        Partido partido = new Partido();
        partido.setLocal(local);
        partido.setVisitante(visitante);
        partido.setFecha(fecha);
        partido.setBalance(0);
        partido.setActivo(1);

        partidoDAO.save(partido);
        
        Iterable<Partido> partidos = partidoDAO.findAll();
        
        List<Partido> partidosActivos = new ArrayList<>();
        
            for (Partido partidoact : partidos) {
                if (partidoact.getActivo() == 1 && partidoact.getId_partido() > 1) {
                    partidosActivos.add(partidoact);
                    }
                }

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
        
        /*for (Partido partido : partidos) {
            System.out.println("Local: " + partido.getLocal());
            System.out.println("Visitante: " + partido.getVisitante());
            System.out.println("Fecha: " + partido.getFecha());
            System.out.println("--------------------");
        }*/
        
        List<Partido> partidosActivos = new ArrayList<>();
        for (Partido partidoact : partidos) {
                if (partidoact.getActivo() == 1) {
                    partidosActivos.add(partidoact);
                    }
                }
        
        model.addAttribute("partidos", partidosActivos);
        
        return "partidosmostrar";
    }
    
}