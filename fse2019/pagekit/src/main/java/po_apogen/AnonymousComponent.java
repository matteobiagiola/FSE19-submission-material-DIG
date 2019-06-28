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

public class AnonymousComponent extends BasePageObject implements PageComponent {

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[5]/TABLE[1]/TBODY[1]/TR[1]/TD[2]")
	private WebElement td_Td_Managesite;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[2]/TD[3]")
	private WebElement td_Td_Td_Manageuserpermissions;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[2]/TABLE[1]/TBODY[1]/TR[1]/TD[2]")
	private WebElement td_Td_Managewidgets;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[5]/TABLE[1]/TBODY[1]/TR[2]/TD[3]")
	private WebElement td_Td_Td_Usethesiteinmaintenancemode;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/P[1]/A[1]")
	private WebElement a_Addrole;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[5]/TABLE[1]/TBODY[1]/TR[1]/TD[1]")
	private WebElement td_Managesite;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[1]/TABLE[1]/TBODY[1]/TR[2]/TD[3]")
	private WebElement td_Td_Td_Applysystemupdates;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[2]/TABLE[1]/TBODY[1]/TR[1]/TD[1]")
	private WebElement td_Managewidgets;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[2]/TD[2]")
	private WebElement td_Td_Manageuserpermissions;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/UL[1]/LI[1]")
	private WebElement li_Ul_Anonymous;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[2]/TD[1]")
	private WebElement td_Manageuserpermissions;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[5]/TABLE[1]/TBODY[1]/TR[2]/TD[2]")
	private WebElement td_Td_Usethesiteinmaintenancemode;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[1]/TD[2]")
	private WebElement td_Td_Manageusers;

	@FindBy(xpath = "//HEADER[@id = 'header']/DIV[1]/NAV[1]/UL[1]/LI[3]")
	private WebElement li_Li_Li_Ul_List;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[3]/TABLE[1]/TBODY[1]/TR[2]/TD[1]")
	private WebElement td_Managestoragereadonly;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[4]/TABLE[1]/TBODY[1]/TR[1]/TD[1]")
	private WebElement td_Accesssystemsettings;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[4]/TABLE[1]/TBODY[1]/TR[1]/TD[2]")
	private WebElement td_Td_Accesssystemsettings;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[5]/TABLE[1]/TBODY[1]/TR[2]/TD[1]")
	private WebElement td_Usethesiteinmaintenancemode;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/UL[1]/LI[3]")
	private WebElement li_Li_Li_Ul_Anonymous;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[1]/TABLE[1]/TBODY[1]/TR[1]/TD[2]")
	private WebElement td_Td_Manageextensionsandthemes;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[3]/TD[3]")
	private WebElement td_Td_Td_Accessadminarea;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/UL[1]/LI[3]/A[1]")
	private WebElement a_Administrator;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[1]/TD[1]")
	private WebElement td_Manageusers;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[3]/TABLE[1]/TBODY[1]/TR[1]/TD[1]")
	private WebElement td_Managestorage;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[3]/TABLE[1]/TBODY[1]/TR[2]/TD[2]")
	private WebElement td_Td_Managestoragereadonly;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/UL[1]/LI[2]/A[1]")
	private WebElement a_Authenticated;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[1]/TABLE[1]/TBODY[1]/TR[2]/TD[2]")
	private WebElement td_Td_Applysystemupdates;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[4]/TABLE[1]/TBODY[1]/TR[1]/TD[3]")
	private WebElement td_Td_Td_Accesssystemsettings;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[3]/TABLE[1]/TBODY[1]/TR[2]/TD[3]")
	private WebElement td_Td_Td_Managestoragereadonly;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/UL[1]/LI[1]/A[1]")
	private WebElement a_Anonymous;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[3]/TABLE[1]/TBODY[1]/TR[1]/TD[2]")
	private WebElement td_Td_Managestorage;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[3]/TD[2]")
	private WebElement td_Td_Accessadminarea;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[4]/TABLE[1]/THEAD[1]/TR[1]/TH[2]")
	private WebElement th_Th_Systemsettings;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[2]/TABLE[1]/TBODY[1]/TR[1]/TD[3]")
	private WebElement td_Td_Td_Managewidgets;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[3]/TABLE[1]/THEAD[1]/TR[1]/TH[2]")
	private WebElement th_Th_Systemfinder;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[3]/TABLE[1]/TBODY[1]/TR[1]/TD[3]")
	private WebElement td_Td_Td_Managestorage;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[1]/TABLE[1]/TBODY[1]/TR[2]/TD[1]")
	private WebElement td_Applysystemupdates;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[1]/TABLE[1]/TBODY[1]/TR[1]/TD[3]")
	private WebElement td_Td_Td_Manageextensionsandthemes;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[1]/TD[3]")
	private WebElement td_Td_Td_Manageusers;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[5]/TABLE[1]/TBODY[1]/TR[1]/TD[3]")
	private WebElement td_Td_Td_Managesite;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[1]/TABLE[1]/TBODY[1]/TR[1]/TD[1]")
	private WebElement td_Manageextensionsandthemes;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[2]/DIV[6]/TABLE[1]/TBODY[1]/TR[3]/TD[1]")
	private WebElement td_Accessadminarea;

	@FindBy(xpath = "//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/UL[1]/LI[2]")
	private WebElement li_Li_Ul_Anonymous;

	@FindBy(xpath = "//*[@id='roles']/div/div[1]/div/ul/li[4]/ul/li[1]/a")
	private WebElement a_editRole;

	@FindBy(xpath = "//*[@id='roles']/div/div[1]/div/ul/li[4]/ul/li[2]/a")
	private WebElement a_deleteRole;

	private WebDriver driver;

	/**
	 * Page Object for Anonymous (state108) --> RolesContainerPage
	 */
	public AnonymousComponent(WebDriver driver) {
		super(driver);
	}

	public void goToAdministrator() {
		this.clickOn(By.xpath("//DIV[@id = 'roles']/DIV[1]/DIV[1]/DIV[1]/UL[1]/LI[3]/A[1]"));
	}

	public void goToAddRole() {
		this.clickOn(By.xpath("//a[@class=\"uk-button\"]"));
	}

	public void goToEditRole(UserRoles userRoles){
		int userRoleIndex = this.getUserRoleIndex(this.getUserRoleNames(), userRoles);
		List<WebElement> userRoleLiElements = this.getUserRoleLiElements();
		WebElement userRoleLiElement = userRoleLiElements.get(userRoleIndex);
		WebElement editButtonElement = this.findElementJSByXPathStartingFrom(userRoleLiElement, ".//a[@class=\"pk-icon-edit pk-icon-hover\"]");
		this.clickOn(editButtonElement);
	}

	public void goToDeleteRole(UserRoles userRoles){
		int userRoleIndex = this.getUserRoleIndex(this.getUserRoleNames(), userRoles);
		List<WebElement> userRoleLiElements = this.getUserRoleLiElements();
		WebElement userRoleLiElement = userRoleLiElements.get(userRoleIndex);
		WebElement deleteButtonElement = this.findElementJSByXPathStartingFrom(userRoleLiElement, ".//a[@class=\"pk-icon-delete pk-icon-hover\"]");
		this.clickOn(deleteButtonElement);
	}

	/*-----------------------------------------------------------------------------*/

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

	public boolean isRoleEditable(UserRoles userRoles){
		int userRoleIndex = this.getUserRoleIndex(this.getUserRoleNames(), userRoles);
		List<WebElement> userRoleLiElements = this.getUserRoleLiElements();
		if(userRoleIndex <= userRoleLiElements.size()){
			WebElement userRoleLiElement = userRoleLiElements.get(userRoleIndex);
			WebElement containerIconsUserRoleEditable = this.findElementJSByXPathStartingFrom(userRoleLiElement, ".//ul");
			if(containerIconsUserRoleEditable != null) return true;
			return false;
		}else{
			throw new IllegalStateException("isRoleEditable: could not established if user role " + userRoles.value() + " is editable or not");
		}
	}

	public int getUserRoleIndex(List<String> userRoleNames, UserRoles userRoles){
		for (int i = 0; i < userRoleNames.size(); i++) {
			if(userRoleNames.get(i).equals(userRoles.value())) return i;
		}
		throw new IllegalStateException("getUserRoleIndex: could not find index for user role: " + userRoles.value());
	}

	public List<WebElement> getUserRoleLiElements(){
		List<WebElement> userLiElements = this.findElements(By.xpath("//ul[@class=\"uk-sortable uk-nav uk-nav-side\"]/li"));
		return userLiElements;
	}

	public List<String> getUserRoleNames(){
		List<String> userRoleNames = new ArrayList<String>();
		List<WebElement> userLiElements = this.findElements(By.xpath("//ul[@class=\"uk-sortable uk-nav uk-nav-side\"]/li"));
		for(WebElement userLiElement: userLiElements){
			WebElement webElementWithText = this.findElementJSByXPathStartingFrom(userLiElement, ".//a[not(@data-uk-tooltip)]");
			String textUserLiElement = this.getText(webElementWithText);
			userRoleNames.add(textUserLiElement);
		}
		return userRoleNames;
	}

	public boolean isRolePresent(UserRoles userRoles){
		return this.getUserRoleNames().contains(userRoles.value());
	}

	public boolean isRoleAdmin(UserRoles userRoles){
		if(userRoles.value().equals("Administrator")) return true;
		return false;
	}

}
