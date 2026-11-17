package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

public class ElementActions {
    //variable
    private final WebDriver driver;
    private WaitManager waitManager;


    //constructor
    public ElementActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //clicking
    public ElementActions click(By locator){
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        // Wait until the element is stable (not moving)
                        Point initialLocation = element.getLocation();
                        LogsManager.info("initialLocation: " + initialLocation);
                        Point finalLocation = element.getLocation();
                        LogsManager.info("finalLocation: " + finalLocation);
                        if (!initialLocation.equals(finalLocation)) {
                            return false; // still moving, wait longer
                        }
                        element.click();
                        LogsManager.info("Clicked on element: " + locator);
                        return true;
                    }catch (Exception e){
                        return false;
                    }
                }
        );
        return this;
    }


    //typing
    public ElementActions type(By locator, String text){
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();
                        element.sendKeys(text);
                        LogsManager.info("Typed text '" + text + "' into element: " + locator);
                        return true;
                    }catch (Exception e){
                        return false;
                    }
                }
        );
        return this;
    }

    //hovering
    public ElementActions hover(By locator) {
        waitManager.fluentWait().until( d -> {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                new Actions(d).moveToElement(element).perform();
                LogsManager.info("Hovered over element: " + locator);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        return this;
    }

    //Getting text
    public String getText(By locator){
        return waitManager.fluentWait().until(d ->
               {
                   try {
                       WebElement element = d.findElement(locator);
                       scrollToElementJS(locator);
                       String msg =  element.getText();
                       LogsManager.info("Retrieved text from element: " + locator + " - Text: " + msg);
                       return msg.isEmpty() ? null : msg;
                   }catch (Exception e){
                       return null;
                   }
               }
        );
    }

    //upload file
    public ElementActions uploadFile(By locator,String filePath)
    {
        String fileAbsolute = System.getProperty("user.dir") + File.separator + filePath ;
        waitManager.fluentWait().until( d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolute);
                        LogsManager.info("Uploaded file: " + fileAbsolute + " to element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    //find an element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //function to scroll to an element using js
    public void scrollToElementJS(By locator) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behaviour:\"auto\",block:\"center\",inline:\"center\"});", findElement(locator));
    }

    // Scrolls to the very bottom of the page
    public ElementActions scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        LogsManager.info("Scrolled to the bottom of the page");
        return this;
    }

    // Scrolls to the very top of the page
    public ElementActions scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

        LogsManager.info("Scrolled to the top of the page");
        return this;
    }

    //select from dropdown
    public ElementActions selectFromDropdown(By locator, String value) {
        waitManager.fluentWait().until( d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        Select select = new Select(element);
                        select.selectByVisibleText(value);
                        LogsManager.info("Selected value '" + value + "' from dropdown: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    /**
    // Wait for an element to disappear from the DOM
    public ElementActions waitForInvisibility(By locator) {
        LogsManager.info("Waiting for element to disappear: " + locator);
        try {
            waitManager.fluentWait().until(d -> {
                try {
                    return d.findElements(locator).isEmpty() || !d.findElement(locator).isDisplayed();
                } catch (Exception e) {
                    return true;
                }
            });
            LogsManager.info("Element disappeared successfully: " + locator);
        } catch (Exception e) {
            LogsManager.error("Timeout: Element '" + locator + "' did not disappear within the wait time!");
            throw e; // Re-throw so the test still fails
        }

        return this;
    }
     **/

    // Wait until the element is not visible: hidden or removed from the page
    public ElementActions waitForInvisibility(By locator) {
        LogsManager.info("Waiting for element to disappear: " + locator);
        try {
            waitManager.fluentWait().until(d -> {
                try {
                    return d.findElements(locator).stream().noneMatch(WebElement::isDisplayed);
                } catch (StaleElementReferenceException e) {
                    return true; // the element was removed while we were checking it
                }
            });
            LogsManager.info("Element disappeared successfully: " + locator);
        } catch (TimeoutException e) {
            LogsManager.error("Timeout: element '" + locator + "' is still visible after the wait time");
            throw e; // re-throw so the test still fails
        }
        return this;
    }

    //find multiple elements
    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    // Handle JavaScript Alerts (Clicking 'OK')
    public ElementActions acceptAlert() {
        waitManager.fluentWait().until(d -> {
            try {
                // Switch to the alert and click OK
                d.switchTo().alert().accept();
                LogsManager.info("Clicked 'OK' on the browser alert.");
                return true;
            } catch (Exception e) {
                return false; // The alert hasn't appeared yet, keep waiting
            }
        });
        return this;
    }
}
