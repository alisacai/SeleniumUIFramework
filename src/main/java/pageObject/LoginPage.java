package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.ObjectMap;

public class LoginPage {

    private WebElement element = null;
    private ObjectMap objectMap = new ObjectMap("ObjectMap.properties");
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement username() throws Exception {
        return driver.findElement(objectMap.getLocator("Mail.LoginIn.username"));
    }

    public WebElement password() throws Exception {
        return driver.findElement(objectMap.getLocator("Mail.LoginIn.password"));
    }

    public WebElement submitButton() throws Exception {
        return driver.findElement(objectMap.getLocator("Mail.LoginIn.loginsubmit"));
    }
}
