package util;

import java.sql.*;
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

public class MysqlConn {

    public static Object[][] getTestData(String tablename) throws SQLException{
       //声明MySQL数据库的驱动
        String driver = "com.mysql.jdbc.Driver";
        //声明本地数据库的IP地址和数据库名称
        String url="jdbc:mysql://127.0.0.1:3306/gloryroad";
        //声明数据库的用户名。为简化数据库权限设定等操作，本例使用数据库的root用户进行操作
        //在正式对外服务的生产数据库中，建议使用非root的用户账户进行自动化测试相关操作
        String user = "root";
        //声明数据库root用户的登陆密码，这和MySQL数据库安装时候设定的root用户密码要保持一致
        String password = "gloryroad";

        //声明存储测试数据的list对象
        List<Object[]> records = new ArrayList<Object[]>();
        try
        {
            //设定驱动
            Class.forName(driver);
            //声明连接数据库的链接对象，使用数据库服务器地址、用户名和密码作为参数
            Connection conn = DriverManager.getConnection(url,user,password);
            //如果数据库链接可用，打印数据库连接成功的信息
            if(!conn.isClosed()){
                System.out.println("连接数据库成功！");
            }
            //创建statement对象
            Statement statement = conn.createStatement();
            //使用函数参数拼接要执行的SQL语句，此语句用来获取数据表的所有数据行
            String sql = "select * from " + tablename;
            //声明ResultSet对象，存取执行SQL语句后返回的数据结果集
            ResultSet rs = statement.executeQuery(sql);
            //声明一个ResultSetMetaData对象
            ResultSetMetaData rsMetaData = rs.getMetaData();
            //调用ResultSetMetaData对象的getColumnCount方法获取数据行的数据
            int cols = rsMetaData.getColumnCount();
            //使用next方法遍历数据结果集中的所有数据行
            while(rs.next()){
                //声明一个字符型数组，数组大小使用数据行的列个数进行声明
                String fields[] = new String[cols];
                int col = 0;
                //遍历所有数据行中的所有列数据，并存储在字符数组中
                for(int colIdx = 0; colIdx<cols; colIdx++){
                    fields[col] = rs.getString(colIdx+1);
                    col++;
                }
                //将每一行的数据存储到字符数据后，存储到records中
                records.add(fields);
                //数据数据行中的前三列内容，用于验证数据库内容是否正确取出
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" " + rs.getString(3));
            }
            //关闭数据结果集对象
            rs.close();
            //关闭数据库连接
            conn.close();

        }catch(ClassNotFoundException e){
            System.out.println("未能找到MySQL的驱动类");
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
        //定义函数返回值，即Object[][]
        //将存储测试数据的list转换为一个Object的二位数组
        Object[][] results = new Object[records.size()][];
        //设置二位数组每行的值，每行是一个Object对象
        for(int i=0;i<records.size();i++){
            results[i] = records.get(i);
        }
        return results;
    }
}
