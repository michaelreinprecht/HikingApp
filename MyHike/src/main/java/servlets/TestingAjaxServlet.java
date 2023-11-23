package servlets;

import java.io.IOException;
import java.io.PrintWriter;


import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myHikeJava.ServletUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@WebServlet(name = "testingAjaxServlet", value = "/testingAjaxServlet")
//@MultipartConfig
public class TestingAjaxServlet extends ServletUtils {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve data from the Ajax request
        String inputData = request.getParameter("data");

        // Perform database operation (e.g., insert into database)
        // Replace this with your actual database logic

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        obj.put("message", "hello from server");
        obj.put("title", "mytitle");
        out.print(obj.toString());

        // Assume a success message for demonstration purposes
        String resultMessage = "<tags:card title='Testing'/>" ;
        out.close();
    }
}