package org.evosuite.ga.metaheuristics.art.distance;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.metaheuristics.art.distance.input.InputDistanceComputation;
import org.evosuite.ga.metaheuristics.art.distance.input.UsefulConstants;
import org.evosuite.ga.metaheuristics.art.sequencealignment.LongestCommonSubsequence;
import org.evosuite.ga.metaheuristics.art.sequencealignment.MethodSequence;
import org.evosuite.ga.metaheuristics.art.utils.PrintObjectCollection;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapCandidatesToMinDistance<T extends Chromosome> implements Function<T,Double> {

    private List<T> executed;
    private final String separator = UsefulConstants.methodSeparator;
    private List<MethodSequence> executedMethodSequences;
    private static final Logger logger = LoggerFactory.getLogger(DistanceComputation.class);

    public MapCandidatesToMinDistance(List<T> executed){
        this.executed = executed;
        this.executedMethodSequences = this.extractExecutedSequences();
    }

    private List<MethodSequence> extractExecutedSequences(){
        List<MethodSequence> executedMethodSequences = this.executed.stream().map(new Function<T, MethodSequence>() {
            @Override
            public MethodSequence apply(T t) {
                TestChromosome executedTestChromosome = (TestChromosome) t;
                TestCase executedTestCase = executedTestChromosome.getTestCase();
                MethodSequenceExtractor methodSequenceExtractor = new MethodSequenceExtractor(executedTestCase,separator);
                MethodSequence executedMethodSequence = methodSequenceExtractor.extractMethodSequence();
                return executedMethodSequence;
            }
        }).collect(Collectors.toList());

        //PrintObjectCollection.print(sequences,this.getClass(),"candidate sequences");

        return executedMethodSequences;
    }


    @Override
    public Double apply(T t) {
        TestChromosome candidateTestChromosome = (TestChromosome) t;
        TestCase candidateTestCase = candidateTestChromosome.getTestCase();
        int idCandidateTestCase = candidateTestCase.getID();
        MethodSequenceExtractor methodSequenceExtractor = new MethodSequenceExtractor(candidateTestCase,this.separator);
        MethodSequence candidateMethodSequence = methodSequenceExtractor.extractMethodSequence();
        Double minDistance = this.computeMinDistance(candidateMethodSequence,idCandidateTestCase,this.executedMethodSequences);

        logger.debug("minDistance for candidate test case " + idCandidateTestCase + ": " + minDistance);
        return minDistance;
    }

    private Double computeMinDistance(MethodSequence candidateMethodSequence, int idCandidateTestCase, List<MethodSequence> executedMethodSequences){
        List<Double> distances = executedMethodSequences.stream().map(new Function<MethodSequence, Double>() {
            @Override
            public Double apply(MethodSequence executedSequence) {
                LongestCommonSubsequence lcs = new LongestCommonSubsequence(executedSequence, candidateMethodSequence);
                MethodSequence longestCommonSubsequence = lcs.getLongestCommonSubsequence(separator);
                Integer levenshteinDistance = lcs.getLevenshteinDistance(longestCommonSubsequence);
                if(Properties.INPUT_DISTANCE){
                    List<Integer> lcsReferencesFirstSequence = lcs.getLcsReferencesFirstSequence();
                    InputDistanceComputation inputDistanceComputation = new InputDistanceComputation(idCandidateTestCase, executedSequence,
                            candidateMethodSequence,longestCommonSubsequence, lcsReferencesFirstSequence);
                    Double inputDistance = inputDistanceComputation.computeDistance();
                    return levenshteinDistance + inputDistance;
                }else{
                    return levenshteinDistance.doubleValue();
                }
            }
        }).collect(Collectors.toList());

        PrintObjectCollection.print(distances,this.getClass(),"distances for candidate test case " + idCandidateTestCase);

        //Double::compare uses the default comparator for integers
        Optional<Double> optionalMinDistance = distances.stream().min(Double::compare);

        if(optionalMinDistance.isPresent()){
            Double minDistance = optionalMinDistance.get();
            return minDistance;
        }else{
            throw new IllegalStateException(this.getClass().getName() + " computeMinDistance: there must exist a min distance. Array: " + optionalMinDistance);
        }
    }


}
