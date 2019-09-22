package framework.tests;
import framework.Logger;
import framework.browser.Browser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

@TestInfo(id = 1, comment = "Test 1: searching the first link for jenkins")
public class TestCaseOne extends BaseTest {

    @Override
    public void run() {
        testInfo = getClass().getAnnotation(TestInfo.class);
        WebElement input = Browser.getInstance().findElement(By.xpath("//input[@name='q']"));
        input.sendKeys("jenkins");
        input.submit();
        String firstLinkText = Browser.getInstance().findElement(By.xpath("//div[@id='rso']//div[@class='rc']//a")).getAttribute("href");
        Logger.getLogger().info(String.format("First link text %s", firstLinkText));
        Assert.assertEquals(firstLinkText, "https://jenkins.io/");
    }
}
