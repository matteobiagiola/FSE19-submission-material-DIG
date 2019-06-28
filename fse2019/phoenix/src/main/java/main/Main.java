package main;

import custom_classes.BoardNames;
import custom_classes.Email;
import custom_classes.Passwords;
import po_utils.ResetAppState;

public class Main {

    public static void main(String[] args){

//        WebDriver driver = DriverProvider.getActiveDriver();

        ResetAppState.reset();
        ClassUnderTest classUnderTest0 = new ClassUnderTest();
        classUnderTest0.loginAsJohnLoginContainerPage();
        BoardNames boardNames0 = BoardNames.WORK_BOARD;
        classUnderTest0.openAddNewBoardFormBoardsContainerPage();
        classUnderTest0.addNewBoardBoardsContainerPage(boardNames0);
        classUnderTest0.goToBoardListBoardListContainerPage();
        classUnderTest0.goToBoardUsingNavbarBoardsContainerPage(boardNames0);
        classUnderTest0.signOutBoardListContainerPage();
        Email email0 = Email.JOHNDOE;
        Passwords passwords0 = Passwords.JOHNDOE;
        classUnderTest0.loginLoginContainerPage(email0, passwords0);
        BoardNames boardNames1 = BoardNames.PERSONAL_BOARD;
        classUnderTest0.addNewBoardBoardsContainerPage(boardNames1);
        ResetAppState.reset();
        classUnderTest0 = new ClassUnderTest();


    }


}
