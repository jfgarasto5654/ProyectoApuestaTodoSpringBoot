/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.persistencia;



import com.Ap.demo.DAO.IApuestaDAO;
import com.Ap.demo.DAO.IPartidoDAO;
import com.Ap.demo.DAO.IPersonaDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.DAO.IResultadoDAO;
import com.Ap.demo.logica.Apuesta;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Usuario;
import com.Ap.demo.logica.Persona;
import com.Ap.demo.logica.Resultado;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
@Slf4j
public class ControladorRest {
          
    
    @GetMapping("/")
    public String indice (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
         model.addAttribute("userLogueado", usuario);
        return "indice";
    }
    
    @GetMapping("/loginget")
    public String showloginpage (){
        
        return "InicioSesion";
    }
    
    @GetMapping("/CrearUsuario")
    public String showCrearpage (){
        return "CrearUsuario";
    }
    
    @Autowired
    private IPersonaDAO personaDAO;
    @GetMapping("/Perfil")
    public String showPerfil(HttpSession session, Model model) {
    // Supongamos que en la sesión el usuario está guardado con el atributo "usuarioLogueado"
    Usuario usuario = (Usuario) session.getAttribute("userLogueado");
    
       if (usuario != null) {
       int id = usuario.getId_usuario();
           System.out.println("id:" + id); 
        // Realiza la búsqueda de la persona con el id
        Persona persona = personaDAO.findById(id).orElse(null);
        //persona.toString();
        if (persona != null) {
            model.addAttribute("persona", persona);
             model.addAttribute("userLogueado", usuario);
            return "perfil";
        } else {
            return "nada";  // Si no se encuentra la persona
        }
    } else {
        return "login";  // Si el usuario no está en sesión
     }
    }

    
    @Autowired
    private IUsuarioDAO usuarioDAO;
    @PostMapping("/login")
    public String login(Model m, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Usuario u = usuarioDAO.findByUsuarioAndContrasenia(username, password);
        
        if (u == null){
                m.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
                return "inicioSesion";
        } else {
            u.toString();
            u.setRol("NoRol");
            session.setAttribute("userLogueado", u);
            m.addAttribute("userLogueado", u);
            return "indice";
        }
    }
    
     @Autowired
    private IPartidoDAO partidoDAO;
     
     @GetMapping("/Apuesta")
    public String mostrarApuesta(@RequestParam("id") int partidoId, Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
         model.addAttribute("userLogueado", usuario);
        usuario.toString();
        
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
    
    @Autowired
    private IResultadoDAO resultadoDAO;
    @GetMapping("/Resultados")
    public String resultados(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
         model.addAttribute("userLogueado", usuario);
        usuario.toString();
        
       Iterable<Resultado> resultados = resultadoDAO.findAll();
       Iterable<Partido> partidos = partidoDAO.findAll();
       model.addAttribute("partidos", partidos);
       model.addAttribute("resultados", resultados);

        return "resultados"; 
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
         model.addAttribute("partidos", partidos);
         
         apuestas.forEach(a -> System.out.println("Apuesta: " + a.getIdPartido()));
         partidos.forEach(p -> System.out.println("Partido: " + p.getId_partido()));
         return "mostrarApuestas"; 
    }
    
    
    @GetMapping("/partidos")
    public String partidos (Model model){
        Iterable<Partido> partidos = partidoDAO.findAll();
        model.addAttribute("partidos", partidos);
        
        /*for (Partido partido : partidos) {
            System.out.println("Local: " + partido.getLocal());
            System.out.println("Visitante: " + partido.getVisitante());
            System.out.println("Fecha: " + partido.getFecha());
            System.out.println("--------------------");
        }*/
        
        return "partidosmostrar";
    }
    
    @Autowired
    private IApuestaDAO apuestaDAO;
    
    @PostMapping("/procesarApuesta")
    public String procesarapuesta (Model model, @RequestParam("monto") int monto,
            @RequestParam("por") String por,
            @RequestParam("idPartido") int idPartido, HttpSession session){
        
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
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
        
        
        return "apuestaCreada";
    }
    
    
}