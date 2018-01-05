package com.bluesource.accounts;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.PageLoaded;
import com.orasi.web.WebBaseTest;

public class AccountViewerRole extends WebBaseTest {
	
	// ************* *
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
    public void accountViewerRole() {
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Employees employees = new Employees(getDriver());
    	Accounts accounts = new Accounts(getDriver());

    	loginPage.AdminLogin();
    	header.navigateEmployees();
    	PageLoaded.isDomComplete(getDriver(), 5);
    	employees.clickAddEmployee();
    	TestReporter.assertTrue(employees.checkAccountPermissionOption("Account Viewer"), "Account Viewer is an option");

    	String first = "Jane";
    	String last = "Doe";
    	String user = first + last;
    	String password = "123";
    	
    	employees.addEmployeeModal(user, first, last);
    	employees.clickLogout();
    	loginPage.LoginWithCredentials(user, password);
    	header.navigateAccounts();
    	TestReporter.assertFalse(accounts.verifyAddButtonIsVisible(), "Add Account button is not present");
    	
    	accounts.clickAccountLink("Account1");
    	TestReporter.assertFalse(accounts.verifyEditButtonIsVisible(), "Edit Account button is not present");
    	
    }

}
