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
import java.util.UUID;

@WebServlet(name = "createHikeServlet", value = "/createHikeServlet")
@MultipartConfig
public class CreateHikeServlet extends ServletUtils {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        try {
            //Create new hike object based on the data entered in create.jsp
            Hike hike = getHike(request);
            //Insert hike into database
            Database.insert(hike);

        } catch (IOException | ServletException e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.sendRedirect("create.jsp?error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("discover.jsp?successAlert=createSuccess");
        }
    }

    private Hike getHike(HttpServletRequest request) throws IOException, ServletException {
        Hike hike = new Hike();

        //Get values from parameters
        String id = UUID.randomUUID().toString(); //Create random UUID for hike
        hike.setHikeId(id); //Id needs to be set early, so that Recommended Objects can be created.
        hike = getHikeBase(request, hike); //Gets the basic hike data (works same for edit and create)

        //Populate the List<Recommended> recommendedMonths (these months are not only needed for our hike, but also
        //need to be inserted into the recommended_in table).
        String recommendedMonths = Month.getBitmapFromMonths(request.getParameterValues("months"));

        //Encode image to Base64 String
        String image = encodeToBase64(request.getPart("fileToUpload"));

        hike.setHikeMonths(recommendedMonths);
        hike.setHikeImage(image);
        return hike;
    }

    //Attempts to encode the given file to a base64 String (doesn't need to check if it's png, jpg, jpeg, as this is
    //already validated in create_edit.js -> function validateForm()
    private String encodeToBase64(Part fileToUpload) throws IOException {
        return getBase64(fileToUpload);
    }
}