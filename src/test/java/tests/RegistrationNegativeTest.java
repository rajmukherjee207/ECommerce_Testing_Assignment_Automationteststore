package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RegistrationPage;  // Import the page object

import java.io.IOException;
import java.time.Duration;

public class RegistrationNegativeTest {
    WebDriver driver;
    RegistrationPage registrationPage;  // Declare page object

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testRegistrationWithMissingPassword() throws IOException {
        driver.get("https://automationteststore.com/index.php?rt=account/create");

        // Initialize RegistrationPage
        registrationPage = new RegistrationPage(driver);

        // Fill all fields EXCEPT password (leave it null)
        registrationPage.fillPersonalDetails("John", "Doe",
                "john" + System.currentTimeMillis() + "@mail.com", "9876543210");

        registrationPage.fillAddressDetails("123 Main Street", "London", "SW1A1AA",
                "United Kingdom", "Aberdeen");

        registrationPage.fillLoginDetails("testuser" + System.currentTimeMillis(),
                null, null);  // Password intentionally null

        registrationPage.selectNewsletter(true);
        registrationPage.agreeToTerms();

        // Submit form
        registrationPage.submitForm();

        // Verify error message
        Assert.assertTrue(registrationPage.isErrorMessageDisplayed(),
                "❌ Validation error not displayed!");

        String errorText = registrationPage.getErrorMessage();
        Assert.assertTrue(errorText.contains("Password"),
                "Error should mention password: " + errorText);

        System.out.println("✅ Negative Test Passed: Validation message is shown");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}