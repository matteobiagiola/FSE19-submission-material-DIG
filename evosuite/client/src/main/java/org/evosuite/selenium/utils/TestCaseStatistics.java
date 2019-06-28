package org.evosuite.selenium.utils;

import java.util.ArrayList;
import java.util.List;

public class TestCaseStatistics {
    private static TestCaseStatistics ourInstance = new TestCaseStatistics();

    public static TestCaseStatistics getInstance() {
        return ourInstance;
    }

    private List<Long> distanceComputationTimes = new ArrayList<>();
    private List<Long> testExecutionTimes = new ArrayList<>();
    private int alreadyExecutedTestCasesSize = 0;

    private TestCaseStatistics() {
    }

    public void storeDistanceComputationTime(long currentDistanceComputationTime){
        this.distanceComputationTimes.add(currentDistanceComputationTime);
    }

    public void storeTestExecutionTime(long currentTestExecutionTime){
        this.testExecutionTimes.add(currentTestExecutionTime);
    }

    public void setAlreadyExecutedTestCasesSize(int alreadyExecutedTestCasesSize){
        this.alreadyExecutedTestCasesSize = alreadyExecutedTestCasesSize;
    }

    public List<Long> getDistanceComputationTimes(){
        return new ArrayList<>(this.distanceComputationTimes);
    }

    public List<Long> getTestExecutionTimes(){
        return new ArrayList<>(this.testExecutionTimes);
    }

    public int getAlreadyExecutedTestCasesSize() { return this.alreadyExecutedTestCasesSize; }
}
