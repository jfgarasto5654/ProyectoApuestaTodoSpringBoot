
package com.Ap.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model m) {
        if ("edad".equals(ex.getName())) {
            m.addAttribute("errorMessage", "La edad debe ser un número entero válido");
            return "crearUser";
        }
        throw ex;
    }
}