package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Comment;
import models.Hike;
import models.PointOfInterest;
import models.User;
import myHikeJava.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


@WebServlet(name = "addCommentServlet", value = "/addCommentServlet")
public class AddCommentServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        String hikeId = request.getParameter("hikeId");
        try {
            Hike hike = Database.getHikeById(hikeId);
            User user = Database.getUserById("admin"); //TODO replace hardcoded username with username from session variable
            String commentDescription = request.getParameter("commentDescription");

            if (commentDescription != null && !commentDescription.isEmpty()) {
                //Create new comment object with the given data
                String id = UUID.randomUUID().toString(); //Create new random UUID for comment.
                Comment comment = new Comment(id, commentDescription, hike, user);
                //Insert comment into database
                Database.insert(comment);

                //Update hike table with new comment
                List<Comment> comments = hike.getHikeComments();
                comments.add(comment);
                hike.setHikeComments(comments);
                Database.update(hike);
            } else {
                error = "Please enter a valid comment description.";
            }
        } catch (Exception e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) +  "&error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&successAlert=addCommentSuccess"); //TODO handle success properly
        }
    }
}