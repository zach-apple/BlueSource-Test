package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class EmployeePage {
	private OrasiDriver driver = null;
		
	/**Page Elements**/
	@FindBy(xpath="//tr[1]//a[@class='glyphicon glyphicon-pencil']") Button btnEditFirstProject;
	/**Edit Active Project Modal**/
	@FindBy(xpath="//input[@id,'filled_role_inherit_start_date']") Checkbox chkInheritStartDate;
	@FindBy(xpath="//input[@id,'filled_role_inherit_end_date']") Checkbox chkInheritEndDate;
	@FindBy(xpath="//label[@for='filled_role_comments']//following-sibling::textarea") Textbox txtComments;
	@FindBy(xpath="//input[contains(@class,'filled_role_btn')]") Button btnUpdateFilledRole;
	
		
	/**Constructor**/
	public EmployeePage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public void clickFirstEditProjectButton(){
		btnEditFirstProject.click();
	}
	
	public void editActiveProjectModal(){
		
	}
	
}