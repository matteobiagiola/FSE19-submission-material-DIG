package org.evosuite.ga.metaheuristics.art.distance;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.metaheuristics.art.distance.input.parsing.CustomClassDetails;
import org.evosuite.ga.metaheuristics.art.distance.input.parsing.Parser;
import org.evosuite.ga.metaheuristics.art.utils.PrintObjectCollection;
import org.evosuite.selenium.utils.CheckCondition;
import org.evosuite.testcase.TestChromosome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class DistanceComputation<T extends Chromosome> {

    private List<T> candidates;
    private List<T> executed;
    private int iteration = 0;
    private static final Logger logger = LoggerFactory.getLogger(DistanceComputation.class);

    public DistanceComputation(List<T> candidates,
                               List<T> executed,
                               int iteration){
        this.candidates = candidates;
        CheckCondition.checkArguments(!this.candidates.isEmpty(), "Candidates list cannot be empty");
        this.executed = executed;
        CheckCondition.checkArguments(!this.candidates.isEmpty(), "Executed list cannot be empty");
        this.iteration = iteration;
    }

    /**
     * @return TestChromosome in the list of candidate test cases with the biggest distance from the list of test cases already executed
    * */
    public T getTestCaseToExecute(){

        MapCandidatesToMinDistance<T> mapCandidatesToMinDistance = new MapCandidatesToMinDistance<T>(this.executed);
        List<Double> minDistances = this.candidates.stream()
                                         .map(mapCandidatesToMinDistance)
                                         .collect(Collectors.toList());

        //Double::compare uses the default comparator for integers
        Optional<Double> optionalMaxDistance = minDistances.stream().max(Double::compare);

        if(optionalMaxDistance.isPresent()){
            Double maxDistance = optionalMaxDistance.get();
            int indexOfTestCaseToExecute = minDistances.indexOf(maxDistance);
            /*logger.debug("maxDistance for all executed test cases, iteration " + this.iteration
                    + ": " + maxDistance + ". Index of test case to execute: " + indexOfTestCaseToExecute);*/
            T testCaseToExecute = this.candidates.get(indexOfTestCaseToExecute);
            return testCaseToExecute;
        }else{
            throw new IllegalStateException(this.getClass().getName() + " getTestCaseToExecute: there must exist a max distance. Array: " + minDistances);
        }
    }

    /**
    * @return number of candidates that have the same distance
    * */
    private int compareMinDistances(List<Double> minDistances){
        // Collections.frequency: Returns the number of elements in the specified collection equal to the specified object. More formally, returns the number of elements e in the collection such that (o == null ? e == null : o.equals(e)).
        //numbers.stream().filter(i -> Collections.frequency(numbers, i) >1)
                //.collect(Collectors.toSet()).forEach(System.out::println);
        //List<Integer> fakeMinDistances = Arrays.asList(1, 2, 3, 4, 5, 5, 1, 1, 9, 10, 5, 4, 11);
        Set<Double> uniqueItems = new LinkedHashSet<Double>();
        minDistances.stream().forEach((minDistance) -> {
            uniqueItems.add(minDistance);
        });
        //PrintObjectCollection.print(uniqueItems,this.getClass(),"Unique items");
        int numberOfDuplicates = minDistances.size() - uniqueItems.size();
        return numberOfDuplicates;
    }


}
