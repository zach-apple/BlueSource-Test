package com.bluesource.accounts;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.ReportedTimesSummary;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class TwoFutureWeeksVisibleTest extends WebBaseTest {

	@BeforeMethod
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion", "operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest, String browserVersion,
			String operatingSystem, String environment) {
		setApplicationUnderTest("BLUESOURCE");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setEnvironment(environment);
		setThreadDriver(true);
		testStart("");
	}

	@AfterMethod
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}

	@Test(groups = { "smoke" })
	public void testTwoFutureWeeks() {
		TestReporter.logStep("Login as a project user");
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.Login("PROJECT_USER");
		TestReporter.logStep("Click the Edit button from the Project Info section");
		EmployeePage employeePage = new EmployeePage(getDriver());
		employeePage.clickManageProject();
		TestReporter.logStep(
				"Find the current week, and verify that there are two future weeks beyond the current week shown");
		ReportedTimesSummary reportedTimesSummary = new ReportedTimesSummary(getDriver());
		TestReporter.assertTrue(reportedTimesSummary.findTwoFutureWeeks(),
				"Verify that there are two weeks beyond the current");
	}
}
