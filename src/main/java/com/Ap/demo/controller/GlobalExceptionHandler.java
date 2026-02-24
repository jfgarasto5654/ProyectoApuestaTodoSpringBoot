package com.Ap.demo.controller;

import com.Ap.demo.exception.LoginException;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String handleNull(NullPointerException e, Model model) {
        model.addAttribute("error", "Dato faltante o sesión inválida");
        return "error";
    }

    @ExceptionHandler(DataAccessException.class)
    public String handleDB(DataAccessException e, Model model) {
        model.addAttribute("error", "Error de base de datos");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("error", "Ocurrió un error inesperado");
        return "error";
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParam(MissingServletRequestParameterException e,
                                     RedirectAttributes redirectAttrs) {

        String nombreParam = e.getParameterName();
        System.out.println("error missing servlet request parameter exception causado por falta de monto en billetera");
        redirectAttrs.addFlashAttribute(
            "error",
            "El campo '" + nombreParam + "' es obligatorio"
        );

       return "redirect:/Billetera"; 
    }
    
    @ExceptionHandler(LoginException.class)
        public String handleLoginException(LoginException ex, Model model) {
            
            model.addAttribute("errorMessage", ex.getMessage());

            return "inicioSesion"; 
        }
         
}

