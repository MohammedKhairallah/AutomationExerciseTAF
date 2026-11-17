package com.automationexercise.drivers;

import com.automationexercise.utils.actions.AlertActions;
import com.automationexercise.utils.actions.BrowserActions;
import com.automationexercise.utils.actions.ElementActions;
import com.automationexercise.utils.actions.FrameActions;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Validation;
import com.automationexercise.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final String browser = PropertyReader.getProperty("browserType");
    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GUIDriver(){
        LogsManager.info("Initializing GUIDriver with browser: " + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser: " + browserType);
        AbstractDriver abstractDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public ElementActions element() {
        return new ElementActions(getInstance());
    }

    public BrowserActions browser() {
        return new BrowserActions(getInstance());
    }

    public FrameActions frame() {
        return new FrameActions(getInstance());
    }

    public AlertActions alert() {
        return new AlertActions(getInstance());
    }

    //soft assertions
    public Validation validation() {
        return new Validation(getInstance());
    }

    // hard assertions
    public Verification verification() {
        return new Verification(getInstance());
    }


    public WebDriver getInstance() {
        return driverThreadLocal.get();
    }

    public void quitDriver(){
        driverThreadLocal.get().quit();
    }
}
