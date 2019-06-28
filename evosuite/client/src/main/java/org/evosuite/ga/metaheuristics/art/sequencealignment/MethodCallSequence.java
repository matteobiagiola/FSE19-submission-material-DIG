package org.evosuite.ga.metaheuristics.art.sequencealignment;

import org.evosuite.ga.metaheuristics.art.distance.input.UsefulConstants;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MethodCallSequence implements MethodSequence {

    /* # */
    private final String separator;
    /*
    * a(1,1)#b(5,"foo")#c()
    * */
    private final String methodSequence;
    /*
    * (1[Id]),(1[int])#(5[Category]),("foo"[String])#()
    * */
    private final String parameterSequence;
    private final String[] methods;
    private final String[] parametersForEachMethod;
    private Map<String,List<String>> mapMethodToParameters = new LinkedHashMap<String,List<String>>();

    /**
     * @param separator at the moment do not pass a character that needs to be escaped in a regex
     * @param methodSequence formatted sequence
    * */
    public MethodCallSequence(String separator, String methodSequence){
        this.separator = separator;
        this.methodSequence = methodSequence;
        this.methods = this.methodSequence.split(this.separator);
        this.parameterSequence = "";
        this.parametersForEachMethod = new String[]{""};
    }

    public MethodCallSequence(String separator, String methodSequence, String parameterSequence){
        this.separator = separator;
        this.methodSequence = methodSequence;
        this.methods = this.methodSequence.split(this.separator);
        this.parameterSequence = parameterSequence;
        this.parametersForEachMethod = parameterSequence.split(UsefulConstants.methodParameterInterSeparator);
        //this.mapMethodToParameters = this.mapMethodNameToAssociatedParameters();
    }

    public MethodCallSequence(String separator, String[] methods){
        this.methods = methods;
        this.separator = separator;
        this.methodSequence = this.buildStringFromMethods(separator,methods);
        this.parameterSequence = "";
        this.parametersForEachMethod = new String[]{""};
    }

    private String buildStringFromMethods(String separator, String[] methods){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < methods.length; i++) {
            buffer.append(methods[i]);
            if(i != methods.length - 1){
                buffer.append(separator);
            }
        }
        return buffer.toString();
    }

    private String buildStringFromMethodsPlusParameters(String separator, String[] methods){
        StringBuffer buffer = new StringBuffer();
        List<List<String>> parameterList = this.getParametersList();
        for (int i = 0; i < methods.length; i++) {
            buffer.append(i + ":" + methods[i]);
            if(i != methods.length - 1){
                buffer.append(separator);
            }
            buffer.append("\n");
        }
        for (int i = 0; i < methods.length; i++) {
            buffer.append(i + ":" + parameterList.get(i));
            if(i != methods.length - 1){
                buffer.append(separator);
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

    private Map<String,List<String>> mapMethodNameToAssociatedParameters(){
        Map<String,List<String>> mapMethodToParameters = new LinkedHashMap<String,List<String>>();
        List<List<String>> parameterList = this.getParametersList();
        for (int i = 0; i < this.methods.length; i++) {
            String key = this.methods[i] + i;
            mapMethodToParameters.put(key,parameterList.get(i));
        }
        return mapMethodToParameters;
    }

    //TODO to fix
    private boolean checkSequenceFormat(){
        String regex = "([a-zA-Z]+" + this.separator + "?)+";
        boolean b = Pattern.matches(regex, this.methodSequence);
        return b;
    }

    @Override
    public boolean equals(MethodSequence sequence) {
        if(sequence instanceof MethodCallSequence){
            MethodCallSequence methodCallSequence = (MethodCallSequence) sequence;
            String[] otherMethods = methodCallSequence.getMethods();
            return this.same(otherMethods);
        }else{
            throw new IllegalArgumentException(this.getClass().getName() + " equals method. Provide a methodCallSequence object as parameter");
        }
    }

    public String charAt(int index) {
        return this.methods[index];
    }

    public int length() {
        return this.methods.length;
    }

    @Override
    public boolean hasMethod(String methodName) {
        if(this.getMethodList().indexOf(methodName) != -1){
            return true;
        }
        return false;
    }

    @Override
    public List<String> getMethodList() {
        return Arrays.stream(this.getMethods()).collect(Collectors.toList());
    }

    @Override
    public List<List<String>> getParametersList() {
        List<List<String>> parameterList = new ArrayList<List<String>>();
        if(!this.parameterSequence.isEmpty()){
            parameterList = Arrays.stream(this.parametersForEachMethod).map(new Function<String, List<String>>() {
                @Override
                public List<String> apply(String methodParameters) {
                    if(methodParameters.equals(UsefulConstants.methodWithNoParameter)){
                        List<String> result = new ArrayList<String>();
                        result.add(UsefulConstants.methodWithNoParameter);
                        return result;
                    }else{
                        String[] individualParameters = methodParameters.split(UsefulConstants.methodParameterIntraSeparator);
                        return Arrays.stream(individualParameters).collect(Collectors.toList());
                    }
                }
            }).collect(Collectors.toList());
        }else{
            throw new IllegalStateException("getParameterList: parameterSequence must not be empty");
        }
        return parameterList;
    }

    @Override
    public List<String> getParametersOfMethod(int index) {
        return this.getParametersList().get(index);
    }

    private String[] getMethods(){
        return this.methods;
    }

    private boolean same(String[] otherMethods){
        int otherLength = otherMethods.length;
        if(otherLength != this.length()){
            return false;
        }
        for (int i = 0; i < this.length(); i++) {
            String method1 = this.methods[i];
            String method2 = otherMethods[i];
            if(!method1.equals(method2)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        String result = "";
        if(this.parametersForEachMethod.length != 0) {
            result = "Methods + params: " + this.buildStringFromMethodsPlusParameters(separator, methods);
            //result = "Methods: " + this.buildStringFromMethods(this.separator,this.methods) + "\nParameters: " + this.parameterSequence;
        }else{
            result = "Methods: " + this.buildStringFromMethods(this.separator,this.methods);
        }
        return result;
    }
}
