package {{ packageName }};

import org.openqa.selenium.WebDriver;
import po_utils.DriverProvider;
import custom_classes.*;

{% if exceptions %}
import po_utils.NotInTheRightPageObjectException;
import po_utils.NotTheRightInputValuesException;
{% endif %}

public class {{ className }}{

private Object currentPage=null;
{% if quitBrowserToReset %}
private WebDriver driver;
{% endif %}

//BOOTSTRAP POINT
public {{className}}(){
        //start driver and browser
        {% if quitBrowserToReset %}
        driver = new DriverProvider().getActiveDriver();
        {% else %}
        WebDriver driver = new DriverProvider().getActiveDriver();
        {% endif %}
        {% include "src/main/resources/templates/Initialization" + projectName + ".java" %}
        
        this.currentPage = new {{ startingPageObjectQualifiedName }}(driver);
    }

    {% for methodName in methodNames%}

        {% include "src/main/resources/temp/" + methodName + ".java" %}

    {% endfor %}

    {% if quitBrowserToReset %}
    // will be called by es at the end of each test. It is important that it is called quitDriver (search for the string in es client)
    private void quitDriver() { driver.quit(); }
    {% endif %}
}
