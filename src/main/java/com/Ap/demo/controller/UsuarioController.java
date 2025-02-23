/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.controller;


import com.Ap.demo.DAO.IPersonaDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.logica.Usuario;
import com.Ap.demo.logica.Usuario;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.logica.Persona;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioDAO usuarioDAO;
    @Autowired
    private IPersonaDAO personaDAO;
    
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
    
    @GetMapping("/Admin")
    public String admin (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);             
        return "admin";
    }
    
    @GetMapping("/Billetera")
    public String billetera (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
        double dinero = usuario.getDinero();
        model.addAttribute("dinero", dinero);
        return "billetera";
    }
    
    @PostMapping("/Billetera")
    public String billeteras (HttpSession session, @RequestParam("monto") Double monto,
            @RequestParam("Modificar") String modificar,
            Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
        
        if(monto<0){
            double dinero = usuario.getDinero();
            model.addAttribute("dinero", dinero);
            model.addAttribute("error", "la cantidad no puede ser menor a 0");
            return "billetera";
        }
        
                if(modificar.equals("retiro")){
                    
                if (monto > usuario.getDinero()){
                        double dinero = usuario.getDinero();
                        model.addAttribute("dinero", dinero);
                        model.addAttribute("error", "el monto ingresado es mayor a su saldo");
                        return "billetera";
                } else {
                    usuario.setDinero(usuario.getDinero() - monto);
                }
            }
                else if (modificar.equals("ingreso")){
                usuario.setDinero(usuario.getDinero() + monto);
            }
         
                usuarioDAO.save(usuario);
        
                double dinero = usuario.getDinero();
                model.addAttribute("dinero", dinero);
                return "billetera";
        
    }
    
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
    
    @PostMapping("/login")
    public String login(Model m, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Usuario u = usuarioDAO.findByUsuarioAndContrasenia(username, password);
        
        if (u == null){
                m.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
                return "inicioSesion";
        } else {
            u.toString();
            session.setAttribute("userLogueado", u);
            m.addAttribute("userLogueado", u);
            return "indice";
        }
    }
    
    @GetMapping("/showcrear")
     
     public String chowcrear (HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        model.addAttribute("userLogueado", usuario);
         
        return "crearUser";
    }
    
    @PostMapping("/crearUser")
    public String crearUser(Model m, @RequestParam String username, 
                                   @RequestParam String nombre, 
                                   @RequestParam String apellido, 
                                   @RequestParam int edad, 
                                   @RequestParam String dni, 
                                   @RequestParam String password,
                                   @RequestParam String reppassword, HttpSession session) {
        
        if (!password.equals(reppassword)){
                m.addAttribute("errorMessage", "las contraseñas no coinciden");
                return "crearUser";
        } else if (edad<18){
                m.addAttribute("errorMessage", "La edad debe ser mayor a 18 años");
                return "crearUser";
        }
        
        Usuario usuario = new Usuario(username, password, 0, "norol");
        usuarioDAO.save(usuario);
       
        Persona persona = new Persona(dni,nombre,apellido,edad,usuarioDAO.findLastUsuarioId());
        personaDAO.save(persona);
        m.addAttribute("goodcreate", "cuenta creada con exito");
        m.addAttribute("userLogueado", usuario);
        session.setAttribute("userLogueado", usuario);
            return "indice";
        }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión actual
        return "indice";
    }}