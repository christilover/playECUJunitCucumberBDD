package io.github.xyz;


import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import page_objects.RegistrationConfirmationPage;
import page_objects.RegistrationPage;
import util.LogUtil;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

//@Execution(ExecutionMode.CONCURRENT)
public class RegistrationSteps {
    static final Logger log = getLogger(lookup().lookupClass());

    private Faker faker = new Faker();  // Instantiate Faker
   private static RegistrationPage registrationPage;
    RegistrationConfirmationPage confirmationPage;// = new RegistrationConfirmationPage(WebTestSupport.currentDriver());

    //  static WebDriver driver;

    private RegistrationPage registrationPage() {
        return new RegistrationPage(WebTestSupport.currentDriver());
    }

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        // driver.get(Hooks.getRegistrationUrl());
        ///HelperClass.openPage("https://membership.basketballengland.co.uk/NewSupporterAccount");
        //driver = WebTestSupport.currentDriver(); // Use the ThreadLocal WebDriver

        LogUtil.logMethodEntry(); // Log method entry

        registrationPage = new RegistrationPage(WebTestSupport.currentDriver());
        String url = "https://membership.basketballengland.co.uk/NewSupporterAccount";
        registrationPage.visit(url);
    }

    @When("I fill in {string}, {string}, {string}, {string}, {string}, {string}, and {string}")
    public void i_fill_in_and(String fname, String lname, String email, String confirmEmail, String password, String confirmPassword, String dob) {
        LogUtil.logMethodEntry(); // Log method entry

        // Use Faker to generate random names for uniqueness
        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().lastName();
        String passwordBase = randomFirstName + faker.number().digits(2); // Adding random digits for more complexity
        String emailBase = faker.internet().emailAddress();

        // If fname or lname are not empty, append random name, otherwise leave them empty
        fname = fname.isEmpty() ? "" : fname + randomFirstName;
        lname = lname.isEmpty() ? "" : lname + randomLastName;
       // password = password.isEmpty() ? "" : password + passwordBase;
       // confirmPassword = confirmPassword.isEmpty() ? "" : password ;
        email = email.isEmpty() ? "" :   email+emailBase;
        confirmEmail=confirmEmail.isEmpty() ? "" : email;

        // Fill out the registration form fields
        registrationPage.setFirstName(fname);
        registrationPage.setLastName(lname);
        registrationPage.setEmail(email);
        registrationPage.setConfirmEmail(confirmEmail);
        registrationPage.setPassword(password);
        registrationPage.setConfirmPassword(confirmPassword);
        registrationPage.setDateOfBirth(dob);
    }


    @And("I accept the terms and conditions {string}")
    public void i_accept_the_terms_and_conditions(String accept) {
        LogUtil.logMethodEntry(); // Log method entry

        if (Boolean.parseBoolean(accept)) {
            registrationPage.acceptTermsAndConditions();
        }

    }

    @And("I confirm age responsibility {string}")
    public void i_confirm_age_responsibility(String confirmAge) {
        LogUtil.logMethodEntry(); // Log method entry

        if (Boolean.parseBoolean(confirmAge)) {
            registrationPage.confirmAgeResponsibility();
        }
    }

    @And("I agree to the Code of Ethics and Conduct {string}")
    public void i_agree_to_the_code_of_ethics_and_conduct(String agreeEthics) {
        LogUtil.logMethodEntry(); // Log method entry

        if (Boolean.parseBoolean(agreeEthics)) {
            registrationPage.agreeToCodeOfEthics();
        }
    }


    @And("I click {string}")
    public void i_click(String button) {
        LogUtil.logMethodEntry(); // Log method entry


        // Wait for all elements to be visible on the page (adjust the locator as per your page structure)
        WebDriverWait wait = new WebDriverWait(WebTestSupport.currentDriver(), Duration.ofSeconds(13)); // Wait for 13 seconds
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))); // Wait until the body element is visible
        registrationPage.clickJoinButton(); // Click the Join button

        // Wait for all elements to be visible on the page (adjust the locator as per your page structure)
        //WebDriverWait wait = new WebDriverWait(WebTestSupport.currentDriver(), Duration.ofSeconds(13)); // Wait for 13 seconds
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))); // Wait until the body element is visible


        // Wait for the error messages to become visible
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Wait up to 10 seconds
        //wait.until(ExpectedConditions.visibilityOfAllElements(registrationPage.getErrorMessages())); // Use the getter method
        LogUtil.logMethodExit();
    }



    @Then("I should see the registration confirmation {string}")
    public void i_should_see_the_registration_confirmation(String expectedResult) {
        LogUtil.logMethodEntry(); // Log method entry

        // Verify that the registration confirmation message is displayed
        confirmationPage = new RegistrationConfirmationPage(WebTestSupport.currentDriver());

        boolean isMessageDisplayed = confirmationPage.isRegistrationConfirmationMessageDisplayed();
        Assertions.assertTrue(isMessageDisplayed, "Expected confirmation message to be displayed.");
        LogUtil.logMethodExit(); // Log method exit

        // Optionally, validate the exact text of the message
        // WebElement confirmationMessageElement = driver.findElement(By.xpath("//h2[text()='THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND']"));
        //Assertions.assertEquals(expectedResult, confirmationMessageElement.getText(), "The confirmation message does not match.");
    }

    @Then("the {string} field should be highlighted as required")
    public void requiredField(String requiredField) {
        LogUtil.logMethodEntry(); // Log method entry

        // Get the list of missing fields
        List<String> missingFields = registrationPage.missingFields();

        // Normalize both expected and actual values to lowercase for case-insensitive comparison
        List<String> lowerCaseMissingFields = missingFields.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        // Convert requiredField to lowercase and check if it's present in the list
        assertThat(lowerCaseMissingFields).contains(requiredField.toLowerCase());

        LogUtil.logMethodExit(); // Log method exit
    }


    @Then("I should see {string}")
    public void i_should_see(String expectedResult) {
        LogUtil.logMethodEntry(); // Log method entry

        List<String> checkUnselectedCheckboxes= registrationPage.checkUnselectedCheckboxes();
        assertThat(checkUnselectedCheckboxes).contains(expectedResult);
        LogUtil.logMethodExit();
    }

 /*
    @Then("I should see the registration confirmation {string}")
    public void iShouldSeeTheRegistrationConfirmation(String expectedConfirmationMessage) {
        LogUtil.logMethodEntry(); // Log method entry
        log.debug("Starting validation of the registration confirmation message...");

         confirmationPage = new RegistrationConfirmationPage(WebTestSupport.currentDriver());
        boolean isConfirmationMessageDisplayed = confirmationPage.isRegistrationConfirmationMessageDisplayed();
        Assertions.assertTrue(isConfirmationMessageDisplayed, "The registration confirmation message was not displayed");

        //confirmationPage.clickGoToMyLockerButton();
        //confirmationPage.waitForGoToMyLockerButton();
        // Wait until the confirmation message is visible

         // String actualConfirmationMessage = confirmationPage.getRegistrationConfirmationMessage().getText();
       //log.debug("<<<<<<<<<<<<< Actual confirmation message: {}", actualConfirmationMessage);


        // Validate if the actual message matches the expected result
       // Assertions.assertEquals(expectedConfirmationMessage, actualConfirmationMessage, "Registration confirmation message does not match expected result.");


        // confirmationPage.waitForGoToMyLockerButton();
      //  confirmationPage.clickGoToMyLockerButton();

        //  String actualConfirmationMessage = confirmationPage.getConfirmationMsg();
        //Assertions.assertEquals(expectedConfirmationMessage, actualConfirmationMessage, "Registration confirmation message does not match!");

//        Assertions.assertTrue(confirmationPage.isRegistrationSuccessful(), "Registration was successful!");

        //confirmationPage = new RegistrationConfirmationPage(WebTestSupport.currentDriver());

        // Wait for the "GO TO MY LOCKER" button
       // confirmationPage.waitForGoToMyLockerButton();

        // Click the button to confirm successful registration
        // confirmationPage.clickGoToMyLockerButton();
        //confirmationPage = new RegistrationConfirmationPage(WebTestSupport.currentDriver());
        //confirmationPage.waitForGoToMyLockerButton();
        //confirmationPage.clickGoToMyLockerButton();

        // Wait for the confirmation message to be visible
      //   WebDriverWait wait = new WebDriverWait(WebTestSupport.currentDriver(), Duration.ofSeconds(10));
        // log.debug("ZZZZ Waiting for the confirmation message to become visible...");
        //confirmationPage.clickGoToMyLockerButton();
        //wait.until(ExpectedConditions.visibilityOf(

        // Retrieve and assert the confirmation message
        // String actualConfirmationMessage = registrationConfirmationPage.getConfirmationMessage().getText();
        //log.debug("Actual confirmation message: {}", actualConfirmationMessage);

        //Assertions.assertEquals(expectedConfirmationMessage, actualConfirmationMessage, "The confirmation message did not match.");
        LogUtil.logMethodExit(); // Log method exit

    }


    @Then("the {string} field should be highlighted as required")
    public void requiredField(String requiredField) {
        LogUtil.logMethodEntry(); // Log method entry

        // Check if the requiredField exists in the error messages
        List<String> missingFields = registrationPage.missingFields();
        assertThat(missingFields).contains(requiredField);
        LogUtil.logMethodExit();
    }

    @Then("I should see {string}")
    public void i_should_see(String expectedResult) {
        LogUtil.logMethodEntry(); // Log method entry

        List<String> checkUnselectedCheckboxes= registrationPage.checkUnselectedCheckboxes();
        assertThat(checkUnselectedCheckboxes).contains(expectedResult);
        LogUtil.logMethodExit();
    }


    @Then("I should be given access to her account")
    public void iShouldBeGivenAccessToHerAccount() {
        //CurrentUserPanel currentUser = new CurrentUserPanel(WebTestSupport.currentDriver());
        //assertEquals(frequentFlyer.email, currentUser.email());
    }
    */

}


