package myHikeJava;

import java.io.IOException;
import java.io.PrintWriter;


import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "testingAjaxServlet", value = "/testingAjaxServlet")
//@MultipartConfig
public class TestingAjaxServlet extends ServletUtils {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve data from the Ajax request
        String inputData = request.getParameter("data");

        // Perform database operation (e.g., insert into database)
        // Replace this with your actual database logic

        // Assume a success message for demonstration purposes
        String resultMessage = "Data successfully inserted into the database.";

        // Send the response back to the Ajax request
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(resultMessage);
        out.close();
    }
}