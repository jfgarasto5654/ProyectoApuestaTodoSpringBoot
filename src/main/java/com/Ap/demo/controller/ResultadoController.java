package com.Ap.demo.controller;


import com.Ap.demo.service.ApuestaService;
import com.Ap.demo.service.PartidoService;
import com.Ap.demo.service.ResultadoService;
import com.Ap.demo.service.SesionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultadoController {

    private final ResultadoService resultadoService;
    private final SesionService sesionService;
    private final ApuestaService apuestaService;
    private final PartidoService partidoService;

    public ResultadoController(ResultadoService resultadoService,
                               SesionService sesionService, ApuestaService apuestaService, PartidoService partidoService) {
        this.resultadoService = resultadoService;
        this.sesionService = sesionService;
        this.apuestaService = apuestaService;
        this.partidoService = partidoService;
    }

   @GetMapping("/Resultados")
public String resultados(Model model, HttpSession session) {
    model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));

    // 1. Mandamos el mapa como siempre
    model.addAttribute("resultadosMap", resultadoService.obtenerResultadosMap());

    // 2. CAMBIO: Mandamos TODOS los partidos (o solo los de estado 0) 
    // para que la vista pueda mostrar los que ya terminaron
    model.addAttribute("partidos", partidoService.obtenerTodosPartidos()); 

    return "resultados";
}

    @GetMapping("/Admin/ShowAñadirResultado")
    public String showAgregarResultado(Model model, HttpSession session) {

        model.addAttribute("userLogueado",
                sesionService.obtenerUsuario(session));

        model.addAttribute("partidos",
                resultadoService.obtenerPartidosSinResultado());

        return "partidosmostrar_agregar_R";
    }

    @GetMapping("/Admin/AgregarResultado")
public String elegirResultado(@RequestParam int id, Model model, HttpSession session) {
    model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
    
    // Aquí usamos el .orElse(null) o nos aseguramos que devuelva el objeto, no el Optional
    model.addAttribute("partido", resultadoService.obtenerPartidoPorId(id)); 

    return "elegirResultado";
}

@GetMapping("/Admin/AgregarR")
public String agregarResultado(@RequestParam int id, 
                               @RequestParam String ganador, 
                               Model model, 
                               HttpSession session) {

    apuestaService.registrarResultadoYLiquidar(id, ganador);

    model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
    model.addAttribute("resultadosMap", resultadoService.obtenerResultadosMap());

    return "resultados";
}
}
