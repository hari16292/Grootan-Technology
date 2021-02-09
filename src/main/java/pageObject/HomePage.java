package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	
	public HomePage(WebDriver driver)
	{
			this.driver = driver;
			PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(text(),'Home')]")
	private WebElement home;
	
	@FindBy(xpath = "//a[contains(text(),'Services')]")
	private WebElement services;
	
	@FindBy(xpath = "//a[contains(text(),'Open Source')]")
	private WebElement openSource;
	
	@FindBy(xpath = "//a[contains(text(),'Blog')]")
	private WebElement blog;
	
	@FindBy(xpath = "//a[contains(text(),'Team')]")
	private WebElement team;
	
	@FindBy(xpath = "//a[contains(text(),'Careers')]")
	private WebElement careers;
	
	@FindBy(xpath = "//a[contains(text(),'Contact Us')]")
	private WebElement contactUs;
	
	@FindBy(xpath = "//button[contains(text(),'Allow Cookies')]")
	WebElement acceptCookies;
	
	@FindBy(xpath = "//iframe[@class='drift-frame-chat']")
	WebElement openChat;
	
	By section = By.xpath("//li[@class='st-nav-section st-nav-primary nav-item']/a");
	
	
	public WebElement getHome() {
		return home;
	}
	
	public WebElement getServices() {
		return services;
	}
	
	public WebElement getOpenSource() {
		return openSource;
	}
	
	public WebElement getBlog() {
		return blog;
	}
	
	public WebElement getTeam() {
		return team;
	}
	
	public WebElement getCareers() {
		return careers;
	}
	
	public WebElement getContactUs() {
		return contactUs;
	}
	
	public WebElement getAccept() {
		return acceptCookies;
	}
	
	public By getAllSections() {
		return section;
	}
	
	public WebElement getOpenChat() {
		return openChat;
	}


}
