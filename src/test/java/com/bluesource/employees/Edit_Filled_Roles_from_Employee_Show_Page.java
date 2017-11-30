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
import com.orasi.bluesource.FilledRoleForm;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.utils.Constants;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.utils.date.DateTimeConversion;
import com.orasi.utils.date.SimpleDate;
import com.orasi.web.WebBaseTest;

public class Edit_Filled_Roles_from_Employee_Show_Page extends WebBaseTest{	
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
	 public void Edit_Filled_Roles_from_Employee_Show_Page()
	 {
		 LoginPage loginPage = new LoginPage(getDriver());
		 MessageCenter messageCenter = new MessageCenter(getDriver());
		 Header header = new Header(getDriver());
		 Employees employees = new Employees(getDriver());
		 EmployeePage employeePage = new EmployeePage(getDriver());
		 FilledRoleForm filledRoleForm = new FilledRoleForm(getDriver());
		 
		 //1.Navigate to Bluesource login page -> Bluesource Login page is displayed
		 TestReporter.logStep("Navigate to BlueSource login page");
		 		 
		 //2.Log in as an administrative user who has access to employee accounts (company.admin for example)->The Company.Admin My Information page will display
		 TestReporter.logStep("Administrator Login");
		 loginPage.AdminLogin();
		 
		 messageCenter.closeMessageCenter();
		 
		 //3.Click the Employees link at the top of the window->The Employees page will be displayed
		 TestReporter.logStep("Navigate to Employees page");
		 header.navigateEmployees();
		 
		 //4.Select an employee who has an active project->The selected employee's Show page will be displayed
		 TestReporter.logStep("Select employee with active project");
		 employees.selectProjectEmployee();
		 
		 //5.Click the pencil icon to the right of the active project->An popup window showing the employee's start and end dates for that role will be displayed.
		 TestReporter.logStep("Edit active project");
		 employeePage.clickFirstEditProjectButton();
		 
		 //6.Uncheck the Inherit Start Date checkbox->The box will be unchecked, enabling the Start Date field.
		 TestReporter.logStep("Uncheck Inherit Start Date checkbox");
		 filledRoleForm.uncheckInheritStartDate();
		 
		 //7.Select a different date from the Start Date field.->The selected date will apear in the Start Date field.
		 TestReporter.logStep("Change Start Date");
		 filledRoleForm.setRoleStartDate(DateTimeConversion.getDaysOut("0", "MMddyyyy"));
		 
		 //8.Uncheck the Inherit End Date checkbox->The box will be unchecked, enabling the Start Date field.
		 TestReporter.logStep("Uncheck Inherit End Date checkbox");
		 filledRoleForm.uncheckInheritEndDate();
		 
		 //9.Select a different date from the End Date field.->The selected date will appear in the End Date field.
		 TestReporter.logStep("Change End Date");
		 filledRoleForm.setRoleEndDate(DateTimeConversion.getDaysOut("7", "MMddyyyy"));
		 
		 //10.Add a comment in the Comments field->The comment will be added to the field.
		 TestReporter.logStep("Add Comments");
		 filledRoleForm.addTestComments();
		 
		 //11.Click the Update Filled Role button->The green "Filled Role Updated" label will appear at the top of the selected employee's show page.
		 TestReporter.logStep("Click Update Filled Role Button");
		 filledRoleForm.clickUpdateFilledRole();
		 
		 //12.Verify that the new start and end dates show on the employee's show page.->The new start and end dates will be displayed.
		 TestReporter.logStep("Verify new start/end dates");
		 
	} 
}