package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.pages.components.SideBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;

public class HomePage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final SideBarComponent sideBar;
    private final FooterComponent footer;

    public HomePage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
        this.sideBar = new SideBarComponent(driver);
        this.footer = new FooterComponent(driver);
    }

    // The navigation bar is a component shared by every page
    public NavigationBarComponent navigationBar() {
        return navigationBar;
    }

    // The Side bar is a component shared by Home page & Products Page
    public SideBarComponent sideBar() {
        return sideBar;
    }

    // The footer is a component shared by every page
    public FooterComponent footer() {
        return footer;
    }


    //variables

    //locators
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By recommendedItemsTitle = By.cssSelector(".recommended_items .title");
    private final By viewCartLink = By.cssSelector("p > [href=\"/view_cart\"]");
    private final By recommendedSlider = By.id("recommended-item-carousel");
    private final By nextRecommendedItemsArrow = By.cssSelector("#recommended-item-carousel a[data-slide='next']");

    //dynamic locators
    private By recommendedAddToCartButton(String productName) {
        return By.xpath("//div[@id='recommended-item-carousel']"
                + "//div[contains(@class,'product-image-wrapper')][.//p[normalize-space()='" + productName + "']]"
                + "//a[contains(@class,'add-to-cart')]");
    }

    private By itemAddedMessage(String expectedText) {
        return By.xpath("//div[@class='modal-body']//p[contains(text(), '" + expectedText + "')]");
    }


    //actions
    @Step("Navigate to the Home Page")
    public HomePage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Scroll down to the 'RECOMMENDED ITEMS' section")
    public HomePage scrollToRecommendedItems() {
        driver.element().scrollToElementJS(recommendedItemsTitle);
        return this;
    }

    @Step("Scroll up page to top")
    public HomePage scrollUpToTop() {
        driver.element().scrollToTop();
        return this;
    }

    @Step("Click on View Cart")
    public CartPage clickOnViewCart() {
        driver.element().click(viewCartLink);
        return new CartPage(driver);
    }

    @Step("Click on 'Add To Cart' on the recommended product: {productName}")
    public HomePage clickOnAddToCartOfRecommendedProduct(String productName) {
        driver.element().hover(recommendedSlider);                 // 1. stop the slider
        for (int i = 0; i < 3 && !isRecommendedProductVisible(productName); i++) {
            driver.element().click(nextRecommendedItemsArrow);     // 2. go to the next slide if needed
        }
        driver.element().click(recommendedAddToCartButton(productName));   // 3. click the button
        return this;
    }


    private boolean isRecommendedProductVisible(String productName) {
        List<WebElement> buttons = driver.getInstance().findElements(recommendedAddToCartButton(productName));
        return !buttons.isEmpty() && buttons.get(0).isDisplayed();
    }

    //validations
    @Step("Verify that home page is visible successfully")
    public HomePage verifyHomePageIsVisible() {
        driver.verification().isElementVisible(homePageLabel);
        LogsManager.info("HomePage page loaded successfully.");
        return this;
    }

    @Step("Verify 'RECOMMENDED ITEMS' is visible")
    public HomePage verifyRecommendedItemsVisible() {
        driver.verification().isElementVisible(recommendedItemsTitle);
        return this;
    }

    @Step("Validate item added message  contains: {expectedText}")
    public HomePage validateItemAddedMessage(String expectedText) {
        String actualTxt = driver.element().getText(itemAddedMessage(expectedText));
        driver.verification().Equals(actualTxt, expectedText, "Item added message  does not match expected text");
        LogsManager.info("Validating item added message : " + actualTxt);
        return this;
    }

    @Step("Verify that the page is scrolled up")
    public HomePage verifyPageIsScrolledUp() {
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

}
