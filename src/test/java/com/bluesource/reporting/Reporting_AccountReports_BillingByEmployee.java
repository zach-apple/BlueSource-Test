package com.bluesource.reporting;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.bluesource.Reporting;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;

public class Reporting_AccountReports_BillingByEmployee extends WebBaseTest{
	
		OrasiDriver driver;
	
		// ************* *
		// Data Provider
		// **************
		@DataProvider(name = "reporting_billingByEmployee", parallel=true)
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
		
		/*
		 * Test steps:
		 * Open browser / navigate to http://10.238.243.127:8080/reporting/login
		 * Login into the page
		 * select the billing by employee tab
		 * select summary radio button
		 * select the account name for account dropdown
		 * select a start and end date
		 * select a project from the list
		 * logout
		 */

		@Test(dataProvider = "reporting_billingByEmployee")
		public void reporting_billingByEmployee_test(String username, String password)
		{
			//declare page objects
			LoginPage loginpage = new LoginPage(getDriver());
	    	Reporting reportingPage = new Reporting(getDriver());
	    	MessageCenter messageCenterpage = new MessageCenter(getDriver());
	    	
	    	loginpage.LoginWithCredentials(username, password);
	    
	    	
		}
}
