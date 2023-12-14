package servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

//Contains methods which may be useful in many different types of servlets.
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
