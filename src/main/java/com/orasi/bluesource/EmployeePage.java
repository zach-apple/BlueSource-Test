package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class EmployeePage {
	private OrasiDriver driver = null;
		
	/**Page Elements**/
	@FindBy(xpath="//tr[1]//a[@class='glyphicon glyphicon-pencil']") Button btnEditFirstProject;
	/**Edit Active Project Modal**/
	@FindBy(xpath="//input[@id='filled_role_inherit_start_date']") Checkbox chkInheritStartDate;
	@FindBy(xpath="//input[@id='filled_role_inherit_end_date']") Checkbox chkInheritEndDate;
	@FindBy(xpath="//label[@for='filled_role_comments']//following-sibling::textarea") Textbox txtComments;
	@FindBy(xpath="//input[contains(@class,'filled_role_btn')]") Button btnUpdateFilledRole;
	@FindBy(xpath="//input[@id='filled_role_start_date']") Element eleFilledRoleStartDate;
	@FindBy(xpath="//input[@id='filled_role_end_date']") Element eleFilledRoleEndDate;
		
	/**Constructor**/
	public EmployeePage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public void clickFirstEditProjectButton(){
		btnEditFirstProject.click();
	}
	
	public void checkInheritStartDate() {
		chkInheritStartDate.check();
	}
	
	public void uncheckInheritStartDate() {
		chkInheritStartDate.uncheck();
	}
	
	public void uncheckInheritEndDate() {
		chkInheritEndDate.uncheck();
	}
	
	public void addComments(String strComment) {
		txtComments.set(strComment);
	}
	
	public void addTestComments() {
		addComments("Test Comment");
	}
	
	public void clickUpdateFilledRole(){
		btnUpdateFilledRole.click();
	}
	
	public void setRoleStartDate(String strStartDate) {
		eleFilledRoleStartDate.sendKeys(strStartDate);
	}
	
	public void setRoleEndDate(String strEndDate){
		eleFilledRoleEndDate.sendKeys(strEndDate);
	}
	
	public void editActiveProjectModal(){
		
	}
	
}