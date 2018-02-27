package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class Show_Rate_on_Project_Show_Page_Verify_Current_Rate extends WebBaseTest {
	@BeforeMethod
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion",
			"operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest,
					  String browserVersion, String operatingSystem, String environment) {
		setApplicationUnderTest("BLUESOURCE");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setEnvironment(environment);
		setThreadDriver(true);
		testStart("Show_Rate_on_Project_Show_Page_Verify_Current_Rate");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test
	public void Show_Rate_on_Project_Show_Page_Verify_Current_Rate()
	{
		/*
		1. Open Browser
		2. Navigate to http://10.238.243.127
		3. Enter Login info
		4. click login button
		5. click on an account name - account info page appears
		6. click on a project name - project info page appears
		7. go to roles section - roles table is showing
		8. click on role name - project roles page appears
		9. go to rates section - rates is showing table in table header
		10. verify current rate is displayed
		11. logout
		 */

		String strAccount = "Account1";
		String strProject = "Project2";
		String strRole = "Role3";
		String roleRateFromProjectPage = "";

		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());

		TestReporter.logStep("Login to application as Account Viewer");
		loginPage.Login("ACCOUNT_VIEWER");

		TestReporter.logStep("Go to the Accounts page");
		header.navigateAccounts();

		TestReporter.logStep("Go to Accounts page for [" + strAccount + "]");
		accounts.clickAccountLink(strAccount);

		TestReporter.logStep("Go to [" + strProject + "] in [" + strAccount + "] page");
		accounts.clickProjectLink(strProject);

		TestReporter.logStep("Get and store [" + strRole + "] rate");
		roleRateFromProjectPage = accounts.getRoleRateFromProjectPage(strRole);

		TestReporter.logStep("Go to Role [" + strRole + "] page");
		accounts.clickRoleLink(strRole);

		TestReporter.assertTrue(accounts.verifyRoleRate(roleRateFromProjectPage),
				"Verify Role rate from Project page exists on Role page");
	}
}
