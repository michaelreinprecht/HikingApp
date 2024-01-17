package servlets.hikeServlets;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;


public class FilterTest {
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
  public void filter() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

    driver.get("http://localhost:8080/MyHike_war_exploded/");
    driver.manage().window().setSize(new Dimension(1722, 1034));

    driver.findElement(By.name("searchQuery")).click();
    driver.findElement(By.name("searchQuery")).sendKeys("Test");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchButton")));
    driver.findElement(By.id("searchButton")).click();

    driver.findElement(By.id("altitudeFilter")).sendKeys("111");
    driver.findElement(By.id("FilterButton")).click();
    assertEquals("Verify filter applied correctly", "111", driver.findElement(By.id("altitudeFilter")).getAttribute("value"));
    driver.findElement(By.id("altitudeFilter")).clear();
    assertEquals("Verify filter reset correctly", "", driver.findElement(By.id("altitudeFilter")).getAttribute("value"));

    driver.findElement(By.id("staminaFilter")).sendKeys("3");
    driver.findElement(By.id("FilterButton")).click();
    assertEquals("Verify filter applied correctly", "3", driver.findElement(By.id("staminaFilter")).getAttribute("value"));
    driver.findElement(By.id("staminaFilter")).clear();
    assertEquals("Verify filter reset correctly", "", driver.findElement(By.id("staminaFilter")).getAttribute("value"));

    driver.findElement(By.id("strengthFilter")).sendKeys("3");
    driver.findElement(By.id("FilterButton")).click();
    assertEquals("Verify filter applied correctly", "3", driver.findElement(By.id("strengthFilter")).getAttribute("value"));
    driver.findElement(By.id("strengthFilter")).clear();
    assertEquals("Verify filter reset correctly", "", driver.findElement(By.id("strengthFilter")).getAttribute("value"));

    driver.findElement(By.id("landscapeFilter")).sendKeys("3");
    driver.findElement(By.id("FilterButton")).click();
    assertEquals("Verify filter applied correctly", "3", driver.findElement(By.id("landscapeFilter")).getAttribute("value"));
    driver.findElement(By.id("landscapeFilter")).clear();
    assertEquals("Verify filter reset correctly", "", driver.findElement(By.id("landscapeFilter")).getAttribute("value"));

  }
}