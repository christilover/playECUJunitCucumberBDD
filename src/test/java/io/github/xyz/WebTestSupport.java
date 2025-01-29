package io.github.xyz;



import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.xyz.config.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;

import java.time.Duration;

import static io.github.xyz.config.ConfigurationManager.configuration;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class WebTestSupport {
    static final Logger log = getLogger(lookup().lookupClass());
    private Configuration configuration;

    private static ThreadLocal<WebDriver> THREAD_LOCAL_DRIVER = new ThreadLocal<>();
    public final static int TIMEOUT = 5;

    private WebDriver driver;

    @Before("@webtest")
    public void setupWebdriver() {
        log.info(" @Before: Preparing test data for the scenario...");
        configuration=configuration();
        driver = new DriverFactory().createInstance(configuration().browser());

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));

        THREAD_LOCAL_DRIVER.set(driver);
    }



    public static WebDriver currentDriver() {
        return THREAD_LOCAL_DRIVER.get();
    }




    @After("@webtest")
    public void closeWebdriver() {
        log.info("@After: Attempting to clean up WebDriver...");
        try {
            WebDriver driver = THREAD_LOCAL_DRIVER.get();
            if (driver == null) {
                log.warn("{}: ThreadLocal driver is null. Skipping cleanup.", this.getClass().getSimpleName());

            } else {
                driver.quit();
                THREAD_LOCAL_DRIVER.remove();
                log.info("{}: WebDriver cleanup successful.",this.getClass().getSimpleName());
            }
        } catch (Exception e) {
            log.error("Error during WebDriver cleanup: {}", e.getMessage(), e);
        }
    }
}


