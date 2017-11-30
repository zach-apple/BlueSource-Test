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
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class industrySort extends WebBaseTest{


	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "accounts_industry", parallel=true)
	public Object[][] scenarios() {
			return new ExcelDataProvider("/testdata/blueSource_Users.xlsx", "Sheet1").getTestData();
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
    
    @Test(dataProvider = "accounts_industry")
    public void sort_industry(String username, String password)
    {
    	try
    	{
	    	//Instantiate objects of the other pages needed
	    	LoginPage loginPage = new LoginPage(getDriver());
	    	MessageCenter messageCenter = new MessageCenter(getDriver());
	    	Header navAccounts = new Header(getDriver());
	    	Accounts accountPage = new Accounts(getDriver());
	    	
	    	//Login to the bluesource page
	    	loginPage.LoginWithCredentials(username, password);
	    	
	    	//Check that messageCenter is open or not / if so close it
	    	messageCenter.check_if_messageCenter_Open();
	    		    	
	    	//Navigate to the accounts page
	    	navAccounts.navigateAccounts();
	    	
	    	//Check for messageCenter again
	    	messageCenter.check_if_messageCenter_Open();
	    	
	    	//Click on the industry sort link
	    	accountPage.sort_by_industry();
    	}
    	catch(NoSuchElementException e)
    	{
    		System.out.println("User: " + username + " does not have account permissions.");
    	}
    }
}