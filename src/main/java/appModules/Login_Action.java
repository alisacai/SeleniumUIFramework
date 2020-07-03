package appModules;

import org.openqa.selenium.WebDriver;
import pageObject.LoginPage;
import util.Log;

public class Login_Action {
    public static void execute(WebDriver driver, String username, String password) throws Exception {
        Log.info("访问网址http://mail.qq.com");
        driver.get("http://mail.qq.com/");
        Log.info("切换到登录模块所在的frame");
        driver.switchTo().frame("login_frame");
        LoginPage loginPage = new LoginPage(driver);
        Log.info("在126邮箱登录页面的密码输入框输入" + username);
        loginPage.username().sendKeys(username);
        Log.info("在126邮箱登录页面的密码输入框输入" + password);
        loginPage.password().sendKeys(password);
        Log.info("单击登录页面的登录按钮");
        loginPage.submitButton().click();
        Log.info("单击登录页面的登录按钮，休眠5秒，等待从登录页面跳转到登录后的用户主页");
        Thread.sleep(5000);
    }
}
