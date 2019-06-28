package po_apogen;

import custom_classes.UserRoles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.ArrayList;
import java.util.List;

public class AdministratorComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for Anonymous (state111) --> RolesContainerPage
	 */
	public AdministratorComponent(WebDriver driver) {
		super(driver);
	}

	public void goToAnonymous() {
		this.clickOnRole(UserRoles.ANONYMOUS);
	}

	public void goToAdministrator() {
		this.clickOn(By.xpath("//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/UL[1]/LI[3]"));
	}

	public void clickOnRole(UserRoles userRoles){
		WebElement userLink = this.getUserRoleLink(userRoles);
		this.clickOn(userLink);
	}

	public WebElement getUserRoleLink(UserRoles userRoles){
		List<WebElement> links = this.getUserRoleLinks();
		for(WebElement link: links){
			String userRoleName = this.getText(link);
			if(userRoleName.equals(userRoles.value())) return link;
		}
		throw new IllegalStateException("getUserRoleLink: user link not found for user role " + userRoles.value());
	}

	public List<WebElement> getUserRoleLinks(){
		List<WebElement> userLiElements = this.findElements(By.xpath("//ul[@class=\"uk-sortable uk-nav uk-nav-side\"]/li"));
		List<WebElement> links = new ArrayList<WebElement>();
		for(WebElement userLiElement: userLiElements){
			WebElement link = this.findElementJSByXPathStartingFrom(userLiElement, ".//a");
			links.add(link);
		}
		return links;
	}
}
