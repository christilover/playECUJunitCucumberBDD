package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationPagePOM {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Using @FindBy annotations to locate elements
    @FindBy(css = "#dp")
    private WebElement dateOfBirth;

    @FindBy(css = "#member_firstname")
    private WebElement firstName;

    @FindBy(css = "#member_lastname")
    private WebElement lastName;

    @FindBy(css = "#member_emailaddress")
    private WebElement email;

    @FindBy(css = "#member_confirmemailaddress")
    private WebElement confirmEmail;

    @FindBy(css = "#signupunlicenced_password")
    private WebElement password;

    @FindBy(css = "#signupunlicenced_confirmpassword")
    private WebElement confirmPassword;

    @FindBy(css = ".md-checkbox:nth-child(7) .box")
    private WebElement termsCheckbox;

    @FindBy(css = ".md-checkbox:nth-child(2) > label > .box")
    private WebElement ageResponsibilityCheckbox;

    @FindBy(css = ".md-checkbox > .md-checkbox:nth-child(1) .box")
    private WebElement ethicsCheckbox;

    @FindBy(name = "join")
    private WebElement joinButton;

    @FindBy(css = ".field-validation-error.warning span")
    private List<WebElement> errorMessages;

    public RegistrationPagePOM(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialize the WebElements using PageFactory
        PageFactory.initElements(driver, this);
    }

    public void visit(String url) {
        driver.get(url);
    }

    private void clearAndSendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        element.sendKeys(text);
    }

    public void setDateOfBirth(String dob) {
        clearAndSendKeys(dateOfBirth, dob);
    }

    public void setFirstName(String fname) {
        clearAndSendKeys(firstName, fname);
    }

    public void setLastName(String lname) {
        clearAndSendKeys(lastName, lname);
    }

    public void setEmail(String emailAddress) {
        clearAndSendKeys(email, emailAddress);
    }

    public void setConfirmEmail(String emailAddress) {
        clearAndSendKeys(confirmEmail, emailAddress);
    }

    public void setPassword(String pwd) {
        clearAndSendKeys(password, pwd);
    }

    public void setConfirmPassword(String pwd) {
        clearAndSendKeys(confirmPassword, pwd);
    }

    public void acceptTermsAndConditions() {
        wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox)).click();
    }

    public void confirmAgeResponsibility() {
        wait.until(ExpectedConditions.elementToBeClickable(ageResponsibilityCheckbox)).click();
    }

    public void agreeToCodeOfEthics() {
        wait.until(ExpectedConditions.elementToBeClickable(ethicsCheckbox)).click();
    }

    public void clickJoinButton() {
        wait.until(ExpectedConditions.elementToBeClickable(joinButton)).click();
    }

    public List<String> missingFields() {
        // Collect error messages and filter based on the required conditions
        return errorMessages.stream()
                .map(WebElement::getText) // Get the error message text
                .map(String::toLowerCase) // Convert to lowercase for case-insensitive comparison
                .filter(errorText -> errorText.contains("is required") || errorText.contains(" not match"))
                .collect(Collectors.toList());
    }

    public List<String> checkUnselectedCheckboxes() {
        List<String> unselectedCheckboxes = new ArrayList<>();

        if (!termsCheckbox.isSelected()) {
            unselectedCheckboxes.add("You must confirm that you have read and accepted our Terms and Conditions");
        }
        if (!ageResponsibilityCheckbox.isSelected()) {
            unselectedCheckboxes.add("You must confirm that you are over 18 or a person with parental responsibility");
        }
        if (!ethicsCheckbox.isSelected()) {
            unselectedCheckboxes.add("You must confirm that you have read, understood, and agree to the Code of Ethics and Conduct");
        }

        return unselectedCheckboxes;
    }
}
