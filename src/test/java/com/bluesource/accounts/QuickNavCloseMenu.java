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

public class QuickNavCloseMenu extends WebBaseTest{
	
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
    public void quickNavCloseMenu() {
    	Header header = new Header(getDriver());
    	Accounts account = new Accounts(getDriver());
    	LoginPage loginPage = new LoginPage(getDriver());
    	
    	TestReporter.logStep("Test start");
    	
    	TestReporter.logStep("Login to application as an admin");
    	loginPage.AdminLogin();
    	
    	TestReporter.logStep("Go to the accounts page");
    	header.navigateAccounts();

    	TestReporter.logStep("Open the Quick Nav Menu");
    	account.clickQuickNav();
    	
    	TestReporter.logStep("Close the Quick Nav menu with the close button on the menu");
    	account.closeQuickNav();

    	TestReporter.assertFalse(account.verifyQuickNavCloseButtonIsVisible(), "The Quick Nav menu was closed with the close button.");
    	
    	TestReporter.logStep("Reopen the Quick Nav menu");
    	account.clickQuickNav();
    	
    	TestReporter.logStep("Close the Quick Nav menu by clicking somewhere else on the page");
    	account.selectCell(2, 2);
    	
    	TestReporter.assertFalse(account.verifyQuickNavCloseButtonIsVisible(), "The Quick Nav menu was closed by clicking somewhere else on the page");

    }
}