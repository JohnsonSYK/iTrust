package edu.ncsu.csc.itrust.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ncsu.csc.itrust.enums.TransactionType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ViewCauseDeathReportTest extends iTrustSeleniumTest {
    private HtmlUnitDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.icd9cmCodes();
        gen.hcp3();
        gen.patient2();
        gen.clearLoginFailures();
        driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testGenerateReport1() throws Exception {
        driver = (HtmlUnitDriver)login("9000000003", "pw");
        assertLogged(TransactionType.HOME_VIEW, 9000000003L, 0L, "");
        driver.findElement(By.linkText("View cause-of-death trends report")).click();
        driver.findElement(By.name("startingYear")).sendKeys("1900");
        driver.findElement(By.name("endingYear")).sendKeys("2020");
        driver.findElement(By.name("fSubmit")).click();
        assertTrue(driver.getPageSource().contains("Diabetes"));
        assertTrue(driver.getPageSource().contains("250.10"));
    }
    public void testGenerateReport2() throws Exception {
        driver = (HtmlUnitDriver)login("9000000003", "pw");
        assertLogged(TransactionType.HOME_VIEW, 9000000003L, 0L, "");
        driver.findElement(By.linkText("View cause-of-death trends report")).click();
        driver.findElement(By.name("startingYear")).sendKeys("1900");
        driver.findElement(By.name("endingYear")).sendKeys("2000");
        driver.findElement(By.name("fSubmit")).click();
        assertTrue(driver.getPageSource().contains("morgue"));
        assertTrue(driver.getPageSource().contains("job"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}