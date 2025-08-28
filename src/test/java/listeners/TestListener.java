package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class TestListener implements ITestListener {

    private static final String REPORT_PATH = "report.txt";
    private static final String SCREENSHOT_DIR = "screenshots/";

    // This will be set from your test classes
    public static WebDriver driver;

    @Override
    public void onTestSuccess(ITestResult result) {
        logToReport(result.getName() + " PASSED ✅");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logToReport(result.getName() + " FAILED ❌ : " + result.getThrowable());
        captureScreenshot(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logToReport(result.getName() + " SKIPPED ⚠️");
    }

    @Override
    public void onStart(ITestContext context) {
        logToReport("===== TEST SUITE STARTED =====");
        createFolder(SCREENSHOT_DIR);
    }

    @Override
    public void onFinish(ITestContext context) {
        logToReport("===== TEST SUITE FINISHED =====");
    }

    // --- Helpers ---

    private void logToReport(String message) {
        try (FileWriter fw = new FileWriter(REPORT_PATH, true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void captureScreenshot(String testName) {
        if (driver != null) {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                File dest = new File(SCREENSHOT_DIR + testName + ".png");
                Files.copy(src.toPath(), dest.toPath());
                logToReport("Screenshot saved: " + dest.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createFolder(String path) {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
    }
}
