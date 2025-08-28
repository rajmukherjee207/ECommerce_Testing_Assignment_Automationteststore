package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class HomepageCategoryTest {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void verifyCategoriesAndProducts() {
        // 1. Navigate to homepage
        driver.get("https://automationteststore.com/");

        // 2. Get all main category elements
        List<WebElement> categories = driver.findElements(By.xpath("//ul[@class='nav-pills categorymenu']/li/a[not(@class='menu_home')]"));

        // Print category names dynamically
        System.out.println("Main Categories:");
        for (WebElement category : categories) {
            System.out.println(category.getText().trim());
        }

        // 3. Pick a random category dynamically
        Random random = new Random();
        WebElement randomCategory = categories.get(random.nextInt(categories.size()));
        String categoryName = randomCategory.getText().trim();
        System.out.println("Navigating to category: " + categoryName);

        // Click the category
        randomCategory.click();

        // 4. Verify category has at least 3 visible products
        List<WebElement> products = driver.findElements(By.cssSelector(".thumbnail")); // Assuming each product has 'thumbnail' class
        System.out.println("Number of products found: " + products.size());
        Assert.assertTrue(products.size() >= 3, "Category " + categoryName + " has less than 3 products!");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
