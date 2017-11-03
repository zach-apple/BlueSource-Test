/*
 * Test for logging into the blueSource page.
 * @author: Daniel Smith
 */

package com.bluesource.employees;

import java.util.ResourceBundle;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.bluesource.ProjectEmployees;
import com.orasi.bluesource.ReportedTimesSummary;
import com.orasi.utils.Constants;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.utils.date.DateTimeConversion;
import com.orasi.utils.date.SimpleDate;
import com.orasi.web.WebBaseTest;

public class Employees_Project_Employees_Page_Back_Button extends WebBaseTest{	
	// ************* *
	// Data Provider
	// **************
	/*@DataProvider(name = "login", parallel=true)
	public Object[][] scenarios() {
	return new ExcelDataProvider("/testdata/blueSource_Users.xlsx", "Sheet1").getTestData();
	}*/
			
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
			testStart("Edit_Filled_Roles_from_Employee_Show_Page");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test//(dataProvider = "login")
	 public void Employees_Project_Employees_Page_Back_Button()
	 {
		 LoginPage loginPage = new LoginPage(getDriver());
		 MessageCenter messageCenter = new MessageCenter(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 ReportedTimesSummary reportedTimesSummary = new ReportedTimesSummary(getDriver());
		 ProjectEmployees projectEmployees = new ProjectEmployees(getDriver());
		 
		 //1.Navigate to Bluesource login page -> Bluesource Login page is displayed
		 TestReporter.logStep("Navigate to BlueSource login page");
		 		 
		 //2.Login as a user who has 'Project Approval' or Project Admin' access on a Project.
		 TestReporter.logStep("Account Admin Login");
		 loginPage.LoginWithCredentials("department1.admin", "asd");
		 
		 messageCenter.closeMessageCenter();
		 
		 //3.Click the Employees link at the top of the window->The Employees page will be displayed
		 TestReporter.logStep("Navigate to Project Employees page");
		 header.navigateProjectEmployees();
		 
		 //4.Click on the name of an Employee assigned to the Project.
		 TestReporter.logStep("Select employee assigned to the Project");
		 employees.selectEmployeeByName("Department4a M2E2");
		 
		 //5.Click the 'Back' button.
		 TestReporter.logStep("Click the Back button");
		 reportedTimesSummary.clickBackbutton();
		 
		 //6.Verify the user is navigated back to the Project Employees page.
		 TestReporter.logStep("Verify the user is taken back to the Project Employees page");
		 projectEmployees.VerifyProjectEmployeesPage();
		 
		 
	} 
}