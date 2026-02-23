package com.Ap.demo.service;

import com.Ap.demo.DAO.IRegistro_dineroDAO;
import com.Ap.demo.DAO.IUsuarioDAO;
import com.Ap.demo.exception.MontoInvalidoException;
import com.Ap.demo.exception.SaldoInsuficienteException;
import com.Ap.demo.logica.Registro_dinero;
import com.Ap.demo.logica.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BilleteraService {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private IRegistro_dineroDAO registroDDAO;

    public void ingresarDinero(Usuario usuario, Double monto) {
        validarMonto(monto);

        usuario.setDinero(usuario.getDinero() + monto);
        usuarioDAO.save(usuario);

        registroDDAO.save(
            new Registro_dinero(monto, "ingreso", usuario.getId_usuario())
        );
    }

    public void retirarDinero(Usuario usuario, Double monto) {
        validarMonto(monto);

        if (monto > usuario.getDinero()) {
            throw new SaldoInsuficienteException();
        }

        usuario.setDinero(usuario.getDinero() - monto);
        usuarioDAO.save(usuario);

        registroDDAO.save(
            new Registro_dinero(monto, "retiro", usuario.getId_usuario())
        );
    }

    public List<Registro_dinero> obtenerRegistros(int idUsuario) {
        return registroDDAO.findAllByFkIdUsuario(idUsuario);
    }

    private void validarMonto(Double monto) {
        if (monto == null || monto <= 0) {
            throw new MontoInvalidoException();
        }
    }
}
