package com.bluesource.reporting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
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
import com.orasi.utils.TestReporter;
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
			return new ExcelDataProvider("/excelsheets/blueSource_reporting_withDates.xlsx", "Sheet1").getTestData();
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
		 * select the billing by employee tab under account reports
		 * select summary radio button
		 * select the account name for account drop down
		 * select a start and end date
		 * select a project from the list
		 * logout
		 */

		@Test(dataProvider = "reporting_billingByEmployee")
		public void reporting_billingByEmployee_test(String username, String password, String startDate, String endDate)
		{
			//declare local variables
			String currentPage, expectedPage, message;
			
			//declare page objects
			LoginPage loginpage = new LoginPage(getDriver());
	    	Reporting reportingPage = new Reporting(getDriver());
	    		    	
	    	//Initialize variables 
	    	currentPage = getDriver().getCurrentUrl();
	    	expectedPage = "http://10.238.243.127:8080/reporting/login";
	    	message = "On reporting page";
	    	
	    	//check that the current page is the reporting page
	    	TestReporter.assertEquals(currentPage, expectedPage, message);
	    	
	    	try
	    	{
	    		//login into the reporting page
		    	loginpage.LoginWithCredentials(username, password);
		    	
		    	//click on the account reports drop down
		    	reportingPage.click_accountReports();
		    	
		    	//click on billing by employee under account reports
		    	reportingPage.click_billingByEmployee();
		    	
		    	//check that the modal appears
		    	reportingPage.check_modal();
		    	
		    	//fill out modal information
		    	reportingPage.fill_out_billing_by_employee(startDate, endDate);
		    	
	    	}
	    	catch(WebDriverException e)
	    	{
	    		try
	    		{
	    			//check for page error upon login
			    	reportingPage.check_for_error(username);
			    	TestReporter.log(e.getLocalizedMessage());
	    		}catch(NoSuchElementException err)
	    		{
	    			TestReporter.log(err.getLocalizedMessage());
	    		}
	    	}
	    
		}
}
