package po.owners.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import po_utils.BasePageObject;
import po_utils.PageComponent;

public class AddEditOwnerComponent extends BasePageObject implements PageComponent {

    public AddEditOwnerComponent(WebDriver driver) {
        super(driver);
    }

    public void registerOwner(String firstName, String lastName,
                              String address, String city, String telephone){
        this.type(By.name("firstName"), firstName);
        this.type(By.name("lastName"), lastName);
        this.type(By.name("address"), address);
        this.type(By.name("city"), city);
        this.type(By.name("telephone"), telephone);
        if(telephone.length() > 10 || firstName.length() > 40){
            this.clickOn(By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]"));
        }else{
            this.bruteForceClick(By.xpath("//div[@class=\"form-group\"]/button[@type=\"submit\"]"),
                    By.xpath("//owner-form/h2[text()=\"Owner\"]"), 20 , "submitOwnerForm: failed to click submit button");
        }
    }
}
