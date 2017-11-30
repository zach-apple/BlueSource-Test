/*
 * Test for logging into the blueSource page.
 * @author: Daniel Smith
 */

package com.bluesource.employees;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Employees;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataHelpers.personFactory.Person;
import com.orasi.web.WebBaseTest;

public class Create_Basic_User extends WebBaseTest{	
	// ************* *
	// Data Provider
	// **************
				
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
			testStart("Create_Basic_Employee");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test//(dataProvider = "login")
	 public void Create_Basic_Employee()
	 {
		 LoginPage loginPage = new LoginPage(getDriver());
		 MessageCenter messageCenter = new MessageCenter(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 Person person = new Person();
		 
		 //1.  Open web browser and navigate to http://10.238.242.236/ to access BlueSource login page
		 //2.  Login to BlueSource with company.admin user
		 loginPage.AdminLogin();
		 
		 messageCenter.closeMessageCenter();
		 
		 //3.  Go to Employees tab and create new user
		 header.navigateEmployees();
		 
		 String strFirstName = person.getFirstName();
		 String strLastName = person.getLastName();
		 String strUserName = strFirstName + "." + strLastName;
		 String strFullName = person.getFullName();
		 		 				 
		 employees.CreateBasicUser(strUserName, strFirstName, strLastName);
		 
		 employees.employeeSearch(strFullName);
		 
		 TestReporter.assertTrue(employees.verifyEmployeeByName(strLastName), "Ensure that Employee is found");
		 
	} 
}