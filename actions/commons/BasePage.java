package commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}
	
	public String getTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}
	
	
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}
	
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}
	
	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public Alert waitForAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void acceptAlert(WebDriver driver) {
		waitForAlertPresence(driver).accept();
	}
	
	public void cancelAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}
	
	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}
	
	public void sendkeyToAlert(WebDriver driver, String textValue) {
		waitForAlertPresence(driver).sendKeys(textValue);
	}
	
	public void switchToWindowByID(WebDriver driver, String windowID)
	{
		Set<String> allWindowIDs = driver.getWindowHandles();
		for(String id:allWindowIDs)
		{
			if(!id.equals(windowID))
			{
				driver.switchTo().window(windowID);
				break;
			}
		}
	}
	
	public void switchToWindowByTitle(WebDriver driver, String tabTitle)
	{
		Set<String> allWindowIDs = driver.getWindowHandles();
		for(String window:allWindowIDs)
		{
			driver.switchTo().window(window);
			String windowTitle = driver.getTitle();
			System.out.println("Title: " +windowTitle);
			if(windowTitle.equals(tabTitle))
			{
				break;
			}
		}
	}
	
	public void closeAllWindowWithoutParent(WebDriver driver, String parentID)
	{
		Set<String> allWindows = driver.getWindowHandles();
		for(String id:allWindows)
		{
			if(!id.equals(parentID))
			{
				driver.switchTo().window(id);
				driver.close();
			}
		}
		
		driver.switchTo().window(parentID);
	}
	
	public void clickToElement(WebDriver driver, String xpathLocator) {
		getWebElement(driver, xpathLocator).click();
	}
	
	public void sendKeyToElement(WebDriver driver, String xpathLocator, String textValue) {
		WebElement element = getWebElement(driver, xpathLocator);
		element.clear();
		element.sendKeys(textValue);
	}
	
	public WebElement getWebElement(WebDriver driver, String xpathLocator) {
		return driver.findElement(getXpath(xpathLocator));
	}
	
	public By getXpath(String xpathLocator) {
		return By.xpath(xpathLocator);
	}
	
	public void selectItemInDefaultDropdown(WebDriver driver, String xpathLocator, String textItem) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		select.selectByValue(textItem);
	}
	
	public String getSelectedItemDefaultDropdown(WebDriver driver, String xpathLocator) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		return select.getFirstSelectedOption().getText();
	}
	
	public boolean isDropdownMultiple(WebDriver driver, String xpathLocator) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		return select.isMultiple();
	}
	
	public void selectItemInCustomerDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedItem)
	{
		getWebElement(driver, parentXpath).click();
		driver.findElement(By.xpath(parentXpath)).click();
		sleepInSecond(3);
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		
		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getXpath(childXpath)));
		
		for (WebElement item: allItems)
		{
			if(item.getText().trim().equals(expectedItem))
			{
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true).", item);
				sleepInSecond(1);
				item.click();
				break;
			}
		}
	}
	
	public void sleepInSecond(long timeout) {
		try {
			Thread.sleep(timeout *1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName) {
		return getWebElement(driver, xpathLocator).getAttribute(attributeName);
	}
	
	public String getElementText(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).getText();
	}
	
	public String getElementCssValue(WebDriver driver, String xpathLocator, String propertyName) {
		return getWebElement(driver, xpathLocator).getCssValue(propertyName);
	}
	
	

}
