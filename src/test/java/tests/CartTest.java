package tests;

import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartTest {

    WebDriver driver;
    FileWriter report;

    // CSS Selectors
    String productCards = "div.col-md-3.col-sm-6.col-xs-12";
    String productName = "a.prdocutname";
    String productPriceOne = ".oneprice";
    String productPriceNew = ".pricenew";
    String addButton = "a.productcart";
    String outOfStockIndicator = "span.nostock";

    @BeforeMethod
    public void setup() throws IOException {
        driver = new ChromeDriver();
        TestListener.driver = driver;
        driver.manage().window().maximize();
        driver.get("https://automationteststore.com/");

        // Initialize report file
        report = new FileWriter("report.txt", true);
        report.write("\n--- Test Started ---\n");
    }

    @Test
    public void selectTwoRandomProducts() throws IOException, InterruptedException {
        List<WebElement> products = driver.findElements(By.cssSelector(productCards));
        report.write("Found " + products.size() + " products on the page\n");

        // Shuffle the list for randomness
        List<WebElement> productsCopy = new ArrayList<>(products);
        Collections.shuffle(productsCopy);

        int productsAdded = 0;

        for (WebElement product : productsCopy) {
            if (productsAdded >= 2) break;

            try {
                String name = product.findElement(By.cssSelector(productName)).getText();

                String price;
                try {
                    price = product.findElement(By.cssSelector(productPriceOne)).getText();
                } catch (Exception e1) {
                    try {
                        price = product.findElement(By.cssSelector(productPriceNew)).getText();
                    } catch (Exception e2) {
                        price = "Price not available";
                    }
                }

                String url = product.findElement(By.cssSelector(productName)).getAttribute("href");
                String imageUrl = product.findElement(By.cssSelector("img")).getAttribute("src");

                boolean isOutOfStock = !product.findElements(By.cssSelector(outOfStockIndicator)).isEmpty();
                boolean hasAddButton = !product.findElements(By.cssSelector(addButton)).isEmpty();

                if (isOutOfStock || !hasAddButton) {
                    report.write("❌ SKIPPED: " + name +
                            " | Price: " + price +
                            " | URL: " + url +
                            " | Image: " + imageUrl +
                            " | Reason: " + (isOutOfStock ? "Out of Stock" : "No Add Button") + "\n");
                    continue;
                }

                // Click Add-to-Cart
                product.findElement(By.cssSelector(addButton)).click();
                Thread.sleep(2000); // wait for cart to update

                report.write("✅ ADDED: " + name +
                        " | Price: " + price +
                        " | URL: " + url +
                        " | Image: " + imageUrl + "\n");

                productsAdded++;

            } catch (Exception e) {
                report.write("❌ ERROR: " + e.getMessage() + "\n");
            }
        }

        report.write("Total products added: " + productsAdded + "\n");
        report.flush();
    }

    @AfterMethod
    public void tearDown() throws IOException {
        if (driver != null) {
            driver.quit();
        }
        if (report != null) {
            report.write("--- Test Ended ---\n");
            report.close();
        }
    }
}
