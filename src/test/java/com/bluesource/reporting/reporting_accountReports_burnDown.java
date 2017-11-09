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

import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.Reporting;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;

public class reporting_accountReports_burnDown extends WebBaseTest{

	OrasiDriver driver;
	
	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "reporting_accountReports_burnDown", parallel=true)
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
	 * Open browser / navigate to reporting page
	 * Login to the page
	 * Click account reports
	 * select burn down
	 * select an account
	 * generate the report
	 */
	@Test(dataProvider = "reporting_accountReports_burnDown")
	public void reporting_account_reports_burndown(String username, String password) 
	{
		//declare page objects
		LoginPage loginpage = new LoginPage(getDriver());
    	Reporting reportingPage = new Reporting(getDriver());
    	
    	try
    	{
    		//Login to reporting page
        	loginpage.LoginWithCredentials(username, password);
        	
        	//Click the drop down for account reports
        	reportingPage.click_accountReports();
        	
        	//Click on the burn down link under account reports
        	reportingPage.click_accountReports_burndown();
        	
        	//Check that the modal is present
        	reportingPage.check_modal();
        	
        	//Click the select all link
        	reportingPage.click_selectAll_onModal();
        	
        	//Click generate report
        	reportingPage.click_generateReport();
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
