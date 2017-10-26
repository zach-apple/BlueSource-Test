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
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class accounts_search extends WebBaseTest{

	// ************* *
		// Data Provider
		// **************
		@DataProvider(name = "accounts_search", parallel=true)
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
	    
	    @Test(dataProvider = "accounts_search")
	    public void search_for_account_test(String username, String password) throws InterruptedException
	    {
	    	//Create page objects for use in the test
	    	LoginPage loginPage = new LoginPage(getDriver());
	    	MessageCenter messageCenter = new MessageCenter(getDriver());
	    	Accounts accountsPage = new Accounts(getDriver());
	    	
	    	//Login to the bluesource page
	    	loginPage.LoginWithCredentials(username, password);
	    	
	    	//Check to see if message center is present upon login
	    	
	    	messageCenter.check_if_messageCenter_Open();
	    	
	    	//Click on the accounts tab if it is present
	    	accountsPage.click_accounts_tab(username);
	    }
	
}
