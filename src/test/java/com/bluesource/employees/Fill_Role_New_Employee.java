/*
 * Test for logging into the blueSource page.
 * @author: Daniel Smith
 */

package com.bluesource.employees;

import com.orasi.utils.dataHelpers.personFactory.*;
import com.orasi.utils.date.DateTimeConversion;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.bluesource.accounts_pageLoad;
import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Employees;
import com.orasi.bluesource.FilledRoleForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.bluesource.ProjectEmployees;
import com.orasi.bluesource.ReportedTimesSummary;
import com.orasi.web.WebBaseTest;

public class Fill_Role_New_Employee extends WebBaseTest{	
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
			testStart("Fill_Role_New_Employee");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	
	 @Test//(dataProvider = "login")
	 public void Fill_Role_New_Employee()
	 {
		 LoginPage loginPage = new LoginPage(getDriver());
		 MessageCenter messageCenter = new MessageCenter(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 ReportedTimesSummary reportedTimesSummary = new ReportedTimesSummary(getDriver());
		 ProjectEmployees projectEmployees = new ProjectEmployees(getDriver());
		 Accounts accounts = new Accounts(getDriver());
		 FilledRoleForm filledRoleForm = new FilledRoleForm(getDriver());
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
		 String strStartDate = DateTimeConversion.getDaysOut("1", "MM/dd/yyyy");
		 String strAccount = "Account1";
		 String strProject = "Project2";
		 String strSubProject = "SubProject3";
		 String strRole = "Role2";
		 				 
		 employees.CreateBasicUser(strUserName, strFirstName, strLastName);
		 
		 //4.  Go to the Accounts tab and and assign employee to a project with user start date in the future but less than expected end date for the project
		 header.navigateAccounts();
		  
		 accounts.clickAccountLink(strAccount);
			
		 accounts.clickProjectLink(strProject);
			
		 accounts.clickSubprojectLink(strSubProject);
			
		 accounts.clickRoleLink(strRole);
		 
		 accounts.clickAssignEmployee();
		 
		 filledRoleForm.selectEmployee(person.getFullName());
		 
		 //filledRoleForm.uncheckInheritStartDate();
		 		 
		 //filledRoleForm.setRoleStartDate(strStartDate);
		 
		 filledRoleForm.clickUpdateFilledRole();
		 
		 //5.  Under Employee tab, go to your user profile and verify employee project info is updated and shows start and end dates
		 header.navigateEmployees();
		 
		 employees.employeeSearch(strFullName);
		 
		 employees.selectEmployeeByName(strFullName);
		 
		 employeePage.verifyProjectAssign(strSubProject);
		 
		 employeePage.verifyStartDate(strStartDate,strSubProject);
		 
	} 
}