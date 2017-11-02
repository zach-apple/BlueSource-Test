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

public class accounts_accountName_select extends WebBaseTest{

	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "accounts_NameSelect", parallel=true)
	public Object[][] scenarios() {
		return new ExcelDataProvider("/excelsheets/blueSource_accounts.xlsx", "Sheet1").getTestData();
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
	 * Open browser / navigate to bluesource page
	 * Login to the bluesource page
	 * Click on the accounts tab
	 * Click on the name of an account
	 * Logout of bluesource page
	 */
	
	@Test(dataProvider = "accounts_NameSelect")
		public void accounts_nameSelect_test(String username, String password, String accountName) throws InterruptedException
		{
			//Page objects declares
			LoginPage login = new LoginPage(getDriver());
			Accounts accountpage = new Accounts(getDriver());
			MessageCenter messageCenterPage = new MessageCenter(getDriver());
    	
			try
			{
				//Login to the bluesource page
				login.LoginWithCredentials(username, password);
				
				//Check that the message center is not open upon login
				messageCenterPage.check_if_messageCenter_Open();
				
				//Click on the accounts tab if it is present
				accountpage.click_accounts_tab(username);
				
				//Click on a name of an account
				accountpage.accountName_Select(accountName);
				Thread.sleep(3000);
				
			}catch(NoSuchElementException e)
    	
			{
				System.out.println(username + " does not have account permissions");
			}
    	
		}	
	
	}
