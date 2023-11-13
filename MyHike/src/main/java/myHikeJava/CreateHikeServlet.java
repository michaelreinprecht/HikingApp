package myHikeJava;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;
import models.Month;
import models.Recommended;

@WebServlet(name = "createHikeServlet", value = "/createHikeServlet")
@MultipartConfig
public class CreateHikeServlet extends HttpServlet {
    private String message;
    //This list is added to the hike, later on it's contents also need to be uploaded to the database.
    List<Recommended> recommendedMonths = new ArrayList<>();

    public void init() {
        message = "Loading ...";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        try {
            Hike hike = getHike(request);
            //Create hike
            Database.insert(hike);

            //Add recommendedMonths to recommended_in table.
            for(Recommended recommended: recommendedMonths) {
                Database.insert(recommended);
            }

        } catch (IOException | ServletException e) {
            error = e.getMessage();
        }
        response.sendRedirect("create.jsp?error=" + response.encodeURL(error));

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    private Hike getHike(HttpServletRequest request) throws IOException, ServletException {
        Hike hike = new Hike();

        //Create random UUID for hike.
        hike.setHikeId(UUID.randomUUID().toString());

        hike.setHikeName(request.getParameter("name"));
        hike.setHikeDescription(request.getParameter("description"));
        hike.setHikeStartLon(new BigDecimal(request.getParameter("startLon")));
        hike.setHikeStartLat(new BigDecimal(request.getParameter("startLat")));
        hike.setHikeEndLon(new BigDecimal(request.getParameter("endLon")));
        hike.setHikeEndLat(new BigDecimal(request.getParameter("endLat")));
        hike.setHikeDistance(new BigDecimal(request.getParameter("distance")));
        hike.setHikeAltitude(Integer.parseInt(request.getParameter("altitude")));
        hike.setHikeLandscape(Integer.parseInt(request.getParameter("landscape-rating")));
        hike.setHikeStrength(Integer.parseInt(request.getParameter("strength-rating")));
        hike.setHikeStamina(Integer.parseInt(request.getParameter("stamina-rating")));
        hike.setHikeDifficulty(Integer.parseInt(request.getParameter("difficulty-rating")));
        hike.setHikeRegion(Database.getRegionById(request.getParameter("region")));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        hike.setHikeDuration(Time.valueOf(LocalTime.parse(request.getParameter("duration"), formatter)));


        //Save recommended months for hike
        String[] months = request.getParameterValues("months");
        for (String monthIdString: months) {
            //Get month object from month table
            int monthId = Integer.parseInt(monthIdString);
            Month month = Database.getMonthById(monthId);

            //Generate UUID for recommended table
            String recommendedId = UUID.randomUUID().toString();

            //Create new recommended tupel with month and hike.
            recommendedMonths.add(new Recommended(recommendedId, month, hike));
        }
        //Add recommendedMonths to the hike (they also need to be inserted into the database still)
        hike.setRecommendedList(recommendedMonths);

        //Encode image to Base64 String and add it to hike
        Part fileToUpload = request.getPart("fileToUpload");
        try (InputStream is = fileToUpload.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            String base64Image = Base64.getEncoder().encodeToString(os.toByteArray());
            hike.setHikeImage(base64Image);
        }
        return hike;
    }
}