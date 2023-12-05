package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import myHikeJava.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //addUser(username, password);

        RequestDispatcher dispatcher = request.getRequestDispatcher("discover.jsp");
        dispatcher.forward(request, response);
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
