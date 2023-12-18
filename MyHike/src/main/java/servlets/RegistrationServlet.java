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


    private boolean isPrintableAscii(String input){
        return input != null && !input.equals("") && input.matches("\\A[\\x21-\\x7E]+\\z");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        User dUser = Database.getUserById(username);
        String messageType = "error";
        String message = "";
        String destination = "registration.jsp";
        boolean redirectIsKnown = false;

        if (dUser != null) {
            message = "Please choose an other username!";
            redirectIsKnown = true;
        }


        if( !redirectIsKnown && !isPrintableAscii(username)) {
            message = "Please use a username with standard characters, numbers, and common symbols. " +
                    "<br>Avoid using characters special symbols.";
            redirectIsKnown = true;

        }

        if (!redirectIsKnown && !isPrintableAscii(password)) {
            message = "Please create a password using only standard characters, numbers, and common symbols. " +
                    "<br>Avoid using characters special symbols.";
            redirectIsKnown = true;
        }

        if (!redirectIsKnown && !(isPrintableAscii(password2) && password.equals(password2))) {
            message = "Password and password confirmation must match. " +
                    "<br>Please ensure both fields contain the same value.";
            redirectIsKnown = true;
        }

        if(!redirectIsKnown) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User();
            user.setUserName(username);
            user.setUserPassword(hashedPassword);
            user.setAdmin(false);

            try {
                Database.insert(user);
            } catch (SQLException e) {
                message = "We encountered a problem during registration. <br> Please try registering again.";
                redirectIsKnown = true;
            }

        }

        if (!redirectIsKnown) {
            message = "Your registration was successful! <br>Welcome " + username + " to MyHike!";
            messageType = "welcome";
            destination = "discover.jsp";
        }

        request.setAttribute(messageType, message);
        RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }
}