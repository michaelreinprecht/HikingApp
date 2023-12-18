package servlets;

import database.Database;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegistrationServlet", value = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private String error;

    private boolean isPrintableAscii(String input) {
        //ensures that the entire string consists of one or more printable ASCII characters,
        //meaning characters that are visible and typically used for display (for example no spacebar)
        return input != null && !input.equals("") && input.matches("\\A[\\x21-\\x7E]+\\z");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        error = "";

        String username = request.getParameter("username"); //admin
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        User userInDb = Database.getUserById(username); //admin
        String destination = "registration.jsp";

        if (userInDb != null) {
            error = "User with username " + username + "already exists.<br>Please choose another username!";
            response.sendRedirect(destination + "?error=" + response.encodeURL(error));
            return;
        }


        if (!isPrintableAscii(username)) {
            error = "Please use a username with standard characters, numbers, and common symbols.";
            response.sendRedirect(destination + "?error=" + response.encodeURL(error));
            return;
        }

        if (!isPrintableAscii(password)) {
            error = "Please create a password using only standard characters, numbers, and common symbols.";
            response.sendRedirect(destination + "?error=" + response.encodeURL(error));
            return;
        }

        if (!(isPrintableAscii(confirmPassword) && password.equals(confirmPassword))) {
            error = "Password and password confirmation must match. " +
                    "<br>Please ensure both fields contain the same value.";
            response.sendRedirect(destination + "?error=" + response.encodeURL(error));
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();
        user.setUserName(username);
        user.setUserPassword(hashedPassword);
        user.setAdmin(false);

        try {
            Database.insert(user);
        } catch (SQLException e) {
            error = "We encountered a problem during registration.<br>Please try registering again.";
        }

        if (!error.isEmpty()) {
            response.sendRedirect(destination + "?error=" + response.encodeURL(error));
        } else {
            destination = "discover.jsp";
            response.sendRedirect(destination + "?successAlert=" + response.encodeURL("Your registration was successful! <br>Welcome " + username + " to MyHike!"));
        }
    }

    public String getError() {
        return error;
    }
}