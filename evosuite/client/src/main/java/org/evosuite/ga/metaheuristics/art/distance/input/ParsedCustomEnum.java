package org.evosuite.ga.metaheuristics.art.distance.input;

import org.evosuite.ga.metaheuristics.art.distance.input.parsing.CustomEnumDetails;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParsedCustomEnum implements ParsedTestData<CustomEnumDetails>{

    private static ParsedCustomEnum ourInstance = new ParsedCustomEnum();

    private Map<String,CustomEnumDetails> mapCustomEnumNameToParsedVersion;

    public static ParsedCustomEnum getInstance() { return ourInstance; }

    private ParsedCustomEnum(){
        this.mapCustomEnumNameToParsedVersion = new LinkedHashMap<String,CustomEnumDetails>();
    }

    public void put(String customEnumName, CustomEnumDetails customEnumDetails){
        this.mapCustomEnumNameToParsedVersion.put(customEnumName, customEnumDetails);
    }

    public CustomEnumDetails get(String customEnumName){
        return this.mapCustomEnumNameToParsedVersion.get(customEnumName);
    }

    public Set<String> keys(){
        return this.mapCustomEnumNameToParsedVersion.keySet();
    }

    public Collection<CustomEnumDetails> values(){
        return this.mapCustomEnumNameToParsedVersion.values();
    }

    public boolean belongs(String type){
        if(this.mapCustomEnumNameToParsedVersion.keySet().contains(type)) return true;
        return false;
    }
}
