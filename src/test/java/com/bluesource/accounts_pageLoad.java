package com.bluesource;

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
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class accounts_pageLoad extends WebBaseTest{

	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "accounts_pageLoad", parallel=true)
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
	
    @Test(dataProvider = "accounts_pageLoad")
    public void accounts_pageLoad_test(String username, String password)
    {
    	LoginPage loginpage = new LoginPage(getDriver());
    	Accounts accountspage = new Accounts(getDriver());
    	MessageCenter messageCenterpage = new MessageCenter(getDriver());
    	
    	try
    	{
	    	loginpage.LoginWithCredentials(username, password);
	    	messageCenterpage.check_if_messageCenter_Open();
	    	
	    	accountspage.click_accounts_tab(username);
    	}
    	catch(NoSuchElementException e)
    	{
    		System.out.println("User: " + username + " does not have account permissions");
    	}
    }
}