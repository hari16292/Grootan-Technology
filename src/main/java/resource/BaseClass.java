package resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;


public class BaseClass {
	WebDriver driver;

	

	public WebDriver initializeBrowser() throws Exception {
		//Initialize Firefox browser and return initialized driver
		WebDriverManager.firefoxdriver().setup();
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");	
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	
	public void takeSnapShot(String ssName, WebDriver driver, int i) throws Exception {
		//Taking screenshot of the window screen and saved in desired folder
		Thread.sleep(1000);
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile;
		if(i==1)
			DestFile=new File(System.getProperty("user.dir") + ".\\Folder1\\" + ssName + ".png");
		else
			DestFile=new File(System.getProperty("user.dir") + ".\\Folder2\\" + ssName + ".png");
		FileUtils.copyFile(SrcFile, DestFile);

	}
	
	public boolean compareImg(String imgName1, String imgName2) throws Exception {
		//Compare screenshots and return result
		BufferedImage f1 = ImageIO.read(new File(System.getProperty("user.dir") + "\\Folder1\\" + imgName1 + ".png"));
		BufferedImage f2 = ImageIO.read(new File(System.getProperty("user.dir") + "\\Folder2\\" + imgName2 + ".png"));
		ImageDiffer imgDiff = new ImageDiffer();
		ImageDiff diff = imgDiff.makeDiff(f1, f2);
		return diff.hasDiff();
	}
	
	public void takeWebElementSnapShot(WebDriver driver, WebElement elemnt, String imgName) throws Exception {
		//Taking screenshot of given specific element and saved in desired folder
		Thread.sleep(1000);
		File img = ((TakesScreenshot)elemnt).getScreenshotAs(OutputType.FILE);
		if(imgName.contains("CTO"))
			org.openqa.selenium.io.FileHandler.copy(img, new File(System.getProperty("user.dir") + ".\\Folder1\\" + imgName + ".png"));
		else
			org.openqa.selenium.io.FileHandler.copy(img, new File(System.getProperty("user.dir") + ".\\Folder2\\" + imgName + ".png"));
	}
	
	public void writeXL(HSSFWorkbook wb, HSSFSheet sh, int rowNo, String s1, String s2, String sheetName) throws Exception {
		//create excel sheet and set value in created new file 
		Cell cell1 = null, cell2 = null;
		Row row;
		if(sheetName.contains("TSR")) {
			if(rowNo==0) { 
				row = sh.createRow(rowNo);
				cell1 = row.createCell(0);
				cell1.setCellValue("TestCase Description");
				cell2 = row.createCell(1);
				cell2.setCellValue("Status");
				cell1.setCellStyle(boldFont(wb, "Bold"));
				cell2.setCellStyle(boldFont(wb, "Bold"));
			}
			row = sh.createRow(rowNo+1);
			sh.getRow(rowNo+1).createCell(0).setCellValue("Compare the screenshots of "+s1+" page from Folder1 and Folder2");
			if(s2.contains("true")) {
				cell2 = row.createCell(1);
				cell2.setCellValue("Fail");
				cell2.setCellStyle(boldFont(wb, "Fail"));
			}		
			else {
				cell2 = row.createCell(1);
				cell2.setCellValue("Pass");
				cell2.setCellStyle(boldFont(wb, "Pass"));
			}	
		}
		else if (sheetName.contains("JuniorEngineers")){ 
			if(rowNo==0) {
				row = sh.createRow(rowNo);
				cell1 = row.createCell(0);
				cell1.setCellValue("SI.No");
				cell2 = row.createCell(1);
				cell2.setCellValue("Employee Name");
				cell1.setCellStyle(boldFont(wb, "Bold"));
				cell2.setCellStyle(boldFont(wb, "Bold"));
				
			}
			sh.createRow(rowNo+1);
			sh.getRow(rowNo+1).createCell(0).setCellValue(s1);
			sh.getRow(rowNo+1).createCell(1).setCellValue(s2);
			
		}
		
	}
	
	public CellStyle boldFont(HSSFWorkbook wb, String s1) {
		//Update font color and bold fond for given style
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if(s1.contains("Bold"))
			font.setBold(true);
		else if(s1.contains("Fail"))
			font.setColor(Font.COLOR_RED);
		style.setFont(font);
		return style;		
	}
	
}