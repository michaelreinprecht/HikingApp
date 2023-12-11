package servlets.commentServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Comment;
import models.Hike;
import models.User;
import database.Database;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@WebServlet(name = "addCommentServlet", value = "/addCommentServlet")
public class AddCommentServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        String hikeId = request.getParameter("hikeId");

        //If users is not logged in, redirect to detail page and display error.
        HttpSession session = request.getSession();
        boolean loggedIn = session.getAttribute("username") != null;
        if (!loggedIn) {
            error = "Unauthorized users are unable to create comments - please log in.";
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
            return;
        }

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
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&successAlert=" +
                    response.encodeURL("Successfully added your comment!"));
        }
    }
}