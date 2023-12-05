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
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && password != null) {
            User user = new User();
            user.setUserName(username);
            user.setUserPassword(password);
            User dUser = Database.getUserLogin(user);
            String destination;
            if (dUser != null && dUser.getUserPassword().equals(password) && dUser.getUserName().equals(username)){
                    session.setAttribute("username", username);
                    destination = "discover.jsp";
                } else {
                    destination = "login.jsp";
                String message = "Invalid username or password";
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
        user.setUserId(id);
        user.setUserName(username);
        user.setUserPassword(hashedPassword);
        user.setAdmin(true);
        user.setUserHikes(null);
        Database.insert(user);
    }
}
