package servlets.loginServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession(false);
        if (session != null) {
            session.removeAttribute("username");
            session.removeAttribute("isAdmin");

            RequestDispatcher dispatcher = request.getRequestDispatcher("discover.jsp?successAlert=" + response.encodeURL("Successfully logged out!"));
            dispatcher.forward(request, response);
        }
    }

}
