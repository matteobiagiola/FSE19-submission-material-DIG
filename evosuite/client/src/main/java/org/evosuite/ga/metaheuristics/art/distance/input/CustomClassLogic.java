package org.evosuite.ga.metaheuristics.art.distance.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomClassLogic {

    private int lower;
    private int upper;
    private static final Logger logger = LoggerFactory.getLogger(CustomClassLogic.class);

    public CustomClassLogic(int lower, int upper){
        this.lower = lower;
        this.upper = upper;
    }

    private int map(int numberToMap){
        int tempValue = numberToMap % (this.upper - this.lower);
        if(tempValue < 0) tempValue += this.upper - this.lower;
        tempValue += lower;
        return tempValue;
    }

    public String map(String stringToMap){
        int numberToMap = Integer.parseInt(stringToMap);
        int result = this.map(numberToMap);
        return String.valueOf(result);
    }
}
