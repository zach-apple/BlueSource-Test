package com.bluesource.projects;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.Constants;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class Show_Rate_on_Project_Show_Page_Verify extends WebBaseTest {
	//*****************
	// Data Provider
	//*****************
	@DataProvider(name = "show_project_rate_verify", parallel = true)
	public Object[][]scenarions(){
		return new ExcelDataProvider(Constants.EXCEL_SHEETS + "/ShowRateOnProjectVerify.xlsx","Sheet1").getTestData();
	}

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
		testStart("Show_Rate_on_Project_Show_Page_Verify");
	}

	@AfterMethod
	public void close(ITestContext testResults){
		endTest("TestAlert", testResults);
	}

	@Test(dataProvider = "show_project_rate_verify")
	public void Show_Rate_on_Project_Show_Page_Verify(String scenario, String account, String project, String role)
	{
		String rateFromProjectPage;
		TestReporter.logScenario("Scenario: " + scenario + " START");
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());

		TestReporter.logStep("Login to application as Account Viewer");
		loginPage.Login("ACCOUNT_VIEWER");

		TestReporter.logStep("Go to the Accounts page");
		header.navigateAccounts();

		TestReporter.logStep("Go to Accounts page for [" + account + "]");
		accounts.clickAccountLink(account);

		TestReporter.logStep("Go to [" + project + "] in [" + account + "] page");
		accounts.clickProjectLink(project);

		TestReporter.logStep("Get and store [" + role + "] rate");
		rateFromProjectPage = accounts.getRoleRateFromProjectPage(role);

		TestReporter.assertNotNull(rateFromProjectPage,"Check that the rate from Project page isn't null.");

		TestReporter.logStep("Go to Role [" + role + "] page");
		accounts.clickRoleLink(role);

		TestReporter.assertTrue(accounts.verifyRoleRate(rateFromProjectPage),
				"Verify Role rate from Project page exists on Role page in scenario \"" + scenario + "\"");

		TestReporter.logScenario("Scenario " + scenario + " END");
	}
}
