package myHikeJava;

import java.io.*;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "createHikeServlet", value = "/createHikeServlet")
@MultipartConfig
public class CreateHikeServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Loading ...";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";

        try {
            Part fileToUpload = request.getPart("fileToUpload");

            if (fileToUpload == null || !fileToUpload.getSubmittedFileName().toLowerCase().endsWith(".png")) {
                throw new IOException("This is not a valid png image.");
            }

            try (InputStream is = fileToUpload.getInputStream();
                 ByteArrayOutputStream os = new ByteArrayOutputStream()) {

                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

                String base64Image = Base64.getEncoder().encodeToString(os.toByteArray());
                request.getSession().setAttribute("last_image", base64Image);
            }
        } catch (IOException | ServletException e) {
            error = e.getMessage();
        }

        response.sendRedirect("create.jsp?error=" + response.encodeURL(error));

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }
}