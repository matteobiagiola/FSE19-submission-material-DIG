package po.shared.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class NavbarComponent extends BasePageObject implements PageComponent {

    public NavbarComponent(WebDriver driver) {
        super(driver);
    }

    public void clickOnOwners(){
        this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/A[1]"));
    }

    public void clickOnRegister(){
        this.clickOnOwners();
        if(this.waitForElementBeingPresentOnPage(
                By.xpath("//div[@id=\"main-navbar\"]/ul/li[@class=\"dropdown open\" or @class=\"dropdown active open\"]"), 1000)){
            this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/UL[1]/LI[2]/A[1]"));
        }else{
            throw new IllegalStateException("clickOnRegister: dropdown timing not handled properly");
        }
    }

    public void clickOnAll(){
        this.clickOnOwners();
        if(this.waitForElementBeingPresentOnPage(
                By.xpath("//div[@id=\"main-navbar\"]/ul/li[@class=\"dropdown open\" or @class=\"dropdown active open\"]"), 1000)){
            this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[2]/UL[1]/LI[1]/A[1]"));
        }else{
            throw new IllegalStateException("clickOnAll: dropdown timing not handled properly");
        }
    }

    public void clickOnVets(){
        this.clickOn(By.xpath("//DIV[@id = 'main-navbar']/UL[1]/LI[3]/A[1]"));
    }

    public void clickOnLogo() {
        this.clickOn(By.xpath("//nav//a[@class=\"navbar-brand\"]"));
    }

    public void clickOnHome(){
        this.clickOn(By.xpath("//div[@id=\"main-navbar\"]/ul/li/a[@title=\"home page\"]"));
    }
}
