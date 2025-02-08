/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.controller;

import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.logica.Resultado;
import com.Ap.demo.DAO.IResultadoDAO;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Usuario;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultadoController {

    @Autowired
    private IResultadoDAO resultadoDAO;
    @Autowired
    private IPartidoDAO partidoDAO;
    
    @GetMapping("/Resultados")
    public String resultados(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
        
       Iterable<Resultado> resultados = resultadoDAO.findAll();
       
       List<Resultado> listaResultados = new ArrayList<>();
        resultados.forEach(listaResultados::add);
       
       Map<Integer, Resultado> mapaResultados = listaResultados.stream()
        .collect(Collectors.toMap(Resultado::getIdPartido, resultado -> resultado));
        
        model.addAttribute("resultadosMap", mapaResultados);
       Iterable<Partido> partidos = partidoDAO.findAll();
       model.addAttribute("partidos", partidos);
       model.addAttribute("resultados", resultados);

        return "resultados"; 
    }
    
    @GetMapping("/ShowAñadirResultado")
    public String ShowAñadirResultado (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario); 
        Iterable<Resultado> resultados = resultadoDAO.findAll(); // Obtener resultados

            // Guardamos los id_partido en una lista
            List<Integer> idsConResultado = new ArrayList<>();
            for (Resultado resultado : resultados) {
                idsConResultado.add(resultado.getIdPartido());
            }
            
            Iterable<Partido> partidos = partidoDAO.findAll(); // Obtener partidos

            // Filtramos los partidos dejando solo los que están en idsConResultado
            List<Partido> partidosConResultado = new ArrayList<>();
            for (Partido partido : partidos) {
                if (!(idsConResultado.contains(partido.getId_partido()))) {
                    partidosConResultado.add(partido);
                }
            }

            model.addAttribute("partidos", partidosConResultado);
        
        return "partidosmostrar_agregar_R";
    }
    
    @GetMapping("/ShowResultado")
    public String showresultado (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);             
        return "agregarpartido";
    }
    

    @GetMapping("/AgregarResultado")
    public String AgregarResultado (HttpSession session, Model model, @RequestParam("id") int partidoId){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
        
        Partido partido = partidoDAO.findById(partidoId).orElse(null);
        model.addAttribute("partido", partido);  
        
        return "elegirResultado";
    }
    
    @GetMapping("/AgregarR")
    public String agregarResultado(
            @RequestParam("id") int idPartido, 
            @RequestParam("ganador") String ganador, 
            Model model) {
            System.out.println(ganador);
            Resultado resultado = new Resultado (idPartido, ganador, idPartido);
            resultadoDAO.save(resultado);
            
            Iterable<Resultado> resultados = resultadoDAO.findAll();
       
                List<Resultado> listaResultados = new ArrayList<>();
                 resultados.forEach(listaResultados::add);

                Map<Integer, Resultado> mapaResultados = listaResultados.stream()
                 .collect(Collectors.toMap(Resultado::getIdPartido, resultadomap -> resultadomap));
        
            
            Iterable<Partido> partidos = partidoDAO.findAll();
            model.addAttribute("partidos", partidos);
            model.addAttribute("resultadosMap", mapaResultados);
            
        return "resultados";
    }



}