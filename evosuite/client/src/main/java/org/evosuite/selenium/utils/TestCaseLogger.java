package org.evosuite.selenium.utils;

import org.evosuite.Properties;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.statements.Statement;

import java.io.*;
import java.util.Date;

public class TestCaseLogger {
    private static TestCaseLogger ourInstance = new TestCaseLogger();

    public static TestCaseLogger getInstance() {
        return ourInstance;
    }
    private Writer debugWriter;

    private TestCaseLogger() {
        File home = new File(System.getProperty("user.home"));
        File debugFile = new File(home.getAbsolutePath() + "/Desktop/debug" + Properties.APPLICATION_NAME + ".txt");
        if(debugFile.exists()) debugFile.delete();
        try {
            this.debugWriter = new FileWriter(debugFile.getAbsoluteFile(), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logException(Statement statement, Throwable exceptionThrown){
        try {
            this.debugWriter.append("Exception thrown in statement position " + statement.getPosition()
                    + ": " + statement.getCode()
                    + " - " + exceptionThrown.getClass().getName() + " - "
                    + exceptionThrown.getMessage());
            this.debugWriter.flush();
            this.logSpace(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(TestCase testCase){
        try {
            this.debugWriter.append("[" + new Date() + "] Test case ID: " + testCase.getID() + "\n");
            this.debugWriter.append(testCase.toCode());
            this.debugWriter.append("---------------------------------------------------------------\n");
            String testCode = testCase.toCode();
            String testCodeWithLineNumbers = this.addLineNumbersToTestCode(testCode);
            this.debugWriter.append(testCodeWithLineNumbers + "\n");
            this.debugWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logSpace(int numberOfSpaces){
        try {
            for (int i = 0; i < numberOfSpaces; i++) {
                this.debugWriter.append("\n");
            }
            this.debugWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addLineNumbersToTestCode(String testCode){
        String[] statements = testCode.split("\n");
        String testCodeWithLineNumbers = "";
        for (int i = 0; i < statements.length; i++) {
            testCodeWithLineNumbers += i + ": " + statements[i] + "\n";
        }
        return testCodeWithLineNumbers;
    }
}
