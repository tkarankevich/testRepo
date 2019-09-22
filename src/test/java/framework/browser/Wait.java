package framework.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Wait {
    public void explicitWait(By locator) {
        new WebDriverWait(Browser.getInstance(), Browser.getTimeoutTime())
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void implicitWait() { Browser.getInstance().manage().timeouts().implicitlyWait(Browser.getTimeoutTime(), TimeUnit.SECONDS); }

    public void fluentWait(By locator) {

        new FluentWait<>(Browser.getInstance())
                .withTimeout(Duration.ofSeconds(Browser.getTimeoutTime()))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)
                .until(driver -> driver.findElement(locator));
    }
}
