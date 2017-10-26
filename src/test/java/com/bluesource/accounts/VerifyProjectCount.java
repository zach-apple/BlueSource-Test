package com.bluesource.accounts;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.Randomness;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.utils.date.SimpleDate;
import com.orasi.web.WebBaseTest;

public class VerifyProjectCount extends WebBaseTest{

	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "verifyProject", parallel=true)
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
        
    @Test(dataProvider = "verifyProject")
    public void verifyProjectCount(String userName, String password) throws InterruptedException{
    	//Login to BlueSource
    	LoginPage loginPage = new LoginPage(getDriver());
    	loginPage.LoginWithCredentials(userName, password);

    	//Close Message Center
    	MessageCenter messageCenter = new MessageCenter(getDriver());
    	messageCenter.check_if_messageCenter_Open();
    		  	    	
    	//Navigate to accounts page
    	try
    	{
	    	Header header = new Header(getDriver());
	    	header.navigateAccounts();
	    	
	    	//Give a hard wait for 1 sec
	    	Thread.sleep(1000);
	    	
	    	//Display the name of the user 
	    	System.out.println("Username is: " + userName);
	    	
	    	//Compare number of accounts displayed to number of accounts 
	    	Accounts accounts = new Accounts(getDriver());
	    
	    	//Change the number of accounts per page to 100
	    	accounts.accountsPerPage();
	    	
	    	//get number of rows
	    	int numberOfRows = accounts.getAccountsTableRows();
	    	    	
	    	//get number of accounts reported
	    	int numberOfAccounts = accounts.getNumberOfAccounts();
	    	
	    	//Print to console the number of rows / columns
	    	System.out.println("number of rows: " + numberOfRows);
	    	System.out.println("number of Accounts: " + numberOfAccounts);
	    	
	    	//Reporter that checks that the number of rows is equal to the number of accounts
	    	if (numberOfRows == numberOfAccounts)
	    	{
	    		TestReporter.assertEquals(numberOfRows, numberOfAccounts, "Number of rows and accounts match");
	    	}
	    	else
	    	{
	    		TestReporter.assertNotEquals(numberOfRows, numberOfAccounts, "Accounts number does not match the number of rows");
	    	}
	    }
    	catch(NoSuchElementException e)
    	{
   			System.out.println("User: " + userName + " does not have account viewing permissions");
   		}
    	catch(WebDriverException e)
    	{
    		e.getMessage();
    		System.out.println("WebDriver exception error.  \n" + e.getLocalizedMessage());
    	}
    }
}