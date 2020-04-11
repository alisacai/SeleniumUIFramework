package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class ObjectMap {

    Properties properties;

    public ObjectMap(String filePath){
        properties = new Properties();

        try {
            FileInputStream fis;
            fis = new FileInputStream(filePath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public By getLocator(String elementNameInPropFile) throws Exception{
        String locator = properties.getProperty(elementNameInPropFile);
        String locatorType = locator.split(":")[0];
        String locatorValue = locator.split(":")[1];

        if(locatorType.toLowerCase().equals("id"))
            return By.id(locatorValue);
        else if(locatorType.toLowerCase().equals("name"))
            return By.name(locatorValue);
        else if(locatorType.toLowerCase().equals("classname")||locatorType.toLowerCase().equals("class"))
            return By.className(locatorValue);
        else if(locatorType.toLowerCase().equals("tagname")||locatorType.toLowerCase().equals("tag"))
            return By.tagName(locatorValue);
        else if(locatorType.toLowerCase().equals("link")||locatorType.toLowerCase().equals("linktext"))
            return By.linkText(locatorValue);
        else if(locatorType.toLowerCase().equals("partiallinktext"))
            return By.partialLinkText(locatorValue);
        else if(locatorType.toLowerCase().equals("cssSelector"))
            return By.cssSelector(locatorValue);
        else if(locatorType.toLowerCase().equals("xpath"))
            return By.xpath(locatorValue);
        else throw new Exception("输入的locator type未在程序中被定义:"+locatorType);
    }
}
