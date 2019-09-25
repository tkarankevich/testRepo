package framework.browser;

import com.google.common.base.Strings;
import framework.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import framework.configurations.PropertiesResourceManager;

public class Browser {
    private static WebDriver driver;
    private static PropertiesResourceManager manager = new PropertiesResourceManager("configuration");
    private static String browserName = initBrowserName();
    private static String startPage = manager.getSystemProperty("startPage");
    private static String timeoutTime = manager.getSystemProperty("timeout");

    private Browser() {
    }

    public static WebDriver getInstance() {
        if (driver == null) {
            driver = initDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void getStartPage() {
        driver.get(startPage);
    }

    private static WebDriver initDriver() {
        switch (browserName.toUpperCase()) {
            case "CHROME":
                return initChrome();
            case "FIREFOX":
                return initFF();
            default:
                Logger.getLogger().info(String.format("This [%s] browser not implemented. Available browsers are: FireFox, Chrome", browserName));
                throw new NullPointerException();
        }
    }

    private static String initBrowserName() {
        if (!Strings.isNullOrEmpty(System.getProperty("browser"))) {
            return System.getProperty("browser");
        }
        return manager.getSystemProperty("browser");
    }


    private static WebDriver initChrome() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private static WebDriver initFF() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(getFFOptions());
    }


    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--whitelisted-ips");
        chromeOptions.addArguments("--disable-extension");
        chromeOptions.addArguments("window-size=1920,1080");
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("safebrowsing.enabled", "true");
        chromeOptions.addArguments();
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        return chromeOptions;
    }

    private static FirefoxOptions getFFOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--headless");
        return firefoxOptions;
    }

    static int getTimeoutTime() {
        return Integer.parseInt(timeoutTime);
    }
}
