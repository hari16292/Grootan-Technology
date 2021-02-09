package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/*Capsule the locators value in Team page which are needed in this project*/
public class TeamPage {
	
	WebDriver driver;
	
	public TeamPage(WebDriver driver)
	{
			this.driver = driver;
			PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//div[@class='row pb-3']/div/h5[contains(text(),'CTO & Co-Founder')]/..")
	WebElement CTO;
	
	@FindBy(xpath = "//div[@class='row pb-3']/div/h5[contains(text(),'HR Manager')]/..")
	WebElement HRM;
	
	By JE = By.xpath("//div[@class='col-xl-10 col-lg-12']/div/div/h5[contains(text(),'Junior Engineer')]/../h3");
	
	public WebElement getCTO() {
		return CTO;
	}

	public WebElement getHRM() {
		return HRM;
	}
	
	public By getJE() {
		return JE;
	}
}
