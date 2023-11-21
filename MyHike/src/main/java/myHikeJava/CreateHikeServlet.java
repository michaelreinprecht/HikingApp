package myHikeJava;

import java.io.*;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;
import models.Month;

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
            response.sendRedirect("index.jsp?createSuccess=true");
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