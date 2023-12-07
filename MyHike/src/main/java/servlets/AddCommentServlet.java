package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Hike;
import models.User;
import myHikeJava.Database;

import java.io.IOException;


@WebServlet(name = "addCommentServlet", value = "/addCommentServlet")
public class AddCommentServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Hike hike = Database.getHikeById(request.getParameter("hikeId"));
        User user = null; //TODO database function to get user name
        String commentDescription = request.getParameter("commentDescription");

        //TODO save comment to db.
    }
}