package servlets.hikeServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import database.Database;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "softDeleteHikeServlet", value = "/softDeleteHikeServlet")
@MultipartConfig
public class SoftDeleteHikeServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        softDeleteHike(request, response);
    }

    private void softDeleteHike(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        String hikeId = request.getParameter("Id");
        try {
            Hike hike = Database.getHikeById(request.getParameter("Id"));

            if (!handleAuthForHike(hike, request, response)) {
                return;
            }

            hike.setIsDeleted(true);
            Database.update(hike);
        }
        catch (IOException | SQLException e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("discover.jsp?successAlert=" + response.encodeURL("Successfully deleted your hike!"));
        }
    }

    //If users does not own the hike or is an admin, redirect to detail page and display error.
    //Returns false if user is not authorized to delete this hike.
    public boolean handleAuthForHike(Hike hike, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        boolean loggedIn = request.getSession().getAttribute("username") != null;
        boolean ownsHike = loggedIn && (hike.getHikeOfUser() != null) && hike.getHikeOfUser().getUserName().equals(session.getAttribute("username"));
        boolean isAdmin = session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin");
        if (!ownsHike && !isAdmin) {
            String error = "You are not authorized to delete this hike.";
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hike.getHikeId()) + "&error=" + response.encodeURL(error));
            return false;
        }
        return true;
    }
}
