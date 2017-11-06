package com.orasi.bluesource;

import org.bouncycastle.asn1.mozilla.PublicKeyAndChallenge;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class FilledRoleForm {
	private OrasiDriver driver = null;
		
	/**Page Elements**/
	@FindBy(xpath="//input[@id='filled_role_inherit_start_date']") private Checkbox chkInheritStartDate;
	@FindBy(xpath="//input[@id='filled_role_inherit_end_date']") private Checkbox chkInheritEndDate;
	@FindBy(xpath="//label[@for='filled_role_comments']//following-sibling::textarea") private Textbox txtComments;
	@FindBy(xpath="//input[contains(@class,'filled_role_btn')]") private Button btnFilledRole;
	@FindBy(xpath="//input[@id='filled_role_start_date']") private Element eleFilledRoleStartDate;
	@FindBy(xpath="//input[@id='filled_role_end_date']") private Element eleFilledRoleEndDate;
	@FindBy(xpath="//span[@id='select2-filled_role_employee_id-container']") private Element eleSelectEmployee;
	@FindBy(xpath="//input[@class='select2-search__field']") private Textbox txtEmployeeSearch;
		
	/**Constructor**/
	public FilledRoleForm(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/	
	public void setEmployeeSearch(String strText){
		txtEmployeeSearch.syncEnabled(5,true);
		txtEmployeeSearch.set(strText);
	}
	public void clickSelectEmployee(){
		eleSelectEmployee.syncEnabled(5,true);
		eleSelectEmployee.click();
	}
	
	public void checkInheritStartDate() {
		chkInheritStartDate.check();
	}
	
	public void uncheckInheritStartDate() {
		chkInheritStartDate.syncEnabled(5,true);
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
		btnFilledRole.click();
	}
	
	public void clickCreateFilledRole(){
		btnFilledRole.click();
	}
	
	public void setRoleStartDate(String strStartDate) {
		eleFilledRoleStartDate.sendKeys(strStartDate);
	}
	
	public void setRoleEndDate(String strEndDate){
		eleFilledRoleEndDate.sendKeys(strEndDate);
	}
	
	public void editActiveProjectModal(){
		
	}

	/**Business Flow**/	
	public void selectEmployee(String strEmployee){
		clickSelectEmployee();
		setEmployeeSearch(strEmployee);
		txtEmployeeSearch.sendKeys(Keys.ENTER);
		PageLoaded.isDomComplete(driver,5);
		
	}
}