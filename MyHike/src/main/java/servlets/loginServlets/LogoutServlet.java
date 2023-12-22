package servlets.loginServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@SuppressWarnings("LombokGetterMayBeUsed")
@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
    private String error;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logoutUser(request, response);
    }

    //Logs out the current user by removing the session attributes for username and isAdmin.
    protected void logoutUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        error = "";
        HttpSession session =request.getSession();
        if (session != null) {
            session.removeAttribute("username");
            session.removeAttribute("isAdmin");
            RequestDispatcher dispatcher = request.getRequestDispatcher("discover.jsp?successAlert=" + response.encodeURL("Successfully logged out!"));
            dispatcher.forward(request, response);
        } else {
            error = "Failed to perform logout.";
            RequestDispatcher dispatcher = request.getRequestDispatcher("discover.jsp?error=" + response.encodeURL(error));
            dispatcher.forward(request, response);
        }
    }

    public String getError() {
        return error;
    }
}
