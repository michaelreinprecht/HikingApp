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


    private boolean isValidAscii(String input){
        if(input == null) return false;
        return input.matches("\\A\\p{ASCII}{1,}\\z")&&!input.matches("\\A[\\x00-\\x1F\\x7F]+\\z");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");


        //validierung wegen ascii

        if(!isValidAscii(username) || !isValidAscii(password)) {
            request.setAttribute("error", "Username and password must contain valid ASCII characters. Try again!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
            return;
        }


        if(password != null && password.equals(password2)){
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User();
            user.setUserName(username);
            user.setUserPassword(hashedPassword);
            user.setAdmin(true);  //oder false, weiß noch nicht genau wie wir das machen...


            try {
                Database.insert(user);
                response.sendRedirect("login.jsp?success=true");
            } catch (SQLException e) {
                request.setAttribute("error", "Registration failed: " + e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("errror", "Passwords do not match, try again!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request,response);
        }
    }
}