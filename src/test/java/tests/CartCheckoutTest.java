package tests;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RegistrationPage;  // Import the page object

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class CartCheckoutTest {

    WebDriver driver;
    RegistrationPage registrationPage;  // Declare page object

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        TestListener.driver = driver;
        driver.manage().window().maximize();
        driver.get("https://automationteststore.com/");
    }

    @Test
    public void completeCheckoutFlow() throws InterruptedException, IOException {
        // --- Step 1: Add two products ---
        driver.findElement(By.cssSelector("a[data-id='50']")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("a[data-id='52']")).click();
        Thread.sleep(1000);

        // --- Step 2: Go to cart and checkout ---
        driver.get("https://automationteststore.com/index.php?rt=checkout/cart");
        driver.findElement(By.id("cart_checkout1")).click();

        // Click Continue button for "I am a new customer"
        driver.findElement(By.xpath("//*[@id='accountFrm']/fieldset/button")).click();

        // Verify navigation
        Assert.assertTrue(driver.getCurrentUrl().contains("account"), "Should be on account creation page");

        // --- Step 3: Read user data from CSV ---
        String[] user = Files.readAllLines(Paths.get("testdata.csv")).get(0).split(",");

        // Generate unique email + loginname
        String baseEmail = user[2].substring(0, user[2].indexOf("@"));
        String domain = user[2].substring(user[2].indexOf("@"));
        String uniqueEmail = baseEmail + System.currentTimeMillis() + domain;
        String uniqueLogin = "user" + System.currentTimeMillis();

        // --- Step 4: Initialize RegistrationPage and fill form ---
        registrationPage = new RegistrationPage(driver);
        registrationPage.fillCompleteForm(user, uniqueEmail, uniqueLogin);

        // --- Step 5: Submit the form ---
        registrationPage.submitForm();

        // --- Step 6: Verify success ---
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1, .heading1"))
        );

        String actualText = heading.getText().trim();
        System.out.println("Page heading after registration: " + actualText);

        Assert.assertTrue(
                actualText.toLowerCase().contains("account")
                        || actualText.toLowerCase().contains("created")
                        || actualText.toLowerCase().contains("checkout"),
                "Unexpected page after registration! Found heading: " + actualText
        );
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}