package io.github.xyz.page_objects;


import io.github.xyz.util.LogUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class BasePagePOM {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger log = getLogger(lookup().lookupClass());

    public BasePagePOM(WebDriver driver, int timeoutInSeconds) {
        LogUtil.logMethodEntry();
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        PageFactory.initElements(driver, this);
        LogUtil.logMethodExit();
    }

    protected void clickElement(WebElement element) {
        LogUtil.logMethodEntry();
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        LogUtil.logMethodExit();
    }

    protected void sendKeys(WebElement element, String text) {
        LogUtil.logMethodEntry();
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        element.sendKeys(text);
        LogUtil.logMethodExit();
    }
}
