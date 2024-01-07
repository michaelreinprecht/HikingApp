package servlets.loginServlets;

import database.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("LombokGetterMayBeUsed")
@WebServlet(name = "RegistrationServlet", value = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private String error = "";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        registerUser(request, response);
    }

    protected void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        error = "";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (registerInfoIsValid(username, password, confirmPassword, response)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User();
            user.setUserName(username);
            user.setUserPassword(hashedPassword);
            user.setAdmin(false);

            try {
                Database.insert(user);
            } catch (SQLException e) {


                String errorMessage = "We encountered a problem during registration.<br>Please try registering again.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("errorPage.jsp").forward(request, response);


            }

            if (!error.isEmpty()) {
                response.sendRedirect("registration.jsp?error=" + response.encodeURL(error));
            } else {
                response.sendRedirect("discover.jsp?successAlert=" + response.encodeURL("Your registration was successful! <br>Welcome " + username + " to MyHike!"));
            }
        }
    }

    //Uses underlying methods to check if the provided register info is valid. If register info is not valid, returns
    //false, and redirects back to registration.jsp with the error set by underlying methods.
    protected boolean registerInfoIsValid(String username, String password, String confirmPassword, HttpServletResponse response) throws IOException {
        if (usernameAvailable(username) &&
                usernameIsValid(username) &&
                passwordIsValid(password) &&
                passwordsMatch(password, confirmPassword)) {
            return true;
        }
        response.sendRedirect("registration.jsp?error=" + response.encodeURL(error));
        return false;
    }

    //Checks if the entered username is already claimed by another user. Sets error accordingly if it is.
    protected boolean usernameAvailable(String username) {
        User userInDb = null;
        try {
            userInDb = Database.getUserById(username);
        } catch (SQLException e) {
            error = "An error occurred when trying to check for username availability, please try again later.";
        }
        if (userInDb != null) {
            error = "User with username " + username + " already exists.<br>Please choose another username!";
            return false;
        }
        return true;
    }

    //Checks if the username consists of valid/printable ascii characters. Sets error accordingly if it doesn't.
    protected boolean usernameIsValid(String username) {
        if (!isPrintableAscii(username)) {
            error = "Please use a username with standard characters, numbers, and common symbols.";
            return false;
        }
        return true;
    }

    //Checks if the password consists of valid/printable ascii characters. Sets error accordingly if it doesn't.
    protected boolean passwordIsValid(String password) {
        if (!isPrintableAscii(password)) {
            error = "Please create a password using only standard characters, numbers, and common symbols.";
            return false;
        }
        return true;
    }

    //Checks if the 2 entered passwords match. Sets error accordingly if they don't.
    protected boolean passwordsMatch(String password, String confirmPassword) {
        if (!(isPrintableAscii(confirmPassword) && password.equals(confirmPassword))) {
            error = "Password and password confirmation must match. " +
                    "<br>Please ensure both fields contain the same value.";
            return false;
        }
        return true;
    }

    //ensures that the entire string consists of one or more printable ASCII characters,
    //meaning characters that are visible and typically used for display (for example no spacebar)
    protected boolean isPrintableAscii(String input) {
        return input != null && !input.isEmpty() && input.matches("\\A[\\x21-\\x7E]+\\z");
    }

    public String getError() {
        return error;
    }
}