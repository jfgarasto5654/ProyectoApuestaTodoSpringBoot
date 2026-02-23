package com.Ap.demo.service;

import com.Ap.demo.logica.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SesionService {

    public Usuario obtenerUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("userLogueado");
    }

    public boolean estaLogueado(HttpSession session) {
        return obtenerUsuario(session) != null;
    }
    
    
}