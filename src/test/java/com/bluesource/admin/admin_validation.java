package com.bluesource.admin;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.Admin;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class admin_validation extends WebBaseTest{

			// ************* *
			// Data Provider
			// **************
			@DataProvider(name = "admin_validate", parallel=true)
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
			 * Open browser navigate to bluesource page
			 * input username and password
			 * login to page
			 * click Admin tab
			 * Validate that: Departmens, titles, industries, role types, company holidays, timesheet locks
			 * and email bluesource users are display
			 * @throws InterruptedException 
			 ******************************************************************************************************/
			
			 @Test(dataProvider = "admin_validate")
			 public void admin_click_test(String username, String password) throws InterruptedException 
			 {
				 
				 //Instantiate page objects
				 LoginPage loginPage = new LoginPage(getDriver());
				 MessageCenter messageCenter = new MessageCenter(getDriver());
				 Admin adminPage = new Admin(getDriver());
				 				 
				 //Begin test steps
				 loginPage.LoginWithCredentials(username, password);
				 messageCenter.check_if_messageCenter_Open();
				try
				{
					adminPage.click_admin_tab(username);
					adminPage.validate_subCats(); 
				}
				catch(NoSuchElementException e)
				{
					System.out.println("User doesn't have admin rights.");
				}
				
				
			 }
	}
