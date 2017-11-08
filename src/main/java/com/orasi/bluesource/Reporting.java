package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Reporting {

	private OrasiDriver driver = null;
	
	/**Page Elements**/
	
	
	public Reporting(OrasiDriver driver)
	{
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
}
