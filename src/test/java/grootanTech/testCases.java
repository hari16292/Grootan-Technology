package grootanTech;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.TeamPage;
import resource.BaseClass;

public class testCases extends BaseClass {
	WebDriver driver;
	Properties prop;
	HomePage h;
	TeamPage t;
	HSSFWorkbook wb;
	HSSFSheet sh1, sh2;
	File newExcel = new File(System.getProperty("user.dir")+"\\Excel_Report\\Report.xls");
	

	// Loading properties file to get test URL
	@BeforeTest
	public void openBrowser() throws Exception {
		prop = new Properties();
		FileInputStream in = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resource\\dataSheet.properties");
		prop.load(in);
		driver = initializeBrowser();
		driver.get(prop.getProperty("url"));
		h = new HomePage(driver);
		t = new TeamPage(driver);

	}

	//write and close the excel file, and close the browser which is opened by webdriver
	@AfterTest
	public void closeBrowser() throws Exception {
		wb.write(newExcel);
		wb.close();
		driver.close();
	}

	/*
	 * clicking all the available sections given in the website and taking
	 * screenshot twise of each page and write list of "JuniorEngineers" in excel
	 * sheet name JuniorEngineers
	 */
	@Test
	public void tc1() throws Exception {
		String homeWindow, blogWindow;
		Set<String> st;
		Iterator<String> it;
		h.getAccept().click();
		wb = new HSSFWorkbook();
		sh1 = wb.createSheet("JuniorEngineers");
		int l=0;
		for (int i = 1; i < 3; i++) {
			h.getHome().click();
			takeSnapShot(h.getHome().getText(), driver, i);
			h.getServices().click();
			takeSnapShot(h.getServices().getText(), driver, i);
			h.getOpenSource().click();
			takeSnapShot(h.getOpenSource().getText(), driver, i);
			h.getBlog().click();
			st = driver.getWindowHandles();
			it = st.iterator();
			homeWindow = it.next();
			blogWindow = it.next();
			driver.switchTo().window(blogWindow);
			takeSnapShot(h.getBlog().getText(), driver, i);
			driver.close();
			driver.switchTo().window(homeWindow);
			
			h.getTeam().click();
			takeSnapShot(h.getTeam().getText(), driver, i);
			if (i == 1) {
				takeWebElementSnapShot(driver, t.getCTO(), "CTO");
				takeWebElementSnapShot(driver, t.getHRM(), "HRM");
				List<WebElement> je = driver.findElements(t.getJE());
				for(WebElement eachJE : je) {
					writeXL(wb, sh1, l, String.valueOf(l+1), eachJE.getText(), sh1.getSheetName());
					l++;
				}
			}
			h.getCareers().click();
			takeSnapShot(h.getCareers().getText(), driver, i);
			h.getContactUs().click();
			takeSnapShot(h.getContactUs().getText(), driver, i);
		}
	}

	//Compare screenshots of each page and write the result with description in excel sheet name as "TSR"
	@Test
	public void tc2() throws Exception {
		List<WebElement> sections = driver.findElements(h.getAllSections());
		boolean result;
		sh2 = wb.createSheet("TSR");
		for (int i = 0; i <= sections.size(); i++) {
			if(i<sections.size()) {
				result = compareImg(sections.get(i).getText(), sections.get(i).getText());
				writeXL(wb, sh2, i, sections.get(i).getText(), Boolean.toString(result), sh2.getSheetName());
			}	
			else {
				result = compareImg("CTO", "HRM");
				writeXL(wb, sh2, i, "CTO & Co-Founder image and HR Manager image in Team", Boolean.toString(result), sh2.getSheetName());
			}
				
			if (result == true)
				System.out.println("Images are different");
				
			else
				System.out.println("Images are same");
		}
	}
}
