// Generated by Selenium IDE
import database.Database;
import facade.JPAFacade;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.io.File;
import java.time.Duration;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class AdminDeleteHikeTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void adminDeleteHike() {
        //Mocking database and removing functionality from insert
        Database.facade = mock(JPAFacade.class);
        doNothing().when(Database.facade).delete(any(Hike.class));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

        driver.get("http://localhost:8080/MyHike_war_exploded/");
        driver.manage().window().setSize(new Dimension(1936, 1056));

        //Login as admin
        AdminLoginTest loginTest = new AdminLoginTest();
        loginTest.login(driver, wait);

        //Try to edit the first hike showing up on discover page.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".row:nth-child(2) > .col-sm-4:nth-child(1) .bg-image")));
        driver.findElement(By.cssSelector(".row:nth-child(2) > .col-sm-4:nth-child(1) .bg-image")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("deleteButton")));
        driver.findElement(By.id("deleteButton")).click();
        assertThat(driver.switchTo().alert().getText(), is("Are you sure that you want to delete this hike entry?"));
        driver.switchTo().alert().accept();

        //Wait until the alert window pops up and check if alert is positive about the hike being successfully deleted.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert")));
        String alertMessage = driver.findElement(By.cssSelector(".alert")).getText();
        System.out.println(alertMessage);
        Assert.assertEquals("Successfully deleted your hike!", alertMessage);
    }
}
