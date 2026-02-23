package com.Ap.demo.service;

import com.Ap.demo.DAO.IPersonaDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.exception.CamposObligatoriosException;
import com.Ap.demo.exception.EdadInvalidaException;
import com.Ap.demo.exception.PasswordNoCoincideException;
import com.Ap.demo.exception.PersonaNoEncontradaException;
import com.Ap.demo.exception.UsuarioYaExisteException;
import com.Ap.demo.logica.Persona;
import com.Ap.demo.logica.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private IPersonaDAO personaDAO;

    public Usuario login(String username, String password) {
        return usuarioDAO.findByUsuarioAndContrasenia(username, password);
    }

    public Usuario crearUsuarioCompleto(
            String username,
            String nombre,
            String apellido,
            Integer edad,
            String dni,
            String password,
            String repPassword
    ) {

        validarCampos(username, nombre, apellido, dni, password, repPassword);
        validarEdad(edad);
        validarPasswords(password, repPassword);
        validarUsuarioNoExiste(username);

        Usuario usuario = new Usuario(username, password, 0, "norol");
        usuarioDAO.save(usuario);

        Persona persona = new Persona(
                dni, nombre, apellido, edad, usuario.getId_usuario()
        );
        personaDAO.save(persona);

        return usuario;
    }

    public Persona obtenerPersona(int idUsuario) {
        return personaDAO.findById(idUsuario)
                .orElseThrow(() -> new PersonaNoEncontradaException(idUsuario));
    }

    // =====================
    // VALIDACIONES
    // =====================

    private void validarCampos(
            String username,
            String nombre,
            String apellido,
            String dni,
            String password,
            String repPassword
    ) {
        if (isBlank(username) || isBlank(nombre) || isBlank(apellido)
                || isBlank(dni) || isBlank(password) || isBlank(repPassword)) {
            throw new CamposObligatoriosException();
        }
    }

    private void validarEdad(Integer edad) {
        if (edad == null || edad < 18) {
            throw new EdadInvalidaException();
        }
    }

    private void validarPasswords(String password, String repPassword) {
        if (!password.equals(repPassword)) {
            throw new PasswordNoCoincideException();
        }
    }

    private void validarUsuarioNoExiste(String username) {
        if (usuarioDAO.existsByUsuario(username)) {
            throw new UsuarioYaExisteException(username);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
    
    public Usuario buscarPorId(int id) {
    // Aquí es donde el Service usa el DAO
    return usuarioDAO.findById(id).orElse(null);
}
}
