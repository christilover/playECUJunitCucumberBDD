package io.github.xyz.page_objects;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationConfirmationPagePOM extends BasePagePOM {

    @FindBy(linkText = "GO TO MY LOCKER")
    private WebElement goToMyLockerButton;

    @FindBy(xpath = "//h2[text()='THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND']")
    private WebElement registrationConfirmationMessage;

    public RegistrationConfirmationPagePOM(WebDriver driver) {
        super(driver, 20);
    }

    public boolean isRegistrationConfirmationMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(registrationConfirmationMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForGoToMyLockerButton() {
        wait.until(ExpectedConditions.elementToBeClickable(goToMyLockerButton));
    }

    public void clickGoToMyLockerButton() {
        clickElement(goToMyLockerButton);
    }
}
