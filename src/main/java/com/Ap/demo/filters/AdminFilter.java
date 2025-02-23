import com.Ap.demo.logica.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false); // Obtener la sesión sin crear una nueva
        if (session == null) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "🚫 Acceso denegado. No hay sesión activa.");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("userLogueado");
        System.out.println(usuario.getRol());
        if (usuario == null || usuario.getRol() == null || !usuario.getRol().equalsIgnoreCase("admin")) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "🚫 Acceso denegado. Se requiere rol admin.");
            return;
        }

        chain.doFilter(request, response);
    }
}
