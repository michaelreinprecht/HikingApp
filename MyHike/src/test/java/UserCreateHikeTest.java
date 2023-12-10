// Generated by Selenium IDE
import database.Database;
import facade.JPAFacade;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.io.File;
import java.time.Duration;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class UserCreateHikeTest {
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
  public void tearDown() { driver.quit(); }

  //Tests if a user is able to properly create a hike. For this the user first needs to log in.
  //This test uses images/beispiel_berge.jpg as a fixed image. Also uses our default user account.
  @Test
  public void userCreateHike() {
    //Mocking database and removing functionality from insert
    Database.facade = mock(JPAFacade.class);
    doNothing().when(Database.facade).insert(any(Hike.class));

    driver.get("http://localhost:8080/MyHike_war_exploded/discover.jsp");
    driver.manage().window().setSize(new Dimension(1936, 1056));
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout
    wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Create Hike")));
    driver.findElement(By.linkText("Create Hike")).click();
    driver.findElement(By.name("username")).click();
    driver.findElement(By.name("username")).sendKeys("user");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.cssSelector(".svg-inline--fa")).click();
    driver.findElement(By.linkText("Create Hike")).click();
    driver.findElement(By.id("map")).click();
    driver.findElement(By.id("map")).click();
    driver.findElement(By.id("map")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Selenium Test User");
    driver.findElement(By.id("altitude")).click();
    driver.findElement(By.id("altitude")).sendKeys("8");
    driver.findElement(By.id("distance")).sendKeys("8");
    driver.findElement(By.id("duration")).sendKeys("08:00");
    driver.findElement(By.id("April")).click();
    driver.findElement(By.id("June")).click();
    driver.findElement(By.id("July")).click();
    driver.findElement(By.cssSelector(".landscape-rating:nth-child(2) path")).click();
    driver.findElement(By.cssSelector(".strength-rating:nth-child(2) > .svg-inline--fa")).click();
    driver.findElement(By.cssSelector(".stamina-rating:nth-child(2) path")).click();
    driver.findElement(By.cssSelector(".difficulty-rating:nth-child(2) path")).click();
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).sendKeys("Testing");
    //TODO somehow replace this path with intern image or sth like that
    String fixedFilePath = "src/main/webapp/images/beispiel_berge.jpg";
    driver.findElement(By.id("fileToUpload")).sendKeys(new File(fixedFilePath).getAbsolutePath());
    driver.findElement(By.cssSelector(".btn")).click();

    //Wait until the alert window pops up and check if alert is positive about the hike being created.
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert")));
    String alertMessage = driver.findElement(By.cssSelector(".alert")).getText();
    System.out.println(alertMessage);
    Assert.assertEquals("Successfully created your new hike - you should now be able to view it in 'Your Hikes' or find it using the search function.", alertMessage);
  }
}
