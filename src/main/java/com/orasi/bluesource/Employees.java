package com.orasi.bluesource;

import java.util.Collection;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Employees {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(xpath = "//*[@id='all-content']/div[3]/div/div[2]/button") private Button btnAdd;
	@FindBy(name = "commit") private Button btnCreateEmployee;
	@FindBy(id = "employee_username") private Textbox txtEmployeeUsername;
	@FindBy(id = "employee_first_name") private Textbox txtEmployeeFirst;
	@FindBy(id = "employee_last_name") private Textbox txtEmployeeLast;
	@FindBy(xpath = "//*[@id='resource-content']/div[1]/table") private Webtable tblEmployees;
	@FindBy(tagName = "p") private Label lblAmountInTable;
	@FindBy(xpath = "//*[@id='accordion']/div/div[3]/h4/a") private Button btnManage;
		
	/**Constructor**/
	public Employees(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	/**
	 * This method clicks the Add Employees page
	 * @author Paul
	 */
	public void clickAddEmployee() {
		btnAdd.click();		
	}
	
	/**
	 * This method completes the Add Employee modal
	 * @author Paul
	 * @param lastname 
	 * @param firstname 
	 * @param username 
	 */
	public void addEmployeeModal(String username, String firstname, String lastname) {
		
		//complete text fields
		completeRequiredFields(username,firstname,lastname);
		
		//click Create Employee
		createEmployee();
	}
	
	/**
	 * This method completes only the required fields to create an employee
	 * @author Paul
	 */
	private void completeRequiredFields(String username,String firstname,String lastname) {
		txtEmployeeUsername.set(username);
		txtEmployeeFirst.set(firstname);
		txtEmployeeLast.set(lastname);
	}

	/**
	 * This method clicks the Create Employee button
	 * @author Paul
	 */
	public void createEmployee() {
		btnCreateEmployee.click();
	}
	
	/*
	 * Get the number of rows that the table has
	 * author: Daniel Smith
	 */
	public int tableCount(int rows, int columns)
	{
		rows = tblEmployees.getRowCount();
		columns = tblEmployees.getColumnCount(1);
		
		System.out.println("Number of rows: " + rows);
		System.out.println("Number of columns: " + columns);
		
		return tableCount(rows, columns);
	}
	
	/**
	 * This method returns the values found in the row containing the employee's username
	 * @author Paul
	 */
	public String getEmployeeInfo(String username,String category) {
		int row = tblEmployees.getRowWithCellText(username);
		int column = tblEmployees.getColumnWithCellText(category);
		
		System.out.println("Row with " + username + " is: " + row);
		System.out.println("Column with " + category + " is: " + column);
		
		return tblEmployees.getCellData(row, column);
	}
	
	/*
	 * Check that the employees table is present
	 * @author: Daniel Smith
	 */
	public boolean employees_table_check() throws InterruptedException
	{
		//hard wait for the page to load 
		Sleeper.sleep(1000);
		
		boolean empTable = tblEmployees.isDisplayed();
		
		//Check that the employees table is present
		if(empTable == true)
		{
			TestReporter.assertEquals(empTable, true, "Table is present");
		}
		else if(empTable == false)
		{
			System.out.println("User is not logged in.. ");
		}
		else
		{
			System.out.println("User is not found or invaild entry");
		}
		return empTable;
	}
	
	/*
	 * Get the number located at the bottom of the table 
	 * showing the total amount of entries
	 * @author: Daniel Smith
	 */
	public void get_table_total()
	{
		System.out.println("Number of entries: " + lblAmountInTable.getText());
	}
	
	/*
	 * Get the information of a certain cell
	 * @author: Daniel Smith
	 */
	public String cell_information(int get_row, int get_column)
	{
		String cell_information = tblEmployees.getCellData(get_row, get_column);
		
		System.out.println("Data in the cell: " + cell_information);
		
		return cell_information;
	}
	
	/*
	 * Select an employee 
	 * @author: Daniel Smith
	 */
	public void select_employee(int row, int column)
	{
		String message = "On the employee information page.";
		String failMessage = "Button 'Manage' is not found\n";
				
		tblEmployees.clickCell(row, column);
		
		if(btnManage.isDisplayed() == true)
		{
			TestReporter.assertTrue(btnManage.isDisplayed(), message);
		}
		else
		{
			TestReporter.assertFalse(btnManage.isDisplayed(), failMessage);
		}
	}
	
	/*
	 * Checks that the add button is present on the current screen
	 * author: Daniel Smith
	 */
	public void check_add_button()
	{
		if (btnAdd.isDisplayed() == true)
		{
			System.out.println("Add button is present. You can add an employee");
		}
		else if (btnAdd.isDisplayed() == false)
		{
			System.out.println("Add button is not seen on this page.");
		}
		else
			System.out.println("Error finding the add button on the current page.");
	}
	
}