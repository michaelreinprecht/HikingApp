package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Comment;
import models.Hike;
import models.User;
import myHikeJava.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


@WebServlet(name = "addCommentServlet", value = "/addCommentServlet")
public class AddCommentServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Hike hike = Database.getHikeById(request.getParameter("hikeId"));
        User user = Database.getUserById("admin"); //TODO replace hardcoded username with username from session variable
        String commentDescription = request.getParameter("commentDescription");

        String error = "";
        try {
            if (commentDescription != null && !commentDescription.isEmpty()) {
                //Create new comment object with the given data
                String id = UUID.randomUUID().toString(); //Create new random UUID for comment.
                Comment comment = new Comment(id, commentDescription, hike, user);
                //Insert hike into database
                Database.insert(hike);
            } else {
                error = "Please enter a valid comment description.";
            }
        } catch (SQLException e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.sendRedirect("create.jsp?error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("discover.jsp?successAlert=createSuccess");
        }
    }
}