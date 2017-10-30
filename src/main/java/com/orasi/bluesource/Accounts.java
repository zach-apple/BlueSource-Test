package com.orasi.bluesource;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import javax.lang.model.util.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;



public class Accounts {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(xpath = "//*[@id='resource-content']/div[2]/p") private Element elmNumberPages;
	@FindBy(tagName = "tbody") private Webtable tblAccounts;
	@FindBy(id = "preference_resources_per_page") private Listbox lstAccountPerPage;
	@FindBy(linkText = "Industry") private Link lnkIndustry;
	@FindBy(linkText = "Accounts") private Link lnkAccountsTab;
	@FindBy(xpath = "//input[@id = 'search-bar']") private Element srhBar;
	
	/**Constructor**/
	public Accounts(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/*
	 * Click on accounts tab 
	 * Make sure that the correct page loads
	 * author: Daniel Smith
	 */
	public void click_accounts_tab(String username)
	{
		try
		{
			if (lnkAccountsTab.isDisplayed() == true)
			{
				System.out.println(username +" has account permissions");
				lnkAccountsTab.click();
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("User does not have account permissions");
		}
		
	}
	
	
	/**
	 * This method gets the number of rows on the Accounts page
	 * @author Paul
	 */
	public int getAccountsTableRows() {
		int rowCount = 0;
		try
		{
			rowCount = tblAccounts.getRowCount();
		}
		catch(NullPointerException e)
		{
			System.out.println("Null pointer exception, no accounts found  \n" + e.getLocalizedMessage());
		}
		return (rowCount - 1);
	}

	/**
	 * This method gets the number of Accounts reported by the page
	 * @author Paul
	 * @return 
	 */
	public Integer getNumberOfAccounts() {
		String str;
		String delims;
		String[] tokens = null;
		Integer lastItem = null;
		
		try
		{
			tblAccounts.syncEnabled(3);
			str = elmNumberPages.getText();
			delims = "[ ]";
			tokens = str.split(delims);
			lastItem = tokens.length;
			return Integer.valueOf(tokens[lastItem-1]);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("No such exception, no accounts found \n" + e.getLocalizedMessage());
		}
		return lastItem;
		
	}
	
	/*
	 * Change the number showing for accounts per page to 100
	 * author: Daniel Smith
	 */
	public void accountsPerPage()
	{
		try
		{
			lstAccountPerPage.selectValue("100");
		}
		catch(NoSuchElementException e)
		{
			System.out.println("No such element found.  \n" + e.getLocalizedMessage());
		}
		catch(UndeclaredThrowableException e)
		{
			System.out.println("Undeclared throwable exception  \n" + e.getLocalizedMessage());
		}
		
	}
	
	/*
	 * Sort accounts table by industry
	 * @author: Daniel Smith
	 */
	public void sort_by_industry()
	{
		if(lnkIndustry.isDisplayed() == true)
		{
			System.out.println("Link 'Industry' is present");
			lnkIndustry.click();
		}
		else
			System.out.println("Link 'Industry' not on current page");
	}
	
	/*
	 * Perform a searh for an account
	 * author: Daniel Smith
	 */
	public void search_for_account(String accountName)
	{		
		if (srhBar.isDisplayed() == true)
		{
			srhBar.click();
			srhBar.sendKeys(accountName);
		}
		else
			System.out.println("srhBar is not found. ");
	
	}
	
}