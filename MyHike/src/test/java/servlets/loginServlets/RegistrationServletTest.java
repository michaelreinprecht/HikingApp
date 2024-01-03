package servlets.loginServlets;

import database.Database;
import facade.JPAFacade;
import facade.JPAUserFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistrationServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RegistrationServlet registrationServlet = new RegistrationServlet();

    // Verifies if the given string consists of one or more printable ascii characters
    @Test
    void isPrintableAscii() {
        //Case: Given string consists of printable ascii
        assertTrue(registrationServlet.isPrintableAscii("ascii"));

        //Case: Given string doesn't consist of printable ascii
        assertFalse(registrationServlet.isPrintableAscii("asc ii"));
    }

    // Verifies whether the username already exists in the database.
    @Test
    void usernameAvailable() throws SQLException {
        Database.userFacade = mock(JPAUserFacade.class);
        when(Database.userFacade.getUserById("nameNotTaken")).thenReturn(null);
        registrationServlet.usernameAvailable("nameNotTaken");
        assertTrue(registrationServlet.getError().isEmpty());
    }

    // Verifies if the username does not adhere to the expected regex pattern.
    @Test
    void usernameIsValid() {
        //Case: Username is valid
        registrationServlet.usernameIsValid("admin");
        assertTrue(registrationServlet.getError().isEmpty());

        //Case: Username does not consist of printable ascii -> not valid
        registrationServlet.usernameIsValid("adm in");
        assertFalse(registrationServlet.getError().isEmpty());
    }

    // It verifies that the password does not adhere to the expected regex pattern.
    @Test
    void passwordIsValid() {
        //Case: Password is valid
        registrationServlet.usernameIsValid("secret");
        assertTrue(registrationServlet.getError().isEmpty());

        //Case: Password does not consist of printable ascii -> not valid
        registrationServlet.usernameIsValid("sec ret");
        assertFalse(registrationServlet.getError().isEmpty());
    }

    // Verifies if the password does not match the confirmed password.
    @Test
    void passwordsMatch() {
        //Case: Passwords to match
        registrationServlet.passwordsMatch("secret", "secret");
        assertTrue(registrationServlet.getError().isEmpty());

        //Case: Passwords do not match
        registrationServlet.passwordsMatch("secret1", "secret2");
        assertFalse(registrationServlet.getError().isEmpty());
    }

    // Checks whether the user registration is functioning properly with the right data.
    @Test
    void registerUser() throws Exception {
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getParameter("confirmPassword")).thenReturn("test");

        //Mock database
        Database.facade = mock(JPAFacade.class);
        JPAUserFacade mockUserFacade;
        mockUserFacade = mock(JPAUserFacade.class);
        Database.userFacade = mockUserFacade;
        //Always return null -> act as if every username is currently available.
        when(Database.getUserById(any(String.class))).thenReturn(null);

        registrationServlet.registerUser(request, response);
        assertTrue(registrationServlet.getError().isEmpty());
    }
}
