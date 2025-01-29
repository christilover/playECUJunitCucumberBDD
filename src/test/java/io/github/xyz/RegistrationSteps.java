package io.github.xyz;


import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import io.github.xyz.page_objects.RegistrationConfirmationPagePOM;
import io.github.xyz.page_objects.RegistrationPagePOM;
import io.github.xyz.util.LogUtil;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

//@Execution(ExecutionMode.CONCURRENT)
public class RegistrationSteps {
    static final Logger log = getLogger(lookup().lookupClass());

    private Faker faker = new Faker();  // Instantiate Faker
    private static RegistrationPagePOM registrationPage;
    RegistrationConfirmationPagePOM confirmationPage;//


    //  static WebDriver driver;

    private RegistrationPagePOM registrationPage() {
        return new RegistrationPagePOM(WebTestSupport.currentDriver());
    }

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        // driver.get(Hooks.getRegistrationUrl());
        ///HelperClass.openPage("https://membership.basketballengland.co.uk/NewSupporterAccount");
        //driver = WebTestSupport.currentDriver(); // Use the ThreadLocal WebDriver

        LogUtil.logMethodEntry(); // Log method entry

        registrationPage = new RegistrationPagePOM(WebTestSupport.currentDriver());
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

        LogUtil.logMethodExit();
    }



    @Then("I should see the registration confirmation {string}")
    public void i_should_see_the_registration_confirmation(String expectedResult) {
        LogUtil.logMethodEntry(); // Log method entry

        // Verify that the registration confirmation message is displayed
        confirmationPage = new RegistrationConfirmationPagePOM(WebTestSupport.currentDriver());

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

}


