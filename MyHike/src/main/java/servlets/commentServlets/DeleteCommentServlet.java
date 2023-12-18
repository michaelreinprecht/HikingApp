package servlets.commentServlets;

import database.Database;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Comment;
import models.Hike;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "deleteCommentServlet", value = "/deleteCommentServlet")
public class DeleteCommentServlet  extends HttpServlet {
    private String error;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        deleteComment(request, response);
    }

    //Attempts to delete the given comment. If this method fails it will redirect to detail page and display an error
    //message. Otherwise, displays a success message.
    protected void deleteComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        error = "";

        String commentId = request.getParameter("commentId");
        String hikeId = request.getParameter("hikeId");

        try {
            //Get the comment based on its id, as well as the hike related to the comment.
            Comment comment = Database.getCommentById(commentId);
            Hike hike = Database.getHikeById(comment.getCommentHike().getHikeId());

            if (!handleAuthForComment(comment, request, response)) {
                return;
            }

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
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&successAlert=" + response.encodeURL("Successfully deleted your comment!"));
        }
    }

    //If users does not own the comment or is an admin, redirect to detail page and display error. Returns false if user
    //is not authorized to delete the comment.
    protected boolean handleAuthForComment(Comment comment, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String commentHikeId = comment.getCommentHike().getHikeId();
        boolean loggedIn = request.getSession().getAttribute("username") != null;
        boolean ownsComment = loggedIn && (comment.getCommentUser() != null) && comment.getCommentUser().getUserName().equals(session.getAttribute("username"));
        boolean isAdmin = session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin");
        if (!ownsComment && !isAdmin) {
            String error = "You are not authorized to delete this comment.";
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(commentHikeId) + "&error=" + response.encodeURL(error));
            return false;
        }
        return true;
    }

    public String getError() {
        return error;
    }
}