package po.home.pages;

import custom_classes.BoardNames;
import org.openqa.selenium.WebDriver;
import po.home.components.HomeComponent;
import po.login.pages.LoginContainerPage;
import po.retrospective.pages.RetrospectiveContainerPage;
import po.shared.components.NavbarComponent;
import po.home.components.TabComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class HomeContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public TabComponent tabComponent;
    public HomeComponent homeComponent;

    public HomeContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.tabComponent = new TabComponent(driver);
        this.homeComponent = new HomeComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("HomeContainerPage not loaded properly");
        }
    }

    //tested
    public LoginContainerPage logout(){
        this.tabComponent.clickAdvanced();
        this.homeComponent.clickLogout();
        return new LoginContainerPage(this.homeComponent.getDriver());
    }

    //tested
    public RetrospectiveContainerPage createNewSession(){
        this.tabComponent.clickCreate();
        this.homeComponent.clickCreateSession();
        return new RetrospectiveContainerPage(this.homeComponent.getDriver());
    }

    //tested
    public HomeContainerPage goToPreviousView(){
        if(this.tabComponent.isPreviousTabPresent()){
            this.tabComponent.clickPrevious();
            return this;
        }else{
            throw new IllegalStateException("goToPreviousView: previous tab is not present");
        }
    }

    //tested
    public RetrospectiveContainerPage goToBoard(BoardNames boardName){
        if(this.tabComponent.isPreviousTabActive() && this.homeComponent.isBoardPresent(boardName.value())){
            this.homeComponent.clickOnBoard(boardName.value());
            return new RetrospectiveContainerPage(this.tabComponent.getDriver());
        }else{
            throw new IllegalStateException("goToBoard: previous tab is not active or board " + boardName.value() + " is not present");
        }
    }

    //tested
    public HomeContainerPage goToHome(){
        this.navbarComponent.clickHomeLink();
        return this;
    }



    @Override
    public boolean isPageLoaded() {
        if(this.homeComponent.waitForElementBeingPresentOnPage(ConstantLocators.HOME.value())){
            return true;
        }
        return false;
    }
}
