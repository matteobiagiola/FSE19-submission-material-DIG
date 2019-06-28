package org.evosuite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stats {
    private static Stats ourInstance = new Stats();

    public static Stats getInstance() {
        return ourInstance;
    }

    private int numberOfTestsExecuted;
    private int currentCoverage; //percentage
    private static final Logger logger = LoggerFactory.getLogger(Stats.class);

    private Stats() {
        this.numberOfTestsExecuted = 0;
        this.currentCoverage = 0;
    }

    public int getCurrentCoverage() {
        return currentCoverage;
    }

    public void setCurrentCoverage(double currentCoverage){
        int currentCoveragePercentage = (int) Math.floor(currentCoverage * 100);
        this.setCurrentCoverage(currentCoveragePercentage);
    }

    public void setCurrentCoverage(int currentCoverage) {
        this.currentCoverage = currentCoverage;
        this.printStats();
    }

    public int getNumberOfTestsExecuted() {
        return numberOfTestsExecuted;
    }

    public void setNumberOfTestsExecuted(int numberOfTestsExecuted) {
        this.numberOfTestsExecuted = numberOfTestsExecuted;
    }

    public void updateNumberOfTestsCaseExecuted(){
        this.numberOfTestsExecuted++;
    }

    private void printStats(){
        logger.debug("Executed test cases - {}",this.numberOfTestsExecuted);
        logger.debug("Coverage percentage - {}",this.currentCoverage);
    }

}
