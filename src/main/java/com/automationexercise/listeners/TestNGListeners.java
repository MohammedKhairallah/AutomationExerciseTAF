package com.automationexercise.listeners;

import com.automationexercise.FileUtils;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.media.ScreenRecordManager;
import com.automationexercise.media.ScreenshotsManager;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.utils.report.AllureAttachmentManager;
import com.automationexercise.utils.report.AllureConstants;
import com.automationexercise.utils.report.AllureEnvironmentManager;
import com.automationexercise.utils.report.AllureReportGenerator;
import com.automationexercise.validations.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements ISuiteListener, IExecutionListener, IInvokedMethodListener,ITestListener {
    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("Automation Exercise");
        LogsManager.info("TestNG Suite started: " + suite.getName());
    }

    public void onFinish(ISuite suite) {
        LogsManager.info("TestNG Suite finished.");
    }

    /** Invoked before the TestNG run starts. */

    /**
    public void onExecutionStart() {
        LogsManager.info("Test Execution Started.");
        cleanTestOutputDirectories();
        LogsManager.info("Directories cleaned.");
        createTestOutputDirectories();
        LogsManager.info("Directories created.");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded.");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info(" Allure environment set.");
    }
     **/

    public void onExecutionStart() {
        // Close the log file first: Windows cannot delete a file that is still open
        LogManager.shutdown();
        cleanTestOutputDirectories();
        ((LoggerContext) LogManager.getContext(false)).reconfigure();

        LogsManager.info("Test Execution Started.");
        LogsManager.info("Directories cleaned.");
        createTestOutputDirectories();
        LogsManager.info("Directories created.");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded.");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info(" Allure environment set.");
    }

    /** Invoked once all the suites have been run. */
    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test Execution Finished.");
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            ScreenRecordManager.startRecording();
            LogsManager.info("Test Case " + testResult.getName() + " started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod())
        {
            ScreenRecordManager.stopRecording(testResult.getName());
            Validation.assertAll(testResult);

            if (testResult.getInstance() instanceof WebDriverProvider provider){
                driver = provider.getWebDriver();  //initialize driver from WebDriverProvider
            }
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS -> ScreenshotsManager.takeFullPageScreenshot(driver, "passed-" + testResult.getName());
                case ITestResult.FAILURE -> ScreenshotsManager.takeFullPageScreenshot(driver, "failed-" + testResult.getName());
                case ITestResult.SKIP -> ScreenshotsManager.takeFullPageScreenshot(driver, "skipped-" + testResult.getName());
            }

            AllureAttachmentManager.attachLogs();
            AllureAttachmentManager.attachRecords(testResult.getName());
        }
    }
    /**
     * Invoked each time a test succeeds.
     */
    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test case " + result.getName() + " passed.");
    }

    /**
     * Invoked each time a test fails.
     */
    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test case " + result.getName() + " failed.");
    }

    /**
     * Invoked each time a test is skipped.
     */
    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test case " + result.getName() + " skipped.");
    }

    // cleaning and creating dirs (logs, screenshots, recordings, allure-results)
    private void cleanTestOutputDirectories() {
        // Implement logic to clean test output directories
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREENSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
        FileUtils.cleanDirectory(new File("src/test/resources/downloads"));
        FileUtils.forceDelete(new File(LogsManager.LOGS_PATH + File.separator + "logs.log"));
    }

    private void createTestOutputDirectories() {
        // Implement logic to create test output directories
        FileUtils.createDirectory(ScreenshotsManager.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
        FileUtils.createDirectory("src/test/resources/downloads");
    }

}
