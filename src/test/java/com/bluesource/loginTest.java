/*
 * Test for logging into the blueSource page.
 * @author: Daniel Smith
 */

package com.bluesource;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class loginTest extends WebBaseTest{	
	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "login", parallel=true)
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
	
	 @Test(dataProvider = "login")
	 public void login_test(String username, String password)
	 {
		 LoginPage loginPage = new LoginPage(getDriver());
		 
		 try
		 {
			 loginPage.LoginWithCredentials(username, password);
			 loginPage.verifyPageIsLoaded();
			 
			 loginPage.check_login(username);
		 }
		 catch(NoSuchElementException e)
		 {
			 loginPage.invalid_login();
		 }
	} 
}