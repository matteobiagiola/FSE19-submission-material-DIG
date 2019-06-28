package po_apogen;

import custom_classes.Goals;
import custom_classes.WalletNames;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class CreatePlanBasicSettingsComponent extends BasePageObject implements PageComponent {

	public CreatePlanBasicSettingsComponent(WebDriver driver) {
		super(driver);
	}

	public void goToPlanPage() {
		this.clickOn(By.cssSelector("#button_step1_back"));
	}

	public void goToPlanAdvancedSettings(Goals goal, WalletNames walletName) {
		this.typeJS(By.id("input_name"), goal.value());
		int walletPosition = this.isWalletPresent(walletName);
		WebElement walletToClick = this.findElement(By.xpath("//*[@id]/div/div[2]/div/div/div[2]/div/div[2]/div/a[" + walletPosition + "]"));
		String classAttributeWalletToClick = this.getAttribute(walletToClick, "class");
		if(!classAttributeWalletToClick.contains("active")){
			// click on wallet only if it not selected yet
			this.clickOn(By.xpath("//*[@id]/div/div[2]/div/div/div[2]/div/div[2]/div/a[" + walletPosition + "]"));
		}
		this.clickOn(By.id("button_step1_next"));
	}

	/* method preconditions */

	public int isWalletPresent(WalletNames walletName){
		if(this.isElementPresentOnPage(By.xpath("//*[@id]/div/div[2]/div/div/div[2]/div/div[2]/div/a"))){
			List<WebElement> elements = this.findElements(By.xpath("//*[@id]/div/div[2]/div/div/div[2]/div/div[2]/div/a"));
			for(int i = 0 ; i < elements.size(); i++){
				if(elements.get(i).getText().trim().equals(walletName.value())){
					return i + 1;
				}
			}
			return -1;
		}
		return -1;
	}

}
