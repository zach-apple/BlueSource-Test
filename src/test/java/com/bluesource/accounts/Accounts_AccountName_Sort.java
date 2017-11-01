package com.bluesource.accounts;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;


public class Accounts_AccountName_Sort extends WebBaseTest{

	
		// ************* *
			// Data Provider
			// **************
			@DataProvider(name = "accounts_NameSort", parallel=true)
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
		     * Open Browser 
		     * Navigate to bluesource page
		     * login to the bluesource page
		     * click the accounts tab (if the user has account permissions
		     * click in the search bar and enter information
		     * logout and close the browser
		     */
		    
		    @Test(dataProvider = "accounts_NameSort")
		    public void accounts_sort_name(String username, String password) throws InterruptedException
		    {
		    	LoginPage login = new LoginPage(getDriver());
		    	Accounts accountpage = new Accounts(getDriver());
		    	MessageCenter messageCenterPage = new MessageCenter(getDriver());
		    	
		    	try
		    	{
		    		login.LoginWithCredentials(username, password);
					messageCenterPage.check_if_messageCenter_Open();
					accountpage.click_accounts_tab(username);
					
					accountpage.sort_by_accountName();
		    	}catch(NoSuchElementException e)
		    	{
		    		System.out.println(username + " does not have account permissions");
		    	}
		      	
		    }
}
