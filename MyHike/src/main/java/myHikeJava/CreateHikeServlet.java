package myHikeJava;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "createHikeServlet", value = "/createHikeServlet")
public class CreateHikeServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Loading ...";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Get parameters from create.jsp like this:
        request.getParameter("name"); //-> returns name entered in


        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println(request.getParameter("region"));
        out.println("</body></html>");
    }

    public void destroy() {
    }
}