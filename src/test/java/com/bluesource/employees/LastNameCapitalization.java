package com.bluesource.employees;

	import org.openqa.selenium.NoSuchElementException;
	import org.openqa.selenium.WebDriverException;
	import org.testng.ITestContext;
	import org.testng.annotations.*;

import com.orasi.bluesource.Employees;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.Randomness;
	import com.orasi.utils.Sleeper;
	import com.orasi.utils.TestEnvironment;
	import com.orasi.utils.TestReporter;
	import com.orasi.utils.dataProviders.ExcelDataProvider;
	import com.orasi.utils.date.SimpleDate;
import com.orasi.web.WebBaseTest;

	public class LastNameCapitalization extends WebBaseTest{

		// ************* *
		// Data Provider
		// **************
		@DataProvider(name = "lastName_Data", parallel=true)
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
	        
	    @Test(dataProvider = "lastName_Data")
	    public void verifyProjectCount(String userName, String password) throws InterruptedException{
	    	
	    	int row = 2, column = 1;
	    	String category = "Title", employee = "Yellow";
	    	
	    	try
	    	{
	    		//Login to the bluesource page
		    	LoginPage loginPage = new LoginPage(getDriver());
		    	loginPage.LoginWithCredentials(userName, password);
		    	
		    	//Print what user is signed in
		    	System.out.println("Username is: " + userName);
		    	
		    	//MessageCenter close if it is open
		    	MessageCenter messageCenter = new MessageCenter(getDriver());
		    	Thread.sleep(500);
		    	System.out.println("Is the message center loaded: " + messageCenter.messageCenterIsLoaded());
		    	messageCenter.check_if_messageCenter_Open();
		    	
		    	//verify that the current page is the employees page
		    	Employees employees = new Employees(getDriver());
		    	employees.employees_table_check();
		    	
		    	//Checking if the add button is present
		    	employees.check_add_button();
		    	
		    	//Get the number shown at the right of the page showing the amount of total entries
		    	employees.get_table_total();
		    	
		    	//Get a cell information
		    	employees.cell_information(row, column);
		    	
		    	//Get employee information
		    	employees.getEmployeeInfo(employee, category);
		    	
		    	//Click on the employee name
		    	employees.select_employee(row, column);	    	
	    	}
	    	catch(NoSuchElementException e)
	    	{
	    		System.out.println("Unable to find element. \n" + e.getLocalizedMessage());
	    	}
	    }
}