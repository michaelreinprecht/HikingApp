package servlets;

import database.Database;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Hike;
import models.Region;
import models.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class ServletUtils extends HttpServlet {
    protected String getBase64(Part fileToUpload) throws IOException {
        try (InputStream is = fileToUpload.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            return Base64.getEncoder().encodeToString(os.toByteArray());
        }
    }
}
