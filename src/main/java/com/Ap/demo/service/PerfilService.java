package com.Ap.demo.service;

import com.Ap.demo.DAO.IPersonaDAO;
import com.Ap.demo.exception.PerfilNoEncontradoException;
import com.Ap.demo.exception.UsuarioNoAutenticadoException;
import com.Ap.demo.logica.Persona;
import com.Ap.demo.logica.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    private IPersonaDAO personaDAO;

    public Persona obtenerPerfil(Usuario usuario) {

        if (usuario == null) {
            throw new UsuarioNoAutenticadoException();
        }

        return personaDAO.findById(usuario.getId_usuario())
                .orElseThrow(() ->
                        new PerfilNoEncontradoException(usuario.getId_usuario())
                );
    }
}
