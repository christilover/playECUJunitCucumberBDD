
package driver.factory;

import driver.IDriverFactory;
import driver.factory.manager.ChromeDriverManager;
import driver.factory.manager.FirefoxDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.github.xyz.config.ConfigurationManager.configuration;


public class RemoteDriverFactory implements IDriverFactory {

    private static final Logger logger = Logger.getLogger("com.eliasnogueira");

    @Override
    public WebDriver createInstance(String browser) {
        MutableCapabilities capability;
        BrowserList browserToCreate = BrowserList.valueOf(browser.toUpperCase());

        capability = switch (browserToCreate) {
            case CHROME -> new ChromeDriverManager().getOptions();
            case FIREFOX -> new FirefoxDriverManager().getOptions();
        };

        return createRemoteInstance(capability);
    }

    private RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            String gridURL = String.format("http://%s:%s", configuration().gridUrl(), configuration().gridPort());


            String gridHost = configuration().gridUrl();
            String gridPort = configuration().gridPort();

            logger.info("Grid Host: " + gridHost);
            logger.info("Grid Port: " + gridPort);

            if (gridHost == null || gridPort == null || gridHost.isEmpty() || gridPort.isEmpty()) {
                throw new RuntimeException("Selenium Grid URL or Port is not configured properly.");
            }

            // String gridURL = String.format("http://%s:%s/wd/hub", gridHost, gridPort);
            logger.info("Final Grid URL: " + gridURL);



            remoteWebDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), capability);
        } catch (java.net.MalformedURLException e) {
            logger.log(Level.SEVERE, "Grid URL is invalid or Grid is not available");
            logger.log(Level.SEVERE, String.format("Browser: %s", capability.getBrowserName()), e);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
        }

        return remoteWebDriver;
    }
}
