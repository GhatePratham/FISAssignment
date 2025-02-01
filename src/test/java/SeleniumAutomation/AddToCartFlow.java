package SeleniumAutomation;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;

public class AddToCartFlow {

	@Test
	public void TC_AddToCart() throws InterruptedException {
		
		//Step 1 : Open browser
		WebDriver driver = new ChromeDriver();
		
		//Step 2 :Navigate to ebay.com
		driver.get("https://www.ebay.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String parentID = driver.getWindowHandle();
	
		//Step 3 :Search for ‘book’
		WebElement Searchbox = driver.findElement(By.xpath("//input[@title='Search']"));
		Searchbox.sendKeys("book");
		
		//Step 4 :Click on the first book in the list
		WebElement searchButton = driver.findElement(By.xpath("//span[@class='gh-search-button__label']"));
		searchButton.click();
		List<WebElement> BooksList = driver.findElements(By.xpath("//div[@class='s-item__image-wrapper image-treatment']//img[contains(@src ,'https://i.ebayimg.com')]"));
		for(int i = 0 ; i<=BooksList.size()-1 ; i++) {
			
			BooksList.get(0).click();
			break;
		}
		
		//Step 5 :In the item listing page, click on ‘Add to cart’
		Set<String> allWindows = driver.getWindowHandles();
	
		for(String childID:allWindows) {
			
			if(!parentID.equalsIgnoreCase(childID)) {
				
				driver.switchTo().window(childID);
				WebElement AddToCartButton = driver.findElement(By.xpath("//span[text()='Add to cart']"));
				AddToCartButton.click();
				
		//Step 6 :verify the cart has been updated and displays the number of items in the cart		
				WebElement  BadgeCountText= driver.findElement(By.xpath("//span[@class='badge']"));
				String ExpCartBadge = "1";
				String ActCartBadge = BadgeCountText.getText();
				System.out.println("Number of items in cart : "+ActCartBadge);	
				Assert.assertEquals(ExpCartBadge, ActCartBadge , "ActResult is not equal to expResult");
		       
			}
		}
		
		//TearDown
		driver.quit();

	}
}
