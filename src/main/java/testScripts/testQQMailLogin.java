package testScripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import appModules.Login_Action;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.CSV;
import util.Constant;
import util.Excel;

import java.io.IOException;

public class testQQMailLogin {
    public WebDriver driver;
    String baseUrl = "http://mail.qq.com/";

    @BeforeMethod
    public void beforeMethod() {
//		DOMConfigurator.configure("log4j.xml");
//		driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "src\\main\\TestData\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("launch 成功");
        driver.manage().window().maximize();
        driver.navigate().to(baseUrl);
        driver.manage().window().maximize();
    }

    @DataProvider(name = "Excel")
    public Object[][] getTestAccountFromExcel() throws IOException {
        return Excel.getTestData(Constant.TestDataExcelFilePath, Constant.TestDataExcelFileSheet);
    }

    @Test(dataProvider = "Excel")
    public void LoginDataFromExcel(String username, String password) throws Exception {
		/*
		driver.switchTo().frame("login_frame");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.username().sendKeys("570549054");
		loginPage.password().sendKeys("Xuminhui2016..");
		loginPage.submitButton().click();
		*/
        /*
         * 1、调用Login_Action类的execute方法
         */
        Login_Action.execute(driver, username, password);
        Thread.sleep(5000);
        Assert.assertTrue(driver.getPageSource().contains("退出"));
    }

    @DataProvider(name = "CSV")
    public Object[][] getTestAccountFromCSV() throws IOException {
        return CSV.getTestData(Constant.TestDataCSVFilePath);
    }

    //tttt
    @Test(dataProvider = "CSV")
    public void LoginDataFromCSV(String username, String password) throws Exception {
        /*
         * 1、调用Login_Action类的execute方法
         */
        Login_Action.execute(driver, username, password);
        Thread.sleep(5000);
        Assert.assertTrue(driver.getPageSource().contains("退出"));
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }
}