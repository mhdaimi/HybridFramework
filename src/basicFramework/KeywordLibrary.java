package basicFramework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class KeywordLibrary {

	static WebDriver driver;
	static String parentWindow;
	static String textMessage = ""; 

	public static void controller(String methodName, String param1, String param2, String param3, String param4, String param5)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Method method = KeywordLibrary.class.getMethod(methodName, String.class, String.class, String.class, String.class, String.class);
		method.invoke(methodName, param1, param2, param3, param4, param5);
	}

	public static void openBrowser(String param1, String param2, String param3, String param4, String param5) {

		if (param2.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\FATEEMA\\Downloads\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}

		driver.get(param1);

	}

	public static void enterText(String param1, String param2, String param3, String param4, String param5) {
		
		 switch(param1) { 
		 	case "name": driver.findElement(By.name(param2)).sendKeys(param3); break;
		  
		 	case "id": driver.findElement(By.id(param2)).sendKeys(param3); break;
		  
		 	case "xpath": driver.findElement(By.xpath(".//*[@" + param2 + "='" + param3 +
				  		"']")).sendKeys(param4); break; 
				  		}
	}

	public static void clickButton(String param1, String param2, String param3, String param4, String param5) {
		switch(param1) { 
	 	case "name": driver.findElement(By.name(param2)).click(); break;
	  
	 	case "id": driver.findElement(By.id(param2)).click(); break;
		
	 	case "xpath": 
	 		
	 		if(! param4.contains("null")) {
	 			driver.findElement(By.xpath(".//*[@" + param2 + "='" + param3 + "']/" + param4)).click();
		 		break;
	 		} else {
	 			
	 			driver.findElement(By.xpath(".//*[@" + param2 + "='" + param3 + "']")).click();
	 			break;
	 		}
		
		}

	}

	public static void selectRadio(String param1, String param2, String param3, String param4, String param5) {
		
		switch(param1) { 
	 	case "name": List<WebElement> radiolist = driver.findElements(By.name(param2));
		 	for (WebElement radio : radiolist) {
				if (radio.getAttribute("value").equals(param3)) {
					radio.click();
				}
			}
	 		break;
	  
	 	case "id": List<WebElement> radiolist1 = driver.findElements(By.id(param2));
		 	for (WebElement radio : radiolist1) {
				if (radio.getAttribute("value").equals(param3)) {
					radio.click();
				}
			}
		 	break;
	 
	 	case "xpath": List<WebElement> radiolist2 = driver.findElements(By.xpath(".//*[@" + param1 + "='" + param2 + "']"));
		 	for (WebElement radio : radiolist2) {
				if (radio.getAttribute("value").equals(param3)) {
					radio.click();
				}
			}
	 		break;
		
		}
	}
	
	public static void getText(String param1, String param2, String param3, String param4, String param5) {
		
		String textMessage="";
		
		 switch(param1) { 
		 	case "name": 
		 		textMessage = driver.findElement(By.name(param2)).getText(); 
		 		break;
		  
		 	case "id": 
		 		textMessage = driver.findElement(By.id(param2)).getText();
		 		break;
		  
		 	case "xpath": 
		 		textMessage = driver.findElement(By.xpath(".//*[@" + param2 + "='" + param3 +
				  		"']")).getText(); 
		 		break; 
				  		}
		 System.out.println(textMessage);
	}

	public static void closeWindow(String param1, String param2, String param3, String param4, String param5) {

		driver.close();
	}
	
	
	public static void selectFromDropDown(String param1, String param2, String param3, String param4, String param5) {
		
		switch(param1) { 
	 		case "name": WebElement dropDownItems = driver.findElement(By.name(param2));
	 			Select obj = new Select(dropDownItems);
	 			obj.selectByVisibleText(param3);
	 			break;
	 		
	 		case "id": WebElement dropDownItemsId = driver.findElement(By.id(param2));
	 			Select objId = new Select(dropDownItemsId);
	 			objId.selectByVisibleText(param3);
	 			break;
	 			
	 		case "xpath": 
	 			if(! param4.contains("null")) {
	 				List<WebElement> dropDownItemsXpath = driver.findElements(By.xpath(".//*[@" + param2 + "='" + param3 + "']/" + param4));
	 				for (WebElement eachValue : dropDownItemsXpath) {
	 					if (eachValue.getText().contains(param5)) {
	 						System.out.println("Found: " + param5);
	 						eachValue.click();
	 						break;
	 					}
	 				}
		 			break;
		 		} else {
		 			
		 			WebElement dropDownItemsXpath = driver.findElement(By.xpath(".//*[@" + param2 + "='" + param3 + "']"));
		 			Select objXpath = new Select(dropDownItemsXpath);
		 			objXpath.selectByVisibleText(param4);
		 			break;
		 		}
	 			
	 	
		}
		
	}
	
	public static void switchToFrame(String param1, String param2, String param3, String param4, String param5) {
		
		driver.switchTo().frame(param1);
	}
	
	public static void getParentWindow(String param1, String param2, String param3, String param4, String param5) {
		parentWindow = driver.getWindowHandle();
	}
	
	public static void switchToWindow(String param1, String param2, String param3, String param4, String param5) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String window : allWindows) {
			if (! window.contains(parentWindow)) {
				driver.switchTo().window(window);
			}
		}
	}
	
	public static void switchToParentWindow(String param1, String param2, String param3, String param4, String param5) {
		if(param1.contains("frame")) {
			driver.switchTo().parentFrame();
		} else {
			driver.switchTo().window(parentWindow);
		}
		
	}
	
	public static void refreshPage(String param1, String param2, String param3, String param4, String param5) {
		driver.navigate().refresh();
	}
	
	public static void threadSleep(String param1, String param2, String param3, String param4) throws Exception, InterruptedException {
		//Thread.sleep(3000);
		//TimeUnit.SECONDS.sleep(3);
		int a = 0;
		for(int i = 0; i < 9999999; i ++) {
			a = i;
		}
		System.out.println(a);
	}
}
