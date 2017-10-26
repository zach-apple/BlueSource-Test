package com.orasi.bluesource;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.LabelImpl;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import com.orasi.utils.TestReporter;

public class Admin {
	
	private OrasiDriver driver = null;
	
	/* Page elements */
	@FindBy(linkText = "Admin") private Link lnkAdmin;
	@FindBy(linkText = "Departments") private Link lnkDepartments;
	@FindBy(linkText = "Titles") private Link lnkTitles;
	@FindBy(linkText = "Industries") private Link lnkIndustries;
	@FindBy(linkText = "Role Types") private Link lnkRoleTypes;
	@FindBy(linkText = "Company Holidays") private Link lnkCompanyHolidays;
	@FindBy(linkText = "Timesheet Locks") private Link lnkTimesheetLocks;
	@FindBy(linkText = "Email BlueSource Users") private Link lnkEmailUsers;
	@FindBy(tagName = "table") private Webtable tblTitles;
	@FindBy(tagName = "h1") private Label lblTitleHeader;
	
	/**Constructor**/
	public Admin(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/*******************************************************************
	 *  Page interactions
	 *******************************************************************/
	
	
	/*
	 * Check to make sure that the admin tab is displayed
	 * If the user does not have admin rights this tab will not be present
	 * author: Daniel Smith
	 */
	public void click_admin_tab(String username)
	{
		if (lnkAdmin.isDisplayed() == true)
		{
			System.out.println("Admin link found for: " + username + " in the nav bar. ");
			TestReporter.softAssertTrue(lnkAdmin.isDisplayed(), "Reporter pass.");
			lnkAdmin.click();
		}
		else
			System.out.println("Admin link not found. " + username + " User doesn't have admin rights");
	}
	
	
	/*
	 * Check the admin link again to hide sub categories 
	 * author: Daniel Smith
	 */
	public void click_admin_hide_subCats()
	{
		if(lnkDepartments.isDisplayed() == true || lnkTitles.isDisplayed() == true || lnkIndustries.isDisplayed() == true)
		{
			System.out.println("Subcategories are being hide.");
			lnkAdmin.click();
		}
	}
	
	/*
	 * validation of the subcategories, under admin tab
	 * author: Daniel Smith
	 */
	public void validate_subCats()
	{
		if(lnkDepartments.isDisplayed() == true)
		{
			if(lnkTitles.isDisplayed() == true)
			{
				if(lnkIndustries.isDisplayed() == true)
				{
					if(lnkRoleTypes.isDisplayed() == true)
					{
						if(lnkCompanyHolidays.isDisplayed() == true)
						{
							if(lnkTimesheetLocks.isDisplayed() == true)
							{
								if(lnkEmailUsers.isDisplayed() == true)
								{
									System.out.println("All links are present!");
									TestReporter.assertTrue(true, "All links displayed, reporter pass");
								}
							}
						}
					}
				}
			}	
		}
	}
	/*
	 * titles validation
	 * author: Daniel Smith
	 */
	public void click_titlesTab()
	{
		if(lnkTitles.isDisplayed() == true)
		{
			System.out.println("Titles link found and being clicked");
			lnkTitles.click();
		}
	}
	
	/*
	 * Checking the the titles page has a table present
	 * author: Daniel Smith
	 */
	public void titles_page_validation()
	{
		int size;
		
		if(lblTitleHeader.isDisplayed() == true)
		{
			System.out.println("On title page");
			
			size = tblTitles.getRowCount();
						
			System.out.println("Number of entries in the table: " + size);
		}
	}
	
	
	
}
