package servlets.commentServlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Comment;
import models.Hike;
import database.Database;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "deleteCommentServlet", value = "/deleteCommentServlet")
public class DeleteCommentServlet  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String error = "";
        String commentId = request.getParameter("commentId");
        String hikeId = request.getParameter("hikeId");
        try {
            //Get the comment based on its id, as well as the hike related to the comment.
            Comment comment = Database.getCommentById(commentId);
            Hike hike = Database.getHikeById(comment.getCommentHike().getHikeId());

            //Remove the comment from the hikes list of comments and update the hike in the database.
            List<Comment> comments = hike.getHikeComments();
            comments.remove(comment);
            hike.setHikeComments(comments);
            Database.update(hike);

            //Delete the comment from the database.
            Database.delete(comment);
        }
        catch (Exception e) {
            error = e.getMessage();
        }
        if (!error.isEmpty()) {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) +  "&error=" + response.encodeURL(error));
        } else {
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&successAlert=deleteCommentSuccess");
        }
    }
}