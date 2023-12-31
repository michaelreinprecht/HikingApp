// Generated by Selenium IDE

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
public class AddDeletePOITest {
  private WebDriver driver;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void addDeletePOI() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

    driver.get("http://localhost:8080/MyHike_war_exploded/");
    driver.manage().window().setSize(new Dimension(1936, 1056));

    //Login as admin
    LoginTest loginTest = new LoginTest();
    loginTest.login(driver, wait);

    driver.findElement(By.cssSelector(".row:nth-child(2) > .col-sm-4:nth-child(1) .bg-image")).click();
    driver.findElement(By.cssSelector(".btn:nth-child(7)")).click();
    driver.findElement(By.id("poiTitle")).click();
    driver.findElement(By.id("poiTitle")).sendKeys("Testing");
    driver.findElement(By.id("poiDescription")).sendKeys("Testing");
    driver.findElement(By.id("poiLon")).sendKeys("10");
    driver.findElement(By.id("poiLat")).sendKeys("10");

    //Link to a fixed image inside of the project.
    String fixedFilePath = "src/test/resources/POIImageTest.jpg";
    driver.findElement(By.id("poiImage")).sendKeys(new File(fixedFilePath).getAbsolutePath());

    //Click add and assert the successAlert.
    driver.findElement(By.id("addPOIButton")).click();
    //In this special case we need to wait for the alert to be visible instead of just being present (because this
    //alert is always present, but empty and with a style of display: none
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successAlert")));
    String alertMessage = driver.findElement(By.id("successAlert")).getText();
    Assert.assertEquals("Your point of interest has been successfully added.", alertMessage);

    //Delete the POI again.
    driver.findElement(By.name("deletePOIButton")).click();
  }
}
