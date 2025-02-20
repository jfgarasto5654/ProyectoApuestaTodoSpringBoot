import com.Ap.demo.logica.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        // 🔹 Obtener la sesión del usuario
        Usuario usuario = (Usuario) session.getAttribute("userLogueado");

        System.out.println(usuario);
        // 🔴 Si no hay sesión o no tiene un rol asignado, bloqueamos la solicitud
        if (session == null || usuario.getRol() == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("🔑 Acceso denegado: No has iniciado sesión.");
            return;
        }


        // 🚨 Bloquear si la ruta es "/Admin" y el usuario no es ADMIN
        if (req.getRequestURI().equalsIgnoreCase("/Admin") && !(usuario.getRol()).equals("admin")) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("🚫 Acceso denegado: Solo administradores pueden entrar aquí.");
            return;
        }

        // ✅ Si el usuario es ADMIN o la ruta no es "/Admin", continuar la solicitud
        chain.doFilter(request, response);
    }
}
