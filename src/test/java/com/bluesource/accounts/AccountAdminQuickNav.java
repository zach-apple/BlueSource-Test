package com.bluesource.accounts;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class AccountAdminQuickNav extends WebBaseTest {

	// **************
	// Data Provider
	// **************
	/*@DataProvider(name = "accounts_industry", parallel=true)
	public Object[][] scenarios() {
		return new ExcelDataProvider("/testdata/blueSource_Users.xlsx", "Sheet1").getTestData();
	}*/
	
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
    
    @Test(groups = {"smoke"} )
    public void accountAdminQuickNav() {
    	Header header = new Header(getDriver());
    	Accounts accounts = new Accounts(getDriver());
    	LoginPage loginPage = new LoginPage(getDriver());
    	
    	TestReporter.logStep("Test started");
    	
    	TestReporter.logStep("Login to application as an Account Admin");
    	loginPage.AdminLogin();
    	
    	TestReporter.logStep("Go to the Accounts page");
    	header.navigateAccounts();
    	
    	TestReporter.assertTrue(accounts.verifyQuickNavButtonEachPage(), "Checked that the Quick Nav button is on each page of accounts");
    	
    	TestReporter.logStep("Go to an account page and verify the Quick Nav button is visible");
    	accounts.clickFirstAccountLink();
    	
    	TestReporter.assertTrue(accounts.verifyQuickNavButtonIsVisible(), "Checked that the Quick Nav button is visible on an account page");
    	
    }
}
