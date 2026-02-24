/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Ap.demo.controller;


import com.Ap.demo.DAO.IPersonaDAO;
import com.Ap.demo.DAO.IRegistro_dineroDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.logica.Usuario;
import com.Ap.demo.logica.Usuario;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.logica.Partido;
import com.Ap.demo.logica.Persona;
import com.Ap.demo.logica.Registro_dinero;
import com.Ap.demo.service.BilleteraService;
import com.Ap.demo.service.SesionService;
import com.Ap.demo.service.UsuarioService;
import com.Ap.demo.service.PerfilService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {


private final SesionService sesionService;
private final UsuarioService usuarioService;
private final BilleteraService billeteraService;
private final PerfilService perfilService;


public UsuarioController(SesionService sesionService, 
                     UsuarioService usuarioService, 
                     BilleteraService billeteraService, 
                     PerfilService perfilService) {
    this.sesionService = sesionService;
    this.usuarioService = usuarioService;
    this.billeteraService = billeteraService;
    this.perfilService = perfilService;
}

    // ---------------- HOME ----------------
    @GetMapping("/")
    public String indice(HttpSession session, Model model) {
        model.addAttribute("userLogueado", sesionService.obtenerUsuario(session));
        return "indice";
    }
    
    @GetMapping("/login")
    public String mostrarLogin() {
        return "InicioSesion";
    }
    
     @GetMapping("/Admin")
    public String mostrarAdmin(HttpSession session,
                        Model model) {
        Usuario usuario = sesionService.obtenerUsuario(session);
        model.addAttribute("userLogueado", usuario);
        
        return "Admin";
    }


    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Usuario usuario = usuarioService.login(username, password);


        session.setAttribute("userLogueado", usuario);
        model.addAttribute("userLogueado", usuario);
        return "indice";
    }
    
    @GetMapping("/showcrear")
        public String mostrarRegistro() {
            return "crearUser";
        }
    
    @PostMapping("/crearUser")
    public String crearUser(@RequestParam String username,
                            @RequestParam String nombre,
                            @RequestParam String apellido,
                            @RequestParam(required = false) Integer edad,
                            @RequestParam String dni,
                            @RequestParam String password,
                            @RequestParam String reppassword,
                            HttpSession session,
                            Model model) {

        try {
            Usuario usuario = usuarioService.crearUsuarioCompleto(
                    username, nombre, apellido, edad, dni, password, reppassword
            );

            session.setAttribute("userLogueado", usuario);
            model.addAttribute("userLogueado", usuario);
            model.addAttribute("goodcreate", "Cuenta creada con éxito");

            return "indice";

        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());

            // llenar ante error formulario
            model.addAttribute("username", username);
            model.addAttribute("nombre", nombre);
            model.addAttribute("apellido", apellido);
            model.addAttribute("dni", dni);
            model.addAttribute("edad", edad);

        return "crearUser";
    }
}
    
    @GetMapping("/Perfil")
        public String showPerfil(HttpSession session, Model model) {

            Usuario usuario = sesionService.obtenerUsuario(session);

            if (usuario == null) {
                return "login";
            }

            try {
                Persona persona = perfilService.obtenerPerfil(usuario);

                model.addAttribute("persona", persona);
                model.addAttribute("userLogueado", usuario);

                return "perfil";

            } catch (RuntimeException e) {
                return "nada";
            }
        }



    // ---------------- BILLETERA ----------------
    @GetMapping("/Billetera")
public String billetera(HttpSession session, Model model) {
    // 1. Obtenemos el usuario de la sesión
    Usuario usuarioSesion = sesionService.obtenerUsuario(session);
    
    // 2. REFRESCAR: En lugar de usar 'usuarioSesion' (que es viejo), 
    // pedimos al servicio que lo busque en la DB por su ID.
    Usuario usuarioActualizado = usuarioService.buscarPorId(usuarioSesion.getId_usuario());

    // 3. Actualizamos la mochila (sesión) con la data nueva
    session.setAttribute("userLogueado", usuarioActualizado);

    // 4. Mandamos a la vista la data real
    model.addAttribute("userLogueado", usuarioActualizado);
    model.addAttribute("dinero", usuarioActualizado.getDinero());
    model.addAttribute("registroUsuario", 
        billeteraService.obtenerRegistros(usuarioActualizado.getId_usuario())
    );

    return "billetera";
}

    @PostMapping("/Billetera")
    public String billeteraPost(HttpSession session,
                                @RequestParam Double monto,
                                @RequestParam String Modificar,
                                Model model) {

        Usuario usuario = sesionService.obtenerUsuario(session);
        model.addAttribute("userLogueado", usuario);

        try {
            if (Modificar.equals("retiro")) {
                billeteraService.retirarDinero(usuario, monto);
            } else {
                billeteraService.ingresarDinero(usuario, monto);
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("dinero", usuario.getDinero());
        model.addAttribute(
            "registroUsuario",
            billeteraService.obtenerRegistros(usuario.getId_usuario())
        );

        return "billetera";
    }

    // ---------------- LOGOUT ----------------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "indice";
    }
}
