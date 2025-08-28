# ECommerce Testing Assignment

Automated test suite for testing an e-commerce website (https://automationteststore.com/) using Selenium WebDriver and TestNG.

## 📋 Test Scenarios Covered

### 1. Homepage & Category Verification
- Navigates to homepage and dynamically detects all main categories
- Selects a random category and verifies it contains at least 3 products
- Prints all available category names

### 2. Product Selection & Cart Addition
- Randomly selects 2 available products from featured items
- Validates stock status and add-to-cart button availability
- Captures product details: Name, Price, URL, Image
- Logs skipped products with reasons in report.txt

### 3. Cart & Checkout Workflow
- Navigates to shopping cart and verifies added items
- Proceeds through checkout process
- Simulates user registration using test data from CSV
- Completes full checkout flow

### 4. Negative Scenario Testing
- Tests form validation by leaving password field empty
- Verifies error messages are displayed correctly
- Captures screenshots of validation failures

## 🛠️ Technologies Used

- **Java 11+**
- **Selenium WebDriver 4.15.0**
- **TestNG 7.8.0**
- **Maven** (Build tool)
- **ChromeDriver** (Browser automation)

## 📁 Project Structure
