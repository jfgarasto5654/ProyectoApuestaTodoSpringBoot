
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class CustomErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode != null) {
            model.addAttribute("status", statusCode);
            if (statusCode == 404) {
                model.addAttribute("error", "Página no encontrada.");
            } else {
                model.addAttribute("error", "Ocurrió un error inesperado.");
            }
        }
        return "error"; // Carga error.html en templates
    }
}

