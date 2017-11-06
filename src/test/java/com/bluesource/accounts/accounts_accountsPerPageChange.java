package com.bluesource.accounts;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class accounts_accountsPerPageChange extends WebBaseTest{

	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "accounts_perPageChange", parallel=true)
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
	     * Open browser and navigate to bluesource page
	     * Login into the bluesource page
	     * Click the account tab located in the header
	     * Change account per page to dropdown to desired amount
	     * author: Daniel Smith
	     */
	    
	    @Test(dataProvider = "accounts_perPageChange")
	    public void accounts_accounts_perPage_change(String username, String password) throws InterruptedException
	    {
	    	//Instantiate objects of the other pages needed
	    	LoginPage loginPage = new LoginPage(getDriver());
	    	MessageCenter messageCenter = new MessageCenter(getDriver());
	    	Header navAccounts = new Header(getDriver());
	    	Accounts accountPage = new Accounts(getDriver());
	    	
	    	try
	    	{
	    		//login to the bluesource page
	    		loginPage.LoginWithCredentials(username, password);
	    		
	    		//Check that the messageCenter popover is not present on login
	    		messageCenter.check_if_messageCenter_Open();
	    		
	    		//Click the accounts tab
	    		accountPage.click_accounts_tab(username);
	    		
	    		//Change the number of accounts showing
	    		accountPage.select_accountPerPage();
	    		
	    	}catch(NoSuchElementException e)
	    	{
	    		System.out.println(username + " User does not have accounts permissions!");
	    	}
	    }
}
