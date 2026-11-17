package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    private final GUIDriver driver;

    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
    }

    //variables
    private final String PRODUCT_DETAILS_ENDPOINT = "/product_details/";


    //locators
    private final By productInformation = By.cssSelector(".product-information");
    private final By productName = By.cssSelector(".product-information > h2");
    private final By productCategory = By.xpath("//div[contains(@class,'product-information')]//p[contains(normalize-space(),'Category:')]");
    private final By productPrice = By.cssSelector(".product-information > span > span");
    private final By productAvailability = By.xpath("//div[contains(@class,'product-information')]//p[b[contains(.,'Availability')]]");
    private final By productCondition = By.xpath("//div[contains(@class,'product-information')]//p[b[contains(.,'Condition')]]");
    private final By productBrand = By.xpath("//div[contains(@class,'product-information')]//p[b[contains(.,'Brand')]]");
    private final By nameField = By.id("name");
    private final By emailField = By.id("email");
    private final By reviewTextArea = By.name("review");
    private final By reviewButton = By.id("button-review");
    private final By reviewMessage = By.cssSelector(".alert-success.alert > span");
    private final By writeYourReviewForm = By.cssSelector("[href=\"#reviews\"]");
    private final By quantityInput = By.id("quantity");
    private final By addToCartButton = By.cssSelector(".btn.btn-default.cart");
    private final By modalViewCartButton = By.cssSelector("p > [href=\"/view_cart\"]");


    //dynamic locators
    private By modalSuccessMessage(String expectedText) {
        return By.xpath("//div[@class='modal-body']//p[contains(text(), '" + expectedText + "')]");
    }



    //actions
    @Step("Navigate to the Product Details Page for product ID: {productId}")
    public ProductDetailsPage navigate(String productId) {
        // Concatenate the base URL + endpoint + the dynamic ID
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + PRODUCT_DETAILS_ENDPOINT + productId);
        return this;
    }

    @Step("Fill review form with Name: {name}, Email: {email}, Review: {review}")
    public ProductDetailsPage fillReviewForm(String name, String email, String review) {
        driver.element().type(nameField, name)
                .type(emailField, email)
                .type(reviewTextArea, review);
        return this;
    }

    @Step("Click 'Submit' on the review form")
    public ProductDetailsPage clickSubmitReview() {
        driver.element().click(reviewButton);
        return this;
    }

    @Step("Increase product quantity to: {quantity}")
    public ProductDetailsPage increaseQuantity(String quantity) {
        driver.element().type(quantityInput, quantity);
        LogsManager.info("Increase quantity to: " + quantity);
        return this;
    }

    @Step("Click on 'Add to cart' button")
    public ProductDetailsPage clickAddToCart() {
        driver.element().click(addToCartButton);
        LogsManager.info("Clicked on 'Add to cart' button on Product Details page.");
        return this;
    }

    //validations
    @Step("Verify that the user is on the product details page")
    public ProductDetailsPage verifyUserIsNavigatedToProductDetailsPage() {
        driver.verification().isElementVisible(productInformation);
        return this;
    }

    @Step("Verify the product details: name, category, price, availability, condition and brand")
    public ProductDetailsPage verifyProductDetails(String name, String category, String price,
                                                   String availability, String condition, String brand) {
        driver.verification().Equals(driver.element().getText(productName), name, "Product name does not match");
        driver.verification().Equals(driver.element().getText(productCategory), category, "Category does not match");
        driver.verification().Equals(driver.element().getText(productPrice), price, "Price does not match");
        driver.verification().Equals(driver.element().getText(productAvailability), availability, "Availability does not match");
        driver.verification().Equals(driver.element().getText(productCondition), condition, "Condition does not match");
        driver.verification().Equals(driver.element().getText(productBrand), brand, "Brand does not match");
        return this;
    }

    @Step("Verify product name is: {expectedName}")
    public ProductDetailsPage verifyProductName(String expectedName) {
        String actualName = driver.element().getText(productName);
        LogsManager.info("Verifying product name. Actual: " + actualName);
        driver.validation().Equals(actualName, expectedName, "Product name does not match!");
        return this;
    }

    @Step("Verify product price is: {expectedPrice}")
    public ProductDetailsPage verifyProductPrice(String expectedPrice) {
        String actualPrice = driver.element().getText(productPrice);
        LogsManager.info("Verifying product price. Actual: " + actualPrice);
        driver.validation().Equals(actualPrice, expectedPrice, "Product price does not match!");
        return this;
    }

    @Step("Verify review success message: {expectedMessage}")
    public ProductDetailsPage verifyReviewSuccessMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(reviewMessage);
        LogsManager.info("Verifying review message. Actual: " + actualMessage);
        driver.verification().Equals(actualMessage, expectedMessage, "Review success message does not match!");
        return this;
    }

    @Step("Verify 'Write Your Review' form is visible")
    public ProductDetailsPage verifyWriteYourReviewFormIsVisible() {
        driver.verification().isElementVisible(writeYourReviewForm);
        LogsManager.info("Verified 'Write Your Review' form is visible.");
        return this;
    }

    @Step("Verify 'Added!' modal success message is displayed")
    public ProductDetailsPage verifyItemAddedMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(modalSuccessMessage(expectedMessage));
        LogsManager.info("Verifying modal message. Actual: " + actualMessage);
        driver.verification().Equals(actualMessage, expectedMessage, "Success message does not match");
        return this;
    }

    @Step("Click 'View Cart' link in the success modal")
    public CartPage clickViewCartInModal() {
        driver.element().click(modalViewCartButton);
        return new CartPage(driver);
    }
}
