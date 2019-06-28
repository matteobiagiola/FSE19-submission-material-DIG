package org.evosuite.ga.metaheuristics.art.distance.input;

import org.evosuite.ga.metaheuristics.art.distance.input.parsing.CustomClassDetails;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParsedCustomClasses implements ParsedTestData<CustomClassDetails>{

    private static ParsedCustomClasses ourInstance = new ParsedCustomClasses();

    private Map<String,CustomClassDetails> mapCustomClassNameToParsedVersion;

    public static ParsedCustomClasses getInstance() { return ourInstance; }

    private ParsedCustomClasses(){
        this.mapCustomClassNameToParsedVersion = new LinkedHashMap<String,CustomClassDetails>();
    }

    public void put(String customClassName, CustomClassDetails customClassDetails){
        this.mapCustomClassNameToParsedVersion.put(customClassName,customClassDetails);
    }

    public CustomClassDetails get(String customClassName){
        return this.mapCustomClassNameToParsedVersion.get(customClassName);
    }

    public Set<String> keys(){
        return this.mapCustomClassNameToParsedVersion.keySet();
    }

    public Collection<CustomClassDetails> values(){
        return this.mapCustomClassNameToParsedVersion.values();
    }

    public boolean belongs(String type){
        if(this.mapCustomClassNameToParsedVersion.keySet().contains(type)) return true;
        return false;
    }

}
