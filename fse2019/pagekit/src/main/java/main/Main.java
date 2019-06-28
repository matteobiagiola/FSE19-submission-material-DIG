package main;

import custom_classes.*;
import po_utils.DriverProvider;
import po_utils.ResetAppState;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ResetAppState.reset();
//        ClassUnderTest classUnderTest0 = new ClassUnderTest();
//        classUnderTest0.addUserWidgetDashboardContainerPage(WidgetUserType.LASTREGISTERED, WidgetUserDisplay.LIST,
//                WidgetTotalUser.HIDE, WidgetNumberOfUsers.SIX);
//        ResetAppState.reset();
//        classUnderTest0 = new ClassUnderTest();
//        classUnderTest0.addFeedWidgetDashboardContainerPage(WidgetFeedTitle.PAGEKITNEWS, WidgetFeedUrl.PAGEKIT,
//                new WidgetFeedNumberOfPosts(1), WidgetFeedPostContent.SHOWFIRSTPOST);

        ClassUnderTestApogen classUnderTestApogen0 = new ClassUnderTestApogen();
        int int0 = (-454);
        Id id0 = new Id(int0);
        classUnderTestApogen0.deletePagekitDashboardPage(id0);
        classUnderTestApogen0.okDeleteWidgetPage();
// Undeclared exception!
        try {
            classUnderTestApogen0.goToEditComponentLocationDashboardPage(id0);
        } catch(RuntimeException e) {
            //
            // WebElement with id 3 is not a location widget or the widget form is open
            //
            //verifyException("main.ClassUnderTestApogen", e);
        }

        WidgetLocation widgetLocation0 = WidgetLocation.MILANO;
        WidgetUnit widgetUnit0 = WidgetUnit.METRIC;
// Undeclared exception!
        try {
            classUnderTestApogen0.formEditLocationPage(widgetLocation0, widgetUnit0);
        } catch(RuntimeException e) {
            //
            // formEditLocationPage: expected po_apogen.EditLocationPage, found DashboardPage
            //
            //verifyException("main.ClassUnderTestApogen", e);
        }

        classUnderTestApogen0.goToEditUserDashboardPage();
        Username username0 = Username.ADMIN;
        Name name0 = Name.JOHN;
        Email email0 = Email.FOO;
        UserPassword userPassword0 = UserPassword.FOO;
        classUnderTestApogen0.user_editEditUserPage(username0, name0, email0, userPassword0);
//// Undeclared exception!
//        try {
//
//        } catch(IllegalStateException e) {
//            //
//            // edit user notifications not handled properly
//            //
//            //verifyException("po_apogen.EditUserPage", e);
//        }
        classUnderTestApogen0.goToUsersEditUserPage();
        new DriverProvider().getActiveDriver().quit();



    }
}
