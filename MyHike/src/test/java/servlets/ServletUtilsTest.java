package servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Hike;
import models.Region;
import models.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServletUtilsTest {
    private final ServletUtils servletUtils = new ServletUtils();

    @Test
    void getAltitude() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("altitude")).thenReturn("111");
        Integer altitude = servletUtils.getAltitude(request);
        Integer expectedAltitude = 111;
        assertEquals(expectedAltitude, altitude);
    }

    @Test
    void getDistance() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("distance")).thenReturn("1.11");
        BigDecimal distance = servletUtils.getDistance(request);
        BigDecimal expectedDistance = new BigDecimal("1.11");
        assertEquals(expectedDistance, distance);
    }

    @Test
    void getBase64() {
        // Get the path to the test image file from the test resources
        Path imagePathJpg = Paths.get("src/test/resources/Base64Test.jpg");
        String jpgBase64 = getBase64UsingImage(imagePathJpg);
        String expectedJpgBase64 = "/9j/4AAQSkZJRgABAQEBLAEsAAD//gATQ3JlYXRlZCB3aXRoIEdJTVD/4gKwSUNDX1BST0ZJTEUAAQEAAAKgbGNtcwQwAABtbnRyUkdCIFhZWiAH5wAMAA0ACQAYACZhY3NwTVNGVAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA9tYAAQAAAADTLWxjbXMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1kZXNjAAABIAAAAEBjcHJ0AAABYAAAADZ3dHB0AAABmAAAABRjaGFkAAABrAAAACxyWFlaAAAB2AAAABRiWFlaAAAB7AAAABRnWFlaAAACAAAAABRyVFJDAAACFAAAACBnVFJDAAACFAAAACBiVFJDAAACFAAAACBjaHJtAAACNAAAACRkbW5kAAACWAAAACRkbWRkAAACfAAAACRtbHVjAAAAAAAAAAEAAAAMZW5VUwAAACQAAAAcAEcASQBNAFAAIABiAHUAaQBsAHQALQBpAG4AIABzAFIARwBCbWx1YwAAAAAAAAABAAAADGVuVVMAAAAaAAAAHABQAHUAYgBsAGkAYwAgAEQAbwBtAGEAaQBuAABYWVogAAAAAAAA9tYAAQAAAADTLXNmMzIAAAAAAAEMQgAABd7///MlAAAHkwAA/ZD///uh///9ogAAA9wAAMBuWFlaIAAAAAAAAG+gAAA49QAAA5BYWVogAAAAAAAAJJ8AAA+EAAC2xFhZWiAAAAAAAABilwAAt4cAABjZcGFyYQAAAAAAAwAAAAJmZgAA8qcAAA1ZAAAT0AAACltjaHJtAAAAAAADAAAAAKPXAABUfAAATM0AAJmaAAAmZwAAD1xtbHVjAAAAAAAAAAEAAAAMZW5VUwAAAAgAAAAcAEcASQBNAFBtbHVjAAAAAAAAAAEAAAAMZW5VUwAAAAgAAAAcAHMAUgBHAEL/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAAKAAoDAREAAhEBAxEB/8QAFgABAQEAAAAAAAAAAAAAAAAAAAYH/8QAGAEBAAMBAAAAAAAAAAAAAAAAAAMFBwj/2gAMAwEAAhADEAAAAbHO6UYh0/KP/8QAFxAAAwEAAAAAAAAAAAAAAAAAAAMTFP/aAAgBAQABBQLeo3qLsLsP/8QAHREAAQIHAAAAAAAAAAAAAAAAAAECAxMXUpHi8P/aAAgBAwEBPwGoLuXUqC7l1JEK1MEiFamD/8QAHBEAAgAHAAAAAAAAAAAAAAAAAAEFEhZRUqLS/9oACAECAQE/AaJjOT16KJjOT16JVYlVj//EABkQAAIDAQAAAAAAAAAAAAAAAAACMzWRk//aAAgBAQAGPwKrXqVa9SRtJG0//8QAFxABAAMAAAAAAAAAAAAAAAAAANHw8f/aAAgBAQABPyGohUQ3bdv/2gAMAwEAAgADAAAAEPwP/8QAFxEBAAMAAAAAAAAAAAAAAAAAAHHR8f/aAAgBAwEBPxCQSDCUwlP/xAAXEQADAQAAAAAAAAAAAAAAAAAAYZEQ/9oACAECAQE/EMYYWgtD/8QAFxAAAwEAAAAAAAAAAAAAAAAAAMHwEP/aAAgBAQABPxDNNL5l8z//2Q==";
        assertEquals(jpgBase64, expectedJpgBase64);

        // Get the path to the test image file from the test resources
        Path imagePathPng = Paths.get("src/test/resources/Base64Test.png");
        String pngBase64 = getBase64UsingImage(imagePathPng);
        String expectedPngBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAABhGlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV9TpSotDnYo4pChdrIgKuIoVSyChdJWaNXB5NIvaGJIUlwcBdeCgx+LVQcXZ10dXAVB8APE1cVJ0UVK/F9SaBHjwXE/3t173L0DhGaNqWbPOKBqlpFJJsR8YUUMvCKIEPoRQUxipp7KLuTgOb7u4ePrXZxneZ/7c4SUoskAn0g8y3TDIl4nnt60dM77xGFWkRTic+Ixgy5I/Mh12eU3zmWHBZ4ZNnKZOeIwsVjuYrmLWcVQiaeIo4qqUb6Qd1nhvMVZrdVZ+578hcGitpzlOs0RJLGIFNIQIaOOKmqwEKdVI8VEhvYTHv5hx58ml0yuKhg55rEBFZLjB/+D392apckJNymYAHpfbPtjFAjsAq2GbX8f23brBPA/A1dax7/RBGY+SW90tOgRMLgNXFx3NHkPuNwBIk+6ZEiO5KcplErA+xl9UwEYugUGVt3e2vs4fQBy1NXSDXBwCMTKlL3m8e6+7t7+PdPu7weBp3KtjjzVbQAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAuIwAALiMBeKU/dgAAAAd0SU1FB+cMDQkaErxKBFoAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAARElEQVQY02P8V8zxn4EIwMJozkAUYPz/n0gTP/9kJcpEFsv9EsQp/PWfmTg38p9/S5wbf7IIEWe11tZ7xCnk2vKaKIUA/pQSEcYhZyYAAAAASUVORK5CYII=";
        assertEquals(pngBase64, expectedPngBase64);
    }

    private String getBase64UsingImage(Path imagePath) {
        Part mockPart = mock(Part.class);
        // Read the image content from the file
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            System.out.println("Test failed in generating mock image");
            throw new RuntimeException(e);
        }

        // Create an InputStream from the image content
        try (InputStream imageStream = new ByteArrayInputStream(imageBytes)) {
            // Configure the mock to return the imageStream when getInputStream() is called
            when(mockPart.getInputStream()).thenReturn(imageStream);
            // Call the method you want to test
            String result;
            try {
                result = servletUtils.getBase64(mockPart);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test
    void getHikeBase() {
        Hike hike = new Hike();
        //Set a random UUID for the new Hike.
        String id = UUID.randomUUID().toString();
        hike.setHikeId(id);

        //Mock request
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("name")).thenReturn("Test Hike");
        when(request.getParameter("description")).thenReturn("Testing");
        when(request.getParameter("route-coordinates")).thenReturn("[[10,10][10,10]]");
        when(request.getParameter("distance")).thenReturn("1.11");
        when(request.getParameter("altitude")).thenReturn("111");
        when(request.getParameter("landscape-rating")).thenReturn("5");
        when(request.getParameter("strength-rating")).thenReturn("5");
        when(request.getParameter("stamina-rating")).thenReturn("5");
        when(request.getParameter("difficulty-rating")).thenReturn("5");
        when(request.getParameter("region")).thenReturn("Bregenzerwald");
        when(request.getParameter("duration")).thenReturn("01:00");

        //Mock session
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("username")).thenReturn("admin");
        when(request.getSession()).thenReturn(mockSession);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Time duration = Time.valueOf(LocalTime.parse("01:00", formatter));

        Hike result = servletUtils.getHikeBase(request, hike);
        Hike expectedHike = new Hike(id, "Test Hike", "Testing", "[[10,10][10,10]]", duration, 111, new BigDecimal("1.11"), 5, 5, 5, 5, null, null, result.getHikeRegion(), null, false, new User("admin", "admin", true, null));
        assertEquals(result.toString(), expectedHike.toString());
    }
}