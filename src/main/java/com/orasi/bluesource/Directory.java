package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Directory {

	private OrasiDriver driver = null;
	
	@FindBy(linkText = "Directory") private Link lnkDirectory;
	
	public Directory(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	public void click_directoryLink()
	{
		if (lnkDirectory.isDisplayed() == true)
		{
			lnkDirectory.click();
			System.out.println("Link clicked");
		}
		else
			System.out.println("Unable to find the directory link");
	}
	
}