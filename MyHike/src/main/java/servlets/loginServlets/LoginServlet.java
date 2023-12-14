package servlets.loginServlets;

import database.Database;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginUser(request, response);
    }

    //Attempts to log in the user with the username and password passed in the request. Redirects back to login page
    //and displays error message if login is unsuccessful, otherwise redirects to discover page and displays welcome message.
    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && password != null) {
            //Database should return one user
            User dUser = Database.getUserById(username);
            String destination;
            String message;
            if (dUser != null && BCrypt.checkpw(password, dUser.getUserPassword()) && dUser.getUserName().equals(username)) {
                session.setAttribute("username", username);
                session.setAttribute("isAdmin", dUser.isAdmin());
                message = "Welcome " + username + "!";
                request.setAttribute("welcome", message);
                destination = "discover.jsp";
            } else {
                destination = "login.jsp";
                message = "Invalid username or password";
                request.setAttribute("error", message);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
            dispatcher.forward(request, response);
        }
    }

    //TODO remove on final cleanup, just leaving it here in case we add a register page
    public void addUser(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();
        user.setUserName(username);
        user.setUserPassword(hashedPassword);
        user.setAdmin(true);
        user.setUserHikes(null);
        try {
            Database.insert(user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
