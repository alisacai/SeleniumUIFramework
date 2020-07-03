package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CSV {
    public static Object[][] getTestData(String fileName) throws IOException {
        List<Object[]> records = new ArrayList<Object[]>();
        String record;
        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader file = new BufferedReader(isr);
        file.readLine();

        while ((record = file.readLine()) != null) {
            String newRecord = record.replace("\"", "");
            String fields[] = record.split(",");
            records.add(fields);
        }
        file.close();

        Object[][] results = new Object[records.size()][];

        for (int i = 0; i < records.size(); i++) {
            results[i] = records.get(i);
        }
        return results;
    }

//
//    public void testGetData() throws IOException {
//        getTestData(Constant.TestDataCSVFilePath);
//    }
}