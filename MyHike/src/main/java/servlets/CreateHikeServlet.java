package servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;
import models.Month;
import models.PointOfInterest;
import myHikeJava.Database;
import myHikeJava.ServletUtils;

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

        /*
        String poiDataJson = request.getParameter("poiData");
        List<PointOfInterest> poiList = parsePoiDataJson(poiDataJson);

        for (int i = 0; i < poiList.size(); i++) {
            Part filePart = request.getPart("poiImage");
            // Create POI object with Part property
            PointOfInterest poi = new PointOfInterest(
                    poiList.get(i).getPointOfInterestId(),
                    poiList.get(i).getPointOfInterestName(),
                    poiList.get(i).getPointOfInterestLon(),
                    poiList.get(i).getPointOfInterestLat(),
                    encodeToBase64(filePart)
            );
        }
         */

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

    /*
    private List<PointOfInterest> parsePoiDataJson(String poiDataJson) {
        List<PointOfInterest> poiList = new ArrayList<>();
        // Implement JSON parsing logic to convert the JSON string to a list of POI objects
        // You can use a JSON library like Jackson or Gson for this purpose
        // Example with Gson:
        // Gson gson = new Gson();
        // Type listType = new TypeToken<List<POI>>() {}.getType();
        // return gson.fromJson(poiDataJson, listType);
        // For simplicity, you can use a placeholder here:
        return List.of(new PointOfInterest("Placeholder Title", "Placeholder Description", Double.valueOf("0.0"), Double.valueOf("0.0"), ""));
    }*/
}