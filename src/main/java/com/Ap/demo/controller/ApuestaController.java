/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.controller;


import com.Ap.demo.logica.Apuesta;
import com.Ap.demo.DAO.IApuestaDAO;
import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.DAO.IResultadoDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Resultado;
import com.Ap.demo.logica.Usuario;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller

public class ApuestaController {
    
     @Autowired
     private IResultadoDAO resultadoDAO;
     @Autowired
     private IPartidoDAO partidoDAO;
     @Autowired
     private IUsuarioDAO usuarioDAO;
     @Autowired
     private IApuestaDAO apuestaDAO;
     
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

     @GetMapping("/Apuesta")
     public String mostrarApuesta(@RequestParam("id") int partidoId, Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
        if(usuario == null){
        model.addAttribute("errorMessage", "Inicie sesion para apostar");
        Iterable<Partido> partidos = partidoDAO.findAll();
        
        List<Partido> partidosActivos = new ArrayList<>();
        
        partidosActivos = obtenerPartidosActivos(partidos);
        
        model.addAttribute("partidos", partidosActivos);
        return "partidosmostrar"; 
        }
        
        Optional<Resultado> resultado = resultadoDAO.findById(partidoId);
        if(!resultado.isPresent()){
         Partido partido = partidoDAO.findById(partidoId).orElse(null);
        System.out.println("Local: " + partido.getLocal());
            System.out.println("Visitante: " + partido.getVisitante());
            System.out.println("Fecha: " + partido.getFecha());
            System.out.println("--------------------");
        model.addAttribute("partido", partido);   
        return "apuesta"; 
        } else {
            model.addAttribute("errorMessage", "Partido finalizado");
            int iderror = partidoId;
            model.addAttribute("iderror", "iderror");
            Iterable<Partido> partidos = partidoDAO.findAll();
            model.addAttribute("partidos", partidos);
            return "partidosmostrar"; 
        }
    }
     
     @GetMapping("/ApuestasUsuario")
    public String apuestasUsuario(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
     
         Iterable<Resultado> resultados = resultadoDAO.findAll();
  
         List<Apuesta> apuestas = apuestaDAO.findByFkIdUsuario(usuario.getId_usuario());

         for(Apuesta apuesta: apuestas){
              Optional<Resultado> resultadoe = resultadoDAO.findById(apuesta.getIdPartido());
             
             if(resultadoe.isPresent()){
                 Resultado resultado = resultadoe.get();
              if("local".equals(resultado.getGanador()) && "local".equals(apuesta.getpor_quien()) && apuesta.getEstado() == 'P'){
                  apuesta.setEstado('G');
                  apuesta.setFk_id_partido(apuesta.getIdPartido());
                  double dineroUser = usuario.getDinero();
                  dineroUser = dineroUser + apuesta.getMonto()*2;
                  usuario.setDinero(dineroUser);
                  usuarioDAO.save(usuario);
                  apuestaDAO.save(apuesta);
              }
              else if("visitante".equals(resultado.getGanador()) && "visitante".equals(apuesta.getpor_quien()) && apuesta.getEstado() == 'P'){
                  apuesta.setEstado('G');
                  apuesta.setFk_id_partido(apuesta.getIdPartido());
                  double dineroUser = usuario.getDinero();
                  dineroUser = dineroUser + apuesta.getMonto()*2;
                  usuario.setDinero(dineroUser);
                  usuarioDAO.save(usuario);
                  apuestaDAO.save(apuesta);
              }
              else if (apuesta.getEstado() == 'P'){
                  apuesta.setEstado('N');
                  apuestaDAO.save(apuesta);
              }
             }
         }
         model.addAttribute("apuestas", apuestas);
         model.addAttribute("userLogueado", usuario);
         Iterable<Partido> partidos = partidoDAO.findAll();
         
         List<Partido> partidosActivos = new ArrayList<>();
        
         partidosActivos = obtenerPartidosActivos(partidos);
         model.addAttribute("partidos", partidosActivos);
         
         apuestas.forEach(a -> System.out.println("Apuesta: " + a.getIdPartido()));
         partidos.forEach(p -> System.out.println("Partido: " + p.getId_partido()));
         return "mostrarApuestas"; 
    }
    
    @PostMapping("/procesarApuesta")
    public String procesarapuesta (Model model, @RequestParam("monto") int monto,
            @RequestParam("por") String por,
            @RequestParam("idPartido") int idPartido, HttpSession session){
        
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        if(usuario.getDinero()< monto || monto<0){
            
            Partido partido = partidoDAO.findById(idPartido).orElse(null);
            model.addAttribute("userLogueado", usuario);
            model.addAttribute("errorMessage", "Apuesta invalida, saldo insuficiente o menor a 0");
            model.addAttribute("partido", partido);
            return "apuesta";
        }else{
        
        int idUsuario = usuario.getId_usuario();
        Optional<Resultado> resultado = resultadoDAO.findById(idPartido);
        if(!resultado.isPresent()){
            Apuesta apuesta = new Apuesta(monto,por, idUsuario,idPartido, 'P', 1);
            apuestaDAO.save(apuesta);
            model.addAttribute("apuesta", apuesta);
            Partido partido = partidoDAO.findById(idPartido).orElse(null);
            model.addAttribute("partido", partido);
            int premio = apuesta.getMonto()*2;
            model.addAttribute(premio);        
        }
        else{
            Apuesta apuesta = new Apuesta(monto,por, idUsuario,idPartido, 'P', idPartido);
            apuesta.toString();
            apuestaDAO.save(apuesta);
            model.addAttribute("apuesta", apuesta);
            System.out.println("el id de partido es: " + idPartido );
            Partido partido = partidoDAO.findById(idPartido).orElse(null);
            System.out.println(partido.toString());
            System.out.println(apuesta.toString());
            model.addAttribute("partido", partido);
            int premio = apuesta.getMonto()*2;
            model.addAttribute(premio);
        }
        
        usuario.setDinero(usuario.getDinero()- monto);
        model.addAttribute("userlogueado", usuario);
        usuarioDAO.save(usuario);
        
        return "apuestaCreada";
        }
    }

}
