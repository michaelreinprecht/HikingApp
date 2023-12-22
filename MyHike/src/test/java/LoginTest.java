// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class LoginTest {
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
    public void login() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

        driver.get("http://localhost:8080/MyHike_war_exploded/");
        driver.manage().window().setSize(new Dimension(1936, 1056));

        login(driver, wait);
    }

    //Login using admin user
    public void login(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Login")));
        driver.findElement(By.linkText("Login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
    }
}
