package servlets;

import java.io.IOException;
import java.io.PrintWriter;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myHikeJava.ServletUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@WebServlet("/testingAjaxServlet")
//@MultipartConfig
public class TestingAjaxServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Retrieve data from the Ajax request

        // Perform database operation (e.g., insert into database)
        // Replace this with your actual database logic

        response.getWriter().write("Testing");
    }
}