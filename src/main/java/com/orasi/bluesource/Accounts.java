package com.orasi.bluesource;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import javax.lang.model.util.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orasi.utils.Randomness;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
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
	@FindBy(xpath = "//button[@data-target='#modal_3']") private Button btnAssignEmployee;
	@FindBy(xpath = "//div[@id='panel_body_1']//table") private Webtable tblProjects;
	@FindBy(xpath = "//button[@data-target='#modal_1']") private Button btnAddAccount;
	@FindBy(xpath = "//input[@id='account_name']") private Textbox txtAccountName;
	@FindBy(xpath = "//select[@id='account_industry_id']") private Listbox lstIndustry;
	@FindBy(xpath = "//input[@value='Create Account']") private Button btnCreateAccount;
	
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
		if (lnkAccountsTab.isDisplayed() == true)
		{
			System.out.println(username +" has account permissions");
			lnkAccountsTab.click();
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
	
	public void clickAccountLink(String strAccount){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strAccount + "')]";
		Link lnkAccount = driver.findLink(By.xpath(xpathExpression));
		lnkAccount.click();
	}
	
	public void clickProjectLink(String strProject){
		/*String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strProject + "')]";
		Link lnkProject = driver.findLink(By.xpath(xpathExpression));
		lnkProject.syncEnabled(5,true);
		lnkProject.click();*/
		
		// verify project is in project column
		// get project column
		Integer intColumn = 1;
		Integer intRow = tblProjects.getRowWithCellText(strProject, intColumn);
		tblProjects.findElement(By.linkText(strProject)).click();
		
		//tblProjects.clickCell(intRow, intColumn);
	}
	
	public Element verifyProjectLink(String strProject){
		Integer intColumn = 1;
		Integer intRow = tblProjects.getRowWithCellText(strProject, intColumn);
		
		tblProjects.findElement(By.linkText(strProject)).click();
		Element eleProject = tblProjects.findElement(By.linkText(strProject));
		
		return eleProject;
		
	}
	
	public void clickSubprojectLink(String strSubProject){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strSubProject + "')]";
		Link  lnkSubProject = driver.findLink(By.xpath(xpathExpression));
		lnkSubProject.syncEnabled(5,true);
		lnkSubProject.click();
	}
	
	public void clickRoleLink(String strRole){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strRole + "')]";
		Link lnkRole = driver.findLink(By.xpath(xpathExpression));
		lnkRole.syncEnabled(5,true);
		lnkRole.click();
		PageLoaded.isDomComplete(driver, 5);
	}
	
	public void clickAssignEmployee(){
		btnAssignEmployee.syncEnabled(5,true);
		btnAssignEmployee.click();
	}

	public void assignEmployee(String strAccount, String strProject, String strSubProject, String strRole, String strEmployeeName) {
		FilledRoleForm filledRoleForm = new FilledRoleForm(driver);
		
		clickAccountLink(strAccount);
		
		clickProjectLink(strProject);
		
		clickSubprojectLink(strSubProject);
		
		clickRoleLink(strRole);
		
		filledRoleForm.selectEmployee(strEmployeeName);
		
	}
	
	public void clickAddAccount(){
		btnAddAccount.click();
	}
	
	public void setAccountNameTextbox(String strAccountName){
		txtAccountName.set(strAccountName);
	}
	
	public void selectIndustry(String strIndustry){
		lstIndustry.select(strIndustry);
	}
	
	public void clickCreateAccount(){
		btnCreateAccount.click();
	}
	
	public boolean verifyAccountLink(String strAccountName){
		String xpathExpression;
		
		xpathExpression = "//td//a[contains(text(),'" + strAccountName + "')]";
		
		Link lnkAccount = driver.findLink(By.xpath(xpathExpression));
		
		return lnkAccount.isDisplayed();
	}
	
	public String createAccount(){
		clickAddAccount();
		
		PageLoaded.isDomComplete(driver, 5);
		
		String strAccountName = Randomness.randomAlphaNumeric(10);
		
		setAccountNameTextbox(strAccountName);
		
		selectIndustry("Other");
		
		clickCreateAccount();
		
		clickAccountLink(strAccountName);
		
		
		
		return strAccountName;
		
	}
	
}