package org.evosuite.selenium.utils;

public class CheckCondition {

    public static void checkState(boolean condition, String errorMessage){
        if(!condition){
            throw new IllegalStateException(errorMessage);
        }
    }

    public static void checkArguments(boolean condition, String errorMessage){
        if(!condition){
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
