package org.evosuite.ga.metaheuristics.art.sequencealignment;

import java.util.List;

public interface MethodSequence {

    public boolean equals(MethodSequence methodSequence);

    public String charAt(int i);

    public int length();

    public boolean hasMethod(String methodName);

    public List<String> getMethodList();

    public List<List<String>> getParametersList();

    public List<String> getParametersOfMethod(int index);

}
