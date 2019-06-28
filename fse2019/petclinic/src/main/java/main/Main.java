package main;

import custom_classes.*;
import po_utils.ResetAppState;


public class Main {

    public static void main(String[] args){

        ResetAppState.reset();

        ClassUnderTest classUnderTest0 = new ClassUnderTest();
        classUnderTest0.goToOwnersListHomeContainerPage();
        ResetAppState.reset();
        classUnderTest0 = new ClassUnderTest();
        classUnderTest0.registerNewOwnerHomeContainerPage();

    }
}
