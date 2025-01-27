package page_objects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import util.LogUtil;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class RegistrationConfirmationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final Logger log = getLogger(lookup().lookupClass());

    // Element locator for the "GO TO MY LOCKER" button
    private By goToMyLockerButton = By.linkText("GO TO MY LOCKER");

    // Element locator for the registration confirmation message
    private By registrationConfirmationMessage = By.xpath("//h2[text()='THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND']");

    public RegistrationConfirmationPage(WebDriver driver) {
        LogUtil.logMethodEntry(); // Log method entry

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        LogUtil.logMethodExit(); // Log method exit
    }


    // Getter method to check if the registration confirmation message is displayed
    public boolean isRegistrationConfirmationMessageDisplayed() {
        LogUtil.logMethodEntry();
        boolean result = false;
        try {
            result = wait.until(ExpectedConditions.visibilityOfElementLocated(registrationConfirmationMessage)).isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for the registration confirmation message.");
        }
        LogUtil.logMethodExit();
        return result;
    }

    // Wait for the "GO TO MY LOCKER" button to be clickable
    public void waitForGoToMyLockerButton() {
        LogUtil.logMethodEntry();
        wait.until(ExpectedConditions.elementToBeClickable(goToMyLockerButton));
        LogUtil.logMethodExit();
    }

    // Click on the "GO TO MY LOCKER" button
    public void clickGoToMyLockerButton() {
        LogUtil.logMethodEntry();
        driver.findElement(goToMyLockerButton).click();
        LogUtil.logMethodExit();
    }
}
