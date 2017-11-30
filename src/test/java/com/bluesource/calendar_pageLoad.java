package com.bluesource;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Calendar;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class calendar_pageLoad extends WebBaseTest{

		// ************* *
		// Data Provider
		// **************
		@DataProvider(name = "calendar_pageLoad", parallel=true)
		public Object[][] scenarios() {
			return new ExcelDataProvider("/testdata/blueSource_Users.xlsx", "Sheet1").getTestData();
		}
		
		@BeforeMethod
	    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
	    	"operatingSystem", "environment" })
	    public void setup(@Optional String runLocation, String browserUnderTest,
		    String browserVersion, String operatingSystem, String environment) {
	    	setApplicationUnderTest("BLUESOURCE");
			setBrowserUnderTest(browserUnderTest);
			setBrowserVersion(browserVersion);
			setOperatingSystem(operatingSystem);
			setRunLocation(runLocation);
			setEnvironment(environment);
			setThreadDriver(true);
			testStart("");
		}
	    
	    @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	    
	    @Test(dataProvider = "calendar_pageLoad")
	    public void calendar_pageLoad_test(String username, String password) throws InterruptedException{
	    	//Create variables
	    	String expectedUrl, actualUrl;
	    	
	    	//Instantiate pages needed as objects
	    	LoginPage loginPage = new LoginPage(getDriver());
	    	Calendar calendar = new Calendar(getDriver());
	    	MessageCenter messageCenter = new MessageCenter(getDriver());
	    	
	    	//login / check that message center is closed / click on calendar link
	    	loginPage.LoginWithCredentials(username, password);
	    	messageCenter.check_if_messageCenter_Open();
	    	calendar.click_calendar_link(username);
	    	
	    	//Wait for 1 second / get current url / set variable
	    	Thread.sleep(1000);
	    	actualUrl = getDriver().getCurrentUrl();
	    	expectedUrl = "http://10.238.243.127/calendar";
	    	
	    	//display the actual url / report check that the page is loaded
	    	System.out.println("actual url: " + actualUrl);
	    	TestReporter.assertEquals(expectedUrl, actualUrl, "Addresses are the same");
	    }
}