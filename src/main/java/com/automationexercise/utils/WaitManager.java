package com.automationexercise.utils;

import com.automationexercise.utils.dataReader.PropertyReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Utility class that provides a pre-configured Selenium {@link FluentWait}.
 * <p>
 * It centralizes the wait settings (timeout, polling interval, ignored exceptions)
 * so every page or test uses the same waiting behavior instead of repeating the
 * configuration or relying on Thread.sleep().
 */
public class WaitManager {
    /** The WebDriver instance that the wait will be applied to. */
    private WebDriver driver;

    /**
     * Creates a new WaitManager for the given driver.
     *
     * @param driver the WebDriver instance used to build the wait
     */
    public WaitManager(WebDriver driver){
        this.driver = driver;
    }


    public FluentWait<WebDriver>fluentWait(){
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("DEFAULT_WAIT"))))
                .pollingEvery(Duration.ofMillis(100))
                .ignoreAll(getExceptions());
    }

    /**
     * Returns the exceptions that the wait should ignore while polling.
     * These are temporary conditions that usually disappear once the page
     * finishes loading or re-rendering:
     * <ul>
     *     <li>{@link NoSuchElementException}: the element is not in the DOM yet</li>
     *     <li>{@link StaleElementReferenceException}: the element was refreshed or removed from the DOM</li>
     *     <li>{@link ElementNotInteractableException}: the element exists but is not ready for interaction</li>
     *     <li>{@link ElementClickInterceptedException}: another element (e.g., a loader or popup) is covering it</li>
     * </ul>
     *
     * @return a list of exception classes to ignore during polling
     */
    private ArrayList<Class<? extends Exception>> getExceptions(){
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<>();
        exceptions.add(NoSuchElementException.class);
        exceptions.add(StaleElementReferenceException.class);
        exceptions.add(ElementNotInteractableException.class);
        exceptions.add(ElementClickInterceptedException.class);

        return exceptions;
    }

}
