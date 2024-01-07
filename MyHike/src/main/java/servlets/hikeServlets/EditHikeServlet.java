package servlets.hikeServlets;

import database.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Hike;
import models.Month;

import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("LombokGetterMayBeUsed")
@WebServlet(name = "editHikeServlet", value = "/editHikeServlet")
@MultipartConfig
public class EditHikeServlet extends HikeServletUtils {
    private String error;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        editHike(request, response);
    }

    //Attempts to edit the given hike (in the request). If this method fails it will redirect back to the edit page and
    //display an error message. Otherwise, it will redirect to the detail page of the edited hike and display a success message.
    protected void editHike(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        error = "";

        String hikeId = request.getParameter("Id");
        try {
            Hike oldHike = Database.getHikeById(request.getParameter("Id"));

            if (!handleAuthForHike(oldHike, request, response)) {
                return;
            }

            //Create new hike object based on the data entered in create.jsp
            Hike hike = getUpdatedHike(oldHike, request);

            //Insert hike into database
            Database.update(hike);

        } catch (IOException | ServletException | SQLException e) {
            String errorMessage = "An error occurred while editing the hike.Please try again later.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
        if (!error.isEmpty()) {
            response.sendRedirect("edit.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&successAlert=" + response.encodeURL("Successfully edited your hike!"));
        }
    }

    protected Hike getUpdatedHike(Hike oldHike, HttpServletRequest request) throws IOException, ServletException {
        Hike newHike; //Gets the basic hike data (works same for edit and create)
        try {
            newHike = getHikeBase(request, oldHike);
        } catch (SQLException e) {
            error = "A database error has occurred. Please try again later.";
            newHike = new Hike();
        }

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
    protected boolean handleAuthForHike(Hike hike, HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    public String getError() {
        return error;
    }
}