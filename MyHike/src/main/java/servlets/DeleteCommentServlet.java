package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "deleteCommentServlet", value = "/deleteCommentServlet")
public class DeleteCommentServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commentId = request.getParameter("commentId");
    }
}