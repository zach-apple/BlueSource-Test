package com.orasi.bluesource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.orasi.utils.Constants;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class ReportedTimesSummary {
	private OrasiDriver driver = null;

	/** Page Elements **/
	@FindBy(xpath = "//div[contains(@class,'time-back')]") private Button btnBack;
	@FindBy(xpath = "//*[@id=\"content\"]/div[4]/a") private Link lnkNextMonth;

	/** Constructor **/
	public ReportedTimesSummary(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/** Page Interactions **/

	public void clickBackbutton() {
		// TODO Auto-generated method stub
		btnBack.click();
	}

	/**
	 * Returns true if there are two weeks shown beyond the current week.
	 * 
	 * @author Zach Apple
	 * @return true if the two future weeks are present, false otherwise
	 */
	public boolean findTwoFutureWeeks() {
		LocalDate theDate = LocalDateTime.now().toLocalDate();
		String startOfWeekPlusOne = "";
		String endOfWeekPlusOne = "";
		String startOfWeekPlusTwo = "";
		String endOfWeekPlusTwo = "";
		// base the start/end of weeks to search for off the current day of the week
		switch (theDate.getDayOfWeek()) {
		case MONDAY:
			startOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusWeeks(1));
			endOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(6).plusWeeks(1));
			startOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusWeeks(2));
			endOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(6).plusWeeks(2));
			break;
		case TUESDAY:
			startOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.minusDays(1).plusWeeks(1));
			endOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(5).plusWeeks(1));
			startOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.minusDays(1).plusWeeks(2));
			endOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(5).plusWeeks(2));
			break;
		case WEDNESDAY:
			startOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.minusDays(2).plusWeeks(1));
			endOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(4).plusWeeks(1));
			startOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.minusDays(2).plusWeeks(2));
			endOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(4).plusWeeks(2));
			break;
		case THURSDAY:
			startOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.minusDays(3).plusWeeks(1));
			endOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(3).plusWeeks(1));
			startOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.minusDays(3).plusWeeks(2));
			endOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(3).plusWeeks(2));
			break;
		case FRIDAY:
			startOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(3));
			endOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(2).plusWeeks(1));
			startOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(3).plusWeeks(1));
			endOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(2).plusWeeks(2));
			break;
		case SATURDAY:
			startOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(2));
			endOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(1).plusWeeks(1));
			startOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(2).plusWeeks(1));
			endOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(1).plusWeeks(2));
			break;
		case SUNDAY:
			startOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusDays(1));
			endOfWeekPlusOne = String.format("%tm/%<td/%<tY", theDate.plusWeeks(1));
			startOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusDays(1).plusWeeks(1));
			endOfWeekPlusTwo = String.format("%tm/%<td/%<tY", theDate.plusWeeks(2));
			break;
		default:
			break;
		}
		boolean allVisible = false;//holds the final verification value
		// the surrounding pieces of the xpath to look for on the page
		String xpathToSearchPart1 = "//td[contains(text(),'", xpathToSearchPart2 = "')]";
		// if the two weeks are both in the current month
		if (Integer.parseInt(endOfWeekPlusTwo.substring(0, 2)) == theDate.getMonthValue()) {
			allVisible = driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusOne + xpathToSearchPart2))
					.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusTwo + xpathToSearchPart2))
							.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusOne + xpathToSearchPart2))
							.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusTwo + xpathToSearchPart2))
							.syncVisible();
		} // if week one is split, starting in current month and ending in the next
		else if (Integer.parseInt(startOfWeekPlusOne.substring(0, 2)) == theDate.getMonthValue()
				&& Integer.parseInt(endOfWeekPlusOne.substring(0, 2)) == theDate.plusMonths(1).getMonthValue()) {
			// check beginning of first week
			allVisible = driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusOne + xpathToSearchPart2))
					.syncVisible();
			// click next month
			lnkNextMonth.click();
			// check end of week one, then the rest
			allVisible &= driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusTwo + xpathToSearchPart2))
					.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusOne + xpathToSearchPart2))
							.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusTwo + xpathToSearchPart2))
							.syncVisible();
		} // if the first week is in the current month, but the second is in the next month
		else if (Integer.parseInt(endOfWeekPlusOne.substring(0, 2)) == theDate.getMonthValue()
				&& Integer.parseInt(startOfWeekPlusTwo.substring(0, 2)) == theDate.plusMonths(1).getMonthValue()) {
			// check week one
			allVisible = driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusOne + xpathToSearchPart2))
					.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusTwo + xpathToSearchPart2))
							.syncVisible();
			// click next month
			lnkNextMonth.click();
			// check the rest
			allVisible &= driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusOne + xpathToSearchPart2))
					.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusTwo + xpathToSearchPart2))
							.syncVisible();
		} // if the first week and beginning of the second are in the current month, but
			// the end of the second week is in the next month
		else if (Integer.parseInt(startOfWeekPlusTwo.substring(0, 2)) == theDate.getMonthValue()
				&& Integer.parseInt(endOfWeekPlusTwo.substring(0, 2)) == theDate.plusMonths(1).getMonthValue()) {
			// check the first week and beginning of the second
			allVisible = driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusOne + xpathToSearchPart2))
					.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusTwo + xpathToSearchPart2))
							.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusOne + xpathToSearchPart2))
							.syncVisible();
			// click the next month
			lnkNextMonth.click();
			// check the end of the second week
			allVisible &= driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusTwo + xpathToSearchPart2))
					.syncVisible();
		} // else both weeks are in the next month
		else {
			// click the next month, and check all
			lnkNextMonth.click();
			allVisible = driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusOne + xpathToSearchPart2))
					.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + startOfWeekPlusTwo + xpathToSearchPart2))
							.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusOne + xpathToSearchPart2))
							.syncVisible()
					&& driver.findElement(By.xpath(xpathToSearchPart1 + endOfWeekPlusTwo + xpathToSearchPart2))
							.syncVisible();
		}
		return allVisible;
	}

}