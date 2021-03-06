package util;

import org.apache.commons.io.FileUtils;
import org.apache.xpath.operations.Bool;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SeleniumAPI {
//    public void startUp(WebDriver driver)
//    {
//        System.setProperty("webdriver.firefox.bin","D:\\Firefox\\firefox.exe");
//        driver = new FirefoxDriver();
//    }

    public void teardown(WebDriver driver) {
        driver.quit();
    }

    public void visitURL(WebDriver driver, String url) {
        driver.navigate().to(url);
    }

    public void visitURL2(WebDriver driver, String url) {
        driver.get(url);
    }

    public void visitRecentUrl(WebDriver driver, String url) {
        driver.navigate().to(url);
        driver.navigate().back();
    }

    //?
    public void visitNextUrl(WebDriver driver, String url, String url2) {
        driver.navigate().to(url);
        driver.navigate().to(url2);
        driver.navigate().back();
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void operateBrowser(WebDriver driver, int x, int y, int width, int heigth) {
        Point point = new Point(x, y);
        Dimension dimension = new Dimension(width, heigth);
        driver.manage().window().setPosition(point);
        driver.manage().window().setSize(dimension);
//        System.out.println(driver.manage().window().getPosition());
//        System.out.println(driver.manage().window().getSize());
        driver.manage().window().maximize();
    }

    public String getTitle(WebDriver driver) {
        String title = driver.getTitle();
        return title;
    }

    public String getPageSource(WebDriver driver) {
        String pageSource = driver.getPageSource();
        return pageSource;
    }

    public String getCurrentPageUrl(WebDriver driver) {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl;
    }

    //双击
    public void doubleclick(WebDriver driver, WebElement element) {
        Actions builder = new Actions(driver);
        builder.doubleClick(element).build().perform();
    }

    //获取单选下拉列表对象
    public Select operateDropList(WebElement select) {
        Select dropList = new Select(select);
        return dropList;
    }

    //判断是否为单选列表
    public Boolean isDropListMultiple(Select select) {
        return select.isMultiple();
    }

    //获取droplist选中项的文本
    public String getDropListText(Select select) {
        String optiontext = select.getFirstSelectedOption().getText();
        return optiontext;
    }

    //下拉列表，通过索引来选择
    public void selectByIndex(Select select, int index) {
        select.selectByIndex(3);
    }

    //下拉列表，通过值来选择
    public void selectByValue(Select select, String value) {
        select.selectByValue(value);
    }

    //下拉列表，通过可见文本来选择
    public void selectByVisibleText(Select select, String text) {
        select.selectByVisibleText(text);
    }

    //检查单选列表的选项文字是否符合期望
    public Boolean checkSelectText(Select select, String expected[]) {
        List<String> expected_Option = Arrays.asList(expected);

        List<String> actual_Option = new ArrayList<String>();

        for (WebElement option : select.getOptions()) {
            actual_Option.add(option.getText());
        }

        return actual_Option.equals(expected_Option);
    }

    //----------?操作多选的选择列表
    public void operateMultipleOptionDroopList(Select select) {
        select.selectByIndex(3);
        select.selectByValue("shanzha");
        select.selectByVisibleText("荔枝");
        select.deselectAll();
    }

    //判断元素是否选中
    public Boolean elementIsSelect(WebElement element) {
        return element.isSelected();
    }

    //选中元素
    public void elementSelect(WebElement element) {
        if (!element.isSelected()) {
            element.click();
        }
    }

    //多选元素集合
    public void elementsSelected(List<WebElement> elements) {
        for (WebElement element : elements) {
            if (!element.isSelected()) {
                element.click();
            }
        }
    }

    //取消选中元素
    public void elementUnselected(WebElement element) {
        if (element.isSelected()) {
            element.click();
        }
    }

    //取消多选元素集合
    public void elementsUnselected(List<WebElement> elements) {
        for (WebElement element : elements) {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    //选中指定属性值的元素
    public void specElementSelected(List<WebElement> elements, String attr, String text) {
        for (WebElement element : elements) {
            if (element.getAttribute(attr).equals(text)) {
                if (!element.isSelected())
                    element.click();
            }
        }
    }

    //杀掉windows的浏览器进程
    public void operateWindowsProcess(String driverName) {
        WindowsUtils.killByName(driverName);
    }

    //将当前浏览器的窗口截屏
    public void captureScreenInCurrentWindow(WebDriver driver) {
        File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(scr, new File("\\src\\main\\TestData\\Screenshot\\" + System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //初始化JavascriptExecutor
    public JavascriptExecutor initJSExecutor(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    //执行JavaScript脚本
    public String executeJavaScript(JavascriptExecutor js, String script) {
        String text = (String) js.executeScript(script);
        return text;
    }

    //--------------           Actions action = new Actions(driver);
    //拖曳页面元素
    public void dragPageElement(WebElement element, Actions action) {
        for (int i = 0; i < 5; i++) {
            action.dragAndDropBy(element, 0, 10).build().perform();
        }

        for (int i = 0; i < 5; i++) {
            action.dragAndDropBy(element, 10, 0).build().perform();
        }
    }

    //模拟键盘的操作
    public void clickKeys(Actions action) {
        action.keyDown(Keys.CONTROL);
        action.keyDown(Keys.SHIFT);
        action.keyDown(Keys.ALT);
        action.keyUp(Keys.CONTROL);
        action.keyUp(Keys.SHIFT);
        action.keyUp(Keys.ALT);
        //输入大写的adbdefg
        action.keyUp(Keys.SHIFT).sendKeys("abcdefg").perform();
    }

    //模拟鼠标右键事件
    public void rightClickMouse(Actions action, WebElement element) {
        action.contextClick(element).perform();
    }

    //在指定元素上方进行鼠标悬浮
    public void roverOnElement(Actions action, WebElement element) {
        action.moveToElement(element).perform();
    }


    //在指定元素上进行鼠标单击左键的操作
    public void mouseClick(Actions action, WebElement element) {
        action.clickAndHold(element).perform();
    }

    //    在指定元素上进行鼠标单击释放的操作
    public void mouseRelease(Actions action, WebElement element) {
        action.release(element).perform();
//        Assert.assertTrue(driver.getPageSource().contains("已经被按下的鼠标左键被释放抬起"));
//        Assert.assertTrue(driver.getPageSource().contains("单击动作发生"));
//
//        WebElement clearButton = driver.findElement(By.xpath("//input"));
//        element.click();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        String context = div.getText();
//        System.out.print("print out the text in div : "+ context);
//
//        Assert.assertTrue(!context.contains("鼠标"));
    }


    //查看页面元素的属性
    public String getWebElementAttribute(WebElement element, String inputString) {
        element.sendKeys(inputString);
        String inputText = element.getAttribute("value");
        return inputText;
    }

    //获取页面元素的css属性值
    public String getWebElementCssValue(WebElement element, String CssKey) {
        String inputWidth = element.getCssValue(CssKey);
        return inputWidth;
    }

    //10.29隐式等待
    public void testImplictWait(WebDriver driver, int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
//        try
//        {
//            WebElement searchInput = driver.findElement(By.id("query"));
//            WebElement searchButton = driver.findElement(By.id("stb"));
//            searchInput.sendKeys("输入框元素被成功找到");
//            searchButton.click();
//        }
//        catch(NoSuchElementException e)
//        {
//            Assert.fail("没有找到搜索的输入框");
//            e.printStackTrace();
//        }
    }

    //初始化显式等待
    public WebDriverWait initExplicitWait(WebDriver driver, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait;
    }

    //显式等待--是否包含指定文本
    public void ExplicitWaitContains(WebDriverWait wait, String text) {
        wait.until(ExpectedConditions.titleContains(text));
    }

    //显式等待--页面元素处于被选中状态
    public void ExplicitWaitSelected(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.elementToBeSelected(element));
    }

    //显式等待--页面元素是否在页面上可用和可被单击
    public void ExplicitWaitClickable(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    //显式等待--页面元素在页面中存在
    public void ExplicitWaitPresence(WebDriverWait wait, By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    //显式等待--页面元素值可见
    public void ExplicitWaitTextPresence(WebDriverWait wait, WebElement element, String expectedText) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));
    }

    //自定义的显式等待
    public void testCustomExplicitWait(WebDriver driver) {
        try {
            WebElement textInputBox = (new WebDriverWait(driver, 10)).
                    until(new ExpectedCondition<WebElement>() {
                        public WebElement apply(WebDriver d) {
                            return d.findElement(By.xpath("//*[@type='text']"));
                        }
                    });

            Assert.assertEquals("今年夏天西瓜相当甜", textInputBox.getAttribute("value"));

            Boolean containTextFlag = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.findElement(By.xpath("//p")).getText().contains("爱吃");
                }
            });

            Assert.assertTrue(containTextFlag);

            Boolean inputTextVisibleFlag = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.findElement(By.xpath("//input[@type=text]")).isDisplayed();
                }
            });

            Assert.assertTrue(inputTextVisibleFlag);
        } catch (NoSuchElementException e) {
            Assert.fail("页面上的输入框元素未被找到！");
            e.printStackTrace();
        }
    }

    //    private boolean isElementPresent(By by){
//        try
//        {
//            driver.findElement(by);
//            return true;
//        }
//        catch(NoSuchElementException e){
//            return false;
//        }
//    }
//
//    public void testIsElementPresent(WebElement element){
//        driver.get(url1);
//        if(isElementPresent(By.id("query")))
//        {
//            WebElement searchInputBox = driver.findElement(By.id("query"));
//            if(searchInputBox.isEnabled() == true){
//                searchInputBox.sendKeys("sogou首页的搜索输入框被成功找到！");
//            }
//            else
//            {
//                Assert.fail("页面上的输入框元素未被找到！");
//            }
//        }
//    }
    //获取单个句柄
    public String getWindowHandle(WebDriver driver) {
        String parentWindowHandle = driver.getWindowHandle();
        return parentWindowHandle;
    }

    //获取句柄集合
    public Set<String> getWindowHandles(WebDriver driver) {
        Set<String> parentWindowHandle = driver.getWindowHandles();
        return parentWindowHandle;
    }

    //切换windows句柄
    public void switchWindowHandle(WebDriver driver, String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    //切换windows句柄标题
    public String getWindowHandleTitle(WebDriver driver, String windowHandle) {
        String windowHandleTitle = driver.switchTo().window(windowHandle).getTitle();
        return windowHandleTitle;
    }

//    public void testPopUpWindowByTitle(WebDriver driver,String windowHanle,Set<String> windowHandles){
//        if(!windowHandles.isEmpty()){
//            for(String windowHandle : windowHandles)
//            {
//                try
//                {
//                    if(driver.switchTo().window(windowHandle).getTitle().equals("搜狗搜索引擎-上网从搜狗开始"))
//                    {
//                        driver.findElement(By.id("query")).sendKeys("sogou首页的浏览器窗口被找到");
//                    }
//                }
//                catch(NoSuchWindowException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        driver.switchTo().window(parentWindowHandle);
//
//        Assert.assertEquals(driver.getTitle(), "你喜欢的水果");
//    }

//    public void testPopUpWindowByPageSource(){
//        driver.get(url11);
//        String parentWindowHandle = driver.getWindowHandle();
//
//        WebElement sogou = driver.findElement(By.xpath("//a"));
//        sogou.click();
//
//        Set<String> windowHandles = driver.getWindowHandles();
//
//        if(!windowHandles.isEmpty()){
//            for(String windowHandle : windowHandles)
//            {
//                try
//                {
//                    if(driver.switchTo().window(windowHandle).getPageSource().contains("搜狗搜索"))
//                    {
//                        driver.findElement(By.id("query")).sendKeys("sogou首页的浏览器窗口被找到");
//                    }
//                }
//                catch(NoSuchWindowException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        driver.switchTo().window(parentWindowHandle);
//
//        Assert.assertEquals(driver.getTitle(), "你喜欢的水果");
//    }
}
