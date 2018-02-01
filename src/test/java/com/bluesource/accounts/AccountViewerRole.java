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
import com.orasi.utils.dataHelpers.personFactory.Person;
import com.orasi.web.WebBaseTest;

public class AccountViewerRole extends WebBaseTest {
	
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
    public void accountViewerRole() {
    	Person person = new Person();
    	Header header = new Header(getDriver());
    	Accounts accounts = new Accounts(getDriver());
    	LoginPage loginPage = new LoginPage(getDriver());
    	Employees employees = new Employees(getDriver());
    	
    	TestReporter.logStep("Test started");
    	
    	TestReporter.logStep("Login to application");
    	loginPage.AdminLogin();
    	
    	TestReporter.logStep("Go to the Employees page to add a new employee");
    	header.navigateEmployees();
    	employees.clickAddEmployee();
    	
    	TestReporter.logStep("Test that Account Viewer is an option in the Account Permission dropdown");
    	TestReporter.assertTrue(employees.checkAccountPermissionOption("Account Viewer"), "Checked that Account Viewer is an option and selects it.");

    	TestReporter.logStep("Add a new employee using the Person class");
    	employees.addEmployeeModal(person);
    	header.navigateLogout();
    	
    	TestReporter.logStep("Login as the new employee");
    	loginPage.LoginWithCredentials(person.getUsername(), person.getPassword());
    	
    	TestReporter.logStep("Go to the Accounts page");
    	header.navigateAccounts();
    	
    	TestReporter.logStep("Test that the add account button is not present");
    	TestReporter.assertFalse(accounts.verifyAddButtonIsVisible(), "Checked that the Add Account button is not present.");
    	
    	TestReporter.logStep("Go to an account");
    	accounts.clickFirstAccountLink();
    	
    	TestReporter.logStep("Test that the edit account button is not present");
    	TestReporter.assertFalse(accounts.verifyEditButtonIsVisible(), "Checked that the Edit Account button is not present.");
    	
    }

}
