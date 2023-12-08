package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import myHikeJava.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && password != null) {
            //Database should return one user
            User dUser = Database.getUserByName(username);
            String destination;
            String message;
            if (dUser != null && BCrypt.checkpw(password, dUser.getUserPassword()) && dUser.getUserName().equals(username)){
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


            //addUser(username, password);

            RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
            dispatcher.forward(request, response);

        }
    }

    public void addUser(String username, String password){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User();
        String id = UUID.randomUUID().toString();
        user.setUserName(username);
        user.setUserPassword(hashedPassword);
        user.setAdmin(true);
        user.setUserHikes(null);
        Database.insert(user);
    }
}
