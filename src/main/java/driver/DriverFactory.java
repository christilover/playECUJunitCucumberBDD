

package driver;

import driver.factory.LocalDriverFactory;
import driver.factory.RemoteDriverFactory;
import org.openqa.selenium.WebDriver;

import static io.github.xyz.config.ConfigurationManager.configuration;

public class DriverFactory implements IDriverFactory {

    @Override
    public WebDriver createInstance(String browser) {
        Target target = Target.valueOf(configuration().target().toUpperCase());

        return switch (target) {
            case LOCAL -> new LocalDriverFactory().createInstance(browser);
            case REMOTE -> new RemoteDriverFactory().createInstance(browser);
        };
    }

    enum Target {
        LOCAL, REMOTE
    }
}
