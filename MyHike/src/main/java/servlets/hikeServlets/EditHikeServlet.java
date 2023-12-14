package servlets.hikeServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Hike;
import models.Month;
import database.Database;
import servlets.ServletUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "editHikeServlet", value = "/editHikeServlet")
@MultipartConfig
public class EditHikeServlet extends ServletUtils {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";

        String hikeId = request.getParameter("Id");
        try {
            Hike oldHike = Database.getHikeById(request.getParameter("Id"));

            if (!handleAuthForHike(oldHike, request, response)) {
                return;
            }

            //Create new hike object based on the data entered in create.jsp
            Hike hike = getUpdatedHike(oldHike, request, response);

            //Insert hike into database
            Database.update(hike);

        } catch (IOException | ServletException | SQLException e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.sendRedirect("edit.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&successAlert=" + response.encodeURL("Successfully edited your hike!"));
        }
    }

    private Hike getUpdatedHike(Hike oldHike, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Hike newHike = getHikeBase(request, oldHike); //Gets the basic hike data (works same for edit and create)

        //Get recommended Months are String[] from html parameter and turn them into a Bitmap
        String recommendedMonths = Month.getBitmapFromMonths(request.getParameterValues("months"));

        //Encode image to Base64 String - if there is no image use old image instead
        String image = getBase64(request.getPart("fileToUpload"), oldHike);

        newHike.setHikeMonths(recommendedMonths);
        newHike.setHikeImage(image);

        return newHike;
    }

    //If users does not own the hike or is an admin, redirect to detail page and display error.
    //Returns false if user is not authorized to edit this hike.
    private boolean handleAuthForHike(Hike hike, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        boolean loggedIn = request.getSession().getAttribute("username") != null;
        boolean ownsHike = loggedIn && (hike.getHikeOfUser() != null) && hike.getHikeOfUser().getUserName().equals(session.getAttribute("username"));
        boolean isAdmin = session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin");
        if (!ownsHike && !isAdmin) {
            String error = "You are not authorized to edit this hike.";
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hike.getHikeId()) + "&error=" + response.encodeURL(error));
            return false;
        }
        return true;
    }

    //Attempts to encode the given file to a base64 String (doesn't need to check if it's png, jpg, jpeg, as this is
    //already validated in create.js -> function validateForm()
    private String getBase64(Part fileToUpload, Hike oldHike) throws IOException {
        if (fileToUpload.getSize() != 0) {
            return super.getBase64(fileToUpload);
        } else {
            return oldHike.getHikeImage();
        }
    }
}