package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage {
    private WebDriver driver;

    // Locators
    private By firstName = By.id("AccountFrm_firstname");
    private By lastName = By.id("AccountFrm_lastname");
    private By email = By.id("AccountFrm_email");
    private By telephone = By.id("AccountFrm_telephone");
    private By address = By.id("AccountFrm_address_1");
    private By city = By.id("AccountFrm_city");
    private By postcode = By.id("AccountFrm_postcode");
    private By country = By.id("AccountFrm_country_id");
    private By zone = By.id("AccountFrm_zone_id");
    private By loginName = By.id("AccountFrm_loginname");
    private By password = By.id("AccountFrm_password");
    private By confirmPassword = By.id("AccountFrm_confirm");
    private By newsletterYes = By.id("AccountFrm_newsletter1");
    private By newsletterNo = By.id("AccountFrm_newsletter0");
    private By agreeCheckbox = By.id("AccountFrm_agree");
    private By continueButton = By.cssSelector("button[title='Continue']");
    private By errorMessage = By.cssSelector(".alert.alert-error");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    // Fill personal details
    public void fillPersonalDetails(String fName, String lName, String emailAddr, String phone) {
        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(email).sendKeys(emailAddr);
        driver.findElement(telephone).sendKeys(phone);
    }

    // Fill address details
    public void fillAddressDetails(String addr, String cityName, String zipCode, String countryName, String zoneName) {
        driver.findElement(address).sendKeys(addr);
        driver.findElement(city).sendKeys(cityName);
        driver.findElement(postcode).sendKeys(zipCode);

        Select countrySelect = new Select(driver.findElement(country));
        countrySelect.selectByVisibleText(countryName);

        // Wait a moment for zones to load
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        Select zoneSelect = new Select(driver.findElement(zone));
        zoneSelect.selectByVisibleText(zoneName);
    }

    // Fill login details
    public void fillLoginDetails(String username, String pwd, String confirmPwd) {
        driver.findElement(loginName).sendKeys(username);
        if (pwd != null) driver.findElement(password).sendKeys(pwd);
        if (confirmPwd != null) driver.findElement(confirmPassword).sendKeys(confirmPwd);
    }

    // Select newsletter option
    public void selectNewsletter(boolean subscribe) {
        if (subscribe) {
            driver.findElement(newsletterYes).click();
        } else {
            driver.findElement(newsletterNo).click();
        }
    }

    // Agree to terms
    public void agreeToTerms() {
        driver.findElement(agreeCheckbox).click();
    }

    // Submit form
    public void submitForm() {
        driver.findElement(continueButton).click();
    }

    // Get error message
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    // Check if error message is displayed
    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    // Fill complete form (for positive tests)
    public void fillCompleteForm(String[] userData, String uniqueEmail, String uniqueUsername) {
        fillPersonalDetails(userData[0], userData[1], uniqueEmail, userData[3]);
        fillAddressDetails(userData[4], userData[5], userData[6], "United Kingdom", "Aberdeen");
        fillLoginDetails(uniqueUsername, userData[7], userData[7]);
        selectNewsletter(true);
        agreeToTerms();
    }
}