package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.LogUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dateOfBirth = By.cssSelector("#dp");
    private final By firstName = By.cssSelector("#member_firstname");
    private final By lastName = By.cssSelector("#member_lastname");
    private final By email = By.cssSelector("#member_emailaddress");
    private final By confirmEmail = By.cssSelector("#member_confirmemailaddress");
    private final By password = By.cssSelector("#signupunlicenced_password");
    private final By confirmPassword = By.cssSelector("#signupunlicenced_confirmpassword");
    private final By termsCheckbox = By.cssSelector(".md-checkbox:nth-child(7) .box");
    private final By ageResponsibilityCheckbox = By.cssSelector(".md-checkbox:nth-child(2) > label > .box");
    private final By ethicsCheckbox = By.cssSelector(".md-checkbox > .md-checkbox:nth-child(1) .box");
    private final By joinButton = By.name("join");
    private final By errorMessages = By.cssSelector(".field-validation-error.warning span");

    public RegistrationPage(WebDriver driver) {
        LogUtil.logMethodEntry();
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LogUtil.logMethodExit();
    }

    public void visit(String url) {
        LogUtil.logMethodEntry();
        driver.get(url);
        LogUtil.logMethodExit();
    }

    private void clearAndSendKeys(By locator, String text) {
        LogUtil.logMethodEntry();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        LogUtil.logMethodExit();
    }

    public void setDateOfBirth(String dob) {
        LogUtil.logMethodEntry();
        clearAndSendKeys(dateOfBirth, dob);
        LogUtil.logMethodExit();
    }

    public void setFirstName(String fname) {
        LogUtil.logMethodEntry();
        clearAndSendKeys(firstName, fname);
        LogUtil.logMethodExit();
    }

    public void setLastName(String lname) {
        LogUtil.logMethodEntry();
        clearAndSendKeys(lastName, lname);
        LogUtil.logMethodExit();
    }

    public void setEmail(String emailAddress) {
        LogUtil.logMethodEntry();
        clearAndSendKeys(email, emailAddress);
        LogUtil.logMethodExit();
    }

    public void setConfirmEmail(String emailAddress) {
        LogUtil.logMethodEntry();
        clearAndSendKeys(confirmEmail, emailAddress);
        LogUtil.logMethodExit();
    }

    public void setPassword(String pwd) {
        LogUtil.logMethodEntry();
        clearAndSendKeys(password, pwd);
        LogUtil.logMethodExit();
    }

    public void setConfirmPassword(String pwd) {
        LogUtil.logMethodEntry();
        clearAndSendKeys(confirmPassword, pwd);
        LogUtil.logMethodExit();
    }

    public void acceptTermsAndConditions() {
        LogUtil.logMethodEntry();
        wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox)).click();
        LogUtil.logMethodExit();
    }

    public void confirmAgeResponsibility() {
        LogUtil.logMethodEntry();
        wait.until(ExpectedConditions.elementToBeClickable(ageResponsibilityCheckbox)).click();
        LogUtil.logMethodExit();
    }

    public void agreeToCodeOfEthics() {
        LogUtil.logMethodEntry();
        wait.until(ExpectedConditions.elementToBeClickable(ethicsCheckbox)).click();
        LogUtil.logMethodExit();
    }

    public void clickJoinButton() {
        LogUtil.logMethodEntry();
        wait.until(ExpectedConditions.elementToBeClickable(joinButton)).click();
        LogUtil.logMethodExit();
    }

    public List<String> missingFields() {
        LogUtil.logMethodEntry();

        // Locate error elements on the page
        List<WebElement> errors = driver.findElements(errorMessages);

        // Collect error messages and filter based on the required conditions
        List<String> missingFields = errors.stream()
                .map(WebElement::getText)  // Get the error message text
                .map(String::toLowerCase)  // Convert to lowercase for case-insensitive comparison
                .filter(errorText -> errorText.contains("is required") || errorText.contains(" not match"))
                .collect(Collectors.toList());

        LogUtil.logMethodExit();
        return missingFields;
    }


    public List<String> checkUnselectedCheckboxes() {
        LogUtil.logMethodEntry();
        List<String> unselectedCheckboxes = new ArrayList<>();

        if (!isSelected(termsCheckbox)) {
            unselectedCheckboxes.add("You must confirm that you have read and accepted our Terms and Conditions");
        }
        if (!isSelected(ageResponsibilityCheckbox)) {
            unselectedCheckboxes.add("You must confirm that you are over 18 or a person with parental responsibility");
        }
        if (!isSelected(ethicsCheckbox)) {
            unselectedCheckboxes.add("You must confirm that you have read, understood, and agree to the Code of Ethics and Conduct");
        }

        LogUtil.logMethodExit();
        return unselectedCheckboxes;
    }

    private boolean isSelected(By locator) {
        LogUtil.logMethodEntry();
        boolean selected = driver.findElement(locator).isSelected();
        LogUtil.logMethodExit();
        return selected;
    }
}
