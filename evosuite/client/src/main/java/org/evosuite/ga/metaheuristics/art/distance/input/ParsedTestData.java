package org.evosuite.ga.metaheuristics.art.distance.input;

import java.util.Collection;

public interface ParsedTestData<T> {

    public void put(String testDataName, T t);

    public T get(String testDataName);

    public Collection<String> keys();

    public Collection<T> values();

    public boolean belongs(String type);
}
