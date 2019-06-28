package org.evosuite.ga.metaheuristics.art.distance.input;

public class PrimitiveValue {

    private Object value;

    public PrimitiveValue(Object value){
        this.value = value;
    }

    public boolean isPrimitive(){
        if(value instanceof Integer){
            return true;
        }else if(value instanceof Boolean){
            return true;
        }else if(value instanceof String){
            return true;
        }else if(value instanceof Float){
            return true;
        }else if(value instanceof Double){
            return true;
        }
        return false;
    }
}
