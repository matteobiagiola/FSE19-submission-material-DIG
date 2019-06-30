This Maven project is needed to create the class under test (`cut`) given a set of [page objects](https://github.com/SeleniumHQ/selenium/wiki/PageObjects). The `cut` is the code representation of the [navigation graph](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/graphs) which can be exercised through unit test generator tools as `evosuite`.

The `poparsing` java project works under the following assumptions regarding the way page objects (POs) are written:
- POs are divided in two categories: pages (also called containers) and components. POs representing pages are supposed to represent entire web pages, whereas components represent parts of a web page, for instance functionalities that can be reused across pages. An example of component is the `NavbarComponent` ([in dimeshift](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/phoenix/src/main/java/po/shared/components/NavbarComponent.java)) which contains the functionalities to navigate to the different sections of a web app. The component `NavbarComponent` is included for example in the `HomePage` PO (as an attribute of a class) if the `HomePage` PO models a web page that contains the navigation bar. Hence, the `HomePage` PO can use the methods of the `NavbarComponent` PO, as well as all pages PO that include it. In `dimeshift` such home page is called [`WalletsManagerPage`](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/dimeshift/src/main/java/po/wallets/pages/WalletsManagerPage.java)
- Not in all cases it makes sense to break down a web page in multiple components. For instance we chose to represent modals as pages that do not contain any component ([in dimeshift](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/fse2019/dimeshift/src/main/java/po/wallets/pages/modals/AddWalletPage.java)), since they expose simple functionalties as confirmation or forms (at least in our subjects)
- Components POs must implement the `PageComponent` interface.
- Pages POs are parsed by the `poparsing` project. Pages POs must implement the `PageObject` interface. Methods of pages POs model the functionalities of the web page they represent. The following snippet of code shows two examples of PO methods of a page PO. `method_1` is an example of a method with `preconditions`, i.e. dynamic checks on the web page to verify that the DOM elements the web driver is about to interact with are present in the web page. If `preconditions` hold then, after some actions are performed on the web page, a POPage `[SomePOPage]` must be returned by the method, which can be either the same PO page the method is part of (`POPageA`) or another one. The actions to be performed on the web page must use the methods of the component POs the page PO includes. More information about `preconditions` are available [in this publication](https://www.researchgate.net/publication/319138308_Search_Based_Path_and_Input_Data_Generation_for_Web_Application_Testing). `method_2` is a method of `POPageA` that has no preconditions, hence, after some actions are performed, it returns a PO Page. By returning a PO in each PO page method, the developer is implicitly specifying the navigation among pages of the web app the POs are modelling:

```java
public class POPageA implements PageObject {
  
  public [SomePOPage] method_1() {
    if(preconditions) {
      // some actions on the web page
      return new [SomePOPage]();
    } else {
      throw new Exception("some message");
    }
  }
  
  public [SomePOPage] method_2() {
    // some actions on the web page
    return new [SomePOPage]();
  }
  
}
```

For the subjects used in the experiments the specific `cut` have already been generated using this project. `poparsing` is needed when a new application is considered and the POs for that application have been developed. The [`config.properties` file](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/poparsing/config.properties) needs to be modified to target the specific application. An example of `config.properties` file for the `dimeshift` application can be found [here](https://github.com/matteobiagiola/FSE19-submission-material-DIG/blob/master/poparsing/config/manual/dimeshift.config.properties). 

```
starting_page_object_qualified_name= --> qualified name of the PO page that represent the HomePage for the target application
path_to_project= --> relative path to the source code that implements the PO for the target application
po_package_in_project= --> package name that contains the PO classes in the java project where the POs are 
project_name= --> application name
exceptions= --> always true, can be removed as a flag...
apogen= --> true if the Apogen tool was used to generate the POs
quit_browser_to_reset= --> true if your application needs the browser to be closed (e.g. service workers) in order to clean up its state
```

# Run the project


Run the script `run.sh`. It takes two arguments:
- `project` which is the project name: `dimeshift|splittypie|phoenix|pagekit|petclinic|retroboard`
- `po` which is either `manual` or `apogen`. This last option is needed to distinguish the two `cut` to be created in the project directory

Example:

`./run.sh dimeshift apogen`

Output:

The project outputs the navigation graph as a text file in the project root (`dimeshift-apogen.txt`) and it writes the generated `cut` in the `main` package of the java project that contains the POs for the target application.




