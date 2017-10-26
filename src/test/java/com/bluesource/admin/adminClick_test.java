package com.bluesource.admin;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Admin;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class adminClick_test extends WebBaseTest{

		
	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "adminClick", parallel=true)
		public Object[][] scenarios() {
			return new ExcelDataProvider("/excelsheets/blueSource_Users.xlsx", "Sheet1").getTestData();
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
	
	 @Test(dataProvider = "adminClick")
	 public void admin_click_test(String username, String password) throws InterruptedException
	 {
		 try
		 {
			//Instantiate page objects
			 LoginPage loginPage = new LoginPage(getDriver());
			 MessageCenter messageCenter = new MessageCenter(getDriver());
			 Admin adminPage = new Admin(getDriver());
			 
			 //login to bluesource page
			 loginPage.LoginWithCredentials(username, password);
			 
			 //Check that the messageCenter popover is not present at login
			 messageCenter.check_if_messageCenter_Open();
			 
			 //click on the admin link if it is found
			 adminPage.click_admin_tab(username);
			
		 }
		 catch(NoSuchElementException e)
		 {
			 System.out.println("Admin tab not found for: " + username);
		 }
		 
	 }
	 
}
