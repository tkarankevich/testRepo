package framework.tests;

import framework.Logger;
import framework.api.APIAddResultForCase;
import framework.api.APICreateRun;
import framework.api.APIException;
import framework.api.Reporter;
import framework.browser.Browser;
import framework.configurations.PropertiesResourceManager;
import framework.enums.TestRailResults;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTest {

    protected TestInfo testInfo;
    private static PropertiesResourceManager manager = new PropertiesResourceManager("configuration");
    private static String reportVal = manager.getSystemProperty("report");
    private static String screenShotPath = manager.getSystemProperty("screenShotPath");

    @BeforeSuite
    public void createTestRun() {
        if (reportVal.equalsIgnoreCase("true")) {
            try {
                Object runId = APICreateRun.createTestRun().get("id");
                Reporter.getReporter().setRunId(runId);
            } catch (IOException | APIException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void run();

    @BeforeTest
    public void setUp() {
        Browser.getInstance().manage().window().maximize();
    }

    @AfterTest
    public void end() {
        Browser.getInstance().quit();
    }

    @Test
    public void test() {
        Browser.getStartPage();
        run();
    }

    protected void log(String message) {
        Logger.getLogger().info(message);
    }

    @AfterMethod
    public void report(ITestResult result) {
        if (reportVal.equalsIgnoreCase("true")) {
            try {
                Object runId = Reporter.getReporter().getRunId();
                APIAddResultForCase.addResultForCase(runId, testInfo.id(), getCaseResult(result), testInfo.comment());
            } catch (IOException | APIException e) {
                e.printStackTrace();
            }
        }
    }

    private int getCaseResult(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            return TestRailResults.FAILED.getCaseStatus();
        } else if (testResult.getStatus() == ITestResult.SUCCESS) {
            return TestRailResults.PASSED.getCaseStatus();
        }
        return testResult.getStatus();
    }

    public void takeScreenshot(){
        File scrFile = ((TakesScreenshot)Browser.getInstance()).getScreenshotAs(OutputType.FILE);
        try {
            Date dateNow = new Date();
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd_hhmmss");
            FileUtils.copyFile(scrFile, new File(String.format("%s/screenshot_%s.jpg", screenShotPath, formatDate.format(dateNow))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
