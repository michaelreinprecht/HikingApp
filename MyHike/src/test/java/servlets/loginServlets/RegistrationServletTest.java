package servlets.loginServlets;

import database.Database;
import facade.JPAFacade;
import facade.JPAUserFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RegistrationServlet mockServlet= new RegistrationServlet();

    private void setup(String username, String password, String confirmPassword){
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getParameter("confirmPassword")).thenReturn(confirmPassword);
    }

    //It tests whether the username already exists in the database.
    @Test
    void usernameExistInDatabase() throws Exception {
        setup("admin", "admin", "admin");
        mockServlet.doPost(request, response);
        assertTrue(!mockServlet.getError().isEmpty());
    }

    // It tests that the password does not match the confirmed password.
    @Test
    void passwortMissMach() throws Exception {
        setup("admins", "admi", "admin");
        mockServlet.doPost(request, response);
        assertTrue(!mockServlet.getError().isEmpty());
    }

    // It verifies that the username does not adhere to the expected regex pattern.
    @Test
    void notValidName() throws Exception {
        setup("ad min", "admi", "admin");
        mockServlet.doPost(request, response);
        assertTrue(!mockServlet.getError().isEmpty());
    }

    // It verifies that the password does not adhere to the expected regex pattern.
    @Test
    void notValidPasswort() throws Exception {
        setup("admins", "ad min", "ad min");
        mockServlet.doPost(request, response);
        assertTrue(!mockServlet.getError().isEmpty());
    }


    //It checks whether the user registration is functioning properly with the right data.
    @Test
    void registration() throws Exception {
        setup("test", "test", "test");
        Database.facade = mock(JPAFacade.class);
        JPAUserFacade mockUserFacade = mock(JPAUserFacade.class);
        Database.userFacade = mockUserFacade;
        when(Database.getUserById(any(String.class))).thenReturn(null);
        mockServlet.doPost(request, response);
        assertTrue(mockServlet.getError().isEmpty());
    }
}
