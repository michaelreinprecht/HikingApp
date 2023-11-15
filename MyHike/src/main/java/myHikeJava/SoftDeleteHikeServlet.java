package myHikeJava;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Hike;

import javax.xml.crypto.Data;
import java.io.IOException;

@WebServlet(name = "softDeleteHikeServlet", value = "/softDeleteHikeServlet")
@MultipartConfig
public class SoftDeleteHikeServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String error = "";
        try {
            Hike hike = softDeleteHike(request);
            Database.update(hike);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
        catch (IOException | ServletException e) {
            error = e.getMessage();
        }
    }
    public Hike softDeleteHike(HttpServletRequest request) throws IOException, ServletException {
        Hike hike = Database.getHikeById(request.getParameter("Id"));

        hike.setIsDeleted(true);
        return hike;
    }
}
