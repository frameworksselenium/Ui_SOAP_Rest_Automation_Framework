package work;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class run1 {

    public static void main(String[] args) {
        clickAllLinksInWebPage();
        clickAllLinksInParticularSection();
    }

    public static void clickAllLinksInWebPage(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.zlti.com/");
        java.util.List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println(links.size());
        for (WebElement element : links) {
            System.out.println(element.getText());
            element.click();
            driver.navigate().back();
        }
        driver.quit();
    }

    public static void clickAllLinksInParticularSection() {

        String homePage = "http://www.zlti.com";
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(homePage);
        List<WebElement> links = driver.findElements(By.xpath(("//*[@id='zlFooterMap']/div[2]/ul/li")));
        int counter = 1;
        int linksSize = links.size();
        for(int ii = 1; ii <= linksSize; ii=ii+1) {
            WebElement link = driver.findElement(By.xpath(("//*[@id='zlFooterMap']/div[2]/ul/li[" + counter + "]/a")));
            String linkNAme = link.getAttribute("href");
            System.out.println(linkNAme);
            counter = counter + 1;
            link.click();
            driver.navigate().back();
        }
        driver.quit();
    }

}