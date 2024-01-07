package servlets.commentServlets;

import database.Database;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Comment;
import models.Hike;
import models.User;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@SuppressWarnings("LombokGetterMayBeUsed")
@WebServlet(name = "addCommentServlet", value = "/addCommentServlet")
public class AddCommentServlet extends HttpServlet {
    private String error;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        addComment(request, response);
    }

    //Attempts to add a new comment to the hike. If this method fails it will redirect to the detail page and display
    //an error message. Otherwise, displays a success message.
    protected void addComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        error = "";

        if (!handleAuth(request, response)) {
            return;
        }

        String hikeId = request.getParameter("hikeId");
        try {
            String username = request.getSession().getAttribute("username").toString();

            Hike hike = Database.getHikeById(hikeId);
            User user = Database.getUserById(username);
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

    //If users is not logged in, redirect to detail.jsp for the current hike. Returns false if user is not authorized.
    protected boolean handleAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String hikeId = request.getParameter("hikeId");
        boolean loggedIn = session.getAttribute("username") != null;
        if (!loggedIn) {
            String error = "Unauthorized users are unable to create comments - please log in.";
            response.sendRedirect("detail.jsp?Id=" + response.encodeURL(hikeId) + "&error=" + response.encodeURL(error));
            return false;
        }
        return true;
    }

    public String getError() {
        return error;
    }
}