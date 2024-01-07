package servlets.hikeServlets;

import database.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;

import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("LombokGetterMayBeUsed")
@WebServlet(name = "softDeleteHikeServlet", value = "/softDeleteHikeServlet")
@MultipartConfig
public class SoftDeleteHikeServlet extends HttpServlet {
    private String error;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        softDeleteHike(request, response);
    }

    //Attempts to delete the given hike (in the request). If this method fails it will redirect back to the edit page and
    //display an error message. Otherwise, it will redirect to the detail page of the edited hike and display a success message.
    protected void softDeleteHike(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        error = "";
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
            // Handle exceptions here
            String errorMessage = "A database error has occurred. Please try again later.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return; // Return early to prevent further processing
        }
        if (!error.isEmpty()) {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("discover.jsp?successAlert=" + response.encodeURL("Successfully deleted your hike!"));
        }
    }

    //If users does not own the hike or is an admin, redirect to detail page and display error.
    //Returns false if user is not authorized to delete this hike.
    protected boolean handleAuthForHike(Hike hike, HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    public String getError() {
        return error;
    }
}
