package com.orasi.bluesource;

import java.awt.List;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.support.FindBy;

import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Reporting {

	private OrasiDriver driver = null;
	
	/**Page Elements**/
	
	/*Login error message from page*/
	@FindBy(xpath = "//div[@class = 'alert alert-danger alert-dismissable']") private Label lblPageAlert;
	
	/**Reporting drop downs**/
	@FindBy(linkText = "Account Reports") private Link lnkAccountReports;
	@FindBy(linkText = "Employee Reports") private Link lnkEmployeeReports;
	@FindBy(linkText = "Department Reports") private Link lnkDepartmentReports;
	@FindBy(linkText = "Project Reports") private Link lnkProjectReports;
	@FindBy(linkText = "Admin Reports") private Link lnkAdminReports;
	
	/*Links under account reporting*/
	@FindBy(linkText = "Time by Employee") private Link lnkTimeByEmployee;
	@FindBy(linkText = "Time by Time Sheet") private Link lnkTimeByTimeSheet;
	@FindBy(linkText = "Billing by Employee") private Link lnkBillingByEmployee;
	@FindBy(linkText = "Burn Down") private Link lnkBurnDown;
	@FindBy(linkText = "Burn down Data") private Link lnkBurnDownData;
	
	/*Modal elements*/
	@FindBy(xpath = "//div[@class = 'modal-content']") private Element elePopover;
	@FindBy(xpath = "//label[contains(text(), 'Summary')]") private Label lblSummary;
	@FindBy(xpath = "//label[contains(text(), 'Detail')]") private Label lblDetail;
	@FindBy(xpath = "//div[contains(text(), 'Select All')]") private Link lnkSelectAll;
	@FindBy(id = "start_date") private Textbox txtStartDate;
	@FindBy(id = "end_date") private Textbox txtEndDate;
	@FindBy(xpath = "//input[contains(@value, 'Generate Report')]") private Button btnGenReport;
	
	
	public Reporting(OrasiDriver driver)
	{
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/*
	 * Click on the Account Reports drop down
	 * -- Sub links should be present after the click
	 * @author: Daniel Smith
	 */
	public void click_accountReports()
	{
		if (lnkAccountReports.isDisplayed() == true)
		{
			lnkAccountReports.syncVisible();
			lnkAccountReports.click();
		}
	}
	
	/*
	 * Failed login error message from page
	 * @author: Daniel Smith
	 */
	public void check_for_error(String username)
	{
		Boolean pageError = lblPageAlert.isDisplayed();
		
		if(pageError == true)
		{
			System.out.print("[" + "Error stating: " + lblPageAlert.getText().trim() + "] for " + username);
		}
	}
	
	/*
	 * Click on billing by employee 
	 * @author: Daniel Smith
	 */
	public void click_billingByEmployee()
	{
		if(lnkBillingByEmployee.isDisplayed() == true)
		{
			lnkBillingByEmployee.syncVisible();
			lnkBillingByEmployee.click();
		}
	}
	
	/*
	 * Check that the modal is displaying 
	 * @author: Daniel smith
	 */
	public void check_modal()
	{
		Boolean visible = elePopover.isDisplayed();
		
		if(visible = true)
		{
			elePopover.syncVisible();
			TestReporter.assertTrue(visible, "Modal is visible");
		}
		
	}
	
	/*
	 * Fill out Account Billing by Employee Report modal form 
	 * @author: Daniel Smith
	 */
	public void fill_out_billing_by_employee(String startDate, String endDate)
	{
		//random number generate to choose between summary and detail
		Random number;
		number = new Random();
		int ranNumber = number.nextInt(2);
		
		if(ranNumber == 0)
		{
			lblSummary.click();
			System.out.println("Summary radio button has been selected");
		}
		else if (ranNumber == 1)
		{
			lblDetail.click();
			System.out.println("Detail radio button has been selected");
		}
		System.out.println("Random number " + ranNumber);
		
		//select all accounts
		lnkSelectAll.click();
		
		//select a start and end date
		txtStartDate.set(startDate);
		txtEndDate.set(endDate);
		
		//Generate report
		btnGenReport.click();
	}
	
	/*
	 * Click the burn down link under Account Reports
	 * @author: Daniel Smith
	 */
	public void click_accountReports_burndown()
	{
		lnkBurnDown.syncVisible();
		lnkBurnDown.click();
	}
	
	/*
	 * click select all when choosing accounts on modal
	 * @author: Daniel Smith
	 */
	public void click_selectAll_onModal()
	{
		lnkSelectAll.syncVisible();
		lnkSelectAll.click();
	}

	/*
	 * Click the generate report on modal
	 * @author: Daniel Smith
	 */
	public void click_generateReport()
	{
		btnGenReport.syncVisible();
		btnGenReport.click();
	}
	
}
