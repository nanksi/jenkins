package testng_assignment;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class herokuappTestng {
	WebDriver driver;
	String url;

	@BeforeTest
	public void openherokuapp() {
		WebDriverManager.edgedriver().setup();
		driver= new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		url= " https://the-internet.herokuapp.com/";
		
	}
	
	@Test(priority = 1)
	private void multipleWindows() {
	    driver.get(url);
		driver.findElement(By.linkText("Multiple Windows")).click();
		driver.findElement(By.linkText("Click Here")).click();
		
		Set<String> id = driver.getWindowHandles();
		Iterator<String> it = id.iterator();
		String parentId = it.next();
		String childId = it.next();
		
		driver.switchTo().window(childId);
		System.out.println(driver.findElement(By.tagName("h3")).getText());
		
		driver.switchTo().window(parentId);
		System.out.println(driver.findElement(By.tagName("h3")).getText());

	}
	
	@Test(priority = 2)
	private void multipleFrames() {
		driver.get(url);
		driver.findElement(By.linkText("Frames")).click();
		driver.findElement(By.linkText("Nested Frames")).click();
		
		driver.switchTo().frame(driver.findElement(By.name("frame-top")));
		driver.switchTo().frame(driver.findElement(By.name("frame-middle")));
		
		System.out.println(driver.findElement(By.id("content")).getText());

	}
	
	@Test(priority = 3)
	private void dragAndDropAction() {
		driver.get(url);
		driver.findElement(By.linkText("Drag and Drop")).click();
		
		WebElement source= driver.findElement(By.id("column-a"));
		WebElement target= driver.findElement(By.id("column-b"));
		
		Actions act= new Actions(driver);
		act.dragAndDrop(source, target).build().perform();

	}
	
	@Test(priority = 4)
	private void mouseHover() {
		driver.get(url);
		List<WebElement> links= driver.findElements(By.tagName("a"));
		
		for(int i=0; i<links.size(); i++) {
			String link= links.get(i).getAttribute("href");
			System.out.println(link);
		}
		
		driver.findElement(By.linkText("Hovers")).click();
		
		SoftAssert softAssert= new SoftAssert();
		softAssert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/hovers");
		softAssert.assertAll();
		
		Actions act= new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//div[1]/img[1]"))).build().perform(); //img of user1
		driver.findElement(By.linkText("View profile")).click();
		System.out.println(driver.findElement(By.tagName("h1")).getText());
	}

}
