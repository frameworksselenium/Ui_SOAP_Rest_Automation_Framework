package work;

import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class run {

    private static WebDriver driver = null;

    public static void main(String[] args) {

        String homePage = "http://www.zlti.com";
        String url = "";
        HttpURLConnection huc = null;
        int respCode = 200;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(homePage);
        List<WebElement> links = driver.findElements(By.xpath(("//*[@id='zlFooterMap']/div[2]/ul/li")));
        Iterator<WebElement> it = links.iterator();
        int counter = 1;
        while(it.hasNext()){
            WebElement link = driver.findElement(By.xpath(("//*[@id='zlFooterMap']/div[2]/ul/li[" + counter + "]/a")));
            String linkNAme = link.getAttribute("href");
            System.out.println(linkNAme);
            counter = counter + 1;
            link.click();
            driver.navigate().back();
        }
        //driver.quit();
    }
}