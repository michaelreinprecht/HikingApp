package servlets.hikeServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Hike;
import database.Database;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "softDeleteHikeServlet", value = "/softDeleteHikeServlet")
@MultipartConfig
public class SoftDeleteHikeServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String error = "";
        String hikeId = request.getParameter("Id");
        try {
            Hike hike = softDeleteHike(request);
            Database.update(hike);
        }
        catch (IOException | ServletException | SQLException e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("discover.jsp?successAlert=" + response.encodeURL("Successfully deleted your hike!"));
        }
    }
    public Hike softDeleteHike(HttpServletRequest request) throws IOException, ServletException {
        Hike hike = Database.getHikeById(request.getParameter("Id"));

        hike.setIsDeleted(true);
        return hike;
    }
}