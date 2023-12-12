// Generated by Selenium IDE
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class AddDeleteCommentTest {
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
  public void comments() {
    //TODO Mock database/fix mocking

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

    driver.get("http://localhost:8080/MyHike_war_exploded/");
    driver.manage().window().setSize(new Dimension(1936, 1056));

    //Login as admin
    AdminLoginTest loginTest = new AdminLoginTest();
    loginTest.login(driver, wait);

    driver.findElement(By.cssSelector(".row:nth-child(2) > .col-sm-4:nth-child(1) .bg-image > .card-body")).click();
    driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();
    driver.findElement(By.id("commentDescription")).click();
    driver.findElement(By.id("commentDescription")).sendKeys("Adding a new comment.");
    driver.findElement(By.cssSelector(".col-md > .btn")).click();
    driver.findElement(By.cssSelector(".alert:nth-child(2)")).click();
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert:nth-child(2)")));
    String alertMessage = driver.findElement(By.cssSelector(".alert:nth-child(2)")).getText();
    Assert.assertEquals("Successfully added your comment!", alertMessage);

    driver.findElement(By.cssSelector(".btn:nth-child(5)")).click();
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".col-md-1 > a > img")));
    driver.findElement(By.cssSelector(".col-md-1 > a > img")).click();
    driver.findElement(By.cssSelector(".alert:nth-child(2)")).click();
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert:nth-child(2)")));
    alertMessage = driver.findElement(By.cssSelector(".alert:nth-child(2)")).getText();
    Assert.assertEquals("Successfully deleted your comment!", alertMessage);
  }
}
