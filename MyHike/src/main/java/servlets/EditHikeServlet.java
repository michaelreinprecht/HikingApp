package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Hike;
import models.Month;
import myHikeJava.Database;
import myHikeJava.ServletUtils;

import java.io.IOException;

@WebServlet(name = "editHikeServlet", value = "/editHikeServlet")
@MultipartConfig
public class EditHikeServlet extends ServletUtils {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        try {
            //Create new hike object based on the data entered in create.jsp
            Hike hike = getUpdatedHike(request);
            //Insert hike into database
            Database.update(hike);

        } catch (IOException | ServletException e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.sendRedirect("edit.jsp?Id=" + request.getParameter("Id") + "&error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("index.jsp?editSuccess=true");
        }
    }

    private Hike getUpdatedHike(HttpServletRequest request) throws IOException, ServletException {
        Hike oldHike = Database.getHikeById(request.getParameter("Id"));
        Hike newHike = getHikeBase(request, oldHike); //Gets the basic hike data (works same for edit and create)

        //Get recommended Months are String[] from html parameter and turn them into a Bitmap
        String recommendedMonths = Month.getBitmapFromMonths(request.getParameterValues("months"));

        //Encode image to Base64 String - if there is no image use old image instead
        String image = encodeToBase64(request.getPart("fileToUpload"), oldHike);

        newHike.setHikeMonths(recommendedMonths);
        newHike.setHikeImage(image);
        return newHike;
    }

    //Attempts to encode the given file to a base64 String (doesn't need to check if it's png, jpg, jpeg, as this is
    //already validated in create.js -> function validateForm()
    private String encodeToBase64(Part fileToUpload, Hike oldHike) throws IOException {
        if (fileToUpload.getSize() != 0) {
            return getBase64(fileToUpload);
        } else {
            return oldHike.getHikeImage();
        }
    }

}