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

import java.util.List;

public class ProductsPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final SideBarComponent sideBar;
    private final FooterComponent footer;

    public ProductsPage(GUIDriver driver) {
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
    private final String PRODUCTS_ENDPOINT = "/products";

    //locators
    private final By searchField = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By viewCartLink = By.cssSelector("p > [href=\"/view_cart\"]");
    private final By continueShoppingButton = By.cssSelector(".modal-footer >button");
    private final By allProductsContainer = By.cssSelector(".features_items");
    private final By cartModalTitle = By.cssSelector("#cartModal .modal-title");
    private final By searchedProductsTitle = By.xpath("//h2[text()='Searched Products']");
    private final By firstProduct = By.cssSelector(".features_items .product-image-wrapper");
    private final By brandsSidebar = By.cssSelector(".brands_products");

    //dynamic locators
    private By productName (String productName)
    {
        return By.xpath("//div[@class='overlay-content'] /p[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//div[@class='overlay-content'] /p[.='" + productName + "'] //preceding-sibling::h2");
    }

    // the card of the product (hovering on it shows the overlay)
    private By hoverOnProduct(String productName) {
        return By.xpath("//div[contains(@class,'product-image-wrapper')][.//p[normalize-space()='"
                + productName + "']]//div[contains(@class,'single-products')]");
    }

    // the "Add to cart" button inside the overlay of the same product
    private By addToCartButton(String productName) {
        return By.xpath("//div[contains(@class,'product-image-wrapper')][.//p[normalize-space()='"
                + productName + "']]//div[contains(@class,'overlay-content')]//a[contains(@class,'add-to-cart')]");
    }
    private By brandsProducts(String brandName){
        return By.xpath("//div[@class='brands_products']//a[contains(., '" + brandName + "')]");
    }

    private By viewProduct(String productName) {
        return By.xpath("//p[.='" + productName + "'] //following::div[@class='choose'][1]");
    }

    private By itemAddedMessage(String expectedText) {
        return By.xpath("//div[@class='modal-body']//p[contains(text(), '" + expectedText + "')]");
    }

    private By headerLocator(String expectedHeader){
        return By.xpath("//h2[contains(text(), '" + expectedHeader + "')]");
    }

    //actions
    @Step("Navigate to the Products Page")
    public ProductsPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + PRODUCTS_ENDPOINT);
        return this;
    }

    @Step("Search for product: {productName}")
    public ProductsPage searchProduct(String productName) {
        driver.element().type(searchField, productName)
                .click(searchButton);
        return this;
    }

        @Step("Click on Add to Cart for product: {productName}")
        public ProductsPage clickOnAddToCart(String productName) {
            driver.element().hover(hoverOnProduct(productName))
                    .click(addToCartButton(productName));
            return this;
        }

    @Step("Click on View Product for product: {productName}")
    public ProductDetailsPage clickOnViewProduct(String productName) {
        driver.element().click(viewProduct(productName));
        return new ProductDetailsPage(driver);
    }

    @Step("Click on View Cart")
    public CartPage clickOnViewCart() {
        driver.element().click(viewCartLink);
        return new CartPage(driver);
    }

    @Step("Click on Continue Shopping")
    public ProductsPage clickOnContinueShopping() {
        driver.element().click(continueShoppingButton)
                .waitForInvisibility(cartModalTitle); //
        return this;
    }

    @Step("Click on brand: {brandName}")
    public ProductsPage clickOnBrand(String brandName){
        driver.element().click(brandsProducts(brandName));
        LogsManager.info("Clicked on brand: " + brandName);
        return this;
    }

    //validations
    @Step("Validate product details for {productName} with price {productPrice}")
    public ProductsPage validateProductDetails(String productName, String productPrice) {
        String actualProductName = driver.element().hover(hoverOnProduct(productName)).getText(productName(productName));
        String actualProductPrice = driver.element().hover(hoverOnProduct(productName)).getText(this.productPrice(productName));
        LogsManager.info("Validating product details for: " + actualProductName + " with price: " + actualProductPrice);
        driver.validation().Equals(actualProductName, productName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice, "Product price does not match");
        return this;
    }

    @Step("Validate item added message  contains: {expectedText}")
    public ProductsPage validateItemAddedMessage(String expectedText) {
        String actualTxt = driver.element().getText(itemAddedMessage(expectedText));
        driver.verification().Equals(actualTxt, expectedText, "Item added message  does not match expected text");
        LogsManager.info("Validating item added message : " + actualTxt);
        return this;
    }

    @Step("Verify user is navigated to ALL PRODUCTS page successfully")
    public ProductsPage verifyUserIsNavigatedToAllProductsPage(){
        // 1. Verify URL contains the correct path
        driver.validation().assertPageUrl(PropertyReader.getProperty("baseUrlWeb") + PRODUCTS_ENDPOINT);
        LogsManager.info("URL verified successfully.");
        // 2. Verify the unique Header text is visible
        driver.verification().isElementVisible(allProductsContainer);
        return this;
    }

    @Step("Verify 'SEARCHED PRODUCTS' title is visible")
    public ProductsPage verifySearchedProductsTitleIsVisible() {
        driver.verification().isElementVisible(searchedProductsTitle);
        LogsManager.info("Verified 'SEARCHED PRODUCTS' title is visible.");
        return this;
    }

    @Step("Verify that the products list is visible")
    public ProductsPage verifyProductsListIsVisible() {
        driver.verification().isElementVisible(firstProduct);
        return this;
    }


    @Step("Verify user is navigated to brand page for brand: {expectedHeader}")
    public ProductsPage verifyBrandPageIsDisplayed(String expectedHeader) {
        driver.verification().isElementVisible(headerLocator(expectedHeader));
        return this;
    }

    @Step("Verify user is navigated to category page for category: {expectedHeader}")
    public ProductsPage verifyCategoryPageIsDisplayed(String expectedHeader) {
        driver.verification().isElementVisible(headerLocator(expectedHeader));
        return this;
    }
}
