package main;

import custom_classes.BoardNames;
import custom_classes.Id;
import custom_classes.IdeasPosts;
import custom_classes.NotWentWellPosts;
import custom_classes.PeopleNames;
import custom_classes.WentWellPosts;
import po_utils.ResetAppState;

public class Main {

    public static void main(String[] args){

        ClassUnderTest classUnderTest0 = new ClassUnderTest();
        classUnderTest0.loginLoginContainerPage(PeopleNames.JOHN);

    }
}
