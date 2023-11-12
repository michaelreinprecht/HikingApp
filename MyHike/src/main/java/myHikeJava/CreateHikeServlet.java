package myHikeJava;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Base64;
import java.time.format.DateTimeFormatter;

import facade.JPAHikeFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Hike;

@WebServlet(name = "createHikeServlet", value = "/createHikeServlet")
@MultipartConfig
public class CreateHikeServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Loading ...";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        try {
            Hike hike = getHike(request);
            Database.insert(hike);
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
        hike.setHikeId(101); //TODO replace with random UUID!
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

        //TODO save recommended months
        hike.setRecommendedList(null);

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