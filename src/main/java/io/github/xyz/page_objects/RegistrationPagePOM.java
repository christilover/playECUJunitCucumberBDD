package io.github.xyz.page_objects;

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

public class RegistrationPagePOM extends BasePagePOM {

  //  private final WebDriver driver;
  //  private final WebDriverWait wait;

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
        super(driver, 10);
    }

    public void visit(String url) {
        driver.get(url);
    }

    public void setDateOfBirth(String dob) {
        sendKeys(dateOfBirth, dob);
    }

    public void setFirstName(String fname) {
        sendKeys(firstName, fname);
    }

    public void setLastName(String lname) {
        sendKeys(lastName, lname);
    }

    public void setEmail(String emailAddress) {
        sendKeys(email, emailAddress);
    }

    public void setConfirmEmail(String emailAddress) {
        sendKeys(confirmEmail, emailAddress);
    }

    public void setPassword(String pwd) {
        sendKeys(password, pwd);
    }

    public void setConfirmPassword(String pwd) {
        sendKeys(confirmPassword, pwd);
    }

    public void acceptTermsAndConditions() {
        clickElement(termsCheckbox);
    }

    public void confirmAgeResponsibility() {
        clickElement(ageResponsibilityCheckbox);
    }

    public void agreeToCodeOfEthics() {
        clickElement(ethicsCheckbox);
    }

    public void clickJoinButton() {
        clickElement(joinButton);
    }

    public List<String> missingFields() {
        return errorMessages.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
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