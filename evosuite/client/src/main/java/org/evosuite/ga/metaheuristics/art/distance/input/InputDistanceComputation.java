package org.evosuite.ga.metaheuristics.art.distance.input;

import org.evosuite.Properties;
import org.evosuite.ga.metaheuristics.art.distance.input.parsing.CustomEnumDetails;
import org.evosuite.ga.metaheuristics.art.sequencealignment.LevenshteinString;
import org.evosuite.ga.metaheuristics.art.sequencealignment.MethodSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputDistanceComputation {

    private final MethodSequence methodSequence1;
    private final MethodSequence methodSequence2;
    private final MethodSequence longestCommonSubsequence;
    private final int idCandidateTestCase;
    private List<Integer> lcsReferencesFirstSequence = new ArrayList<Integer>();
    private static final Logger logger = LoggerFactory.getLogger(InputDistanceComputation.class);

    public InputDistanceComputation(int idCandidateTestCase,
                                    MethodSequence methodSequence1,
                                    MethodSequence methodSequence2,
                                    MethodSequence longestCommonSubsequence,
                                    List<Integer> lcsReferencesFirstSequence){
        this.idCandidateTestCase = idCandidateTestCase;
        this.methodSequence1 = methodSequence1;
        this.methodSequence2 = methodSequence2;
        this.longestCommonSubsequence = longestCommonSubsequence;
        this.lcsReferencesFirstSequence = lcsReferencesFirstSequence;
    }

    public Double computeDistance(){
        //logger.debug("First sequence: " + this.methodSequence1);
        List<List<String>> parameterList1 = this.getParameterList(this.methodSequence1,this.lcsReferencesFirstSequence);
        //logger.debug("Positions LCS first sequence: " + this.lcsReferencesFirstSequence);
        //logger.debug("ParameterList1: " + parameterList1);
        List<Integer> positionsSecondSequence = this.getPositionsSecondSequence(this.lcsReferencesFirstSequence,methodSequence1.getMethodList(),methodSequence2.getMethodList());
        //logger.debug("Second sequence: " + this.methodSequence2);
        //logger.debug("Positions LCS second sequence: " + positionsSecondSequence);
        List<List<String>> parameterList2 = this.getParameterList(this.methodSequence2,positionsSecondSequence);
        //logger.debug("ParameterList2: " + parameterList2);

        //logger.debug("Candidate test case id: " + this.idCandidateTestCase);
        //logger.debug("First sequence: " + this.methodSequence1.toString());
        //logger.debug("Second sequence: " + this.methodSequence2.toString());
        //logger.debug("Longest common subsequence (LCS): " + this.longestCommonSubsequence.toString());
        //logger.debug("Positions LCS first sequence: " + this.lcsReferencesFirstSequence);
        //logger.debug("Positions LCS second sequence: " + positionsSecondSequence);

        if(parameterList1.size() != parameterList2.size()){
            throw new IllegalStateException(this.getClass().getName() + ": size of first method parameter list and size of second method parameter list must be equal");
        }

        //logger.debug("Checking if methods in the two sequences after LCS extraction, are equal");
        //for (int i = 0; i < this.lcsReferencesFirstSequence.size(); i++) {
            //logger.debug("First sequence method: " + methodSequence1.getMethodList().get(this.lcsReferencesFirstSequence.get(i)));
            //logger.debug("Second sequence method: " + methodSequence2.getMethodList().get(positionsSecondSequence.get(i)));
        //}

        List<Double> inputDistances = this.computeInputDistances(parameterList1,this.lcsReferencesFirstSequence,parameterList2,positionsSecondSequence);
        Double distance = inputDistances.stream().mapToDouble(Double::doubleValue).sum();
        return distance;
    }

    private List<Double> computeInputDistances(List<List<String>> parameterList1,
                                               List<Integer> positionsFirstSequence,
                                               List<List<String>> parameterList2,
                                               List<Integer> positionsSecondSequence){
        List<Double> inputDistances = new ArrayList<Double>();
        for (int i = 0; i < parameterList1.size(); i++) {
            //logger.debug("Analyzing parameters of method: " + this.methodSequence1.getMethodList().get(positionsFirstSequence.get(i)) + " first sequence");
            //logger.debug("Analyzing parameters of method: " + this.methodSequence2.getMethodList().get(positionsSecondSequence.get(i)) + " second sequence");
            List<String> parameters1 = parameterList1.get(i);
            List<String> parameters2 = parameterList2.get(i);
            Double sum = 0.0;
            for (int j = 0; j < parameters1.size(); j++) {
                logger.debug("Analyze parameters: " + parameters1.get(j) + " - " + parameters2.get(j));
                if(parameters1.get(j).equals(UsefulConstants.methodWithNoParameter)){
                    continue;
                }
                String parameterWithType1 = parameters1.get(j);
                String parameterWIthType2 = parameters2.get(j);
                Double distance = this.getNormalizedDistance(parameterWithType1,parameterWIthType2);
                sum = sum + distance;
            }
            if(Double.compare(sum,0.0) == 0) {
                inputDistances.add(0.0);
            }
            else{
                Double scalingFactor = 1 / (double) (parameters1.size());
                inputDistances.add((double) scalingFactor * sum);
            }
        }
        return inputDistances;
    }

    private Double getNormalizedDistance(String parameterWithType1, String parameterWithType2){
        String type1 = this.extractType(parameterWithType1);
        String type2 = this.extractType(parameterWithType2);
        //logger.debug("Get normalized distance: " + parameterWithType1 + " " + type1 + " - " + parameterWithType2 + " " + type2);

        if(type1.isEmpty() || type2.isEmpty()){
            throw new IllegalStateException(this.getClass().getName() + " the two types must not be empty. Found type1: " + type1 + " type2: " + type2);
        }

        if(!type1.equals(type2)){
            if(ParsedCustomEnum.getInstance().belongs(type1) && ParsedCustomEnum.getInstance().belongs(type2)){
                //it is ok if both custom enum implements the same interface
                CustomEnumDetails customEnumDetailsType1 = ParsedCustomEnum.getInstance().get(type1);
                CustomEnumDetails customEnumDetailsType2 = ParsedCustomEnum.getInstance().get(type2);
                List<String> implementedInterfacesType1 = customEnumDetailsType1.getImpementedInterfaces();
                List<String> implementedInterfacesType2 = customEnumDetailsType2.getImpementedInterfaces();
                List<String> commonElements = new ArrayList<String>(implementedInterfacesType2);
                commonElements.retainAll(implementedInterfacesType1);
                if(commonElements.size() == 1 && commonElements.get(0).equals(Properties.CUSTOM_INTERFACE_NAME)) throw new IllegalStateException(this.getClass().getName() + " the two types are different and they don't implement a common interface");
                logger.debug("Common interfaces: " + commonElements);
            }else{
                throw new IllegalStateException(this.getClass().getName() + " the two types must be equal during input distance computation. Found type1: " + type1 + " type2: " + type2);
            }
        }

        if(ParsedCustomClasses.getInstance().belongs(type1)){
            //the two values are integers and are categorical: if they are different d=1 otherwise d=0
            String valueString1 = this.extractValue(parameterWithType1);
            String valueString2 = this.extractValue(parameterWithType2);
            Integer value1 = Integer.parseInt(valueString1);
            Integer value2 = Integer.parseInt(valueString2);
            if(!value1.equals(value2)) return 1.0;
            else return 0.0;
        }else if(type1.equals(Properties.CUSTOM_INTERFACE_NAME) || type1.equals("int")){
            //the two values are integers and are not categorical: compute absolute value and normalize the result
            String valueString1 = this.extractValue(parameterWithType1);
            String valueString2 = this.extractValue(parameterWithType2);
            Integer value1 = Integer.parseInt(valueString1);
            Integer value2 = Integer.parseInt(valueString2);
            Integer abs = Math.abs(value1 - value2);
            Double normalized = this.normalize(abs);
            return normalized;
        }else if(ParsedCustomEnum.getInstance().belongs(type1)){ // TODO: try to get real param types from enum types
            //the two values are strings and are categorical: if they are different d=1 otherwise d=0
            String value1 = this.extractValue(parameterWithType1);
            String value2 = this.extractValue(parameterWithType2);
            if(!value1.equals(value2)) return 1.0;
            else return 0.0;
        }else if(type1.equals("java.lang.String")){
            //the two values are strings and are not categorical: compute Levenshtein distance and normalize it
            String value1 = this.extractValue(parameterWithType1);
            String value2 = this.extractValue(parameterWithType2);
            int levenshteinDistance = LevenshteinString.computeDistance(value1,value2);
            Double normalized = this.normalize(levenshteinDistance);
            //logger.debug("String value1: " + value1 + ". String value2: " + value2 + ". Levenshtein distance: " + levenshteinDistance + ". Normalized: " + normalized);
            return normalized;
        }
        else{
            throw new IllegalStateException(this.getClass().getName() + " found type " + type1 + " not supported yet");
        }
    }

    private Double normalize(int toBeNormalized){
        Double d = ((double) toBeNormalized / ((double) toBeNormalized + 1));
        //BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);
        //d = bd.doubleValue();
        return d;
    }

    //TODO refactor extractValue and extractType as single function given that they are different only for delimiters
    //syntax of parameter grouping: (value[type])
    private String extractValue(String stringWithParenthesis){
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("\\((.*)\\["); //matches any character between '(' and '['
        Matcher matcher = pattern.matcher(stringWithParenthesis);
        if(matcher.find()){
            buffer.append(matcher.group(1)); //take text between delimiters
        }
        return buffer.toString();
    }

    //TODO refactor extractValue and extractType as single function given that they are different only for delimiters
    //syntax of parameter grouping: (value[type])
    private String extractType(String stringWithParenthesis){
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("\\[(.*)\\]"); //matches any character between '[' and ']'
        Matcher matcher = pattern.matcher(stringWithParenthesis);
        if(matcher.find()){
            buffer.append(matcher.group(1)); //take text between delimiters
        }
        return buffer.toString();
    }

    private List<List<String>> getParameterList(MethodSequence methodSequence, List<Integer> positions){
        List<List<String>> parameterList = new ArrayList<List<String>>();
        for (int i = 0; i < positions.size(); i++) {
            List<String> parameters = methodSequence.getParametersOfMethod(positions.get(i));
            //logger.debug("Parameter of method: " + methodSequence.getMethodList().get(positions.get(i)) + " " + parameters);
            parameterList.add(parameters);
        }
        return parameterList;
    }

    private List<Integer> getPositionsSecondSequence(List<Integer> lcsReferencesFirstSequence, List<String> firstMethodList, List<String> secondMethodList){
        List<Integer> positionsSecondSequence = new ArrayList<Integer>();
        int counterSecondSequence = 0;
        for (int i = 0; i < lcsReferencesFirstSequence.size(); i++) {
            String methodNameFirstSequence = firstMethodList.get(lcsReferencesFirstSequence.get(i));
            int index = this.indexOfWithIndex(secondMethodList,methodNameFirstSequence,counterSecondSequence);
            if(index != -1){
                counterSecondSequence = index + 1;
                positionsSecondSequence.add(index);
            }
        }
        return positionsSecondSequence;
    }

    private int indexOfWithIndex(List<String> methodList, String methodName, int startingFromIndex){
        for (int i = startingFromIndex; i < methodList.size(); i++) {
            if(methodList.get(i).equals(methodName)) return i;
        }
        return -1;
    }
}
