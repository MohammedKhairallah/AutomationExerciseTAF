package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private final FooterComponent footer;

    public CheckoutPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
        this.footer = new FooterComponent(driver);
    }

    // The navigation bar is a component shared by every page
    public NavigationBarComponent navigationBar() {
        return navigationBar;
    }

    // The footer is a component shared by every page
    public FooterComponent footer() {
        return footer;
    }

    //variables
    private final String CHECKOUT_ENDPOINT = "/checkout";

    //locators
    private final By reviewYourOrderHeading = By.xpath("//h2[text()='Review Your Order']");
    private final By orderCommentTextArea = By.cssSelector("textarea[name='message']");
    private final By placeOrderButton = By.xpath("//a[text()='Place Order']");
    private final By grandTotalAmount = By.cssSelector("tbody tr:last-child td:last-child p.cart_total_price");

    //delivery address
    private final By deliveryName = By.cssSelector("#address_delivery .address_firstname");
    private final By deliveryCompany = By.xpath("//ul[@id='address_delivery']//li[contains(@class,'address_address1')][1]");
    private final By deliveryAddress1 = By.xpath("//ul[@id='address_delivery']//li[contains(@class,'address_address1')][2]");
    private final By deliveryAddress2 = By.xpath("//ul[@id='address_delivery']//li[contains(@class,'address_address1')][3]");
    private final By deliveryCityStateZip = By.xpath("//ul[@id='address_delivery']//li[contains(@class,'address_city')]");
    private final By deliveryCountry = By.xpath("//ul[@id='address_delivery']//li[contains(@class,'address_country_name')]");
    private final By deliveryPhone = By.xpath("//ul[@id='address_delivery']//li[contains(@class,'address_phone')]");
    //billing address
    private final By billingName = By.cssSelector("#address_invoice .address_firstname");
    private final By billingCompany = By.xpath("//ul[@id='address_invoice']//li[contains(@class,'address_address1')][1]");
    private final By billingAddress1 = By.xpath("//ul[@id='address_invoice']//li[contains(@class,'address_address1')][2]");
    private final By billingAddress2 = By.xpath("//ul[@id='address_invoice']//li[contains(@class,'address_address1')][3]");
    private final By billingCityStateZip = By.xpath("//ul[@id='address_invoice']//li[contains(@class,'address_city')]");
    private final By billingCountry = By.xpath("//ul[@id='address_invoice']//li[contains(@class,'address_country_name')]");
    private final By billingPhone = By.xpath("//ul[@id='address_invoice']//li[contains(@class,'address_phone')]");

    //dynamic locators
    private By orderProductName(String productName) {
        return By.xpath("//td[@class='cart_description']//a[text()='" + productName + "']");
    }

    private By orderProductPrice(String productName) {
        return By.xpath("//tr[.//a[text()='" + productName + "']]//td[@class='cart_price']//p");
    }

    private By orderProductQuantityButton(String productName) {
        return By.xpath("//tr[.//a[text()='" + productName + "']]//td[@class='cart_quantity']//button");
    }

    private By orderProductTotal(String productName) {
        return By.xpath("//tr[.//a[text()='" + productName + "']]//td[@class='cart_total']//p");
    }

    //actions
    @Step("Navigate to Checkout Page")
    public CheckoutPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + CHECKOUT_ENDPOINT);
        return this;
    }

    @Step("Enter comment for the order: {comment}")
    public CheckoutPage enterOrderComment(String comment) {
        driver.element().type(orderCommentTextArea, comment);
        LogsManager.info("Entered order comment: " + comment);
        return this;
    }

    @Step("Click on 'Place Order' button")
    public PaymentPage clickPlaceOrder() {
        driver.element().click(placeOrderButton);
        return new PaymentPage(driver);
    }


    //validations
    @Step("Verify user is navigated to the Checkout Page successfully")
    public CheckoutPage verifyUserIsNavigatedToCheckoutPage() {
        driver.verification().assertPageUrl(PropertyReader.getProperty("baseUrlWeb") + CHECKOUT_ENDPOINT);
        driver.verification().isElementVisible(reviewYourOrderHeading);
        LogsManager.info("Checkout page loaded successfully.");
        return this;
    }

    @Step("Verify Delivery Address Details")
    public CheckoutPage verifyDeliveryAddress(String title, String expectedFirstName,String expectedLastName, String expectedCompany,
                                              String expectedAddress1, String expectedAddress2, String expectedCity, String expectedState,
                                              String expectedZip, String expectedCountry, String expectedPhone) {
        driver.verification().Equals(driver.element().getText(deliveryName), (title+". ")+expectedFirstName+" "+expectedLastName, "Delivery Name mismatch");
        driver.verification().Equals(driver.element().getText(deliveryCompany), expectedCompany, "Delivery Company mismatch");
        driver.verification().Equals(driver.element().getText(deliveryAddress1), expectedAddress1, "Delivery Address1 mismatch");
        driver.verification().Equals(driver.element().getText(deliveryAddress2), expectedAddress2, "Delivery Address2 mismatch");
        driver.verification().Equals(driver.element().getText(deliveryCityStateZip), (expectedCity+" "+expectedState+" "+expectedZip), "Delivery CityStateZip mismatch");
        driver.verification().Equals(driver.element().getText(deliveryCountry), expectedCountry, "Delivery Country mismatch");
        driver.verification().Equals(driver.element().getText(deliveryPhone), expectedPhone, "Delivery Phone mismatch");
        return this;
    }

    @Step("Verify Billing Address Details")
    public CheckoutPage verifyBillingAddress(String title, String expectedFirstName,String expectedLastName, String expectedCompany,
                                              String expectedAddress1, String expectedAddress2, String expectedCity, String expectedState,
                                              String expectedZip, String expectedCountry, String expectedPhone) {
        driver.verification().Equals(driver.element().getText(billingName), (title+". ")+expectedFirstName+" "+expectedLastName, "Billing Name mismatch");
        driver.verification().Equals(driver.element().getText(billingCompany), expectedCompany, "Billing Company mismatch");
        driver.verification().Equals(driver.element().getText(billingAddress1), expectedAddress1, "Billing Address1 mismatch");
        driver.verification().Equals(driver.element().getText(billingAddress2), expectedAddress2, "Billing Address2 mismatch");
        driver.verification().Equals(driver.element().getText(billingCityStateZip), (expectedCity+" "+expectedState+" "+expectedZip), "Billing CityStateZip mismatch");
        driver.verification().Equals(driver.element().getText(billingCountry), expectedCountry, "Billing Country mismatch");
        driver.verification().Equals(driver.element().getText(billingPhone), expectedPhone, "Billing Phone mismatch");
        return this;
    }

    @Step("Verify product '{productName}' in checkout review table (Price: {expectedPrice}, Qty: {expectedQty}, Total: {expectedTotal})")
    public CheckoutPage verifyProductInReviewTable(String productName, String expectedPrice, String expectedQty, String expectedTotal) {
        driver.verification().Equals(driver.element().getText(orderProductName(productName)), productName, "Product Name does not match");
        driver.verification().Equals(driver.element().getText(orderProductPrice(productName)), expectedPrice, "Price mismatch for " + productName);
        driver.verification().Equals(driver.element().getText(orderProductQuantityButton(productName)), expectedQty, "Quantity mismatch for " + productName);
        driver.verification().Equals(driver.element().getText(orderProductTotal(productName)), expectedTotal, "Total mismatch for " + productName);
        LogsManager.info("Verified product row for: " + productName);
        return this;
    }

    @Step("Verify Grand Total Amount is: {expectedTotal}")
    public CheckoutPage verifyGrandTotalAmount(String expectedTotal) {
        String actualTotal = driver.element().getText(grandTotalAmount);
        driver.verification().Equals(actualTotal, expectedTotal, "Grand Total Amount does not match!");
        LogsManager.info("Verifying Grand Total. Actual: " + actualTotal);
        return this;
    }
}
