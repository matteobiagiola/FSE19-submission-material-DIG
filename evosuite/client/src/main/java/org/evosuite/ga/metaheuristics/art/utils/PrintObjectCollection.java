package org.evosuite.ga.metaheuristics.art.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrintObjectCollection {

    private static final Logger logger = LoggerFactory.getLogger(PrintObjectCollection.class);
    private static final String stringSeparator = "####";

    public static <E> void print(Collection<E> collection, Class<?> callerClass, String message){
        logger.debug("Caller class: " + callerClass.getSimpleName() + ". Message: " + message + ". Elements in the collection: " + collection.size());
        String stringToPrint = collection.stream().map(new Function<E, String>() {
            @Override
            public String apply(E e) {
                return e.toString();
            }
        }).collect(Collectors.joining(stringSeparator));
        logger.debug(stringToPrint);
    }
}
