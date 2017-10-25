package com.bluesource.messagecenter;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class messageCenterOpen_test extends WebBaseTest{
	

	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "messageCenter_data", parallel=true)
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
    
    @Test(dataProvider = "messageCenter_data")
    public void messageCenter_Test(String username, String password)
    {
    	LoginPage login = new LoginPage(getDriver());
    	MessageCenter messageCenter = new MessageCenter(getDriver());
    	
    	login.LoginWithCredentials(username, password);
    	messageCenter.check_if_messageCenter_Open();
    	System.out.println("Current user: " + username);
    	messageCenter.messageCount();
    	
    	try
    	{
        	messageCenter.messageCenter_linkClick_withAdminTab();   	
    	}
    	catch(NoSuchElementException e)
    	{
    		messageCenter.messageCenter_linkClick_NoAdminTab();
    	}
    	
    	//view the message center full screen
    	messageCenter.view_full_btnClick();
    	
    	try
    	{
    		//Message center full header print
        	messageCenter.getText_viewFull();        	
    	}
    	catch(NoSuchElementException e)
    	{
    		//Message center table header print
    		messageCenter.getText_tableHead();
    	}
    	
    	try
    	{
    		//Display the table row count
    		messageCenter.getTableCount();
    	}
    	catch(NoSuchElementException e)
    	{
    		System.out.println("No table is present. ");
    	}
    
    }

}