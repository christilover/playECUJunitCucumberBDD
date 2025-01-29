
package driver.factory;


import driver.IDriverFactory;
import driver.factory.manager.ChromeDriverManager;
import driver.factory.manager.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;

public class LocalDriverFactory implements IDriverFactory {

    @Override
    public WebDriver createInstance(String browser) {
        WebDriver driver;
        BrowserList browserToCreate = BrowserList.valueOf(browser.toUpperCase());

        driver = switch (browserToCreate) {
            case CHROME -> new ChromeDriverManager().createDriver();
            case FIREFOX -> new FirefoxDriverManager().createDriver();
        };
        return driver;
    }
}
