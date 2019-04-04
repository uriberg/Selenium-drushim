package emailSelenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class CvSelenium {
	private WebDriver driver;
	private String baseUrl;

	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		//the job link page on drushim
		baseUrl = "https://www.drushim.co.il/job/17792439/caa4c723/?utm_source=alerts&utm_campaign=cv_alerts&utm_medium=email&utm_content=gmail.com&ref=34";
		
		//chrome driver path
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\123\\Documents\\eclipse-workspace\\SeleniumWD2Tutorial\\src\\tutorialselenium\\chromedriver.exe");
		driver = new ChromeDriver();;

		// Maximize the browser's window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(baseUrl);
	}
	
	@Test
	public void fileUpload() throws Exception {
		
		driver.findElement(By.xpath("//a[@class='stdButton orangeBg roundCorners sendCV']")).click();//click on 'Send CV'
		driver.findElement(By.xpath("//a[@href='javascript:ShowLoginContainer();']")).click();//click if you are already a registered member 

		driver.findElement(By.xpath("//input[@id='MainContent_SignUp_Login_EmailText']")).sendKeys("<your email>");//your email	
		driver.findElement(By.xpath("//input[@id='MainContent_SignUp_Login_PasswordText']")).sendKeys("<your password>");//your password
		
		Thread.sleep(2000);
		
		driver.findElement(By.name("ctl00$MainContent$SignUp$Login$SubmitLogin")).click();//submit login
		driver.findElement(By.xpath("//a[@id='MainContent_EditCVDetails']")).click();//edit details before sending your cv
		driver.findElement(By.xpath("//a[@class='showCvCoverPage']")).click();//edit your CV with adding cover letter and more
		
		String cvLetter=StoreTextFile.readFileAsString();//read your cover letter into a string
		
		//cover letter body
		driver.findElement(By.xpath("//textarea[@id='MainContent_Details_coverPage']")).sendKeys(cvLetter);
		
		driver.findElement(By.xpath("//input[@id='MainContent_btnSubmit']")).click();//send your CV
	}
}


class StoreTextFile {
	
	public static String readFileAsString()throws Exception {
	// The name of the file to open.
    String fileName = "<cover letter text file to open>";

    // This will reference one line at a time
    String line = null;
    String result=null;
    StringBuilder sb = new StringBuilder();
    
    

    try {
        // FileReader reads text files in the default encoding.
        FileReader fileReader = 
            new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = 
            new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
        	//System.out.println(line);
            sb.append(line + "\n");
        }   

        // Always close files.
        bufferedReader.close();   
        result=sb.toString();
       
    }
    catch(FileNotFoundException ex) {
        System.out.println(
            "Unable to open file '" + 
            fileName + "'");                
    }
    catch(IOException ex) {
        System.out.println(
            "Error reading file '" 
            + fileName + "'");                  
        // Or we could just do this: 
        // ex.printStackTrace();
    }
    return result;
}
	
	public static void main(String[]args) throws Exception {
		String file=readFileAsString();
		System.out.println(file);
	}
	 

}
