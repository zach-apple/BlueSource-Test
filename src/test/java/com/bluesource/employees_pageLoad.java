package com.bluesource;

import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Employees;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class employees_pageLoad extends WebBaseTest{
	

	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "employees_pageLoad", parallel=true)
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
    
    @Test(dataProvider = "employees_pageLoad")
    public void employee_pageLoad_test(String username, String password)
    {   
    	String currentURL, expectedURL, description;
    	
    	LoginPage loginpage = new LoginPage(getDriver());
    	Employees employeespage = new Employees(getDriver());
    	
    	try
    	{
	    	//Login to the bluesource page
	    	loginpage.LoginWithCredentials(username, password);
	    	
	    	//Check that the current page is the employees page
	    	currentURL = getDriver().getCurrentUrl();
	    	expectedURL = "http://10.238.243.127/";
	    	description = "Page url's are the same";
	    	
	    	TestReporter.assertEquals(currentURL, expectedURL, description);
	    	System.out.println("Current page URL: " + currentURL);
    	}
    	catch(WebDriverException e)
    	{
    		System.out.println("WebDriver exception has occured \n: " + e.getLocalizedMessage());
    	}
    }
}