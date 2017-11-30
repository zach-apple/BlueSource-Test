package com.orasi.web.webelements;

import java.util.List;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orasi.web.WebBaseTest;
import com.orasi.web.by.angular.FindByNG;
import com.orasi.web.webelements.impl.internal.ElementFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestElementInternalHandlers extends WebBaseTest {
    @FindBy(xpath = "//*[@ng-model='firstName']")
    private Button btn1;
    @FindBys({ @FindBy(xpath = "//*[@ng-model='firstName']"), @FindBy(xpath = "//*[@ng-model='firstName']") })
    private List<Textbox> btn2;
    @FindByNG(ngModel = "firstName")
    private Textbox btn3;
    @FindAll({ @FindBy(tagName = "input"), @FindBy(tagName = "input") })
    private Textbox btn4;

    @BeforeTest(groups = { "regression", "interfaces", "internal", "dev" })
    public void setup() {
        if (getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty()) {
            throw new SkipException("Test not valid for HTMLUnitDriver");
        }
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/angular/angularPage.html");
        testStart("TestButton");
    }

    @AfterTest(groups = { "regression", "interfaces", "internal", "dev" })
    public void close(ITestContext testResults) {
        endTest("TestAlert", testResults);
    }

    @Features("Element Interfaces")
    @Stories("ElementInternalHandlers")
    @Title("factory")
    @Test(groups = { "regression", "interfaces", "button" })
    public void factory() {
        ElementFactory.initElements(getDriver(), this);
        btn3.getWrappedElement();
        btn3.click();
    }

    @Features("Element Interfaces")
    @Stories("ElementInternalHandlers")
    @Title("factory")
    @Test(groups = { "regression", "interfaces", "button" }, dependsOnMethods = "factory")
    public void elementList() {
        btn2.get(0).getWrappedElement();
        btn2.get(0).highlight();
    }
}
