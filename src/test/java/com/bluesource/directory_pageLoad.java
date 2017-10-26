package com.bluesource;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Directory;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.TestEnvironment;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class directory_pageLoad extends WebBaseTest{


	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "directory_pageLoad", parallel=true)
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
    
    @Test(dataProvider = "directory_pageLoad")
    public void directory_loadPage_test(String username, String password) throws InterruptedException
    {
    	String expected, actual;
    	
    	expected = "http://10.238.243.127/directory";
    	
    	LoginPage login = new LoginPage(getDriver());
    	MessageCenter messageCenter = new MessageCenter(getDriver());
    	Directory directory = new Directory(getDriver());
    	
    	login.LoginWithCredentials(username, password);
    	messageCenter.check_if_messageCenter_Open();
    	directory.click_directoryLink();
    
    	Thread.sleep(1000);
    	actual = getDriver().getCurrentUrl();
    	TestReporter.assertEquals(expected, actual, "On the directory page");
    }
	
}