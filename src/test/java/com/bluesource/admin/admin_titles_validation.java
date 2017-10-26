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

public class admin_titles_validation extends WebBaseTest{

	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "admin_title_validate", parallel=true)
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
	
	
	/******************************************************************************************************
	 * Test steps:
	 * Open browser / navigate to bluesource page
	 * Input username and password 
	 * Login into the page
	 * click the admin tab
	 * click titles
	 * validate that titles appear in table
	 * logout of page
	 * @throws InterruptedException 
	 ******************************************************************************************************/
	
	 @Test(dataProvider = "admin_title_validate")
	 public void admin_title_validation_test(String username, String password) throws InterruptedException
	 {
		//Instantiate page objects
		 LoginPage loginPage = new LoginPage(getDriver());
		 MessageCenter messageCenter = new MessageCenter(getDriver());
		 Admin adminPage = new Admin(getDriver());
		 
		 try
		 {
			 loginPage.LoginWithCredentials(username, password);
			 messageCenter.check_if_messageCenter_Open();
			 adminPage.click_admin_tab(username);
			 adminPage.click_titlesTab();
			 adminPage.titles_page_validation();
		 }
		 catch(NoSuchElementException e)
		 {
			 System.out.println(username + " does not have admin rights.");
		 }
	 }
}
